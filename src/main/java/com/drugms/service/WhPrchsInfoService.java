package com.drugms.service;

import com.drugms.entity.WhPrchsInfo;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 进货信息 服务类
 * </p>
 *
 * @author lhy
 * @since 2023-01-05
 */
public interface WhPrchsInfoService extends IService<WhPrchsInfo> {
    /**
     * 新增进货信息
     */
    Boolean addWhPrchs(WhPrchsInfo whPrchsInfo);

}
