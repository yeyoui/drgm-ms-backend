package com.drugms.controller;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.drugms.common.R;
import com.drugms.dto.WarehouseInfoDto;
import com.drugms.entity.DrugProblemInfo;
import com.drugms.entity.WarehouseInfo;
import com.drugms.service.DrugProblemInfoService;
import com.drugms.service.WarehouseInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 药品仓库信息 前端控制器
 * </p>
 *
 * @author lhy
 * @since 2023-01-05
 */
@RestController
@RequestMapping("/warehouse")
@Slf4j
public class WarehouseInfoController {
    @Autowired
    private WarehouseInfoService warehouseInfoService;
    @Autowired
    private DrugProblemInfoService drugProblemInfoService;

    /**
     * 分页查询仓库信息
     * @param type=0 表示通过药品名查询  type=1 表示通过厂商名查询
     */
    @GetMapping("/page")
    public R<Page<WarehouseInfoDto>> page(@RequestParam(required = false,defaultValue = "-1") Integer type, Integer curPage, Integer limit, String sname){
        if(!StringUtils.isNotEmpty(sname)) sname=null;//输入的sname为空字符
        Page<WarehouseInfoDto> dtoPage = new Page<>();
        //获取总页数
        dtoPage.setTotal(warehouseInfoService.getWhDtoPageCount(type,(curPage-1)*limit,limit,sname));
        //交给Service层处理查询数据
        dtoPage.setRecords(warehouseInfoService.getWhDtoPage(type, (curPage-1)*limit, limit, sname));
        return R.success(dtoPage);
    }

    /**
     * 修改仓库信息
     */
    @PostMapping("/editWarehouse")
    public R<String> editWarehouse(@RequestBody WarehouseInfo warehouseInfo){
        warehouseInfoService.updateById(warehouseInfo);
        return R.success("修改成功");
    }


    /**
     * 删除仓库信息
     */
    @PostMapping ("/delWarehouse")
    public R<String> delWarehouse(@RequestParam Integer wid){
        warehouseInfoService.removeById(wid);
        return  R.success("删除成功");
    }

    /**
     * 增加仓库信息(在WhPrchsInfoController中实现)
     */


    /**
     * 获取药品的库存
     */
    @GetMapping("/getStockByDrugId")
    public R<Integer> getStockByDrugId(Integer did){
        return R.success(warehouseInfoService.getById(did).getStock());
    }

    /**
     *
     */
    @PostMapping("/submitDrugProblem")
    public R<String> submitDrugProblem(@RequestBody  DrugProblemInfo drugProblemInfo){
        drugProblemInfoService.submitDrugProblem(drugProblemInfo);
        return R.success("提交成功");
    }
}
