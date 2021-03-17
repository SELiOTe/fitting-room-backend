-- -------------------------------------------------------------------------
-- 数据库创建脚本
-- 版本: MariaDB 10.5.8
-- 用法: mariadb -uroot -p < create.sql
-- -------------------------------------------------------------------------

-- 创建数据库
CREATE DATABASE fitting_room CHARACTER SET 'utf8mb4' COLLATE 'utf8mb4_unicode_ci';

-- 创建数据库用户
CREATE USER 'x7pGz6D'@'%' IDENTIFIED BY 'sU3%^vr7UJtd3KyYm';

-- 配置数据库用户权限
GRANT INSERT, UPDATE, SELECT ON fitting_room.* TO 'x7pGz6D'@'%';
FLUSH PRIVILEGES;

-- 切换数据库
USE fitting_room;

-- 创建用户表
CREATE TABLE user
(
    id           BIGINT      NOT NULL AUTO_INCREMENT COMMENT 'row id',
    country_code VARCHAR(4)  NOT NULL COMMENT 'telephone number country calling code',
    tel_no       VARCHAR(15) NOT NULL COMMENT 'telephone number',
    nickname     VARCHAR(16) NOT NULL COMMENT 'user nickname',
    gender       TINYINT     NOT NULL DEFAULT 0 COMMENT 'user gender, 0 unknown, 1 male, 2 female',
    deleted      BOOLEAN     NOT NULL DEFAULT FALSE COMMENT 'row logic deleted',
    created_at   TIMESTAMP   NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT 'row create time',
    modified_at  TIMESTAMP   NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT 'row modify time',
    PRIMARY KEY pk_id (id) COMMENT 'row primary key'
) AUTO_INCREMENT = 10001
  ENGINE = InnoDB
  CHARACTER SET 'utf8mb4'
  COLLATE 'utf8mb4_unicode_ci'
    COMMENT 'user info';

-- 创建短信发送记录表
CREATE TABLE sms
(
    id           BIGINT      NOT NULL AUTO_INCREMENT COMMENT 'row id',
    country_code VARCHAR(4)  NOT NULL COMMENT 'telephone number country calling code',
    tel_no       VARCHAR(15) NOT NULL COMMENT 'telephone number',
    type         TINYINT     NOT NULL COMMENT 'sms type, 1 login',
    result       TINYINT     NOT NULL COMMENT 'send result, 0 success, 1 failed send by api',
    deleted      BOOLEAN     NOT NULL DEFAULT FALSE COMMENT 'row logic deleted',
    created_at   TIMESTAMP   NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT 'row create time',
    modified_at  TIMESTAMP   NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT 'row modify time',
    PRIMARY KEY pk_id (id) COMMENT 'row primary key'
) ENGINE = InnoDB
  CHARACTER SET 'utf8mb4'
  COLLATE 'utf8mb4_unicode_ci'
    COMMENT 'sms send log';

-- 创建用户头像表
CREATE TABLE user_avatar
(
    id          BIGINT    NOT NULL AUTO_INCREMENT COMMENT 'row id',
    user_id     BIGINT    NOT NULL COMMENT 'user id',
    avatar      CHAR(40)  NOT NULL COMMENT 'avatar file sha1, default value is sha1("default")',
    deleted     BOOLEAN   NOT NULL DEFAULT FALSE COMMENT 'row logic deleted',
    created_at  TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT 'row create time',
    modified_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT 'row modify time',
    PRIMARY KEY pk_id (id) COMMENT 'row primary key',
    FOREIGN KEY ua_fk_user_id (user_id) REFERENCES user (id)
) ENGINE = InnoDB
  CHARACTER SET 'utf8mb4'
  COLLATE 'utf8mb4_unicode_ci'
    COMMENT 'user avatar';

-- 创建用户地理位置表
CREATE TABLE user_position
(
    id          BIGINT    NOT NULL AUTO_INCREMENT COMMENT 'row id',
    user_id     BIGINT    NOT NULL COMMENT 'user id',
    longitude   DOUBLE    NOT NULL COMMENT 'longitude',
    latitude    DOUBLE    NOT NULL COMMENT 'latitude',
    deleted     BOOLEAN   NOT NULL DEFAULT FALSE COMMENT 'row logic deleted',
    created_at  TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT 'row create time',
    modified_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT 'row modify time',
    PRIMARY KEY pk_id (id) COMMENT 'row primary key',
    FOREIGN KEY up_fk_user_id (user_id) REFERENCES user (id)
) ENGINE = InnoDB
  CHARACTER SET 'utf8mb4'
  COLLATE 'utf8mb4_unicode_ci'
    COMMENT 'user position';

