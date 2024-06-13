CREATE DATABASE ddbbpfg;
USE ddbbpfg;
-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 13-06-2024 a las 11:59:06
-- Versión del servidor: 10.4.32-MariaDB
-- Versión de PHP: 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `ddbbpfg`
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `t_chats`
--

CREATE TABLE `t_chats` (
  `id` bigint(11) NOT NULL,
  `content_url` varchar(255) NOT NULL,
  `id_creator` bigint(11) DEFAULT NULL,
  `id_interest` bigint(11) DEFAULT NULL,
  `creation_date` datetime(6) NOT NULL,
  `chat_type` int(11) NOT NULL,
  `name` varchar(50) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;


--
-- Estructura de tabla para la tabla `t_chat_users`
--

CREATE TABLE `t_chat_users` (
  `id_user` bigint(20) NOT NULL,
  `id_chat` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;


--
-- Estructura de tabla para la tabla `t_events`
--

CREATE TABLE `t_events` (
  `id` bigint(11) NOT NULL,
  `name` varchar(255) NOT NULL,
  `initial_date` date NOT NULL,
  `end_date` date NOT NULL,
  `id_creator` bigint(11) NOT NULL,
  `create_date` date NOT NULL,
  `location` varchar(255) NOT NULL,
  `description` longtext NOT NULL,
  `id_interest` bigint(11) NOT NULL DEFAULT 0,
  `image_url` varchar(255) NOT NULL,
  `id_event` bigint(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;


-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `t_events_users`
--

CREATE TABLE `t_events_users` (
  `id_user` bigint(20) NOT NULL,
  `id_event` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;


-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `t_interest`
--

CREATE TABLE `t_interest` (
  `id` bigint(11) NOT NULL,
  `name` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;


-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `t_rol`
--

CREATE TABLE `t_rol` (
  `id` bigint(11) NOT NULL,
  `name` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `t_rol`
--

INSERT INTO `t_rol` (`id`, `name`) VALUES
(1, 'Administrador'),
(2, 'Usuario');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `t_userdata`
--

CREATE TABLE `t_userdata` (
  `id` bigint(11) NOT NULL,
  `id_user` bigint(11) NOT NULL,
  `report_number` int(11) NOT NULL DEFAULT 0
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;


-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `t_userdata_interests`
--

CREATE TABLE `t_userdata_interests` (
  `id_userdata` bigint(20) NOT NULL,
  `id_interest` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;


-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `t_users`
--

CREATE TABLE `t_users` (
  `id` bigint(11) NOT NULL,
  `username` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `name` varchar(255) NOT NULL,
  `surname` varchar(255) NOT NULL,
  `email` varchar(255) NOT NULL,
  `age` int(11) NOT NULL,
  `birthdate` date DEFAULT NULL,
  `gender` int(11) NOT NULL,
  `id_rol` bigint(11) NOT NULL,
  `image_url` varchar(255) NOT NULL,
  `profile_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;


--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `t_chats`
--
ALTER TABLE `t_chats`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FK90bes4mnw5q87ombh4alo83ni` (`id_interest`),
  ADD KEY `FKhcl7qy4ue7l2c5aae6uiux180` (`id_creator`);

--
-- Indices de la tabla `t_chat_users`
--
ALTER TABLE `t_chat_users`
  ADD PRIMARY KEY (`id_user`,`id_chat`),
  ADD KEY `FK7iqvvbwr8vnus0pot15j2fx28` (`id_chat`);

--
-- Indices de la tabla `t_events`
--
ALTER TABLE `t_events`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKrqbuclsayuta0u8k1lc9ul1d4` (`id_event`);

--
-- Indices de la tabla `t_events_users`
--
ALTER TABLE `t_events_users`
  ADD PRIMARY KEY (`id_user`,`id_event`),
  ADD KEY `FKcm1oludpg2pe8h8wabgde3p2o` (`id_event`);

--
-- Indices de la tabla `t_interest`
--
ALTER TABLE `t_interest`
  ADD PRIMARY KEY (`id`);

--
-- Indices de la tabla `t_rol`
--
ALTER TABLE `t_rol`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `UK_qihgroqce508mtlg1pmom7r7y` (`name`);

--
-- Indices de la tabla `t_userdata`
--
ALTER TABLE `t_userdata`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKiddipk8e8wwyt6hpwsshxe3ac` (`id_user`);

--
-- Indices de la tabla `t_userdata_interests`
--
ALTER TABLE `t_userdata_interests`
  ADD PRIMARY KEY (`id_userdata`,`id_interest`),
  ADD KEY `FK41aicb7npbb2sf9b7keu35n6o` (`id_interest`);

--
-- Indices de la tabla `t_users`
--
ALTER TABLE `t_users`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `UK_1f8qpknpngd98342v0j2ceadc` (`email`),
  ADD KEY `FKlliqg9k23e5k2umqf25e0ljpd` (`id_rol`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `t_chats`
--
ALTER TABLE `t_chats`
  MODIFY `id` bigint(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=49;

--
-- AUTO_INCREMENT de la tabla `t_events`
--
ALTER TABLE `t_events`
  MODIFY `id` bigint(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=50;

--
-- AUTO_INCREMENT de la tabla `t_interest`
--
ALTER TABLE `t_interest`
  MODIFY `id` bigint(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;

--
-- AUTO_INCREMENT de la tabla `t_rol`
--
ALTER TABLE `t_rol`
  MODIFY `id` bigint(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT de la tabla `t_userdata`
--
ALTER TABLE `t_userdata`
  MODIFY `id` bigint(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=60;

--
-- AUTO_INCREMENT de la tabla `t_users`
--
ALTER TABLE `t_users`
  MODIFY `id` bigint(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=60;

--
-- Restricciones para tablas volcadas
--

--
-- Filtros para la tabla `t_chats`
--
ALTER TABLE `t_chats`
  ADD CONSTRAINT `FK90bes4mnw5q87ombh4alo83ni` FOREIGN KEY (`id_interest`) REFERENCES `t_interest` (`id`),
  ADD CONSTRAINT `FKhcl7qy4ue7l2c5aae6uiux180` FOREIGN KEY (`id_creator`) REFERENCES `t_users` (`id`);

--
-- Filtros para la tabla `t_chat_users`
--
ALTER TABLE `t_chat_users`
  ADD CONSTRAINT `FK7iqvvbwr8vnus0pot15j2fx28` FOREIGN KEY (`id_chat`) REFERENCES `t_chats` (`id`),
  ADD CONSTRAINT `FKelert91bt5tw8eh9hta8a93bb` FOREIGN KEY (`id_user`) REFERENCES `t_users` (`id`);

--
-- Filtros para la tabla `t_events`
--
ALTER TABLE `t_events`
  ADD CONSTRAINT `FKrqbuclsayuta0u8k1lc9ul1d4` FOREIGN KEY (`id_event`) REFERENCES `t_interest` (`id`);

--
-- Filtros para la tabla `t_events_users`
--
ALTER TABLE `t_events_users`
  ADD CONSTRAINT `FKcas1gfy5ncevcyicigcq1hnlw` FOREIGN KEY (`id_user`) REFERENCES `t_users` (`id`),
  ADD CONSTRAINT `FKcm1oludpg2pe8h8wabgde3p2o` FOREIGN KEY (`id_event`) REFERENCES `t_events` (`id`);

--
-- Filtros para la tabla `t_userdata`
--
ALTER TABLE `t_userdata`
  ADD CONSTRAINT `FKiddipk8e8wwyt6hpwsshxe3ac` FOREIGN KEY (`id_user`) REFERENCES `t_users` (`id`);

--
-- Filtros para la tabla `t_userdata_interests`
--
ALTER TABLE `t_userdata_interests`
  ADD CONSTRAINT `FK41aicb7npbb2sf9b7keu35n6o` FOREIGN KEY (`id_interest`) REFERENCES `t_interest` (`id`),
  ADD CONSTRAINT `FKsar4rjwflvl5rpcqjvf8t155g` FOREIGN KEY (`id_userdata`) REFERENCES `t_userdata` (`id`);

--
-- Filtros para la tabla `t_users`
--
ALTER TABLE `t_users`
  ADD CONSTRAINT `FKlliqg9k23e5k2umqf25e0ljpd` FOREIGN KEY (`id_rol`) REFERENCES `t_rol` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
