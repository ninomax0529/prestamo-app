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
  `capital` double NOT NULL,
  `interes` double NOT NULL,
  `monto_pagado` double NOT NULL,
  `monto_pendiente` double NOT NULL,
  `concepto` varchar(45) NOT NULL,
  `estado` bit(1) NOT NULL,
  `fecha_actualizacion` datetime DEFAULT NULL,
  PRIMARY KEY (`codigo`),
  KEY `fk_det_prestamo_idx` (`prestamo`),
  CONSTRAINT `fk_det_prestamo` FOREIGN KEY (`prestamo`) REFERENCES `prestamo` (`codigo`)
) ENGINE=InnoDB AUTO_INCREMENT=53 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `detalle_prestamo`
--

LOCK TABLES `detalle_prestamo` WRITE;
/*!40000 ALTER TABLE `detalle_prestamo` DISABLE KEYS */;
INSERT INTO `detalle_prestamo` VALUES (29,'2025-07-05','2025-07-01 16:47:59',12,1,2400,2000,400,2400,0,'pago',_binary '','2025-07-01 16:47:59'),(30,'2025-07-06','2025-07-01 17:01:50',12,2,2400,2000,400,2400,0,'pago',_binary '','2025-07-01 17:01:50'),(31,'2025-07-07','2025-07-01 17:12:52',12,3,2400,2000,400,2400,0,'pago',_binary '','2025-07-01 17:12:52'),(32,'2025-07-08','2025-07-01 17:12:52',12,4,2400,2000,400,0,2400,'pago',_binary '\0','2025-07-01 17:12:52'),(33,'2025-07-09','2025-07-01 17:12:52',12,5,2400,2000,400,0,2400,'pago',_binary '\0','2025-07-01 17:12:52'),(34,'2025-07-10','2025-07-01 17:12:52',12,6,2400,2000,400,0,2400,'pago',_binary '\0','2025-07-01 17:12:52'),(35,'2025-07-11','2025-07-01 17:12:52',12,7,2400,2000,400,0,2400,'pago',_binary '\0','2025-07-01 17:12:52'),(36,'2025-07-12','2025-07-01 17:12:52',12,8,2400,2000,400,0,2400,'pago',_binary '\0','2025-07-01 17:12:52'),(37,'2025-07-13','2025-07-01 17:12:52',12,9,2400,2000,400,0,2400,'pago',_binary '\0','2025-07-01 17:12:52'),(38,'2025-07-14','2025-07-01 17:12:52',12,10,2400,2000,400,0,2400,'pago',_binary '\0','2025-07-01 17:12:52'),(39,'2025-07-15','2025-07-01 11:18:32',13,1,3000,2500,500,0,3000,'pago',_binary '\0',NULL),(40,'2025-07-30','2025-07-01 11:18:32',13,2,3000,2500,500,0,3000,'pago',_binary '\0',NULL),(41,'2025-08-14','2025-07-01 11:18:32',13,3,3000,2500,500,0,3000,'pago',_binary '\0',NULL),(42,'2025-08-29','2025-07-01 11:18:32',13,4,3000,2500,500,0,3000,'pago',_binary '\0',NULL),(43,'2025-09-13','2025-07-01 11:18:32',13,5,3000,2500,500,0,3000,'pago',_binary '\0',NULL),(44,'2025-09-28','2025-07-01 11:18:32',13,6,3000,2500,500,0,3000,'pago',_binary '\0',NULL),(45,'2025-10-13','2025-07-01 11:18:32',13,7,3000,2500,500,0,3000,'pago',_binary '\0',NULL),(46,'2025-10-28','2025-07-01 11:18:32',13,8,3000,2500,500,0,3000,'pago',_binary '\0',NULL),(47,'2025-07-04','2025-07-02 09:22:32',14,1,1250,1000,250,0,1250,'pago',_binary '\0',NULL),(48,'2025-07-11','2025-07-02 09:22:32',14,2,1250,1000,250,0,1250,'pago',_binary '\0',NULL),(49,'2025-07-18','2025-07-02 09:22:32',14,3,1250,1000,250,0,1250,'pago',_binary '\0',NULL),(50,'2025-07-25','2025-07-02 09:22:32',14,4,1250,1000,250,0,1250,'pago',_binary '\0',NULL),(51,'2025-07-04','2025-07-02 11:21:19',15,1,1250,1000,250,1250,0,'pago',_binary '','2025-07-02 11:21:19'),(52,'2025-07-11','2025-07-02 11:21:55',15,2,1250,1000,250,1250,0,'pago',_binary '','2025-07-02 11:21:55');
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
  `monto_capital` double DEFAULT NULL,
  `monto_interes` double DEFAULT NULL,
  `monto_pendiente` double NOT NULL,
  `concepto` varchar(120) NOT NULL,
  PRIMARY KEY (`codigo`),
  KEY `fk_det_recibo_idx` (`recibo`),
  CONSTRAINT `fk_det_recibo` FOREIGN KEY (`recibo`) REFERENCES `recibo_de_ingreso` (`codigo`)
) ENGINE=InnoDB AUTO_INCREMENT=49 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `detalle_recibo_de_ingreso`
--

