package com.drugms.service;

import com.drugms.dto.WhPrchsInfoDto;
import com.drugms.entity.WhPrchsInfo;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

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

    /**
     * 检查药品是否过期
     */
    void checkAndUpdExpiredDrug();

    /**
     * 查询匹配的进货信息列表
     */
    List<WhPrchsInfoDto> getWhPrchsPage(int type,int curPage,int limit,String name);
    Integer getWhPrchsPageCount(int type,int curPage,int limit,String name);

    /**
     * 增加进货信息药品的剩余量
     */
    void addRemainByWID(Integer wid,int num);

    /**
     * 减少进货信息药品的剩余量
     */
    void decRemainByWID(Integer wid,int num);
}
