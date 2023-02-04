package com.drugms.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.drugms.common.CustomException;
import com.drugms.dto.WhPrchsInfoDto;
import com.drugms.entity.DrugInfo;
import com.drugms.entity.DrugProblemInfo;
import com.drugms.entity.WarehouseInfo;
import com.drugms.entity.WhPrchsInfo;
import com.drugms.mapper.WhPrchsInfoMapper;
import com.drugms.service.DrugInfoService;
import com.drugms.service.DrugProblemInfoService;
import com.drugms.service.WarehouseInfoService;
import com.drugms.service.WhPrchsInfoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 * 进货信息 服务实现类
 * </p>
 *
 * @author lhy
 * @since 2023-01-05
 */
@Service
public class WhPrchsInfoServiceImpl extends ServiceImpl<WhPrchsInfoMapper, WhPrchsInfo> implements WhPrchsInfoService {
    @Autowired
    WarehouseInfoService warehouseInfoService;
    @Autowired
    DrugProblemInfoService drugProblemInfoService;
    @Autowired
    DrugInfoService drugInfoService;
    @Autowired
    WhPrchsInfoMapper whPrchsInfoMapper;

    @Override
    public Boolean addWhPrchs(WhPrchsInfo whPrchsInfo) {
        //设置药品未过期状态
        whPrchsInfo.setHadExpired(false);
        //设置药品的剩余量为供应量
        whPrchsInfo.setRemain(whPrchsInfo.getSplyNum());
        //进货时间
        whPrchsInfo.setPrchsTime(LocalDateTime.now());
        //在仓库中查询是否存在该药品
        LambdaQueryWrapper<WarehouseInfo> WIwq = new LambdaQueryWrapper<>();
        WIwq.eq(WarehouseInfo::getDid,whPrchsInfo.getDid());
        WarehouseInfo WItem = warehouseInfoService.getOne(WIwq);
        if(WItem==null){
            //仓库内无此药品
            WarehouseInfo warehouseInfo = new WarehouseInfo(null,whPrchsInfo.getDid(),whPrchsInfo.getSplyNum(),0,0,new BigDecimal(99999),false);
            warehouseInfoService.save(warehouseInfo);
            whPrchsInfo.setWid(warehouseInfo.getWid());//获取新增的仓库ID
        }else {
            //仓库内有此药品
            whPrchsInfo.setWid(WItem.getWid());//获取仓库ID
            //更新仓库信息
            UpdateWrapper<WarehouseInfo> updateWrapper = new UpdateWrapper<>();
            updateWrapper.eq("wid",WItem.getWid()).set("stock",WItem.getStock()+whPrchsInfo.getSplyNum());
            warehouseInfoService.update(updateWrapper);//更新库存
        }
        this.save(whPrchsInfo);//更新进货信息
        return true;
    }


    @Override
    public void checkAndUpdExpiredDrug() {
        //检查所有未过期的药品
        LambdaQueryWrapper<WhPrchsInfo> whPrchsInfoQW = new LambdaQueryWrapper<>();
        whPrchsInfoQW.eq(WhPrchsInfo::getHadExpired,false).ne(WhPrchsInfo::getRemain,0);
        List<WhPrchsInfo> whPrchsInfoList = this.list(whPrchsInfoQW);

        LocalDateTime nowTime=LocalDateTime.now();
        whPrchsInfoList.forEach((whPrchsInfo -> {
            //获取保质期
            DrugInfo drugInfo = drugInfoService.getById(whPrchsInfo.getDid());
            Integer warranty = drugInfo.getWarranty();
            LocalDateTime proTime = whPrchsInfo.getProTime();
            //获取相差的天数（保质期剩余10天内的算作过期,保质期的一个月按31天算）
            Duration duration = Duration.between(proTime, nowTime);
            long days = duration.toDays();
            if(days>=10+warranty* 31L){
                //构建问题药品信息
                DrugProblemInfo drugProblemInfo = new DrugProblemInfo();
                drugProblemInfo.setProblemType(0);
                drugProblemInfo.setWid(whPrchsInfo.getWid());
                drugProblemInfo.setDpNum(whPrchsInfo.getRemain());
                //减少当前药品剩余量
                this.decRemainByWID(drugProblemInfo.getWid(),drugProblemInfo.getDpNum());
                //需要处理过期药品
                UpdateWrapper<WhPrchsInfo> whPrchsInfoUW = new UpdateWrapper<>();
                whPrchsInfoUW.eq("prchs_no",whPrchsInfo.getPrchsNo()).set("had_expired",true);
                this.update(whPrchsInfoUW);
                //提交到问题列表中
                drugProblemInfoService.submitDrugProblem(drugProblemInfo,false);
            }
        }));
    }

