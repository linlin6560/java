-- 清空文章表
DELETE FROM article;

-- 重置自增ID
ALTER TABLE article AUTO_INCREMENT = 1;