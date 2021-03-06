-- phpMyAdmin SQL Dump
-- version 5.0.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: 2020 m. Bal 30 d. 20:40
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
-- Sukurta duomenų struktūra lentelei `user`
--

CREATE TABLE `user` (
  `id` int(11) NOT NULL,
  `username` varchar(127) NOT NULL,
  `name_surname` varchar(127) NOT NULL,
  `password` varchar(127) NOT NULL,
  `email` varchar(127) NOT NULL,
  `team_name` varchar(127) NOT NULL,
  `admin` tinyint(4) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='users data';

--
-- Sukurta duomenų kopija lentelei `user`
--

INSERT INTO `user` (`id`, `username`, `name_surname`, `password`, `email`, `team_name`, `admin`) VALUES
(1, 'Saulaitis', 'S.Saulaitis', 'saule12', 'saulaitis@job.com', 'Supreme Team', 1),
(2, 'Samas', 'K.Samas', 'samas23', 'samas@job.com', 'Dream Team', 0),
(3, 'AstaAd', 'A.Adomaitienė', 'astra5', 'astaad@job.com', 'Leading Team', 0),
(4, 'MortaM', 'M.Morkuvienė', 'mmmmm1', 'mortam@job.com', 'Dream Team', 1),
(6, 'Jonulis', 'J.Jonaitis', 'jojo0', 'jon@job.com', 'Supreme Team', 0);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `user`
--
ALTER TABLE `user`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `user`
--
ALTER TABLE `user`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
