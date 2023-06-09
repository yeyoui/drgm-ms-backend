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
 * 仓库退货信息
 * </p>
 *
 * @author lhy
 * @since 2023-01-05
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class WarehouseRetInfo implements Serializable {

    private static final long serialVersionUID = 8L;

    /**
     * 仓库退货ID
     */
    @TableId(value = "wtid", type = IdType.AUTO)
    private Integer wtid;

    /**
     * 仓库记录ID
     */
    private Integer wid;

    /**
     * 回厂数量
     */
    private Integer wretNum;

    /**
     * 回厂时间
     */
    private LocalDateTime retTime;

    /**
     * 问题原因类型（和用户退货表同名字段共用） 0:过期 1:包装破损 2:药品变质 3:药品发错 4:7天无理由
     */
    private Integer problemType;


}
