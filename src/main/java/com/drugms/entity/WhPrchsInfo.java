package com.drugms.entity;

import java.time.LocalDateTime;
import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 进货信息
 * </p>
 *
 * @author lhy
 * @since 2023-01-05
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class WhPrchsInfo implements Serializable {

    private static final long serialVersionUID = 9L;

    /**
     * 进货批号
     */
    @TableId(value = "prchsNo",type = IdType.AUTO)
    private Integer prchsNo;

    /**
     * 药品ID
     */
    private Integer did;

    /**
     * 仓库记录ID
     */
    private Integer wid;

    /**
     * 供应量
     */
    private Integer splyNum;

    /**
     * 生产日期
     */
    private LocalDateTime proTime;

    /**
     * 是否过期
     */
    private Boolean hadExpired;

}
