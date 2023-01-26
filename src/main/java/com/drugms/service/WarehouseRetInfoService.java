package com.drugms.service;

import com.drugms.dto.WarehouseRetInfoDto;
import com.drugms.entity.WarehouseRetInfo;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 仓库退货信息 服务类
 * </p>
 *
 * @author lhy
 * @since 2023-01-05
 */
public interface WarehouseRetInfoService extends IService<WarehouseRetInfo> {
    List<WarehouseRetInfoDto> getWarehouseRetInfoDtoPage(int type, int curPage, int limit, String name);
    Integer getWarehouseRetInfoDtoPageCount(int type, int curPage, int limit, String name);

}
