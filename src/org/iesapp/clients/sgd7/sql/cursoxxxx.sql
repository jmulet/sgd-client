/*
SQLyog Ultimate v8.71 
MySQL - 5.5.25a : Database - curso2012
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
/*Table structure for table `actividades` */

CREATE TABLE `actividades` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `idProfesores` int(11) NOT NULL,
  `idGrupAsig` int(11) NOT NULL,
  `idEvaluacionesDetalle` int(11) NOT NULL,
  `idSistemasNotas` int(11) NOT NULL,
  `descripcion` varchar(255) NOT NULL,
  `fecha` date NOT NULL,
  `publicarWeb` char(1) NOT NULL,
  `seguimiento` char(1) NOT NULL,
  `evaluable` char(1) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=3140 DEFAULT CHARSET=latin1;

/*Table structure for table `actividadesalumno` */

CREATE TABLE `actividadesalumno` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `idAlumnos` int(11) NOT NULL,
  `idActividades` int(11) NOT NULL,
  `fechaEntrega` date DEFAULT NULL,
  `nota` float DEFAULT NULL,
  `idTipoNotasSistemas` int(11) DEFAULT NULL,
  `matriculado` char(1) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=50082 DEFAULT CHARSET=latin1;

/*Table structure for table `agenda` */

CREATE TABLE `agenda` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `idProfesores` int(11) NOT NULL,
  `fechaHora` datetime NOT NULL,
  `avisador` char(1) NOT NULL,
  `sistema` int(11) DEFAULT NULL,
  `tipo` char(1) NOT NULL,
  `idAlumnos` int(11) NOT NULL,
  `iniciativa` int(11) DEFAULT NULL,
  `idTipoCita` int(11) DEFAULT NULL,
  `comentarios` text,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

/*Table structure for table `agrupacionincidencias` */

CREATE TABLE `agrupacionincidencias` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `idConceptosIncidencias` int(11) DEFAULT NULL,
  `idTipoIncidencias` int(11) DEFAULT NULL,
  `posicion` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `idConceptosIncidencias` (`idConceptosIncidencias`,`idTipoIncidencias`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

/*Table structure for table `alumnos` */

CREATE TABLE `alumnos` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `expediente` varchar(255) NOT NULL,
  `nombre` varchar(255) NOT NULL,
  `sexo` char(1) DEFAULT NULL,
  `dni` varchar(255) DEFAULT NULL,
  `codHistorial` varchar(255) DEFAULT NULL,
  `tarjetaSanitaria` varchar(255) DEFAULT NULL,
  `libroEscolaridad` varchar(255) DEFAULT NULL,
  `fechaNac` date DEFAULT NULL,
  `fechaMat` date DEFAULT NULL,
  `domicilio` varchar(255) DEFAULT NULL,
  `localidad` varchar(255) DEFAULT NULL,
  `provincia` varchar(255) DEFAULT NULL,
  `codPostal` varchar(255) DEFAULT NULL,
  `telefono` varchar(255) DEFAULT NULL,
  `codGrupo` varchar(255) DEFAULT NULL,
  `extra1` varchar(255) DEFAULT NULL,
  `autoriza` char(1) NOT NULL,
  `foto` blob,
  `baja` char(1) NOT NULL,
  `comedor` char(1) NOT NULL,
  `dietas` varchar(255) DEFAULT NULL,
  `alergias` varchar(255) DEFAULT NULL,
  `avisos` varchar(255) DEFAULT NULL,
  `observaciones` varchar(255) DEFAULT NULL,
  `idGruposComedor` int(11) DEFAULT NULL,
  `observacionesAlumno` text,
  `campo1` varchar(255) DEFAULT NULL,
  `campo2` varchar(255) DEFAULT NULL,
  `campo3` varchar(255) DEFAULT NULL,
  `campo4` varchar(255) DEFAULT NULL,
  `campo5` varchar(255) DEFAULT NULL,
  `campo6` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `expediente` (`expediente`)
) ENGINE=MyISAM AUTO_INCREMENT=706 DEFAULT CHARSET=latin1;

/*Table structure for table `alumnosentradassalidas` */

CREATE TABLE `alumnosentradassalidas` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `idAlumnos` int(11) NOT NULL,
  `idProfesores` int(11) NOT NULL,
  `idUsuarios` int(11) NOT NULL,
  `idTipoObservaciones` int(11) DEFAULT NULL,
  `entrada` char(1) NOT NULL,
  `salida` char(1) NOT NULL,
  `fecha` datetime DEFAULT NULL,
  `observaciones` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

/*Table structure for table `asignaturas` */

CREATE TABLE `asignaturas` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `asignatura` varchar(255) NOT NULL,
  `codigo` varchar(255) DEFAULT NULL,
  `descripcion` varchar(255) NOT NULL,
  `codGes` varchar(255) DEFAULT NULL,
  `evaluable` char(1) NOT NULL,
  `asistencia` char(1) NOT NULL,
  `codigoGH` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `asignatura` (`asignatura`),
  KEY `codigo` (`codigo`)
) ENGINE=MyISAM AUTO_INCREMENT=263 DEFAULT CHARSET=latin1;

