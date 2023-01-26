package com.drugms.mapper;

import com.drugms.dto.WarehouseRetInfoDto;
import com.drugms.entity.WarehouseRetInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * <p>
 * 仓库退货信息 Mapper 接口
 * </p>
 *
 * @author lhy
 * @since 2023-01-05
 */
@Mapper
public interface WarehouseRetInfoMapper extends BaseMapper<WarehouseRetInfo> {
    List<WarehouseRetInfoDto> warehouseRetInfoDtoPage(int type,int curPage,int limit,String drugName,Integer problemType);
    Integer warehouseRetInfoDtoPageCount(int type,int curPage,int limit,String drugName,Integer problemType);
}
