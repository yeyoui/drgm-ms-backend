package com.drugms.controller;


import com.drugms.common.R;
import com.drugms.entity.WhPrchsInfo;
import com.drugms.service.WhPrchsInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

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

}
