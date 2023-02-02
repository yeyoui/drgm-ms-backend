package com.drugms.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.drugms.common.MSGlobalObject;
import com.drugms.common.R;
import com.drugms.entity.DailySales;
import com.drugms.service.DailySalesService;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import java.io.Serializable;
import java.text.DateFormat;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.LinkedList;

/**
 * <p>
 * 每日销售信息 前端控制器
 * </p>
 *
 * @author lhy
 * @since 2023-02-01
 */
@RestController
@RequestMapping("/dailySales")
@Slf4j
public class DailySalesController {

    @Autowired
    DailySalesService dailySalesService;

    @GetMapping("/todaySales")
    public R<LinkedList<HomeSalesDto>> todaySales(){
        LinkedList<HomeSalesDto> list = new LinkedList<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd  HH:mm:ss");

        list.add(new HomeSalesDto("更新时间",MSGlobalObject.dailySales.getDay().format(formatter)
            ,"date","#2ec7c9"));
        list.add(new HomeSalesDto("退货数",MSGlobalObject.dailySales.getRetNum().toString()
                ,"sold-out","#ffbc81"));
        list.add(new HomeSalesDto("销售数",MSGlobalObject.dailySales.getSalesNum().toString()
                ,"shopping-bag-1","#51acee"));
        list.add(new HomeSalesDto("订单数",MSGlobalObject.dailySales.getOrderNum().toString()
                ,"shopping-cart-1","#2ec7c9"));
        list.add(new HomeSalesDto("销售金额",MSGlobalObject.dailySales.getSalesAmount().toString()
                ,"coin","#ffbc81"));
        list.add(new HomeSalesDto("收入",MSGlobalObject.dailySales.getIncome().toString()
                ,"money","#51acee"));

        return R.success(list);
    }
    @GetMapping("/recentSales")
    public R<RecentSalesDto>recentSales(){
        Page<DailySales> page = new Page<>(0,7);//七天内
//        LambdaQueryWrapper<DailySales> queryWrapper = new LambdaQueryWrapper<>();
//        queryWrapper.orderByDesc(DailySales::getDay);
        dailySalesService.page(page);
        //封装数据
        RecentSalesDto dto = new RecentSalesDto();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yy-MM-dd");
        page.getRecords().forEach((record)->{
            dto.day.add(record.getDay().format(formatter));//时间列表
        });
        LinkedList<LinkedList<String>> recentSales = dailySalesService.getRecentSales();
        dto.recentSales.put("销售金额",recentSales.get(0));
        dto.recentSales.put("销售数",recentSales.get(1));
        dto.recentSales.put("订单数",recentSales.get(2));
        dto.recentSales.put("退货数",recentSales.get(3));
        dto.recentSales.put("收入",recentSales.get(4));
        return R.success(dto);
    }


    @Data
    @EqualsAndHashCode(callSuper = false)
    @Accessors(chain = true)
    private static class HomeSalesDto implements Serializable {
        private  String name;
        private  String value;
        private  String icon;
        private  String color;

        public HomeSalesDto() {
        }

        public HomeSalesDto(String name, String value, String icon, String color) {
            this.name = name;
            this.value = value;
            this.icon = icon;
            this.color = color;
        }

        @Override
        public String toString() {
            return "HomeSalesDto{" +
                    "name='" + name + '\'' +
                    ", value='" + value + '\'' +
                    ", icon='" + icon + '\'' +
                    ", color='" + color + '\'' +
                    '}';
        }
    }

    @Data
    @EqualsAndHashCode(callSuper = false)
    @Accessors(chain = true)
    private static class RecentSalesDto implements Serializable{
        private LinkedList<String> day=new LinkedList<>();
        private HashMap<String,LinkedList<String>> recentSales=new HashMap<>();
    }
}
