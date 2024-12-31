-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Jan 06, 2024 at 01:02 PM
-- Server version: 10.4.32-MariaDB
-- PHP Version: 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `tubes`
--

-- --------------------------------------------------------

--
-- Table structure for table `admin`
--

CREATE TABLE `admin` (
  `email` varchar(32) NOT NULL,
  `password` varchar(32) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `admin`
--

INSERT INTO `admin` (`email`, `password`) VALUES
('admin@admin.com', 'admin'),
('admin', 'admin');

-- --------------------------------------------------------

--
-- Table structure for table `booking`
--

CREATE TABLE `booking` (
  `BookingID` int(11) NOT NULL,
  `CustomerID` int(11) NOT NULL,
  `TrainID` int(11) NOT NULL,
  `WagonID` int(11) NOT NULL,
  `RouteID` int(11) NOT NULL,
  `Status` int(11) DEFAULT 0,
  `PaymentInfo` int(11) DEFAULT 0,
  `Seats` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `booking`
--

INSERT INTO `booking` (`BookingID`, `CustomerID`, `TrainID`, `WagonID`, `RouteID`, `Status`, `PaymentInfo`, `Seats`) VALUES
(1, 1, 2, 1, 1, 1, 0, 30),
(2, 2, 2, 2, 1, 1, 0, 5),
(3, 1, 4, 3, 3, 1, 1, 1),
(4, 1, 2, 1, 1, 1, 0, 15),
(5, 1, 2, 1, 1, 1, 0, 7),
(6, 1, 2, 2, 1, 1, 0, 2),
(7, 1, 2, 1, 1, 1, 0, 20),
(8, 6, 4, 2, 3, 0, 0, 2),
(9, 6, 3, 1, 2, 0, 1, 1),
(10, 1, 5, 3, 8, 1, 0, 1),
(11, 1, 4, 1, 3, 1, 0, 5),
(12, 1, 7, 6, 9, 1, 1, 2),
(13, 1, 4, 2, 3, 1, 0, 2);

-- --------------------------------------------------------

--
-- Table structure for table `review`
--

CREATE TABLE `review` (
  `ReviewID` int(11) NOT NULL,
  `BookingID` int(11) DEFAULT NULL,
  `ReviewText` varchar(512) DEFAULT NULL,
  `Rating` smallint(6) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `review`
--

INSERT INTO `review` (`ReviewID`, `BookingID`, `ReviewText`, `Rating`) VALUES
(1, 3, 'Bagus', 5),
(2, 12, 'Menarik', 5);

-- --------------------------------------------------------

--
-- Table structure for table `route`
--

CREATE TABLE `route` (
  `RouteID` int(11) NOT NULL,
  `RouteName` varchar(32) NOT NULL,
  `RouteFrom` varchar(64) NOT NULL,
  `Destination` varchar(64) NOT NULL,
  `Duration` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `route`
--

INSERT INTO `route` (`RouteID`, `RouteName`, `RouteFrom`, `Destination`, `Duration`) VALUES
(1, 'JaBan', 'Jakarta', 'Bandung', 90),
(2, 'BanJa', 'Bandung', 'Jakarta', 90),
(3, 'JakSem', 'Jakarta', 'Semarang', 270),
(4, 'WoTa', 'Wonogiri', 'Tasikmalaya', 540),
(5, 'GiTa', 'Gianyar', 'Tabanan', 30),
(6, 'SaNi', 'Sawahlunto', 'Nias', 240),
(7, 'SukaMojok', 'Sukabumi', 'Mojokerto', 540),
(8, 'CiKa', 'Cikarang', 'Karawang', 20),
(9, 'Balon', 'Bandung', 'London', 960),
(10, 'SaKa', 'Salatiga', 'Katapang', 120);

-- --------------------------------------------------------

--
-- Table structure for table `train`
--

CREATE TABLE `train` (
  `TrainID` int(11) NOT NULL,
  `TrainName` varchar(32) NOT NULL,
  `TrainRoute` int(11) NOT NULL,
  `TrainCapacity` int(11) NOT NULL,
  `Day` varchar(16) NOT NULL,
  `Time` varchar(16) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `train`
--

INSERT INTO `train` (`TrainID`, `TrainName`, `TrainRoute`, `TrainCapacity`, `Day`, `Time`) VALUES
(2, 'Whoosh', 1, 50, 'Senin', '09:00'),
(3, 'Shoohw', 2, 30, 'Selasa', '12:00'),
(4, 'Bangunkarta', 3, 65, 'Jumat', '18:00'),
(5, 'KeretaGrad', 8, 60, 'Sabtu', '18:00'),
(6, 'KeretaMalam', 7, 80, 'Kamis', '18:00'),
(7, 'AgroLondon', 9, 45, 'Sabtu', '15:00'),
(8, 'SakaTrain', 10, 20, 'Sabtu', '18:00');

-- --------------------------------------------------------

--
-- Table structure for table `trainwagon`
--

CREATE TABLE `trainwagon` (
  `TrainWagonID` int(11) NOT NULL,
  `TrainID` int(11) NOT NULL,
  `WagonID` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `trainwagon`
--

INSERT INTO `trainwagon` (`TrainWagonID`, `TrainID`, `WagonID`) VALUES
(3, 2, 1),
(4, 2, 2),
(5, 3, 1),
(6, 4, 1),
(8, 4, 2),
(7, 4, 3),
(14, 5, 1),
(11, 5, 3),
(12, 5, 4),
(15, 6, 1),
(16, 6, 2),
(18, 6, 3),
(19, 6, 5),
(26, 7, 3),
(21, 7, 5),
(20, 7, 6),
(27, 8, 7);

-- --------------------------------------------------------

--
-- Table structure for table `user`
--

CREATE TABLE `user` (
  `ID` int(11) NOT NULL,
  `Nama` varchar(64) NOT NULL,
  `Email` varchar(128) NOT NULL,
  `Phone` varchar(16) NOT NULL,
  `Password` varchar(64) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `user`
--

INSERT INTO `user` (`ID`, `Nama`, `Email`, `Phone`, `Password`) VALUES
(1, 'Aqil', 'Aqil', '082298930550', 'aqil'),
(2, 'haiqal', 'haiqal', '089929384667', 'haiqal'),
(6, 'Adzana Shaliha', 'Ashel48@gmail.com', '085925326270', 'ditowota'),
(7, 'user', 'user@gmail.com', '08', 'user');

-- --------------------------------------------------------

--
-- Table structure for table `wagon`
--

CREATE TABLE `wagon` (
  `WagonID` int(11) NOT NULL,
  `WagonName` varchar(32) NOT NULL,
  `WagonType` varchar(32) NOT NULL,
  `Seats` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `wagon`
--

INSERT INTO `wagon` (`WagonID`, `WagonName`, `WagonType`, `Seats`) VALUES
(1, 'GerbongPertama', 'Economy', 30),
(2, 'GerbongKedua', 'Business', 20),
(3, 'GerbongPerempuan', 'Business', 15),
(4, 'GerbongExecutive', 'Executive', 15),
(5, 'GerbongExecutive 2', 'Executive', 15),
(6, 'Eksekutif 1', 'Executive', 15),
(7, 'Bisnis 1', 'Business', 20);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `booking`
--
ALTER TABLE `booking`
  ADD PRIMARY KEY (`BookingID`),
  ADD KEY `TrainID` (`TrainID`),
  ADD KEY `WagonID` (`WagonID`),
  ADD KEY `RouteID` (`RouteID`);

--
-- Indexes for table `review`
--
ALTER TABLE `review`
  ADD PRIMARY KEY (`ReviewID`),
  ADD KEY `BookingID` (`BookingID`);

--
-- Indexes for table `route`
--
ALTER TABLE `route`
  ADD PRIMARY KEY (`RouteID`);

--
-- Indexes for table `train`
--
ALTER TABLE `train`
  ADD PRIMARY KEY (`TrainID`),
  ADD KEY `TrainRoute` (`TrainRoute`);

--
-- Indexes for table `trainwagon`
--
ALTER TABLE `trainwagon`
  ADD PRIMARY KEY (`TrainWagonID`),
  ADD UNIQUE KEY `unique_train_wagon` (`TrainID`,`WagonID`),
  ADD KEY `WagonID` (`WagonID`);

--
-- Indexes for table `user`
--
ALTER TABLE `user`
  ADD PRIMARY KEY (`ID`),
  ADD UNIQUE KEY `Email` (`Email`);

--
-- Indexes for table `wagon`
--
ALTER TABLE `wagon`
  ADD PRIMARY KEY (`WagonID`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `booking`
--
ALTER TABLE `booking`
  MODIFY `BookingID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=14;

--
-- AUTO_INCREMENT for table `review`
--
ALTER TABLE `review`
  MODIFY `ReviewID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT for table `route`
--
ALTER TABLE `route`
  MODIFY `RouteID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;

--
-- AUTO_INCREMENT for table `train`
--
ALTER TABLE `train`
  MODIFY `TrainID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=9;

--
-- AUTO_INCREMENT for table `trainwagon`
--
ALTER TABLE `trainwagon`
  MODIFY `TrainWagonID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=28;

--
-- AUTO_INCREMENT for table `user`
--
ALTER TABLE `user`
  MODIFY `ID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;

--
-- AUTO_INCREMENT for table `wagon`
--
ALTER TABLE `wagon`
  MODIFY `WagonID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `booking`
--
ALTER TABLE `booking`
  ADD CONSTRAINT `booking_ibfk_1` FOREIGN KEY (`TrainID`) REFERENCES `train` (`TrainID`),
  ADD CONSTRAINT `booking_ibfk_2` FOREIGN KEY (`WagonID`) REFERENCES `wagon` (`WagonID`),
  ADD CONSTRAINT `booking_ibfk_3` FOREIGN KEY (`RouteID`) REFERENCES `route` (`RouteID`);

--
-- Constraints for table `review`
--
ALTER TABLE `review`
  ADD CONSTRAINT `review_ibfk_1` FOREIGN KEY (`BookingID`) REFERENCES `booking` (`BookingID`);

--
-- Constraints for table `train`
--
ALTER TABLE `train`
  ADD CONSTRAINT `train_ibfk_1` FOREIGN KEY (`TrainRoute`) REFERENCES `route` (`RouteID`);

--
-- Constraints for table `trainwagon`
--
ALTER TABLE `trainwagon`
  ADD CONSTRAINT `trainwagon_ibfk_1` FOREIGN KEY (`TrainID`) REFERENCES `train` (`TrainID`),
  ADD CONSTRAINT `trainwagon_ibfk_2` FOREIGN KEY (`WagonID`) REFERENCES `wagon` (`WagonID`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
