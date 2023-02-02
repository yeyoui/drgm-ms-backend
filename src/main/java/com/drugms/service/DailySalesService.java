package com.drugms.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.drugms.entity.DailySales;

import java.util.LinkedList;

/**
 * <p>
 * 销售信息 服务类
 * </p>
 *
 * @author lhy
 * @since 2023-02-01
 */
public interface DailySalesService extends IService<DailySales> {
    /**
     * 获取各个类型数据7天的集合
     * @return index:0:销售金额  1:销售数  2:订单数  3:退货数  4:收入
     */
    LinkedList<LinkedList<String>> getRecentSales();
}
