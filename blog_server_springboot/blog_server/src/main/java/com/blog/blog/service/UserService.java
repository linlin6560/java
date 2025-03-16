package com.blog.blog.service;

import com.blog.blog.entity.User;

public interface UserService {
    
    // 添加这个方法
    User findByUsername(String username);
    
    // 其他可能存在的方法
    User getUserByUsername(String username);
    
    boolean saveUser(User user);
    // 在UserService接口中添加以下方法
    boolean updateUser(User user);
}