package com.drugms.service.impl;

import com.drugms.dto.OrderInfoDto;
import com.drugms.entity.OrderInfo;
import com.drugms.mapper.OrderInfoMapper;
import com.drugms.service.OrderInfoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 订单信息 服务实现类
 * </p>
 *
 * @author lhy
 * @since 2023-01-05
 */
@Service
public class OrderInfoServiceImpl extends ServiceImpl<OrderInfoMapper, OrderInfo> implements OrderInfoService {
    @Autowired
    private OrderInfoMapper orderInfoMapper;

    @Override
    public List<OrderInfoDto> getOrderDtoPage(int type, int curPage, int limit, String name) {
        return orderInfoMapper.getOrderDtoPage(type,curPage,limit,name);
    }

    @Override
    public Integer getOrderDtoPageCount(int type, int curPage, int limit, String name) {
        return orderInfoMapper.getOrderDtoPageCount(type,curPage,limit,name);
    }
}
