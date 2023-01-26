package com.drugms.dto;

import com.drugms.entity.WarehouseInfo;
import com.drugms.entity.WarehouseRetInfo;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class WarehouseRetInfoDto extends WarehouseRetInfo implements Serializable {
    private static final long serialVersionUID = 106L;

    private String drugName;
}
