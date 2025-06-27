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
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cliente`
--

LOCK TABLES `cliente` WRITE;
/*!40000 ALTER TABLE `cliente` DISABLE KEYS */;
INSERT INTO `cliente` VALUES (1,'JOSEFINA DE LOS RIO','034-002921-4','829-276-5148','SANTIAGO DE LOS CABALLEROS','Cementos cibao cc',NULL,'MAXIMILIANO','2025-06-25 17:53:58','max','2025-06-25 17:53:58',_binary ''),(2,'WQEQ','QWE','WE','QWE','EWQ',NULL,'QWE','2025-06-18 17:01:27','nino','2025-06-18 17:01:27',_binary '\0'),(3,'Ramon','wrewe','dgdfdg','wre','fdgdf',NULL,'dfgd','2025-06-18 17:01:33',NULL,'2025-06-18 17:01:33',_binary '\0'),(4,'Pepe rodriguez','21212','21213232','asdsad','Cemento cibao',NULL,'rafael','2025-06-20 09:35:51',NULL,'2025-06-20 09:35:51',_binary ''),(5,'hipolito barrero','031-212113','809-213-12421','mao valverde','super edwar',NULL,'nino almonte','2025-06-12 11:20:28',NULL,'2025-06-12 11:20:28',_binary ''),(6,'MAXIMILIANO ALMONTE','034-00-44420-8','809-280-7944','LAS COLINA 29-5','CEMENTOS CIBAO',NULL,'WELINTON','2025-06-20 09:50:43',NULL,'2025-06-20 09:50:43',_binary ''),(7,'JOSE ALMONTE','034-0044412-7','809-542-4527','LOS JARDINES SANTIAGO','LA COROMINA',NULL,'PEDRO RODRIGUEZ','2025-06-20 11:10:29',NULL,'2025-06-20 11:10:29',_binary ''),(8,'RAFAEL PEREZ','031-214235-4','809-425-4136','JACAGUA SANTIAGO','CECONSA SANTIAGO',NULL,'JOSELITO  PEREZ','2025-06-20 14:43:18',NULL,'2025-06-20 14:43:18',_binary ''),(9,'RADAMEZ GONZALES','031-214235-4','809-425-4139','BUENOS AIRES','CECONSA SANTIAGO',NULL,'JOSELITO  PEREZ','2025-06-20 14:50:19',NULL,'2025-06-20 14:50:19',_binary ''),(10,'EUGENIO ALMONTE JIMENEZ','034-009921-8','809-869-1750','MAO VALVERDE RESTAURACION 52 SANANTONIO','CASA U  HOGAR',NULL,'NINO','2025-06-24 15:57:13',NULL,'2025-06-24 15:57:13',_binary ''),(11,'BERNALDO GOMEZ','031-235-2124-2','809-235-4521','SANTIAGO','COROMINA',NULL,'MAXIMO','2025-06-25 13:40:45',NULL,'2025-06-25 13:40:45',_binary '');
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
) ENGINE=InnoDB AUTO_INCREMENT=23 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `detalle_prestamo`
--

LOCK TABLES `detalle_prestamo` WRITE;
/*!40000 ALTER TABLE `detalle_prestamo` DISABLE KEYS */;
INSERT INTO `detalle_prestamo` VALUES (1,'2025-06-30','2025-06-25 16:49:30',1,1,1000,1000,0,'pago',_binary ''),(2,'2025-07-15','2025-06-25 16:49:30',1,2,1000,1000,0,'pago',_binary ''),(3,'2025-07-30','2025-06-25 16:49:30',1,3,1000,1000,0,'pago',_binary ''),(4,'2025-08-14','2025-06-25 16:49:30',1,4,1000,1000,0,'pago',_binary ''),(5,'2025-08-29','2025-06-25 16:49:30',1,5,1000,1000,0,'pago',_binary ''),(6,'2025-09-13','2025-06-25 16:49:30',1,6,1000,300,700,'pago',_binary ''),(7,'2025-06-27','2025-06-25 16:54:39',2,1,1250,1250,0,'pago',_binary ''),(8,'2025-07-04','2025-06-25 16:54:39',2,2,1250,1250,0,'pago',_binary ''),(9,'2025-07-11','2025-06-25 16:54:39',2,3,1250,0,1250,'pago',_binary '\0'),(10,'2025-07-18','2025-06-25 16:54:39',2,4,1250,0,1250,'pago',_binary '\0'),(11,'2025-07-15','2025-06-25 16:56:29',3,1,1250,1250,0,'pago',_binary ''),(12,'2025-07-30','2025-06-25 16:56:29',3,2,1250,750,500,'pago',_binary ''),(13,'2025-08-14','2025-06-25 16:56:29',3,3,1250,0,1250,'pago',_binary '\0'),(14,'2025-08-29','2025-06-25 16:56:29',3,4,1250,0,1250,'pago',_binary '\0'),(15,'2025-09-13','2025-06-25 16:56:29',3,5,1250,0,1250,'pago',_binary '\0'),(16,'2025-09-28','2025-06-25 16:56:29',3,6,1250,0,1250,'pago',_binary '\0'),(17,'2025-06-30','2025-06-26 11:22:32',4,1,1000,1000,0,'pago',_binary ''),(18,'2025-07-15','2025-06-26 11:22:32',4,2,1000,1000,0,'pago',_binary ''),(19,'2025-07-30','2025-06-26 11:22:32',4,3,1000,1000,0,'pago',_binary ''),(20,'2025-08-14','2025-06-26 11:22:32',4,4,1000,1000,0,'pago',_binary ''),(21,'2025-08-29','2025-06-26 11:22:32',4,5,1000,1000,0,'pago',_binary ''),(22,'2025-09-13','2025-06-26 11:22:32',4,6,1000,1000,0,'pago',_binary '');
/*!40000 ALTER TABLE `detalle_prestamo` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `detalle_recibo_de_ingreso`
--

DROP TABLE IF EXISTS `detalle_recibo_de_ingreso`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `detalle_recibo_de_ingreso` (
  `codigo` int NOT NULL AUTO_INCREMENT,
  `recibo` int NOT NULL,
  `cuota` int DEFAULT NULL,
  `numero_cuota` int DEFAULT NULL,
  `total` double NOT NULL,
  `monto_pendiente` double NOT NULL,
  `concepto` varchar(120) NOT NULL,
  PRIMARY KEY (`codigo`),
  KEY `fk_det_recibo_idx` (`recibo`),
  CONSTRAINT `fk_det_recibo` FOREIGN KEY (`recibo`) REFERENCES `recibo_de_ingreso` (`codigo`)
) ENGINE=InnoDB AUTO_INCREMENT=32 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `detalle_recibo_de_ingreso`
--

LOCK TABLES `detalle_recibo_de_ingreso` WRITE;
/*!40000 ALTER TABLE `detalle_recibo_de_ingreso` DISABLE KEYS */;
INSERT INTO `detalle_recibo_de_ingreso` VALUES (1,1,11,1,1250,0,'Pago cuota 1 de 6'),(2,2,1,1,500,500,'Abono a cuota numero 1'),(3,3,1,1,500,0,'Abono a cuota numero 1'),(4,4,2,2,1000,0,'Pago cuota 2 de 6'),(5,5,3,3,1000,0,'Pago cuota 3 de 6'),(6,6,4,4,500,500,'Abono a cuota numero 4'),(7,7,4,4,300,200,'Abono a cuota numero 4'),(8,7,5,5,600,400,'Abono a cuota numero 5'),(9,8,4,4,100,100,'Abono a cuota numero 4'),(10,8,5,5,400,0,'Abono a cuota numero 5'),(11,9,4,4,50,50,'Abono a cuota numero 4'),(12,10,4,4,50,0,'Abono a cuota numero 4'),(13,10,6,6,300,700,'Abono a cuota numero 6'),(14,11,7,1,100,1150,'Abono a cuota numero 1'),(15,12,7,1,250,900,'Abono a cuota numero 1'),(16,13,7,1,450,450,'Abono a cuota numero 1'),(17,14,7,1,450,0,'Abono a cuota numero 1'),(18,14,8,2,1250,0,'Pago cuota 2 de 4'),(19,15,11,1,1250,0,'Pago cuota 1 de 6'),(20,16,12,2,500,750,'Abono a cuota numero 2'),(21,17,11,1,0,1250,'Abono a cuota numero 1'),(22,18,11,1,1250,0,'Pago cuota 1 de 6'),(23,19,12,2,750,500,'Abono a cuota numero 2'),(24,20,17,1,1000,0,'Pago cuota 1 de 6'),(25,20,18,2,500,500,'Abono a cuota numero 2'),(26,21,18,2,120,380,'Abono a cuota numero 2'),(27,22,18,2,380,0,'Abono a cuota numero 2'),(28,22,19,3,1000,0,'Pago cuota 3 de 6'),(29,22,20,4,1000,0,'Pago cuota 4 de 6'),(30,22,21,5,1000,0,'Pago cuota 5 de 6'),(31,22,22,6,1000,0,'Pago cuota 6 de 6');
/*!40000 ALTER TABLE `detalle_recibo_de_ingreso` ENABLE KEYS */;
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
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `prestamo`
--

LOCK TABLES `prestamo` WRITE;
/*!40000 ALTER TABLE `prestamo` DISABLE KEYS */;
INSERT INTO `prestamo` VALUES (1,'2025-06-25',NULL,1,'SAM',7,'QUINCENAL',6,1,'JOSEFINA DE LOS RIO',20,5000,1000,6000,5300,700,1000,'ADMIN','2025-06-25 16:49:50',NULL,NULL,_binary '\0',NULL,NULL,'2025-06-30'),(2,'2025-06-25',NULL,3,'PERSONAL',6,'SEMANAL',4,6,'MAXIMILIANO ALMONTE',25,4000,1000,5000,2500,2500,1250,'ADMIN','2025-06-25 16:54:53',NULL,NULL,_binary '\0',NULL,NULL,'2025-06-27'),(3,'2025-06-25',NULL,1,'SAM',7,'QUINCENAL',6,11,'BERNALDO GOMEZ',25,6000,1500,7500,2000,5500,1250,'ADMIN','2025-06-25 16:56:38',NULL,NULL,_binary '\0',NULL,NULL,'2025-07-15'),(4,'2025-06-26',NULL,1,'SAM',7,'QUINCENAL',6,4,'Pepe rodriguez',20,5000,1000,6000,6000,0,1000,'ADMIN','2025-06-26 11:22:47',NULL,NULL,_binary '\0',NULL,NULL,'2025-06-30');
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
  `descripcion_pago` varchar(150) DEFAULT NULL,
  PRIMARY KEY (`codigo`),
  KEY `fk_prestamo_cliente_idx` (`cliente`),
  KEY `fk_recibo_prestamo_idx` (`prestamo`),
  CONSTRAINT `fk_recibo_cliente` FOREIGN KEY (`cliente`) REFERENCES `cliente` (`codigo`),
  CONSTRAINT `fk_recibo_prestamo` FOREIGN KEY (`prestamo`) REFERENCES `prestamo` (`codigo`)
) ENGINE=InnoDB AUTO_INCREMENT=23 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `recibo_de_ingreso`
--

LOCK TABLES `recibo_de_ingreso` WRITE;
/*!40000 ALTER TABLE `recibo_de_ingreso` DISABLE KEYS */;
INSERT INTO `recibo_de_ingreso` VALUES (1,'2025-06-25',3,11,'BERNALDO GOMEZ',1250,6250,'ADMIN','2025-06-25 16:57:24',_binary '','ADMIN','2025-06-25 17:07:17','Pago cuota 1 de 6\n'),(2,'2025-06-25',1,1,'JOSEFINA DE LOS RIO',1000,5000,'ADMIN','2025-06-25 17:09:42',_binary '\0',NULL,NULL,'Abono a cuota numero 1\n'),(3,'2025-06-25',1,1,'JOSEFINA DE LOS RIO',1000,4500,'ADMIN','2025-06-25 17:14:54',_binary '\0',NULL,NULL,'Abono a cuota numero 1\n'),(4,'2025-06-25',1,1,'JOSEFINA DE LOS RIO',1000,5000,'ADMIN','2025-06-25 17:29:39',_binary '\0',NULL,NULL,'Pago cuota 2 de 6\n'),(5,'2025-06-25',1,1,'JOSEFINA DE LOS RIO',1000,3000,'ADMIN','2025-06-25 17:33:11',_binary '\0',NULL,NULL,'Pago cuota 3 de 6\n'),(6,'2025-06-25',1,1,'JOSEFINA DE LOS RIO',1000,2000,'ADMIN','2025-06-25 17:34:53',_binary '\0',NULL,NULL,'Abono a cuota numero 4\n'),(7,'2025-06-25',1,1,'JOSEFINA DE LOS RIO',0,1600,'ADMIN','2025-06-25 17:49:42',_binary '\0',NULL,NULL,'Abono a cuota numero 4\nAbono a cuota numero 5\n'),(8,'2025-06-26',1,1,'JOSEFINA DE LOS RIO',500,1100,'ADMIN','2025-06-26 09:53:35',_binary '\0',NULL,NULL,'Abono a cuota numero 4\nAbono a cuota numero 5\n'),(9,'2025-06-26',1,1,'JOSEFINA DE LOS RIO',50,1050,'ADMIN','2025-06-26 09:54:37',_binary '\0',NULL,NULL,'Abono a cuota numero 4\n'),(10,'2025-06-26',1,1,'JOSEFINA DE LOS RIO',350,700,'ADMIN','2025-06-26 09:55:06',_binary '\0',NULL,NULL,'Abono a cuota numero 4\nAbono a cuota numero 6\n'),(11,'2025-06-26',2,6,'MAXIMILIANO ALMONTE',100,4900,'ADMIN','2025-06-26 10:44:44',_binary '\0',NULL,NULL,'Abono a cuota numero 1\n'),(12,'2025-06-26',2,6,'MAXIMILIANO ALMONTE',250,4650,'ADMIN','2025-06-26 10:57:21',_binary '\0',NULL,NULL,'Abono a cuota numero 1\n'),(13,'2025-06-26',2,6,'MAXIMILIANO ALMONTE',450,4200,'ADMIN','2025-06-26 11:02:44',_binary '\0',NULL,NULL,'Abono a cuota numero 1\n'),(14,'2025-06-26',2,6,'MAXIMILIANO ALMONTE',1700,2500,'ADMIN','2025-06-26 11:04:38',_binary '\0',NULL,NULL,'Abono a cuota numero 1\nPago cuota 2 de 4\n'),(15,'2025-06-26',3,11,'BERNALDO GOMEZ',1250,6250,'ADMIN','2025-06-26 11:09:50',_binary '','ADMIN','2025-06-26 11:11:52','Pago cuota 1 de 6\n'),(16,'2025-06-26',3,11,'BERNALDO GOMEZ',500,5750,'ADMIN','2025-06-26 11:10:26',_binary '','ADMIN','2025-06-26 11:11:55','Abono a cuota numero 2\n'),(17,'2025-06-26',3,11,'BERNALDO GOMEZ',0,7500,'ADMIN','2025-06-26 11:17:02',_binary '','ADMIN','2025-06-26 11:17:23','Abono a cuota numero 1\n'),(18,'2025-06-26',3,11,'BERNALDO GOMEZ',1250,6250,'ADMIN','2025-06-26 11:18:02',_binary '\0',NULL,NULL,'Pago cuota 1 de 6\n'),(19,'2025-06-26',3,11,'BERNALDO GOMEZ',750,5500,'ADMIN','2025-06-26 11:19:07',_binary '\0',NULL,NULL,'Abono a cuota numero 2\n'),(20,'2025-06-26',4,4,'Pepe rodriguez',1500,4500,'ADMIN','2025-06-26 11:23:55',_binary '\0',NULL,NULL,'Pago cuota 1 de 6\nAbono a cuota numero 2\n'),(21,'2025-06-26',4,4,'Pepe rodriguez',120,4380,'ADMIN','2025-06-26 14:43:49',_binary '\0',NULL,NULL,'Abono a cuota numero 2\n'),(22,'2025-06-27',4,4,'Pepe rodriguez',4380,0,'ADMIN','2025-06-27 15:39:35',_binary '\0',NULL,NULL,'Abono a cuota numero 2\nPago cuota 3 de 6\nPago cuota 4 de 6\nPago cuota 5 de 6\nPago cuota 6 de 6\n');
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
INSERT INTO `tipo_prestamo` VALUES (1,' SAM','2025-09-11 00:00:00','ADMIN',_binary ''),(3,'PERSONAL','2025-06-11 00:00:00','ADMIN',_binary ''),(4,'BARCO','2025-06-11 00:00:00','ADMIN',_binary '');
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

-- Dump completed on 2025-06-27 16:47:04
