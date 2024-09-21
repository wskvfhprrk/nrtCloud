package com.jc.nrtcloud.controller;

import com.jc.nrtcloud.sevice.JwtTokenUtil;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @PostMapping("/login")
    public String login(@RequestParam String username, @RequestParam String password) {
        // 这里应该进行用户名和密码的验证
        if ("user".equals(username) && "password".equals(password)) {
            return JwtTokenUtil.generateToken(username);  // 登录成功，返回 JWT Token
        } else {
            throw new RuntimeException("登录失败，用户名或密码错误");
        }
    }
}
