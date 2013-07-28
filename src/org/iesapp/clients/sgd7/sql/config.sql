/*
SQLyog Ultimate v8.71 
MySQL - 5.5.25a : Database - config
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
/*Table structure for table `archivos` */

CREATE TABLE `archivos` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(255) NOT NULL,
  `datos` longblob,
  `fecha` datetime DEFAULT NULL,
  `version` varchar(255) DEFAULT NULL,
  `descripcion` text,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=104 DEFAULT CHARSET=latin1;

/*Table structure for table `centrales` */

CREATE TABLE `centrales` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(255) DEFAULT NULL,
  `idComunes` int(11) DEFAULT NULL,
  `comunicacion` varchar(255) DEFAULT NULL,
  `puerto` varchar(255) DEFAULT NULL,
  `centralIP` varchar(255) DEFAULT NULL,
  `port` int(11) DEFAULT NULL,
  `timeout` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;

/*Table structure for table `comunes` */

CREATE TABLE `comunes` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `codigoIP` varchar(255) DEFAULT NULL,
  `nombre` varchar(255) DEFAULT NULL,
  `puertoRMI` int(11) DEFAULT NULL,
  `horaComunicacion` varchar(255) DEFAULT NULL,
  `apagarEquipo` char(1) DEFAULT NULL,
  `forzarApagado` char(1) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `codigoIP` (`codigoIP`)
) ENGINE=MyISAM AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;

/*Table structure for table `configuracion` */

CREATE TABLE `configuracion` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `clave` varchar(255) NOT NULL DEFAULT '',
  `valor` text,
  PRIMARY KEY (`id`),
  UNIQUE KEY `clave` (`clave`)
) ENGINE=MyISAM AUTO_INCREMENT=36 DEFAULT CHARSET=latin1;

/*Table structure for table `diccionario` */

CREATE TABLE `diccionario` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `idIdioma` int(11) NOT NULL DEFAULT '0',
  `idUp` int(11) NOT NULL DEFAULT '0',
  `tipo` varchar(255) NOT NULL DEFAULT '',
  `clave` varchar(255) NOT NULL DEFAULT '',
  `cadena` text NOT NULL,
  `valido` char(1) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=3555 DEFAULT CHARSET=latin1;

/*Table structure for table `estilodetalles` */

