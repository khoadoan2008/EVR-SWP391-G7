IF DB_ID('demoDB4SWP') IS NOT NULL
BEGIN
    ALTER DATABASE demoDB4SWP SET SINGLE_USER WITH ROLLBACK IMMEDIATE;
    DROP DATABASE demoDB4SWP;
END
GO

CREATE DATABASE demoDB4SWP;
GO

USE demoDB4SWP;
GO
------------------------------------------------------
-- USERS (Customer / Staff / Admin)
------------------------------------------------------
CREATE TABLE Users (
    UserID INT IDENTITY(1,1) PRIMARY KEY,
    Name NVARCHAR(100) NOT NULL,
    DateOfBirth DATE NULL,
    Email NVARCHAR(100) UNIQUE NOT NULL,
    Phone NVARCHAR(20) UNIQUE NOT NULL,
    PasswordHash NVARCHAR(255) NOT NULL,
    Address NVARCHAR(255) NULL,
    PersonalIDImage NVARCHAR(255) NULL,
    LicenseImage NVARCHAR(255) NULL,
    CreatedAt DATETIME DEFAULT GETDATE(),
    Role NVARCHAR(20) CHECK (Role IN ('Customer','Staff','Admin')) NOT NULL,
    Status NVARCHAR(20) CHECK (Status IN ('Active','Suspended','Deleted')) NOT NULL DEFAULT 'Active'
);

------------------------------------------------------
-- STATION
------------------------------------------------------
CREATE TABLE Station (
    StationID INT IDENTITY(1,1) PRIMARY KEY,
    Name NVARCHAR(100) NOT NULL,
    Address NVARCHAR(255) NOT NULL,
    ContactNumber NVARCHAR(20) NULL,
    TotalSlots INT NOT NULL,
    AvailableSlots INT NOT NULL,
    OperatingHours NVARCHAR(50) NULL
);

------------------------------------------------------
-- MODEL (thay Category)
------------------------------------------------------
CREATE TABLE Model (
    ModelID INT IDENTITY(1,1) PRIMARY KEY,
    Brand NVARCHAR(50) NOT NULL,
    ModelName NVARCHAR(50) NOT NULL,
    VehicleType NVARCHAR(30) NOT NULL,  -- Sedan, SUV, EV...
    BasePrice DECIMAL(10,2) NOT NULL,
    Features NVARCHAR(255) NULL
);

------------------------------------------------------
-- VEHICLE
------------------------------------------------------
CREATE TABLE Vehicle (
    VehicleID INT IDENTITY(1,1) PRIMARY KEY,
    ModelID INT NOT NULL,
    StationID INT NOT NULL,
    PlateNumber NVARCHAR(20) UNIQUE NOT NULL,
    BatteryLevel DECIMAL(5,2) NULL,
    Mileage DECIMAL(10,2) NULL,
    Status NVARCHAR(20) CHECK (Status IN ('Available','Rented','Maintenance')) NOT NULL DEFAULT 'Available',
    LastMaintenanceDate DATE NULL,
    CONSTRAINT FK_Vehicle_Model FOREIGN KEY (ModelID) REFERENCES Model(ModelID),
    CONSTRAINT FK_Vehicle_Station FOREIGN KEY (StationID) REFERENCES Station(StationID)
);

------------------------------------------------------
-- BOOKING
------------------------------------------------------
CREATE TABLE Booking (
    BookingID INT IDENTITY(1,1) PRIMARY KEY,
    UserID INT NOT NULL,
    VehicleID INT NOT NULL,
    StationID INT NOT NULL,
    StaffID INT NULL,
    StartTime DATETIME NOT NULL,
    EndTime DATETIME NOT NULL,
    TotalPrice DECIMAL(10,2) NOT NULL,
    BookingStatus NVARCHAR(20) CHECK (BookingStatus IN ('Pending','Confirmed','Cancelled','Completed')) NOT NULL DEFAULT 'Pending',
    CONSTRAINT FK_Booking_User FOREIGN KEY (UserID) REFERENCES Users(UserID),
    CONSTRAINT FK_Booking_Staff FOREIGN KEY (StaffID) REFERENCES Users(UserID),
    CONSTRAINT FK_Booking_Vehicle FOREIGN KEY (VehicleID) REFERENCES Vehicle(VehicleID),
    CONSTRAINT FK_Booking_Station FOREIGN KEY (StationID) REFERENCES Station(StationID)
);

------------------------------------------------------
-- DEPOSIT
------------------------------------------------------
CREATE TABLE Deposit (
    DepositID INT IDENTITY(1,1) PRIMARY KEY,
    BookingID INT NOT NULL,
    Amount DECIMAL(10,2) NOT NULL,
    Status NVARCHAR(20) CHECK (Status IN ('Held','Refunded','Forfeited')) NOT NULL DEFAULT 'Held',
    CreatedAt DATETIME DEFAULT GETDATE(),
    CONSTRAINT FK_Deposit_Booking FOREIGN KEY (BookingID) REFERENCES Booking(BookingID)
);

