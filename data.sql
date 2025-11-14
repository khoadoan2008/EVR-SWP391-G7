INSERT INTO Users (UserID, Address, CreatedAt, DateOfBirth, Email, LicenseImage, Name, PasswordHash, PersonalImage, Phone, Role, Status, StationID)
VALUES
(1, 'Admin Address', '2024-01-01 08:00:00.000000', '1980-01-15', 'admin@evr.com', NULL, 'Admin User', '$2a$10$dummyhash', 'admin_id.jpg', '0900000001', 'ADMIN', 'ACTIVE', NULL),
(2, '123 Staff St', '2024-01-02 09:00:00.000000', '1990-03-20', 'john.staff@evr.com', 'john_license.jpg', 'John Staff', '$2a$10$dummyhash', 'john_id.jpg', '0900000002', 'STAFF', 'ACTIVE', 1),
(3, '456 Staff Ave', '2024-01-03 09:00:00.000000', '1992-05-15', 'jane.staff@evr.com', 'jane_license.jpg', 'Jane Staff', '$2a$10$dummyhash', 'jane_id.jpg', '0900000003', 'STAFF', 'ACTIVE', 1),
(4, '789 Staff Rd', '2024-01-04 09:00:00.000000', '1988-07-10', 'bob.staff@evr.com', 'bob_license.jpg', 'Bob Staff', '$2a$10$dummyhash', 'bob_id.jpg', '0900000004', 'STAFF', 'ACTIVE', 2),
(5, '321 Staff Blvd', '2024-01-05 09:00:00.000000', '1991-09-25', 'alice.staff@evr.com', 'alice_license.jpg', 'Alice Staff', '$2a$10$dummyhash', 'alice_id.jpg', '0900000005', 'STAFF', 'ACTIVE', 2),
(6, '654 Staff Ln', '2024-01-06 09:00:00.000000', '1989-11-30', 'charlie.staff@evr.com', 'charlie_license.jpg', 'Charlie Staff', '$2a$10$dummyhash', 'charlie_id.jpg', '0900000006', 'STAFF', 'ACTIVE', 3),
(7, '987 Staff Dr', '2024-01-07 09:00:00.000000', '1993-12-05', 'diana.staff@evr.com', 'diana_license.jpg', 'Diana Staff', '$2a$10$dummyhash', 'diana_id.jpg', '0900000007', 'STAFF', 'ACTIVE', 4),
(8, '111 Customer St', '2024-01-10 10:00:00.000000', '1995-02-14', 'alice.customer@email.com', 'alice_cust_license.jpg', 'Alice Customer', '$2a$10$dummyhash', 'alice_cust_id.jpg', '0911111111', 'CUSTOMER', 'ACTIVE', NULL),
(9, '222 Customer Ave', '2024-01-11 10:00:00.000000', '1996-04-20', 'bob.customer@email.com', 'bob_cust_license.jpg', 'Bob Customer', '$2a$10$dummyhash', 'bob_cust_id.jpg', '0922222222', 'CUSTOMER', 'ACTIVE', NULL),
(10, '333 Customer Rd', '2024-01-12 10:00:00.000000', '1997-06-15', 'carol.customer@email.com', 'carol_cust_license.jpg', 'Carol Customer', '$2a$10$dummyhash', 'carol_cust_id.jpg', '0933333333', 'CUSTOMER', 'ACTIVE', NULL),
(11, '444 Customer Blvd', '2024-01-13 10:00:00.000000', '1994-08-22', 'david.customer@email.com', 'david_cust_license.jpg', 'David Customer', '$2a$10$dummyhash', 'david_cust_id.jpg', '0944444444', 'CUSTOMER', 'ACTIVE', NULL),
(12, '555 Customer Ln', '2024-01-14 10:00:00.000000', '1998-10-18', 'eve.customer@email.com', 'eve_cust_license.jpg', 'Eve Customer', '$2a$10$dummyhash', 'eve_cust_id.jpg', '0955555555', 'CUSTOMER', 'ACTIVE', NULL),
(13, '666 Customer Dr', '2024-01-15 10:00:00.000000', '1993-03-25', 'frank.customer@email.com', 'frank_cust_license.jpg', 'Frank Customer', '$2a$10$dummyhash', 'frank_cust_id.jpg', '0966666666', 'CUSTOMER', 'SUSPENDED', NULL);