LOCK TABLES `detalle_recibo_de_ingreso` WRITE;
/*!40000 ALTER TABLE `detalle_recibo_de_ingreso` DISABLE KEYS */;
INSERT INTO `detalle_recibo_de_ingreso` VALUES (37,28,29,1,300,0,300,2100,'Abono a cuota numero 1'),(38,29,29,1,2100,1700,400,0,'Abono a cuota numero 1'),(39,30,29,1,2100,1800,100,0,'Abono a cuota numero 1'),(40,31,29,1,2100,2000,100,0,'Abono a cuota numero 1'),(41,32,30,2,500,100,400,1900,'Abono a cuota numero 2'),(42,33,30,2,1900,1900,0,0,'Abono a cuota numero 2'),(43,34,31,3,2100,1700,400,300,'Abono a cuota numero 3'),(44,35,31,3,100,100,0,200,'Abono a cuota numero 3'),(45,36,31,3,200,200,0,0,'Abono a cuota numero 3'),(46,37,31,3,200,200,0,0,'Abono a cuota numero 3'),(47,38,51,1,1250,1000,250,0,'Pago cuota 1 de 2'),(48,39,52,2,1250,1000,250,0,'Pago cuota 2 de 2');
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
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `prestamo`
--

LOCK TABLES `prestamo` WRITE;
/*!40000 ALTER TABLE `prestamo` DISABLE KEYS */;
INSERT INTO `prestamo` VALUES (12,'2025-07-01',NULL,1,' SAM',5,'DIARIO',10,1,'JOSEFINA DE LOS RIO',20,20000,4000,24000,7200,16800,2400,'ADMIN','2025-07-01 11:13:18',NULL,NULL,_binary '\0',NULL,NULL,'2025-07-05'),(13,'2025-07-01',NULL,3,'PERSONAL',7,'QUINCENAL',8,4,'Pepe rodriguez',20,20000,4000,24000,0,20000,3000,'ADMIN','2025-07-01 11:19:38',NULL,NULL,_binary '\0',NULL,NULL,'2025-07-15'),(14,'2025-07-02',NULL,1,' SAM',6,'SEMANAL',4,8,'RAFAEL PEREZ',25,4000,1000,5000,0,4000,1250,'ADMIN','2025-07-02 09:22:46',NULL,NULL,_binary '\0',NULL,NULL,'2025-07-04'),(15,'2025-07-02',NULL,1,' SAM',6,'SEMANAL',2,10,'EUGENIO ALMONTE JIMENEZ',25,2000,500,2500,2500,0,1250,'ADMIN','2025-07-02 11:21:02',NULL,NULL,_binary '\0',NULL,NULL,'2025-07-04');
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
) ENGINE=InnoDB AUTO_INCREMENT=40 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `recibo_de_ingreso`
--

LOCK TABLES `recibo_de_ingreso` WRITE;
/*!40000 ALTER TABLE `recibo_de_ingreso` DISABLE KEYS */;
INSERT INTO `recibo_de_ingreso` VALUES (28,'2025-07-01',12,1,'JOSEFINA DE LOS RIO',300,23700,'ADMIN','2025-07-01 14:29:46',_binary '\0',NULL,NULL,'Abono a cuota numero 1\n'),(29,'2025-07-01',12,1,'JOSEFINA DE LOS RIO',2100,21600,'ADMIN','2025-07-01 15:12:11',_binary '','ADMIN','2025-07-01 16:24:06','Abono a cuota numero 1\n'),(30,'2025-07-01',12,1,'JOSEFINA DE LOS RIO',2100,21600,'ADMIN','2025-07-01 16:44:55',_binary '','ADMIN','2025-07-01 16:47:39','Abono a cuota numero 1\n'),(31,'2025-07-01',12,1,'JOSEFINA DE LOS RIO',2100,21600,'ADMIN','2025-07-01 16:47:59',_binary '\0',NULL,NULL,'Abono a cuota numero 1\n'),(32,'2025-07-01',12,1,'JOSEFINA DE LOS RIO',500,21100,'ADMIN','2025-07-01 16:52:54',_binary '\0',NULL,NULL,'Abono a cuota numero 2\n'),(33,'2025-07-01',12,1,'JOSEFINA DE LOS RIO',1900,19200,'ADMIN','2025-07-01 17:01:50',_binary '\0',NULL,NULL,'Abono a cuota numero 2\n'),(34,'2025-07-01',12,1,'JOSEFINA DE LOS RIO',2100,17100,'ADMIN','2025-07-01 17:05:07',_binary '\0',NULL,NULL,'Abono a cuota numero 3\n'),(35,'2025-07-01',12,1,'JOSEFINA DE LOS RIO',100,17000,'ADMIN','2025-07-01 17:09:37',_binary '\0',NULL,NULL,'Abono a cuota numero 3\n'),(36,'2025-07-01',12,1,'JOSEFINA DE LOS RIO',200,16800,'ADMIN','2025-07-01 17:11:07',_binary '','ADMIN','2025-07-01 17:12:28','Abono a cuota numero 3\n'),(37,'2025-07-01',12,1,'JOSEFINA DE LOS RIO',200,16800,'ADMIN','2025-07-01 17:12:52',_binary '\0',NULL,NULL,'Abono a cuota numero 3\n'),(38,'2025-07-02',15,10,'EUGENIO ALMONTE JIMENEZ',1250,1250,'ADMIN','2025-07-02 11:21:19',_binary '\0',NULL,NULL,'Pago cuota 1 de 2\n'),(39,'2025-07-02',15,10,'EUGENIO ALMONTE JIMENEZ',1250,0,'ADMIN','2025-07-02 11:21:55',_binary '\0',NULL,NULL,'Pago cuota 2 de 2\n');
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

