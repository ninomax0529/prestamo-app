CREATE DATABASE  IF NOT EXISTS `db_prestamo` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `db_prestamo`;
-- MySQL dump 10.13  Distrib 8.0.41, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: db_prestamo
-- ------------------------------------------------------
-- Server version	8.4.4

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
-- Table structure for table `cliente`
--

DROP TABLE IF EXISTS `cliente`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `cliente` (
  `codigo` int NOT NULL AUTO_INCREMENT,
  `nombre` varchar(150) DEFAULT NULL,
  `cedula` varchar(20) DEFAULT NULL,
  `celular` varchar(15) DEFAULT NULL,
  `direccion` varchar(200) DEFAULT NULL,
  `lugar_de_trabajo` varchar(150) DEFAULT NULL,
  `actualizado_por` varchar(90) DEFAULT NULL,
  `referencia` varchar(45) DEFAULT NULL,
  `fecha_creacion` datetime DEFAULT NULL,
  `creado_por` varchar(90) DEFAULT NULL,
  `fecha_actualizacion` datetime DEFAULT NULL,
  `habilitado` bit(1) DEFAULT NULL,
  PRIMARY KEY (`codigo`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cliente`
--

LOCK TABLES `cliente` WRITE;
/*!40000 ALTER TABLE `cliente` DISABLE KEYS */;
INSERT INTO `cliente` VALUES (1,'JOSEFINA DE LOS RIO','034-002921-4','829-276-5148','SANTIAGO DE LOS CABALLEROS','Cementos cibao cc',NULL,'MAXIMILIANO','2025-06-20 09:51:38','max','2025-06-20 09:51:38',_binary ''),(2,'WQEQ','QWE','WE','QWE','EWQ',NULL,'QWE','2025-06-18 17:01:27','nino','2025-06-18 17:01:27',_binary '\0'),(3,'Ramon','wrewe','dgdfdg','wre','fdgdf',NULL,'dfgd','2025-06-18 17:01:33',NULL,'2025-06-18 17:01:33',_binary '\0'),(4,'Pepe rodriguez','21212','21213232','asdsad','Cemento cibao',NULL,'rafael','2025-06-20 09:35:51',NULL,'2025-06-20 09:35:51',_binary ''),(5,'hipolito barrero','031-212113','809-213-12421','mao valverde','super edwar',NULL,'nino almonte','2025-06-12 11:20:28',NULL,'2025-06-12 11:20:28',_binary ''),(6,'MAXIMILIANO ALMONTE','034-00-44420-8','809-280-7944','LAS COLINA 29-5','CEMENTOS CIBAO',NULL,'WELINTON','2025-06-20 09:50:43',NULL,'2025-06-20 09:50:43',_binary ''),(7,'JOSE ALMONTE','034-0044412-7','809-542-4527','LOS JARDINES SANTIAGO','LA COROMINA',NULL,'PEDRO RODRIGUEZ','2025-06-20 11:10:29',NULL,'2025-06-20 11:10:29',_binary ''),(8,'RAFAEL PEREZ','031-214235-4','809-425-4136','JACAGUA SANTIAGO','CECONSA SANTIAGO',NULL,'JOSELITO  PEREZ','2025-06-20 14:43:18',NULL,'2025-06-20 14:43:18',_binary ''),(9,'RADAMEZ GONZALES','031-214235-4','809-425-4139','BUENOS AIRES','CECONSA SANTIAGO',NULL,'JOSELITO  PEREZ','2025-06-20 14:50:19',NULL,'2025-06-20 14:50:19',_binary '');
/*!40000 ALTER TABLE `cliente` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `detalle_prestamo`
--

DROP TABLE IF EXISTS `detalle_prestamo`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `detalle_prestamo` (
  `codigo` int NOT NULL AUTO_INCREMENT,
  `fecha` date NOT NULL,
  `fecha_aplicado` datetime DEFAULT NULL,
  `prestamo` int NOT NULL,
  `numero_cuota` int NOT NULL,
  `valor_cuota` double NOT NULL,
  `monto_pagado` double NOT NULL,
  `monto_pendiente` double NOT NULL,
  `concepto` varchar(45) NOT NULL,
  `estado` bit(1) NOT NULL,
  PRIMARY KEY (`codigo`),
  KEY `fk_det_prestamo_idx` (`prestamo`),
  CONSTRAINT `fk_det_prestamo` FOREIGN KEY (`prestamo`) REFERENCES `prestamo` (`codigo`)
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `detalle_prestamo`
--

LOCK TABLES `detalle_prestamo` WRITE;
/*!40000 ALTER TABLE `detalle_prestamo` DISABLE KEYS */;
INSERT INTO `detalle_prestamo` VALUES (1,'2025-06-20','2025-06-20 15:57:08',13,1,1650,1650,0,'pago',_binary ''),(2,'2025-06-20','2025-06-20 15:57:08',13,2,1650,1650,0,'pago',_binary ''),(3,'2025-06-20','2025-06-20 15:57:08',13,3,1650,1650,0,'pago',_binary ''),(4,'2025-06-20','2025-06-20 15:57:08',13,4,1650,1650,0,'pago',_binary ''),(5,'2025-06-20','2025-06-20 15:57:08',13,5,1650,1650,0,'pago',_binary ''),(6,'2025-06-20','2025-06-20 15:57:08',13,6,1650,1650,0,'pago',_binary ''),(7,'2025-06-20','2025-06-20 15:57:08',13,7,1650,1650,0,'pago',_binary ''),(8,'2025-06-20','2025-06-20 15:57:08',13,8,1650,1650,0,'pago',_binary ''),(9,'2025-06-20','2025-06-20 15:57:08',13,9,1650,1650,0,'pago',_binary ''),(10,'2025-06-20','2025-06-20 15:57:08',13,10,1650,1650,0,'pago',_binary ''),(11,'2025-06-20','2025-06-20 16:15:12',14,1,625,625,0,'pago',_binary ''),(12,'2025-06-20','2025-06-20 16:15:12',14,2,625,625,0,'pago',_binary ''),(13,'2025-06-20','2025-06-20 16:15:12',14,3,625,625,0,'pago',_binary ''),(14,'2025-06-20','2025-06-20 16:15:12',14,4,625,625,0,'pago',_binary ''),(15,'2025-06-20','2025-06-20 16:19:44',15,1,2000,2000,0,'pago',_binary ''),(16,'2025-06-20','2025-06-20 16:19:44',15,2,2000,2000,0,'pago',_binary ''),(17,'2025-06-20','2025-06-20 16:19:44',15,3,2000,2000,0,'pago',_binary ''),(18,'2025-06-20','2025-06-20 16:19:44',15,4,2000,2000,0,'pago',_binary ''),(19,'2025-06-20','2025-06-20 16:19:44',15,5,2000,2000,0,'pago',_binary ''),(20,'2025-06-20','2025-06-20 16:19:44',15,6,2000,2000,0,'pago',_binary '');
/*!40000 ALTER TABLE `detalle_prestamo` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `periodo`
--

DROP TABLE IF EXISTS `periodo`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `periodo` (
  `codigo` int NOT NULL AUTO_INCREMENT,
  `nombre` varchar(15) NOT NULL,
  `fecha_creacion` datetime NOT NULL,
  `creado_por` varchar(90) DEFAULT NULL,
  `habilitado` bit(1) NOT NULL DEFAULT b'1',
  PRIMARY KEY (`codigo`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `periodo`
--

LOCK TABLES `periodo` WRITE;
/*!40000 ALTER TABLE `periodo` DISABLE KEYS */;
INSERT INTO `periodo` VALUES (5,'DIARIO','2025-06-12 00:00:00','ADMIN',_binary ''),(6,'SEMANAL','2025-06-12 00:00:00','ADMIN',_binary ''),(7,'QUINCENAL','2025-06-12 00:00:00','ADMIN',_binary ''),(8,'MENSUAL','2025-06-12 00:00:00','ADMIN',_binary '');
/*!40000 ALTER TABLE `periodo` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `prestamo`
--

DROP TABLE IF EXISTS `prestamo`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `prestamo` (
  `codigo` int NOT NULL AUTO_INCREMENT,
  `fecha_inicio` date DEFAULT NULL,
  `fecha_final` date DEFAULT NULL,
  `tipo_prestamo` int DEFAULT NULL,
  `nombre_tipo_prestamo` varchar(30) DEFAULT NULL,
  `periodo` int DEFAULT NULL,
  `nombre_periodo` varchar(15) DEFAULT NULL,
  `cantidad_periodo` int DEFAULT NULL,
  `cliente` int DEFAULT NULL,
  `nombre_cliente` varchar(90) DEFAULT NULL,
  `tasa_de_intere` double DEFAULT NULL,
  `monto_prestado` double DEFAULT NULL,
  `monto_intere` double DEFAULT NULL,
  `total` double DEFAULT NULL,
  `total_pagado` double DEFAULT NULL,
  `total_pendiente` double DEFAULT NULL,
  `monto_cuota` double DEFAULT NULL,
  `creado_por` varchar(90) DEFAULT NULL,
  `fecha_creacion` datetime DEFAULT NULL,
  `fecha_actualizacion` datetime DEFAULT NULL,
  `actualizado_por` varchar(90) DEFAULT NULL,
  `anulado` bit(1) NOT NULL DEFAULT b'0',
  `anulado_por` varchar(90) DEFAULT NULL,
  `fecha_anulado` datetime DEFAULT NULL,
  `fecha_primer_pago` date DEFAULT NULL,
  PRIMARY KEY (`codigo`),
  KEY `fk_prestamo_cliente_idx` (`cliente`),
  KEY `fk_prestamo_tp_idx` (`tipo_prestamo`),
  KEY `fk_prestamo_periodo_idx` (`periodo`),
  CONSTRAINT `fk_prestamo_cliente` FOREIGN KEY (`cliente`) REFERENCES `cliente` (`codigo`),
  CONSTRAINT `fk_prestamo_periodo` FOREIGN KEY (`periodo`) REFERENCES `periodo` (`codigo`),
  CONSTRAINT `fk_prestamo_tp` FOREIGN KEY (`tipo_prestamo`) REFERENCES `tipo_prestamo` (`codigo`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `prestamo`
--

LOCK TABLES `prestamo` WRITE;
/*!40000 ALTER TABLE `prestamo` DISABLE KEYS */;
INSERT INTO `prestamo` VALUES (9,'2025-06-18',NULL,1,'TIPO SAM',6,'SEMANAL',10,1,'JOSEFINA DE LOS RIO',40,10000,4000,14000,2800,11200,1400,'ADMIN','2025-06-18 15:27:28',NULL,NULL,_binary '\0',NULL,NULL,'2025-06-27'),(10,'2025-06-18',NULL,1,'TIPO SAM',6,'SEMANAL',8,4,'Pepe rodriguez',20,10000,2000,12000,1500,10500,1500,'ADMIN','2025-06-18 16:36:40',NULL,NULL,_binary '\0',NULL,NULL,'2025-06-21'),(11,'2025-06-20',NULL,3,'PERSONAL',5,'DIARIO',20,9,'RADAMEZ GONZALES',33.33,1500,500,2000,200,1800,100,'ADMIN','2025-06-20 14:51:21',NULL,NULL,_binary '\0',NULL,NULL,'2025-06-21'),(13,'2025-06-20',NULL,3,'PERSONAL',7,'QUINCENAL',10,6,'MAXIMILIANO ALMONTE',10,15000,1500,16500,NULL,NULL,1650,'ADMIN','2025-06-20 15:57:17',NULL,NULL,_binary '\0',NULL,NULL,'2025-06-27'),(14,'2025-06-20',NULL,3,'PERSONAL',6,'SEMANAL',4,4,'Pepe rodriguez',25,2000,500,2500,NULL,NULL,625,'ADMIN','2025-06-20 16:15:21',NULL,NULL,_binary '\0',NULL,NULL,'2025-06-27'),(15,'2025-06-20',NULL,1,'TIPO SAM',7,'QUINCENAL',6,7,'JOSE ALMONTE',20,10000,2000,12000,NULL,NULL,2000,'ADMIN','2025-06-20 16:19:54',NULL,NULL,_binary '\0',NULL,NULL,'2025-06-30');
/*!40000 ALTER TABLE `prestamo` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `recibo_de_ingreso`
--

DROP TABLE IF EXISTS `recibo_de_ingreso`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `recibo_de_ingreso` (
  `codigo` int NOT NULL AUTO_INCREMENT,
  `fecha` date DEFAULT NULL,
  `prestamo` int NOT NULL,
  `cliente` int NOT NULL,
  `nombre_cliente` varchar(90) DEFAULT NULL,
  `total` double DEFAULT NULL,
  `monto_pendiente` double DEFAULT NULL,
  `creado_por` varchar(90) DEFAULT NULL,
  `fecha_creacion` datetime DEFAULT NULL,
  `anulado` bit(1) NOT NULL DEFAULT b'0',
  `anulado_por` varchar(90) DEFAULT NULL,
  `fecha_anulado` datetime DEFAULT NULL,
  `descripcion_pago` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`codigo`),
  KEY `fk_prestamo_cliente_idx` (`cliente`),
  KEY `fk_recibo_prestamo_idx` (`prestamo`),
  CONSTRAINT `fk_recibo_cliente` FOREIGN KEY (`cliente`) REFERENCES `cliente` (`codigo`),
  CONSTRAINT `fk_recibo_prestamo` FOREIGN KEY (`prestamo`) REFERENCES `prestamo` (`codigo`)
) ENGINE=InnoDB AUTO_INCREMENT=24 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `recibo_de_ingreso`
--

LOCK TABLES `recibo_de_ingreso` WRITE;
/*!40000 ALTER TABLE `recibo_de_ingreso` DISABLE KEYS */;
INSERT INTO `recibo_de_ingreso` VALUES (17,'2025-06-18',9,1,'JOSEFINA DE LOS RIO',1400,12600,NULL,'2025-06-18 15:29:46',_binary '\0',NULL,NULL,'Pago 1 de 10'),(18,'2025-06-18',10,3,'Ramon',450,8550,NULL,'2025-06-18 16:05:12',_binary '','ADMIN','2025-06-18 16:49:17','Pago 1 de 20'),(19,'2025-06-18',9,1,'JOSEFINA DE LOS RIO',1400,11200,NULL,'2025-06-18 17:09:53',_binary '','ADMIN','2025-06-18 17:10:38','Pago 2 de 10'),(20,'2025-06-20',11,9,'RADAMEZ GONZALES',100,1900,NULL,'2025-06-20 14:52:17',_binary '\0',NULL,NULL,'Pago 1 de 20'),(21,'2025-06-20',11,9,'RADAMEZ GONZALES',100,1800,NULL,'2025-06-20 15:02:04',_binary '\0',NULL,NULL,'Pago 2 de 20'),(22,'2025-06-20',10,4,'Pepe rodriguez',1500,10500,NULL,'2025-06-20 15:06:59',_binary '\0',NULL,NULL,'Pago 1 de 8'),(23,'2025-06-20',9,1,'JOSEFINA DE LOS RIO',1400,11200,NULL,'2025-06-20 15:25:12',_binary '\0',NULL,NULL,'Pago 2 de 10');
/*!40000 ALTER TABLE `recibo_de_ingreso` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tipo_prestamo`
--

DROP TABLE IF EXISTS `tipo_prestamo`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tipo_prestamo` (
  `codigo` int NOT NULL AUTO_INCREMENT,
  `nombre` varchar(15) NOT NULL,
  `fecha_creacion` datetime NOT NULL,
  `creado_por` varchar(90) DEFAULT NULL,
  `habilitado` bit(1) NOT NULL DEFAULT b'1',
  PRIMARY KEY (`codigo`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tipo_prestamo`
--

LOCK TABLES `tipo_prestamo` WRITE;
/*!40000 ALTER TABLE `tipo_prestamo` DISABLE KEYS */;
INSERT INTO `tipo_prestamo` VALUES (1,'TIPO SAM','2025-09-11 00:00:00','ADMIN',_binary ''),(3,'PERSONAL','2025-06-11 00:00:00','ADMIN',_binary ''),(4,'BARCO','2025-06-11 00:00:00','ADMIN',_binary '');
/*!40000 ALTER TABLE `tipo_prestamo` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-06-20 16:49:51
