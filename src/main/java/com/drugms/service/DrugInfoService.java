package com.drugms.service;

import com.drugms.dto.DrugInfoDto;
import com.drugms.entity.DrugInfo;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 药品信息 服务类
 * </p>
 *
 * @author lhy
 * @since 2023-01-05
 */
public interface DrugInfoService extends IService<DrugInfo> {

    void delDrugAndSupply(Integer did);

    void updDrugAndSupply(DrugInfoDto drugInfoDto);
}