/*Table structure for table `asignaturasalumno` */

CREATE TABLE `asignaturasalumno` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `idAlumnos` int(11) NOT NULL,
  `idGrupAsig` int(11) NOT NULL,
  `idEvaluacionesDetalle` int(11) DEFAULT NULL,
  `opcion` varchar(255) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `idAlumnos` (`idAlumnos`,`idGrupAsig`,`idEvaluacionesDetalle`)
) ENGINE=MyISAM AUTO_INCREMENT=191357 DEFAULT CHARSET=latin1;

/*Table structure for table `asignaturasalumnoimport` */

CREATE TABLE `asignaturasalumnoimport` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `expediente` varchar(255) DEFAULT NULL,
  `grupoGestion` varchar(255) DEFAULT NULL,
  `codAsig` varchar(255) DEFAULT NULL,
  `codEvaluacion` varchar(255) DEFAULT NULL,
  `extra1` varchar(255) DEFAULT NULL,
  `exportable` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `expediente` (`expediente`),
  KEY `grupoGestion` (`grupoGestion`),
  KEY `codAsig` (`codAsig`),
  KEY `codEvaluacion` (`codEvaluacion`)
) ENGINE=MyISAM AUTO_INCREMENT=214177 DEFAULT CHARSET=latin1;

/*Table structure for table `aulas` */

CREATE TABLE `aulas` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `codigo` varchar(255) NOT NULL,
  `descripcion` varchar(255) NOT NULL,
  `descripcionLarga` varchar(255) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `codigo` (`codigo`)
) ENGINE=MyISAM AUTO_INCREMENT=53 DEFAULT CHARSET=latin1;

/*Table structure for table `bloqueshorarios` */