-- 创建商品表
CREATE TABLE goods
(
    id                BIGINT      NOT NULL AUTO_INCREMENT COMMENT 'row id',
    name              VARCHAR(60) NOT NULL COMMENT 'goods name',
    price             INT         NOT NULL COMMENT 'ex-factory price, unit is penny',
    list_price_method INT         NOT NULL COMMENT 'list price calculate method, 0 is normal',
    deleted           BOOLEAN     NOT NULL DEFAULT FALSE COMMENT 'row logic deleted',
    created_at        TIMESTAMP   NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT 'row create time',
    modified_at       TIMESTAMP   NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT 'row modify time',
    PRIMARY KEY pk_id (id) COMMENT 'row primary key'
) ENGINE = InnoDB
  CHARACTER SET 'utf8mb4'
  COLLATE 'utf8mb4_unicode_ci'
    COMMENT 'goods info';

-- 创建商品图片表
CREATE TABLE goods_image
(
    id          BIGINT    NOT NULL AUTO_INCREMENT COMMENT 'row id',
    goods_id    BIGINT    NOT NULL COMMENT 'goods id',
    image       CHAR(40)  NOT NULL COMMENT 'goods image, display at goods info',
    image_order INT       NOT NULL
        COMMENT 'goods image display order, can be negative number, same goods can not continuous',
    deleted     BOOLEAN   NOT NULL DEFAULT FALSE COMMENT 'row logic deleted',
    created_at  TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT 'row create time',
    modified_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT 'row modify time',
    PRIMARY KEY pk_id (id) COMMENT 'row primary key',
    FOREIGN KEY gi_fk_goods_id (goods_id) REFERENCES goods (id),
    UNIQUE KEY gi_uk_goods_id_image_order (goods_id, image_order)
) ENGINE = InnoDB
  CHARACTER SET 'utf8mb4'
  COLLATE 'utf8mb4_unicode_ci'
    COMMENT 'goods images, minimum order is main image';

-- 创建衣服颜色表
CREATE TABLE goods_color
(
    id          BIGINT      NOT NULL AUTO_INCREMENT COMMENT 'row id',
    color_desc  VARCHAR(10) NOT NULL COMMENT 'color description',
    deleted     BOOLEAN     NOT NULL DEFAULT FALSE COMMENT 'row logic deleted',
    created_at  TIMESTAMP   NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT 'row create time',
    modified_at TIMESTAMP   NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT 'row modify time',
    PRIMARY KEY pk_id (id) COMMENT 'row primary key'
) ENGINE = InnoDB
  CHARACTER SET 'utf8mb4'
  COLLATE 'utf8mb4_unicode_ci'
    COMMENT 'goods color set';

-- 初始化颜色表
INSERT INTO goods_color(color_desc)
VALUES ('white'),
       ('black'),
       ('red'),
       ('orange'),
       ('yellow'),
       ('green'),
       ('blue'),
       ('purple'),
       ('pink');

-- 创建衣服尺码表
CREATE TABLE goods_size
(
    id          BIGINT      NOT NULL AUTO_INCREMENT COMMENT 'row id',
    size_desc   VARCHAR(10) NOT NULL COMMENT 'size description',
    deleted     BOOLEAN     NOT NULL DEFAULT FALSE COMMENT 'row logic deleted',
    created_at  TIMESTAMP   NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT 'row create time',
    modified_at TIMESTAMP   NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT 'row modify time',
    PRIMARY KEY pk_id (id) COMMENT 'row primary key'
) ENGINE = InnoDB
  CHARACTER SET 'utf8mb4'
  COLLATE 'utf8mb4_unicode_ci'
    COMMENT 'goods size set';

-- 初始化衣服尺寸表
INSERT INTO goods_size (size_desc)
VALUES ('150'),
       ('155'),
       ('160'),
       ('165'),
       ('170'),
       ('175'),
       ('180'),
       ('185'),
       ('190'),
       ('195'),
       ('200');

-- 创建商品 SKU 表
-- FIXME 重构的时候这个表得重新设计
-- 不合理，不仅只有颜色、尺码这两个属性
-- 或者含有其他属性的商品就分为其他商品?
-- 表就叫属性? 实际属性自己填，后面再考虑吧
CREATE TABLE goods_sku
(
    id          BIGINT    NOT NULL AUTO_INCREMENT COMMENT 'row id',
    goods_id    BIGINT    NOT NULL COMMENT 'goods id',
    color_id    BIGINT    NOT NULL COMMENT 'goods size id',
    size_id     BIGINT    NOT NULL COMMENT 'goods color id',
    stock       INT       NOT NULL COMMENT 'goods sku stock',
    deleted     BOOLEAN   NOT NULL DEFAULT FALSE COMMENT 'row logic deleted',
    created_at  TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT 'row create time',
    modified_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT 'row modify time',
    PRIMARY KEY pk_id (id) COMMENT 'row primary key',
    FOREIGN KEY gs_fk_goods_id (goods_id) REFERENCES goods (id),
    FOREIGN KEY gs_fk_color_id (color_id) REFERENCES goods_color (id),
    FOREIGN KEY gs_fk_size_id (size_id) REFERENCES goods_size (id),
    CONSTRAINT stock CHECK ( stock >= 0 )
) ENGINE = InnoDB
  CHARACTER SET 'utf8mb4'
  COLLATE 'utf8mb4_unicode_ci'
    COMMENT 'goods sku table, include color, size, stock';

