package com.drugms.entity;

import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * <p>
 * 药品仓库信息
 * </p>
 *
 * @author lhy
 * @since 2023-01-05
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
public class WarehouseInfo implements Serializable {

    private static final long serialVersionUID = 7L;

    /**
     * 仓库记录ID
     */
    @TableId(value = "wid", type = IdType.AUTO)
    private Integer wid;

    /**
     * 药品ID
     */
    private Integer did;

    /**
     * 库存
     */
    private Integer stock;

    /**
     * 已售数量
     */
    private Integer sellNum;

    /**
     * 退货数量
     */
    private Integer retNum;

    /**
     * 售价
     */
    private BigDecimal goodsPrice;


}
