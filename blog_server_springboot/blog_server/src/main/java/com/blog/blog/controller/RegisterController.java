package com.blog.blog.controller;

import com.blog.blog.entity.User;
import com.blog.blog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class RegisterController {

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/register")
    public String showRegisterForm(Model model) {
        model.addAttribute("user", new User());
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(@ModelAttribute("user") User user, Model model) {
        // 检查用户名是否已存在
        if (userService.findByUsername(user.getUsername()) != null) {
            model.addAttribute("error", "用户名已存在，请选择其他用户名");
            return "register";
        }

        // 设置用户信息
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setStatus(1); // 正常状态
        user.setCreateTime(System.currentTimeMillis());
        user.setUpdateTime(System.currentTimeMillis());

        // 保存用户
        boolean success = userService.saveUser(user);
        if (success) {
            model.addAttribute("success", "注册成功，请登录");
            model.addAttribute("user", new User()); // 清空表单
            return "register";
        } else {
            model.addAttribute("error", "注册失败，请稍后重试");
            return "register";
        }
    }
}