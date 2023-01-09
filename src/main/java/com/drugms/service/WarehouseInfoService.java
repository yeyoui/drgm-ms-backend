package com.drugms.service;

import com.drugms.dto.WarehouseInfoDto;
import com.drugms.entity.WarehouseInfo;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 药品仓库信息 服务类
 * </p>
 *
 * @author lhy
 * @since 2023-01-05
 */
public interface WarehouseInfoService extends IService<WarehouseInfo> {
    List<WarehouseInfoDto> getWhDtoPage(int type,int curPage,int limit,String name);
    Integer getWhDtoPageCount(int type,int curPage,int limit,String name);

}
