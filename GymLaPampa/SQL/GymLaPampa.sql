drop database if exists `gym`;
CREATE DATABASE  IF NOT EXISTS `gym` /*!40100 DEFAULT CHARACTER SET latin1 */;
USE `gym`;
-- MySQL dump 10.13  Distrib 5.6.23, for Win64 (x86_64)
--
-- Host: localhost    Database: gym
-- ------------------------------------------------------
-- Server version	5.6.14

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
-- Table structure for table `arancels`
--

DROP TABLE IF EXISTS `arancels`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `arancels` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(80) DEFAULT NULL,
  `precio` float DEFAULT NULL,
  `fecha` date DEFAULT NULL,
  `activo` int(5) DEFAULT NULL,
  `categoria` varchar(15) DEFAULT NULL,
  `dias` int(2) DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `arancels`
--

LOCK TABLES `arancels` WRITE;
/*!40000 ALTER TABLE `arancels` DISABLE KEYS */;
/*!40000 ALTER TABLE `arancels` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `articulos`
--

DROP TABLE IF EXISTS `articulos`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `articulos` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `codigo` varchar(10) DEFAULT NULL,
  `articulo` varchar(20) DEFAULT NULL,
  `precio` float DEFAULT NULL,
  `precio_compra` float DEFAULT NULL,
  `stock` int(11) DEFAULT NULL,
  `descripcion` varchar(120) DEFAULT NULL,
  `proveedor_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `articulos`
--

LOCK TABLES `articulos` WRITE;
/*!40000 ALTER TABLE `articulos` DISABLE KEYS */;
/*!40000 ALTER TABLE `articulos` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `articulos_compras`
--

DROP TABLE IF EXISTS `articulos_compras`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `articulos_compras` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `compra_id` int(11) DEFAULT NULL,
  `articulo_id` int(11) DEFAULT NULL,
  `cantidad` float NOT NULL,
  `precio_final` float DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `articulos_compras`
--

LOCK TABLES `articulos_compras` WRITE;
/*!40000 ALTER TABLE `articulos_compras` DISABLE KEYS */;
/*!40000 ALTER TABLE `articulos_compras` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `articulos_ventas`
--

DROP TABLE IF EXISTS `articulos_ventas`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `articulos_ventas` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `venta_id` int(11) DEFAULT NULL,
  `articulo_id` int(11) DEFAULT NULL,
  `cantidad` int(11) NOT NULL,
  `precio_final` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `articulos_ventas`
--

LOCK TABLES `articulos_ventas` WRITE;
/*!40000 ALTER TABLE `articulos_ventas` DISABLE KEYS */;
/*!40000 ALTER TABLE `articulos_ventas` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `asistencias`
--

DROP TABLE IF EXISTS `asistencias`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `asistencias` (
  `ID_ASISTENCIA` int(11) NOT NULL AUTO_INCREMENT,
  `ID_DATOS_PERS` int(11) NOT NULL,
  `FECHA` date DEFAULT NULL,
  `ID_ACTIV` int(11) DEFAULT NULL,
  `ID_ACTIV_COMBO` int(11) DEFAULT NULL,
  PRIMARY KEY (`ID_ASISTENCIA`),
  KEY `FK_ASISTENCIA` (`ID_DATOS_PERS`),
  CONSTRAINT `FK_ASISTENCIA` FOREIGN KEY (`ID_DATOS_PERS`) REFERENCES `socios` (`ID_DATOS_PERS`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `asistencias`
--

LOCK TABLES `asistencias` WRITE;
/*!40000 ALTER TABLE `asistencias` DISABLE KEYS */;
/*!40000 ALTER TABLE `asistencias` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `categorias`
--

DROP TABLE IF EXISTS `categorias`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `categorias` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `categorias`
--

LOCK TABLES `categorias` WRITE;
/*!40000 ALTER TABLE `categorias` DISABLE KEYS */;
INSERT INTO `categorias` VALUES (1,'AEROBICO'),(2,'PILATES'),(3,'SPINNING'),(4,'MUSCULACION'),(5,'COMBO'),(6,'COMPRAS');
/*!40000 ALTER TABLE `categorias` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `clase`
--

DROP TABLE IF EXISTS `clase`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `clase` (
  `ID_CLASE` int(11) NOT NULL AUTO_INCREMENT,
  `ID_PROFES` int(11) NOT NULL,
  `FECHA` date DEFAULT NULL,
  `ACTIV` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`ID_CLASE`),
  KEY `FK_CLASE` (`ID_PROFES`),
  CONSTRAINT `FK_CLASE` FOREIGN KEY (`ID_PROFES`) REFERENCES `profes` (`ID_PROFES`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `clase`
--

LOCK TABLES `clase` WRITE;
/*!40000 ALTER TABLE `clase` DISABLE KEYS */;
/*!40000 ALTER TABLE `clase` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `combos`
--

DROP TABLE IF EXISTS `combos`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `combos` (
  `id_combo` int(11) NOT NULL,
  `id_activ` int(11) NOT NULL,
  `dias` int(11) DEFAULT NULL,
  PRIMARY KEY (`id_combo`,`id_activ`),
  KEY `fk_activ_combo` (`id_activ`),
  CONSTRAINT `fk_activ_combo` FOREIGN KEY (`id_activ`) REFERENCES `arancels` (`id`) ON DELETE CASCADE ON UPDATE NO ACTION,
  CONSTRAINT `fk_combo` FOREIGN KEY (`id_combo`) REFERENCES `arancels` (`id`) ON DELETE CASCADE ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `combos`
--

LOCK TABLES `combos` WRITE;
/*!40000 ALTER TABLE `combos` DISABLE KEYS */;
/*!40000 ALTER TABLE `combos` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `compras`
--

DROP TABLE IF EXISTS `compras`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `compras` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `monto` float DEFAULT NULL,
  `proveedor_id` int(11) DEFAULT NULL,
  `fecha` date NOT NULL,
  `pago` int(11) DEFAULT '0',
  `fecha_pago` date DEFAULT NULL,
  `descuento` float DEFAULT '0',
  `pago_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `compras`
--

LOCK TABLES `compras` WRITE;
/*!40000 ALTER TABLE `compras` DISABLE KEYS */;
/*!40000 ALTER TABLE `compras` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `datos`
--

DROP TABLE IF EXISTS `datos`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `datos` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `descripcion` varchar(200) DEFAULT NULL,
  `categoria_id` int(11) DEFAULT NULL,
  `ingreso_egreso` varchar(7) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `datos`
--

LOCK TABLES `datos` WRITE;
/*!40000 ALTER TABLE `datos` DISABLE KEYS */;
INSERT INTO `datos` VALUES (1,'ARANCEL',1,'ingreso'),(2,'ARANCEL',2,'ingreso'),(3,'ARANCEL',3,'ingreso'),(4,'ARANCEL',4,'ingreso'),(5,'ARANCEL',5,'ingreso');
/*!40000 ALTER TABLE `datos` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `fichas`
--

DROP TABLE IF EXISTS `fichas`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `fichas` (
  `ID_FICHA_MEDICA` int(11) NOT NULL AUTO_INCREMENT,
  `ID_DATOS_PERS` int(11) NOT NULL,
  `TEL_EMERG` varchar(20) DEFAULT NULL,
  `GRUPO_SANG` varchar(2) DEFAULT NULL,
  `FACTOR` varchar(3) DEFAULT NULL,
  `ALERGICO` varchar(30) DEFAULT NULL,
  `MEDICAM` varchar(30) DEFAULT NULL,
  `OBSERV` varchar(3000) DEFAULT NULL,
  `ARTROSIS` int(2) DEFAULT NULL,
  `ASMA` int(2) DEFAULT NULL,
  `CARDIACO` int(2) DEFAULT NULL,
  `DIABETES` int(2) DEFAULT NULL,
  `EMBARAZO` int(2) DEFAULT NULL,
  `ENDOCRINOLOGIA` int(2) DEFAULT NULL,
  `HUESOS` int(2) DEFAULT NULL,
  `PULMONARES` int(2) DEFAULT NULL,
  `EPILEPTICO` int(2) DEFAULT NULL,
  `HIPERTENSION` int(2) DEFAULT NULL,
  `DEPORTIVA` int(2) DEFAULT NULL,
  `OBESIDAD` int(2) DEFAULT NULL,
  `REUMA` int(2) DEFAULT NULL,
  PRIMARY KEY (`ID_FICHA_MEDICA`),
  KEY `FK_FICHA_MEDICA` (`ID_DATOS_PERS`),
  CONSTRAINT `FK_FICHA_MEDICA` FOREIGN KEY (`ID_DATOS_PERS`) REFERENCES `socios` (`ID_DATOS_PERS`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `fichas`
--

LOCK TABLES `fichas` WRITE;
/*!40000 ALTER TABLE `fichas` DISABLE KEYS */;
/*!40000 ALTER TABLE `fichas` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `gastos`
--

DROP TABLE IF EXISTS `gastos`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `gastos` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `dato_id` int(11) DEFAULT NULL,
  `monto` float DEFAULT NULL,
  `fecha` date DEFAULT NULL,
  `descrip` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `gastos`
--

LOCK TABLES `gastos` WRITE;
/*!40000 ALTER TABLE `gastos` DISABLE KEYS */;
/*!40000 ALTER TABLE `gastos` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `huellas`
--

DROP TABLE IF EXISTS `huellas`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `huellas` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `huella` blob,
  `dedo` varchar(45) DEFAULT NULL,
  `client_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_cliente` (`client_id`),
  CONSTRAINT `fk_cliente` FOREIGN KEY (`client_id`) REFERENCES `socios` (`ID_DATOS_PERS`) ON DELETE CASCADE ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `huellas`
--

LOCK TABLES `huellas` WRITE;
/*!40000 ALTER TABLE `huellas` DISABLE KEYS */;
/*!40000 ALTER TABLE `huellas` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `pagoproveedors`
--

DROP TABLE IF EXISTS `pagoproveedors`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `pagoproveedors` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `fecha` date DEFAULT NULL,
  `monto` float DEFAULT NULL,
  `proveedor_id` int(11) DEFAULT NULL,
  `descripcion` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `pagoproveedors`
--

LOCK TABLES `pagoproveedors` WRITE;
/*!40000 ALTER TABLE `pagoproveedors` DISABLE KEYS */;
/*!40000 ALTER TABLE `pagoproveedors` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `pagos`
--

DROP TABLE IF EXISTS `pagos`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `pagos` (
  `ID_PAGOS` int(11) NOT NULL AUTO_INCREMENT,
  `ID_DATOS_PERS` int(11) NOT NULL,
  `FECHA` date DEFAULT NULL,
  `MONTO` float DEFAULT NULL,
  `MODO` varchar(100) DEFAULT NULL,
  `DESCRIPCION` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`ID_PAGOS`),
  UNIQUE KEY `ID_PAGOS_UNIQUE` (`ID_PAGOS`),
  KEY `FK_PAGOS` (`ID_DATOS_PERS`),
  CONSTRAINT `FK_PAGOS` FOREIGN KEY (`ID_DATOS_PERS`) REFERENCES `socios` (`ID_DATOS_PERS`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `pagos`
--

LOCK TABLES `pagos` WRITE;
/*!40000 ALTER TABLE `pagos` DISABLE KEYS */;
/*!40000 ALTER TABLE `pagos` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `profes`
--

DROP TABLE IF EXISTS `profes`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `profes` (
  `ID_PROFES` int(11) NOT NULL AUTO_INCREMENT,
  `NOMBRE` varchar(20) DEFAULT NULL,
  `APELLIDO` varchar(20) DEFAULT NULL,
  `DNI` varchar(12) DEFAULT NULL,
  `FECHA_NAC` date DEFAULT NULL,
  `DIR` varchar(30) DEFAULT NULL,
  `TEL` varchar(20) DEFAULT NULL,
  `SEXO` char(1) DEFAULT NULL,
  `OBSERV` varchar(3000) DEFAULT NULL,
  PRIMARY KEY (`ID_PROFES`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `profes`
--

LOCK TABLES `profes` WRITE;
/*!40000 ALTER TABLE `profes` DISABLE KEYS */;
/*!40000 ALTER TABLE `profes` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `proveedors`
--

DROP TABLE IF EXISTS `proveedors`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `proveedors` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(100) NOT NULL,
  `cuit` varchar(45) DEFAULT NULL,
  `direccion` varchar(100) DEFAULT NULL,
  `telefono` varchar(45) DEFAULT NULL,
  `celular` varchar(45) DEFAULT NULL,
  `email` varchar(45) DEFAULT NULL,
  `cuenta_corriente` float DEFAULT '0',
  `forma_de_pago` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `proveedors`
--

LOCK TABLES `proveedors` WRITE;
/*!40000 ALTER TABLE `proveedors` DISABLE KEYS */;
/*!40000 ALTER TABLE `proveedors` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `socioarancels`
--

DROP TABLE IF EXISTS `socioarancels`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `socioarancels` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `id_socio` int(11) DEFAULT NULL,
  `id_arancel` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_cliente_arancel` (`id_socio`),
  KEY `fk_arancel` (`id_arancel`),
  CONSTRAINT `fk_arancel` FOREIGN KEY (`id_arancel`) REFERENCES `arancels` (`id`) ON DELETE CASCADE ON UPDATE NO ACTION,
  CONSTRAINT `fk_cliente_arancel` FOREIGN KEY (`id_socio`) REFERENCES `socios` (`ID_DATOS_PERS`) ON DELETE CASCADE ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `socioarancels`
--

LOCK TABLES `socioarancels` WRITE;
/*!40000 ALTER TABLE `socioarancels` DISABLE KEYS */;
/*!40000 ALTER TABLE `socioarancels` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `socios`
--

DROP TABLE IF EXISTS `socios`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `socios` (
  `ID_DATOS_PERS` int(11) NOT NULL AUTO_INCREMENT,
  `NOMBRE` varchar(20) DEFAULT NULL,
  `APELLIDO` varchar(20) DEFAULT NULL,
  `DNI` varchar(12) DEFAULT NULL,
  `FECHA_NAC` varchar(10) DEFAULT NULL,
  `FECHA_ING` date DEFAULT NULL,
  `DIR` varchar(30) DEFAULT NULL,
  `TEL` varchar(20) DEFAULT NULL,
  `SEXO` char(1) DEFAULT NULL,
  `FECHA_ULT_PAGO` date DEFAULT NULL,
  `ACTIV` bigint(20) DEFAULT NULL,
  `FECHA_PROX_PAGO` date DEFAULT NULL,
  `ACTIVO` int(2) DEFAULT NULL,
  `CUENTA_CORRIENTE` float DEFAULT '0',
  PRIMARY KEY (`ID_DATOS_PERS`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `socios`
--

LOCK TABLES `socios` WRITE;
/*!40000 ALTER TABLE `socios` DISABLE KEYS */;
/*!40000 ALTER TABLE `socios` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `users` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `USUARIO` varchar(20) DEFAULT NULL,
  `PASSWD` varchar(20) DEFAULT NULL,
  `ADMINIS` int(2) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES (1,'admin','tecpro',1);
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ventas`
--

DROP TABLE IF EXISTS `ventas`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ventas` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `monto` float DEFAULT NULL,
  `socio_id` int(11) DEFAULT NULL,
  `fecha` date NOT NULL,
  `forma_pago` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ventas`
--

LOCK TABLES `ventas` WRITE;
/*!40000 ALTER TABLE `ventas` DISABLE KEYS */;
/*!40000 ALTER TABLE `ventas` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2015-03-29 15:28:11


CREATE  TABLE `gym`.`demos` (
  `id` INT NOT NULL AUTO_INCREMENT ,
  `fecha` DATE NULL ,
  `dias` INT NULL DEFAULT 7 ,
  PRIMARY KEY (`id`) );
ALTER TABLE `gym`.`demos` ADD COLUMN `activado` INT(1) NULL  AFTER `dias` ;