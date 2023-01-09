package com.drugms;

import com.drugms.dto.OrderInfoDto;
import com.drugms.dto.WarehouseInfoDto;
import com.drugms.mapper.WarehouseInfoMapper;
import com.drugms.service.OrderInfoService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.List;

@SpringBootTest
class DrugMsApplicationTests {
    @Autowired
    WarehouseInfoMapper warehouseInfoMapper;
    @Autowired
    OrderInfoService orderInfoService;
    @Autowired
    RedisTemplate<String,Object> redisTemplate;

    @Test
    void contextLoads() {
    }
    @Test
    void testPage(){
        List<OrderInfoDto> dtos = orderInfoService.getOrderDtoPage(-1, 0, 10, "");
        dtos.forEach(System.out::println);
        System.out.println(">>>>>>"+orderInfoService.getOrderDtoPageCount(-1, 0, 10, ""));
    }
    @Test
    void testRedis(){
        redisTemplate.opsForValue().set("lhy123","yeyoui");
        System.out.println(redisTemplate.opsForValue().get("lhy123"));
    }

}
