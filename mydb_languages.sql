-- MySQL dump 10.13  Distrib 5.7.17, for Win64 (x86_64)
--
-- Host: localhost    Database: mydb
-- ------------------------------------------------------
-- Server version	5.7.18-log

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
-- Table structure for table `languages`
--

DROP TABLE IF EXISTS `languages`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `languages` (
  `ID` int(11) NOT NULL,
  `Name` varchar(45) DEFAULT NULL,
  `Imperative` tinyint(4) DEFAULT NULL,
  `Object-Oriented` tinyint(4) DEFAULT NULL,
  `Functional` tinyint(4) DEFAULT NULL,
  `Procedural` tinyint(4) DEFAULT NULL,
  `Generic` tinyint(4) DEFAULT NULL,
  `Reflective` tinyint(4) DEFAULT NULL,
  `Event-Driven` tinyint(4) DEFAULT NULL,
  `For Loop` int(11) DEFAULT NULL,
  `While Loop` int(11) DEFAULT NULL,
  `Arrays` int(11) DEFAULT NULL,
  `Operators` int(11) DEFAULT NULL,
  `Uses` varchar(200) DEFAULT NULL,
  `Paradigms` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `languages`
--

LOCK TABLES `languages` WRITE;
/*!40000 ALTER TABLE `languages` DISABLE KEYS */;
INSERT INTO `languages` VALUES (1,'Java',1,1,1,1,1,1,1,1,1,1,1,'Application, business, client-side, general, mobile development, server-side, web','Concurrent'),(2,'C',1,0,0,1,0,0,0,1,1,2,1,'Application, system, general purpose, low-level operations',NULL),(3,'C++',1,1,1,1,1,0,0,1,1,2,1,'Application, system',NULL),(4,'Python',1,1,1,1,0,1,0,2,1,3,1,'Application, general, web, scripting, artificial intelligence, scientific computing','Aspect-orented'),(5,'C#',1,1,1,1,1,1,1,1,1,1,1,'Application, RAD, business, client-side, general, server-side, web','Structured, concurrent'),(6,'VisualBasic.net',1,1,1,1,1,1,1,3,1,4,2,'Application, RAD, education, web, business, general','Structured, concurrent'),(7,'Javascript',1,1,1,0,0,1,1,1,1,1,1,'Client-side, server-side, web','Prototype-based'),(8,'Assembly Language',1,0,0,0,0,0,0,0,0,0,0,'General','Syntax is usually highly specific (related to the target processor)'),(9,'PHP',1,1,1,1,0,1,0,1,1,5,3,'Server-side, web application, web',NULL),(10,'Perl',1,1,1,1,1,1,0,1,1,6,4,'Application, scripting, text processing, Web',NULL),(11,'Ruby',1,1,1,0,0,1,0,4,1,1,5,'Application, scripting, web','Aspect-oriented'),(12,'VisualBasic',1,1,0,0,1,0,1,3,1,4,2,'Application, RAD, education, business, general, (Includes VBA), office automation','Component-oriented'),(13,'Swift',1,1,1,0,1,1,1,4,1,1,1,'Application, general','Concurrent'),(14,'R',1,1,1,1,0,0,0,5,1,0,6,'Application, statistics',NULL),(15,'Objective-C',1,1,0,0,0,1,0,1,1,7,1,'Application, general','Concurrent'),(16,'Go',1,1,1,1,0,1,1,1,1,3,1,'Application, web, server-side','Concurrent'),(17,'MatLab',1,1,0,1,0,0,0,6,1,8,1,'Highly domain-specific, numerical computing',NULL),(18,'Delphi/Object-Pascal',1,1,0,1,1,1,1,7,1,3,2,'Application, general, mobile app, web','Structured'),(19,'PL/SQL',1,1,0,1,0,0,0,4,1,0,7,'Application',NULL),(20,'Lua',1,1,1,1,0,1,0,1,1,9,8,'Application, embedded scripting','Aspect-oriented'),(21,'Dart',1,1,1,0,0,0,0,1,1,1,9,'Application, web, server-side, mobile, IoT','Structured');
/*!40000 ALTER TABLE `languages` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2017-05-15  9:18:34
