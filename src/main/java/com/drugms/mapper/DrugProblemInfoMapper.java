package com.drugms.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.drugms.dto.DrugProblemInfoDto;
import com.drugms.entity.DrugProblemInfo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * <p>
 * 药品问题信息 Mapper 接口
 * </p>
 *
 * @author lhy
 * @since 2023-01-10
 */
@Mapper
public interface DrugProblemInfoMapper extends BaseMapper<DrugProblemInfo> {
    List<DrugProblemInfoDto> getDrugProblemInfoDtoPage(int type, int curPage, int limit, String drugName,Integer problemType,Integer hadHandle);
    Integer getDrugProblemInfoDtoPageCount(int type, int curPage, int limit, String drugName,Integer problemType,Integer hadHandle);
}
