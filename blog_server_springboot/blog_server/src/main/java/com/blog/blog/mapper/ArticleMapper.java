package com.blog.blog.mapper;

import com.blog.blog.entity.Article;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;

@Mapper
public interface ArticleMapper {
    // 查询所有文章
    List<Article> findAll();
    
    // 根据ID查询文章
    Article findById(Long id);
    
    // 根据用户ID查询文章
    List<Article> findByUserId(Long userId);
    
    // 查询最新的文章
    List<Article> findLatest(int limit);
    
    // 查询已发布的文章
    List<Article> findPublished();
    
    // 查询用户相关的文章
    List<Article> findForUser(Long userId);
    
    // 插入文章
    int insert(Article article);
    
    // 更新文章
    int update(Article article);
    
    // 删除文章
    // 在接口中添加清空方法
    int deleteAll();
    int delete(Long id);
}