-- phpMyAdmin SQL Dump
-- version 5.1.0
-- https://www.phpmyadmin.net/
--
-- Servidor: localhost
-- Tiempo de generación: 10-06-2021 a las 16:01:45
-- Versión del servidor: 10.4.19-MariaDB
-- Versión de PHP: 8.0.6

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `VirtualDecision`
--
CREATE DATABASE IF NOT EXISTS `VirtualDecision` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;
USE `VirtualDecision`;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `FAQs`
--

CREATE TABLE `FAQs` (
  `ID` int(11) NOT NULL,
  `Question` varchar(255) NOT NULL,
  `Answer` varchar(255) NOT NULL,
  `ReportID` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `Reports`
--

CREATE TABLE `Reports` (
  `ID` int(11) NOT NULL,
  `Report` varchar(255) NOT NULL,
  `Description` varchar(255) NOT NULL,
  `MaintenanceType` tinyint(1) DEFAULT 0,
  `SupportManagerID` int(11) NOT NULL,
  `MaintenanceEngineerID` int(11) NOT NULL,
  `Observation` varchar(255) DEFAULT NULL,
  `State` tinyint(1) DEFAULT 0
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `Users`
--

CREATE TABLE `Users` (
  `ID` int(11) NOT NULL,
  `Username` varchar(30) COLLATE utf8mb4_spanish_ci NOT NULL,
  `Hash` char(128) COLLATE utf8mb4_spanish_ci NOT NULL,
  `Type` tinyint(1) DEFAULT 0
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_spanish_ci;

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `FAQs`
--
ALTER TABLE `FAQs`
  ADD PRIMARY KEY (`ID`);

--
-- Indices de la tabla `Reports`
--
ALTER TABLE `Reports`
  ADD PRIMARY KEY (`ID`);

--
-- Indices de la tabla `Users`
--
ALTER TABLE `Users`
  ADD PRIMARY KEY (`ID`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `FAQs`
--
ALTER TABLE `FAQs`
  MODIFY `ID` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `Reports`
--
ALTER TABLE `Reports`
  MODIFY `ID` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `Users`
--
ALTER TABLE `Users`
  MODIFY `ID` int(11) NOT NULL AUTO_INCREMENT;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
