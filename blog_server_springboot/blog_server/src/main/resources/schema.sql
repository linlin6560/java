-- 用户表
CREATE TABLE IF NOT EXISTS user (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(100) NOT NULL,
    nickname VARCHAR(50),
    email VARCHAR(100),
    avatar VARCHAR(255),
    role VARCHAR(20) DEFAULT 'USER',
    status INTEGER DEFAULT 1,  -- 添加status字段，1表示正常状态
    create_time BIGINT,
    update_time BIGINT
);

-- 文章表
CREATE TABLE IF NOT EXISTS article (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    title VARCHAR(100) NOT NULL,
    content TEXT,
    author VARCHAR(50),
    user_id INTEGER,
    status INTEGER DEFAULT 0, -- 0: 草稿, 1: 已发布
    create_time BIGINT,
    update_time BIGINT,
    FOREIGN KEY (user_id) REFERENCES user(id)
);

-- 分类表
CREATE TABLE IF NOT EXISTS category (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    name VARCHAR(50) NOT NULL,
    description VARCHAR(255),
    create_time BIGINT,
    update_time BIGINT
);

-- 文章分类关联表
CREATE TABLE IF NOT EXISTS article_category (
    article_id INTEGER,
    category_id INTEGER,
    PRIMARY KEY (article_id, category_id),
    FOREIGN KEY (article_id) REFERENCES article(id),
    FOREIGN KEY (category_id) REFERENCES category(id)
);

-- 评论表
CREATE TABLE IF NOT EXISTS comment (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    content TEXT NOT NULL,
    article_id INTEGER,
    user_id INTEGER,
    parent_id INTEGER,
    create_time BIGINT,
    FOREIGN KEY (article_id) REFERENCES article(id),
    FOREIGN KEY (user_id) REFERENCES user(id),
    FOREIGN KEY (parent_id) REFERENCES comment(id)
);

-- 初始化管理员账号 (密码: admin)
INSERT OR IGNORE INTO user (username, password, nickname, status, create_time, update_time)
VALUES ('admin', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iKTVKIUi', '管理员', 1, 
        strftime('%s','now') * 1000, strftime('%s','now') * 1000);