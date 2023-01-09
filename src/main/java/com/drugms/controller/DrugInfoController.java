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
import com.drugms.service.DrugInfoService;
import com.drugms.service.SupplierInfoService;
import com.drugms.service.SupplyInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 药品信息 前端控制器
 * </p>
 *
 * @author lhy
 * @since 2023-01-05
 * 实现了Cache
 */
@RestController
@RequestMapping("/drug")
@Slf4j
public class DrugInfoController {
    @Autowired
    private DrugInfoService drugInfoService;
    @Autowired
    private SupplyInfoService supplyInfoService;
    @Autowired
    private SupplierInfoService supplierInfoService;

    /**
     * 分页查询
     * @param curPage
     * @param limit
     * @param sname
     */
    @GetMapping("/page")
    @Cacheable(cacheNames = "DrugInfoCache",key = "'page'+'_'+#curPage+'_'+#sname")
    public R<Page<DrugInfoDto>> page(Integer curPage,Integer limit,String sname){
//        log.info("curPage:{} limit:{} sname:{}",curPage,limit,sname);

        //按照名字查询
        LambdaQueryWrapper<DrugInfo> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(StringUtils.isNotEmpty(sname),DrugInfo::getDrugName,sname);

        Page<DrugInfo> page=new Page<>(curPage,limit);
        Page<DrugInfoDto> dtoPage=new Page<>();
        drugInfoService.page(page,queryWrapper);
        //将数据整合到DTO
        BeanUtils.copyProperties(page,dtoPage,"records");
        List<DrugInfo> records=page.getRecords();
        List<DrugInfoDto> dtoRecord=records.stream().map((item)->{
            DrugInfoDto drugInfoDto = new DrugInfoDto();
            BeanUtils.copyProperties(item,drugInfoDto);
            Integer drugId=item.getDid();
            //获取供应商ID
            LambdaQueryWrapper<SupplyInfo> qwSupply = new LambdaQueryWrapper<>();
            qwSupply.eq(SupplyInfo::getDid,drugId);
            Integer supplierId=supplyInfoService.getOne(qwSupply).getSid();
            //将供应商名和ID添加到DTO中
            String supplierName=supplierInfoService.getById(supplierId).getSplName();
            drugInfoDto.setSplName(supplierName);
            drugInfoDto.setSid(supplierId);
            return drugInfoDto;
        }).collect(Collectors.toList());
        dtoPage.setRecords(dtoRecord);

        return R.success(dtoPage);
    }

    /**
     * 修改药品和供应关系
     * @param drugInfoDto
     */
    @PostMapping("/editDrug")
    @CacheEvict(cacheNames = "DrugInfoCache",allEntries = true)
    public R<String> edituser(@RequestBody DrugInfoDto drugInfoDto){

        drugInfoService.updDrugAndSupply(drugInfoDto);
        return R.success("修改成功");
    }


    /**
     * 删除药品和供应关系
     * @param did
     */
    @PostMapping ("/delDrug")
    @CacheEvict(cacheNames = "DrugInfoCache",allEntries = true)
    public R<String> delUser(@RequestParam Integer did){
        drugInfoService.delDrugAndSupply(did);
        return  R.success("删除成功");
    }

    /**
     * 增加药品和供应关系
     * @param drugInfoDto
     * @return
     */
    @PostMapping ("/addDrug")
    @CacheEvict(cacheNames = "DrugInfoCache",allEntries = true)
    public R<String> addDrug(@RequestBody DrugInfoDto drugInfoDto){
        //保存药品信息
        drugInfoService.save(drugInfoDto);
        //保存供应信息
        SupplyInfo supplyInfo = new SupplyInfo();
        supplyInfo.setSid(drugInfoDto.getSid());
        supplyInfo.setDid(drugInfoDto.getDid());
        supplyInfoService.save(supplyInfo);
        return R.success("添加成功");
    }

    /**
     * 获取简单药品信息
     */
    @GetMapping("/list")
    @Cacheable(cacheNames = "DrugInfoCache",key = "'list'")
    public R<List<DrugInfo>> list(@RequestParam(required = false) String name){
        QueryWrapper<DrugInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("did","drug_name").like(StringUtils.isNotEmpty(name),"drug_name",name);
        return R.success(drugInfoService.list(queryWrapper));
    }

    /**
     * 获取某个药品的价格
     */
    @GetMapping("/getOneDrugPriceByDrugId")
    @Cacheable(cacheNames = "DrugInfoCache",key = "'getOnePrice'+'_'+#did")
    public R<BigDecimal> getOnePrice(@RequestParam Integer did){
        QueryWrapper<DrugInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("drug_price").eq("did",did);
        DrugInfo drugInfo = drugInfoService.getOne(queryWrapper);
        return R.success(drugInfo.getDrugPrice());
    }

}
