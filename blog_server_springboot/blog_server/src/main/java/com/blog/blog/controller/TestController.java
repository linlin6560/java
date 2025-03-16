package com.blog.blog.controller;

import com.blog.blog.entity.User;
import com.blog.blog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class TestController {

    @Autowired
    private UserService userService;
    
    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/user")
    public String testUser() {
        User user = userService.findByUsername("admin");
        if (user == null) {
            return "管理员用户不存在，请检查数据库初始化";
        }
        
        boolean passwordMatches = passwordEncoder.matches("admin", user.getPassword());
        
        return "管理员用户存在，ID: " + user.getId() + 
               ", 密码是否匹配: " + passwordMatches + 
               ", 密码长度: " + user.getPassword().length() +
               ", 密码前10个字符: " + user.getPassword().substring(0, 10) + "...";
    }
    
    @GetMapping("/login")
    public String manualLogin() {
        User user = userService.findByUsername("admin");
        if (user == null) {
            return "用户不存在";
        }
        
        return "这是一个测试登录页面，请使用正常的登录页面进行登录";
    }
    
    // 在TestController类中添加以下方法
    @GetMapping("/reset-password")
    public String resetAdminPassword() {
        User user = userService.findByUsername("admin");
        if (user == null) {
            return "管理员用户不存在";
        }
        
        // 使用passwordEncoder加密"admin"
        String encodedPassword = passwordEncoder.encode("admin");
        
        // 更新用户密码
        user.setPassword(encodedPassword);
        user.setUpdateTime(System.currentTimeMillis());
        
        boolean success = userService.updateUser(user);
        
        if (success) {
            return "管理员密码已重置为'admin'，新的加密密码: " + encodedPassword;
        } else {
            return "密码重置失败";
        }
    }
}