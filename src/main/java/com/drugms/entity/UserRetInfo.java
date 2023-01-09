package com.drugms.entity;

import java.time.LocalDateTime;
import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 用户退货信息
 * </p>
 *
 * @author lhy
 * @since 2023-01-05
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class UserRetInfo implements Serializable {

    private static final long serialVersionUID = 6L;

    /**
     * 订单ID
     */
    @TableId
    private Integer oid;

    /**
     * 退货时间
     */
    private LocalDateTime retTime;

    /**
     * 退货原因
     */
    private String reason;


}
