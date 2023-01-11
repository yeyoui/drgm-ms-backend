package com.drugms.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.drugms.entity.WarehouseInfo;
import com.drugms.entity.WhPrchsInfo;
import com.drugms.mapper.WhPrchsInfoMapper;
import com.drugms.service.WarehouseInfoService;
import com.drugms.service.WhPrchsInfoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

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

    @Override
    public Boolean addWhPrchs(WhPrchsInfo whPrchsInfo) {
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
}
