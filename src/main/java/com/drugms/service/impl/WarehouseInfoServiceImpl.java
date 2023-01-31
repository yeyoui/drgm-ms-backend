package com.drugms.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.drugms.dto.WarehouseInfoDto;
import com.drugms.entity.DrugInfo;
import com.drugms.entity.DrugProblemInfo;
import com.drugms.entity.WarehouseInfo;
import com.drugms.mapper.WarehouseInfoMapper;
import com.drugms.service.WarehouseInfoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 药品仓库信息 服务实现类
 * </p>
 *
 * @author lhy
 * @since 2023-01-05
 */
@Service
public class WarehouseInfoServiceImpl extends ServiceImpl<WarehouseInfoMapper, WarehouseInfo> implements WarehouseInfoService {
    @Autowired
    WarehouseInfoMapper warehouseInfoMapper;

    @Override
    public List<WarehouseInfoDto> getWhDtoPage(int type, int curPage, int limit, String name) {
        return warehouseInfoMapper.WarehouseInfoDtoPage(type,(curPage-1)*limit,limit,"%"+name+"%");
    }

    @Override
    public Integer getWhDtoPageCount(int type, int curPage, int limit, String name) {
        return warehouseInfoMapper.WarehouseInfoDtoPageCount(type,(curPage-1)*limit,limit,"%"+name+"%");
    }

    @Override
    public List<DrugInfo> getSaleDrugList() {
        return warehouseInfoMapper.getSaleDrugList();
    }

    @Override
    public void addRetNum(int wid, int num) {
        WarehouseInfo warehouseInfo = this.getById(wid);
        UpdateWrapper<WarehouseInfo> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("wid",wid).set("ret_num",warehouseInfo.getRetNum()+num);
        this.update(updateWrapper);
    }

    @Override
    public void decSaleNum(int wid, int num) {
        WarehouseInfo warehouseInfo = this.getById(wid);
        UpdateWrapper<WarehouseInfo> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("wid",wid).set("sell_num",warehouseInfo.getSellNum()-num);
        this.update(updateWrapper);
    }


}
