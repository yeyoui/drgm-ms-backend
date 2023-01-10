package com.drugms;

import com.drugms.dto.DrugProblemInfoDto;
import com.drugms.dto.OrderInfoDto;
import com.drugms.dto.WarehouseInfoDto;
import com.drugms.entity.DrugProblemInfo;
import com.drugms.mapper.DrugProblemInfoMapper;
import com.drugms.mapper.WarehouseInfoMapper;
import com.drugms.service.OrderInfoService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
@Slf4j
class DrugMsApplicationTests {
    @Autowired
    DrugProblemInfoMapper drugProblemInfoMapper;
    @Autowired
    OrderInfoService orderInfoService;
//    @Autowired
//    RedisTemplate<String,Object> redisTemplate;

    @Test
    void contextLoads() {
    }
    @Test
    void testPage() {
        List<DrugProblemInfoDto> dtos = drugProblemInfoMapper.getDrugProblemInfoDtoPage(
                -1, 0, 10, null,null,null);
//        dtos.forEach(System.out::println);
//        drugProblemInfoMapper.getDrugProblemInfoDtoPageCount(
//                7, 0, 10, "%保和%",1,true);
    }
    @Test
    void TestSplit(){
        String name=" , , ,";
        String[] split = name.split(",");
        System.out.println(split.length);
        for (String s : split) {
            System.out.println("*"+s+"*");
        }
    }

}
