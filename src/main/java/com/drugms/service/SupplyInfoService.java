package com.drugms.service;

import com.drugms.entity.SupplyInfo;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 供应信息 服务类
 * </p>
 *
 * @author lhy
 * @since 2023-01-05
 */
public interface SupplyInfoService extends IService<SupplyInfo> {
    void checkAndUpdExpiredDrug();
}
