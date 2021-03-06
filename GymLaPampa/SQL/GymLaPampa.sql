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
-- Table structure for table `alimentos`
--

DROP TABLE IF EXISTS `alimentos`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `alimentos` (
  `nombre` varchar(50) NOT NULL,
  `pc` float DEFAULT '0',
  `agua` float DEFAULT '0',
  `prot` float DEFAULT '0',
  `hc` float DEFAULT '0',
  `grasa` float DEFAULT '0',
  `satur` float DEFAULT '0',
  `mono` float DEFAULT '0',
  `poli` float DEFAULT '0',
  `colesterol` float DEFAULT '0',
  `fibra` float DEFAULT '0',
  `sodio` float DEFAULT '0',
  `potasio` float DEFAULT '0',
  `magnesio` float DEFAULT '0',
  `calcio` float DEFAULT '0',
  `fosforo` float DEFAULT '0',
  `hierro` float DEFAULT '0',
  `indiceglucemico` float DEFAULT '0',
  `id` int(11) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`id`,`nombre`)
) ENGINE=InnoDB AUTO_INCREMENT=564 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `alimentos_dietas`
--

DROP TABLE IF EXISTS `alimentos_dietas`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `alimentos_dietas` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `alimento_id` int(11) NOT NULL,
  `dieta_id` int(11) NOT NULL,
  `porcion` float DEFAULT NULL,
  `hora` varchar(45) DEFAULT NULL,
  `dia` varchar(45) DEFAULT NULL,
  `numero_dia` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`,`dieta_id`,`alimento_id`)
) ENGINE=InnoDB AUTO_INCREMENT=155 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

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
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

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
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

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
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

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
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

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
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

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
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `demos`
--

DROP TABLE IF EXISTS `demos`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `demos` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `fecha` date DEFAULT NULL,
  `dias` int(11) DEFAULT '7',
  `activado` int(1) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `dias`
--

DROP TABLE IF EXISTS `dias`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `dias` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `rutina_id` int(10) DEFAULT NULL,
  `dia` varchar(15) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `dias_ejercicios`
--

DROP TABLE IF EXISTS `dias_ejercicios`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `dias_ejercicios` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `dia_id` int(10) DEFAULT NULL,
  `ejercicio_id` int(10) DEFAULT NULL,
  `series` int(10) DEFAULT 0,
  `repeticiones` int(10) DEFAULT 0,
  `tiempo` varchar(100) DEFAULT 0,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `dietas`
--

DROP TABLE IF EXISTS `dietas`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `dietas` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(45) NOT NULL,
  `descripcion` varchar(300) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `ejercicios`
--

DROP TABLE IF EXISTS `ejercicios`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ejercicios` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `ejercicio` varchar(120) DEFAULT NULL,
  `grupo` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=88 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `emails`
--

DROP TABLE IF EXISTS `emails`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `emails` (
  `email` varchar(45) NOT NULL,
  `password` varchar(45) DEFAULT NULL,
  `id` int(11) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`id`,`email`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `envios`
--

DROP TABLE IF EXISTS `envios`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `envios` (
  `fecha` date NOT NULL,
  `enviado` int(11) DEFAULT NULL,
  `id` int(11) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`id`,`fecha`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

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
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

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
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

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
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `pagosventas`
--

DROP TABLE IF EXISTS `pventas`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `pventas` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `ID_DATOS_PERS` int(11) NOT NULL,
  `fecha` date DEFAULT NULL,
  `monto` float DEFAULT NULL,
  `modo` varchar(100) DEFAULT NULL,
  `gasto_id` int(10) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`),
  KEY `FK_pventas` (`ID_DATOS_PERS`),
  CONSTRAINT `FK_pventas` FOREIGN KEY (`ID_DATOS_PERS`) REFERENCES `socios` (`ID_DATOS_PERS`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

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
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

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
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `rutinas`
--

DROP TABLE IF EXISTS `rutinas`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `rutinas` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `socio_id` int(10) DEFAULT NULL,
  `descrip` varchar(200) DEFAULT NULL,
  `fecha_inicio` date DEFAULT NULL,
  `fecha_fin` date DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

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
) ENGINE=InnoDB AUTO_INCREMENT=43 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `socios`
--

DROP TABLE IF EXISTS `socios`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `socios` (
  `ID_DATOS_PERS` int(11) NOT NULL AUTO_INCREMENT,
  `NOMBRE` varchar(50) DEFAULT NULL,
  `APELLIDO` varchar(50) DEFAULT NULL,
  `DNI` varchar(20) DEFAULT NULL,
  `FECHA_NAC` varchar(10) DEFAULT NULL,
  `FECHA_ING` date DEFAULT NULL,
  `DIR` varchar(200) DEFAULT NULL,
  `TEL` varchar(50) DEFAULT NULL,
  `SEXO` char(1) DEFAULT NULL,
  `FECHA_ULT_PAGO` date DEFAULT NULL,
  `ACTIV` bigint(20) DEFAULT NULL,
  `FECHA_PROX_PAGO` date DEFAULT NULL,
  `ACTIVO` int(2) DEFAULT NULL,
  `CUENTA_CORRIENTE` float DEFAULT '0',
  `FACEBOOK` varchar(100) DEFAULT NULL,
  `NOSCONOCIOPOR` varchar(30) DEFAULT NULL,
  `CELULAR` varchar(50) DEFAULT NULL,
  `MAIL` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`ID_DATOS_PERS`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `socios_dietas`
--

DROP TABLE IF EXISTS `socios_dietas`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `socios_dietas` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `socio_id` varchar(45) NOT NULL,
  `dieta_id` varchar(45) NOT NULL,
  `desde` date DEFAULT NULL,
  `hasta` date DEFAULT NULL,
  PRIMARY KEY (`id`,`socio_id`,`dieta_id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

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
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

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
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2015-04-15 18:54:05
