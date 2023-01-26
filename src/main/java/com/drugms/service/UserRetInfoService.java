package com.drugms.service;

import com.drugms.dto.UserRetInfoDto;
import com.drugms.dto.WarehouseRetInfoDto;
import com.drugms.entity.UserRetInfo;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 用户退货信息 服务类
 * </p>
 *
 * @author lhy
 * @since 2023-01-05
 */
public interface UserRetInfoService extends IService<UserRetInfo> {
    List<UserRetInfoDto> getUserRetInfoDtoPage(int type, int curPage, int limit, String name);
    Integer getUserRetInfoDtoPageCount(int type, int curPage, int limit, String name);

    void agreeUserRet(int oid);

}
