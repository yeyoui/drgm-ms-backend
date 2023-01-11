package com.drugms.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 药品问题信息
 * </p>
 *
 * @author lhy
 * @since 2023-01-10
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class DrugProblemInfo implements Serializable {
    private static final long serialVersionUID = 10L;
    /**
     * 药品问题记录ID
     */
    @TableId(value = "dpid", type = IdType.AUTO)
    private Integer dpid;

    /**
     * 仓库记录ID
     */
    private Integer wid;

    /**
     * 数量
     */
    private Integer dpNum;

    /**
     * 是否处理
     */
    private Boolean hadHandle;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 修改时间
     */
    private LocalDateTime updateTime;

    /**
     * 问题原因类型（和用户退货表同名字段共用） 0:过期 1:包装破损 2:药品变质 3:药品发错 4:7天无理由
     */
    private Integer problemType;
}
