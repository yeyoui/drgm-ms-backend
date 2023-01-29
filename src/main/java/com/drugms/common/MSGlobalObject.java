package com.drugms.common;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
@Data
public class MSGlobalObject {
    private static BigDecimal totalSaleMoney;
}
