package com.drugms.service;

import com.drugms.dto.DrugInfoDto;
import com.drugms.dto.OrderInfoDto;
import com.drugms.dto.WarehouseInfoDto;
import com.drugms.entity.OrderInfo;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 订单信息 服务类
 * </p>
 *
 * @author lhy
 * @since 2023-01-05
 */
public interface OrderInfoService extends IService<OrderInfo> {
    /**
     * 分页查询订单信息
     * @param type type=0 表示通过药品名查询 type=1 表示通过用户名查询
     */
    List<OrderInfoDto> getOrderDtoPage(int type, int curPage, int limit, String name);
    Integer getOrderDtoPageCount(int type,int curPage,int limit,String name);

}
