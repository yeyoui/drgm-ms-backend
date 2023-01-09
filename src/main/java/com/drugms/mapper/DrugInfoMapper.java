package com.drugms.mapper;

import com.drugms.entity.DrugInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 药品信息 Mapper 接口
 * </p>
 *
 * @author lhy
 * @since 2023-01-05
 */
@Mapper
public interface DrugInfoMapper extends BaseMapper<DrugInfo> {

}
