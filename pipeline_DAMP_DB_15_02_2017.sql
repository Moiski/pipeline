-- --------------------------------------------------------
-- Хост:                         127.0.0.1
-- Версия сервера:               5.1.41-community - MySQL Community Server (GPL)
-- ОС Сервера:                   Win32
-- HeidiSQL Версия:              9.3.0.4984
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;

-- Дамп структуры базы данных mkpipeline
CREATE DATABASE IF NOT EXISTS `mkpipeline` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `mkpipeline`;


-- Дамп структуры для таблица mkpipeline.actions
CREATE TABLE IF NOT EXISTS `actions` (
  `actionID` bigint(20) NOT NULL AUTO_INCREMENT,
  `actionType` enum('PRINT','RANDOM','COMPLETED','DELAYED') DEFAULT NULL,
  `taskID` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`actionID`),
  KEY `FK_lsn4pc1e7w4kjprnw6gj21drv` (`taskID`),
  CONSTRAINT `FK_lsn4pc1e7w4kjprnw6gj21drv` FOREIGN KEY (`taskID`) REFERENCES `tasks` (`taskID`)
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8;

-- Дамп данных таблицы mkpipeline.actions: ~6 rows (приблизительно)
/*!40000 ALTER TABLE `actions` DISABLE KEYS */;
/*!40000 ALTER TABLE `actions` ENABLE KEYS */;


-- Дамп структуры для таблица mkpipeline.pipelines
CREATE TABLE IF NOT EXISTS `pipelines` (
  `pipelineID` bigint(20) NOT NULL AUTO_INCREMENT,
  `description` varchar(255) DEFAULT NULL,
  `endTime` datetime DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `startTime` datetime DEFAULT NULL,
  `statusType` enum('PENDING','IN_PROGRESS','SKIPPED','FAILED','COMPLETED') DEFAULT NULL,
  PRIMARY KEY (`pipelineID`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

-- Дамп данных таблицы mkpipeline.pipelines: ~1 rows (приблизительно)
/*!40000 ALTER TABLE `pipelines` DISABLE KEYS */;
/*!40000 ALTER TABLE `pipelines` ENABLE KEYS */;


-- Дамп структуры для таблица mkpipeline.tasks
CREATE TABLE IF NOT EXISTS `tasks` (
  `taskID` bigint(20) NOT NULL AUTO_INCREMENT,
  `description` varchar(255) DEFAULT NULL,
  `endTime` datetime DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `startTime` datetime DEFAULT NULL,
  `statusType` enum('PENDING','IN_PROGRESS','SKIPPED','FAILED','COMPLETED') DEFAULT NULL,
  `pipelineID` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`taskID`),
  KEY `FK_1hk7vb53hvjf7dea6xtpkrkex` (`pipelineID`),
  CONSTRAINT `FK_1hk7vb53hvjf7dea6xtpkrkex` FOREIGN KEY (`pipelineID`) REFERENCES `pipelines` (`pipelineID`)
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8;

-- Дамп данных таблицы mkpipeline.tasks: ~6 rows (приблизительно)
/*!40000 ALTER TABLE `tasks` DISABLE KEYS */;
/*!40000 ALTER TABLE `tasks` ENABLE KEYS */;
/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
