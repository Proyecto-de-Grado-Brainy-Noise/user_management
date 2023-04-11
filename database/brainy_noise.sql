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
     `jobtitle` varchar(255) NOT NULL,
     `area` varchar(255) NOT NULL,
     `observations` varchar(500) NOT NULL,
     `password` varchar(255) DEFAULT NULL,
     `creation_date` varchar(255) DEFAULT NULL,
     `id_employee` int DEFAULT NULL,
     PRIMARY KEY (`credential`)
);

CREATE TABLE `result` (
    `id` decimal(38,0) NOT NULL,
    `email_searcher` varchar(255) DEFAULT NULL,
    `file_name` varchar(255) DEFAULT NULL,
    `result` varchar(255) DEFAULT NULL,
    `search_date` datetime(6) DEFAULT NULL,
    PRIMARY KEY (`id`)
);

INSERT INTO brainy_noise.users
(`role`, credential, name, name2, lastname, lastname2, doctype, document, birthdate, jobtitle, area, observations, password, creation_date, id_employee)
VALUES('A', 'estefanibearroyo@gmail.com', 'Estefania', 'Maria', 'Bermudez', 'Arroyo', 1, '1193093873', '2001-01-01', 'Enfermera general', 'Salud general', 'Bien', '$2a$10$ciFhFKgpPZoxhJmhYgD/ROS21ScU4XmETxpWW8aX.qJ/eaYMxlHcC', NULL, 12345678);