package com.drugms.mapper;

import com.drugms.dto.WarehouseInfoDto;
import com.drugms.entity.DrugInfo;
import com.drugms.entity.WarehouseInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * <p>
 * 药品仓库信息 Mapper 接口
 * </p>
 *
 * @author lhy
 * @since 2023-01-05
 */
@Mapper
public interface WarehouseInfoMapper extends BaseMapper<WarehouseInfo> {
    /**
     * 查询仓库信息
     * @param type=0 表示通过药品名查询  type=1 表示通过厂商名查询
     */
    Integer WarehouseInfoDtoPageCount(int type,int curPage,int limit,String name);
    List<WarehouseInfoDto>  WarehouseInfoDtoPage(int type,int curPage,int limit,String name);

    List<DrugInfo> getSaleDrugList();
}
