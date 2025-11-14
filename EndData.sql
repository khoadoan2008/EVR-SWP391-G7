-- 1. Insert dữ liệu bảng Station (không thay đổi)
INSERT INTO Station (name, address, contactNumber, totalSlots, AvailableSlots, operatingHours, latitude, longitude)
VALUES
  ('Chi Nhánh Quận 1', '123 Nguyễn Huệ, Phường Bến Nghé, Quận 1, TP.HCM', '0281234567', 50, 40, '07:00-22:00', 10.7756, 106.7019),
  ('Chi Nhánh Quận 3', '456 Võ Văn Tần, Phường 5, Quận 3, TP.HCM', '0287654321', 30, 25, '07:00-22:00', 10.7826, 106.6909),
  ('Chi Nhánh Bình Thạnh', '789 Điện Biên Phủ, Phường 25, Quận Bình Thạnh, TP.HCM', '0283456789', 40, 35, '06:30-22:30', 10.8014, 106.7147),
  ('Chi Nhánh Tân Bình', '321 Cách Mạng Tháng 8, Phường 10, Quận Tân Bình, TP.HCM', '0289876543', 35, 28, '07:00-22:00', 10.7991, 106.6528);

-- 2. Insert dữ liệu bảng Model (không thay đổi)
INSERT INTO Model (brand, modelName, vehicleType, basePrice, features)
VALUES
  ('VinFast', 'VF 5 Plus', 'Car', 450000, 'Màn hình cảm ứng 10 inch;Hệ thống hỗ trợ lái ADAS;Sạc nhanh;Kết nối thông minh'),
  ('VinFast', 'VF 6', 'Car', 550000, 'Màn hình cảm ứng 12.9 inch;ADAS nâng cao;Sạc nhanh;Ghế da cao cấp;Cửa sổ trời Panorama'),
  ('VinFast', 'VF 7', 'Car', 650000, 'Màn hình cảm ứng 12.9 inch;ADAS cấp 2;Sạc nhanh DC;Ghế chỉnh điện;Âm thanh 8 loa'),
  ('VinFast', 'VF 8', 'Car', 950000, 'Màn hình cảm ứng 15.6 inch;ADAS cấp 2+;Sạc nhanh DC 150kW;Ghế massage;Âm thanh 11 loa;Cốp điện'),
  ('VinFast', 'VF 9', 'Car', 1350000, 'Màn hình cảm ứng 15.6 inch;ADAS cấp 2+;Sạc nhanh DC 150kW;7 chỗ ngồi;Ghế massage;Âm thanh 13 loa;Cửa sổ trời Panorama;Hệ thống treo khí nén'),
  ('VinFast', 'VF e34', 'Car', 350000, 'Màn hình cảm ứng 10 inch;Hệ thống an toàn cơ bản;Sạc AC;Kết nối Bluetooth');

-- 3. Insert dữ liệu bảng Users (sửa Role, Status đúng case)
SET IDENTITY_INSERT Users ON;

