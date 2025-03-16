-- 添加测试用户 (密码: 123456)
INSERT OR IGNORE INTO user (username, password, nickname, role, status, create_time, update_time) 
VALUES ('admin', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iKTVKIUi', '管理员', 'ADMIN', 1, strftime('%s','now') * 1000, strftime('%s','now') * 1000);