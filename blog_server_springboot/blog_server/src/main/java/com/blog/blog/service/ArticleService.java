package com.blog.blog.service;

import com.blog.blog.entity.Article;

import java.util.List;

public interface ArticleService {
    
    Article getArticleById(Long id);
    
    List<Article> getAllArticles();
    
    List<Article> getLatestArticles(int limit);
    
    List<Article> getPublishedArticles();
    
    List<Article> getArticlesForUser(Long userId);
    
    // 添加这个方法
    List<Article> getArticlesByUserId(Long userId);
    
    boolean createArticle(Article article);
    
    boolean updateArticle(Article article);
    
    boolean deleteArticle(Long id);
    
    // 添加清空文章方法
    boolean deleteAllArticles();
}