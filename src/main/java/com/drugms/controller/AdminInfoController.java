package com.drugms.controller;


import com.drugms.common.CustomException;
import com.drugms.common.R;
import com.drugms.service.WarehouseInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 管理员信息 前端控制器
 * </p>
 *
 * @author lhy
 * @since 2023-01-05
 */
@RestController
@RequestMapping("/admin")
@Slf4j
public class AdminInfoController {
    @Autowired
    private WarehouseInfoService warehouseInfoService;


    @GetMapping("/checkAndUpdExpiredDrug")
    public R<String> checkAndUpdExpiredDrug(){
        throw new CustomException("该功能不支持!");
//        return null;
    }
}