INSERT INTO Station (name, address, contactNumber, totalSlots, AvailableSlots, operatingHours, latitude, longitude)
VALUES
  ('Central Station', '123 Main St', '0123456789', 50, 40, '08:00-22:00', 10.775, 106.700),
  ('East Station', '456 East Rd', '0987654321', 30, 25, '08:00-22:00', 10.780, 106.710);

-- Seed Models
INSERT INTO Model (brand, modelName, vehicleType, basePrice, features)
VALUES
  ('Tesla', 'Model 3', 'Car', 1000000, 'Autopilot;Fast Charge'),
  ('Yadea', 'G5', 'Bike', 150000, 'Eco Mode;Removable Battery');


-- Seed Vehicles (requires existing StationID, ModelID)
INSERT INTO Vehicle (ModelID, StationID, plateNumber, batteryLevel, mileage, status, lastMaintenanceDate)
VALUES
  (1, 1, 'EV-001', 90, 1200, 'AVAILABLE', '2025-01-05'),
  (2, 2, 'EB-101', 75, 500, 'AVAILABLE', '2025-02-10');

-- Optional: a sample Booking referencing the above user/vehicle/station/staff
-- Note: using CURRENT_TIMESTAMP for dates
INSERT INTO Booking (UserID, VehicleID, StationID, StaffID, startTime, endTime, totalPrice, BookingStatus)
VALUES
  (1, 1, 1, 2, CURRENT_TIMESTAMP, DATEADD(HOUR, 2, CURRENT_TIMESTAMP), 200000, 'PENDING');

-- Contracts for bookings
INSERT INTO Contract (BookingID, renterSignature, staffSignature, signedAt, status)
VALUES
  (1, 'Alice Sig', 'Bob Sig', CURRENT_TIMESTAMP, 'ACTIVE');

-- Deposits for bookings
INSERT INTO Deposit (BookingID, amount, status, createdAt)
VALUES
  (1, 100000, 'HELD', CURRENT_TIMESTAMP);

-- Payments for bookings
INSERT INTO Payment (BookingID, method, amount, paymentDate, status)
VALUES
  (1, 'CARD', 200000, CURRENT_TIMESTAMP, 'SUCCESS');

-- Complaints linked to contract/user/staff
INSERT INTO Complaint (ContractID, UserID, StaffID, issueDescription, createdAt, status)
VALUES
  (1, 1, 2, 'Vehicle had minor scratches', CURRENT_TIMESTAMP, 'PENDING');

-- Feedback linked to contract/user
INSERT INTO Feedback (ContractID, UserID, category, stars, comment, createdAt)
VALUES
  (1, 1, 'SERVICE', 5, 'Great service and smooth process', CURRENT_TIMESTAMP);

-- Issue Reports for a vehicle at station by a user
INSERT INTO IssueReport (VehicleID, UserID, StationID, issueCategory, priority, description, photos, status, reportedAt)
VALUES
  (1, 1, 1, 'MECHANICAL', 'HIGH', 'Strange noise from motor', '["photo1.jpg","photo2.jpg"]', 'OPEN', CURRENT_TIMESTAMP);

-- Maintenance records for a vehicle
INSERT INTO Maintenance (VehicleID, StationID, StaffID, issueDescription, status, scheduledAt, closedAt, remarks)
VALUES
  (1, 1, 2, 'Motor noise inspection', 'IN_PROGRESS', DATEADD(DAY, 1, CURRENT_TIMESTAMP), NULL, 'Awaiting parts');

-- Risk flags for a user
INSERT INTO RiskFlag (UserID, FlaggedBy, reason, riskScore, status, flaggedAt)
VALUES
  (1, 3, 'Late return on previous booking', 7, 'Active', CURRENT_TIMESTAMP);

-- Staff schedules
INSERT INTO StaffSchedule (StaffID, StationID, shiftStart, shiftEnd, shiftType, status, notes)
VALUES
(2, 1, DATEADD(HOUR, 1, CURRENT_TIMESTAMP), DATEADD(HOUR, 9, CURRENT_TIMESTAMP), 'Morning', 'Scheduled', 'Opening shift');

-- Vehicle condition reports
INSERT INTO VehicleConditionReport (ContractID, VehicleID, StaffID, reportTime, battery, damageDescription, photos, ReportType)
VALUES
  (1, 1, 2, CURRENT_TIMESTAMP, 88, 'Minor scratch on left door', '["vcr1.jpg"]', 'PRE_RENTAL');

