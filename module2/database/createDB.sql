CREATE DATABASE `certificates`  DEFAULT CHARACTER SET utf8;

CREATE TABLE `certificate` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(45) DEFAULT NULL,
  `description` varchar(45) DEFAULT NULL,
  `price` double DEFAULT NULL,
  `duration` int DEFAULT NULL,
  `create_date` varchar(45) DEFAULT NULL,
  `last_update_date` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=26 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `tag` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(45) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `name_UNIQUE` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=31 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `certificate_tag` (
  `id` int NOT NULL AUTO_INCREMENT,
  `certificate_id` int NOT NULL,
  `tag_id` int NOT NULL,
  PRIMARY KEY (`id`,`certificate_id`,`tag_id`),
  KEY `to_gift_certificate_idx` (`certificate_id`),
  KEY `to_tag_idx` (`tag_id`),
  CONSTRAINT `to_certificate` FOREIGN KEY (`certificate_id`) REFERENCES `certificate` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `to_tag` FOREIGN KEY (`tag_id`) REFERENCES `tag` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=25 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