INSERT INTO Users (UserID, Address, CreatedAt, DateOfBirth, Email, LicenseImage, Name, PasswordHash, PersonalIdImage, Phone, Role, Status, StationID)
VALUES
(1, '123 Nguyễn Huệ, Quận 1, TP.HCM', '2024-01-01 08:00:00', '1980-01-15', 'admin@evr.com', NULL, 'Nguyễn Văn An', '$2a$10$dummyhash', 'admin_id.jpg', '0901234567', 'Admin', 'Active', NULL),
(2, '456 Lê Lợi, Quận 1, TP.HCM', '2024-01-02 09:00:00', '1990-03-20', 'tranhung.staff@evr.com', 'tranhung_license.jpg', 'Trần Văn Hùng', '$2a$10$dummyhash', 'tranhung_id.jpg', '0912345678', 'Staff', 'Active', 1),
(3, '789 Hai Bà Trưng, Quận 3, TP.HCM', '2024-01-03 09:00:00', '1992-05-15', 'lethimai.staff@evr.com', 'lethimai_license.jpg', 'Lê Thị Mai', '$2a$10$dummyhash', 'lethimai_id.jpg', '0923456789', 'Staff', 'Active', 1),
(4, '321 Trần Hưng Đạo, Quận 5, TP.HCM', '2024-01-04 09:00:00', '1988-07-10', 'phamhoang.staff@evr.com', 'phamhoang_license.jpg', 'Phạm Văn Hoàng', '$2a$10$dummyhash', 'phamhoang_id.jpg', '0934567890', 'Staff', 'Active', 2),
(5, '654 Võ Văn Tần, Quận 3, TP.HCM', '2024-01-05 09:00:00', '1991-09-25', 'vothilan.staff@evr.com', 'vothilan_license.jpg', 'Võ Thị Lan', '$2a$10$dummyhash', 'vothilan_id.jpg', '0945678901', 'Staff', 'Active', 2),
(6, '147 Pasteur, Quận 1, TP.HCM', '2024-01-06 09:00:00', '1989-11-30', 'hoangminh.staff@evr.com', 'hoangminh_license.jpg', 'Hoàng Văn Minh', '$2a$10$dummyhash', 'hoangminh_id.jpg', '0956789012', 'Staff', 'Active', 3),
(7, '258 Điện Biên Phủ, Quận Bình Thạnh, TP.HCM', '2024-01-07 09:00:00', '1993-12-05', 'dothiphuong.staff@evr.com', 'dothiphuong_license.jpg', 'Đỗ Thị Phương', '$2a$10$dummyhash', 'dothiphuong_id.jpg', '0967890123', 'Staff', 'Active', 4),
(8, '369 Lý Thường Kiệt, Quận 10, TP.HCM', '2024-01-10 10:00:00', '1995-02-14', 'nguyenthuy@email.com', 'nguyenthuy_license.jpg', 'Nguyễn Thị Thùy', '$2a$10$dummyhash', 'nguyenthuy_id.jpg', '0978901234', 'Customer', 'Active', NULL),
(9, '741 Nguyễn Thị Minh Khai, Quận 3, TP.HCM', '2024-01-11 10:00:00', '1996-04-20', 'trankien@email.com', 'trankien_license.jpg', 'Trần Văn Kiên', '$2a$10$dummyhash', 'trankien_id.jpg', '0989012345', 'Customer', 'Active', NULL),
(10, '852 Cách Mạng Tháng 8, Quận Tân Bình, TP.HCM', '2024-01-12 10:00:00', '1997-06-15', 'phamhoa@email.com', 'phamhoa_license.jpg', 'Phạm Thị Hoa', '$2a$10$dummyhash', 'phamhoa_id.jpg', '0990123456', 'Customer', 'Active', NULL),
(11, '963 Hoàng Văn Thụ, Quận Phú Nhuận, TP.HCM', '2024-01-13 10:00:00', '1994-08-22', 'leductuan@email.com', 'leductuan_license.jpg', 'Lê Đức Tuấn', '$2a$10$dummyhash', 'leductuan_id.jpg', '0901112233', 'Customer', 'Active', NULL),
(12, '159 Phan Đăng Lưu, Quận Bình Thạnh, TP.HCM', '2024-01-14 10:00:00', '1998-10-18', 'vuthilinh@email.com', 'vuthilinh_license.jpg', 'Vũ Thị Linh', '$2a$10$dummyhash', 'vuthilinh_id.jpg', '0912223344', 'Customer', 'Active', NULL),
(13, '357 Võ Thị Sáu, Quận 3, TP.HCM', '2024-01-15 10:00:00', '1993-03-25', 'buivannam@email.com', 'buivannam_license.jpg', 'Bùi Văn Nam', '$2a$10$dummyhash', 'buivannam_id.jpg', '0923334455', 'Customer', 'Suspended', NULL);

SET IDENTITY_INSERT Users OFF;

