package com.drugms.dto;

import com.drugms.entity.WarehouseInfo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class WarehouseInfoDto extends WarehouseInfo implements Serializable {

    private static final long serialVersionUID = 102L;

    private String drugName;
    private String splName;
    private BigDecimal drugPrice;

}
