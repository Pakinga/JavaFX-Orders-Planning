-- phpMyAdmin SQL Dump
-- version 5.0.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: 2020 m. Bal 30 d. 20:39
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
(2, 9001502, 'Truck Box Body', 'K.Samas', 3, 0, 'Scheduled', 2),
(3, 9001503, 'Semitrailer', 'K.Samas, S.Saulaitis', 2, 0, 'Scheduled', 2),
(4, 9001504, 'Truck Box Body', 'M.Morkuvienė, S.Saulaitis', 2, 3, 'Completed', 2),
(5, 9001505, 'Curtainsider', 'K.Samas, M.Morkuvienė', 2, 0, 'Scheduled', 2),
(6, 9001506, 'Semitrailer', 'K.Samas, M.Morkuvienė', 2, 0, 'Processing', 2),
(8, 9001510, 'Drawbar Trailer', 'A.Adomaitienė, S.Saulaitis', 0, 0, 'Scheduled', 1),
(9, 9001508, 'Semitrailer', 'J.Jonaitis, S.Saulaitis', 3, 0, 'Scheduled', 1),
(11, 9001513, 'Semitrailer', 'K.Samas, S.Saulaitis', 2, 0, 'Scheduled', 1),
(12, 9001511, 'Curtainsider', 'J.Jonaitis, K.Samas', 3, 2, 'Completed', 2),
(13, 9001512, 'Drawbar Trailer', 'K.Samas, P.Petraitis', 6, 0, 'Processing', 2),
(14, 9001514, 'Truck Box Body', 'K.Samas, S.Saulaitis', 5, 6, 'Completed', 2),
(15, 9001515, 'Semitrailer', 'K.Samas, A.Adomaitienė', 2, 2, 'Completed', 2),
(17, 9001517, 'Curtainsider', 'J.Jonaitis, K.Samas', 5, 0, 'Processing', 2),
(18, 9001520, 'Truck Box Body', 'M.Morkuvienė, S.Saulaitis', 5, 0, 'Scheduled', 1),
(20, 9001519, 'Drawbar Trailer', 'J.Jonaitis, S.Saulaitis', 3, 0, 'Scheduled', 1),
(21, 9001522, 'Truck Box Body', 'K.Samas, A.Adomaitienė', 2, 0, 'Scheduled', 2),
(22, 9001518, 'Semitrailer', 'K.Samas, A.Adomaitienė', 2, 0, 'Scheduled', 2),
(24, 9001523, 'Curtainsider', 'J.Jonaitis, A.Adomaitienė', 2, 0, 'Processing', 3),
(25, 9001524, 'Completed', 'M.Morkuvienė, A.Adomaitienė', 1, 2, 'Completed', 3),
(26, 9001527, 'Truck Box Body', 'M.Morkuvienė, P.Petraitis, A.Adomaitienė', 5, 0, 'Scheduled', 3),
(27, 9001528, 'Curtainsider', 'A.Adomaitienė, S.Saulaitis', 2, 0, 'Scheduled', 3),
(28, 9001522, 'Drawbar Trailer', 'A.Adomaitienė', 3, 0, 'Processing', 3),
(30, 9001525, 'Truck Box Body', 'J.Jonaitis, M.Morkuvienė, P.Petraitis', 2, 1, 'Completed', 4),
(31, 9001526, 'Drawbar Trailer', 'M.Morkuvienė', 1, 0, 'Scheduled', 4),
(32, 9001530, 'Semitrailer', 'M.Morkuvienė, P.Petraitis', 3, 0, 'Processing', 4),
(33, 9001533, 'Truck Box Body', 'M.Morkuvienė', 3, 0, 'Scheduled', 4),
(34, 9001531, 'Semitrailer', 'J.Jonaitis, A.Adomaitienė', 3, 2, 'Completed', 6),
(35, 9001535, 'Curtainsider', 'J.Jonaitis', 2, 0, 'Processing', 6),
(36, 9001536, 'Truck Box Body', 'J.Jonaitis, K.Samas', 1, 0, 'Scheduled', 6),
(37, 9001537, 'Drawbar Trailer', 'J.Jonaitis, K.Samas', 3, 0, 'Scheduled', 6);

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
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=38;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