-- 4. Insert dữ liệu bảng Vehicle (sửa status đúng case)
INSERT INTO Vehicle (ModelID, StationID, plateNumber, batteryLevel, mileage, status, lastMaintenanceDate)
VALUES
  (1, 1, '51A-12345', 90, 1200, 'Available', '2025-01-05'),
  (1, 1, '51B-67890', 85, 800, 'Available', '2025-01-10'),
  (1, 1, '51C-11111', 70, 2500, 'Rented', '2024-12-20'),
  (2, 1, '51D-22222', 95, 500, 'Available', '2025-02-01'),
  (2, 1, '51E-33333', 88, 1500, 'Available', '2025-01-25'),
  (3, 1, '51F-44444', 92, 1000, 'Available', '2025-01-15'),
  (3, 1, '51G-55555', 60, 3000, 'Maintenance', '2025-02-05'),
  (4, 2, '51H-66666', 100, 300, 'Available', '2025-02-08'),
  (4, 2, '51K-77777', 78, 1800, 'Available', '2025-01-20'),
  (4, 2, '51L-88888', 85, 1200, 'Rented', '2025-01-28'),
  (5, 3, '51M-99999', 90, 2000, 'Available', '2025-01-30'),
  (5, 3, '51N-10101', 95, 800, 'Available', '2025-02-03'),
  (6, 4, '51P-12121', 80, 4000, 'Available', '2024-12-15'),
  (6, 4, '51S-13131', 88, 2200, 'Available', '2025-01-18'),
  (6, 4, '51T-14141', 75, 3500, 'Rented', '2025-01-22');

-- 5. Insert dữ liệu bảng Booking (sửa BookingStatus)
INSERT INTO Booking (UserID, VehicleID, StationID, StaffID, startTime, endTime, totalPrice, BookingStatus)
VALUES
  (8, 1, 1, 2, '2025-01-15 08:00:00', '2025-01-17 18:00:00', 2700000, 'Completed'),
  (9, 4, 1, 3, '2025-01-20 09:00:00', '2025-01-22 17:00:00', 3300000, 'Completed'),
  (10, 3, 1, 2, '2025-02-10 07:00:00', '2025-02-12 19:00:00', 3900000, 'Confirmed'),
  (11, 10, 2, 4, '2025-02-11 10:00:00', '2025-02-13 10:00:00', 3800000, 'Confirmed'),
  (12, 11, 3, 6, '2025-01-25 08:30:00', '2025-01-28 20:00:00', 8100000, 'Completed'),
  (13, 15, 4, 7, '2025-02-09 06:00:00', '2025-02-14 18:00:00', 3150000, 'Confirmed'),
  (8, 5, 1, 3, '2025-02-15 09:00:00', '2025-02-18 17:00:00', 3300000, 'Pending'),
  (9, 8, 2, 5, '2025-02-16 08:00:00', '2025-02-19 20:00:00', 5700000, 'Pending'),
  (10, 6, 1, 2, '2025-01-10 10:00:00', '2025-01-13 18:00:00', 3900000, 'Cancelled'),
  (11, 13, 4, 7, '2025-01-05 07:00:00', '2025-01-08 19:00:00', 2100000, 'Completed');

-- 6. Insert dữ liệu bảng Contract (sửa status)
INSERT INTO Contract (BookingID, renterSignature, staffSignature, signedAt, status)
VALUES
  (1, 'Chữ ký Nguyễn Thị Thùy', 'Chữ ký Trần Văn Hùng', '2025-01-15 08:00:00', 'Completed'),
  (2, 'Chữ ký Trần Văn Kiên', 'Chữ ký Lê Thị Mai', '2025-01-20 09:00:00', 'Completed'),
  (3, 'Chữ ký Phạm Thị Hoa', 'Chữ ký Trần Văn Hùng', '2025-02-10 07:00:00', 'Active'),
  (4, 'Chữ ký Lê Đức Tuấn', 'Chữ ký Phạm Văn Hoàng', '2025-02-11 10:00:00', 'Active'),
  (5, 'Chữ ký Vũ Thị Linh', 'Chữ ký Hoàng Văn Minh', '2025-01-25 08:30:00', 'Completed'),
  (6, 'Chữ ký Bùi Văn Nam', 'Chữ ký Đỗ Thị Phương', '2025-02-09 06:00:00', 'Active'),
  (10, 'Chữ ký Lê Đức Tuấn', 'Chữ ký Đỗ Thị Phương', '2025-01-05 07:00:00', 'Completed');

