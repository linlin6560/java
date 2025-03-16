-- 清空文章表
DELETE FROM article;

-- 重置自增ID (SQLite语法)
DELETE FROM sqlite_sequence WHERE name = 'article';