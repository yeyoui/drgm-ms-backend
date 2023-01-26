package com.drugms.service.impl;

import com.drugms.dto.DrugProblemInfoDto;
import com.drugms.dto.WarehouseInfoDto;
import com.drugms.dto.WarehouseRetInfoDto;
import com.drugms.entity.WarehouseRetInfo;
import com.drugms.mapper.WarehouseRetInfoMapper;
import com.drugms.service.WarehouseRetInfoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 仓库退货信息 服务实现类
 * </p>
 *
 * @author lhy
 * @since 2023-01-05
 */
@Service
public class WarehouseRetInfoServiceImpl extends ServiceImpl<WarehouseRetInfoMapper, WarehouseRetInfo> implements WarehouseRetInfoService {
    @Autowired
    private WarehouseRetInfoMapper warehouseRetInfoMapper;

    @Override
    public List<WarehouseRetInfoDto> getWarehouseRetInfoDtoPage(int type, int curPage, int limit, String name) {
        String drugName=null;
        Integer problemType=null;
        String[] strings = name.split(",");
        if(!" ".equals(strings[0])){
            drugName="%"+strings[0]+"%";
        }
        if(!" ".equals(strings[1])) problemType=Integer.parseInt(strings[1]);

        return warehouseRetInfoMapper.warehouseRetInfoDtoPage(type, (curPage-1)*limit, limit, drugName, problemType);
    }

    @Override
    public Integer getWarehouseRetInfoDtoPageCount(int type, int curPage, int limit, String name) {
        String drugName=null;
        Integer problemType=null;
        String[] strings = name.split(",");
        if(!" ".equals(strings[0])) drugName=strings[0];
        if(!" ".equals(strings[1])) problemType=Integer.parseInt(strings[1]);

        return warehouseRetInfoMapper.warehouseRetInfoDtoPageCount(type, (curPage-1)*limit, limit, drugName, problemType);
    }
}