    @Override
    public List<WhPrchsInfoDto> getWhPrchsPage(int type, int curPage, int limit, String name) {
        if(type==2) return whPrchsInfoMapper.getWhPrchsPage(type,(curPage-1)*limit,limit,name);
        return whPrchsInfoMapper.getWhPrchsPage(type,(curPage-1)*limit,limit,"%"+name+"%");
    }

    @Override
    public Integer getWhPrchsPageCount(int type, int curPage, int limit, String name) {
        if(type==2) return whPrchsInfoMapper.getWhPrchsPageCount(type,(curPage-1)*limit,limit,name);
        return whPrchsInfoMapper.getWhPrchsPageCount(type,(curPage-1)*limit,limit,"%"+name+"%");
    }

    /**
     * 增加进货信息药品的剩余量
     */
    @Override
    public void addRemainByWID(Integer wid, int num) {
        //获取进货信息
        LambdaQueryWrapper<WhPrchsInfo> whPrchsInfoQW = new LambdaQueryWrapper<>();
        whPrchsInfoQW.eq(WhPrchsInfo::getWid,wid).eq(WhPrchsInfo::getHadExpired,false);
        List<WhPrchsInfo> whPrchsInfoList = this.list(whPrchsInfoQW);
        //当前药品已过期，不进行处理
        if(whPrchsInfoList.size()==0) throw new CustomException("当前药品已过期，拒绝返厂");
        //增加剩余量
        UpdateWrapper<WhPrchsInfo> whPrchsInfoUW = new UpdateWrapper<>();
        whPrchsInfoUW.eq("prchs_no", whPrchsInfoList.get(0).getPrchsNo()).set(
                "remain",whPrchsInfoList.get(0).getRemain()+num);
        this.update(whPrchsInfoUW);
    }
    /**
     * 减少进货信息药品的剩余量
     */
    @Override
    public void decRemainByWID(Integer wid, int num) {
        //获取进货信息
        LambdaQueryWrapper<WhPrchsInfo> whPrchsInfoQW = new LambdaQueryWrapper<>();
        whPrchsInfoQW.eq(WhPrchsInfo::getWid,wid).eq(WhPrchsInfo::getHadExpired,false);
        List<WhPrchsInfo> whPrchsInfoList = this.list(whPrchsInfoQW);
        //减少剩余量
        for (WhPrchsInfo whPrchsInfo : whPrchsInfoList) {
            //剩余量不足，需要遍历多次
            UpdateWrapper<WhPrchsInfo> whPrchsInfoUW = new UpdateWrapper<>();
            if(whPrchsInfo.getRemain()<num){
                //减少剩余量
                num-=whPrchsInfo.getRemain();
                whPrchsInfoUW.eq("prchs_no", whPrchsInfo.getPrchsNo()).set(
                        "remain",0);
                this.update(whPrchsInfoUW);
            }else {
                //可以在一个进货项中减去剩余量
                //减少剩余量
                whPrchsInfoUW.eq("prchs_no", whPrchsInfo.getPrchsNo()).set(
                        "remain",whPrchsInfo.getRemain()-num);
                this.update(whPrchsInfoUW);
                return;
            }
        }
    }
}
