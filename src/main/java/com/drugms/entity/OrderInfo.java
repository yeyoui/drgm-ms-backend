package com.drugms.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 订单信息
 * </p>
 *
 * @author lhy
 * @since 2023-01-05
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class OrderInfo implements Serializable {

    private static final long serialVersionUID = 3L;

    /**
     * 订单ID
     */
    @TableId(value = "oid", type = IdType.AUTO)
    private Integer oid;

    /**
     * 仓库记录ID
     */
    private Integer wid;

    /**
     * 订单状态(0：正常状态 1：未处理的退货 2： 完成回厂退货)
     */
    private Integer status;

    /**
     * 购买数量
     */
    private Integer prchsNum;

    /**
     * 下单地址
     */
    private String address;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 下单用户
     */
    private String prchsUser;


}
