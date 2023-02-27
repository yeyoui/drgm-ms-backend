package com.drugms.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class LoginDto {
    /**
     * 管理员账号
     */
    private String username;

    /**
     * 管理员密码
     */
    private String password;
    /**
     * 验证码
     */
    private String vercode;

}
