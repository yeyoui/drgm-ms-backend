package com.drugms.controller;


import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.drugms.common.R;
import com.drugms.dto.WarehouseRetInfoDto;
import com.drugms.dto.WhPrchsInfoDto;
import com.drugms.entity.WhPrchsInfo;
import com.drugms.service.WhPrchsInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 进货信息 前端控制器
 * </p>
 *
 * @author lhy
 * @since 2023-01-05
 */
@RestController
@RequestMapping("/whPrchs")
@Slf4j
public class WhPrchsInfoController {
    @Autowired
    WhPrchsInfoService whPrchsInfoService;
    /**
     * 进货
     */
    @PostMapping("/addWhPrchs")
    public R<String> addWhPrchs(@RequestBody  WhPrchsInfo whPrchsInfo){
        whPrchsInfoService.addWhPrchs(whPrchsInfo);
        return R.success("更新成功");
    }

    /**
     * 进货列表
     */
    @GetMapping("/page")
    public R<Page<WhPrchsInfoDto>> page(@RequestParam(required = false,defaultValue = "-1") Integer type, Integer curPage, Integer limit, String name){
        if(!StringUtils.isNotEmpty(name)) type=-1;
        List<WhPrchsInfoDto> whPrchsInfoDtoList = whPrchsInfoService.getWhPrchsPage(type, curPage, limit, name);
        Integer total = whPrchsInfoService.getWhPrchsPageCount(type, curPage, limit, name);
        Page<WhPrchsInfoDto> page = new Page<>(curPage, limit);
        page.setRecords(whPrchsInfoDtoList);
        page.setTotal(total);
        return R.success(page);
    }

}
