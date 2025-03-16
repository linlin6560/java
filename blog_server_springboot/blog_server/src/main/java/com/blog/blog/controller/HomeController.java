package com.blog.blog.controller;

import com.blog.blog.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    
    @Autowired
    private ArticleService articleService;
    
    @GetMapping("/")
    public String home(Model model) {
        // 获取最新的10篇已发布文章（状态为1的文章）
        model.addAttribute("articles", articleService.getLatestArticles(10));
        return "index";
    }
    
    @GetMapping("/about")
    public String about() {
        return "about";
    }
}