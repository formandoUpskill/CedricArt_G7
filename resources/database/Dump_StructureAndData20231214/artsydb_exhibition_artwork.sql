-- MySQL dump 10.13  Distrib 8.0.34, for Win64 (x86_64)
--
-- Host: localhost    Database: artsydb
-- ------------------------------------------------------
-- Server version	8.0.34

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `exhibition_artwork`
--

DROP TABLE IF EXISTS `exhibition_artwork`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `exhibition_artwork` (
  `id_Artwork` char(40) NOT NULL,
  `id_Exhibition` char(40) NOT NULL,
  KEY `id_Artwork` (`id_Artwork`),
  KEY `id_Exhibition` (`id_Exhibition`),
  CONSTRAINT `exhibition_artwork_ibfk_1` FOREIGN KEY (`id_Artwork`) REFERENCES `artwork` (`id_Artwork`),
  CONSTRAINT `exhibition_artwork_ibfk_2` FOREIGN KEY (`id_Exhibition`) REFERENCES `exhibition` (`id_Exhibition`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `exhibition_artwork`
--

LOCK TABLES `exhibition_artwork` WRITE;
/*!40000 ALTER TABLE `exhibition_artwork` DISABLE KEYS */;
INSERT INTO `exhibition_artwork` VALUES ('515b0c5b9562c8fee200049d','57ec2f87275b2403270003bf'),('515b234a223afae9a5000ff4','57ec2f87275b2403270003bf'),('515b8b301b12b0244a000d39','57ec2f87275b2403270003bf'),('515bb95d1b12b0244a00210a','57ec2f87275b2403270003bf'),('515ce0537b7057eb4c001066','57ec2f87275b2403270003bf'),('515d22f15eeb1c524c002fc9','57ec2f87275b2403270003bf'),('515d6d615eeb1c524c004a75','57ec2f87275b2403270003bf'),('516cca40b31e2b8bf60005a6','57ec2f87275b2403270003bf'),('516de0a7fdc441dbf00003ba','57ec2f87275b2403270003bf'),('516df4b0ed1712d0a800093e','57ec2f87275b2403270003bf'),('516df74dfdc4419488000bb5','57ec2f87275b2403270003bf'),('52163abe7622dd2a5c0000ed','58359e5da09a671e5b000b90'),('4eaefa6c76e78f0001009d40','54666b857261696ab21f0d00'),('5075a1937771d70002000bae','54666b857261696ab21f0d00'),('5075a1938620310002000b8b','54666b857261696ab21f0d00'),('5075a19386a0e10002000c68','54666b857261696ab21f0d00'),('507d8ea4ee758f000200246a','54666b857261696ab21f0d00'),('52027a92c9dc24ee2c000039','54666b857261696ab21f0d00'),('5202b2eaa09a67fe01000015','54666b857261696ab21f0d00'),('52163ac1139b21d70600024b','54666b857261696ab21f0d00'),('50770b86794efb0002000b6e','5588589272616971440001d9'),('5084459b0ebbe8000200058e','5588589272616971440001d9'),('51e6be278b3b818a7a00028b','5588589272616971440001d9'),('520268a6a09a67280800009d','5588589272616971440001d9'),('521e475e9c18db71db000158','5588589272616971440001d9'),('521e6881a09a6774530000ee','5588589272616971440001d9'),('521e68b0139b213452000769','5588589272616971440001d9'),('5064d08064046f000200019d','573bbefdb202a36602000d1e'),('50770b862012080002000b25','573bbefdb202a36602000d1e'),('51fab4c7275b2448480005bb','573bbefdb202a36602000d1e'),('520d32947622dd777c0001d6','573bbefdb202a36602000d1e'),('520d4174c9dc24909900028f','573bbefdb202a36602000d1e'),('521d24bd139b215f20000301','573bbefdb202a36602000d1e'),('521e6c2f275b241e63000673','573bbefdb202a36602000d1e'),('52cd66477622dd36e8000023','52c9a6267622dd3f87000056'),('50770b85794efb0002000b66','599b359ac9dc243c2fb324af'),('5344053b9c18dbc35a00014f','533c264debad647b6800012f');
/*!40000 ALTER TABLE `exhibition_artwork` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2023-12-14 14:15:55
