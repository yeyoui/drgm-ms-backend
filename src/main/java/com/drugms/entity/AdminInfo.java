package com.drugms.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 管理员信息
 * </p>
 *
 * @author lhy
 * @since 2023-01-05
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class AdminInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 管理员ID
     */
    @TableId(value = "ID", type = IdType.AUTO)
    private Integer id;

    /**
     * 管理员账号
     */
    private String username;

    /**
     * 管理员密码
     */
    private String password;

    /**
     * 管理员邮箱
     */
    private String email;

    /**
     * 管理员电话
     */
    private String phone;


}
