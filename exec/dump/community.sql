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


-- community 데이터베이스 구조 내보내기
CREATE DATABASE IF NOT EXISTS `community` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci */;
USE `community`;

-- 테이블 community.feed 구조 내보내기
CREATE TABLE IF NOT EXISTS `feed` (
  `feed_disable` bit(1) DEFAULT NULL,
  `feed_type` tinyint(4) DEFAULT NULL CHECK (`feed_type` between 0 and 1),
  `download_count` bigint(20) DEFAULT NULL,
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `like_count` bigint(20) DEFAULT NULL,
  `member_id` bigint(20) DEFAULT NULL,
  `origin_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- 내보낼 데이터가 선택되어 있지 않습니다.

-- 테이블 community.like_log 구조 내보내기
CREATE TABLE IF NOT EXISTS `like_log` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `feed_id` bigint(20) DEFAULT NULL,
  `like_active` bit(1) DEFAULT NULL,
  `member_id` bigint(20) DEFAULT NULL,
  `timestamp` datetime(6) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- 내보낼 데이터가 선택되어 있지 않습니다.

-- 테이블 community.subscribe 구조 내보내기
CREATE TABLE IF NOT EXISTS `subscribe` (
  `following_member_id` bigint(20) NOT NULL,
  `member_id` bigint(20) NOT NULL,
  `subscribe_date` datetime(6) DEFAULT NULL,
  PRIMARY KEY (`following_member_id`,`member_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- 내보낼 데이터가 선택되어 있지 않습니다.

/*!40103 SET TIME_ZONE=IFNULL(@OLD_TIME_ZONE, 'system') */;
/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IFNULL(@OLD_FOREIGN_KEY_CHECKS, 1) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40111 SET SQL_NOTES=IFNULL(@OLD_SQL_NOTES, 1) */;
