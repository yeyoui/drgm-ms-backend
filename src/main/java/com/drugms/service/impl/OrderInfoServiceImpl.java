package com.drugms.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.drugms.common.MSGlobalObject;
import com.drugms.dto.OrderInfoDto;
import com.drugms.entity.DrugInfo;
import com.drugms.entity.OrderInfo;
import com.drugms.entity.WarehouseInfo;
import com.drugms.mapper.OrderInfoMapper;
import com.drugms.service.DrugInfoService;
import com.drugms.service.OrderInfoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.drugms.service.WarehouseInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

/**
 * <p>
 * 订单信息 服务实现类
 * </p>
 *
 * @author lhy
 * @since 2023-01-05
 */
@Service
public class OrderInfoServiceImpl extends ServiceImpl<OrderInfoMapper, OrderInfo> implements OrderInfoService {
    @Autowired
    private OrderInfoMapper orderInfoMapper;
    @Autowired
    private DrugInfoService drugInfoService;
    @Autowired
    private WarehouseInfoService warehouseInfoService;

    @Override
    public List<OrderInfoDto> getOrderDtoPage(int type, int curPage, int limit, String name) {
        if(type==0) return orderInfoMapper.getOrderDtoPage(type,curPage,limit,"%"+name+"%");
        else  return orderInfoMapper.getOrderDtoPage(type,curPage,limit,name);
    }

    @Override
    public Integer getOrderDtoPageCount(int type, int curPage, int limit, String name) {
        if(type==0) return orderInfoMapper.getOrderDtoPageCount(type,curPage,limit,"%"+name+"%");
        else  return orderInfoMapper.getOrderDtoPageCount(type,curPage,limit,name);
    }

    @Override
    public void updOrderStatus(Integer oid,Integer status) {
        UpdateWrapper<OrderInfo> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("oid",oid).set("status",status);
        this.update(updateWrapper);
    }

    @Override
    public void updSales(int num, int did,int wid,Boolean modifyOrder) {
        DrugInfo drugInfo = drugInfoService.getById(did);
        WarehouseInfo warehouseInfo = warehouseInfoService.getById(wid);
        //计算利润
        BigDecimal profit=warehouseInfo.getGoodsPrice().subtract(drugInfo.getDrugPrice()).setScale(2, RoundingMode.HALF_UP);
        if(modifyOrder) MSGlobalObject.dailySales.purchaseItem(num,warehouseInfo.getGoodsPrice(),profit);
        else MSGlobalObject.dailySales.updPurchaseItem(num,warehouseInfo.getGoodsPrice(),profit);
    }

    @Override
    public void updRetSales(int num, int did, int wid) {
        DrugInfo drugInfo = drugInfoService.getById(did);
        WarehouseInfo warehouseInfo = warehouseInfoService.getById(wid);
        //计算利润
        BigDecimal profit=warehouseInfo.getGoodsPrice().subtract(drugInfo.getDrugPrice()).setScale(2, RoundingMode.HALF_UP);
        MSGlobalObject.dailySales.retItem(num,warehouseInfo.getGoodsPrice(),profit);
    }

}
