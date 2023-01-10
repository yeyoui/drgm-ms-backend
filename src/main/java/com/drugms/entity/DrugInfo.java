package com.drugms.entity;

import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 药品信息
 * </p>
 *
 * @author lhy
 * @since 2023-01-05
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class DrugInfo implements Serializable {

    private static final long serialVersionUID = 2L;

    /**
     * 药品ID
     */
    @TableId(value = "did", type = IdType.AUTO)
    private Integer did;

    /**
     * 药品名
     */
    private String drugName;

    /**
     * 保质期
     */
    private Integer warranty;

    /**
     * 药品编码
     */
    private String drugNo;

    /**
     * 进价
     */
    private BigDecimal drugPrice;

}
