package com.drugms.dto;

import com.drugms.entity.OrderInfo;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ToString(callSuper = true)
public class OrderInfoDto extends OrderInfo implements Serializable {

    private static final long serialVersionUID = 103L;
    private String drugName;
    private BigDecimal drugPrice;
    private Integer did;
    private Boolean updWarehouse;

}