CREATE TABLE `estilodetalles` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nomDetalle` varchar(255) NOT NULL DEFAULT '',
  `idEstilos` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  UNIQUE KEY `nomDetalle` (`nomDetalle`,`idEstilos`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

/*Table structure for table `estilopropiedades` */

CREATE TABLE `estilopropiedades` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nomProp` varchar(255) NOT NULL DEFAULT '',
  `valorProp` varchar(255) NOT NULL DEFAULT '',
  `idEstiloDetalles` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  UNIQUE KEY `nomProp` (`nomProp`,`idEstiloDetalles`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

/*Table structure for table `estilos` */

CREATE TABLE `estilos` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nomEstilo` varchar(255) NOT NULL DEFAULT '',
  PRIMARY KEY (`id`),
  UNIQUE KEY `nomEstilo` (`nomEstilo`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

/*Table structure for table `filtros` */

CREATE TABLE `filtros` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `tipo` varchar(255) NOT NULL DEFAULT '',
  `nombre` varchar(255) NOT NULL DEFAULT '',
  `idUsuarios` int(11) DEFAULT NULL,
  `filtro` longblob NOT NULL,
  `curso` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;

/*Table structure for table `firmware` */

CREATE TABLE `firmware` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(255) DEFAULT NULL,
  `ver` int(11) NOT NULL DEFAULT '0',
  `sub` int(11) NOT NULL DEFAULT '0',
  `rev` int(11) NOT NULL DEFAULT '0',
  `datos` blob,
  PRIMARY KEY (`id`),
  UNIQUE KEY `nombre` (`nombre`)
) ENGINE=MyISAM AUTO_INCREMENT=13 DEFAULT CHARSET=latin1;

/*Table structure for table `idiomas` */

CREATE TABLE `idiomas` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(255) DEFAULT NULL,
  `language` char(2) DEFAULT NULL,
  `country` char(2) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=8 DEFAULT CHARSET=latin1;

/*Table structure for table `informes` */

CREATE TABLE `informes` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(255) DEFAULT NULL,
  `descripcion` varchar(255) DEFAULT NULL,
  `source` varchar(255) DEFAULT NULL,
  `texto` text,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

/*Table structure for table `licencias` */

CREATE TABLE `licencias` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `centro` varchar(255) DEFAULT NULL,
  `codigo` varchar(255) DEFAULT NULL,
  `corto` varchar(255) DEFAULT NULL,
  `creado` date DEFAULT NULL,
  `caduca` date DEFAULT NULL,
  `modulo` varchar(255) DEFAULT NULL,
  `param` varchar(255) DEFAULT NULL,
  `clave` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

/*Table structure for table `log2011` */

CREATE TABLE `log2011` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `host` varchar(255) DEFAULT NULL,
  `hostIp` varchar(255) DEFAULT NULL,
  `user` varchar(255) DEFAULT NULL,
  `tipo` varchar(255) DEFAULT NULL,
  `datos` varchar(255) DEFAULT NULL,
  `fecha` datetime DEFAULT NULL,
  `tabla` varchar(255) DEFAULT NULL,
  `sentenciaSQL` text,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=824104 DEFAULT CHARSET=latin1;

/*Table structure for table `log2012` */

CREATE TABLE `log2012` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `host` varchar(255) DEFAULT NULL,
  `hostIp` varchar(255) DEFAULT NULL,
  `user` varchar(255) DEFAULT NULL,
  `tipo` varchar(255) DEFAULT NULL,
  `datos` varchar(255) DEFAULT NULL,
  `fecha` datetime DEFAULT NULL,
  `tabla` varchar(255) DEFAULT NULL,
  `sentenciaSQL` text,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=219489 DEFAULT CHARSET=latin1;

/*Table structure for table `opciones` */

CREATE TABLE `opciones` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `codigo` varchar(255) NOT NULL DEFAULT '',
  `nombre` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=76 DEFAULT CHARSET=latin1;

/*Table structure for table `opcionesinformes` */

CREATE TABLE `opcionesinformes` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `usuario` varchar(255) DEFAULT NULL,
  `idInformes` int(11) NOT NULL DEFAULT '0',
  `source` varchar(255) DEFAULT NULL,
  `campo` varchar(255) DEFAULT NULL,
  `variable` varchar(255) DEFAULT NULL,
  `valor` blob,
  `tipoInformes` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=8793 DEFAULT CHARSET=latin1;

/*Table structure for table `pdadata` */

CREATE TABLE `pdadata` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nup` int(11) DEFAULT NULL,
  `tabla` varchar(255) DEFAULT NULL,
  `linea` int(11) DEFAULT NULL,
  `datos` text,
  PRIMARY KEY (`id`),
  UNIQUE KEY `nup` (`nup`,`tabla`,`linea`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

/*Table structure for table `perfiles` */

CREATE TABLE `perfiles` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=7 DEFAULT CHARSET=latin1;

/*Table structure for table `permisos` */

CREATE TABLE `permisos` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `idUsuarios` int(11) NOT NULL DEFAULT '0',
  `idPerfiles` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=775 DEFAULT CHARSET=latin1;

/*Table structure for table `permisoscurso` */

CREATE TABLE `permisoscurso` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `idUsuarios` int(11) NOT NULL DEFAULT '0',
  `idProfesores` int(11) NOT NULL DEFAULT '0',
  `curso` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=706 DEFAULT CHARSET=latin1;

/*Table structure for table `permisosperfiles` */

CREATE TABLE `permisosperfiles` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `idPerfiles` int(11) NOT NULL DEFAULT '0',
  `idOpciones` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=110 DEFAULT CHARSET=latin1;

/*Table structure for table `prefijos` */

CREATE TABLE `prefijos` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `prefijo` varchar(10) NOT NULL,
  `pais` varchar(50) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `prefijo` (`prefijo`),
  UNIQUE KEY `pais` (`pais`)
) ENGINE=MyISAM AUTO_INCREMENT=6 DEFAULT CHARSET=latin1;

/*Table structure for table `tipoup` */

CREATE TABLE `tipoup` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `codigo` varchar(255) NOT NULL DEFAULT '',
  `nombre` varchar(255) NOT NULL DEFAULT '',
  PRIMARY KEY (`id`),
  UNIQUE KEY `codigo` (`codigo`)
) ENGINE=MyISAM AUTO_INCREMENT=6 DEFAULT CHARSET=latin1;

/*Table structure for table `ultimosaccesos` */

CREATE TABLE `ultimosaccesos` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `idUsuarios` int(11) NOT NULL DEFAULT '0',
  `clave` varchar(255) NOT NULL DEFAULT '',
  `fecha` datetime NOT NULL DEFAULT '0000-00-00 00:00:00',
  `valor` varchar(255) NOT NULL DEFAULT '',
  `idiomaPrincipal` int(11) NOT NULL DEFAULT '0',
  `idiomaSecundario` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=790 DEFAULT CHARSET=latin1;

/*Table structure for table `unidadespersonales` */

CREATE TABLE `unidadespersonales` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `codigoUP` varchar(255) DEFAULT NULL,
  `idFirmware` int(11) DEFAULT NULL,
  `idFirmware2` int(11) DEFAULT NULL,
  `ultimaAct` datetime DEFAULT NULL,
  `idTipoUp` int(11) DEFAULT NULL,
  `estado` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `codigoUP` (`codigoUP`)
) ENGINE=MyISAM AUTO_INCREMENT=201 DEFAULT CHARSET=latin1;

/*Table structure for table `usuarios` */

CREATE TABLE `usuarios` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `usuario` varchar(255) NOT NULL DEFAULT '',
  `clave` varchar(255) NOT NULL DEFAULT '',
  `nombre` varchar(255) DEFAULT NULL,
  `idProfesores` int(11) DEFAULT NULL,
  `idIdiomas` int(11) DEFAULT NULL,
  `apariencia` int(11) DEFAULT NULL,
  `zoomValue` int(11) DEFAULT NULL,
  `fechaPass` date DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=491 DEFAULT CHARSET=latin1;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
