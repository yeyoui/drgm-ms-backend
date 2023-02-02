package com.drugms.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.drugms.entity.AdminInfo;
import com.drugms.mapper.AdminInfoMapper;
import com.drugms.service.AdminInfoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

/**
 * <p>
 * 管理员信息 服务实现类
 * </p>
 *
 * @author lhy
 * @since 2023-01-05
 */
@Service
public class AdminInfoServiceImpl extends ServiceImpl<AdminInfoMapper, AdminInfo> implements AdminInfoService {
    @Override
    public Integer checkLogin(String username, String password) {
        LambdaQueryWrapper<AdminInfo> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(AdminInfo::getUsername,username);
        AdminInfo user = this.getOne(queryWrapper);
        if(user==null) return 0;
        else if(user.getPassword().equals(DigestUtils.md5DigestAsHex((password).getBytes()))) return 1;
        else return -1;
    }
}
