package com.drugms.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.drugms.common.R;
import com.drugms.dto.DrugProblemInfoDto;
import com.drugms.dto.DrugProblemType;
import com.drugms.dto.OrderInfoDto;
import com.drugms.entity.SupplierInfo;
import com.drugms.entity.SupplyInfo;
import com.drugms.service.DrugProblemInfoService;
import com.drugms.service.SupplierInfoService;
import com.drugms.service.SupplyInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
    private DrugProblemInfoService problemInfoService;


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
//        dtoPage.setTotal(problemInfoService.getDrugProblemInfoDtoPageCount(type,curPage,limit,name));
        dtoPage.setRecords(problemInfoService.getDrugProblemInfoDtoPage(type,curPage,limit,name));

        return R.success(dtoPage);
    }



}
