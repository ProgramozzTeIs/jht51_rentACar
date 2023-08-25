CREATE DATABASE  IF NOT EXISTS `carrentdb` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `carrentdb`;
-- MySQL dump 10.13  Distrib 8.0.33, for Win64 (x86_64)
--
-- Host: localhost    Database: carrentdb
-- ------------------------------------------------------
-- Server version	8.0.33

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `reservations`
--

DROP TABLE IF EXISTS `reservations`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `reservations` (
  `id` int NOT NULL AUTO_INCREMENT,
  `user_name` varchar(45) NOT NULL,
  `user_email` varchar(45) NOT NULL,
  `user_phone` varchar(45) NOT NULL,
  `user_address` varchar(45) NOT NULL,
  `start_date` date NOT NULL,
  `end_date` date NOT NULL,
  `car_id` int NOT NULL,
  PRIMARY KEY (`id`),
  KEY `reservation_cars_fk_idx` (`car_id`),
  CONSTRAINT `reservation_cars_fk` FOREIGN KEY (`car_id`) REFERENCES `cars` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `reservations`
--

LOCK TABLES `reservations` WRITE;
/*!40000 ALTER TABLE `reservations` DISABLE KEYS */;
INSERT INTO `reservations` VALUES (1,'Tóth Zoltán','ztoth@gmail.com','30/876-6543','Budapest, Blaha Lujza tér','2023-08-11','2023-08-14',7),(2,'Szabó István','istvan.sz@freemail.hu','20/455-5520','Siófok, Aranypart','2023-08-10','2023-08-19',5),(3,'Kovács Gabriella','gabikov@gmail.com','20/661-1122','Szeged, Kárász utca','2023-08-20','2023-08-21',1),(4,'Tóth Zoltán','ztoth@gmail.com','30/876-6543','Budapest, Blaha Lujza tér','2023-08-20','2023-08-27',5),(5,'Horváth Aladár','carfan57@freemail.hu','70/234-5643','Sopron, Somfalvi utca','2023-08-19','2023-08-24',3),(6,'Szabó István','istvan.sz@freemail.hu','20/455-5520','Siófok, Aranypart','2023-08-25','2023-09-02',6),(7,'Tóth Zoltán','ztoth@gmail.com','30/876-6543','Budapest, Blaha Lujza tér','2023-08-30','2023-09-04',5),(10,'Kovács Gabriella','gabikov@gmail.com','20/661-1122','Szeged, Kárász utca','2023-08-17','2023-08-26',2),(11,'Nagy Géza','ng82@outlook.com','30/224-5050','Dunaújváros, Vasmű tér','2023-08-23','2023-08-26',1),(12,'Nagy Géza','ng82@outlook.com','30/224-5050','Dunaújváros, Vasmű tér','2023-09-06','2023-09-09',1),(13,'Halász Márta','mart.hal@gmail.com','20/101-5775','Kaposvár, Somogyi utca','2023-08-24','2023-08-27',4),(14,'Tóth Zoltán','ztoth@gmail.com','30/876-6543','Budapest, Blaha Lujza tér','2023-09-08','2023-09-11',3),(15,'Kecskeméti Anna','anna@outlook.com','70/630-2791','Kecskemét, Békéscsabai út','2023-08-16','2023-08-21',4);
/*!40000 ALTER TABLE `reservations` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2023-08-25 11:16:54