-- 创建用户商品浏览记录表
CREATE TABLE goods_browse_record
(
    id          BIGINT    NOT NULL AUTO_INCREMENT COMMENT 'row id',
    user_id     BIGINT    NOT NULL COMMENT 'user id',
    goods_id    BIGINT    NOT NULL COMMENT 'goods id',
    jump_from   INT       NOT NULL COMMENT 'jump from where to browse goods',
    deleted     BOOLEAN   NOT NULL DEFAULT FALSE COMMENT 'row logic deleted',
    created_at  TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT 'row create time',
    modified_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT 'row modify time',
    PRIMARY KEY pk_id (id) COMMENT 'row primary key',
    FOREIGN KEY gbr_fk_user_id (user_id) REFERENCES user (id),
    FOREIGN KEY gbr_fk_goods_id (goods_id) REFERENCES goods (id)
) ENGINE = InnoDB
  CHARACTER SET 'utf8mb4'
  COLLATE 'utf8mb4_unicode_ci'
    COMMENT 'user browse goods records';

-- 创建评论表
CREATE TABLE goods_comment
(
    id           BIGINT        NOT NULL AUTO_INCREMENT COMMENT 'row id',
    user_id      BIGINT        NOT NULL COMMENT 'user id',
    goods_id     BIGINT        NOT NULL COMMENT 'goods id',
    comment_text VARCHAR(1024) NOT NULL COMMENT 'comment text',
    deleted      BOOLEAN       NOT NULL DEFAULT FALSE COMMENT 'row logic deleted',
    created_at   TIMESTAMP     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT 'row create time',
    modified_at  TIMESTAMP     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT 'row modify time',
    PRIMARY KEY pk_id (id) COMMENT 'row primary key',
    FOREIGN KEY gc_fk_user_id (user_id) REFERENCES user (id),
    FOREIGN KEY gc_fk_goods_id (goods_id) REFERENCES goods (id),
    UNIQUE KEY gc_uk_user_id_goods_id (user_id, goods_id)
) ENGINE = InnoDB
  CHARACTER SET 'utf8mb4'
  COLLATE 'utf8mb4_unicode_ci'
    COMMENT 'goods comment';

-- 创建评论图片表
CREATE TABLE goods_comment_image
(
    id          BIGINT    NOT NULL AUTO_INCREMENT COMMENT 'row id',
    comment_id  BIGINT    NOT NULL COMMENT 'comment id',
    image       CHAR(40)  NOT NULL COMMENT 'comment image',
    image_order INT       NOT NULL
        COMMENT 'comment image display order, can be negative number, same comment can not continuous',
    deleted     BOOLEAN   NOT NULL DEFAULT FALSE COMMENT 'row logic deleted',
    created_at  TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT 'row create time',
    modified_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT 'row modify time',
    PRIMARY KEY pk_id (id) COMMENT 'row primary key',
    FOREIGN KEY gci_fk_comment_id (comment_id) REFERENCES goods_comment (id),
    UNIQUE KEY gci_uk_comment_id_image_order (comment_id, image_order)
) ENGINE = InnoDB
  CHARACTER SET 'utf8mb4'
  COLLATE 'utf8mb4_unicode_ci'
    COMMENT 'comment images, minimum order is main image';

-- 创建购物车表
CREATE TABLE goods_cart
(

    id          BIGINT    NOT NULL AUTO_INCREMENT COMMENT 'row id',
    user_id     BIGINT    NOT NULL COMMENT 'user id',
    goods_id    BIGINT    NOT NULL COMMENT 'goods id',
    sku_id      BIGINT    NOT NULL COMMENT 'goods sku id',
    deleted     BOOLEAN   NOT NULL DEFAULT FALSE COMMENT 'row logic deleted',
    created_at  TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT 'row create time',
    modified_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT 'row modify time',
    PRIMARY KEY pk_id (id) COMMENT 'row primary key',
    FOREIGN KEY gca_fk_user_id (user_id) REFERENCES user (id),
    FOREIGN KEY gca_fk_goods_id (goods_id) REFERENCES goods (id),
    FOREIGN KEY gca_fk_sku_id (sku_id) REFERENCES goods_sku (id)
) ENGINE = InnoDB
  CHARACTER SET 'utf8mb4'
  COLLATE 'utf8mb4_unicode_ci'
    COMMENT 'goods cart';

