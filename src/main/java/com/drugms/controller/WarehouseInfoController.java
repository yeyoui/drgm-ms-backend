package com.drugms.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.drugms.common.R;
import com.drugms.dto.WarehouseInfoDto;
import com.drugms.entity.DrugInfo;
import com.drugms.entity.DrugProblemInfo;
import com.drugms.entity.WarehouseInfo;
import com.drugms.service.DrugProblemInfoService;
import com.drugms.service.WarehouseInfoService;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.Serializable;
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
        if(!StringUtils.isNotEmpty(sname)){
            type=-1;//输入为空，不进行条件过滤
        }
        Page<WarehouseInfoDto> dtoPage = new Page<>();
        //获取总页数
        dtoPage.setTotal(warehouseInfoService.getWhDtoPageCount(type,curPage,limit,sname));
        //交给Service层处理查询数据
        dtoPage.setRecords(warehouseInfoService.getWhDtoPage(type, curPage, limit, sname));
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
    @GetMapping("/getStockAndStateByDrugId")
    public R<WIStokeAndState> getStockByDrugId(Integer did){
        LambdaQueryWrapper<WarehouseInfo> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(WarehouseInfo::getDid,did);
        WarehouseInfo warehouseInfo = warehouseInfoService.getOne(queryWrapper);
        return R.success(WIStokeAndState.getObject(warehouseInfo.getIsSale()?"在售":"停售",warehouseInfo.getStock()));
    }

    /**
     * 提交问题药品
     */
    @PostMapping("/submitDrugProblem")
    public R<String> submitDrugProblem(@RequestBody  DrugProblemInfo drugProblemInfo){
        drugProblemInfoService.submitDrugProblem(drugProblemInfo);
        return R.success("提交成功");
    }

    /**
     * 获取在售药品列表
     */
    @GetMapping("/getSaleDrugList")
    public R<List<DrugInfo>> getSaleDrugList(){
        List<DrugInfo> drugList = warehouseInfoService.getSaleDrugList();
        return R.success(drugList);
    }

    @Data
    private static class WIStokeAndState implements Serializable {
        private String isSale;
        private Integer stoke;


        private static WIStokeAndState getObject(String state,Integer stoke){
            WIStokeAndState wiStokeAndState = new WIStokeAndState();
            wiStokeAndState.isSale=state;
            wiStokeAndState.stoke=stoke;
            return wiStokeAndState;
        }
    }
}
