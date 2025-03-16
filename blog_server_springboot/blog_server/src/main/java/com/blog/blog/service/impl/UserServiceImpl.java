package com.blog.blog.service.impl;

import com.blog.blog.entity.User;
import com.blog.blog.mapper.UserMapper;
import com.blog.blog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    
    @Autowired
    private UserMapper userMapper;
    
    @Override
    public User findByUsername(String username) {
        return userMapper.findByUsername(username);
    }
    
    @Override
    public User getUserByUsername(String username) {
        // 可以直接调用findByUsername方法
        return findByUsername(username);
    }
    
    @Override
    public boolean saveUser(User user) {
        try {
            int result = userMapper.insertUser(user);
            return result > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    @Override
    public boolean updateUser(User user) {
        try {
            int result = userMapper.updateUser(user);
            return result > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}