package com.blog.blog.mapper;

import com.blog.blog.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
// 移除不需要的 Select 导入
// import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserMapper {
    
    // 移除 @Select 注解，让方法只在 XML 中定义
    User findByUsername(@Param("username") String username);
    
    int insertUser(User user);
    
    int updateUser(User user);
}