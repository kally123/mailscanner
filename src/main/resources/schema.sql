# ************************************************************
# Sequel Pro SQL dump
# Version 4541
#
# http://www.sequelpro.com/
# https://github.com/sequelpro/sequelpro
#
# Host: 127.0.0.1 (MySQL 5.7.19)
# Database: ZOMATO_ORDERS
# Generation Time: 2019-08-22 15:54:02 +0000
# ************************************************************


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;


# Dump of table customer
# ------------------------------------------------------------

DROP TABLE IF EXISTS `customer`;

CREATE TABLE `customer` (
  `id` bigint(20) NOT NULL,
  `address` varchar(500) DEFAULT NULL,
  `mob_number` varchar(20) DEFAULT NULL,
  `name` varchar(36) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;


# Dump of table hibernate_sequence
# ------------------------------------------------------------

DROP TABLE IF EXISTS `hibernate_sequence`;

CREATE TABLE `hibernate_sequence` (
  `next_val` bigint(20) DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8;


# Dump of table invalidcustomer
# ------------------------------------------------------------

DROP TABLE IF EXISTS `invalidcustomer`;

CREATE TABLE `invalidcustomer` (
  `name` varchar(35) NOT NULL DEFAULT '',
  `error_message` varchar(2000) NOT NULL DEFAULT '',
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


# Dump of table messagesscanned
# ------------------------------------------------------------

DROP TABLE IF EXISTS `messagesscanned`;

CREATE TABLE `messagesscanned` (
  `id` int(11) NOT NULL,
  `messages_scanned` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

LOCK TABLES `messagesscanned` WRITE;
/*!40000 ALTER TABLE `messagesscanned` DISABLE KEYS */;


INSERT INTO `messagesscanned` (`id`, `messages_scanned`)
VALUES
	(1,1);

/*!40000 ALTER TABLE `messagesscanned` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table orderdetails
# ------------------------------------------------------------

DROP TABLE IF EXISTS `orderdetails`;

CREATE TABLE `orderdetails` (
  `order_id` varchar(50) NOT NULL DEFAULT '',
  `order_date` varchar(100) DEFAULT NULL,
  `order_summary` varchar(1000) DEFAULT NULL,
  `packaging_charge` int(11) DEFAULT NULL,
  `payment_mode` varchar(40) DEFAULT NULL,
  `restaurant_promo` double DEFAULT NULL,
  `zomato_promo` double DEFAULT NULL,
  `customer_id` bigint(20) NOT NULL DEFAULT '1',
  `bill` double DEFAULT NULL,
  PRIMARY KEY (`order_id`),
  KEY `FKekul8gge4s4346gymrfpqth19` (`customer_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;
