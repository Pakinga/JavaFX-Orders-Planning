-- phpMyAdmin SQL Dump
-- version 5.0.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: 2020 m. Kov 30 d. 17:25
-- Server version: 10.4.11-MariaDB
-- PHP Version: 7.4.1

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `db_plan`
--

-- --------------------------------------------------------

--
-- Sukurta duomenų struktūra lentelei `plan`
--

CREATE TABLE `plan` (
  `id` int(11) NOT NULL,
  `order_number` int(11) NOT NULL,
  `product_type` varchar(127) NOT NULL,
  `worker` varchar(127) NOT NULL,
  `planned_time` int(11) NOT NULL,
  `actual_time` int(11) NOT NULL,
  `order_status` varchar(127) NOT NULL,
  `user_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Sukurta duomenų kopija lentelei `plan`
--

INSERT INTO `plan` (`id`, `order_number`, `product_type`, `worker`, `planned_time`, `actual_time`, `order_status`, `user_id`) VALUES
(1, 9001501, 'Curtainsider', 'K.Samas', 3, 4, 'Completed', 1),
(2, 9001502, 'Truck Box Body', 'K.Samas', 3, 0, 'Sheduled', 2),
(3, 9001503, 'Semitrailer', 'K.Samas, S.Saulaitis', 2, 0, 'Scheduled', 2),
(4, 9001504, 'Truck Box Body', 'M.Morkuvienė, S.Saulaitis', 2, 2, 'Completed', 2),
(5, 9001505, 'Curtainsider', 'K.Samas, M.Morkuvienė', 2, 0, 'Scheduled', 2),
(6, 9001506, 'Semitrailer', 'K.Samas, M.Morkuvienė', 2, 0, 'Processing', 2),
(8, 9001510, 'Drawbar Trailer', 'A.Adomaitienė, S.Saulaitis', 0, 0, 'Scheduled', 1),
(9, 9001508, 'Semitrailer', 'J.Jonaitis, S.Saulaitis', 3, 0, 'Scheduled', 1),
(10, 9001509, 'Drawbar Trailer', 'P.Petraitis, S.Saulaitis', 11, 0, 'Scheduled', 1),
(11, 9001513, 'Semitrailer', 'K.Samas, S.Saulaitis', 2, 0, 'Scheduled', 1),
(12, 9001511, 'Curtainsider', 'J.Jonaitis, K.Samas', 3, 2, 'Completed', 2),
(13, 9001512, 'Drawbar Trailer', 'K.Samas, P.Petraitis', 6, 5, 'Processing', 2),
(14, 9001514, 'Truck Box Body', 'K.Samas, S.Saulaitis', 5, 6, 'Scheduled', 2),
(15, 9001515, 'Semitrailer', 'K.Samas, A.Adomaitienė', 2, 2, 'Scheduled', 2);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `plan`
--
ALTER TABLE `plan`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `plan`
--
ALTER TABLE `plan`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=16;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
