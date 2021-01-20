/*
 Navicat Premium Data Transfer

 Source Server         : test1
 Source Server Type    : MySQL
 Source Server Version : 80020
 Source Host           : localhost:3306
 Source Schema         : wuzhi

 Target Server Type    : MySQL
 Target Server Version : 80020
 File Encoding         : 65001

 Date: 11/12/2020 09:23:04
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for admin_info
-- ----------------------------
DROP TABLE IF EXISTS `admin_info`;
CREATE TABLE `admin_info`  (
  `id` int(0) NOT NULL,
  INDEX `id`(`id`) USING BTREE,
  CONSTRAINT `admin_info_ibfk_1` FOREIGN KEY (`id`) REFERENCES `user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of admin_info
-- ----------------------------

-- ----------------------------
-- Table structure for clo_img
-- ----------------------------
DROP TABLE IF EXISTS `clo_img`;
CREATE TABLE `clo_img`  (
  `id` int(0) NOT NULL AUTO_INCREMENT COMMENT '图片ID',
  `clo_type_id` int(0) NULL DEFAULT NULL COMMENT '这个图片属于的服装ID',
  `img_url` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '图片的路径',
  `img_type` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '图片的类型（首页缩略图、介绍页图',
  `store_id` int(0) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 25 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of clo_img
-- ----------------------------
INSERT INTO `clo_img` VALUES (19, 1, 'icon\\cloth001.jpg', 'icon', 22226);
INSERT INTO `clo_img` VALUES (20, 2, 'icon\\cloth04.jpg', 'icon', 22226);
INSERT INTO `clo_img` VALUES (21, 3, 'icon\\MG-1-1.jpg', 'icon', 22226);
INSERT INTO `clo_img` VALUES (22, 4, 'icon\\MG-1-10.jpg', 'icon', 22226);
INSERT INTO `clo_img` VALUES (23, 4, 'icon\\MG-1-10.jpg', 'icon', 22226);
INSERT INTO `clo_img` VALUES (24, 55, 'icon\\MG-1-5.jpg', 'icon', 22226);
INSERT INTO `clo_img` VALUES (25, 2222, 'icon\\MG-1-9.jpg', 'icon', 22226);

-- ----------------------------
-- Table structure for clo_type
-- ----------------------------
DROP TABLE IF EXISTS `clo_type`;
CREATE TABLE `clo_type`  (
  `id` int(0) NOT NULL COMMENT '衣服型号ID',
  `type` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '衣服的类型',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of clo_type
-- ----------------------------
INSERT INTO `clo_type` VALUES (1, NULL);
INSERT INTO `clo_type` VALUES (2, NULL);
INSERT INTO `clo_type` VALUES (3, NULL);
INSERT INTO `clo_type` VALUES (4, NULL);
INSERT INTO `clo_type` VALUES (55, NULL);
INSERT INTO `clo_type` VALUES (2222, NULL);

-- ----------------------------
-- Table structure for clothing
-- ----------------------------
DROP TABLE IF EXISTS `clothing`;
CREATE TABLE `clothing`  (
  `id` int(0) NOT NULL AUTO_INCREMENT COMMENT '服装id',
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '服装名称',
  `state` char(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '服装状态（在库，租赁，出售',
  `deposit` char(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '单件押金',
  `rent` int(0) NULL DEFAULT NULL COMMENT '单件单日租金',
  `cost` int(0) NULL DEFAULT NULL COMMENT '单件售价',
  `store_id` int(0) NULL DEFAULT NULL,
  `clo_type_id` int(0) NULL DEFAULT NULL,
  `sell_list_id` int(0) NULL DEFAULT NULL,
  `rent_list_id` int(0) NULL DEFAULT NULL,
  `img_url` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `store_id`(`store_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 116 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of clothing
-- ----------------------------
INSERT INTO `clothing` VALUES (74, '商户1的服装1', '已出售', '1000', 10, 1000, 22226, 1, 30, NULL, 'icon\\cloth001.jpg');
INSERT INTO `clothing` VALUES (75, '商户1的服装1', '已出售', '1000', 10, 1000, 22226, 1, 30, NULL, 'icon\\cloth001.jpg');
INSERT INTO `clothing` VALUES (76, '商户1的服装1', '已出售', '1000', 10, 1000, 22226, 1, 31, NULL, 'icon\\cloth001.jpg');
INSERT INTO `clothing` VALUES (77, '商户1的服装1', '已出售', '1000', 10, 1000, 22226, 1, 32, NULL, 'icon\\cloth001.jpg');
INSERT INTO `clothing` VALUES (78, '商户1的服装1', '已出售', '1000', 10, 1000, 22226, 1, 38, NULL, 'icon\\cloth001.jpg');
INSERT INTO `clothing` VALUES (79, '商户1的服装1', '出租', '1000', 10, 1000, 22226, 1, NULL, 15, 'icon\\cloth001.jpg');
INSERT INTO `clothing` VALUES (80, '商户1的服装1', '在库', '1000', 10, 1000, 22226, 1, NULL, NULL, 'icon\\cloth001.jpg');
INSERT INTO `clothing` VALUES (81, '商户1的服装1', '在库', '1000', 10, 1000, 22226, 1, NULL, NULL, 'icon\\cloth001.jpg');
INSERT INTO `clothing` VALUES (82, '商户1的服装1', '在库', '1000', 10, 1000, 22226, 1, NULL, NULL, 'icon\\cloth001.jpg');
INSERT INTO `clothing` VALUES (83, '商户1的服装1', '在库', '1000', 10, 1000, 22226, 1, NULL, NULL, 'icon\\cloth001.jpg');
INSERT INTO `clothing` VALUES (84, '商户1的服装1', '在库', '1000', 10, 1000, 22226, 1, NULL, NULL, 'icon\\cloth001.jpg');
INSERT INTO `clothing` VALUES (85, '商户1的服装1', '在库', '1000', 10, 1000, 22226, 1, NULL, NULL, 'icon\\cloth001.jpg');
INSERT INTO `clothing` VALUES (86, '商户1的服装1', '在库', '1000', 10, 1000, 22226, 1, NULL, NULL, 'icon\\cloth001.jpg');
INSERT INTO `clothing` VALUES (87, '商户1的服装1', '在库', '1000', 10, 1000, 22226, 1, NULL, NULL, 'icon\\cloth001.jpg');
INSERT INTO `clothing` VALUES (88, '商户1的服装1', '在库', '1000', 10, 1000, 22226, 1, NULL, NULL, 'icon\\cloth001.jpg');
INSERT INTO `clothing` VALUES (89, '商户1的服装1', '在库', '1000', 10, 1000, 22226, 1, NULL, NULL, 'icon\\cloth001.jpg');
INSERT INTO `clothing` VALUES (90, '商户1的服装1', '在库', '1000', 10, 1000, 22226, 1, NULL, NULL, 'icon\\cloth001.jpg');
INSERT INTO `clothing` VALUES (91, '商户1的服装1', '在库', '1000', 10, 1000, 22226, 1, NULL, NULL, 'icon\\cloth001.jpg');
INSERT INTO `clothing` VALUES (92, '商户1的服装1', '在库', '1000', 10, 1000, 22226, 1, NULL, NULL, 'icon\\cloth001.jpg');
INSERT INTO `clothing` VALUES (93, '商户1的服装1', '在库', '1000', 10, 1000, 22226, 1, NULL, NULL, 'icon\\cloth001.jpg');
INSERT INTO `clothing` VALUES (94, '商户1的服装2', '已出售', '200', 2, 200, 22226, 2, 29, NULL, 'icon\\cloth04.jpg');
INSERT INTO `clothing` VALUES (95, '商户1的服装2', '已出售', '200', 2, 200, 22226, 2, 29, NULL, 'icon\\cloth04.jpg');
INSERT INTO `clothing` VALUES (96, '商户1的服装2', '出租', '200', 2, 200, 22226, 2, NULL, 12, 'icon\\cloth04.jpg');
INSERT INTO `clothing` VALUES (97, '商户1的服装2', '出租', '200', 2, 200, 22226, 2, NULL, 12, 'icon\\cloth04.jpg');
INSERT INTO `clothing` VALUES (98, '商户1的服装2', '已出售', '200', 2, 200, 22226, 2, 34, NULL, 'icon\\cloth04.jpg');
INSERT INTO `clothing` VALUES (99, '商户1的服装2', '已出售', '200', 2, 200, 22226, 2, 34, NULL, 'icon\\cloth04.jpg');
INSERT INTO `clothing` VALUES (100, '商户1的服装2', '出租', '200', 2, 200, 22226, 2, NULL, 13, 'icon\\cloth04.jpg');
INSERT INTO `clothing` VALUES (101, '商户1的服装2', '在库', '200', 2, 200, 22226, 2, NULL, NULL, 'icon\\cloth04.jpg');
INSERT INTO `clothing` VALUES (102, '商户1的服装3', '已出售', '20', 1, 20, 22226, 3, 36, NULL, 'icon\\MG-1-1.jpg');
INSERT INTO `clothing` VALUES (103, '商户1的服装3', '已出售', '20', 1, 20, 22226, 3, 36, NULL, 'icon\\MG-1-1.jpg');
INSERT INTO `clothing` VALUES (104, '商户1的服装3', '出租', '20', 1, 20, 22226, 3, NULL, 16, 'icon\\MG-1-1.jpg');
INSERT INTO `clothing` VALUES (105, '商户1的服装3', '在库', '20', 1, 20, 22226, 3, NULL, NULL, 'icon\\MG-1-1.jpg');
INSERT INTO `clothing` VALUES (106, '商户1的服装3', '在库', '20', 1, 20, 22226, 3, NULL, NULL, 'icon\\MG-1-1.jpg');
INSERT INTO `clothing` VALUES (107, '商户1的服装3', '在库', '20', 1, 20, 22226, 3, NULL, NULL, 'icon\\MG-1-1.jpg');
INSERT INTO `clothing` VALUES (108, '商户1的服装3', '在库', '20', 1, 20, 22226, 3, NULL, NULL, 'icon\\MG-1-1.jpg');
INSERT INTO `clothing` VALUES (109, '商户1的服装3', '在库', '20', 1, 20, 22226, 3, NULL, NULL, 'icon\\MG-1-1.jpg');
INSERT INTO `clothing` VALUES (110, '商户1的服装3', '在库', '20', 1, 20, 22226, 3, NULL, NULL, 'icon\\MG-1-1.jpg');
INSERT INTO `clothing` VALUES (111, '商户1的服装3', '在库', '20', 1, 20, 22226, 3, NULL, NULL, 'icon\\MG-1-1.jpg');
INSERT INTO `clothing` VALUES (112, '商户1的衣服4', '已出售', '2333', 23, 2333, 22226, 4, 37, NULL, 'icon\\MG-1-10.jpg');
INSERT INTO `clothing` VALUES (113, '商户1的衣服4', '已出售', '2333', 23, 2333, 22226, 4, 37, NULL, 'icon\\MG-1-10.jpg');
INSERT INTO `clothing` VALUES (114, '商户1的衣服4', '在库', '2333', 23, 2333, 22226, 4, NULL, NULL, 'icon\\MG-1-10.jpg');
INSERT INTO `clothing` VALUES (115, '商户1的衣服4', '在库', '2333', 23, 2333, 22226, 4, NULL, NULL, 'icon\\MG-1-10.jpg');
INSERT INTO `clothing` VALUES (116, 'eixljid', '已出售', '1000', 10, 1000, 22226, 55, 39, NULL, 'icon\\MG-1-5.jpg');
INSERT INTO `clothing` VALUES (117, 'eixljid', '已出售', '1000', 10, 1000, 22226, 55, 40, NULL, 'icon\\MG-1-5.jpg');
INSERT INTO `clothing` VALUES (118, 'eixljid', '已出售', '1000', 10, 1000, 22226, 55, 40, NULL, 'icon\\MG-1-5.jpg');
INSERT INTO `clothing` VALUES (119, 'eixljid', '在库', '1000', 10, 1000, 22226, 55, NULL, NULL, 'icon\\MG-1-5.jpg');
INSERT INTO `clothing` VALUES (120, 'eixljid', '在库', '1000', 10, 1000, 22226, 55, NULL, NULL, 'icon\\MG-1-5.jpg');
INSERT INTO `clothing` VALUES (121, '2222', '在库', '222', 2, 222, 22226, 2222, NULL, NULL, 'icon\\MG-1-9.jpg');
INSERT INTO `clothing` VALUES (122, '2222', '在库', '222', 2, 222, 22226, 2222, NULL, NULL, 'icon\\MG-1-9.jpg');
INSERT INTO `clothing` VALUES (123, '2222', '在库', '222', 2, 222, 22226, 2222, NULL, NULL, 'icon\\MG-1-9.jpg');
INSERT INTO `clothing` VALUES (124, '2222', '在库', '222', 2, 222, 22226, 2222, NULL, NULL, 'icon\\MG-1-9.jpg');
INSERT INTO `clothing` VALUES (125, '2222', '在库', '222', 2, 222, 22226, 2222, NULL, NULL, 'icon\\MG-1-9.jpg');
INSERT INTO `clothing` VALUES (126, '2222', '在库', '222', 2, 222, 22226, 2222, NULL, NULL, 'icon\\MG-1-9.jpg');
INSERT INTO `clothing` VALUES (127, '2222', '在库', '222', 2, 222, 22226, 2222, NULL, NULL, 'icon\\MG-1-9.jpg');
INSERT INTO `clothing` VALUES (128, '2222', '在库', '222', 2, 222, 22226, 2222, NULL, NULL, 'icon\\MG-1-9.jpg');
INSERT INTO `clothing` VALUES (129, '2222', '在库', '222', 2, 222, 22226, 2222, NULL, NULL, 'icon\\MG-1-9.jpg');
INSERT INTO `clothing` VALUES (130, '2222', '在库', '222', 2, 222, 22226, 2222, NULL, NULL, 'icon\\MG-1-9.jpg');
INSERT INTO `clothing` VALUES (131, '2222', '在库', '222', 2, 222, 22226, 2222, NULL, NULL, 'icon\\MG-1-9.jpg');
INSERT INTO `clothing` VALUES (132, '2222', '在库', '222', 2, 222, 22226, 2222, NULL, NULL, 'icon\\MG-1-9.jpg');
INSERT INTO `clothing` VALUES (133, '2222', '在库', '222', 2, 222, 22226, 2222, NULL, NULL, 'icon\\MG-1-9.jpg');
INSERT INTO `clothing` VALUES (134, '2222', '在库', '222', 2, 222, 22226, 2222, NULL, NULL, 'icon\\MG-1-9.jpg');
INSERT INTO `clothing` VALUES (135, '2222', '在库', '222', 2, 222, 22226, 2222, NULL, NULL, 'icon\\MG-1-9.jpg');
INSERT INTO `clothing` VALUES (136, '2222', '在库', '222', 2, 222, 22226, 2222, NULL, NULL, 'icon\\MG-1-9.jpg');
INSERT INTO `clothing` VALUES (137, '2222', '在库', '222', 2, 222, 22226, 2222, NULL, NULL, 'icon\\MG-1-9.jpg');
INSERT INTO `clothing` VALUES (138, '2222', '在库', '222', 2, 222, 22226, 2222, NULL, NULL, 'icon\\MG-1-9.jpg');
INSERT INTO `clothing` VALUES (139, '2222', '在库', '222', 2, 222, 22226, 2222, NULL, NULL, 'icon\\MG-1-9.jpg');
INSERT INTO `clothing` VALUES (140, '2222', '在库', '222', 2, 222, 22226, 2222, NULL, NULL, 'icon\\MG-1-9.jpg');
INSERT INTO `clothing` VALUES (141, '2222', '在库', '222', 2, 222, 22226, 2222, NULL, NULL, 'icon\\MG-1-9.jpg');
INSERT INTO `clothing` VALUES (142, '2222', '在库', '222', 2, 222, 22226, 2222, NULL, NULL, 'icon\\MG-1-9.jpg');

-- ----------------------------
-- Table structure for customer_info
-- ----------------------------
DROP TABLE IF EXISTS `customer_info`;
CREATE TABLE `customer_info`  (
  `user_id` int(0) NOT NULL COMMENT '账号id',
  `address` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`user_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of customer_info
-- ----------------------------
INSERT INTO `customer_info` VALUES (22227, '北京');

-- ----------------------------
-- Table structure for log
-- ----------------------------
DROP TABLE IF EXISTS `log`;
CREATE TABLE `log`  (
  `id` int(0) NOT NULL,
  `path` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `time` date NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of log
-- ----------------------------

-- ----------------------------
-- Table structure for notice
-- ----------------------------
DROP TABLE IF EXISTS `notice`;
CREATE TABLE `notice`  (
  `id` int(0) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `time` datetime(0) NULL DEFAULT NULL COMMENT '编写时间',
  `title` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '题目',
  `text` varchar(800) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '正文',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 14 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of notice
-- ----------------------------
INSERT INTO `notice` VALUES (12, '2020-12-06 08:00:00', '商户1进新衣服啦', '商户1进新衣服啦商户1进新衣服啦商户1进新衣服啦商户1进新衣服啦商户1进新衣服啦商户1进新衣服啦商户1进新衣服啦商户1进新衣服啦商户1进新衣服啦');
INSERT INTO `notice` VALUES (13, '2020-12-10 08:00:00', '2222', '222222222222222');

-- ----------------------------
-- Table structure for record
-- ----------------------------
DROP TABLE IF EXISTS `record`;
CREATE TABLE `record`  (
  `id` int(0) NOT NULL,
  `time` datetime(0) NULL DEFAULT NULL,
  `user_id` int(0) NULL DEFAULT NULL,
  `clo_id` int(0) NULL DEFAULT NULL,
  `num` int(0) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of record
-- ----------------------------

-- ----------------------------
-- Table structure for rent_list
-- ----------------------------
DROP TABLE IF EXISTS `rent_list`;
CREATE TABLE `rent_list`  (
  `id` int(0) NOT NULL AUTO_INCREMENT COMMENT '租赁表',
  `custom_id` int(0) NOT NULL,
  `store_id` int(0) NOT NULL,
  `clo_type_id` int(0) NULL DEFAULT NULL,
  `num` int(0) NULL DEFAULT NULL,
  `deposit` int(0) NULL DEFAULT NULL,
  `rent` int(0) NULL DEFAULT NULL,
  `retime` datetime(0) NULL DEFAULT NULL,
  `state` char(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '未支付、已支付、已归还、逾期扣押金',
  `time` datetime(0) NULL DEFAULT NULL,
  `shipping_address` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '发货地址',
  `delivery_address` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '收货地址',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `custom_id`(`custom_id`) USING BTREE,
  INDEX `store_id`(`store_id`) USING BTREE,
  INDEX `clo_id`(`clo_type_id`) USING BTREE,
  INDEX `time`(`time`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 15 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of rent_list
-- ----------------------------
INSERT INTO `rent_list` VALUES (12, 22227, 22226, 2, 2, 400, 400, NULL, '未归还，已发货', '2020-12-09 16:49:25', '南京', '北京');
INSERT INTO `rent_list` VALUES (13, 22227, 22226, 2, 1, 200, 200, NULL, '未归还，未发货', '2020-12-10 08:53:37', '火星', '北京');
INSERT INTO `rent_list` VALUES (14, 22227, 22226, 3, 1, 0, 0, '2020-12-10 15:11:31', '已归还', '2020-12-10 15:10:56', '火星', '北京');
INSERT INTO `rent_list` VALUES (15, 22227, 22226, 1, 1, 1000, 1000, NULL, '未归还，未发货', '2020-12-10 21:48:29', '火星', '北京');
INSERT INTO `rent_list` VALUES (16, 22227, 22226, 3, 1, 20, 20, NULL, '未归还，未发货', '2020-12-10 21:48:58', '火星', '北京');

-- ----------------------------
-- Table structure for role
-- ----------------------------
DROP TABLE IF EXISTS `role`;
CREATE TABLE `role`  (
  `role_id` int(0) NOT NULL AUTO_INCREMENT COMMENT '角色id',
  `role_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '角色名称（客户、管理员、商户',
  `jurisdiction` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '权限',
  PRIMARY KEY (`role_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of role
-- ----------------------------

-- ----------------------------
-- Table structure for sell_list
-- ----------------------------
DROP TABLE IF EXISTS `sell_list`;
CREATE TABLE `sell_list`  (
  `id` int(0) NOT NULL AUTO_INCREMENT,
  `customer_id` int(0) NULL DEFAULT NULL,
  `store_id` int(0) NULL DEFAULT NULL,
  `clo_type_id` int(0) NULL DEFAULT NULL,
  `time` datetime(0) NULL DEFAULT NULL,
  `amoney` int(0) NULL DEFAULT NULL,
  `num` int(0) NULL DEFAULT NULL,
  `shipping_address` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '发货地址',
  `delivery_address` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '收货地址',
  `state` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '未支付，已支付',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `customer_id`(`customer_id`) USING BTREE,
  INDEX `store_id`(`store_id`) USING BTREE,
  INDEX `clo_id`(`clo_type_id`) USING BTREE,
  INDEX `time`(`time`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 37 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sell_list
-- ----------------------------
INSERT INTO `sell_list` VALUES (29, 22227, 22226, 2, '2020-12-09 16:49:00', 400, 2, '南京', '北京', '已发货');
INSERT INTO `sell_list` VALUES (30, 22227, 22226, 1, '2020-12-09 16:51:22', 2000, 2, '南京', '北京', '未发货');
INSERT INTO `sell_list` VALUES (31, 22227, 22226, 1, '2020-12-10 08:40:46', 1000, 1, '火星', '北京', '已发货');
INSERT INTO `sell_list` VALUES (32, 22227, 22226, 1, '2020-12-10 08:42:13', 1000, 1, '火星', '北京', '已发货');
INSERT INTO `sell_list` VALUES (33, 22227, 22226, 1, '2020-12-10 08:42:13', 1000, 1, '火星', '北京', '未发货');
INSERT INTO `sell_list` VALUES (34, 22227, 22226, 2, '2020-12-10 08:42:13', 400, 2, '火星', '北京', '已发货');
INSERT INTO `sell_list` VALUES (35, 22227, 22226, 2, '2020-12-10 08:42:13', 400, 2, '火星', '北京', '未发货');
INSERT INTO `sell_list` VALUES (36, 22227, 22226, 3, '2020-12-10 08:46:00', 40, 2, '火星', '北京', '未发货');
INSERT INTO `sell_list` VALUES (37, 22227, 22226, 4, '2020-12-10 08:46:00', 4666, 2, '火星', '北京', '未发货');
INSERT INTO `sell_list` VALUES (38, 22227, 22226, 1, '2020-12-10 15:10:04', 1000, 1, '火星', '北京', '未发货');
INSERT INTO `sell_list` VALUES (39, 22227, 22226, 55, '2020-12-10 15:10:04', 1000, 1, '火星', '北京', '已发货');
INSERT INTO `sell_list` VALUES (40, 22227, 22226, 55, '2020-12-10 21:49:58', 2000, 2, '火星', '北京', '未发货');

-- ----------------------------
-- Table structure for shopping_cart
-- ----------------------------
DROP TABLE IF EXISTS `shopping_cart`;
CREATE TABLE `shopping_cart`  (
  `id` int(0) NOT NULL AUTO_INCREMENT,
  `user_id` int(0) NULL DEFAULT NULL,
  `clo_type_id` int(0) NULL DEFAULT NULL,
  `num` int(0) NULL DEFAULT NULL,
  `store_id` int(0) NULL DEFAULT NULL,
  `cost` int(0) NULL DEFAULT NULL,
  `amoney` int(0) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 15 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of shopping_cart
-- ----------------------------

-- ----------------------------
-- Table structure for star_list
-- ----------------------------
DROP TABLE IF EXISTS `star_list`;
CREATE TABLE `star_list`  (
  `id` int(0) NOT NULL AUTO_INCREMENT,
  `user_id` int(0) NULL DEFAULT NULL,
  `clo_type_id` int(0) NULL DEFAULT NULL,
  `store_id` int(0) NULL DEFAULT NULL,
  `cost` int(0) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 7 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of star_list
-- ----------------------------
INSERT INTO `star_list` VALUES (5, 22227, 1, 22226, 1000);
INSERT INTO `star_list` VALUES (6, 22227, 55, 22226, 1000);

-- ----------------------------
-- Table structure for stock
-- ----------------------------
DROP TABLE IF EXISTS `stock`;
CREATE TABLE `stock`  (
  `id` int(0) NOT NULL AUTO_INCREMENT,
  `clo_type_id` int(0) NULL DEFAULT NULL,
  `num` int(0) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 17 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of stock
-- ----------------------------
INSERT INTO `stock` VALUES (12, 1, 14);
INSERT INTO `stock` VALUES (13, 2, 1);
INSERT INTO `stock` VALUES (14, 3, 7);
INSERT INTO `stock` VALUES (15, 4, 2);
INSERT INTO `stock` VALUES (16, 55, 2);
INSERT INTO `stock` VALUES (17, 2222, 22);

-- ----------------------------
-- Table structure for store_info
-- ----------------------------
DROP TABLE IF EXISTS `store_info`;
CREATE TABLE `store_info`  (
  `id` int(0) NOT NULL COMMENT '商户的账号id',
  `stock_id` int(0) NULL DEFAULT NULL,
  `address` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of store_info
-- ----------------------------
INSERT INTO `store_info` VALUES (22226, NULL, '火星');

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `id` int(0) NOT NULL AUTO_INCREMENT COMMENT '用户表Id',
  `password` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '密码',
  `nickname` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '昵称(商户账号将代表商店名',
  `role_id` int(0) NOT NULL COMMENT '角色id',
  `time` date NULL DEFAULT NULL COMMENT '开通时间（商户将代表开店时间',
  `email` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '邮箱',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `role_id`(`role_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 22230 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES (22226, '3255afb3ab188cabee0cd0fda109a02e', '商户一号', 2, '2020-12-09', '21@qq.com');
INSERT INTO `user` VALUES (22227, '3255afb3ab188cabee0cd0fda109a02e', '用户1', 1, '2020-12-09', '11@qq.com');
INSERT INTO `user` VALUES (22228, '4c473097d2393fec7b55a564a8536855', '测试测试', 1, '2020-12-10', '12@qq.com');
INSERT INTO `user` VALUES (22229, '3255afb3ab188cabee0cd0fda109a02e', 'sdwdwd', 1, '2020-12-10', '123@qq.com');

SET FOREIGN_KEY_CHECKS = 1;