-- 7. Insert dữ liệu bảng Deposit (status: 'FORFEITED', 'HELD', 'REFUNDED')
INSERT INTO Deposit (BookingID, amount, status, createdAt)
VALUES
  (1, 2000000, 'FORFEITED', '2025-01-15 08:00:00'),
  (2, 3000000, 'FORFEITED', '2025-01-20 09:00:00'),
  (3, 3000000, 'HELD', '2025-02-10 07:00:00'),
  (4, 5000000, 'HELD', '2025-02-11 10:00:00'),
  (5, 7000000, 'FORFEITED', '2025-01-25 08:30:00'),
  (6, 2000000, 'HELD', '2025-02-09 06:00:00'),
  (7, 3000000, 'HELD', '2025-02-15 09:00:00'),
  (8, 5000000, 'HELD', '2025-02-16 08:00:00'),
  (9, 3000000, 'REFUNDED', '2025-01-10 10:00:00'),
  (10, 2000000, 'FORFEITED', '2025-01-05 07:00:00');

-- 8. Insert dữ liệu bảng Payment (method: 'E_WALLET', 'CARD', 'CASH'; status: 'SUCCESS', 'PENDING', 'FAILED')
INSERT INTO Payment (BookingID, method, amount, paymentDate, status)
VALUES
  (1, 'E_WALLET', 2700000, '2025-01-15 08:00:00', 'SUCCESS'),
  (2, 'CARD', 3300000, '2025-01-20 09:00:00', 'SUCCESS'),
  (3, 'E_WALLET', 3900000, '2025-02-10 07:00:00', 'SUCCESS'),
  (4, 'CARD', 3800000, '2025-02-11 10:00:00', 'SUCCESS'),
  (5, 'CARD', 8100000, '2025-01-25 08:30:00', 'SUCCESS'),
  (6, 'CASH', 3150000, '2025-02-09 06:00:00', 'SUCCESS'),
  (7, 'E_WALLET', 1650000, '2025-02-15 09:00:00', 'PENDING');

-- Complaint (status: 'PENDING', 'RESOLVED', 'REJECTED')
INSERT INTO Complaint (ContractID, UserID, StaffID, issueDescription, createdAt, status)
VALUES
  (1, 8, 2, 'Xe có vết trầy xước nhỏ ở cửa sau bên trái', '2025-01-15 08:30:00', 'RESOLVED'),
  (2, 9, 3, 'Giao xe chậm 30 phút so với giờ hẹn', '2025-01-20 09:30:00', 'RESOLVED'),
  (3, 10, 2, 'Pin xe không đủ 100% như cam kết', '2025-02-10 07:30:00', 'PENDING'),
  (5, 12, 6, 'Hệ thống điều hòa không hoạt động tốt', '2025-01-25 10:00:00', 'RESOLVED'),
  (7, 11, 7, 'Nội thất xe chưa được vệ sinh sạch sẽ', '2025-01-05 08:00:00', 'RESOLVED');

-- Feedback (category ENUM: 'SERVICE', 'VEHICLE', 'CLEANLINESS', 'MAINTENANCE')
INSERT INTO Feedback (ContractID, UserID, category, stars, comment, createdAt)
VALUES
  (1, 8, 'SERVICE', 5, 'Dịch vụ tuyệt vời, nhân viên nhiệt tình. Xe chạy êm và tiết kiệm pin.', '2025-01-17 18:30:00'),
  (2, 9, 'VEHICLE', 4, 'Xe đẹp và hiện đại, tuy nhiên giao xe hơi chậm.', '2025-01-22 17:30:00'),
  (5, 12, 'SERVICE', 5, 'Rất hài lòng với dịch vụ thuê xe. Sẽ quay lại lần sau!', '2025-01-28 20:30:00'),
  (7, 11, 'SERVICE', 3, 'Xe hơi bẩn, cần vệ sinh kỹ hơn trước khi giao cho khách.', '2025-01-08 19:30:00'),
  (1, 8, 'VEHICLE', 5, 'Xe được bảo dưỡng tốt, không có vấn đề gì.', '2025-01-17 19:00:00');

