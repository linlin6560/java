package com.blog.blog.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private JdbcTemplate jdbcTemplate;
    
    @GetMapping("/clear-articles")
    @ResponseBody
    public String clearArticles() {
        try {
            // 清空文章表
            int deletedRows = jdbcTemplate.update("DELETE FROM article");
            
            // 重置SQLite自增ID
            jdbcTemplate.update("DELETE FROM sqlite_sequence WHERE name = 'article'");
            
            return "文章表已清空，删除了 " + deletedRows + " 条记录，自增ID已重置";
        } catch (Exception e) {
            e.printStackTrace();
            return "清空文章表失败: " + e.getMessage();
        }
    }
    
    @GetMapping("/article-count")
    @ResponseBody
    public String getArticleCount() {
        try {
            Integer count = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM article", Integer.class);
            return "当前文章数量: " + count;
        } catch (Exception e) {
            e.printStackTrace();
            return "获取文章数量失败: " + e.getMessage();
        }
    }
}