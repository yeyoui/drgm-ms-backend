package com.drugms;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.drugms.common.MSGlobalObject;
import com.drugms.dto.WhPrchsInfoDto;
import com.drugms.entity.DailySales;
import com.drugms.mapper.WhPrchsInfoMapper;
import com.drugms.service.DailySalesService;
import com.drugms.service.WarehouseRetInfoService;
import com.drugms.service.WhPrchsInfoService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.TimeUnit;

@SpringBootTest
@Slf4j
class DrugMsApplicationTests {
    @Autowired
    WarehouseRetInfoService warehouseRetInfoService;
    @Autowired
    MSGlobalObject msGlobalObject;
    @Autowired
    WhPrchsInfoMapper whPrchsInfoMapper;

    @Autowired
    WhPrchsInfoService whPrchsInfoService;
    @Autowired
    DailySalesService dailySalesService;

    @Test
    void contextLoads() {
    }
    @Test
    void testPage() {
        List<WhPrchsInfoDto> dtos = whPrchsInfoMapper.getWhPrchsPage(0, 0, 10, "%保%");
        dtos.forEach(System.out::println);
//        dtos.forEach(System.out::println);
//        drugProblemInfoMapper.getDrugProblemInfoDtoPageCount(
//                7, 0, 10, "%保和%",1,true);
    }

    @Test
    void test1() throws InterruptedException {
        BigDecimal bigDecimal = BigDecimal.valueOf(3.13333);
        String s = bigDecimal.toString();
        System.out.println(s);
    }

    @Test
    void testCheck(){
        LambdaQueryWrapper<DailySales> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.orderByDesc(DailySales::getDay);
        Page<DailySales> page = new Page<>(0,1);//获取最新的销售记录
        dailySalesService.page(page, queryWrapper);
        if(page.getRecords().size()==0){
            //空记录,直接插入
            dailySalesService.save(MSGlobalObject.dailySales);
            return;
        }
        LocalDateTime latest=page.getRecords().get(0).getDay();//最新的销售记录
        LocalDateTime now=MSGlobalObject.dailySales.getDay();//实时销售记录
        if(latest.getYear()==now.getYear() && latest.getMonth()==now.getMonth() && latest.getDayOfMonth()==now.getDayOfMonth()){
            //更新当天数据
            MSGlobalObject.dailySales.setSaleid(page.getRecords().get(0).getSaleid());//设置saleID
            dailySalesService.updateById(MSGlobalObject.dailySales);
            log.info("更新当天数据");
        }else {
            //新的一天数据
            dailySalesService.save(MSGlobalObject.dailySales);
            log.info("新的一天数据");
        }
        log.info("更新一次销售记录");
    }
}