-- Issue Reports (issueCategory ENUM: 'mechanical', 'electrical', 'cosmetic'; priority ENUM: 'LOW', 'MEDIUM', 'HIGH'; status ENUM: 'OPEN', 'RESOLVED', 'IN_PROGRESS')
INSERT INTO IssueReport (VehicleID, UserID, StationID, issueCategory, priority, description, photos, status, reportedAt)
VALUES
  (1, 8, 1, 'mechanical', 'LOW', 'Có tiếng kêu nhẹ ở động cơ khi tăng tốc', '["issue_51a12345_1.jpg","issue_51a12345_2.jpg"]', 'RESOLVED', '2025-01-16 14:00:00'),
  (4, 9, 1, 'electrical', 'MEDIUM', 'Pin sụt nhanh hơn bình thường, chỉ chạy được 200km thay vì 300km', '["issue_51d22222_1.jpg"]', 'OPEN', '2025-01-21 11:00:00'),
  (10, 11, 2, 'cosmetic', 'LOW', 'Nội thất bẩn, có vết bẩn trên ghế sau', '[]', 'RESOLVED', '2025-02-11 11:30:00'),
  (11, 12, 3, 'electrical', 'HIGH', 'Hệ thống điều hòa không hoạt động', '["issue_51m99999_1.jpg"]', 'RESOLVED', '2025-01-26 09:00:00'),
  (15, 13, 4, 'mechanical', 'MEDIUM', 'Phanh hơi mềm, cần kiểm tra', '["issue_51t14141_1.jpg"]', 'OPEN', '2025-02-10 08:00:00');

-- Maintenance (status ENUM: 'Open', 'InProgress', 'Closed')
INSERT INTO Maintenance (VehicleID, StationID, StaffID, issueDescription, status, scheduledAt, closedAt, remarks)
VALUES
  (1, 1, 2, 'Kiểm tra tiếng động cơ bất thường', 'Closed', '2025-01-17 08:00:00', '2025-01-17 10:30:00', 'Đã bôi trơn các bộ phận, tiếng động đã hết'),
  (4, 1, 3, 'Kiểm tra sức khỏe pin, cân bằng cell pin', 'IN_PROGRESS', '2025-02-12 09:00:00', NULL, 'Đang chờ thiết bị kiểm tra chuyên dụng'),
  (7, 1, 2, 'Bảo dưỡng định kỳ 3000km', 'Closed', '2025-02-05 08:00:00', '2025-02-05 16:00:00', 'Đã thay dầu phanh, kiểm tra toàn bộ hệ thống'),
  (10, 2, 4, 'Vệ sinh nội thất và ngoại thất', 'Closed', '2025-02-11 14:00:00', '2025-02-11 16:00:00', 'Đã vệ sinh sạch sẽ'),
  (11, 3, 6, 'Sửa chữa hệ thống điều hòa', 'Closed', '2025-01-27 08:00:00', '2025-01-27 15:00:00', 'Đã thay gas điều hòa và kiểm tra compressor'),
  (15, 4, 7, 'Kiểm tra và điều chỉnh hệ thống phanh', 'Open', '2025-02-13 09:00:00', NULL, 'Đã đặt lịch bảo trì');

-- RiskFlag (status ENUM: 'Active', 'Resolved')
INSERT INTO RiskFlag (UserID, FlaggedBy, reason, riskScore, status, flaggedAt)
VALUES
  (13, 2, 'Trả xe muộn 2 giờ so với hợp đồng ở lần thuê trước', 5, 'Active', '2025-01-10 12:00:00'),
  (9, 3, 'Có khiếu nại về giao xe chậm', 3, 'Resolved', '2025-01-20 10:00:00'),
  (11, 7, 'Trả xe với nội thất bẩn, cần vệ sinh lại', 4, 'Active', '2025-01-08 20:00:00');

