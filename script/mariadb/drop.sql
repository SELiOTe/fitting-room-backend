-- -------------------------------------------------------------------------
-- 数据库清除脚本
-- 版本: MariaDB 10.5.8
-- 用法: mariadb -uroot -p < drop.sql
-- -------------------------------------------------------------------------

-- 清除数据库
DROP DATABASE IF EXISTS fitting_room;

-- 清除用户
DROP USER IF EXISTS 'x7pGz6D'@'%';
FLUSH PRIVILEGES;
