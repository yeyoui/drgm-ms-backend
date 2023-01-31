package com.drugms.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.drugms.common.CustomException;
import com.drugms.dto.DrugProblemInfoDto;
import com.drugms.dto.OrderInfoDto;
import com.drugms.entity.DrugProblemInfo;
import com.drugms.entity.WarehouseInfo;
import com.drugms.mapper.DrugProblemInfoMapper;
import com.drugms.service.DrugProblemInfoService;
import com.drugms.service.WarehouseInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

/**
 * <p>
 * 药品问题信息 服务实现类
 * </p>
 *
 * @author lhy
 * @since 2023-01-10
 */
@Service
public class DrugProblemInfoServiceImpl extends ServiceImpl<DrugProblemInfoMapper, DrugProblemInfo> implements DrugProblemInfoService {
    @Autowired
    DrugProblemInfoMapper drugProblemInfoMapper;
    @Autowired
    WarehouseInfoService warehouseInfoService;

    @Override
    public List<DrugProblemInfoDto> getDrugProblemInfoDtoPage(int type, int curPage, int limit, String name) {
        String drugName=null;
        Integer problemType=null;
        Integer handleType=null;
        String[] strings = name.split(",");
        if(!" ".equals(strings[0])) drugName=strings[0];
        if(!" ".equals(strings[1])) problemType=Integer.parseInt(strings[1]);
        if(!" ".equals(strings[2])) handleType=Integer.parseInt(strings[2]);

        return drugProblemInfoMapper.getDrugProblemInfoDtoPage(type, curPage, limit, drugName, problemType, handleType);
    }

    @Override
    public Integer getDrugProblemInfoDtoPageCount(int type, int curPage, int limit, String name) {
        String drugName=null;
        Integer problemType=null;
        Integer handleType=null;
        String[] strings = name.split(",");
        if(!" ".equals(strings[0])){
            drugName="%"+strings[0]+"%";
        }
        if(!" ".equals(strings[1])) problemType=Integer.parseInt(strings[1]);
        if(!" ".equals(strings[2])) handleType=Integer.parseInt(strings[2]);

        return drugProblemInfoMapper.getDrugProblemInfoDtoPageCount(type, curPage, limit, drugName, problemType, handleType);
    }

    /**
     *提交问题药品信息
     * @param drugProblemInfo 需要提供Wid、ProblemType、PrchsNum
     */
    @Override
    public void submitDrugProblem(DrugProblemInfo drugProblemInfo,Boolean inSale) {
        //获取当前时间
        LocalDateTime nowTime = LocalDateTime.now();
        //初始化数据
        drugProblemInfo.setCreateTime(nowTime);
        drugProblemInfo.setHadHandle(false);
        drugProblemInfo.setUpdateTime(nowTime);
        //同步更新到仓库的库存信息
        if(drugProblemInfo.getDpNum()<=0) throw new CustomException("问题数量必须大于0 当前输入："+drugProblemInfo.getDpNum());
        WarehouseInfo warehouseInfo = warehouseInfoService.getById(drugProblemInfo.getWid());
        //更新后的库存量
        if(!inSale){//在订单的退货信息不需要更新仓库库存
            int remain=warehouseInfo.getStock()-drugProblemInfo.getDpNum();
            if(remain<0) throw new CustomException("库存不足");
            UpdateWrapper<WarehouseInfo> updateWrapper = new UpdateWrapper<>();
            updateWrapper.eq("wid",warehouseInfo.getWid()).set("stock",remain);
            warehouseInfoService.update(updateWrapper);
        }
        //判断DrugProblemInfo中是否有相同的仓库记录ID和类型并且未处理
        LambdaQueryWrapper<DrugProblemInfo> dpWQ = new LambdaQueryWrapper<>();
        dpWQ.eq(DrugProblemInfo::getWid,warehouseInfo.getWid()).eq(DrugProblemInfo::getProblemType,drugProblemInfo.getProblemType()).eq(DrugProblemInfo::getHadHandle,false);
        DrugProblemInfo preProblemInfo = this.getOne(dpWQ);
        if(preProblemInfo!=null){
            //药品问题表中有了相同药品
            //更新数据
            UpdateWrapper<DrugProblemInfo> dpUpdateWrapper = new UpdateWrapper<>();
            dpUpdateWrapper.eq("dpid",preProblemInfo.getDpid()).set("dp_num",preProblemInfo.getDpNum()+drugProblemInfo.getDpNum())
                    .set("update_time", nowTime);
            this.update(dpUpdateWrapper);
            return;
        }

        this.save(drugProblemInfo);
    }

    @Override
    public void updateInfoAfterHandler(Integer dpid) {
        DrugProblemInfo problemInfo = this.getById(dpid);
        problemInfo.setUpdateTime(LocalDateTime.now());
        problemInfo.setHadHandle(true);
        this.updateById(problemInfo);
    }
}