--
-- Dumping events for database 'db_prestamo'
--

--
-- Dumping routines for database 'db_prestamo'
--
/*!50003 DROP FUNCTION IF EXISTS `F_CAPITAL_COBRADO` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` FUNCTION `F_CAPITAL_COBRADO`(p_fecha date) RETURNS double
    READS SQL DATA
    DETERMINISTIC
BEGIN
    DECLARE monto_pago DOUBLE DEFAULT 0;

    SELECT  round(SUM(d. monto_capital),4)
     INTO monto_pago
    FROM  recibo_de_ingreso o
    inner join detalle_recibo_de_ingreso d on o.codigo=d.recibo
    
    WHERE anulado = false
      AND month(fecha)=month(p_fecha) ;
 
    RETURN IFNULL(monto_pago, 0);
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP FUNCTION IF EXISTS `F_CAPITAL_PRESTADO` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` FUNCTION `F_CAPITAL_PRESTADO`(p_fecha date) RETURNS double
    READS SQL DATA
    DETERMINISTIC
BEGIN
    DECLARE monto  DOUBLE DEFAULT 0;

    SELECT  round(SUM(monto_prestado),4)
     INTO  monto
     from prestamo p      
      WHERE anulado = false
      AND month(fecha_inicio)=month(p_fecha) ;
 
    RETURN IFNULL(monto, 0);
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP FUNCTION IF EXISTS `F_INTERES_COBRADO` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` FUNCTION `F_INTERES_COBRADO`(p_fecha date) RETURNS double
    READS SQL DATA
    DETERMINISTIC
BEGIN
    DECLARE monto_pago DOUBLE DEFAULT 0;

    SELECT  round(SUM(d. monto_interes),4)
     INTO monto_pago
    FROM  recibo_de_ingreso o
    inner join detalle_recibo_de_ingreso d on o.codigo=d.recibo
    
    WHERE anulado = false
      AND month(fecha)=month(p_fecha) ;
 
    RETURN IFNULL(monto_pago, 0);
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP FUNCTION IF EXISTS `F_INTERES_PAGADO_A_CUOTA` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` FUNCTION `F_INTERES_PAGADO_A_CUOTA`(p_prestamo INT, p_cuota INT) RETURNS double
    READS SQL DATA
    DETERMINISTIC
BEGIN
    DECLARE monto_pago DOUBLE DEFAULT 0;

    SELECT  round(SUM(d. monto_interes),4)
  INTO monto_pago
    FROM  recibo_de_ingreso o
    inner join detalle_recibo_de_ingreso d on o.codigo=d.recibo
    
    WHERE anulado = false
      AND prestamo = p_prestamo
      and cuota= p_cuota ;

    RETURN IFNULL(monto_pago, 0);
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP FUNCTION IF EXISTS `F_TOTAL_ABONO` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` FUNCTION `F_TOTAL_ABONO`(p_prestamo INT) RETURNS double
    READS SQL DATA
    DETERMINISTIC
BEGIN
    DECLARE monto_pago DOUBLE DEFAULT 0;

    SELECT  round(SUM(d. total),4)
  INTO monto_pago
    FROM  recibo_de_ingreso o
    inner join detalle_recibo_de_ingreso d on o.codigo=d.recibo
    
    WHERE anulado = false
      AND prestamo = p_prestamo;

    RETURN IFNULL(monto_pago, 0);
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP FUNCTION IF EXISTS `F_TOTAL_ABONO_CUOTA` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` FUNCTION `F_TOTAL_ABONO_CUOTA`(p_prestamo INT, p_cuota INT) RETURNS double
    READS SQL DATA
    DETERMINISTIC
BEGIN
    DECLARE monto_pago DOUBLE DEFAULT 0;

    SELECT  round(SUM(d. total),4)
  INTO monto_pago
    FROM  recibo_de_ingreso o
    inner join detalle_recibo_de_ingreso d on o.codigo=d.recibo
    
    WHERE anulado = false
      AND prestamo = p_prestamo
      and cuota= p_cuota ;

    RETURN IFNULL(monto_pago, 0);
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-07-02 17:53:15
