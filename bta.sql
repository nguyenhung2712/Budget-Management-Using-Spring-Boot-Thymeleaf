-- phpMyAdmin SQL Dump
-- version 5.1.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Jun 17, 2022 at 05:32 PM
-- Server version: 10.4.22-MariaDB
-- PHP Version: 8.1.2

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `bta`
--

-- --------------------------------------------------------

--
-- Table structure for table `categories`
--

CREATE TABLE `categories` (
  `id` bigint(20) NOT NULL,
  `name` varchar(255) NOT NULL,
  `icon` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `categories`
--

INSERT INTO `categories` (`id`, `name`, `icon`) VALUES
(1, 'Ăn uống', 'food.png'),
(2, 'Bảo dưỡng xe', 'vehicle.png'),
(3, 'Học phí', 'education.png'),
(4, 'Kiểm tra sức khỏe', 'medical.png'),
(5, 'Hóa đơn tiền điện', 'elec_bill.png'),
(6, 'Hóa đơn tiền nước', 'water_bill.png'),
(7, 'Hóa đơn tiền cáp', 'tv_bill.png'),
(8, 'Hóa đơn tiền wifi', 'wifi_bill.png'),
(9, 'Hóa đơn tiện ích khác', 'other_bills.png'),
(10, 'Tiền thuê nhà', 'rental.png'),
(11, 'Đồ gia dụng', 'houseware.png'),
(12, 'Vật tư', 'personal_items.png'),
(13, 'Bảo hiểm', 'insurances.png'),
(14, 'Nợ', 'debt.png');

-- --------------------------------------------------------

--
-- Table structure for table `expenses`
--

CREATE TABLE `expenses` (
  `expense_id` bigint(20) NOT NULL,
  `amount` float NOT NULL,
  `date` date NOT NULL,
  `description` varchar(255) NOT NULL,
  `csid` bigint(20) DEFAULT NULL,
  `user_id` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `expenses`
--

INSERT INTO `expenses` (`expense_id`, `amount`, `date`, `description`, `csid`, `user_id`) VALUES
(1, 25000, '2022-05-24', 'Tiền uống cà phê', 1, 4),
(4, 3000000, '2022-06-23', 'Go to restaurents with friends', 1, 4),
(5, 1000000, '2022-06-16', 'Tiền mua nội thất thiết yếu', 12, 4),
(7, 1250000, '2022-06-17', 'Tiền nợ vay ngân hàng trả hằng tháng', 14, 4),
(8, 336000, '2022-06-17', 'Tiền bảo hiểm định kỳ', 13, 4),
(9, 2250000, '2022-06-20', 'Tiền thuê nhà', 10, 4),
(10, 350000, '2022-06-01', 'Kiểm tra sức khỏe định kỳ', 4, 4),
(11, 100000, '2022-06-01', 'Tiền thay nhớt xe', 2, 4),
(12, 120000, '2022-06-01', 'Tiền đi chợ', 1, 4),
(13, 345300, '2022-06-01', 'Tiền wifi hằng tháng', 8, 4),
(14, 250000, '2022-06-02', 'Tiền đi đi chợ', 1, 4),
(15, 50000, '2022-06-02', 'Tiền uống coffee', 1, 4),
(16, 94000, '2022-06-03', 'Hóa đơn tiền rác', 9, 4),
(17, 150000, '2022-06-03', 'Tiền đi chợ', 1, 4),
(18, 300000, '2022-06-04', 'Tiền cáp tivi', 7, 4),
(19, 125000, '2022-06-04', 'Tiền đi chợ', 1, 4),
(20, 450000, '2022-06-05', 'Tiền đi chợ', 1, 4),
(21, 101000, '2022-06-05', 'Tiền nước định kỳ', 6, 4),
(22, 1200000, '2022-06-06', 'Mua đồ mới thay cho các món đã cũ', 12, 4),
(23, 160000, '2022-06-07', 'Tiền đi chợ', 1, 4),
(24, 120000, '2022-06-08', 'Tiền đi chợ', 1, 4),
(25, 133400, '2022-06-09', 'Tiền đi chợ', 1, 4),
(26, 125000, '2022-06-10', 'Tiền 5l xăng', 2, 4),
(27, 125000, '2022-06-20', 'Tiền 5l xăng', 2, 4),
(28, 155500, '2022-06-11', 'Tiền đi chợ', 1, 4),
(29, 167300, '2022-06-13', 'Tiền đi chợ', 1, 4),
(30, 450000, '2022-06-14', 'Tiền đi chợ', 1, 4),
(31, 125000, '2022-06-12', 'Tiền đi chợ', 1, 4),
(32, 230000, '2022-06-15', 'Tiền đi chợ', 1, 4),
(33, 120000, '2022-06-16', 'Tiền đi chợ', 1, 4),
(34, 490000, '2022-06-17', 'Tiền đi chợ', 1, 4),
(35, 300000, '2022-06-20', 'Tiền đi chợ', 1, 4),
(36, 500000, '2022-06-23', 'Tiền đi chợ', 1, 4),
(37, 1200000, '2022-06-27', 'Ăn nhà hàng cùng gia đình', 1, 4),
(38, 500000, '2022-06-30', 'Liên hoan cuối tháng cùng công ty', 1, 4),
(39, 300000, '2022-06-17', 'Tiền đi chợ', 1, 8),
(40, 430000, '2022-06-30', 'Tiền xăng', 2, 8);

-- --------------------------------------------------------

--
-- Table structure for table `incomes`
--

CREATE TABLE `incomes` (
  `income_id` bigint(20) NOT NULL,
  `amount` float NOT NULL,
  `date` date NOT NULL,
  `description` varchar(255) NOT NULL,
  `csid` bigint(20) DEFAULT NULL,
  `user_id` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `incomes`
--

INSERT INTO `incomes` (`income_id`, `amount`, `date`, `description`, `csid`, `user_id`) VALUES
(3, 4500000, '2022-06-01', 'Tiền thưởng làm thêm giờ', 2, 4),
(4, 3894000, '2022-06-17', 'Tiền tiết kiệm hằng tuần', 3, 4),
(6, 4500000, '2022-06-04', 'Tiền thường làm thêm giờ', 2, 4),
(7, 4500000, '2022-06-07', 'Tiền thưởng làm thêm giờ', 2, 4),
(8, 4500000, '2022-06-10', 'Tiền thưởng làm thêm giờ', 2, 4),
(9, 4500000, '2022-06-13', 'Tiền thưởng làm thêm giờ', 2, 4),
(10, 4500000, '2022-06-21', 'Tiền thưởng làm thêm giờ', 2, 4),
(11, 15000000, '2022-06-30', 'Tiền lương hằng tháng', 1, 4),
(12, 2534000, '2022-06-10', 'Tiền tiết kiệm hằng tuần', 3, 4),
(13, 4430000, '2022-06-03', 'Tiền tiết kiệm hằng tuần', 3, 4),
(14, 5000000, '2022-06-24', 'Tiền tiết kiệm hằng tuần', 3, 4),
(15, 45000, '2022-06-16', 'Tiền tiết kiệm ngày 16/6/2022', 3, 7),
(16, 24999000, '2022-06-30', 'Tiền lương hằng tháng', 1, 7),
(18, 12000000, '2022-06-18', 'Tiền lương làm thêm tháng 6', 1, 8),
(19, 4500000, '2022-06-30', 'Tiền thưởng làm thêm giờ', 2, 8);

-- --------------------------------------------------------

--
-- Table structure for table `logs`
--

CREATE TABLE `logs` (
  `id` bigint(20) NOT NULL,
  `log_at` varchar(255) NOT NULL,
  `user_id` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `logs`
--

INSERT INTO `logs` (`id`, `log_at`, `user_id`) VALUES
(101, '2022-06-05 12:45:01', 3),
(102, '2022-06-05 12:45:26', 4),
(103, '2022-06-05 13:24:59', 4),
(104, '2022-06-05 13:43:15', 4),
(105, '2022-06-05 13:43:18', 4),
(106, '2022-06-05 13:51:01', 4),
(107, '2022-06-05 14:03:52', 4),
(108, '2022-06-05 14:24:29', 4),
(109, '2022-06-05 14:27:14', 4),
(110, '2022-06-05 14:39:47', 4),
(111, '2022-06-05 14:41:35', 4),
(112, '2022-06-05 14:43:19', 4),
(113, '2022-06-05 14:51:26', 4),
(114, '2022-06-05 14:51:29', 4),
(115, '2022-06-05 14:53:39', 4),
(116, '2022-06-05 15:00:55', 4),
(117, '2022-06-05 15:10:09', 4),
(118, '2022-06-05 15:11:08', 4),
(119, '2022-06-05 15:28:29', 4),
(120, '2022-06-05 15:34:08', 4),
(121, '2022-06-05 15:34:37', 4),
(122, '2022-06-05 15:37:45', 4),
(123, '2022-06-05 15:39:28', 4),
(124, '2022-06-05 16:16:52', 4),
(125, '2022-06-05 17:13:40', 4),
(126, '2022-06-05 17:14:06', 4),
(127, '2022-06-05 17:24:18', 4),
(128, '2022-06-05 17:29:38', 4),
(129, '2022-06-05 17:57:38', 4),
(130, '2022-06-05 17:58:05', 4),
(131, '2022-06-05 17:58:59', 4),
(132, '2022-06-05 18:01:11', 4),
(133, '2022-06-05 18:04:56', 4),
(134, '2022-06-05 18:11:40', 4),
(135, '2022-06-05 18:12:31', 4),
(136, '2022-06-05 18:12:51', 4),
(137, '2022-06-05 18:18:20', 4),
(138, '2022-06-05 18:29:50', 4),
(139, '2022-06-05 18:30:50', 4),
(140, '2022-06-05 18:37:39', 4),
(141, '2022-06-05 18:40:41', 4),
(142, '2022-06-05 18:43:14', 5),
(143, '2022-06-05 18:45:16', 3),
(144, '2022-06-05 22:11:55', 3),
(145, '2022-06-05 22:12:06', 4),
(146, '2022-06-05 22:17:21', 1),
(147, '2022-06-10 18:08:57', 1),
(148, '2022-06-10 19:17:10', 1),
(149, '2022-06-10 20:08:32', 4),
(150, '2022-06-10 20:50:45', 4),
(151, '2022-06-10 21:04:37', 4),
(152, '2022-06-10 21:05:05', 4),
(153, '2022-06-10 21:06:12', 5),
(154, '2022-06-10 21:07:01', 5),
(155, '2022-06-10 21:08:56', 4),
(156, '2022-06-10 21:09:06', 1),
(157, '2022-06-10 21:21:01', 1),
(158, '2022-06-10 22:03:53', 1),
(159, '2022-06-10 22:33:58', 4),
(160, '2022-06-10 22:54:42', 5),
(161, '2022-06-10 22:55:11', 4),
(162, '2022-06-11 00:03:36', 4),
(163, '2022-06-11 00:21:55', 4),
(164, '2022-06-11 00:22:48', 4),
(165, '2022-06-11 00:33:55', 4),
(166, '2022-06-11 01:07:53', 4),
(167, '2022-06-11 01:09:31', 4),
(168, '2022-06-11 01:11:00', 4),
(169, '2022-06-11 01:12:45', 4),
(170, '2022-06-11 01:17:30', 4),
(171, '2022-06-11 01:19:47', 4),
(172, '2022-06-11 01:40:09', 4),
(173, '2022-06-11 01:56:28', 4),
(174, '2022-06-11 01:57:04', 4),
(175, '2022-06-11 02:16:54', 4),
(176, '2022-06-11 02:28:28', 4),
(177, '2022-06-11 02:29:37', 4),
(178, '2022-06-11 02:31:15', 4),
(179, '2022-06-11 18:27:59', 4),
(180, '2022-06-11 18:28:23', 3),
(181, '2022-06-11 19:04:17', 3),
(182, '2022-06-11 19:15:05', 3),
(183, '2022-06-11 19:27:08', 3),
(184, '2022-06-11 19:30:46', 3),
(185, '2022-06-11 19:54:21', 3),
(186, '2022-06-11 19:56:54', 3),
(187, '2022-06-11 20:06:28', 3),
(188, '2022-06-11 20:12:01', 3),
(189, '2022-06-11 20:13:43', 1),
(190, '2022-06-11 20:17:36', 1),
(191, '2022-06-11 20:21:11', 1),
(192, '2022-06-11 22:35:52', 6),
(193, '2022-06-11 23:23:05', 6),
(194, '2022-06-11 23:30:14', 6),
(195, '2022-06-11 23:35:40', 6),
(196, '2022-06-11 23:39:33', 6),
(197, '2022-06-11 23:43:06', 6),
(198, '2022-06-11 23:50:22', 6),
(199, '2022-06-11 23:52:14', 6),
(200, '2022-06-11 23:52:34', 6),
(201, '2022-06-11 23:52:43', 6),
(202, '2022-06-11 23:52:54', 3),
(203, '2022-06-11 23:53:19', 4),
(204, '2022-06-11 23:54:09', 4),
(205, '2022-06-13 13:29:18', 4),
(206, '2022-06-13 13:30:19', 1),
(207, '2022-06-13 18:35:47', 1),
(208, '2022-06-13 18:37:50', 4),
(209, '2022-06-14 15:12:27', 4),
(210, '2022-06-14 15:22:52', 4),
(211, '2022-06-14 15:38:07', 1),
(212, '2022-06-14 15:40:16', 1),
(213, '2022-06-14 15:45:39', 6),
(214, '2022-06-14 15:45:44', 1),
(215, '2022-06-14 15:49:55', 6),
(216, '2022-06-14 15:49:59', 1),
(217, '2022-06-14 15:57:20', 1),
(218, '2022-06-14 15:58:00', 1),
(219, '2022-06-14 16:01:34', 1),
(220, '2022-06-14 16:02:11', 4),
(221, '2022-06-14 16:19:42', 4),
(222, '2022-06-14 16:19:58', 4),
(223, '2022-06-14 16:22:40', 4),
(224, '2022-06-14 16:25:16', 4),
(225, '2022-06-14 17:42:16', 4),
(226, '2022-06-14 19:15:38', 4),
(227, '2022-06-14 20:00:31', 1),
(228, '2022-06-14 20:03:30', 1),
(229, '2022-06-14 20:39:26', 1),
(230, '2022-06-14 20:55:55', 3),
(231, '2022-06-14 20:56:09', 3),
(232, '2022-06-14 20:56:30', 3),
(233, '2022-06-14 21:11:54', 3),
(234, '2022-06-14 21:14:54', 5),
(235, '2022-06-14 21:15:01', 3),
(236, '2022-06-16 02:34:27', 1),
(237, '2022-06-16 02:39:25', 7),
(238, '2022-06-16 16:46:26', 8),
(240, '2022-06-16 16:55:00', 4),
(241, '2022-06-16 16:56:45', 3);

-- --------------------------------------------------------

--
-- Table structure for table `roles`
--

CREATE TABLE `roles` (
  `id` bigint(20) NOT NULL,
  `name` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `roles`
--

INSERT INTO `roles` (`id`, `name`) VALUES
(1, 'ROLE_ADMIN'),
(2, 'ROLE_USER'),
(3, 'ROLE_MODERATOR');

-- --------------------------------------------------------

--
-- Table structure for table `sources`
--

CREATE TABLE `sources` (
  `id` bigint(20) NOT NULL,
  `name` varchar(255) NOT NULL,
  `icon` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `sources`
--

INSERT INTO `sources` (`id`, `name`, `icon`) VALUES
(1, 'Tiền lương', 'salary.png'),
(2, 'Tiền thưởng thêm', 'tip.png'),
(3, 'Tiền tiết kiệm', 'save.png');

-- --------------------------------------------------------

--
-- Table structure for table `users`
--

CREATE TABLE `users` (
  `user_id` bigint(20) NOT NULL,
  `email` varchar(50) NOT NULL,
  `name` varchar(255) NOT NULL,
  `password` varchar(120) NOT NULL,
  `username` varchar(20) NOT NULL,
  `image` varchar(500) DEFAULT NULL,
  `phone` varchar(15) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `users`
--

INSERT INTO `users` (`user_id`, `email`, `name`, `password`, `username`, `image`, `phone`) VALUES
(1, 'nguyenhung12c1@gmail.com', 'Nguyễn Hưng', '$2a$12$f4YUK/Ka87/lS5d.BNej.Oizo5bnBkNclTRlgdW/ayzfVLMl6G6Rq', 'jaser2712', 'portrait_1.jpg', '0706096936'),
(2, 'thahcthognthaci@gmail.com', 'Nguyễn Văn Thạch', '$2a$12$j1rM2QmQi00aGBuTiOMuq.D9SnFKPB/Zhx799UUk6fyvcoXhn.iYy', 'thachnv1234', 'portrait_3.jpeg', '0353938404'),
(3, 'huongthu1773@gmail.com', 'Nguyễn Thị Thu Hương', '$2a$10$l0qUgHmIUsVgmr5VPhIYw.LIfskLFXsNDakj.dnQogWcDlIjWUeOq', 'huong1774', 'portrait_4.jpg', '0963603956'),
(4, 'truonnnntt@gmail.com', 'Nguyễn Nhật Trường', '$2a$10$br41DX2wpPizknqUM7EHVeZdkSxsBHepxnQtD0iOmBQ3pHzCgeqLS', 'truong7974', 'portrait_2.jpg', '0932426516'),
(5, 'quochung97@gmail.com', 'Nguyễn Quốc Hưng', '$2a$10$4SwgCnrrsyAjBvgSV3BpkeSZoCSl236ZdSV06p8vscHRQ/4EXq7tS', 'quochungn9301', 'default.png', '0391828192'),
(6, 'nguyenhung2712@gmail.com', 'Nguyễn Hưng', '$2a$10$pvYD5yInx/eODxCO/.0NT.JoqA.4./T16OfOTRWT/7cUX3C/txoIy', 'jaser9427', 'default.png', '0706096936'),
(7, 'nguyenhung271221@gmail.com', 'Nguyễn Hưng', '$2a$10$TKlgERrKuZSfcY80KcFsEemHeiGY/Z0iDxjdlNPtlY1023jjdtT2y', 'hungnguyen2712', 'default.png', '0706096936'),
(8, 'huyhoang2773@gmail.com', 'Nguyễn Huy Hoàng', '$2a$10$.oLqbXEl3nj2ksW1dL/DcOVEDL7JDJq0asRZYb04lsBu3IYramvzG', 'hoang1994', 'default.png', '0706096936');

-- --------------------------------------------------------

--
-- Table structure for table `user_roles`
--

CREATE TABLE `user_roles` (
  `user_id` bigint(20) NOT NULL,
  `role_id` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `user_roles`
--

INSERT INTO `user_roles` (`user_id`, `role_id`) VALUES
(1, 1),
(1, 2),
(1, 3),
(2, 2),
(2, 3),
(3, 2),
(3, 3),
(4, 2),
(5, 2),
(6, 2),
(7, 2),
(8, 2);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `categories`
--
ALTER TABLE `categories`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `expenses`
--
ALTER TABLE `expenses`
  ADD PRIMARY KEY (`expense_id`),
  ADD KEY `FKf8168aqnieca3e7ld56vt8pnp` (`csid`),
  ADD KEY `FKhpk0n2cbnfiuu5nrgl0ika3hq` (`user_id`);

--
-- Indexes for table `incomes`
--
ALTER TABLE `incomes`
  ADD PRIMARY KEY (`income_id`),
  ADD KEY `FKdi5u0lyrsbdjv6q9w37crjogw` (`csid`),
  ADD KEY `FKfq6qeso6vbt9wu7dyhnx8tpu9` (`user_id`);

--
-- Indexes for table `logs`
--
ALTER TABLE `logs`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKgqy8beil5y4almtq1tiyofije` (`user_id`);

--
-- Indexes for table `roles`
--
ALTER TABLE `roles`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `sources`
--
ALTER TABLE `sources`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`user_id`);

--
-- Indexes for table `user_roles`
--
ALTER TABLE `user_roles`
  ADD PRIMARY KEY (`user_id`,`role_id`),
  ADD KEY `FKh8ciramu9cc9q3qcqiv4ue8a6` (`role_id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `categories`
--
ALTER TABLE `categories`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=19;

--
-- AUTO_INCREMENT for table `expenses`
--
ALTER TABLE `expenses`
  MODIFY `expense_id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=41;

--
-- AUTO_INCREMENT for table `incomes`
--
ALTER TABLE `incomes`
  MODIFY `income_id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=20;

--
-- AUTO_INCREMENT for table `logs`
--
ALTER TABLE `logs`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=242;

--
-- AUTO_INCREMENT for table `roles`
--
ALTER TABLE `roles`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT for table `sources`
--
ALTER TABLE `sources`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- AUTO_INCREMENT for table `users`
--
ALTER TABLE `users`
  MODIFY `user_id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=10;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `expenses`
--
ALTER TABLE `expenses`
  ADD CONSTRAINT `FKf8168aqnieca3e7ld56vt8pnp` FOREIGN KEY (`csid`) REFERENCES `categories` (`id`),
  ADD CONSTRAINT `FKhpk0n2cbnfiuu5nrgl0ika3hq` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`);

--
-- Constraints for table `incomes`
--
ALTER TABLE `incomes`
  ADD CONSTRAINT `FKdi5u0lyrsbdjv6q9w37crjogw` FOREIGN KEY (`csid`) REFERENCES `sources` (`id`),
  ADD CONSTRAINT `FKfq6qeso6vbt9wu7dyhnx8tpu9` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`);

--
-- Constraints for table `logs`
--
ALTER TABLE `logs`
  ADD CONSTRAINT `FKgqy8beil5y4almtq1tiyofije` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`);

--
-- Constraints for table `user_roles`
--
ALTER TABLE `user_roles`
  ADD CONSTRAINT `FKh8ciramu9cc9q3qcqiv4ue8a6` FOREIGN KEY (`role_id`) REFERENCES `roles` (`id`),
  ADD CONSTRAINT `FKhfh9dx7w3ubf1co1vdev94g3f` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
