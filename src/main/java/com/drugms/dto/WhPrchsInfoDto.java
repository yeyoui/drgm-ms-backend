package com.drugms.dto;

import com.drugms.entity.DrugInfo;
import com.drugms.entity.WhPrchsInfo;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ToString(callSuper = true)
public class WhPrchsInfoDto extends WhPrchsInfo implements Serializable {
    private static final long serialVersionUID = 108L;

    /**
     * 厂商名
     */
    private String splName;

    /**
     *  药品名
     */
    private String drugName;
}