------------------------------------------------------
-- CONTRACT
------------------------------------------------------
CREATE TABLE Contract (
    ContractID INT IDENTITY(1,1) PRIMARY KEY,
    BookingID INT NOT NULL,
    RenterSignature NVARCHAR(255) NULL,
    StaffSignature NVARCHAR(255) NULL,
    SignedAt DATETIME DEFAULT GETDATE(),
    Status NVARCHAR(20) CHECK (Status IN ('Active','Completed','Violated')) NOT NULL DEFAULT 'Active',
    CONSTRAINT FK_Contract_Booking FOREIGN KEY (BookingID) REFERENCES Booking(BookingID)
);

------------------------------------------------------
-- VEHICLE CONDITION REPORT (trước & sau khi thuê)
------------------------------------------------------
CREATE TABLE VehicleConditionReport (
    ReportID INT IDENTITY(1,1) PRIMARY KEY,
    ContractID INT NOT NULL,
    VehicleID INT NOT NULL,
    StaffID INT NOT NULL,
    ReportTime DATETIME DEFAULT GETDATE(),
    Battery DECIMAL(5,2) NULL,
    DamageDescription NVARCHAR(255) NULL,
    Photos NVARCHAR(255) NULL,
    ReportType NVARCHAR(20) CHECK (ReportType IN ('PreRental','PostRental')) NOT NULL,
    CONSTRAINT FK_Report_Contract FOREIGN KEY (ContractID) REFERENCES Contract(ContractID),
    CONSTRAINT FK_Report_Vehicle FOREIGN KEY (VehicleID) REFERENCES Vehicle(VehicleID),
    CONSTRAINT FK_Report_Staff FOREIGN KEY (StaffID) REFERENCES Users(UserID)
);

------------------------------------------------------
-- FEEDBACK / RATING
------------------------------------------------------
CREATE TABLE Feedback (
    FeedbackID INT IDENTITY(1,1) PRIMARY KEY,
    ContractID INT NOT NULL,
    UserID INT NOT NULL,
    Category NVARCHAR(20) CHECK (Category IN ('Vehicle','Service')) NOT NULL,
    Stars INT CHECK (Stars BETWEEN 1 AND 5),
    Comment NVARCHAR(255) NULL,
    CreatedAt DATETIME DEFAULT GETDATE(),
    CONSTRAINT FK_Feedback_Contract FOREIGN KEY (ContractID) REFERENCES Contract(ContractID),
    CONSTRAINT FK_Feedback_User FOREIGN KEY (UserID) REFERENCES Users(UserID)
);

------------------------------------------------------
-- COMPLAINT RECORD
------------------------------------------------------
CREATE TABLE Complaint (
    ComplaintID INT IDENTITY(1,1) PRIMARY KEY,
    ContractID INT NOT NULL,
    UserID INT NOT NULL,
    StaffID INT NULL,
    IssueDescription NVARCHAR(255) NOT NULL,
    CreatedAt DATETIME DEFAULT GETDATE(),
    Status NVARCHAR(20) CHECK (Status IN ('Pending','Resolved','Rejected')) NOT NULL DEFAULT 'Pending',
    CONSTRAINT FK_Complaint_Contract FOREIGN KEY (ContractID) REFERENCES Contract(ContractID),
    CONSTRAINT FK_Complaint_User FOREIGN KEY (UserID) REFERENCES Users(UserID),
    CONSTRAINT FK_Complaint_Staff FOREIGN KEY (StaffID) REFERENCES Users(UserID)
);

------------------------------------------------------
-- PAYMENT
------------------------------------------------------
CREATE TABLE Payment (
    PaymentID INT IDENTITY(1,1) PRIMARY KEY,
    BookingID INT NOT NULL,
    Method NVARCHAR(20) CHECK (Method IN ('Card','E-wallet','Cash')) NOT NULL,
    Amount DECIMAL(10,2) NOT NULL,
    PaymentDate DATETIME DEFAULT GETDATE(),
    Status NVARCHAR(20) CHECK (Status IN ('Pending','Success','Failed','Refunded')) NOT NULL,
    CONSTRAINT FK_Payment_Booking FOREIGN KEY (BookingID) REFERENCES Booking(BookingID)
);

------------------------------------------------------
-- AUDIT LOG
------------------------------------------------------
CREATE TABLE AuditLog (
    LogID INT IDENTITY(1,1) PRIMARY KEY,
    UserID INT NOT NULL,
    Action NVARCHAR(255) NOT NULL,
    Timestamp DATETIME DEFAULT GETDATE(),
    CONSTRAINT FK_AuditLog_User FOREIGN KEY (UserID) REFERENCES Users(UserID)
);
