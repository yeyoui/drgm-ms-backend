package com.drugms.entity;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 供应信息
 * </p>
 *
 * @author lhy
 * @since 2023-01-05
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class SupplyInfo implements Serializable {

    private static final long serialVersionUID = 5L;

    /**
     * 药品ID
     */
    @TableId
    private Integer did;

    /**
     * 供应商ID
     */
    private Integer sid;


}
