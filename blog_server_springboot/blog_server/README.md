# Blog Server

基于Spring Boot的博客系统后端服务

## 项目说明

这是一个使用Spring Boot开发的博客系统后端服务，提供博客的基础功能，包括文章管理、用户认证等功能。

## 技术栈

- Java 17
- Spring Boot 3.4.3
- Spring Security
- MyBatis
- SQLite
- Maven

## 环境要求

- JDK 17+
- Maven 3.6+
- SQLite 3+

## 快速开始

### 1. 克隆项目

```bash
git clone <repository-url>
cd blog_server_springboot

### 2. 配置数据库
项目使用SQLite作为数据库，数据库文件会在项目首次运行时自动创建在项目根目录下的 blog.db 。

### 3. 修改配置
主要配置文件位于 src/main/resources/application.properties ，可根据需要修改：

- 服务器端口：默认8084
- 数据库配置
- MyBatis配置
- 日志级别
### 4. 构建运行
```bash
mvn clean package
java -jar target/blog-0.0.1-SNAPSHOT.jar
 ```
```

或者直接使用Maven运行：

```bash
mvn spring-boot:run
 ```

## 项目结构
```plaintext
blog_server_springboot/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/blog/blog/
│   │   │       ├── config/        # 配置类
│   │   │       ├── controller/    # 控制器
│   │   │       ├── entity/        # 实体类
│   │   │       ├── mapper/        # MyBatis映射器
│   │   │       ├── service/       # 服务层
│   │   │       └── BlogApplication.java
│   │   └── resources/
│   │       ├── mapper/           # MyBatis映射文件
│   │       ├── static/           # 静态资源
│   │       ├── templates/        # 模板文件
│   │       └── application.properties
│   └── test/                     # 测试代码
└── pom.xml
 ```
```

## API文档
主要API端点：

- 认证相关： /auth/*
- 文章相关： /article/*
- 用户相关： /user/*
详细API文档请参考项目文档或接口说明。

## 开发说明
1. 本项目使用Maven管理依赖
2. 使用MyBatis作为ORM框架
3. 采用RESTful API设计规范
4. 集成Spring Security进行安全认证
## 许可证
MIT License

```plaintext

这个README文件包含了项目的基本信息、环境要求、快速开始指南、项目结构等重要信息。你可以根据实际需求对内容进行调整和补充。

要使用这个README，只需将文件保存为`README.md`放在项目根目录下即可。如果需要修改或补充任何部分，请告诉我。
 ```
```
