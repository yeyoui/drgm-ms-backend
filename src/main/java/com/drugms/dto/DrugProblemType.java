package com.drugms.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
public class DrugProblemType implements Serializable {

    /**
     * 类型ID
     */
    private Integer typeId;

    /**
     * 类型名称
     */
    private String typeName;
}
