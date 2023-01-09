package com.drugms.mapper;

import com.drugms.dto.DrugInfoDto;
import com.drugms.dto.OrderInfoDto;
import com.drugms.entity.OrderInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * <p>
 * 订单信息 Mapper 接口
 * </p>
 *
 * @author lhy
 * @since 2023-01-05
 */
@Mapper
public interface OrderInfoMapper extends BaseMapper<OrderInfo> {

    List<OrderInfoDto> getOrderDtoPage(int type, int curPage, int limit, String name);
    Integer getOrderDtoPageCount(int type,int curPage,int limit,String name);

}
