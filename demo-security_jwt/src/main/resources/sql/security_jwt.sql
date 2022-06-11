/*
SQLyog Ultimate v11.25 (64 bit)
MySQL - 10.6.7-MariaDB : Database - demo_security
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`demo_security` /*!40100 DEFAULT CHARACTER SET utf8mb4 */;

USE `demo_security`;

/*Table structure for table `permission` */

DROP TABLE IF EXISTS `permission`;

CREATE TABLE `permission` (
                              `permission_id` bigint(20) NOT NULL AUTO_INCREMENT,
                              `permission_name` varchar(32) NOT NULL,
                              PRIMARY KEY (`permission_id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4;

/*Data for the table `permission` */

insert  into `permission`(`permission_id`,`permission_name`) values (1,'QUERY'),(2,'CREATE'),(3,'UPDATE'),(4,'DELETE');

/*Table structure for table `role` */

DROP TABLE IF EXISTS `role`;

CREATE TABLE `role` (
                        `role_id` bigint(20) NOT NULL AUTO_INCREMENT,
                        `role_name` varchar(32) NOT NULL,
                        PRIMARY KEY (`role_id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4;

/*Data for the table `role` */

insert  into `role`(`role_id`,`role_name`) values (1,'ROLE_ADMIN'),(2,'ROLE_MANAGER'),(3,'ROLE_NP');

/*Table structure for table `role_permission` */

DROP TABLE IF EXISTS `role_permission`;

CREATE TABLE `role_permission` (
                                   `role_permission_id` bigint(20) NOT NULL AUTO_INCREMENT,
                                   `role_id` bigint(20) NOT NULL,
                                   `permission_id` bigint(20) NOT NULL,
                                   PRIMARY KEY (`role_permission_id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4;

/*Data for the table `role_permission` */

insert  into `role_permission`(`role_permission_id`,`role_id`,`permission_id`) values (1,1,1),(2,1,2),(3,1,3),(4,1,4),(5,2,1),(6,2,2),(7,2,3),(8,3,1),(9,3,2);

/*Table structure for table `user` */

DROP TABLE IF EXISTS `user`;

CREATE TABLE `user` (
                        `user_id` bigint(20) NOT NULL AUTO_INCREMENT,
                        `username` varchar(32) NOT NULL,
                        `password` varchar(64) NOT NULL,
                        PRIMARY KEY (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4;

/*Data for the table `user` */

insert  into `user`(`user_id`,`username`,`password`) values (1,'zzq','$2a$10$UyCgLflFZrTD4vBzx23N4.xe/u5IG1spmGdih/LDbMVBVZGBnZwda');

/*Table structure for table `user_role` */

DROP TABLE IF EXISTS `user_role`;

CREATE TABLE `user_role` (
                             `user_role_id` bigint(20) NOT NULL AUTO_INCREMENT,
                             `user_id` bigint(20) NOT NULL,
                             `role_id` bigint(20) NOT NULL,
                             PRIMARY KEY (`user_role_id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4;

/*Data for the table `user_role` */

insert  into `user_role`(`user_role_id`,`user_id`,`role_id`) values (1,1,1);

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
/*
SQLyog Ultimate v11.25 (64 bit)
MySQL - 10.6.7-MariaDB : Database - demo_security
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`demo_security` /*!40100 DEFAULT CHARACTER SET utf8mb4 */;

USE `demo_security`;

/*Table structure for table `permission` */

DROP TABLE IF EXISTS `permission`;

CREATE TABLE `permission` (
                              `permission_id` bigint(20) NOT NULL AUTO_INCREMENT,
                              `permission_name` varchar(32) NOT NULL,
                              PRIMARY KEY (`permission_id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4;

/*Data for the table `permission` */

insert  into `permission`(`permission_id`,`permission_name`) values (1,'QUERY'),(2,'CREATE'),(3,'UPDATE'),(4,'DELETE');

/*Table structure for table `role` */

DROP TABLE IF EXISTS `role`;

CREATE TABLE `role` (
                        `role_id` bigint(20) NOT NULL AUTO_INCREMENT,
                        `role_name` varchar(32) NOT NULL,
                        PRIMARY KEY (`role_id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4;

/*Data for the table `role` */

insert  into `role`(`role_id`,`role_name`) values (1,'ROLE_ADMIN'),(2,'ROLE_MANAGER'),(3,'ROLE_NP');

/*Table structure for table `role_permission` */

DROP TABLE IF EXISTS `role_permission`;

CREATE TABLE `role_permission` (
                                   `role_permission_id` bigint(20) NOT NULL AUTO_INCREMENT,
                                   `role_id` bigint(20) NOT NULL,
                                   `permission_id` bigint(20) NOT NULL,
                                   PRIMARY KEY (`role_permission_id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4;

/*Data for the table `role_permission` */

insert  into `role_permission`(`role_permission_id`,`role_id`,`permission_id`) values (1,1,1),(2,1,2),(3,1,3),(4,1,4),(5,2,1),(6,2,2),(7,2,3),(8,3,1),(9,3,2);

/*Table structure for table `user` */

DROP TABLE IF EXISTS `user`;

CREATE TABLE `user` (
                        `user_id` bigint(20) NOT NULL AUTO_INCREMENT,
                        `username` varchar(32) NOT NULL,
                        `password` varchar(64) NOT NULL,
                        PRIMARY KEY (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4;

/*Data for the table `user` */

insert  into `user`(`user_id`,`username`,`password`) values (1,'zzq','$2a$10$UyCgLflFZrTD4vBzx23N4.xe/u5IG1spmGdih/LDbMVBVZGBnZwda');

/*Table structure for table `user_role` */

DROP TABLE IF EXISTS `user_role`;

CREATE TABLE `user_role` (
                             `user_role_id` bigint(20) NOT NULL AUTO_INCREMENT,
                             `user_id` bigint(20) NOT NULL,
                             `role_id` bigint(20) NOT NULL,
                             PRIMARY KEY (`user_role_id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4;

/*Data for the table `user_role` */

insert  into `user_role`(`user_role_id`,`user_id`,`role_id`) values (1,1,1);

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
