package com.drugms.service.impl;

import com.drugms.dto.UserRetInfoDto;
import com.drugms.entity.DrugProblemInfo;
import com.drugms.entity.OrderInfo;
import com.drugms.entity.UserRetInfo;
import com.drugms.mapper.UserRetInfoMapper;
import com.drugms.service.DrugProblemInfoService;
import com.drugms.service.OrderInfoService;
import com.drugms.service.UserRetInfoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.drugms.service.WarehouseInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 用户退货信息 服务实现类
 * </p>
 *
 * @author lhy
 * @since 2023-01-05
 */
@Service
public class UserRetInfoServiceImpl extends ServiceImpl<UserRetInfoMapper, UserRetInfo> implements UserRetInfoService {
    @Autowired
    private UserRetInfoMapper userRetInfoMapper;
    @Autowired
    private DrugProblemInfoService drugProblemInfoService;
    @Autowired
    private OrderInfoService orderInfoService;
    @Autowired
    private WarehouseInfoService warehouseInfoService;

    @Override
    public List<UserRetInfoDto> getUserRetInfoDtoPage(int type, int curPage, int limit, String name) {
        String searchName=null;
        Integer problemType=null;
        Integer searchType=null;

        String[] strings = name.split(",");

        if(!" ".equals(strings[0])) searchType=Integer.parseInt(strings[0]);
        if(!" ".equals(strings[1])){
            if(searchType==0) searchName="%"+strings[1]+"%";
            else searchName=strings[1];

        }
        if(!" ".equals(strings[2])) problemType=Integer.parseInt(strings[2]);

        return userRetInfoMapper.getUserRetInfoDtoPage(type,(curPage-1)*limit,limit,searchType,searchName,problemType);
    }

    @Override
    public Integer getUserRetInfoDtoPageCount(int type, int curPage, int limit, String name) {
        String searchName=null;
        Integer problemType=null;
        Integer searchType=null;

        String[] strings = name.split(",");

        if(!" ".equals(strings[0])) searchType=Integer.parseInt(strings[0]);
        if(!" ".equals(strings[1])){
            if(searchType==0) searchName="%"+strings[1]+"%";
            else searchName=strings[1];

        }
        if(!" ".equals(strings[2])) problemType=Integer.parseInt(strings[2]);

        return userRetInfoMapper.getUserRetInfoDtoPageCount(type,(curPage-1)*limit,limit,searchType,searchName,problemType);
    }

    @Override
    public void agreeUserRet(int oid) {
        //获取退货信息
        UserRetInfo userRetInfo = this.getById(oid);
        OrderInfo orderInfo = orderInfoService.getById(userRetInfo.getOid());
        DrugProblemInfo drugProblemInfo = new DrugProblemInfo();

        drugProblemInfo.setWid(orderInfo.getWid());
        drugProblemInfo.setProblemType(userRetInfo.getProblemType());
        drugProblemInfo.setDpNum(orderInfo.getPrchsNum());
        //提交退货
        drugProblemInfoService.submitDrugProblem(drugProblemInfo,true);

        //更新仓库退货数
        warehouseInfoService.addRetNum(orderInfo.getWid(),orderInfo.getPrchsNum());
        //更新仓库销售数
        warehouseInfoService.decSaleNum(orderInfo.getWid(),orderInfo.getPrchsNum());
    }
}
