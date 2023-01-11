package com.drugms.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.drugms.common.R;
import com.drugms.dto.DrugProblemInfoDto;
import com.drugms.dto.DrugProblemType;
import com.drugms.dto.OrderInfoDto;
import com.drugms.entity.DrugProblemInfo;
import com.drugms.entity.SupplierInfo;
import com.drugms.entity.SupplyInfo;
import com.drugms.entity.WarehouseInfo;
import com.drugms.service.DrugProblemInfoService;
import com.drugms.service.SupplierInfoService;
import com.drugms.service.SupplyInfoService;
import com.drugms.service.WarehouseInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 药品问题信息 前端控制器
 * </p>
 *
 * @author lhy
 * @since 2023-01-10
 */
@RestController
@RequestMapping("/drugProblem")
public class DrugProblemInfoController {
    @Autowired
    private DrugProblemInfoService drugProblemInfoService;
    @Autowired
    private WarehouseInfoService warehouseInfoService;


    /**
     * 问题类型列表
     */
    @GetMapping("/typeList")
    public R<List<DrugProblemType>> typeList(){
        List<DrugProblemType> list = Arrays.asList(
                new DrugProblemType(-1,"所有类型"),
                new DrugProblemType(0,"过期"),
                new DrugProblemType(1,"包装破损"),
                new DrugProblemType(2,"药品变质"),
                new DrugProblemType(3,"药品发错"),
                new DrugProblemType(4,"7天无理由"));
        return R.success(list);
    }

    /**
     * 分页查询问题药品信息
     * @param type
     * type=a+b+..
     * a:按名字查询：1  b:按照类型查询：2  c:按照是否处理查询 3
     * example: 按照名字和类型查询：type：1+2=3 [二进制形式 11] 只按照名字查询：type=1 [二进制形式 01]
     * @param name
     * name="value1"+","+"value2"+... 按照,分割
     */
    @GetMapping("/page")
    public R<Page<DrugProblemInfoDto>> page(@RequestParam(required = false,defaultValue = "-1") Integer type, Integer curPage, Integer limit, String name){
        Page<DrugProblemInfoDto> dtoPage = new Page<>();
        //交给Service处理
        dtoPage.setTotal(drugProblemInfoService.getDrugProblemInfoDtoPageCount(type,curPage,limit,name));
        dtoPage.setRecords(drugProblemInfoService.getDrugProblemInfoDtoPage(type,curPage,limit,name));

        return R.success(dtoPage);
    }

    /**
     * 问题药品退回仓库
     */
    @PostMapping("/retToWarehouse")
    public R<String> retToWarehouse(@RequestParam Integer dpid){
        DrugProblemInfo drugProblemInfo = drugProblemInfoService.getById(dpid);
        WarehouseInfo warehouseInfo = warehouseInfoService.getById(drugProblemInfo.getWid());
        //更新仓库信息
        UpdateWrapper<WarehouseInfo> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("wid",drugProblemInfo.getWid()).set("stock",warehouseInfo.getStock()+drugProblemInfo.getDpNum());
        warehouseInfoService.update(updateWrapper);
        //更新药品问题信息
        drugProblemInfoService.updateInfoAfterHandler(dpid);
        return R.success("回仓成功");
    }



}
