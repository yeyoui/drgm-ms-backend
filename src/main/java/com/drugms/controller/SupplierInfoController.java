package com.drugms.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.drugms.common.R;
import com.drugms.dto.DrugInfoDto;
import com.drugms.entity.DrugInfo;
import com.drugms.entity.SupplierInfo;
import com.drugms.entity.SupplyInfo;
import com.drugms.service.SupplierInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 供应商信息 前端控制器
 * </p>
 *
 * @author lhy
 * @since 2023-01-05
 * 实现了Cache
 */
@RestController
@RequestMapping("/supplier")
@Slf4j
public class SupplierInfoController {
    @Autowired
    SupplierInfoService supplierInfoService;

    /**
     * 获取厂商列表
     */
    @GetMapping("/list")
    @Cacheable(cacheNames = "supplierCache",key = "'list'")
    public R<List<SupplierInfo>> list(){
        QueryWrapper<SupplierInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("sid","spl_name");
        return R.success(supplierInfoService.list(queryWrapper));
    }

    /**
     * 分页查询厂商信息
     */
    @GetMapping("/page")
    @Cacheable(cacheNames = "supplierCache",key = "'page'+'_'+#curPage+'_'+#sname")
    public R<Page<SupplierInfo>> page(Integer curPage, Integer limit, String sname){
//        log.info("curPage:{} limit:{} sname:{}",curPage,limit,sname);
        //按照名字查询
        LambdaQueryWrapper<SupplierInfo> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(StringUtils.isNotEmpty(sname),SupplierInfo::getSplName,sname);

        Page<SupplierInfo> page=new Page<>(curPage,limit);
        supplierInfoService.page(page,queryWrapper);
        return R.success(page);
    }

    /**
     * 修改供应商信息
     */
    @PostMapping("/editSupplier")
    @CacheEvict(cacheNames = "supplierCache",allEntries = true)
    public R<String> editSupplier(@RequestBody SupplierInfo supplierInfo){

        supplierInfoService.updateById(supplierInfo);
        return R.success("修改成功");
    }


    /**
     * 删除供应商信息
     */
    @PostMapping ("/delSupplier")
    @CacheEvict(cacheNames = "supplierCache",allEntries = true)
    public R<String> delSupplier(@RequestParam Integer sid){
        supplierInfoService.removeById(sid);
        return  R.success("删除成功");
    }

    /**
     * 增加供应商信息
     */
    @PostMapping ("/addSupplier")
    @CacheEvict(cacheNames = "supplierCache",allEntries = true)
    public R<String> addSupplier(@RequestBody SupplierInfo drugInfoDto){
        if(supplierInfoService.addSplNoExists(drugInfoDto)){
            return R.success("添加成功");
        }else {
            return R.error("当前厂商已经存在");
        }
    }

}
