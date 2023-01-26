package com.drugms.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.drugms.common.CustomException;
import com.drugms.common.R;
import com.drugms.dto.UserRetInfoDto;
import com.drugms.entity.DrugProblemInfo;
import com.drugms.entity.UserRetInfo;
import com.drugms.service.OrderInfoService;
import com.drugms.service.UserRetInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

/**
 * <p>
 * 用户退货信息 前端控制器
 * </p>
 *
 * @author lhy
 * @since 2023-01-05
 */
@RestController
@RequestMapping("/userRetInfo")
@Slf4j
public class UserRetInfoController {
    @Autowired
    private UserRetInfoService userRetInfoService;
    @Autowired
    private OrderInfoService orderInfoService;

    /**
     * 分页查询用户退货信息
     * @param type
     * type=a+b+..
     * a:按名字或订单ID查询：1  b:按照类型查询：2
     * @param name
     * name="value1"+","+"value2"+... 按照,分割
     */
    @GetMapping("/page")
    public R<Page<UserRetInfoDto>> page(@RequestParam(required = false,defaultValue = "-1") Integer type, Integer curPage, Integer limit, String name){
        Page<UserRetInfoDto> userRetInfoDtoPage = new Page<>();
        userRetInfoDtoPage.setRecords(userRetInfoService.getUserRetInfoDtoPage(type,curPage,limit,name));
        userRetInfoDtoPage.setTotal(userRetInfoService.getUserRetInfoDtoPageCount(type,curPage,limit,name));
        return R.success(userRetInfoDtoPage);
    }

    /**
     * 同意退货
     */
    @PostMapping("/agreeUserRet")
    public R<String> delWarehouse(@RequestParam Integer oid){
        //修改订单状态为退货完成
        orderInfoService.updOrderStatus(oid,2);
        //提交到问题药品
        userRetInfoService.agreeUserRet(oid);
        //删除用户退货申请数据
        userRetInfoService.removeById(oid);
        return  R.success("同意退货");
    }

    /**
     * 拒绝退货
     */
    @PostMapping("/refuseUserRet")
    public R<String> refuseUserRet(@RequestParam Integer oid){
        //修改订单状态为正常
        orderInfoService.updOrderStatus(oid,0);
        //删除用户退货申请数据
        userRetInfoService.removeById(oid);
        return  R.success("拒绝退货");
    }

    /**
     * 提交退货订单
     */
    @PostMapping("/submitProblemOrder")
    public R<String> submitProblemOrder(@RequestBody UserRetInfo userRetInfo){
        userRetInfo.setRetTime(LocalDateTime.now());
        userRetInfoService.save(userRetInfo);
        orderInfoService.updOrderStatus(userRetInfo.getOid(),1);
        return R.success("提交成功");
    }
}
