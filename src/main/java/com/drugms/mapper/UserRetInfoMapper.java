package com.drugms.mapper;

import com.drugms.dto.UserRetInfoDto;
import com.drugms.entity.UserRetInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * <p>
 * 用户退货信息 Mapper 接口
 * </p>
 *
 * @author lhy
 * @since 2023-01-05
 */
@Mapper
public interface UserRetInfoMapper extends BaseMapper<UserRetInfo> {
    /**
     * @param searchType 0表示通关药品名查询   1表示通过订单ID查询
     */
    List<UserRetInfoDto> getUserRetInfoDtoPage(int type, int curPage, int limit, Integer searchType, String name, Integer problemType);
    Integer getUserRetInfoDtoPageCount(int type, int curPage, int limit, Integer searchType, String name, Integer problemType);
}
