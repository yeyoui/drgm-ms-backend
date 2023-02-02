package com.drugms.common;

import com.drugms.entity.DailySales;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
@Data
public class MSGlobalObject {
    public static DailySales dailySales=new DailySales();
}
