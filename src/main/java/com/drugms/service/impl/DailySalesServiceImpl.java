package com.drugms.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.drugms.entity.DailySales;
import com.drugms.mapper.DailySalesMapper;
import com.drugms.service.DailySalesService;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * <p>
 * 管理员信息 服务实现类
 * </p>
 *
 * @author lhy
 * @since 2023-01-05
 */
@Service
public class DailySalesServiceImpl extends ServiceImpl<DailySalesMapper, DailySales> implements DailySalesService {

    @Override
    public LinkedList<LinkedList<String>>  getRecentSales() {
        Page<DailySales> page = new Page<>(0, 7);
        LambdaQueryWrapper<DailySales> wrapper = new LambdaQueryWrapper<>();
        wrapper.orderByDesc(DailySales::getDay);
        this.page(page,wrapper);
        List<DailySales> records = page.getRecords();
        LinkedList<LinkedList<String>> AllTypeLinkedLists = new LinkedList<>();
        Collections.reverse(records);
        //销售金额
        LinkedList<String> list0 = new LinkedList<>();
        records.forEach((item)->{
            list0.add(item.getSalesAmount().toString());
        });
        AllTypeLinkedLists.add(list0);

        //销售数
        LinkedList<String> list1 = new LinkedList<>();
        records.forEach((item)->{
            list1.add(item.getSalesNum().toString());
        });
        AllTypeLinkedLists.add(list1);

        //订单数
        LinkedList<String> list2 = new LinkedList<>();
        records.forEach((item)->{
            list2.add(item.getOrderNum().toString());
        });
        AllTypeLinkedLists.add(list2);

        //退货数
        LinkedList<String> list3 = new LinkedList<>();
        records.forEach((item)->{
            list3.add(item.getRetNum().toString());
        });
        AllTypeLinkedLists.add(list3);

        //收入
        LinkedList<String> list4 = new LinkedList<>();
        records.forEach((item)->{
            list4.add(item.getIncome().toString());
        });
        AllTypeLinkedLists.add(list4);

        return AllTypeLinkedLists;
    }
}
