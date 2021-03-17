-- -------------------------------------------------------------------------
-- 数据库测试数据脚本
-- 版本: MariaDB 10.5.8
-- 用法: mariadb -uroot -p < drop.sql
-- 所用数据: bash test.sql ${PATH}
-- -------------------------------------------------------------------------

USE fitting_room;

INSERT INTO user(country_code, tel_no, nickname)
VALUES ('86', '11111111111', '僵尸 1 号'),
       ('86', '11111111112', '僵尸 2 号'),
       ('86', '11111111113', '僵尸 3 号'),
       ('86', '11111111114', '僵尸 4 号'),
       ('86', '11111111115', '僵尸 5 号'),
       ('86', '11111111116', '僵尸 6 号'),
       ('86', '11111111117', '僵尸 7 号'),
       ('86', '11111111118', '僵尸 8 号'),
       ('86', '11111111119', '僵尸 9 号'),
       ('86', '11111111120', '僵尸 10 号'),
       ('86', '11111111121', '僵尸 11 号'),
       ('86', '11111111122', '僵尸 12 号');

INSERT INTO goods(name, price, list_price_method)
VALUES ('[Skasee]男休闲夹克', 24800, 0);

INSERT INTO goods_image(goods_id, image, image_order)
VALUES (1, '1000430c999d8f78e712252f4183ca2d6d1e0e2d', 0),
       (1, '0ba47411bc0ec68d2e7003d8105f9361128deec8', 1),
       (1, '77f2ea9ab2159967f2d1e91aa8423098e32e1c10', 2),
       (1, 'ec20787ea0c64150a125833c42c2f24151ed1adb', 3),
       (1, 'f0042aa8add9ebdbbf1a9b27243cc454598a08dc', 4);

INSERT INTO goods_sku(goods_id, color_id, size_id, stock)
VALUES (1, 2, 4, 19),
       (1, 2, 5, 32),
       (1, 2, 6, 11),
       (1, 2, 7, 3),
       (1, 6, 5, 0),
       (1, 6, 6, 17),
       (1, 4, 5, 39),
       (1, 4, 6, 25),
       (1, 7, 5, 7),
       (1, 7, 6, 0);

INSERT INTO goods_comment(user_id, goods_id, comment_text)
VALUES (10001, 1, '神说要有评论，于是就有了评论'),
       (10002, 1, '神说要有评论，于是就有了评论'),
       (10003, 1, '神说要有评论，于是就有了评论'),
       (10004, 1, '神说要有评论，于是就有了评论'),
       (10005, 1, '神说要有评论，于是就有了评论'),
       (10006, 1, '神说要有评论，于是就有了评论，神还说这个得带九张图'),
       (10007, 1, '神说要有评论，于是就有了评论'),
       (10008, 1, '神说要有评论，于是就有了评论'),
       (10009, 1, '神说要有评论，于是就有了评论'),
       (10010, 1, '神说要有评论，于是就有了评论'),
       (10011, 1, '神说要有评论，于是就有了评论'),
       (10012, 1, '僵尸 12 号说要有评论，于是就有了评论，这个带两张，不是神说的，我说的');

INSERT INTO goods_comment_image(comment_id, image, image_order)
VALUES (6, 'fdcbe2f6fb9c22c25ee9023840078cc9f74a9c4d', 0),
       (6, '6b44634f408a6c2bcfd59c93a2052b2ed19a52d8', 1),
       (6, 'bc92f1e73609d6b196dae812ffde912b61b4482b', 2),
       (6, '8f6cd9146f1b178e3ef0c88f4f4f1db2246e8351', 3),
       (6, '5bcdf8b75b53a4399d32a55808cf36d5a265cbcf', 4),
       (6, 'a4d2700149ca6b84a5644becbb54ae59f9a19d19', 5),
       (6, '04253b1f24c2d1dd7f3987860a925e4913c7fb2b', 6),
       (6, 'ab24d0a3c1a04359e377acd87befe142a96cc665', 7),
       (6, '4133347dd785e7960e3b0ef17fb964105ba8a023', 8);

INSERT INTO goods_comment_image(comment_id, image, image_order)
VALUES (12, '8f6f88631c42c9bde801bdffb27de4422368a9e8', 7),
       (12, '7020da2185a765482f69b8b89b51b6b1868b9c11', 8);