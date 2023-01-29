package com.drugms.service.impl;

import com.drugms.entity.SupplyInfo;
import com.drugms.entity.WarehouseInfo;
import com.drugms.mapper.SupplyInfoMapper;
import com.drugms.service.DrugProblemInfoService;
import com.drugms.service.SupplyInfoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.drugms.service.WarehouseInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 供应信息 服务实现类
 * </p>
 *
 * @author lhy
 * @since 2023-01-05
 */
@Service
public class SupplyInfoServiceImpl extends ServiceImpl<SupplyInfoMapper, SupplyInfo> implements SupplyInfoService {
    @Autowired
    private WarehouseInfoService warehouseInfoService;
    @Autowired
    DrugProblemInfoService drugProblemInfoService;

    @Override
    public void checkAndUpdExpiredDrug() {


    }
}
