package com.drugms.dto;

import com.drugms.entity.DrugInfo;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class DrugInfoDto extends DrugInfo implements Serializable {
    private static final long serialVersionUID = 101L;
    /**
     * 厂商名
     */
    private String splName;
    private Integer sid;

}
