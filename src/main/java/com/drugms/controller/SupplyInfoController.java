package com.drugms.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.drugms.common.R;
import com.drugms.entity.SupplierInfo;
import com.drugms.entity.SupplyInfo;
import com.drugms.service.SupplierInfoService;
import com.drugms.service.SupplyInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 供应信息 前端控制器
 * </p>
 *
 * @author lhy
 * @since 2023-01-05
 */
@RestController
@RequestMapping("/supply")
public class SupplyInfoController {
   @Autowired
   private SupplierInfoService supplierInfoService;
    @Autowired
    private SupplyInfoService supplyInfoService;
    /**
     * 通过药品ID获取厂商名
     */
    @GetMapping("/getNameByDrugId")
    public R<String> getNameByDrugId(Integer did){
        SupplyInfo supplierInfo = supplyInfoService.getById(did);
        LambdaQueryWrapper<SupplierInfo> supplierInfoQW = new LambdaQueryWrapper<>();
        supplierInfoQW.select(SupplierInfo::getSplName).eq(SupplierInfo::getSid,supplierInfo.getSid());
        SupplierInfo one = supplierInfoService.getOne(supplierInfoQW);
        return R.success(one.getSplName());
    }

}
