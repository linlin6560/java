package com.blog.blog.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.io.File;

@Component
public class SqliteInitializer implements CommandLineRunner {

    @Autowired
    private JdbcTemplate jdbcTemplate;
    
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        // 开始初始化数据库
        System.out.println("开始初始化数据库...");
        File dbFile = new File("blog.db");
        System.out.println("数据库文件路径: " + dbFile.getAbsolutePath());
        System.out.println("数据库文件是否存在: " + dbFile.exists());
        
        // 创建用户表
        jdbcTemplate.execute("CREATE TABLE IF NOT EXISTS user (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "username VARCHAR(50) NOT NULL UNIQUE," +
                "password VARCHAR(100) NOT NULL," +
                "nickname VARCHAR(50)," +
                "email VARCHAR(100)," +
                "avatar VARCHAR(255)," +
                "status INTEGER DEFAULT 1," +
                "create_time BIGINT," +
                "update_time BIGINT" +
                ")");
        
        // 检查是否已有管理员账号
        Integer count = jdbcTemplate.queryForObject(
                "SELECT COUNT(*) FROM user WHERE username = 'admin'", Integer.class);
        
        // 如果没有管理员账号，则创建一个
        if (count != null && count == 0) {
            // 使用passwordEncoder直接加密明文密码
            String encodedPassword = passwordEncoder.encode("admin");
            System.out.println("创建管理员账号，加密后的密码: " + encodedPassword);
            
            jdbcTemplate.update(
                    "INSERT INTO user (username, password, nickname, status, create_time, update_time) VALUES (?, ?, ?, ?, ?, ?)",
                    "admin",
                    encodedPassword, // 使用动态生成的加密密码
                    "管理员",
                    1,
                    System.currentTimeMillis(),
                    System.currentTimeMillis()
            );
            System.out.println("管理员账号创建成功");
        } else {
            System.out.println("管理员账号已存在，无需创建");
        }
    }
}