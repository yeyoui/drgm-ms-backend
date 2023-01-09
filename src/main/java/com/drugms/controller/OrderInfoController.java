package com.drugms.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.drugms.common.R;
import com.drugms.dto.OrderInfoDto;
import com.drugms.dto.WarehouseInfoDto;
import com.drugms.entity.DrugInfo;
import com.drugms.entity.OrderInfo;
import com.drugms.entity.WarehouseInfo;
import com.drugms.service.DrugInfoService;
import com.drugms.service.OrderInfoService;
import com.drugms.service.WarehouseInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 订单信息 前端控制器
 * </p>
 *
 * @author lhy
 * @since 2023-01-05
 */
@RestController
@RequestMapping("/order")
@Slf4j
public class OrderInfoController {

    @Autowired
    private OrderInfoService orderInfoService;
    @Autowired
    private WarehouseInfoService warehouseInfoService;
    /**
     * 订单列表（分页）
     * @param type type=0为通过药品名查询  type=1为通过用户名查询
     */
    @GetMapping("/page")
    public R<Page<OrderInfoDto>> page(@RequestParam(required = false,defaultValue = "-1") Integer type, Integer curPage, Integer limit, String name){
        if(!StringUtils.isNotEmpty(name)) name=null;//输入的name为空字符
        Page<OrderInfoDto> dtoPage = new Page<>();
        dtoPage.setTotal(orderInfoService.getOrderDtoPageCount(type,(curPage-1)*limit,limit,name));
        dtoPage.setRecords(orderInfoService.getOrderDtoPage(type,(curPage-1)*limit,limit,name));
        return R.success(dtoPage);
    }

    /**
     * 新增订单(通过管理系统)
     */
    @PostMapping("/addDrugThroughAdmin")
    public R<String> addDrugThroughAdmin(@RequestBody OrderInfoDto orderInfoDto){
        orderInfoDto.setCreateTime(LocalDateTime.now());
        orderInfoDto.setState(0);//默认正常状态
        //设置仓库信息
        LambdaQueryWrapper<WarehouseInfo> wIQW = new LambdaQueryWrapper<>();
        wIQW.eq(WarehouseInfo::getDid,orderInfoDto.getDid());
        WarehouseInfo warehouseInfo = warehouseInfoService.getOne(wIQW);
        orderInfoDto.setWid(warehouseInfo.getWid());
        //判断是否要更新仓库信息
        if(orderInfoDto.getUpdWarehouse()){
            if(warehouseInfo.getStock()<orderInfoDto.getPrchsNum()){
                //库存不足，操作失败
                return R.error("库存不足，当前库存为:"+warehouseInfo.getStock());
            }
            //更新
            UpdateWrapper<WarehouseInfo> updateWrapper = new UpdateWrapper<>();
            updateWrapper.eq("wid",orderInfoDto.getWid()).set("stock",warehouseInfo.getStock()-orderInfoDto.getPrchsNum());
            warehouseInfoService.update(updateWrapper);
        }
        orderInfoService.save(orderInfoDto);
        return R.success("操作成功");
    }

    /**
     * 修改订单信息
     */
    @PostMapping("/editOrder")
    public R<String> editWarehouse(@RequestBody OrderInfoDto orderInfoDto){
        //判断是否要更新仓库信息
        if( orderInfoDto.getUpdWarehouse()){
            //查询仓库信息
            LambdaQueryWrapper<WarehouseInfo> wIQW = new LambdaQueryWrapper<>();
            wIQW.eq(WarehouseInfo::getWid,orderInfoDto.getWid());
            WarehouseInfo warehouseInfo = warehouseInfoService.getOne(wIQW);
            //查询修改前的订单信息
            QueryWrapper<OrderInfo> oIQW=new QueryWrapper<>();
            oIQW.select("prchs_num").eq("oid",orderInfoDto.getOid());
            OrderInfo preOrderInfo = orderInfoService.getOne(oIQW);
            Integer diff=orderInfoDto.getPrchsNum()-preOrderInfo.getPrchsNum();//差值
            if(warehouseInfo.getStock() < diff){
                //库存不足，操作失败
                return R.error("库存不足，当前库存为:"+warehouseInfo.getStock());
            }
            //更新
            UpdateWrapper<WarehouseInfo> updateWrapper = new UpdateWrapper<>();
            updateWrapper.eq("wid",orderInfoDto.getWid()).set("stock",warehouseInfo.getStock()-diff);
            warehouseInfoService.update(updateWrapper);
        }
        orderInfoService.updateById(orderInfoDto);
        return R.success("修改成功");
    }


    /**
     * 删除订单信息
     */
    @PostMapping ("/delOrder")
    public R<String> delWarehouse(@RequestParam Integer oid){
        orderInfoService.removeById(oid);
        return  R.success("删除成功");
    }

}
