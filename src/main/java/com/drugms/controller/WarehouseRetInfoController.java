package com.drugms.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.drugms.common.R;
import com.drugms.dto.WarehouseRetInfoDto;
import com.drugms.entity.WarehouseRetInfo;
import com.drugms.service.WarehouseRetInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * 仓库退货信息 前端控制器
 * </p>
 *
 * @author lhy
 * @since 2023-01-05
 */
@RestController
@RequestMapping("/warehouseRet")
public class WarehouseRetInfoController {
    @Autowired
    private WarehouseRetInfoService warehouseRetInfoService;
    @GetMapping("/page")
    public R<Page<WarehouseRetInfoDto>> page(@RequestParam(required = false,defaultValue = "-1") Integer type, Integer curPage, Integer limit, String name){
        Page<WarehouseRetInfoDto> page = new Page<>(curPage,limit);
        List<WarehouseRetInfoDto> warehouseRetInfoDtos = warehouseRetInfoService.getWarehouseRetInfoDtoPage(type, curPage, limit, name);
        page.setRecords(warehouseRetInfoDtos);
        page.setTotal(warehouseRetInfoService.getWarehouseRetInfoDtoPageCount(type, curPage, limit, name));
        return R.success(page);
    }
}