-- Thêm seed Stations
INSERT INTO Station (name, address, contactNumber, totalSlots, AvailableSlots, operatingHours, latitude, longitude)
VALUES
  ('West Station', '789 West Blvd', '0345566778', 40, 32, '07:00-20:00', 10.785, 106.720),
  ('South Station', '222 South Rd', '0998833777', 20, 15, '09:00-22:00', 10.765, 106.690);

-- Thêm seed Models
INSERT INTO Model (brand, modelName, vehicleType, basePrice, features)
VALUES
  ('VinFast', 'VF e34', 'Car', 800000, 'Vietnamese EV;Eco'),
  ('Honda', 'PCX Electric', 'Bike', 120000, 'City mode;Removable Battery');

-- Thêm seed Vehicles
INSERT INTO Vehicle (ModelID, StationID, plateNumber, batteryLevel, mileage, status, lastMaintenanceDate)
VALUES
  (3, 3, 'EV-002', 80, 450, 'AVAILABLE', '2025-02-15'),
  (4, 4, 'EB-102', 60, 200, 'AVAILABLE', '2025-03-05');

-- Thêm seed Bookings
INSERT INTO Booking (UserID, VehicleID, StationID, StaffID, startTime, endTime, totalPrice, BookingStatus)
VALUES
  (2, 3, 3, 4, CURRENT_TIMESTAMP, DATEADD(HOUR, 5, CURRENT_TIMESTAMP), 375000, 'CONFIRMED'),
  (3, 2, 2, 5, CURRENT_TIMESTAMP, DATEADD(HOUR, 6, CURRENT_TIMESTAMP), 400000, 'PENDING');

-- Thêm seed Contracts
INSERT INTO Contract (BookingID, renterSignature, staffSignature, signedAt, status)
VALUES
  (2, 'Bob Sig', 'Eve Sig', CURRENT_TIMESTAMP, 'ACTIVE'),
  (3, 'Carol Sig', 'John Sig', CURRENT_TIMESTAMP, 'ACTIVE');

-- Thêm seed Deposits
INSERT INTO Deposit (BookingID, amount, status, createdAt)
VALUES
  (2, 150000, 'HELD', CURRENT_TIMESTAMP),
  (3, 200000, 'HELD', CURRENT_TIMESTAMP);

-- Thêm seed Payments
INSERT INTO Payment (BookingID, method, amount, paymentDate, status)
VALUES
  (2, 'MOMO', 375000, CURRENT_TIMESTAMP, 'SUCCESS'),
  (3, 'CASH', 400000, CURRENT_TIMESTAMP, 'FAILED');

-- Thêm seed Complaints
INSERT INTO Complaint (ContractID, UserID, StaffID, issueDescription, createdAt, status)
VALUES
  (2, 2, 4, 'Delay on vehicle delivery', CURRENT_TIMESTAMP, 'PENDING'),
  (3, 3, 5, 'Dirty vehicle interior', CURRENT_TIMESTAMP, 'RESOLVED');

-- Thêm seed Feedback
INSERT INTO Feedback (ContractID, UserID, category, stars, comment, createdAt)
VALUES
  (2, 2, 'CLEANLINESS', 3, 'Vehicle could be cleaner.', CURRENT_TIMESTAMP),
  (3, 3, 'MAINTENANCE', 4, 'Maintenance was prompt!', CURRENT_TIMESTAMP);

-- Thêm seed Issue Reports
INSERT INTO IssueReport (VehicleID, UserID, StationID, issueCategory, priority, description, photos, status, reportedAt)
VALUES
  (2, 2, 2, 'BATTERY', 'MEDIUM', 'Battery drains quickly', '["photo3.jpg"]', 'OPEN', CURRENT_TIMESTAMP),
(4, 3, 4, 'BREAK', 'LOW', 'Soft brake pedal', '[]', 'OPEN', CURRENT_TIMESTAMP);

-- Thêm seed Maintenance records
INSERT INTO Maintenance (VehicleID, StationID, StaffID, issueDescription, status, scheduledAt, closedAt, remarks)
VALUES
  (2, 2, 5, 'Battery health check', 'IN_PROGRESS', DATEADD(DAY, 2, CURRENT_TIMESTAMP), NULL, 'Parts ordered'),
  (4, 4, 6, 'Check brakes', 'COMPLETED', DATEADD(DAY, -3, CURRENT_TIMESTAMP), DATEADD(DAY, -2, CURRENT_TIMESTAMP), 'Fixed brake');