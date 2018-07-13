-- MySQL dump 10.13  Distrib 5.5.57, for Win64 (AMD64)
--
-- Host: localhost    Database: pandanew
-- ------------------------------------------------------
-- Server version	5.5.57

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `t_accounts`
--

DROP TABLE IF EXISTS `t_accounts`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_accounts` (
  `createDate` datetime DEFAULT NULL,
  `modifyDate` datetime DEFAULT NULL,
  `ISACCOUNTNONEXPIRED` bit(1) DEFAULT NULL,
  `ISACCOUNTNONLOCKED` bit(1) DEFAULT NULL,
  `ISCREDENTIALSNONEXPIRED` bit(1) DEFAULT NULL,
  `ENABLED` bit(1) DEFAULT NULL,
  `username` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `id` int(11) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_accounts`
--

LOCK TABLES `t_accounts` WRITE;
/*!40000 ALTER TABLE `t_accounts` DISABLE KEYS */;
INSERT INTO `t_accounts` VALUES ('2018-06-23 00:00:00','2018-06-23 00:00:00','','','','','admin','$2a$10$PUIYRsOI7hhi9IIoQDAiZe7LK8e7uqLSsxLPm3XO9/FMHKAslD4Mi',1),('2018-06-23 00:00:00','2018-06-23 00:00:00','','','','','user1','$2a$10$PUIYRsOI7hhi9IIoQDAiZe7LK8e7uqLSsxLPm3XO9/FMHKAslD4Mi',2);
/*!40000 ALTER TABLE `t_accounts` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_authorities`
--

DROP TABLE IF EXISTS `t_authorities`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_authorities` (
  `createDate` datetime DEFAULT NULL,
  `modifyDate` datetime DEFAULT NULL,
  `account_id` int(11) DEFAULT NULL,
  `role_id` int(11) DEFAULT NULL,
  `id` int(11) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_authorities`
--

LOCK TABLES `t_authorities` WRITE;
/*!40000 ALTER TABLE `t_authorities` DISABLE KEYS */;
INSERT INTO `t_authorities` VALUES ('2018-06-23 00:00:00','2018-06-23 00:00:00',1,1,1),('2018-06-23 00:00:00','2018-06-23 00:00:00',1,2,2),('2018-06-23 00:00:00','2018-06-23 00:00:00',2,2,3),('2018-06-23 00:00:00','2018-06-23 00:00:00',2,1,4);
/*!40000 ALTER TABLE `t_authorities` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_pageinvocations`
--

DROP TABLE IF EXISTS `t_pageinvocations`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_pageinvocations` (
  `createDate` datetime DEFAULT NULL,
  `modifyDate` datetime DEFAULT NULL,
  `roles` longtext,
  `url` longtext,
  `id` int(11) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_pageinvocations`
--

LOCK TABLES `t_pageinvocations` WRITE;
/*!40000 ALTER TABLE `t_pageinvocations` DISABLE KEYS */;
INSERT INTO `t_pageinvocations` VALUES ('2016-08-08 00:00:00','2016-08-08 00:00:00','ROLE_SYSTEM,ROLE_USER','/manage/index',1);
/*!40000 ALTER TABLE `t_pageinvocations` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_roles`
--

DROP TABLE IF EXISTS `t_roles`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_roles` (
  `createDate` datetime DEFAULT NULL,
  `modifyDate` datetime DEFAULT NULL,
  `description` longtext,
  `rname` varchar(255) DEFAULT NULL,
  `showname` varchar(255) DEFAULT NULL,
  `rstatus` bit(1) NOT NULL,
  `id` int(11) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_roles`
--

LOCK TABLES `t_roles` WRITE;
/*!40000 ALTER TABLE `t_roles` DISABLE KEYS */;
INSERT INTO `t_roles` VALUES ('2016-08-08 00:00:00','2016-08-08 00:00:00','系统管理员','SYSTEM','系统管理员','',1),('2016-08-08 00:00:00','2016-08-08 00:00:00','普通管理员','USER','普通管理员','',2);
/*!40000 ALTER TABLE `t_roles` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_users`
--

DROP TABLE IF EXISTS `t_users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_users` (
  `createDate` date DEFAULT NULL,
  `modifyDate` date DEFAULT NULL,
  `roleNames` varchar(255) DEFAULT NULL,
  `userName` varchar(255) DEFAULT NULL,
  `account_id` varchar(255) DEFAULT NULL,
  `id` int(11) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_users`
--

LOCK TABLES `t_users` WRITE;
/*!40000 ALTER TABLE `t_users` DISABLE KEYS */;
INSERT INTO `t_users` VALUES ('2018-06-23','2018-06-23','USER','user1','1',1),('2018-06-23','2018-06-23','USER','user2','2',2),('2018-06-25','2018-06-25','USER','user4','3',3),('2018-06-25','2018-06-25','USER','user4','4',4),('2018-06-25','2018-06-25','USER','user4','5',5),('2018-06-25','2018-06-25','USER','user4','6',6),('2018-06-25','2018-06-25','USER','user4','7',7),('2018-06-25','2018-06-25','USER','user4','8',8);
/*!40000 ALTER TABLE `t_users` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2018-07-06 16:44:49
