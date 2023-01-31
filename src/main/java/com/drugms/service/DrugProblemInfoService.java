package com.drugms.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.drugms.dto.DrugInfoDto;
import com.drugms.dto.DrugProblemInfoDto;
import com.drugms.entity.DrugInfo;
import com.drugms.entity.DrugProblemInfo;

import java.util.List;

/**
 * <p>
 * 药品问题信息 服务类
 * </p>
 *
 * @author lhy
 * @since 2023-01-10
 */
public interface DrugProblemInfoService extends IService<DrugProblemInfo> {
    List<DrugProblemInfoDto> getDrugProblemInfoDtoPage(int type, int curPage, int limit, String name);
    Integer getDrugProblemInfoDtoPageCount(int type, int curPage, int limit, String name);
    /**
     *提交问题药品信息
     * @param drugProblemInfo 需要提供Wid、ProblemType、PrchsNum
     */
    void submitDrugProblem(DrugProblemInfo drugProblemInfo,Boolean inSale);

    void updateInfoAfterHandler(Integer dpid);
}
