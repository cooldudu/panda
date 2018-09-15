/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50720
Source Host           : localhost:3306
Source Database       : pandanew

Target Server Type    : MYSQL
Target Server Version : 50720
File Encoding         : 65001

Date: 2018-09-15 12:52:51
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for t_accounts
-- ----------------------------
DROP TABLE IF EXISTS `t_accounts`;
CREATE TABLE `t_accounts` (
  `isAccoumtNonExpired` bit(1) DEFAULT NULL,
  `isAccountNonLocked` bit(1) DEFAULT NULL,
  `isCredentialsNonExpired` bit(1) DEFAULT NULL,
  `enabled` bit(1) DEFAULT NULL,
  `userName` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `id` int(11) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;

-- ----------------------------
-- Records of t_accounts
-- ----------------------------
INSERT INTO `t_accounts` VALUES ('', '', '', '', 'admin', '$2a$10$PUIYRsOI7hhi9IIoQDAiZe7LK8e7uqLSsxLPm3XO9/FMHKAslD4Mi', '1');
INSERT INTO `t_accounts` VALUES ('', '', '', '', 'user1', '$2a$10$PUIYRsOI7hhi9IIoQDAiZe7LK8e7uqLSsxLPm3XO9/FMHKAslD4Mi', '2');

-- ----------------------------
-- Table structure for t_authorities
-- ----------------------------
DROP TABLE IF EXISTS `t_authorities`;
CREATE TABLE `t_authorities` (
  `createDate` datetime DEFAULT NULL,
  `modifyDate` datetime DEFAULT NULL,
  `account_id` int(11) DEFAULT NULL,
  `role_id` int(11) DEFAULT NULL,
  `id` int(11) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;

-- ----------------------------
-- Records of t_authorities
-- ----------------------------
INSERT INTO `t_authorities` VALUES ('2018-06-23 00:00:00', '2018-06-23 00:00:00', '1', '1', '1');
INSERT INTO `t_authorities` VALUES ('2018-06-23 00:00:00', '2018-06-23 00:00:00', '1', '2', '2');
INSERT INTO `t_authorities` VALUES ('2018-06-23 00:00:00', '2018-06-23 00:00:00', '2', '2', '3');

-- ----------------------------
-- Table structure for t_buttons
-- ----------------------------
DROP TABLE IF EXISTS `t_buttons`;
CREATE TABLE `t_buttons` (
  `name` varchar(200) NOT NULL,
  `url` varchar(255) NOT NULL,
  `menuUid` varchar(200) NOT NULL,
  `id` int(11) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;

-- ----------------------------
-- Records of t_buttons
-- ----------------------------
INSERT INTO `t_buttons` VALUES ('新建', '/1', 'fbda2586-4cff-c716-1620-bd316b938d31', '9');
INSERT INTO `t_buttons` VALUES ('修改', '/2', 'fbda2586-4cff-c716-1620-bd316b938d31', '10');
INSERT INTO `t_buttons` VALUES ('删除', '/3', 'fbda2586-4cff-c716-1620-bd316b938d31', '11');
INSERT INTO `t_buttons` VALUES ('新建', '/4', '3831e8dc-a14d-d8f5-4657-c3049b412427', '12');
INSERT INTO `t_buttons` VALUES ('修改', '/5', '3831e8dc-a14d-d8f5-4657-c3049b412427', '13');
INSERT INTO `t_buttons` VALUES ('删除', '/6', '3831e8dc-a14d-d8f5-4657-c3049b412427', '14');

-- ----------------------------
-- Table structure for t_companies
-- ----------------------------
DROP TABLE IF EXISTS `t_companies`;
CREATE TABLE `t_companies` (
  `name` varchar(200) NOT NULL,
  `descript` text NOT NULL,
  `logo` varchar(255) DEFAULT NULL,
  `id` int(11) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;

-- ----------------------------
-- Records of t_companies
-- ----------------------------
INSERT INTO `t_companies` VALUES ('郑州迪维乐普', '郑州迪维乐普有限公司，中国联通河南研发基地', '/console/upload/pic/logo/Chrysanthemum.jpg', '1');
INSERT INTO `t_companies` VALUES ('啊啊啊', '巴巴爸爸', '/console/upload/pic/logo/Jellyfish.jpg', '5');

-- ----------------------------
-- Table structure for t_depts
-- ----------------------------
DROP TABLE IF EXISTS `t_depts`;
CREATE TABLE `t_depts` (
  `name` varchar(200) NOT NULL,
  `level` varchar(200) NOT NULL,
  `descript` text NOT NULL,
  `companyId` int(11) NOT NULL,
  `parentId` int(11) DEFAULT NULL,
  `orderNum` int(11) NOT NULL,
  `id` int(11) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;

-- ----------------------------
-- Records of t_depts
-- ----------------------------
INSERT INTO `t_depts` VALUES ('总经办', '0001', '总经办', '1', null, '1000', '1');
INSERT INTO `t_depts` VALUES ('技术部', '0002', '技术部', '1', null, '1001', '2');
INSERT INTO `t_depts` VALUES ('销售部', '00010001', '销售部', '1', '1', '1000', '3');
INSERT INTO `t_depts` VALUES ('开发部', '00020001', '开发部', '1', '2', '1003', '4');
INSERT INTO `t_depts` VALUES ('Java部', '00020002', 'Java部', '1', '2', '10000', '5');

-- ----------------------------
-- Table structure for t_diagrams
-- ----------------------------
DROP TABLE IF EXISTS `t_diagrams`;
CREATE TABLE `t_diagrams` (
  `entityType` text NOT NULL,
  `content` longtext NOT NULL,
  `entityId` int(11) DEFAULT NULL,
  `id` int(11) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;

-- ----------------------------
-- Records of t_diagrams
-- ----------------------------
INSERT INTO `t_diagrams` VALUES ('menu', '[  {    \"type\": \"draw2d.shape.state.State\",    \"id\": \"root\",    \"x\": 0,    \"y\": 280,    \"width\": 92,    \"height\": 61,    \"alpha\": 1,    \"angle\": 0,    \"userData\": {},    \"cssClass\": \"draw2d_shape_state_State\",    \"ports\": [      {        \"type\": \"draw2d.HybridPort\",        \"id\": \"6c43d0f0-9775-ddbd-b8e1-ebea6fec93cd\",        \"width\": 10,        \"height\": 10,        \"alpha\": 1,        \"angle\": 0,        \"userData\": {},        \"cssClass\": \"draw2d_HybridPort\",        \"bgColor\": \"#4F6870\",        \"color\": \"#1B1B1B\",        \"stroke\": 1,        \"dasharray\": null,        \"maxFanOut\": 9007199254740991,        \"name\": \"hybrid0\",        \"port\": \"draw2d.HybridPort\",        \"locator\": \"draw2d.layout.locator.BottomLocator\"      }    ],    \"bgColor\": \"#F3F3F3\",    \"color\": \"#E0E0E0\",    \"stroke\": 1,    \"radius\": 5,    \"dasharray\": null,    \"gap\": 0,    \"label\": \"系统根菜单\"  },  {    \"type\": \"example.shape.Activity\",    \"id\": \"d68511fe-bd9b-41a8-daf3-25654b8de2b8\",    \"x\": 200,    \"y\": 180,    \"width\": 92,    \"height\": 55,    \"alpha\": 1,    \"angle\": 0,    \"userData\": {      \"activity\": \"目录\",      \"mapping\": [        {          \"parameterName\": \"图标\",          \"value\": \"fa fa-desktop \"        },        {          \"parameterName\": \"排序\",          \"value\": \"1\"        }      ]    },    \"cssClass\": \"activity\",    \"ports\": [      {        \"type\": \"draw2d.HybridPort\",        \"id\": \"b220fb79-81c0-58d7-72e4-919c4e63f4d8\",        \"width\": 10,        \"height\": 10,        \"alpha\": 1,        \"angle\": 0,        \"userData\": {},        \"cssClass\": \"draw2d_HybridPort\",        \"bgColor\": \"#4F6870\",        \"color\": \"#1B1B1B\",        \"stroke\": 1,        \"dasharray\": null,        \"maxFanOut\": 9007199254740991,        \"name\": \"hybrid0\",        \"port\": \"draw2d.HybridPort\",        \"locator\": \"draw2d.layout.locator.BottomLocator\"      }    ],    \"bgColor\": \"#F3F3F3\",    \"color\": \"#E0E0E0\",    \"stroke\": 1,    \"dasharray\": null,    \"gap\": 0,    \"label\": \"系统管理\"  },  {    \"type\": \"example.shape.Activity\",    \"id\": \"3831e8dc-a14d-d8f5-4657-c3049b412427\",    \"x\": 360,    \"y\": 180,    \"width\": 92,    \"height\": 55,    \"alpha\": 1,    \"angle\": 0,    \"userData\": {      \"activity\": \"菜单\",      \"mapping\": [        {          \"parameterName\": \"图标\",          \"value\": \"fa fa-sitemap \"        },        {          \"parameterName\": \"排序\",          \"value\": \"1\"        },        {          \"parameterName\": \"URL地址\",          \"value\": \"/console/menu\"        }      ]    },    \"cssClass\": \"activity\",    \"ports\": [      {        \"type\": \"draw2d.HybridPort\",        \"id\": \"670688c0-e6fb-cc31-02a6-54938aea0d04\",        \"width\": 10,        \"height\": 10,        \"alpha\": 1,        \"angle\": 0,        \"userData\": {},        \"cssClass\": \"draw2d_HybridPort\",        \"bgColor\": \"#4F6870\",        \"color\": \"#1B1B1B\",        \"stroke\": 1,        \"dasharray\": null,        \"maxFanOut\": 9007199254740991,        \"name\": \"hybrid0\",        \"port\": \"draw2d.HybridPort\",        \"locator\": \"draw2d.layout.locator.BottomLocator\"      }    ],    \"bgColor\": \"#F3F3F3\",    \"color\": \"#E0E0E0\",    \"stroke\": 1,    \"dasharray\": null,    \"gap\": 0,    \"label\": \"菜单管理\"  },  {    \"type\": \"example.shape.Activity\",    \"id\": \"b45559e2-e708-b55c-3301-659040e42028\",    \"x\": 200,    \"y\": 360,    \"width\": 92,    \"height\": 55,    \"alpha\": 1,    \"angle\": 0,    \"userData\": {      \"activity\": \"目录\",      \"mapping\": [        {          \"parameterName\": \"图标\",          \"value\": \"fa fa-shopping-bag \"        },        {          \"parameterName\": \"排序\",          \"value\": \"2\"        }      ]    },    \"cssClass\": \"activity\",    \"ports\": [      {        \"type\": \"draw2d.HybridPort\",        \"id\": \"8d0b94b9-42eb-a7d5-7cae-e33554ba3a45\",        \"width\": 10,        \"height\": 10,        \"alpha\": 1,        \"angle\": 0,        \"userData\": {},        \"cssClass\": \"draw2d_HybridPort\",        \"bgColor\": \"#4F6870\",        \"color\": \"#1B1B1B\",        \"stroke\": 1,        \"dasharray\": null,        \"maxFanOut\": 9007199254740991,        \"name\": \"hybrid0\",        \"port\": \"draw2d.HybridPort\",        \"locator\": \"draw2d.layout.locator.BottomLocator\"      }    ],    \"bgColor\": \"#F3F3F3\",    \"color\": \"#E0E0E0\",    \"stroke\": 1,    \"dasharray\": null,    \"gap\": 0,    \"label\": \"项目管理\"  },  {    \"type\": \"example.shape.Activity\",    \"id\": \"1168fc3e-6e24-3597-3877-7c3f1f354228\",    \"x\": 640,    \"y\": 380,    \"width\": 92,    \"height\": 55,    \"alpha\": 1,    \"angle\": 0,    \"userData\": {      \"activity\": \"菜单\",      \"mapping\": [        {          \"parameterName\": \"图标\",          \"value\": \"fa fa-send \"        },        {          \"parameterName\": \"排序\",          \"value\": \"1\"        },        {          \"parameterName\": \"URL地址\",          \"value\": \"/project/add\"        }      ]    },    \"cssClass\": \"activity\",    \"ports\": [      {        \"type\": \"draw2d.HybridPort\",        \"id\": \"e5a8bc00-6e89-c810-1d78-fbbcd8018668\",        \"width\": 10,        \"height\": 10,        \"alpha\": 1,        \"angle\": 0,        \"userData\": {},        \"cssClass\": \"draw2d_HybridPort\",        \"bgColor\": \"#4F6870\",        \"color\": \"#1B1B1B\",        \"stroke\": 1,        \"dasharray\": null,        \"maxFanOut\": 9007199254740991,        \"name\": \"hybrid0\",        \"port\": \"draw2d.HybridPort\",        \"locator\": \"draw2d.layout.locator.BottomLocator\"      }    ],    \"bgColor\": \"#F3F3F3\",    \"color\": \"#E0E0E0\",    \"stroke\": 1,    \"dasharray\": null,    \"gap\": 0,    \"label\": \"发起项目\"  },  {    \"type\": \"example.shape.Activity\",    \"id\": \"fbda2586-4cff-c716-1620-bd316b938d31\",    \"x\": 360,    \"y\": 100,    \"width\": 92,    \"height\": 55,    \"alpha\": 1,    \"angle\": 0,    \"userData\": {      \"activity\": \"菜单\",      \"mapping\": [        {          \"parameterName\": \"图标\",          \"value\": \"fa fa-users \"        },        {          \"parameterName\": \"排序\",          \"value\": \"2\"        },        {          \"parameterName\": \"URL地址\",          \"value\": \"/console/user\"        }      ]    },    \"cssClass\": \"activity\",    \"ports\": [      {        \"type\": \"draw2d.HybridPort\",        \"id\": \"50fca3f6-9c87-10f7-e09a-6e5f34213691\",        \"width\": 10,        \"height\": 10,        \"alpha\": 1,        \"angle\": 0,        \"userData\": {},        \"cssClass\": \"draw2d_HybridPort\",        \"bgColor\": \"#4F6870\",        \"color\": \"#1B1B1B\",        \"stroke\": 1,        \"dasharray\": null,        \"maxFanOut\": 9007199254740991,        \"name\": \"hybrid0\",        \"port\": \"draw2d.HybridPort\",        \"locator\": \"draw2d.layout.locator.BottomLocator\"      }    ],    \"bgColor\": \"#F3F3F3\",    \"color\": \"#E0E0E0\",    \"stroke\": 1,    \"dasharray\": null,    \"gap\": 0,    \"label\": \"用户管理\"  },  {    \"type\": \"example.shape.Activity\",    \"id\": \"d9e3756b-36eb-7d34-ef98-a1cca6c858d8\",    \"x\": 360,    \"y\": 260,    \"width\": 92,    \"height\": 55,    \"alpha\": 1,    \"angle\": 0,    \"userData\": {      \"activity\": \"菜单\",      \"mapping\": [        {          \"parameterName\": \"图标\",          \"value\": \"fa fa-paw \"        },        {          \"parameterName\": \"排序\",          \"value\": \"3\"        },        {          \"parameterName\": \"URL地址\",          \"value\": \"/console/role\"        }      ]    },    \"cssClass\": \"activity\",    \"ports\": [      {        \"type\": \"draw2d.HybridPort\",        \"id\": \"783488a1-bb5b-1823-6e71-4c82c4b58362\",        \"width\": 10,        \"height\": 10,        \"alpha\": 1,        \"angle\": 0,        \"userData\": {},        \"cssClass\": \"draw2d_HybridPort\",        \"bgColor\": \"#4F6870\",        \"color\": \"#1B1B1B\",        \"stroke\": 1,        \"dasharray\": null,        \"maxFanOut\": 9007199254740991,        \"name\": \"hybrid0\",        \"port\": \"draw2d.HybridPort\",        \"locator\": \"draw2d.layout.locator.BottomLocator\"      }    ],    \"bgColor\": \"#F3F3F3\",    \"color\": \"#E0E0E0\",    \"stroke\": 1,    \"dasharray\": null,    \"gap\": 0,    \"label\": \"角色管理\"  },  {    \"type\": \"example.shape.Activity\",    \"id\": \"291e3038-055f-104f-713e-ea159b3a332b\",    \"x\": 340,    \"y\": 380,    \"width\": 92,    \"height\": 55,    \"alpha\": 1,    \"angle\": 0,    \"userData\": {      \"activity\": \"目录\",      \"mapping\": [        {          \"parameterName\": \"图标\",          \"value\": \"fa fa-desktop \"        },        {          \"parameterName\": \"排序\",          \"value\": \"1\"        }      ]    },    \"cssClass\": \"activity\",    \"ports\": [      {        \"type\": \"draw2d.HybridPort\",        \"id\": \"0c2586a7-a86d-068a-6874-9e695e651bb6\",        \"width\": 10,        \"height\": 10,        \"alpha\": 1,        \"angle\": 0,        \"userData\": {},        \"cssClass\": \"draw2d_HybridPort\",        \"bgColor\": \"#4F6870\",        \"color\": \"#1B1B1B\",        \"stroke\": 1,        \"dasharray\": null,        \"maxFanOut\": 9007199254740991,        \"name\": \"hybrid0\",        \"port\": \"draw2d.HybridPort\",        \"locator\": \"draw2d.layout.locator.BottomLocator\"      }    ],    \"bgColor\": \"#F3F3F3\",    \"color\": \"#E0E0E0\",    \"stroke\": 1,    \"dasharray\": null,    \"gap\": 0,    \"label\": \"项目管理2\"  },  {    \"type\": \"example.shape.Activity\",    \"id\": \"5f9f1e31-2546-336c-5cb2-17eec91754b6\",    \"x\": 500,    \"y\": 380,    \"width\": 92,    \"height\": 55,    \"alpha\": 1,    \"angle\": 0,    \"userData\": {      \"activity\": \"目录\",      \"mapping\": [        {          \"parameterName\": \"图标\",          \"value\": \"fa fa-desktop \"        },        {          \"parameterName\": \"排序\",          \"value\": \"1\"        }      ]    },    \"cssClass\": \"activity\",    \"ports\": [      {        \"type\": \"draw2d.HybridPort\",        \"id\": \"86b8ca0a-bdea-d38d-06aa-3737c62d942a\",        \"width\": 10,        \"height\": 10,        \"alpha\": 1,        \"angle\": 0,        \"userData\": {},        \"cssClass\": \"draw2d_HybridPort\",        \"bgColor\": \"#4F6870\",        \"color\": \"#1B1B1B\",        \"stroke\": 1,        \"dasharray\": null,        \"maxFanOut\": 9007199254740991,        \"name\": \"hybrid0\",        \"port\": \"draw2d.HybridPort\",        \"locator\": \"draw2d.layout.locator.BottomLocator\"      }    ],    \"bgColor\": \"#F3F3F3\",    \"color\": \"#E0E0E0\",    \"stroke\": 1,    \"dasharray\": null,    \"gap\": 0,    \"label\": \"项目管理3\"  },  {    \"type\": \"example.shape.Activity\",    \"id\": \"4d6aaacc-7db1-d39c-3529-8fc3a956e03b\",    \"x\": 360,    \"y\": 20,    \"width\": 92,    \"height\": 55,    \"alpha\": 1,    \"angle\": 0,    \"userData\": {      \"activity\": \"菜单\",      \"mapping\": [        {          \"parameterName\": \"图标\",          \"value\": \"fa fa-sitemap \"        },        {          \"parameterName\": \"排序\",          \"value\": \"4\"        },        {          \"parameterName\": \"URL地址\",          \"value\": \"/console/company\"        }      ]    },    \"cssClass\": \"activity\",    \"ports\": [      {        \"type\": \"draw2d.HybridPort\",        \"id\": \"20a40d99-8339-eca3-eb6f-893ad90b312f\",        \"width\": 10,        \"height\": 10,        \"alpha\": 1,        \"angle\": 0,        \"userData\": {},        \"cssClass\": \"draw2d_HybridPort\",        \"bgColor\": \"#4F6870\",        \"color\": \"#1B1B1B\",        \"stroke\": 1,        \"dasharray\": null,        \"maxFanOut\": 9007199254740991,        \"name\": \"hybrid0\",        \"port\": \"draw2d.HybridPort\",        \"locator\": \"draw2d.layout.locator.BottomLocator\"      }    ],    \"bgColor\": \"#F3F3F3\",    \"color\": \"#E0E0E0\",    \"stroke\": 1,    \"dasharray\": null,    \"gap\": 0,    \"label\": \"组织部门\"  },  {    \"type\": \"draw2d.Connection\",    \"id\": \"162f5863-c3f8-8f71-a15e-f98b299f5f6c\",    \"alpha\": 1,    \"angle\": 0,    \"userData\": {},    \"cssClass\": \"draw2d_Connection\",    \"stroke\": 3,    \"color\": \"#00A8F0\",    \"outlineStroke\": 1,    \"outlineColor\": \"#303030\",    \"policy\": \"draw2d.policy.line.LineSelectionFeedbackPolicy\",    \"vertex\": [      {        \"x\": 273.6755485893417,        \"y\": 179      },      {        \"x\": 377.67445482866043,        \"y\": 75      }    ],    \"router\": \"draw2d.layout.connection.FanConnectionRouter\",    \"radius\": 5,    \"source\": {      \"node\": \"d68511fe-bd9b-41a8-daf3-25654b8de2b8\",      \"port\": \"hybrid0\"    },    \"target\": {      \"node\": \"4d6aaacc-7db1-d39c-3529-8fc3a956e03b\",      \"port\": \"hybrid0\",      \"decoration\": \"draw2d.decoration.connection.ArrowDecorator\"    }  },  {    \"type\": \"draw2d.Connection\",    \"id\": \"9a942a70-4715-6b46-6123-28d782150f86\",    \"alpha\": 1,    \"angle\": 0,    \"userData\": {},    \"cssClass\": \"draw2d_Connection\",    \"stroke\": 3,    \"color\": \"#00A8F0\",    \"outlineStroke\": 1,    \"outlineColor\": \"#303030\",    \"policy\": \"draw2d.policy.line.LineSelectionFeedbackPolicy\",    \"vertex\": [      {        \"x\": 92,        \"y\": 327.97381546134665      },      {        \"x\": 199,        \"y\": 369.1691729323308      }    ],    \"router\": \"draw2d.layout.connection.FanConnectionRouter\",    \"radius\": 5,    \"source\": {      \"node\": \"root\",      \"port\": \"hybrid0\"    },    \"target\": {      \"node\": \"b45559e2-e708-b55c-3301-659040e42028\",      \"port\": \"hybrid0\",      \"decoration\": \"draw2d.decoration.connection.ArrowDecorator\"    }  },  {    \"type\": \"draw2d.Connection\",    \"id\": \"4a23aaa7-aed0-915a-15a4-2d18bdb3c5f7\",    \"alpha\": 1,    \"angle\": 0,    \"userData\": {},    \"cssClass\": \"draw2d_Connection\",    \"stroke\": 3,    \"color\": \"#00A8F0\",    \"outlineStroke\": 1,    \"outlineColor\": \"#303030\",    \"policy\": \"draw2d.policy.line.LineSelectionFeedbackPolicy\",    \"vertex\": [      {        \"x\": 592,        \"y\": 407.16548042704625      },      {        \"x\": 639,        \"y\": 407.1666666666667      }    ],    \"router\": \"draw2d.layout.connection.FanConnectionRouter\",    \"radius\": 5,    \"source\": {      \"node\": \"5f9f1e31-2546-336c-5cb2-17eec91754b6\",      \"port\": \"hybrid0\"    },    \"target\": {      \"node\": \"1168fc3e-6e24-3597-3877-7c3f1f354228\",      \"port\": \"hybrid0\",      \"decoration\": \"draw2d.decoration.connection.ArrowDecorator\"    }  },  {    \"type\": \"draw2d.Connection\",    \"id\": \"21ecf5d1-66eb-e8ce-51fd-139db84730ca\",    \"alpha\": 1,    \"angle\": 0,    \"userData\": {},    \"cssClass\": \"draw2d_Connection\",    \"stroke\": 3,    \"color\": \"#00A8F0\",    \"outlineStroke\": 1,    \"outlineColor\": \"#303030\",    \"policy\": \"draw2d.policy.line.LineSelectionFeedbackPolicy\",    \"vertex\": [      {        \"x\": 432,        \"y\": 407.1448598130841      },      {        \"x\": 499,        \"y\": 407.1457680250784      }    ],    \"router\": \"draw2d.layout.connection.FanConnectionRouter\",    \"radius\": 5,    \"source\": {      \"node\": \"291e3038-055f-104f-713e-ea159b3a332b\",      \"port\": \"hybrid0\"    },    \"target\": {      \"node\": \"5f9f1e31-2546-336c-5cb2-17eec91754b6\",      \"port\": \"hybrid0\",      \"decoration\": \"draw2d.decoration.connection.ArrowDecorator\"    }  },  {    \"type\": \"draw2d.Connection\",    \"id\": \"926bad3d-88e5-a5ce-f2b0-899fc68134c9\",    \"alpha\": 1,    \"angle\": 0,    \"userData\": {},    \"cssClass\": \"draw2d_Connection\",    \"stroke\": 3,    \"color\": \"#00A8F0\",    \"outlineStroke\": 1,    \"outlineColor\": \"#303030\",    \"policy\": \"draw2d.policy.line.LineSelectionFeedbackPolicy\",    \"vertex\": [      {        \"x\": 292,        \"y\": 393.7846975088968      },      {        \"x\": 339,        \"y\": 400.5      }    ],    \"router\": \"draw2d.layout.connection.FanConnectionRouter\",    \"radius\": 5,    \"source\": {      \"node\": \"b45559e2-e708-b55c-3301-659040e42028\",      \"port\": \"hybrid0\"    },    \"target\": {      \"node\": \"291e3038-055f-104f-713e-ea159b3a332b\",      \"port\": \"hybrid0\",      \"decoration\": \"draw2d.decoration.connection.ArrowDecorator\"    }  },  {    \"type\": \"draw2d.Connection\",    \"id\": \"8cd7b969-98cc-4a79-df28-849d192d6439\",    \"alpha\": 1,    \"angle\": 0,    \"userData\": {},    \"cssClass\": \"draw2d_Connection\",    \"stroke\": 3,    \"color\": \"#00A8F0\",    \"outlineStroke\": 1,    \"outlineColor\": \"#303030\",    \"policy\": \"draw2d.policy.line.LineSelectionFeedbackPolicy\",    \"vertex\": [      {        \"x\": 292,        \"y\": 207.1448598130841      },      {        \"x\": 359,        \"y\": 207.14576802507838      }    ],    \"router\": \"draw2d.layout.connection.FanConnectionRouter\",    \"radius\": 5,    \"source\": {      \"node\": \"d68511fe-bd9b-41a8-daf3-25654b8de2b8\",      \"port\": \"hybrid0\"    },    \"target\": {      \"node\": \"3831e8dc-a14d-d8f5-4657-c3049b412427\",      \"port\": \"hybrid0\",      \"decoration\": \"draw2d.decoration.connection.ArrowDecorator\"    }  },  {    \"type\": \"draw2d.Connection\",    \"id\": \"7601c842-e982-9fb2-c5be-564c3e332549\",    \"alpha\": 1,    \"angle\": 0,    \"userData\": {},    \"cssClass\": \"draw2d_Connection\",    \"stroke\": 3,    \"color\": \"#00A8F0\",    \"outlineStroke\": 1,    \"outlineColor\": \"#303030\",    \"policy\": \"draw2d.policy.line.LineSelectionFeedbackPolicy\",    \"vertex\": [      {        \"x\": 292,        \"y\": 230.32242990654206      },      {        \"x\": 359,        \"y\": 263.8228840125392      }    ],    \"router\": \"draw2d.layout.connection.FanConnectionRouter\",    \"radius\": 5,    \"source\": {      \"node\": \"d68511fe-bd9b-41a8-daf3-25654b8de2b8\",      \"port\": \"hybrid0\"    },    \"target\": {      \"node\": \"d9e3756b-36eb-7d34-ef98-a1cca6c858d8\",      \"port\": \"hybrid0\",      \"decoration\": \"draw2d.decoration.connection.ArrowDecorator\"    }  },  {    \"type\": \"draw2d.Connection\",    \"id\": \"d1cd838a-0fdd-8765-4aa9-7d59fa85ca53\",    \"alpha\": 1,    \"angle\": 0,    \"userData\": {},    \"cssClass\": \"draw2d_Connection\",    \"stroke\": 3,    \"color\": \"#00A8F0\",    \"outlineStroke\": 1,    \"outlineColor\": \"#303030\",    \"policy\": \"draw2d.policy.line.LineSelectionFeedbackPolicy\",    \"vertex\": [      {        \"x\": 292,        \"y\": 183.96728971962617      },      {        \"x\": 359,        \"y\": 150.46865203761755      }    ],    \"router\": \"draw2d.layout.connection.FanConnectionRouter\",    \"radius\": 5,    \"source\": {      \"node\": \"d68511fe-bd9b-41a8-daf3-25654b8de2b8\",      \"port\": \"hybrid0\"    },    \"target\": {      \"node\": \"fbda2586-4cff-c716-1620-bd316b938d31\",      \"port\": \"hybrid0\",      \"decoration\": \"draw2d.decoration.connection.ArrowDecorator\"    }  },  {    \"type\": \"draw2d.Connection\",    \"id\": \"5832754c-222e-c9ff-8cf1-4bcb4b9d30a6\",    \"alpha\": 1,    \"angle\": 0,    \"userData\": {},    \"cssClass\": \"draw2d_Connection\",    \"stroke\": 3,    \"color\": \"#00A8F0\",    \"outlineStroke\": 1,    \"outlineColor\": \"#303030\",    \"policy\": \"draw2d.policy.line.LineSelectionFeedbackPolicy\",    \"vertex\": [      {        \"x\": 92,        \"y\": 286.2281795511222      },      {        \"x\": 199,        \"y\": 231.12406015037595      }    ],    \"router\": \"draw2d.layout.connection.FanConnectionRouter\",    \"radius\": 5,    \"source\": {      \"node\": \"root\",      \"port\": \"hybrid0\"    },    \"target\": {      \"node\": \"d68511fe-bd9b-41a8-daf3-25654b8de2b8\",      \"port\": \"hybrid0\",      \"decoration\": \"draw2d.decoration.connection.ArrowDecorator\"    }  }]', null, '10');

-- ----------------------------
-- Table structure for t_menus
-- ----------------------------
DROP TABLE IF EXISTS `t_menus`;
CREATE TABLE `t_menus` (
  `name` text NOT NULL,
  `icon` text NOT NULL,
  `url` text,
  `orderNum` int(11) NOT NULL,
  `uid` varchar(200) DEFAULT NULL,
  `parentId` varchar(200) DEFAULT '0',
  `id` int(11) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`id`) USING BTREE,
  KEY `parent_fk` (`parentId`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=36 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;

-- ----------------------------
-- Records of t_menus
-- ----------------------------
INSERT INTO `t_menus` VALUES ('系统管理', 'fa fa-desktop ', null, '1', 'd68511fe-bd9b-41a8-daf3-25654b8de2b8', 'root', '26');
INSERT INTO `t_menus` VALUES ('菜单管理', 'fa fa-sitemap ', '/console/menu', '1', '3831e8dc-a14d-d8f5-4657-c3049b412427', 'd68511fe-bd9b-41a8-daf3-25654b8de2b8', '27');
INSERT INTO `t_menus` VALUES ('项目管理', 'fa fa-shopping-bag ', null, '2', 'b45559e2-e708-b55c-3301-659040e42028', 'root', '28');
INSERT INTO `t_menus` VALUES ('发起项目', 'fa fa-send ', '/project/add', '1', '1168fc3e-6e24-3597-3877-7c3f1f354228', '5f9f1e31-2546-336c-5cb2-17eec91754b6', '29');
INSERT INTO `t_menus` VALUES ('用户管理', 'fa fa-users ', '/console/user', '2', 'fbda2586-4cff-c716-1620-bd316b938d31', 'd68511fe-bd9b-41a8-daf3-25654b8de2b8', '30');
INSERT INTO `t_menus` VALUES ('角色管理', 'fa fa-paw ', '/console/role', '3', 'd9e3756b-36eb-7d34-ef98-a1cca6c858d8', 'd68511fe-bd9b-41a8-daf3-25654b8de2b8', '31');
INSERT INTO `t_menus` VALUES ('项目管理2', 'fa fa-desktop ', null, '1', '291e3038-055f-104f-713e-ea159b3a332b', 'b45559e2-e708-b55c-3301-659040e42028', '33');
INSERT INTO `t_menus` VALUES ('项目管理3', 'fa fa-desktop ', null, '1', '5f9f1e31-2546-336c-5cb2-17eec91754b6', '291e3038-055f-104f-713e-ea159b3a332b', '34');
INSERT INTO `t_menus` VALUES ('组织部门', 'fa fa-sitemap ', '/console/company', '4', '4d6aaacc-7db1-d39c-3529-8fc3a956e03b', 'd68511fe-bd9b-41a8-daf3-25654b8de2b8', '35');

-- ----------------------------
-- Table structure for t_pageinvocations
-- ----------------------------
DROP TABLE IF EXISTS `t_pageinvocations`;
CREATE TABLE `t_pageinvocations` (
  `entityName` varchar(255) NOT NULL,
  `entityType` varchar(20) NOT NULL,
  `urls` text NOT NULL,
  `menuIds` text,
  `buttonIds` text,
  `id` int(11) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;

-- ----------------------------
-- Records of t_pageinvocations
-- ----------------------------
INSERT INTO `t_pageinvocations` VALUES ('SYSTEM', 'role', '/**', '', '', '1');
INSERT INTO `t_pageinvocations` VALUES ('USER', 'role', '/console/menu;/menu/get;/console/user;/user/get;/console/role;/role/get;/diagram/getmenu;/upload/logo;/console/index;/token/get;/menu/getbuttons;/department/makelevel;/department/drag;/console/default;/role/gettotal;/user/gettotal;/company/gettotal;', '3831e8dc-a14d-d8f5-4657-c3049b412427,fbda2586-4cff-c716-1620-bd316b938d31,d9e3756b-36eb-7d34-ef98-a1cca6c858d8,', '[{\"name\":\"菜单列表\",\"menuUid\":\"3831e8dc-a14d-d8f5-4657-c3049b412427\"},{\"name\":\"用户列表\",\"menuUid\":\"fbda2586-4cff-c716-1620-bd316b938d31\"},{\"name\":\"角色列表\",\"menuUid\":\"d9e3756b-36eb-7d34-ef98-a1cca6c858d8\"}]', '6');

-- ----------------------------
-- Table structure for t_relegations
-- ----------------------------
DROP TABLE IF EXISTS `t_relegations`;
CREATE TABLE `t_relegations` (
  `userName` text NOT NULL,
  `dept_id` int(11) NOT NULL,
  `id` int(11) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`id`) USING BTREE,
  KEY `rele_dept_fk` (`dept_id`) USING BTREE,
  CONSTRAINT `rele_dept_fk` FOREIGN KEY (`dept_id`) REFERENCES `t_depts` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;

-- ----------------------------
-- Records of t_relegations
-- ----------------------------
INSERT INTO `t_relegations` VALUES ('user1', '1', '1');

-- ----------------------------
-- Table structure for t_roles
-- ----------------------------
DROP TABLE IF EXISTS `t_roles`;
CREATE TABLE `t_roles` (
  `rolename` varchar(255) DEFAULT NULL,
  `showname` varchar(255) DEFAULT NULL,
  `id` int(11) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=23 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;

-- ----------------------------
-- Records of t_roles
-- ----------------------------
INSERT INTO `t_roles` VALUES ('SYSTEM', '系统管理员', '1');
INSERT INTO `t_roles` VALUES ('USER', '普通管理员', '2');
INSERT INTO `t_roles` VALUES ('Role1', 'Role1', '3');
INSERT INTO `t_roles` VALUES ('Role2', 'Role2', '4');
INSERT INTO `t_roles` VALUES ('Role3', 'Role3', '5');
INSERT INTO `t_roles` VALUES ('Role4', 'Role4', '6');
INSERT INTO `t_roles` VALUES ('Role5', 'Role5', '7');
INSERT INTO `t_roles` VALUES ('Role6', 'Role6', '8');
INSERT INTO `t_roles` VALUES ('Role7', 'Role7', '9');
INSERT INTO `t_roles` VALUES ('Role8', 'Role8', '10');
INSERT INTO `t_roles` VALUES ('Role9', 'Role9', '11');
INSERT INTO `t_roles` VALUES ('Role10', 'Role10', '12');
INSERT INTO `t_roles` VALUES ('Role11', 'Role11', '13');
INSERT INTO `t_roles` VALUES ('Role12', 'Role12', '14');
INSERT INTO `t_roles` VALUES ('Role13', 'Role13', '15');
INSERT INTO `t_roles` VALUES ('Role14', 'Role14', '16');
INSERT INTO `t_roles` VALUES ('Role15', 'Role15', '17');
INSERT INTO `t_roles` VALUES ('Role16', 'Role16', '18');
INSERT INTO `t_roles` VALUES ('Role17', 'Role17', '19');
INSERT INTO `t_roles` VALUES ('Role18', 'Role18', '20');
INSERT INTO `t_roles` VALUES ('Role19', 'Role19', '21');
INSERT INTO `t_roles` VALUES ('Role20', 'Role20', '22');

-- ----------------------------
-- Table structure for t_users
-- ----------------------------
DROP TABLE IF EXISTS `t_users`;
CREATE TABLE `t_users` (
  `createDate` date DEFAULT NULL,
  `modifyDate` date DEFAULT NULL,
  `roleNames` varchar(255) DEFAULT NULL,
  `userName` varchar(255) DEFAULT NULL,
  `account_id` varchar(255) DEFAULT NULL,
  `id` int(11) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;

-- ----------------------------
-- Records of t_users
-- ----------------------------
INSERT INTO `t_users` VALUES ('2018-06-23', '2018-06-23', 'USER', 'admin', '1', '1');
INSERT INTO `t_users` VALUES ('2018-06-23', '2018-06-23', 'USER', 'user1', '2', '2');
