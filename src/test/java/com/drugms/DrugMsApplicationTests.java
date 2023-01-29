package com.drugms;

import com.drugms.common.MSGlobalObject;
import com.drugms.dto.UserRetInfoDto;
import com.drugms.dto.WarehouseRetInfoDto;
import com.drugms.mapper.DrugProblemInfoMapper;
import com.drugms.mapper.UserRetInfoMapper;
import com.drugms.service.WarehouseRetInfoService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.TimeUnit;

@SpringBootTest
@Slf4j
class DrugMsApplicationTests {
    @Autowired
    UserRetInfoMapper userRetInfoMapper;
    @Autowired
    WarehouseRetInfoService warehouseRetInfoService;
    @Autowired
    MSGlobalObject msGlobalObject;
    @Test
    void contextLoads() {
    }
    @Test
    void testPage() {
        List<UserRetInfoDto> dtos = userRetInfoMapper.getUserRetInfoDtoPage(
                0, 0, 10, null,null,null);
        dtos.forEach(System.out::println);
//        dtos.forEach(System.out::println);
//        drugProblemInfoMapper.getDrugProblemInfoDtoPageCount(
//                7, 0, 10, "%保和%",1,true);
    }

    @Test
    void testGlobalObject() throws InterruptedException {
        LocalDateTime begin = LocalDateTime.now();
        TimeUnit.SECONDS.sleep(5);
        LocalDateTime end = LocalDateTime.now();
        Duration between = Duration.between(begin, end);
        System.out.println(between.toMillis());
        System.out.println(between.toDays());
        System.out.println(between.toHours());
    }

}
