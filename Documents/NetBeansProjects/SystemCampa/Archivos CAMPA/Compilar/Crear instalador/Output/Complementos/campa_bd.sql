-- --------------------------------------------------------
-- Host:                         127.0.0.1
-- Versión del servidor:         5.7.26 - MySQL Community Server (GPL)
-- SO del servidor:              Win64
-- HeidiSQL Versión:             9.3.0.4984
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8mb4 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;

-- Volcando estructura de base de datos para campamento_db
CREATE DATABASE IF NOT EXISTS `campamento_db` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `campamento_db`;


-- Volcando estructura para tabla campamento_db.campistas
CREATE TABLE IF NOT EXISTS `campistas` (
  `idcampistas` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(65) NOT NULL,
  `apellido` varchar(65) NOT NULL,
  `direccion` varchar(65) NOT NULL,
  `telefono` varchar(45) NOT NULL,
  `staff` varchar(4) DEFAULT NULL,
  PRIMARY KEY (`idcampistas`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- La exportación de datos fue deseleccionada.


-- Volcando estructura para tabla campamento_db.detalle_capital
CREATE TABLE IF NOT EXISTS `detalle_capital` (
  `id_detalle_capital` int(11) NOT NULL AUTO_INCREMENT,
  `descripcion` varchar(100) NOT NULL,
  `cantidad` double NOT NULL,
  `tipo` varchar(45) NOT NULL,
  `fecha` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id_detalle_capital`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- La exportación de datos fue deseleccionada.


-- Volcando estructura para tabla campamento_db.detalle_pagos
CREATE TABLE IF NOT EXISTS `detalle_pagos` (
  `id_detalle_pagos` int(11) NOT NULL AUTO_INCREMENT,
  `abono` int(11) DEFAULT NULL,
  `pagado` tinyint(4) DEFAULT NULL,
  `idpagos` int(11) NOT NULL,
  `fechaa` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id_detalle_pagos`),
  KEY `fk_detalle_pagos_pagos1_idx` (`idpagos`),
  CONSTRAINT `fk_detalle_pagos_pagos1` FOREIGN KEY (`idpagos`) REFERENCES `pagos` (`idpagos`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- La exportación de datos fue deseleccionada.


-- Volcando estructura para tabla campamento_db.pagos
CREATE TABLE IF NOT EXISTS `pagos` (
  `idpagos` int(11) NOT NULL AUTO_INCREMENT,
  `cantidad` int(11) NOT NULL DEFAULT '275',
  `fecha` datetime DEFAULT NULL,
  `campistas_id` int(11) NOT NULL,
  PRIMARY KEY (`idpagos`),
  KEY `fk_detalle_pagos_campistas1_idx` (`campistas_id`),
  CONSTRAINT `fk_detalle_pagos_campistas1` FOREIGN KEY (`campistas_id`) REFERENCES `campistas` (`idcampistas`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- La exportación de datos fue deseleccionada.


-- Volcando estructura para disparador campamento_db.Abonar
SET @OLDTMP_SQL_MODE=@@SQL_MODE, SQL_MODE='STRICT_ALL_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_AUTO_CREATE_USER';
DELIMITER //
CREATE TRIGGER `Abonar` AFTER INSERT ON `detalle_pagos` FOR EACH ROW update pagos set cantidad = cantidad - new.abono WHERE idpagos = new.idpagos//
DELIMITER ;
SET SQL_MODE=@OLDTMP_SQL_MODE;


-- Volcando estructura para disparador campamento_db.InsertarPago
SET @OLDTMP_SQL_MODE=@@SQL_MODE, SQL_MODE='STRICT_ALL_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_AUTO_CREATE_USER';
DELIMITER //
CREATE TRIGGER `InsertarPago` AFTER INSERT ON `campistas` FOR EACH ROW INSERT INTO pagos(cantidad, fecha, campistas_id)
VALUES (275, NOW(), NEW.idcampistas)//
DELIMITER ;
SET SQL_MODE=@OLDTMP_SQL_MODE;
/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