-- StaffSchedule (shiftType ENUM: 'Morning', 'Evening', status ENUM: 'Scheduled', 'Completed', 'Cancelled')
INSERT INTO StaffSchedule (StaffID, StationID, shiftStart, shiftEnd, shiftType, status, notes)
VALUES
  (2, 1, '2025-02-12 07:00:00', '2025-02-12 15:00:00', 'Morning', 'Scheduled', 'Ca sáng, mở cửa chi nhánh'),
  (3, 1, '2025-02-12 15:00:00', '2025-02-12 22:00:00', 'Evening', 'Scheduled', 'Ca chiều, đóng cửa chi nhánh'),
  (4, 2, '2025-02-12 07:00:00', '2025-02-12 15:00:00', 'Morning', 'Scheduled', 'Ca sáng'),
  (5, 2, '2025-02-12 15:00:00', '2025-02-12 22:00:00', 'Evening', 'Scheduled', 'Ca chiều'),
  (6, 3, '2025-02-12 06:30:00', '2025-02-12 14:30:00', 'Morning', 'Scheduled', 'Ca sáng'),
  (6, 3, '2025-02-13 14:30:00', '2025-02-13 22:30:00', 'Evening', 'Scheduled', 'Ca chiều'),
  (7, 4, '2025-02-12 07:00:00', '2025-02-12 15:00:00', 'Morning', 'Completed', 'Ca sáng - Đã hoàn thành'),
  (7, 4, '2025-02-13 07:00:00', '2025-02-13 15:00:00', 'Morning', 'Scheduled', 'Ca sáng');

-- VehicleConditionReport (ReportType ENUM: 'PRE_RENTAL', 'POST_RENTAL')
INSERT INTO VehicleConditionReport (ContractID, VehicleID, StaffID, reportTime, battery, damageDescription, photos, ReportType)
VALUES
  -- Báo cáo trước khi thuê
  (1, 1, 2, '2025-01-15 07:45:00', 90, 'Xe trong tình trạng tốt, không có hư hỏng', '["vcr_51a12345_pre_1.jpg","vcr_51a12345_pre_2.jpg"]', 'PRE_RENTAL'),
  (2, 4, 3, '2025-01-20 08:45:00', 95, 'Xe hoàn hảo, mới bảo dưỡng', '["vcr_51d22222_pre_1.jpg"]', 'PRE_RENTAL'),
  (3, 3, 2, '2025-02-10 06:45:00', 92, 'Xe tốt, pin đầy', '["vcr_51c11111_pre_1.jpg"]', 'PRE_RENTAL'),
  (4, 10, 4, '2025-02-11 09:45:00', 85, 'Xe trong tình trạng tốt', '["vcr_51l88888_pre_1.jpg"]', 'PRE_RENTAL'),
  (5, 11, 6, '2025-01-25 08:15:00', 90, 'Xe hoàn hảo', '["vcr_51m99999_pre_1.jpg"]', 'PRE_RENTAL'),

  -- Báo cáo sau khi trả xe
  (1, 1, 2, '2025-01-17 18:15:00', 35, 'Vết trầy nhỏ ở cửa sau trái, pin còn 35%', '["vcr_51a12345_post_1.jpg","vcr_51a12345_post_2.jpg"]', 'POST_RENTAL'),
  (2, 4, 3, '2025-01-22 17:15:00', 40, 'Xe sạch sẽ, không có hư hỏng mới', '["vcr_51d22222_post_1.jpg"]', 'POST_RENTAL'),
  (5, 11, 6, '2025-01-28 20:15:00', 25, 'Hệ thống điều hòa có vấn đề', '["vcr_51m99999_post_1.jpg"]', 'POST_RENTAL'),
  (7, 15, 7, '2025-01-08 19:15:00', 48, 'Nội thất bẩn, cần vệ sinh', '["vcr_51t14141_post_1.jpg"]', 'POST_RENTAL');
-- AuditLog
INSERT INTO AuditLog (action, timestamp, UserID)
VALUES
  ('LOGIN', '2025-01-01 08:00:00', 1),
  ('CREATE', '2025-01-02 09:15:00', 2),
  ('UPDATE', '2025-01-03 10:30:00', 3),
  ('DELETE', '2025-01-04 11:45:00', 4),
  ('LOGOUT', '2025-01-05 12:00:00', 1),
  ('VIEW', '2025-01-06 13:20:00', 5),
  ('LOGIN', '2025-01-07 08:05:00', 6),
  ('UPDATE', '2025-01-08 14:00:00', 7),
  ('CREATE', '2025-01-09 15:15:00', 8),
  ('LOGOUT', '2025-01-10 16:30:00', 9);

