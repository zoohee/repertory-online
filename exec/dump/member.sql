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


-- member 데이터베이스 구조 내보내기
CREATE DATABASE IF NOT EXISTS `member` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci */;
USE `member`;

-- 테이블 member.member 구조 내보내기
CREATE TABLE IF NOT EXISTS `member` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `member_email` varchar(255) DEFAULT NULL,
  `member_is_available` int(11) NOT NULL,
  `member_join_date` varchar(255) DEFAULT NULL,
  `member_login_id` varchar(255) DEFAULT NULL,
  `member_name` varchar(255) DEFAULT NULL,
  `member_password` varchar(255) DEFAULT NULL,
  `member_profile` varchar(255) DEFAULT NULL,
  `member_role` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- 내보낼 데이터가 선택되어 있지 않습니다.

-- 테이블 member.member_disable 구조 내보내기
CREATE TABLE IF NOT EXISTS `member_disable` (
  `id` int(11) NOT NULL,
  `member_disable_date` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- 내보낼 데이터가 선택되어 있지 않습니다.

-- 테이블 member.oauth2_authorized_client 구조 내보내기
CREATE TABLE IF NOT EXISTS `oauth2_authorized_client` (
  `principal_name` varchar(200) NOT NULL,
  `client_registration_id` varchar(100) NOT NULL,
  `access_token_expires_at` datetime(6) NOT NULL,
  `access_token_issued_at` datetime(6) NOT NULL,
  `access_token_scopes` varchar(1000) DEFAULT NULL,
  `access_token_type` varchar(100) NOT NULL,
  `access_token_value` tinyblob NOT NULL,
  `created_at` timestamp NOT NULL DEFAULT current_timestamp(),
  `refresh_token_issued_at` datetime(6) DEFAULT NULL,
  `refresh_token_value` tinyblob DEFAULT NULL,
  PRIMARY KEY (`client_registration_id`,`principal_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- 내보낼 데이터가 선택되어 있지 않습니다.

/*!40103 SET TIME_ZONE=IFNULL(@OLD_TIME_ZONE, 'system') */;
/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IFNULL(@OLD_FOREIGN_KEY_CHECKS, 1) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40111 SET SQL_NOTES=IFNULL(@OLD_SQL_NOTES, 1) */;
