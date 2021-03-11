-- MySQL Administrator dump 1.4
--
-- ------------------------------------------------------
-- Server version	5.0.45-community-nt-log


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;


--
-- Create schema fresh_mvcproject
--

CREATE DATABASE IF NOT EXISTS fresh_mvcproject;
USE fresh_mvcproject;

--
-- Definition of table `roles`
--

DROP TABLE IF EXISTS `roles`;
CREATE TABLE `roles` (
  `id` int(10) unsigned NOT NULL auto_increment,
  `role` varchar(50) NOT NULL,
  `name` varchar(50) NOT NULL,
  PRIMARY KEY  (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `roles`
--

/*!40000 ALTER TABLE `roles` DISABLE KEYS */;
INSERT INTO `roles` (`id`,`role`,`name`) VALUES 
 (1,'ROLE_ADMIN','Admin'),
 (2,'ROLE_USER','User');
/*!40000 ALTER TABLE `roles` ENABLE KEYS */;


--
-- Definition of table `user_roles`
--

DROP TABLE IF EXISTS `user_roles`;
CREATE TABLE `user_roles` (
  `user_id` int(10) unsigned NOT NULL,
  `role_id` int(10) unsigned NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Dumping data for table `user_roles`
--

/*!40000 ALTER TABLE `user_roles` DISABLE KEYS */;
INSERT INTO `user_roles` (`user_id`,`role_id`) VALUES 
 (1,2),
 (1,1),
 (2,2),
 (3,2),
 (3,1),
 (4,1),
 (4,2),
 (5,2),
 (100,101),
 (14,1),
 (14,2),
 (15,1),
 (16,2),
 (17,1),
 (17,2),
 (18,1),
 (18,2),
 (19,1),
 (19,2),
 (20,2),
 (21,1),
 (21,2),
 (22,1),
 (22,2),
 (23,2),
 (24,1),
 (24,2),
 (25,1),
 (25,2);
/*!40000 ALTER TABLE `user_roles` ENABLE KEYS */;


--
-- Definition of table `users`
--

DROP TABLE IF EXISTS `users`;
CREATE TABLE `users` (
  `username` varchar(45) NOT NULL,
  `password` varchar(45) NOT NULL,
  `enabled` tinyint(4) NOT NULL default '1',
  `id` int(10) unsigned NOT NULL auto_increment,
  PRIMARY KEY  USING BTREE (`id`,`username`)
) ENGINE=MyISAM AUTO_INCREMENT=26 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `users`
--

/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` (`username`,`password`,`enabled`,`id`) VALUES 
 ('mkyong','123456',1,1),
 ('alex','123456',1,2),
 ('admin','d033e22ae348aeb5660fc2140aec35850c4da997',1,21),
 ('user','12dea96fec20593566ab75692c9949596833adc9',1,20),
 ('sam','f16bed56189e249fe4ca8ed10a1ecae60e8ceac0',1,19),
 ('pop','4f197c99a78b8411f1cf48ab409a0a6d176b99b7',1,17),
 ('admin1','6c7ca345f63f835cb353ff15bd6c5e052ec08e7a',1,22),
 ('user1','b3daa77b4c04a9551b8781d03191fe098f325e67',1,23),
 ('w','aff024fe4ab0fece4091de044c58c9ae4233383a',0,24),
 ('t','8efd86fb78a56a5145ed7739dcb00c78581c5375',1,25);
/*!40000 ALTER TABLE `users` ENABLE KEYS */;




/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
