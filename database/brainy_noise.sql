CREATE DATABASE `brainy_noise`;

USE `brainy_noise`;

CREATE TABLE `doctypes` (
  `id` int NOT NULL AUTO_INCREMENT,
  `alias` varchar(100) NOT NULL,
  `description` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
);

CREATE TABLE `users` (
  `role` char(1) NOT NULL,
  `credential` varchar(255) NOT NULL,
  `name` varchar(255) NOT NULL,
  `name2` varchar(255) DEFAULT NULL,
  `lastname` varchar(255) NOT NULL,
  `lastname2` varchar(255) DEFAULT NULL,
  `doctype` int NOT NULL,
  `document` varchar(255) NOT NULL,
  `birthdate` date NOT NULL,
  `idEmployee` bigint NOT NULL,
  `jobtitle` varchar(255) NOT NULL,
  `area` varchar(255) NOT NULL,
  `observations` varchar(500) NOT NULL,
  `creationDate` date NOT NULL,
  PRIMARY KEY (`credential`)
);

CREATE TABLE `credentials` (
  `email` varchar(255) NOT NULL,
  `hash` varchar(400) NOT NULL,
  `salt` varchar(400) NOT NULL,
  PRIMARY KEY (`email`),
  CONSTRAINT `credentials_FK` FOREIGN KEY (`email`) REFERENCES `users` (`credential`) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE `results` (
  `searchDate` datetime NOT NULL,
  `result` varchar(255) NOT NULL,
  `fileName` varchar(255) NOT NULL,
  `id` bigint NOT NULL,
  `emailSearcher` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
);