package com.drugms.mapper;

import com.drugms.dto.WhPrchsInfoDto;
import com.drugms.entity.WhPrchsInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * <p>
 * 进货信息 Mapper 接口
 * </p>
 *
 * @author lhy
 * @since 2023-01-05
 */
@Mapper
public interface WhPrchsInfoMapper extends BaseMapper<WhPrchsInfo> {
    /**
     * @param type 0根据药品名模糊查询  1根据厂商名模糊查询 2通过进货ID查询
     */
    List<WhPrchsInfoDto> getWhPrchsPage(int type, int curPage, int limit, String name);
    Integer getWhPrchsPageCount(int type, int curPage, int limit, String name);

}
