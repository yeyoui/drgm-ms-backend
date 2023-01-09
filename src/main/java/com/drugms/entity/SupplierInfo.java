package com.drugms.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 供应商信息
 * </p>
 *
 * @author lhy
 * @since 2023-01-05
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class SupplierInfo implements Serializable {

    private static final long serialVersionUID = 4L;

    /**
     * 供应商ID
     */
    @TableId(value = "sid", type = IdType.AUTO)
    private Integer sid;

    /**
     * 供应商名称
     */
    private String splName;

    /**
     * 供应商简介
     */
    private String splContent;

    /**
     * 电话
     */
    private String splPhone;


}