CREATE TABLE `bloqueshorarios` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `nombre` (`nombre`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

/*Table structure for table `centro` */

CREATE TABLE `centro` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `codigo` varchar(255) DEFAULT NULL,
  `nombre` varchar(255) DEFAULT NULL,
  `direccion` varchar(255) DEFAULT NULL,
  `poblacion` varchar(255) DEFAULT NULL,
  `provincia` varchar(255) DEFAULT NULL,
  `codigoPostal` varchar(255) DEFAULT NULL,
  `telefono` varchar(255) DEFAULT NULL,
  `fax` varchar(255) DEFAULT NULL,
  `web` varchar(255) DEFAULT NULL,
  `cabecera` char(1) DEFAULT NULL,
  `programaGestion` int(11) DEFAULT NULL,
  `programaHorarios` int(11) DEFAULT NULL,
  `desgloseEvaluaciones` char(1) DEFAULT NULL,
  `incidenciaObservacion` int(11) DEFAULT NULL,
  `incidenciaDefecto` int(11) DEFAULT NULL,
  `logo` blob,
  `firmaV10` varchar(255) DEFAULT NULL,
  `gestionPendientes` char(1) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;

/*Table structure for table `clases` */

CREATE TABLE `clases` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` char(32) NOT NULL,
  `mezclarGrupos` char(1) NOT NULL,
  `borrarDuplicados` char(1) NOT NULL,
  `idColor` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=435 DEFAULT CHARSET=latin1;

/*Table structure for table `clasesanotadas` */

CREATE TABLE `clasesanotadas` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `idProfesores` int(11) NOT NULL,
  `idProfesoresReal` int(11) DEFAULT NULL,
  `fecha` date DEFAULT NULL,
  `idHorasCentro` int(11) NOT NULL,
  `idGrupAsig` int(11) DEFAULT NULL,
  `anotacionesClase` varchar(255) DEFAULT NULL,
  `tareasClase` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `idGrupAsig` (`idGrupAsig`),
  KEY `idProfesores` (`idProfesores`)
) ENGINE=MyISAM AUTO_INCREMENT=28164 DEFAULT CHARSET=latin1;

/*Table structure for table `clasesdetalle` */

CREATE TABLE `clasesdetalle` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `idClases` int(11) NOT NULL,
  `idGrupasig` int(11) NOT NULL,
  `opcion` char(3) NOT NULL,
  `orden` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `idClases` (`idClases`),
  KEY `idGrupasig` (`idGrupasig`)
) ENGINE=MyISAM AUTO_INCREMENT=547 DEFAULT CHARSET=latin1;

/*Table structure for table `clavesexportacionincidencias` */

CREATE TABLE `clavesexportacionincidencias` (
  `claveExportacion` varchar(255) NOT NULL,
  `descripcion` varchar(255) NOT NULL,
  PRIMARY KEY (`claveExportacion`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

/*Table structure for table `clavesexportacionnotas` */

CREATE TABLE `clavesexportacionnotas` (
  `claveExportacion` varchar(255) NOT NULL,
  `descripcion` varchar(255) NOT NULL,
  PRIMARY KEY (`claveExportacion`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

/*Table structure for table `compatibilidadincidencias` */

CREATE TABLE `compatibilidadincidencias` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `idTipoIncidencias` int(11) NOT NULL,
  `idTipoIncidencias2` int(11) NOT NULL,
  `resultado` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=38 DEFAULT CHARSET=latin1;

/*Table structure for table `conceptosevaluables` */

CREATE TABLE `conceptosevaluables` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `idTipoNota` int(11) NOT NULL,
  `idTipoConceptos` int(11) NOT NULL,
  `posicion` int(11) NOT NULL,
  `claveExportacion` varchar(255) DEFAULT NULL,
  `notaMaxima` float DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `idTipoNota` (`idTipoNota`,`idTipoConceptos`)
) ENGINE=MyISAM AUTO_INCREMENT=48 DEFAULT CHARSET=latin1;

/*Table structure for table `conceptosevaluaciones` */

CREATE TABLE `conceptosevaluaciones` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `idEvaluacionesDetalle` int(11) NOT NULL,
  `idConceptosEvaluables` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

/*Table structure for table `conceptosincidencias` */

CREATE TABLE `conceptosincidencias` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `simbolo` varchar(255) NOT NULL,
  `descripcion` varchar(255) NOT NULL,
  `posicion` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `simbolo` (`simbolo`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

/*Table structure for table `cotutoresgrupo` */

CREATE TABLE `cotutoresgrupo` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `idAlumnos` int(11) NOT NULL,
  `grupoGestion` varchar(255) NOT NULL,
  `idProfesores` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `idAlumnos` (`idAlumnos`,`grupoGestion`,`idProfesores`)
) ENGINE=MyISAM AUTO_INCREMENT=72 DEFAULT CHARSET=latin1;

/*Table structure for table `desplegables` */

CREATE TABLE `desplegables` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `tipo` varchar(255) NOT NULL,
  `codigo` varchar(255) NOT NULL,
  `texto` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=155 DEFAULT CHARSET=latin1;

/*Table structure for table `detallenota` */

CREATE TABLE `detallenota` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `idConceptosEvaluables` int(11) NOT NULL,
  `idTipoDetalleNota` int(11) NOT NULL,
  `posicion` int(11) NOT NULL,
  `valorExportable` varchar(255) DEFAULT NULL,
  `apto` char(1) NOT NULL,
  `notaMinima` float DEFAULT NULL,
  `valor` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `idConceptosEvaluables` (`idConceptosEvaluables`,`idTipoDetalleNota`)
) ENGINE=MyISAM AUTO_INCREMENT=305 DEFAULT CHARSET=latin1;

/*Table structure for table `dias` */

CREATE TABLE `dias` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(16) NOT NULL,
  `abreviatura` char(2) NOT NULL,
  `lectivo` char(1) NOT NULL,
  `orden` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=8 DEFAULT CHARSET=latin1;

/*Table structure for table `evaluacionautomaticaguia` */

CREATE TABLE `evaluacionautomaticaguia` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `idConceptoEvalGuia` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `idConceptoEvalGuia` (`idConceptoEvalGuia`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

/*Table structure for table `evaluacionautomaticaperfil` */

CREATE TABLE `evaluacionautomaticaperfil` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `idConceptoGuia` int(11) NOT NULL,
  `idDetalleNotaGuia` int(11) NOT NULL,
  `idConceptoDependiente` int(11) NOT NULL,
  `idDetalleNotaDependiente` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `idDetalleNotaGuia` (`idDetalleNotaGuia`,`idConceptoDependiente`,`idDetalleNotaDependiente`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

/*Table structure for table `evaluaciones` */

CREATE TABLE `evaluaciones` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(255) NOT NULL,
  `codEvaluaciones` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;

/*Table structure for table `evaluacionesdetalle` */

CREATE TABLE `evaluacionesdetalle` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `idEvaluaciones` int(11) NOT NULL,
  `idTipoEvaluacionesDetalle` int(11) NOT NULL,
  `fechaInicio` date DEFAULT NULL,
  `fechaFin` date DEFAULT NULL,
  `fechaInicioReal` date DEFAULT NULL,
  `fechaFinReal` date DEFAULT NULL,
  `pasaLista` char(1) NOT NULL,
  `publicarSGDWeb` char(1) NOT NULL,
  `fechaSGDWeb` date DEFAULT NULL,
  `idRecuperacionDe` int(11) DEFAULT NULL,
  `valorExportable` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `idEvaluaciones` (`idEvaluaciones`,`idTipoEvaluacionesDetalle`)
) ENGINE=MyISAM AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;

/*Table structure for table `exportable` */

CREATE TABLE `exportable` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `alumno` varchar(255) DEFAULT NULL,
  `grupo` varchar(255) DEFAULT NULL,
  `diaSemana` varchar(255) DEFAULT NULL,
  `hora` varchar(255) DEFAULT NULL,
  `exportableCurso` varchar(255) DEFAULT NULL,
  `exportableGrupo` varchar(255) DEFAULT NULL,
  `exportableAsig` varchar(255) DEFAULT NULL,
  `exportableHora` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `alumno` (`alumno`),
  KEY `grupo` (`grupo`),
  KEY `diaSemana` (`diaSemana`),
  KEY `hora` (`hora`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

/*Table structure for table `faltasalumnos` */

CREATE TABLE `faltasalumnos` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `idAlumnos` int(11) NOT NULL,
  `idProfesores` int(11) DEFAULT NULL,
  `idTipoIncidencias` int(11) DEFAULT NULL,
  `idHorasCentro` int(11) DEFAULT NULL,
  `idGrupAsig` int(11) DEFAULT NULL,
  `idTipoObservaciones` int(11) DEFAULT NULL,
  `dia` date NOT NULL,
  `hora` varchar(255) DEFAULT NULL,
  `comentarios` text,
  `fechaModificado` datetime DEFAULT NULL,
  `exportado` char(1) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `dia` (`dia`),
  KEY `idAlumnos` (`idAlumnos`),
  KEY `idProfesores` (`idProfesores`),
  KEY `idTipoIncidencias` (`idTipoIncidencias`),
  KEY `idHorasCentro` (`idHorasCentro`),
  KEY `idGrupAsig` (`idGrupAsig`)
) ENGINE=MyISAM AUTO_INCREMENT=67414 DEFAULT CHARSET=latin1;

/*Table structure for table `faltasalumnos_deleted` */

CREATE TABLE `faltasalumnos_deleted` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `idFaltasAlumnos` int(11) NOT NULL,
  `fechaModificado` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=2299 DEFAULT CHARSET=latin1;

/*Table structure for table `faltasprofesores` */

CREATE TABLE `faltasprofesores` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `idProfesores` int(11) NOT NULL,
  `idGrupAsig` int(11) DEFAULT NULL,
  `fecha` date DEFAULT NULL,
  `hora` varchar(255) NOT NULL,
  `idHorasCentro` int(11) NOT NULL,
  `idProfesores2` int(11) DEFAULT NULL,
  `idTipoIncidencias` int(11) DEFAULT NULL,
  `observaciones` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=177 DEFAULT CHARSET=latin1;

/*Table structure for table `festivos` */

CREATE TABLE `festivos` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `fecha` date NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

/*Table structure for table `frases` */

CREATE TABLE `frases` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `idUnidades` int(11) NOT NULL,
  `frase` varchar(255) NOT NULL,
  `descripcion` varchar(255) NOT NULL,
  `valorExportable` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `idUnidades` (`idUnidades`,`frase`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

/*Table structure for table `frasesalumnos` */

CREATE TABLE `frasesalumnos` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `idFrases` int(11) DEFAULT NULL,
  `idAlumnos` int(11) NOT NULL,
  `idEvaluacionesDetalle` int(11) NOT NULL,
  `idGrupAsig` int(11) NOT NULL,
  `observaciones` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

/*Table structure for table `grupasig` */

CREATE TABLE `grupasig` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `idGrupos` int(11) NOT NULL,
  `idAsignaturas` int(11) NOT NULL,
  `idTipoNota` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `idGrupos` (`idGrupos`),
  KEY `idAsignaturas` (`idAsignaturas`),
  KEY `idTipoNota` (`idTipoNota`)
) ENGINE=MyISAM AUTO_INCREMENT=518 DEFAULT CHARSET=latin1;

/*Table structure for table `grupos` */

CREATE TABLE `grupos` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `grupo` varchar(255) NOT NULL,
  `descripcion` varchar(255) NOT NULL,
  `idProfesores` int(11) DEFAULT NULL,
  `idTipoNota` int(11) DEFAULT NULL,
  `idEvaluaciones` int(11) DEFAULT NULL,
  `grupoGestion` varchar(255) DEFAULT NULL,
  `codigoGH` varchar(255) DEFAULT NULL,
  `incidenciasPorBloque` char(1) DEFAULT NULL,
  `nivelGrupo` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `grupo` (`grupo`),
  KEY `grupoGestion` (`grupoGestion`),
  KEY `idEvaluaciones` (`idEvaluaciones`),
  KEY `idTipoNota` (`idTipoNota`)
) ENGINE=MyISAM AUTO_INCREMENT=38 DEFAULT CHARSET=latin1;

/*Table structure for table `gruposalumno` */

CREATE TABLE `gruposalumno` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `idAlumnos` int(11) NOT NULL,
  `grupoGestion` varchar(255) DEFAULT NULL,
  `extra1` varchar(255) DEFAULT NULL,
  `curso` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `idAlumnos` (`idAlumnos`),
  KEY `grupoGestion` (`grupoGestion`)
) ENGINE=MyISAM AUTO_INCREMENT=8188 DEFAULT CHARSET=latin1;

/*Table structure for table `gruposcomedor` */

CREATE TABLE `gruposcomedor` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `grupo` varchar(255) NOT NULL,
  `descripcion` varchar(255) NOT NULL,
  `idProfesores` int(11) NOT NULL,
  `idTurnosComedor` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `grupo` (`grupo`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

/*Table structure for table `gruposevaluacionesimport` */

CREATE TABLE `gruposevaluacionesimport` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `grupoGestion` varchar(255) DEFAULT NULL,
  `extra1` varchar(50) DEFAULT NULL,
  `codEvaluacion` varchar(255) DEFAULT NULL,
  `exportable` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `grupoGestion` (`grupoGestion`),
  KEY `codEvaluacion` (`codEvaluacion`),
  KEY `extra1` (`extra1`)
) ENGINE=MyISAM AUTO_INCREMENT=109 DEFAULT CHARSET=latin1;

/*Table structure for table `horarios` */

CREATE TABLE `horarios` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `idProfesores` int(11) NOT NULL,
  `idDias` int(11) NOT NULL,
  `idHorascentro` int(11) NOT NULL,
  `idAulas` int(11) DEFAULT NULL,
  `tipoClase` int(11) NOT NULL,
  `idClases` int(11) DEFAULT NULL,
  `idTareas` int(11) DEFAULT NULL,
  `idEvaluacionesDetalle` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `idClases` (`idClases`),
  KEY `idProfesores` (`idProfesores`)
) ENGINE=MyISAM AUTO_INCREMENT=2067 DEFAULT CHARSET=latin1;

/*Table structure for table `horascentro` */

CREATE TABLE `horascentro` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `idTipoHoras` int(11) NOT NULL,
  `codigo` varchar(255) NOT NULL,
  `inicio` varchar(255) NOT NULL,
  `fin` varchar(255) NOT NULL,
  `codHora` varchar(255) DEFAULT NULL,
  `idBloquesHorarios` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `idTipoHoras` (`idTipoHoras`,`codigo`)
) ENGINE=MyISAM AUTO_INCREMENT=33 DEFAULT CHARSET=latin1;

/*Table structure for table `incidenciascomedor` */

CREATE TABLE `incidenciascomedor` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `idAlumnos` int(11) NOT NULL,
  `idProfesores` int(11) DEFAULT NULL,
  `idTipoIncidencias` int(11) DEFAULT NULL,
  `idGruposComedor` int(11) DEFAULT NULL,
  `idTipoObservaciones` int(11) DEFAULT NULL,
  `dia` date NOT NULL,
  `comentarios` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `dia` (`dia`),
  KEY `idAlumnos` (`idAlumnos`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

/*Table structure for table `logtareas` */

CREATE TABLE `logtareas` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `tipo` varchar(50) NOT NULL,
  `fecha` datetime DEFAULT NULL,
  `resultado` varchar(255) DEFAULT NULL,
  `datos` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=475 DEFAULT CHARSET=latin1;

/*Table structure for table `mantenimiento` */

CREATE TABLE `mantenimiento` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `idTipoIncidencias` int(11) NOT NULL,
  `descripcion` varchar(255) NOT NULL,
  `idProfesores` int(11) DEFAULT NULL,
  `idUsuarios` int(11) DEFAULT NULL,
  `fechaAnotado` date DEFAULT NULL,
  `horaAnotado` varchar(255) DEFAULT NULL,
  `avisarA` varchar(255) DEFAULT NULL,
  `fechaAvisado` date DEFAULT NULL,
  `horaAvisado` varchar(255) DEFAULT NULL,
  `fechaCerrado` date DEFAULT NULL,
  `horaCerrado` varchar(255) DEFAULT NULL,
  `pagado` varchar(255) DEFAULT NULL,
  `observaciones` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;

/*Table structure for table `mensajes` */

CREATE TABLE `mensajes` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(255) DEFAULT NULL,
  `fecha` date NOT NULL,
  `texto` varchar(255) NOT NULL,
  `idUsuarios` int(11) DEFAULT NULL,
  `idProfesores` int(11) DEFAULT NULL,
  `borradoUp` char(1) DEFAULT NULL,
  `idWeb` int(11) DEFAULT NULL,
  `contestado` char(1) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=716 DEFAULT CHARSET=latin1;

/*Table structure for table `mensajeslistas` */

CREATE TABLE `mensajeslistas` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;

/*Table structure for table `mensajeslistasprofesores` */

CREATE TABLE `mensajeslistasprofesores` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `idMensajesListas` int(11) NOT NULL,
  `idProfesores` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=22 DEFAULT CHARSET=latin1;

/*Table structure for table `mensajesprofesores` */

CREATE TABLE `mensajesprofesores` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `idMensajes` int(11) NOT NULL,
  `idProfesores` int(11) NOT NULL,
  `fechaEnviado` date DEFAULT NULL,
  `fechaLeido` date DEFAULT NULL,
  `borradoUp` char(1) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=12972 DEFAULT CHARSET=latin1;

/*Table structure for table `mensajesweb` */

CREATE TABLE `mensajesweb` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `idProfesores` int(11) NOT NULL,
  `code` int(11) NOT NULL,
  `paraQuien` varchar(255) NOT NULL,
  `deQuien` varchar(255) NOT NULL,
  `sobreQuien` varchar(255) NOT NULL,
  `asunto` varchar(255) DEFAULT NULL,
  `fecha` date DEFAULT NULL,
  `hora` varchar(255) DEFAULT NULL,
  `texto` varchar(255) NOT NULL,
  `usuario` varchar(255) NOT NULL,
  `respuesta` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

/*Table structure for table `menucomedor` */

CREATE TABLE `menucomedor` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `dia` date NOT NULL,
  `plato1` varchar(255) NOT NULL,
  `plato2` varchar(255) NOT NULL,
  `plato3` varchar(255) NOT NULL,
  `comentario` varchar(255) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `dia` (`dia`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

/*Table structure for table `notasalumnos` */

CREATE TABLE `notasalumnos` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `idAlumnos` int(11) NOT NULL,
  `idGrupAsig` int(11) NOT NULL,
  `idEvaluacionesDetalle` int(11) NOT NULL,
  `idDetalleNota` int(11) NOT NULL,
  `fechaModificado` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `idGrupAsig` (`idGrupAsig`),
  KEY `idEvaluacionesDetalle` (`idEvaluacionesDetalle`),
  KEY `idDetalleNota` (`idDetalleNota`),
  KEY `idAlumnos` (`idAlumnos`)
) ENGINE=MyISAM AUTO_INCREMENT=10154 DEFAULT CHARSET=latin1;

/*Table structure for table `notasalumnos_deleted` */

CREATE TABLE `notasalumnos_deleted` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `idNotasAlumnos` int(11) NOT NULL,
  `fechaModificado` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=59 DEFAULT CHARSET=latin1;

/*Table structure for table `notasalumnoscompetencias` */

CREATE TABLE `notasalumnoscompetencias` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `idGruposAlumno` int(11) NOT NULL,
  `idEvaluacionesDetalle` int(11) NOT NULL,
  `idDetalleNota` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `idEvaluacionesDetalle` (`idEvaluacionesDetalle`),
  KEY `idDetalleNota` (`idDetalleNota`),
  KEY `idGruposAlumno` (`idGruposAlumno`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

/*Table structure for table `notasunidad` */

CREATE TABLE `notasunidad` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `idAlumnos` int(11) NOT NULL,
  `idUnidades` int(11) NOT NULL,
  `idGrupAsig` int(11) NOT NULL,
  `idEvaluacionesDetalle` int(11) NOT NULL,
  `idDetalleNota` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

/*Table structure for table `observacionesnotas` */

CREATE TABLE `observacionesnotas` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `idAlumnos` int(11) NOT NULL,
  `idGrupAsig` int(11) NOT NULL,
  `idEvaluacionesDetalle` int(11) NOT NULL,
  `idTipoObservaciones` int(11) DEFAULT NULL,
  `observaciones` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `idAlumnos` (`idAlumnos`,`idGrupAsig`,`idEvaluacionesDetalle`)
) ENGINE=MyISAM AUTO_INCREMENT=60 DEFAULT CHARSET=latin1;

/*Table structure for table `observacionesnotascompetencias` */

CREATE TABLE `observacionesnotascompetencias` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `idGruposAlumno` int(11) NOT NULL,
  `idEvaluacionesDetalle` int(11) NOT NULL,
  `idTipoObservaciones` int(11) DEFAULT NULL,
  `observaciones` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `idEvaluacionesDetalle` (`idEvaluacionesDetalle`),
  KEY `idGruposAlumno` (`idGruposAlumno`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

/*Table structure for table `pesoactividades` */

CREATE TABLE `pesoactividades` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `idActividades` int(11) NOT NULL,
  `idConceptosEvaluables` int(11) NOT NULL,
  `peso` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=15854 DEFAULT CHARSET=latin1;

/*Table structure for table `pesocompetencias` */

CREATE TABLE `pesocompetencias` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `idTipoConceptos` int(11) NOT NULL,
  `idGrupAsig` int(11) NOT NULL,
  `idEvaluacionesDetalle` int(11) NOT NULL,
  `peso` float DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `idGrupAsig` (`idGrupAsig`),
  KEY `idEvaluacionesDetalle` (`idEvaluacionesDetalle`),
  KEY `idTipoConceptos` (`idTipoConceptos`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

/*Table structure for table `presencia` */

CREATE TABLE `presencia` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `codigoUP` int(11) NOT NULL,
  `idProfesores` int(11) DEFAULT NULL,
  `idCentrales` int(11) DEFAULT NULL,
  `desde` datetime DEFAULT NULL,
  `hasta` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `desde` (`desde`),
  KEY `hasta` (`hasta`)
) ENGINE=MyISAM AUTO_INCREMENT=21641 DEFAULT CHARSET=latin1;

/*Table structure for table `profesores` */

CREATE TABLE `profesores` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `codigo` varchar(255) NOT NULL,
  `idUnidadesPersonales` int(11) DEFAULT NULL,
  `nombre` varchar(255) NOT NULL,
  `cargo` varchar(255) DEFAULT NULL,
  `asignatura` varchar(255) DEFAULT NULL,
  `claveUp` varchar(255) DEFAULT NULL,
  `sonidoUp` char(1) DEFAULT NULL,
  `enviarMsgUp` char(1) DEFAULT NULL,
  `controlCalidad` char(1) NOT NULL,
  `actualizarNombres` char(1) NOT NULL,
  `actualizarObservaciones` char(1) NOT NULL,
  `extra1` varchar(255) DEFAULT NULL,
  `enviarSMS` char(1) DEFAULT NULL,
  `telefonoSMS` varchar(255) DEFAULT NULL,
  `codigoGH` varchar(255) DEFAULT NULL,
  `foto` blob,
  `dni` varchar(255) DEFAULT NULL,
  `observacionesProfesor` varchar(255) DEFAULT NULL,
  `fechaSyncMyClass` datetime DEFAULT NULL,
  `fechaBloqueoMyClass` datetime DEFAULT NULL,
  `claveMyClass` varchar(255) DEFAULT NULL,
  `usuarioSG` varchar(255) DEFAULT NULL,
  `claveSG` varchar(255) DEFAULT NULL,
  `habilitadoSG` char(1) DEFAULT NULL,
  `bloqueadoSGDMobile` char(1) DEFAULT NULL,
  `bloqueoMyClass` char(1) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `codigo` (`codigo`)
) ENGINE=MyISAM AUTO_INCREMENT=85 DEFAULT CHARSET=latin1;

/*Table structure for table `programadortareas` */

CREATE TABLE `programadortareas` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `hora` varchar(5) NOT NULL,
  `idDias` int(11) NOT NULL,
  `tipo` varchar(50) DEFAULT NULL,
  `configuracion` text,
  PRIMARY KEY (`id`),
  UNIQUE KEY `hora` (`hora`,`idDias`,`tipo`)
) ENGINE=MyISAM AUTO_INCREMENT=13 DEFAULT CHARSET=latin1;

/*Table structure for table `seguimientofaltas` */

CREATE TABLE `seguimientofaltas` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `idFaltasAlumnos` int(11) NOT NULL,
  `fechaHora` datetime NOT NULL,
  `idProfesores` int(11) NOT NULL,
  `tipo` varchar(255) DEFAULT NULL,
  `comentarios` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

/*Table structure for table `sistemasnotas` */

CREATE TABLE `sistemasnotas` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `codigo` varchar(10) NOT NULL,
  `nombre` varchar(50) NOT NULL,
  `valorMaximo` float NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `codigo` (`codigo`),
  UNIQUE KEY `nombre` (`nombre`)
) ENGINE=MyISAM AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;

/*Table structure for table `smsenvios` */

CREATE TABLE `smsenvios` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `idSMSEnviosServer` int(11) DEFAULT NULL,
  `fecha` datetime DEFAULT NULL,
  `fechaDesde` date DEFAULT NULL,
  `fechaHasta` date DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

/*Table structure for table `smsincidencias` */

CREATE TABLE `smsincidencias` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `idSMSMensajes` int(11) DEFAULT NULL,
  `profesor` varchar(255) DEFAULT NULL,
  `dia` date DEFAULT NULL,
  `hora` varchar(255) DEFAULT NULL,
  `asignatura` varchar(255) DEFAULT NULL,
  `grupo` varchar(255) DEFAULT NULL,
  `idTipoIncidencias` int(11) DEFAULT NULL,
  `idTipoObservaciones` int(11) DEFAULT NULL,
  `comentarios` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

/*Table structure for table `smsmensajes` */

CREATE TABLE `smsmensajes` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `idSMSEnvios` int(11) DEFAULT NULL,
  `idAlumnos` int(11) DEFAULT NULL,
  `idTutores` int(11) DEFAULT NULL,
  `idProfesores` int(11) DEFAULT NULL,
  `telefono` varchar(255) DEFAULT NULL,
  `texto` varchar(255) DEFAULT NULL,
  `enviado` char(1) DEFAULT NULL,
  `operadora` varchar(255) DEFAULT NULL,
  `error` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

/*Table structure for table `smstextos` */

CREATE TABLE `smstextos` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `idTipoIncidencias` int(11) DEFAULT NULL,
  `usar` char(1) DEFAULT NULL,
  `singular` varchar(255) DEFAULT NULL,
  `plural` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

/*Table structure for table `tareas` */

CREATE TABLE `tareas` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `codigo` varchar(255) NOT NULL,
  `nombre` varchar(255) NOT NULL,
  `guardia` char(1) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `codigo` (`codigo`)
) ENGINE=MyISAM AUTO_INCREMENT=48 DEFAULT CHARSET=latin1;

/*Table structure for table `tareasanotadas` */

CREATE TABLE `tareasanotadas` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `idProfesores` int(11) NOT NULL,
  `idProfesoresReal` int(11) DEFAULT NULL,
  `fecha` date DEFAULT NULL,
  `idHorasCentro` int(11) NOT NULL,
  `idTareas` int(11) DEFAULT NULL,
  `hora` varchar(255) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `idTareas` (`idTareas`),
  KEY `idProfesores` (`idProfesores`)
) ENGINE=MyISAM AUTO_INCREMENT=14 DEFAULT CHARSET=latin1;

/*Table structure for table `temarios` */

CREATE TABLE `temarios` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `codigo` varchar(255) NOT NULL,
  `descripcion` varchar(255) NOT NULL,
  `idAsignaturas` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

/*Table structure for table `temas` */

CREATE TABLE `temas` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `idTemarios` int(11) NOT NULL,
  `codigo` varchar(255) NOT NULL,
  `descripcion` varchar(255) NOT NULL,
  `fechaDesde` date NOT NULL,
  `fechaHasta` date NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

/*Table structure for table `temasimpartidos` */

CREATE TABLE `temasimpartidos` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `idClases` int(11) NOT NULL,
  `idProfesores` int(11) NOT NULL,
  `idTemas` int(11) NOT NULL,
  `fecha` date NOT NULL,
  `tipo` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

/*Table structure for table `tipoconceptos` */

CREATE TABLE `tipoconceptos` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `simbolo` varchar(255) NOT NULL,
  `descripcion` varchar(255) NOT NULL,
  `esCompetencia` char(1) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=10 DEFAULT CHARSET=latin1;

/*Table structure for table `tipodetallenota` */

CREATE TABLE `tipodetallenota` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `simbolo` varchar(255) NOT NULL,
  `descripcion` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=30 DEFAULT CHARSET=latin1;

/*Table structure for table `tipoevaluacionesdetalle` */

CREATE TABLE `tipoevaluacionesdetalle` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `simbolo` varchar(255) NOT NULL,
  `simboloUPV6` varchar(255) NOT NULL,
  `descripcion` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;

/*Table structure for table `tipohoras` */

CREATE TABLE `tipohoras` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `descripcion` varchar(255) DEFAULT NULL,
  `codTipoHoras` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `descripcion` (`descripcion`)
) ENGINE=MyISAM AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;

/*Table structure for table `tipoincidencias` */

CREATE TABLE `tipoincidencias` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `simbolo` varchar(255) NOT NULL,
  `descripcion` varchar(255) NOT NULL,
  `peso` int(11) DEFAULT NULL,
  `visibleUp` char(1) DEFAULT NULL,
  `exportarSGDWeb` char(1) DEFAULT NULL,
  `color` int(11) NOT NULL,
  `claveExportacion` varchar(255) DEFAULT NULL,
  `posicion` int(11) NOT NULL,
  `tipo` varchar(30) NOT NULL,
  `justificadaPor` int(11) DEFAULT NULL,
  `requiereObs` char(1) DEFAULT NULL,
  `deBloque` char(1) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `simbolo` (`simbolo`,`tipo`)
) ENGINE=MyISAM AUTO_INCREMENT=27 DEFAULT CHARSET=latin1;

/*Table structure for table `tiponota` */

CREATE TABLE `tiponota` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `abreviatura` varchar(255) NOT NULL,
  `descripcion` varchar(255) NOT NULL,
  `codTipoNota` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=16 DEFAULT CHARSET=latin1;

/*Table structure for table `tiponotassistemas` */

CREATE TABLE `tiponotassistemas` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(50) NOT NULL,
  `codNota` char(2) NOT NULL,
  `idSistemasNotas` int(11) NOT NULL,
  `valor` float NOT NULL,
  `valorMinimo` float NOT NULL,
  `valorMaximo` float NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `codNota` (`codNota`,`idSistemasNotas`),
  KEY `id` (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=6 DEFAULT CHARSET=latin1;

/*Table structure for table `tipoobservaciones` */

CREATE TABLE `tipoobservaciones` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `codigo` varchar(255) NOT NULL,
  `nombre` varchar(255) NOT NULL,
  `descripcion` varchar(255) NOT NULL,
  `valorExportable` varchar(255) DEFAULT NULL,
  `tipo` varchar(50) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `nombre` (`nombre`,`tipo`)
) ENGINE=MyISAM AUTO_INCREMENT=28 DEFAULT CHARSET=latin1;

/*Table structure for table `turnoscomedor` */

CREATE TABLE `turnoscomedor` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `codigo` varchar(255) NOT NULL,
  `descripcion` varchar(255) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `codigo` (`codigo`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

/*Table structure for table `tutores` */

CREATE TABLE `tutores` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `idAlumnos` int(11) DEFAULT NULL,
  `parentesco` varchar(25) NOT NULL,
  `nombre` varchar(255) NOT NULL,
  `enviarCarta` char(1) DEFAULT NULL,
  `dni` varchar(15) DEFAULT NULL,
  `domicilio` varchar(255) DEFAULT NULL,
  `localidad` varchar(255) DEFAULT NULL,
  `provincia` varchar(255) DEFAULT NULL,
  `codPostal` varchar(255) DEFAULT NULL,
  `telefono` varchar(255) DEFAULT NULL,
  `enviarSMS` char(1) DEFAULT NULL,
  `telefonoSMS` varchar(255) DEFAULT NULL,
  `pesoSMS` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `idAlumnos` (`idAlumnos`,`parentesco`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

/*Table structure for table `unidades` */

CREATE TABLE `unidades` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `idAsignaturas` int(11) NOT NULL,
  `unidad` varchar(255) NOT NULL,
  `descripcion` varchar(255) NOT NULL,
  `valorExportable` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `idAsignaturas` (`idAsignaturas`,`unidad`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

/*Table structure for table `versiones` */

CREATE TABLE `versiones` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(255) NOT NULL,
  `valor` varchar(255) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `nombre` (`nombre`)
) ENGINE=MyISAM AUTO_INCREMENT=5 DEFAULT CHARSET=latin1;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
