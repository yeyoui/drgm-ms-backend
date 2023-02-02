package com.drugms.listener;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.drugms.common.MSGlobalObject;
import com.drugms.entity.DailySales;
import com.drugms.service.DailySalesService;
import com.drugms.service.WhPrchsInfoService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.concurrent.BasicThreadFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 监听spring boot容器启动
 */
@Component
@Slf4j
public class ContextRefreshedListener implements ApplicationListener<ContextRefreshedEvent> {

    @Autowired
    WhPrchsInfoService whPrchsInfoService;
    @Autowired
    DailySalesService dailySalesService;
    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        //获取当日销售情况
        getDailySales();

        //手动创建定时任务线程池
        ScheduledThreadPoolExecutor service = new ScheduledThreadPoolExecutor(4,
                new BasicThreadFactory.Builder().namingPattern("checkExpire").daemon(true).build(),
                new ThreadPoolExecutor.AbortPolicy());

        //一小时检查一次药品过期情况
        service.scheduleAtFixedRate(()->{
            whPrchsInfoService.checkAndUpdExpiredDrug();
        },0,1, TimeUnit.HOURS);

        //1分钟更新一次销售记录
        service.scheduleAtFixedRate(()->{
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
            MSGlobalObject.dailySales.setDay(LocalDateTime.now());//更新时间
            LocalDateTime now=MSGlobalObject.dailySales.getDay();//实时销售记录
            if(latest.getYear()==now.getYear() && latest.getMonth()==now.getMonth() && latest.getDayOfMonth()==now.getDayOfMonth()){
                //更新当天数据
                MSGlobalObject.dailySales.setSaleid(page.getRecords().get(0).getSaleid());//设置saleID
                dailySalesService.updateById(MSGlobalObject.dailySales);
//                log.info("更新当天数据");
            }else {
                //新的一天数据
                dailySalesService.save(MSGlobalObject.dailySales);
//                log.info("新的一天数据");
            }
//            log.info("更新一次销售记录");
        },0,1, TimeUnit.MINUTES);
    }

    private void getDailySales(){
        LambdaQueryWrapper<DailySales> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.orderByDesc(DailySales::getDay);
        Page<DailySales> page = new Page<>(0,1);//获取最新的销售记录
        dailySalesService.page(page, queryWrapper);
        if(page.getRecords().size()==0){
            //空记录,无需处理
            return;
        }
        LocalDateTime latest=page.getRecords().get(0).getDay();//最新的销售记录
        LocalDateTime now=MSGlobalObject.dailySales.getDay();//实时销售记录
        if(latest.getYear()==now.getYear() && latest.getMonth()==now.getMonth() && latest.getDayOfMonth()==now.getDayOfMonth()){
            //恢复当天数据
            MSGlobalObject.dailySales=page.getRecords().get(0);
        }
    }
}
