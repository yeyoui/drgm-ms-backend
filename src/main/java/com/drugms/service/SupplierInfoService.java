package com.drugms.service;

import com.drugms.entity.SupplierInfo;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 供应商信息 服务类
 * </p>
 *
 * @author lhy
 * @since 2023-01-05
 */
public interface SupplierInfoService extends IService<SupplierInfo> {
    Boolean addSplNoExists(SupplierInfo supplierInfo);
}
