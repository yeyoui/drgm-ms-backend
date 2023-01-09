package com.drugms.service.impl;

import com.drugms.dto.DrugInfoDto;
import com.drugms.entity.DrugInfo;
import com.drugms.entity.SupplyInfo;
import com.drugms.mapper.DrugInfoMapper;
import com.drugms.service.DrugInfoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.drugms.service.SupplyInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 药品信息 服务实现类
 * </p>
 *
 * @author lhy
 * @since 2023-01-05
 */
@Service
public class DrugInfoServiceImpl extends ServiceImpl<DrugInfoMapper, DrugInfo> implements DrugInfoService {

    @Autowired
    SupplyInfoService supplyInfoService;
    @Override
    public void delDrugAndSupply(Integer did) {
        //删除药品信息
        this.removeById(did);
        //删除供应关系
        supplyInfoService.removeById(did);
    }
    @Override
    public void updDrugAndSupply(DrugInfoDto drugInfoDto) {
        //更新药品信息
        this.updateById(drugInfoDto);
        SupplyInfo supplyInfo = new SupplyInfo();
        //更新供应关系
        supplyInfo.setSid(drugInfoDto.getSid());
        supplyInfo.setDid(drugInfoDto.getDid());
        supplyInfoService.updateById(supplyInfo);
    }
}
