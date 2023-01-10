package com.drugms.dto;

import com.drugms.entity.DrugProblemInfo;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class DrugProblemInfoDto extends DrugProblemInfo implements Serializable {
    private static final long serialVersionUID = 104L;
    /**
     * 药品名
     */
    private String drugName;
}
