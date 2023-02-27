package com.drugms.controller;


import com.drugms.common.CustomException;
import com.drugms.common.R;
import com.drugms.common.VerifyCode;
import com.drugms.dto.LoginDto;
import com.drugms.entity.AdminInfo;
import com.drugms.service.AdminInfoService;
import com.drugms.service.WarehouseInfoService;
import com.drugms.utils.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletRequest;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.UUID;

/**
 * <p>
 * 管理员信息 前端控制器
 * </p>
 *
 * @author lhy
 * @since 2023-01-05
 */
@RestController
@RequestMapping("/admin")
@Slf4j
public class AdminInfoController {
    @Autowired
    private AdminInfoService adminInfoService;


    @GetMapping("/checkAndUpdExpiredDrug")
    public R<String> checkAndUpdExpiredDrug(){
        throw new CustomException("该功能不支持!");
//        return null;
    }

    @PostMapping("/login")
    public R<String> login(@RequestBody LoginDto adminInfo, HttpServletRequest request, HttpServletResponse response){

        HttpSession session = request.getSession();
        log.info(request.getRemoteAddr());
        String valid = (String) RedisUtils.getValue(request.getRemoteAddr());
        if(valid==null) return R.error("验证码过期");
        else if(!valid.equals(adminInfo.getVercode())) return R.error("验证码错误");
        Integer checkStatus=adminInfoService.checkLogin(adminInfo.getUsername(),adminInfo.getPassword());
        if(checkStatus==1){
            String tokenUUID= UUID.randomUUID().toString();
            log.info("token: {}",tokenUUID);
            Cookie cookie = new Cookie("token", tokenUUID);
            cookie.setPath("/");
            //一天过期（24小时）
            cookie.setMaxAge(60*60*24);
            response.addCookie(cookie);
            session.setAttribute("admin",adminInfo.getUsername());
            //将登录token缓存到Redis数据库中(token-username键值对)
            RedisUtils.addWithExpired(tokenUUID,adminInfo.getUsername(),60*60*24L);
            return R.success("登录成功");
        } else if (checkStatus==0) {
            return R.error("账号不存在");
        }else {
            return R.error("账号或密码错误");
        }
    }
    @PostMapping("/register")
    public R<String> register(@RequestBody AdminInfo adminInfo){
        adminInfo.setPassword(DigestUtils.md5DigestAsHex(adminInfo.getPassword().getBytes()));//md5加密密码
        adminInfoService.save(adminInfo);
        return R.success("注册成功");
    }

    @GetMapping("/getAdminName")
    public R<String> getAdminName(HttpServletRequest request){
        Object usernameObject = request.getSession().getAttribute("admin");
        if(usernameObject!=null){
            return R.success((String)usernameObject);
        }
        else return R.success("error!");
    }

    @GetMapping("/vercode")
    public void vercode(HttpServletRequest req,HttpServletResponse resp) throws IOException {
        VerifyCode verifyCode = new VerifyCode();
        BufferedImage image = verifyCode.getImage();
        String text= verifyCode.getText();
        log.info(req.getRemoteAddr());
        RedisUtils.setWithExpired(req.getRemoteAddr(),text,30L);
        VerifyCode.output(image,resp.getOutputStream());
    }
}
