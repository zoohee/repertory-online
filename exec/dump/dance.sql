-- --------------------------------------------------------
-- 호스트:                          repertory.online
-- 서버 버전:                        11.2.2-MariaDB-1:11.2.2+maria~ubu2204 - mariadb.org binary distribution
-- 서버 OS:                        debian-linux-gnu
-- HeidiSQL 버전:                  12.6.0.6765
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;


-- dance 데이터베이스 구조 내보내기
CREATE DATABASE IF NOT EXISTS `dance` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci */;
USE `dance`;

-- 테이블 dance.clone_source 구조 내보내기
CREATE TABLE IF NOT EXISTS `clone_source` (
  `id` bigint(20) NOT NULL,
  `clone_date` datetime(6) DEFAULT NULL,
  `member_id` bigint(20) DEFAULT NULL,
  `tag_name` varchar(255) DEFAULT NULL,
  `origin_id` bigint(20) DEFAULT NULL,
  `source_thumbnail_url` varchar(255) DEFAULT NULL,
  `source_thumbnail` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKg4jf8xgwfifuqybcf1naiycsi` (`origin_id`),
  CONSTRAINT `FKg4jf8xgwfifuqybcf1naiycsi` FOREIGN KEY (`origin_id`) REFERENCES `source` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- 내보낼 데이터가 선택되어 있지 않습니다.

-- 테이블 dance.clone_source_detail 구조 내보내기
CREATE TABLE IF NOT EXISTS `clone_source_detail` (
  `clone_source_id` bigint(20) NOT NULL,
  `source_count` int(11) NOT NULL,
  `source_end` varchar(255) DEFAULT NULL,
  `source_length` int(11) NOT NULL,
  `source_name` varchar(255) DEFAULT NULL,
  `source_start` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`clone_source_id`),
  CONSTRAINT `FKskg2tndwbxbc5subixvb07kl1` FOREIGN KEY (`clone_source_id`) REFERENCES `clone_source` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- 내보낼 데이터가 선택되어 있지 않습니다.

-- 테이블 dance.clone_source_seq 구조 내보내기
CREATE TABLE IF NOT EXISTS `clone_source_seq` (
  `next_not_cached_value` bigint(21) NOT NULL,
  `minimum_value` bigint(21) NOT NULL,
  `maximum_value` bigint(21) NOT NULL,
  `start_value` bigint(21) NOT NULL COMMENT 'start value when sequences is created or value if RESTART is used',
  `increment` bigint(21) NOT NULL COMMENT 'increment value',
  `cache_size` bigint(21) unsigned NOT NULL,
  `cycle_option` tinyint(1) unsigned NOT NULL COMMENT '0 if no cycles are allowed, 1 if the sequence should begin a new cycle when maximum_value is passed',
  `cycle_count` bigint(21) NOT NULL COMMENT 'How many cycles have been done'
) ENGINE=InnoDB SEQUENCE=1;

-- 내보낼 데이터가 선택되어 있지 않습니다.

-- 테이블 dance.source 구조 내보내기
CREATE TABLE IF NOT EXISTS `source` (
  `id` bigint(20) NOT NULL,
  `member_id` bigint(20) DEFAULT NULL,
  `source_date` datetime(6) DEFAULT NULL,
  `source_thumbnail_url` varchar(255) DEFAULT NULL,
  `source_url` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- 내보낼 데이터가 선택되어 있지 않습니다.

-- 테이블 dance.source_detail 구조 내보내기
CREATE TABLE IF NOT EXISTS `source_detail` (
  `source_id` bigint(20) NOT NULL,
  `is_source_open` bit(1) NOT NULL,
  `source_count` int(11) NOT NULL,
  `source_end` varchar(255) DEFAULT NULL,
  `source_length` int(11) NOT NULL,
  `source_name` varchar(255) DEFAULT NULL,
  `source_start` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`source_id`),
  CONSTRAINT `FKcvcu9q7lqplwu0ji8exsxxvwn` FOREIGN KEY (`source_id`) REFERENCES `source` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- 내보낼 데이터가 선택되어 있지 않습니다.

-- 테이블 dance.source_repertory 구조 내보내기
CREATE TABLE IF NOT EXISTS `source_repertory` (
  `repertory_id` bigint(20) NOT NULL,
  `source_id` bigint(20) NOT NULL,
  PRIMARY KEY (`repertory_id`,`source_id`),
  KEY `FKkqhc88svslpv4g4nt7bsp674o` (`source_id`),
  CONSTRAINT `FKkqhc88svslpv4g4nt7bsp674o` FOREIGN KEY (`source_id`) REFERENCES `source` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- 내보낼 데이터가 선택되어 있지 않습니다.

-- 테이블 dance.source_seq 구조 내보내기
CREATE TABLE IF NOT EXISTS `source_seq` (
  `next_not_cached_value` bigint(21) NOT NULL,
  `minimum_value` bigint(21) NOT NULL,
  `maximum_value` bigint(21) NOT NULL,
  `start_value` bigint(21) NOT NULL COMMENT 'start value when sequences is created or value if RESTART is used',
  `increment` bigint(21) NOT NULL COMMENT 'increment value',
  `cache_size` bigint(21) unsigned NOT NULL,
  `cycle_option` tinyint(1) unsigned NOT NULL COMMENT '0 if no cycles are allowed, 1 if the sequence should begin a new cycle when maximum_value is passed',
  `cycle_count` bigint(21) NOT NULL COMMENT 'How many cycles have been done'
) ENGINE=InnoDB SEQUENCE=1;

-- 내보낼 데이터가 선택되어 있지 않습니다.

-- 테이블 dance.source_tag 구조 내보내기
CREATE TABLE IF NOT EXISTS `source_tag` (
  `source_id` bigint(20) NOT NULL,
  `tag_id` bigint(20) NOT NULL,
  PRIMARY KEY (`source_id`,`tag_id`),
  KEY `FKnv4fny4n08hvkl5db1sld1aff` (`tag_id`),
  CONSTRAINT `FKln5hxvhssbjdr3j1kxswme6t7` FOREIGN KEY (`source_id`) REFERENCES `source` (`id`),
  CONSTRAINT `FKnv4fny4n08hvkl5db1sld1aff` FOREIGN KEY (`tag_id`) REFERENCES `tag` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- 내보낼 데이터가 선택되어 있지 않습니다.

-- 테이블 dance.tag 구조 내보내기
CREATE TABLE IF NOT EXISTS `tag` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `member_id` bigint(20) DEFAULT NULL,
  `tag_name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- 내보낼 데이터가 선택되어 있지 않습니다.

/*!40103 SET TIME_ZONE=IFNULL(@OLD_TIME_ZONE, 'system') */;
/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IFNULL(@OLD_FOREIGN_KEY_CHECKS, 1) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40111 SET SQL_NOTES=IFNULL(@OLD_SQL_NOTES, 1) */;
