package com.drugms.mapper;

import com.drugms.entity.SupplierInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 供应商信息 Mapper 接口
 * </p>
 *
 * @author lhy
 * @since 2023-01-05
 */
@Mapper
public interface SupplierInfoMapper extends BaseMapper<SupplierInfo> {
    Boolean checkSplNameExists(String sName);

}
