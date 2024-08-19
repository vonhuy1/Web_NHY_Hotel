-- phpMyAdmin SQL Dump
-- version 5.2.0
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Dec 24, 2022 at 03:17 AM
-- Server version: 10.4.27-MariaDB
-- PHP Version: 8.1.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `hotel`
--

-- --------------------------------------------------------

--
-- Table structure for table `account`
--

CREATE TABLE `account` (
  `username` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `password` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `full_name` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `gender` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `birthday` date NOT NULL,
  `identity_card` varchar(12) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `phone_number` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `email` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `date_set` date DEFAULT NULL,
  `time_set` time DEFAULT NULL,
  `position_id` int(11) NOT NULL,
  `id` int(11) NOT NULL DEFAULT 0
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `account`
--

INSERT INTO `account` (`username`, `password`, `full_name`, `gender`, `birthday`, `identity_card`, `phone_number`, `email`, `date_set`, `time_set`, `position_id`, `id`) VALUES
('admin', 'MTIzNDU2Nzg=', 'Admin', 'Male', '2002-08-09', '1122334455', '0394197371', 'nhyhotel@gmail.com', '2022-12-10', '20:14:58', 1, 0),
('hienntd', 'MTIzNDU2Nzg=', 'Nguyễn Thị Diệu Hiền', 'Female', '2002-10-30', '4017094944', '0394197371', 'hienntd.02@gmail.com', '2022-12-24', '09:13:27', 2, 0),
('huynhnguyen', 'MTIzNDU2Nzg=', 'Huỳnh Nguyễn Như Nguyên', 'Female', '2002-06-14', '2498719844', '0987893645', 'huynhnguyen123@gmail.com', '2022-12-24', '09:14:46', 2, 0);

-- --------------------------------------------------------

--
-- Table structure for table `checkin`
--

CREATE TABLE `checkin` (
  `ma_dat_phong` int(11) NOT NULL,
  `ho_ten` varchar(100) DEFAULT NULL,
  `sodt` varchar(15) DEFAULT NULL,
  `email` varchar(250) DEFAULT NULL,
  `socmnd` varchar(20) DEFAULT NULL,
  `tien_coc` decimal(12,0) DEFAULT NULL,
  `ma_phong` int(11) DEFAULT NULL,
  `ngay_dat` date DEFAULT NULL,
  `gio_dat` time DEFAULT NULL,
  `loai_dat` varchar(255) DEFAULT NULL,
  `ten_dang_nhap` varchar(50) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;

--
-- Dumping data for table `checkin`
--

INSERT INTO `checkin` (`ma_dat_phong`, `ho_ten`, `sodt`, `email`, `socmnd`, `tien_coc`, `ma_phong`, `ngay_dat`, `gio_dat`, `loai_dat`, `ten_dang_nhap`) VALUES
(1, 'Nguyễn Thị Diệu Hiền', '0394197371', 'hienntd.02@gmail.com', NULL, NULL, 5, '2022-12-25', NULL, NULL, NULL),
(39, 'Nguyễn Vũ Quỳnh Anh', '0356789435', 'hienntd.02@gmail.com', '4927359755', '10', 5, '2022-12-24', '09:55:00', 'theongay', 'admin'),
(40, 'Huỳnh Nguyễn Như Nguyên', '0987463421', NULL, '3407592073', '5', 4, '2022-12-24', '09:57:00', 'homestay', 'admin'),
(41, 'Hoàng Nguyên', '0968754383', NULL, '8458829279', '15', 3, '2022-12-24', '10:00:00', 'homestay', 'admin'),
(42, 'Võ Như Ý', '0978645383', '20133118@student.hcmute.edu.vn', '4972470929', '20', 2, '2022-12-24', '10:10:00', 'theogio', 'admin');

-- --------------------------------------------------------

--
-- Table structure for table `checkincalendar`
--

CREATE TABLE `checkincalendar` (
  `ma_lich` int(11) NOT NULL,
  `ten_nguoi_dat` varchar(50) NOT NULL,
  `so_dien_thoai` varchar(20) NOT NULL,
  `ngay_dat` date NOT NULL,
  `gio_dat` time DEFAULT NULL,
  `thong_tin_them` varchar(255) DEFAULT NULL,
  `ten_dang_nhap` varchar(60) DEFAULT NULL,
  `ma_phong` int(11) DEFAULT NULL,
  `imail` varchar(255) DEFAULT NULL,
  `ngay_tra` date DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;

--
-- Dumping data for table `checkincalendar`
--

INSERT INTO `checkincalendar` (`ma_lich`, `ten_nguoi_dat`, `so_dien_thoai`, `ngay_dat`, `gio_dat`, `thong_tin_them`, `ten_dang_nhap`, `ma_phong`, `imail`, `ngay_tra`) VALUES
(1, 'Nguyễn Quang Phúc', '0847573164', '2022-12-25', NULL, NULL, NULL, 19, 'dieuhiena9@gmail.com', '2022-12-26');

-- --------------------------------------------------------

--
-- Table structure for table `checkout`
--

CREATE TABLE `checkout` (
  `ma_tra_phong` int(11) NOT NULL,
  `ma_dat_phong` int(11) NOT NULL,
  `ngay_tra` date NOT NULL,
  `gio_tra` time DEFAULT NULL,
  `tong_tien` decimal(12,0) NOT NULL,
  `nguoi_thu_tien` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;

--
-- Dumping data for table `checkout`
--

INSERT INTO `checkout` (`ma_tra_phong`, `ma_dat_phong`, `ngay_tra`, `gio_tra`, `tong_tien`, `nguoi_thu_tien`) VALUES
(1, 1, '2022-12-26', NULL, '300', NULL);

-- --------------------------------------------------------

--
-- Table structure for table `collect`
--

CREATE TABLE `collect` (
  `id` int(11) NOT NULL,
  `content` varchar(255) NOT NULL,
  `collect_type` int(1) NOT NULL,
  `collect_date` date DEFAULT NULL,
  `collect_time` time DEFAULT NULL,
  `money` decimal(12,0) NOT NULL,
  `username` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;

--
-- Dumping data for table `collect`
--

INSERT INTO `collect` (`id`, `content`, `collect_type`, `collect_date`, `collect_time`, `money`, `username`) VALUES
(1, 'Buy two Pepsi boxs', 1, '2022-12-12', '15:17:35', '20', 'admin'),
(2, 'Guest buy seven lons Pepsi', 0, '2022-12-14', '15:18:06', '5', 'admin');

-- --------------------------------------------------------

--
-- Table structure for table `display`
--

CREATE TABLE `display` (
  `id` int(2) NOT NULL,
  `position_name` varchar(25) DEFAULT NULL,
  `address` varchar(60) DEFAULT NULL,
  `phone_number` varchar(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;

--
-- Dumping data for table `display`
--

INSERT INTO `display` (`id`, `position_name`, `address`, `phone_number`) VALUES
(1, 'NHY Hotels', '01 Vo Van Ngan, Linh Chieu Ward, Ho Chi Minh City', '0909909909');

-- --------------------------------------------------------

--
-- Table structure for table `historylogin`
--

CREATE TABLE `historylogin` (
  `id` int(11) NOT NULL,
  `login_date` date NOT NULL,
  `login_time` time NOT NULL,
  `account_login` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;

--
-- Dumping data for table `historylogin`
--

INSERT INTO `historylogin` (`id`, `login_date`, `login_time`, `account_login`) VALUES
(67, '2022-12-24', '08:45:02', 'admin');

-- --------------------------------------------------------

--
-- Table structure for table `hourlyparameters`
--

CREATE TABLE `hourlyparameters` (
  `id` int(11) NOT NULL,
  `many_hours_first` int(11) NOT NULL,
  `hours_turn_to_days` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;

--
-- Dumping data for table `hourlyparameters`
--

INSERT INTO `hourlyparameters` (`id`, `many_hours_first`, `hours_turn_to_days`) VALUES
(1, 1, 5);

-- --------------------------------------------------------

--
-- Table structure for table `phong`
--

CREATE TABLE `phong` (
  `ma_phong` int(11) NOT NULL,
  `so_phong` int(11) NOT NULL,
  `tang` int(2) DEFAULT NULL,
  `tien_nghi` varchar(250) DEFAULT NULL,
  `ma_loai_phong` int(11) NOT NULL,
  `hinh_anh` varchar(255) DEFAULT NULL,
  `gia_phong` decimal(12,0) NOT NULL,
  `gia_phong_gio_dau` decimal(12,0) NOT NULL,
  `gia_phong_gio_sau` decimal(12,0) NOT NULL,
  `gia_homestay` decimal(12,0) NOT NULL,
  `khuyen_mai` varchar(250) DEFAULT NULL,
  `trang_thai` int(1) NOT NULL,
  `count_homestay` int(11) DEFAULT NULL,
  `count_dat_lich` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;

--
-- Dumping data for table `phong`
--

INSERT INTO `phong` (`ma_phong`, `so_phong`, `tang`, `tien_nghi`, `ma_loai_phong`, `hinh_anh`, `gia_phong`, `gia_phong_gio_dau`, `gia_phong_gio_sau`, `gia_homestay`, `khuyen_mai`, `trang_thai`, `count_homestay`, `count_dat_lich`) VALUES
(2, 102, 1, 'Air conditioner, Fan, TV, Water heater, Wifi, Fridge, The simplest room with minimal equipment, Small area, Low floor, No view', 2, '2.jpg ', '350', '50', '20', '800', '20', 1, NULL, 1),
(3, 103, 1, 'Air conditioner, Fan, TV, Water heater, Wifi, Fridge, The simplest room with minimal equipment, Small area, Low floor, No view', 2, '3.jpg ', '380', '50', '20', '800', '20', 2, 1, NULL),
(4, 104, 1, 'Air conditioner, Fan, TV, Water heater, Wifi, Fridge, The simplest room with minimal equipment, Small area, Low floor, No view', 2, '4.jpg ', '300', '50', '20', '800', '0', 2, 1, NULL),
(5, 105, 1, 'Air conditioner, Fan, TV, Water heater, Wifi, Fridge, The simplest room with minimal equipment, Small area, Low floor, No view', 2, '5.jpg ', '300', '50', '20', '800', '0', 1, NULL, NULL),
(6, 106, 1, 'Air conditioner, Fan, TV, Water heater, Wifi, Fridge, The simplest room with minimal equipment, Small area, Low floor, No view', 2, '6.jpg ', '350', '50', '20', '800', '0', 0, NULL, NULL),
(7, 201, 2, 'Air conditioner, Fan, TV, Water heater, Wifi, Fridge, The simplest room with minimal equipment, Small area, Low floor, No view', 2, '7.jpg ', '370', '50', '20', '800', '0', 0, NULL, NULL),
(8, 202, 2, 'Air conditioner, Fan, TV, Water heater, Wifi, Fridge, The simplest room with minimal equipment, Small area, Low floor, No view', 2, '8.jpg ', '370', '50', '20', '800', '0', 0, NULL, NULL),
(9, 203, 2, 'Air conditioner, Fan, TV, Water heater, Wifi, Fridge, The simplest room with minimal equipment, Small area, Low floor, No view', 2, '9.jpg ', '380', '50', '20', '800', '0', 0, NULL, NULL),
(10, 204, 2, 'Air conditioner, Fan, TV, Water heater, Wifi, Fridge, The simplest room with minimal equipment, Small area, Low floor, No view', 2, '10.jpg ', '390', '50', '20', '800', '0', 0, NULL, NULL),
(11, 205, 2, 'Air conditioner, Fan, TV, Water heater, Wifi, Fridge, The simplest room with minimal equipment, Small area, Low floor, No view', 2, '11.jpg ', '390', '50', '20', '800', '0', 0, NULL, NULL),
(12, 206, 2, 'Air conditioner, Fan, TV, Water heater, Wifi, Fridge, The simplest room with minimal equipment, Small area, Low floor, No view', 2, '12.jpg ', '320', '50', '20', '800', '0', 0, NULL, NULL),
(13, 301, 3, 'Air conditioner, Fan, TV, Water heater, Wifi, Fridge, The simplest room with minimal equipment, Small area, Low floor, No view', 2, '13.jpg ', '320', '50', '20', '800', '0', 0, NULL, NULL),
(14, 302, 3, 'Air conditioner, Fan, TV, Water heater, Wifi, Fridge, The simplest room with minimal equipment, Small area, Low floor, No view', 2, '14.jpg ', '300', '50', '20', '800', '0', 0, NULL, NULL),
(15, 303, 3, 'Air conditioner, Fan, TV, Water heater, Wifi, Fridge, The simplest room with minimal equipment, Small area, Low floor, No view', 2, '15.jpg ', '300', '50', '20', '800', '0', 0, NULL, NULL),
(16, 304, 3, 'Air conditioner, Fan, TV, Water heater, Wifi, Fridge, The simplest room with minimal equipment, Small area, Low floor, No view', 2, '16.jpg ', '300', '50', '20', '800', '0', 0, NULL, NULL),
(17, 305, 3, 'Air conditioner, Fan, TV, Water heater, Wifi, Fridge, The simplest room with minimal equipment, Small area, Low floor, No view', 2, '17.jpg ', '400', '50', '20', '800', '0', 0, NULL, NULL),
(18, 306, 3, 'Air conditioner, Fan, TV, Water heater, Wifi, Fridge, The simplest room with minimal equipment, Small area, Low floor, No view', 2, '18.jpg ', '420', '50', '20', '800', '0', 0, NULL, NULL),
(19, 401, 4, 'Air conditioner, Fan, TV, Water heater, Wifi, Fridge, The simplest room with minimal equipment, Small area, Low floor, No view, S', 1, '19.jpg ', '510', '50', '20', '800', '0', 0, NULL, 0),
(20, 402, 4, 'Air conditioner, Fan, TV, Water heater, Larger area, Equipped with many comfortable equipment, Beautiful view, Nice Location', 1, '20.jpg ', '510', '30', '60', '1200', '0', 0, NULL, NULL),
(21, 403, 4, 'Air conditioner, Fan, TV, Water heater, Larger area, Equipped with many comfortable equipment, Beautiful view, Nice Location', 1, '21.jpg ', '500', '30', '60', '1200', '0', 0, NULL, NULL),
(22, 404, 4, 'Air conditioner, Fan, TV, Water heater, Larger area, Equipped with many comfortable equipment, Beautiful view, Nice Location', 1, '22.jpg ', '500', '30', '60', '1200', '0', 0, NULL, NULL),
(23, 405, 4, 'Air conditioner, Fan, TV, Water heater, Larger area, Equipped with many comfortable equipment, Beautiful view, Nice Location', 1, '23.jpg ', '550', '30', '60', '1200', '0', 0, NULL, NULL),
(24, 406, 4, 'Air conditioner, Fan, TV, Water heater, Larger area, Equipped with many comfortable equipment, Beautiful view, Nice Location', 1, '24.jpg ', '550', '30', '60', '1200', '0', 0, NULL, NULL),
(25, 501, 5, 'Air conditioner, Fan, TV, Water heater, Larger area, Equipped with many comfortable equipment, Beautiful view, Nice Location', 1, '25.jpg ', '550', '30', '60', '1200', '0', 0, NULL, NULL),
(26, 502, 5, 'Air conditioner, Fan, TV, Water heater, Larger area, Equipped with many comfortable equipment, Beautiful view, Nice Location', 1, '26.jpg ', '580', '30', '60', '1200', '0', 0, NULL, NULL),
(27, 503, 5, 'Air conditioner, Fan, TV, Water heater, Larger area, Equipped with many comfortable equipment, Beautiful view, Nice Location', 1, '27.jpg ', '580', '30', '60', '1200', '0', 0, NULL, NULL),
(28, 504, 5, 'Air conditioner, Fan, TV, Water heater, Larger area, Equipped with many comfortable equipment, Beautiful view, Nice Location', 1, '28.jpg ', '600', '30', '60', '1200', '0', 0, NULL, NULL),
(29, 505, 5, 'Air conditioner, Fan, TV, Water heater, Larger area, Equipped with many comfortable equipment, Beautiful view, Nice Location', 1, '29.jpg ', '600', '30', '60', '1200', '0', 0, NULL, NULL),
(30, 506, 5, 'Air conditioner, Fan, TV, Water heater, Larger area, Equipped with many comfortable equipment, Beautiful view, Nice Location', 1, '30.jpg ', '600', '30', '60', '1200', '0', 0, NULL, NULL),
(31, 601, 6, 'Air conditioning', 1, '70226406757799.jpg', '200', '50', '50', '500', '10', 0, NULL, NULL);

-- --------------------------------------------------------

--
-- Table structure for table `position`
--

CREATE TABLE `position` (
  `position_id` int(11) NOT NULL,
  `position_name` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;

--
-- Dumping data for table `position`
--

INSERT INTO `position` (`position_id`, `position_name`) VALUES
(1, 'Manager'),
(2, 'Staff');

-- --------------------------------------------------------

--
-- Table structure for table `roomtype`
--

CREATE TABLE `roomtype` (
  `ma_loai_phong` int(11) NOT NULL,
  `ten_loai_phong` varchar(100) NOT NULL,
  `mo_ta` varchar(250) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;

--
-- Dumping data for table `roomtype`
--

INSERT INTO `roomtype` (`ma_loai_phong`, `ten_loai_phong`, `mo_ta`) VALUES
(1, 'VIP', 'The room is spacious and airy, fully equipped.'),
(2, 'Normal', 'The room is usually cheap.'),
(3, 'Homestay', 'Rooms for households, go from 4 people or more.');

-- --------------------------------------------------------

--
-- Table structure for table `service`
--

CREATE TABLE `service` (
  `ma_dich_vu` int(11) NOT NULL,
  `ten_dich_vu` varchar(50) NOT NULL,
  `loai_dich_vu` int(11) NOT NULL,
  `gia_dich_vu` decimal(12,0) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;

--
-- Dumping data for table `service`
--

INSERT INTO `service` (`ma_dich_vu`, `ten_dich_vu`, `loai_dich_vu`, `gia_dich_vu`) VALUES
(1, 'Pepsi', 0, '1'),
(2, 'Coca', 0, '1'),
(3, 'Wine CHIVAS', 0, '520'),
(4, 'Wine', 0, '100'),
(5, 'RedBull', 0, '2'),
(6, 'Stomach Drug', 0, '3'),
(7, 'Headache Medicine', 0, '2'),
(8, 'Hamburger', 0, '4'),
(9, 'Broken Rice', 0, '4'),
(10, 'Chicken Rice', 0, '3'),
(11, 'Pork Rib Rice', 0, '3'),
(12, 'Mineral Spring Water', 0, '1'),
(13, 'Fried Chicken KFC', 0, '3'),
(14, 'Beef Stew', 0, '4'),
(15, 'Beef Steak', 0, '5'),
(16, 'Noodles Egg', 0, '2'),
(17, 'Laundry', 1, '2'),
(18, 'Massage Tiket', 2, '6'),
(19, 'Swim Pool Tiket', 2, '3'),
(20, 'Gym Tiket', 2, '3'),
(21, 'Ice', 1, '1');

-- --------------------------------------------------------

--
-- Table structure for table `servicemenu`
--

CREATE TABLE `servicemenu` (
  `ma_don_dich_vu` int(11) NOT NULL,
  `ma_dat_phong` int(11) NOT NULL,
  `ma_dich_vu` int(11) NOT NULL,
  `so_luong` int(11) NOT NULL,
  `ngay_dat` date DEFAULT NULL,
  `gio_dat` time DEFAULT NULL,
  `thong_tin_them` varchar(255) DEFAULT NULL,
  `ten_dang_nhap` varchar(50) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;

--
-- Dumping data for table `servicemenu`
--

INSERT INTO `servicemenu` (`ma_don_dich_vu`, `ma_dat_phong`, `ma_dich_vu`, `so_luong`, `ngay_dat`, `gio_dat`, `thong_tin_them`, `ten_dang_nhap`) VALUES
(10, 40, 1, 5, '2022-12-24', '08:59:22', NULL, 'admin'),
(11, 40, 17, 1, '2022-12-24', '08:59:28', NULL, 'admin');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `account`
--
ALTER TABLE `account`
  ADD PRIMARY KEY (`username`),
  ADD KEY `position_id` (`position_id`);

--
-- Indexes for table `checkin`
--
ALTER TABLE `checkin`
  ADD PRIMARY KEY (`ma_dat_phong`),
  ADD KEY `ma_phong` (`ma_phong`);

--
-- Indexes for table `checkincalendar`
--
ALTER TABLE `checkincalendar`
  ADD PRIMARY KEY (`ma_lich`),
  ADD KEY `ma_phong` (`ma_phong`);

--
-- Indexes for table `checkout`
--
ALTER TABLE `checkout`
  ADD PRIMARY KEY (`ma_tra_phong`),
  ADD KEY `ma_dat_phong` (`ma_dat_phong`);

--
-- Indexes for table `collect`
--
ALTER TABLE `collect`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `display`
--
ALTER TABLE `display`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `historylogin`
--
ALTER TABLE `historylogin`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `hourlyparameters`
--
ALTER TABLE `hourlyparameters`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `phong`
--
ALTER TABLE `phong`
  ADD PRIMARY KEY (`ma_phong`),
  ADD KEY `ma_loai_phong` (`ma_loai_phong`);

--
-- Indexes for table `position`
--
ALTER TABLE `position`
  ADD PRIMARY KEY (`position_id`);

--
-- Indexes for table `roomtype`
--
ALTER TABLE `roomtype`
  ADD PRIMARY KEY (`ma_loai_phong`);

--
-- Indexes for table `service`
--
ALTER TABLE `service`
  ADD PRIMARY KEY (`ma_dich_vu`);

--
-- Indexes for table `servicemenu`
--
ALTER TABLE `servicemenu`
  ADD PRIMARY KEY (`ma_don_dich_vu`),
  ADD KEY `ma_dat_phong` (`ma_dat_phong`),
  ADD KEY `ma_dich_vu` (`ma_dich_vu`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `checkin`
--
ALTER TABLE `checkin`
  MODIFY `ma_dat_phong` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=43;

--
-- AUTO_INCREMENT for table `checkincalendar`
--
ALTER TABLE `checkincalendar`
  MODIFY `ma_lich` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT for table `checkout`
--
ALTER TABLE `checkout`
  MODIFY `ma_tra_phong` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=26;

--
-- AUTO_INCREMENT for table `collect`
--
ALTER TABLE `collect`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT for table `display`
--
ALTER TABLE `display`
  MODIFY `id` int(2) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT for table `historylogin`
--
ALTER TABLE `historylogin`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=68;

--
-- AUTO_INCREMENT for table `hourlyparameters`
--
ALTER TABLE `hourlyparameters`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT for table `phong`
--
ALTER TABLE `phong`
  MODIFY `ma_phong` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=32;

--
-- AUTO_INCREMENT for table `position`
--
ALTER TABLE `position`
  MODIFY `position_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT for table `roomtype`
--
ALTER TABLE `roomtype`
  MODIFY `ma_loai_phong` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT for table `service`
--
ALTER TABLE `service`
  MODIFY `ma_dich_vu` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=22;

--
-- AUTO_INCREMENT for table `servicemenu`
--
ALTER TABLE `servicemenu`
  MODIFY `ma_don_dich_vu` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=12;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `account`
--
ALTER TABLE `account`
  ADD CONSTRAINT `account_ibfk_1` FOREIGN KEY (`position_id`) REFERENCES `position` (`position_id`) ON DELETE CASCADE;

--
-- Constraints for table `checkin`
--
ALTER TABLE `checkin`
  ADD CONSTRAINT `checkin_ibfk_1` FOREIGN KEY (`ma_phong`) REFERENCES `phong` (`ma_phong`) ON DELETE CASCADE;

--
-- Constraints for table `checkincalendar`
--
ALTER TABLE `checkincalendar`
  ADD CONSTRAINT `checkincalendar_ibfk_1` FOREIGN KEY (`ma_phong`) REFERENCES `phong` (`ma_phong`) ON DELETE CASCADE;

--
-- Constraints for table `checkout`
--
ALTER TABLE `checkout`
  ADD CONSTRAINT `checkout_ibfk_1` FOREIGN KEY (`ma_dat_phong`) REFERENCES `checkin` (`ma_dat_phong`) ON DELETE CASCADE;

--
-- Constraints for table `phong`
--
ALTER TABLE `phong`
  ADD CONSTRAINT `phong_ibfk_1` FOREIGN KEY (`ma_loai_phong`) REFERENCES `roomtype` (`ma_loai_phong`) ON DELETE CASCADE;

--
-- Constraints for table `servicemenu`
--
ALTER TABLE `servicemenu`
  ADD CONSTRAINT `servicemenu_ibfk_1` FOREIGN KEY (`ma_dat_phong`) REFERENCES `checkin` (`ma_dat_phong`) ON DELETE CASCADE,
  ADD CONSTRAINT `servicemenu_ibfk_2` FOREIGN KEY (`ma_dich_vu`) REFERENCES `service` (`ma_dich_vu`) ON DELETE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
