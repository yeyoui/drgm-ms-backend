package com.drugms.dto;

import com.drugms.entity.UserRetInfo;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ToString(callSuper = true)
public class UserRetInfoDto extends UserRetInfo implements Serializable {
    private static final long serialVersionUID = 103L;

    /**
     * 药品名称
     */
    private String drugName;
}
