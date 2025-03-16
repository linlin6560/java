package com.blog.blog.controller;

import com.blog.blog.entity.Article;
import com.blog.blog.entity.User;
import com.blog.blog.service.ArticleService;
import com.blog.blog.service.UserService; // 添加UserService导入
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/articles")
public class ArticleController {
    
    @Autowired
    private ArticleService articleService;
    
    @Autowired
    private UserService userService; // 添加UserService注入
    
    // 文章列表页
    @GetMapping
    public String listArticles(Model model, Authentication authentication) {
        List<Article> articles;
        
        // 如果是管理员，显示所有文章
        if (authentication != null && authentication.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"))) {
            articles = articleService.getAllArticles();
        } else if (authentication != null) {
            // 普通用户只能看到自己的文章和已发布的公开文章
            String username = authentication.getName();
            User user = userService.findByUsername(username);
            if (user != null) {
                articles = articleService.getArticlesForUser(user.getId());
            } else {
                articles = articleService.getPublishedArticles();
            }
        } else {
            // 未登录用户只能看到已发布的公开文章
            articles = articleService.getPublishedArticles();
        }
        
        model.addAttribute("articles", articles);
        return "article/list";
    }
    
    // 查看单篇文章
    @GetMapping("/{id}")
    public String viewArticle(@PathVariable Long id, Model model) {
        Article article = articleService.getArticleById(id);
        if (article == null) {
            return "redirect:/articles";
        }
        model.addAttribute("article", article);
        return "article/view";
    }
    
    // 创建文章页面
    @GetMapping("/create")
    public String createArticleForm(Model model) {
        model.addAttribute("article", new Article());
        return "article/edit";
    }
    
    // 处理创建文章请求
    @PostMapping("/create")  // 删除重复的注解，只保留一个
    public String createArticle(@ModelAttribute Article article, Authentication authentication) {
        if (authentication != null && authentication.isAuthenticated()) {
            String username = authentication.getName();
            User user = userService.getUserByUsername(username);
            
            if (user != null) {
                // 清理文章内容中的 HTML 标签
                String content = article.getContent();
                if (content != null) {
                    content = content.replaceAll("<[^>]*>", "");
                    article.setContent(content);
                }
                
                // 设置文章作者信息
                article.setUserId(user.getId());
                article.setAuthor(user.getUsername());
                
                // 设置创建时间和更新时间
                long currentTime = System.currentTimeMillis();
                article.setCreateTime(currentTime);
                article.setUpdateTime(currentTime);
                
                // 保存文章
                articleService.createArticle(article);
                return "redirect:/articles";
            }
        }
        
        // 未登录或保存失败，重定向到登录页
        return "redirect:/login";
    }
    
    // 编辑文章页面
    @GetMapping("/edit/{id}")
    public String editArticleForm(@PathVariable Long id, Model model) {
        Article article = articleService.getArticleById(id);
        if (article == null) {
            return "redirect:/articles";
        }
        
        // 检查当前用户是否是文章作者
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        // 修改用户获取方式，避免类型转换错误
        String username = authentication.getName();
        User currentUser = userService.getUserByUsername(username);
        
        if (currentUser == null || !article.getUserId().equals(currentUser.getId())) {
            return "redirect:/articles";
        }
        
        model.addAttribute("article", article);
        return "article/edit";
    }
    
    // 处理编辑文章请求
    @PostMapping("/edit/{id}")
    public String editArticle(@PathVariable Long id, @ModelAttribute Article article, RedirectAttributes redirectAttributes) {
        Article existingArticle = articleService.getArticleById(id);
        if (existingArticle == null) {
            return "redirect:/articles";
        }
        
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User currentUser = userService.getUserByUsername(username);
        
        if (currentUser == null || !existingArticle.getUserId().equals(currentUser.getId())) {
            return "redirect:/articles";
        }
        
        // 清理文章内容中的 HTML 标签
        String content = article.getContent();
        if (content != null) {
            content = content.replaceAll("<[^>]*>", "");
            article.setContent(content);
        }
        
        // 更新文章信息
        existingArticle.setTitle(article.getTitle());
        existingArticle.setContent(content);
        existingArticle.setStatus(article.getStatus());
        existingArticle.setUpdateTime(System.currentTimeMillis());
        
        boolean success = articleService.updateArticle(existingArticle);
        if (success) {
            return "redirect:/articles/" + id;
        } else {
            redirectAttributes.addAttribute("error", true);
            return "redirect:/articles/edit/" + id;
        }
    }
    
    // 删除文章
    @GetMapping("/delete/{id}")
    public String deleteArticle(@PathVariable Long id) {
        // 获取原文章
        Article existingArticle = articleService.getArticleById(id);
        if (existingArticle == null) {
            return "redirect:/articles";
        }
        
        // 检查当前用户是否是文章作者
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        // 修改用户获取方式
        String username = authentication.getName();
        User currentUser = userService.getUserByUsername(username);
        
        if (currentUser == null || !existingArticle.getUserId().equals(currentUser.getId())) {
            return "redirect:/articles";
        }
        
        articleService.deleteArticle(id);
        return "redirect:/articles";
    }
    
    // 我的文章页面
    @GetMapping("/my-articles")
    public String myArticles(Model model) {
        // 获取当前登录用户
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        // 修改用户获取方式
        String username = authentication.getName();
        User currentUser = userService.getUserByUsername(username);
        
        if (currentUser == null) {
            return "redirect:/login";
        }
        
        List<Article> articles = articleService.getArticlesByUserId(currentUser.getId());
        model.addAttribute("articles", articles);
        return "article/my-articles";
    }
}