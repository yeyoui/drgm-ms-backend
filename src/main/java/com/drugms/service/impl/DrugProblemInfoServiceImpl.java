package com.drugms.service.impl;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.drugms.dto.DrugProblemInfoDto;
import com.drugms.dto.OrderInfoDto;
import com.drugms.entity.DrugProblemInfo;
import com.drugms.mapper.DrugProblemInfoMapper;
import com.drugms.service.DrugProblemInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * <p>
 * 药品问题信息 服务实现类
 * </p>
 *
 * @author lhy
 * @since 2023-01-10
 */
@Service
public class DrugProblemInfoServiceImpl extends ServiceImpl<DrugProblemInfoMapper, DrugProblemInfo> implements DrugProblemInfoService {
    @Autowired
    DrugProblemInfoMapper drugProblemInfoMapper;

    @Override
    public List<DrugProblemInfoDto> getDrugProblemInfoDtoPage(int type, int curPage, int limit, String name) {
        String drugName=null;
        Integer problemType=null;
        Integer handleType=null;
        String[] strings = name.split(",");
        if(!" ".equals(strings[0])) drugName=strings[0];
        if(!" ".equals(strings[1])) problemType=Integer.parseInt(strings[1]);
        if(!" ".equals(strings[2])) handleType=Integer.parseInt(strings[2]);

        return drugProblemInfoMapper.getDrugProblemInfoDtoPage(type, curPage, limit, drugName, problemType, handleType);
    }

    @Override
    public Integer getDrugProblemInfoDtoPageCount(int type, int curPage, int limit, String name) {
        return null;
    }
}
