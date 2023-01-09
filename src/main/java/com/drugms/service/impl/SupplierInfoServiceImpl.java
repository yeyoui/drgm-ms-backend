package com.drugms.service.impl;

import com.drugms.entity.SupplierInfo;
import com.drugms.mapper.SupplierInfoMapper;
import com.drugms.service.SupplierInfoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 供应商信息 服务实现类
 * </p>
 *
 * @author lhy
 * @since 2023-01-05
 */
@Service
public class SupplierInfoServiceImpl extends ServiceImpl<SupplierInfoMapper, SupplierInfo> implements SupplierInfoService {
    @Autowired
    SupplierInfoMapper supplierInfoMapper;
    /**
     * 厂商名无重复则添加
     */
    public Boolean addSplNoExists(SupplierInfo supplierInfo){
        if(supplierInfoMapper.checkSplNameExists(supplierInfo.getSplName())){
            //当前厂商名已经存在
            return false;
        }else {
            //新增厂商
            this.save(supplierInfo);
            return true;
        }

    }

}
