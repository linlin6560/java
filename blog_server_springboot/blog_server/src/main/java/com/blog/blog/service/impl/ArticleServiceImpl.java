package com.blog.blog.service.impl;

import com.blog.blog.entity.Article;
import com.blog.blog.mapper.ArticleMapper;
import com.blog.blog.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ArticleServiceImpl implements ArticleService {
    
    @Autowired
    private ArticleMapper articleMapper;
    
    @Override
    public List<Article> getAllArticles() {
        return articleMapper.findAll();
    }
    
    @Override
    public Article getArticleById(Long id) {
        return articleMapper.findById(id);
    }
    
    @Override
    public List<Article> getArticlesByUserId(Long userId) {
        return articleMapper.findByUserId(userId);
    }
    
    @Override
    public List<Article> getLatestArticles(int limit) {
        return articleMapper.findLatest(limit);
    }
    
    @Override
    public List<Article> getPublishedArticles() {
        try {
            return articleMapper.findPublished();
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }
    
    @Override
    public List<Article> getArticlesForUser(Long userId) {
        List<Article> articles = articleMapper.findForUser(userId);
        for (Article article : articles) {
            // 去除HTML标签
            String content = article.getContent();
            if (content != null) {
                content = content.replaceAll("<[^>]+>", "");
                article.setContent(content);
            }
        }
        return articles;
    }
    
    @Override
    public boolean createArticle(Article article) {
        try {
            return articleMapper.insert(article) > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    @Override
    public boolean updateArticle(Article article) {
        try {
            return articleMapper.update(article) > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    @Override
    public boolean deleteArticle(Long id) {
        try {
            return articleMapper.delete(id) > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    @Override
    public boolean deleteAllArticles() {
        try {
            return articleMapper.deleteAll() >= 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}