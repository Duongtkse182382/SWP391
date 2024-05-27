CREATE TABLE Material (
    MaterialID int PRIMARY KEY,
    Name nvarchar(255)
);

CREATE TABLE MaterialPriceList (
	MPid int PRIMARY KEY,
	MaterialID int,
	BuyPrice money,
	SellPrice money,
	ApplyDate datetime
);

CREATE TABLE ProductMaterial (
    ProductMaterialID int PRIMARY KEY,
    ProductID int,
    MaterialID int,
    Weight float
);

CREATE TABLE Type (
    TypeID int PRIMARY KEY,
    TypeName nvarchar(255),
    WarantyTime int,
    isActive int
);

CREATE TABLE Category (
    CategoryID int PRIMARY KEY,
    CatName nvarchar(255),
    isActive int
);

CREATE TABLE Gem (
    GemID int PRIMARY KEY,
    ProductID int,
    GemCode varchar(255),
    GemName nvarchar(255),
    Origin varchar(255),
    Carat float,
    Color varchar(255),
    Clarity varchar(255),
    Cut varchar(255)
);

CREATE TABLE GemPriceList (
    GemPriceListID int PRIMARY KEY,
    GemID int,
    Origin varchar(255),
    Carat float,
    Color varchar(255),
    Clarity varchar(255),
    Cut varchar(255),
    BuyPrice money,
    SellPrice money,
    ApplyDate datetime
);

CREATE TABLE Product (
    ProductID int PRIMARY KEY,
    ProductName nvarchar(255),
    ProductCode varchar(255),
    GemCost money,
    CounterID int,
    CategoryID int,
    TypeID int,
    ProductionCost money,
	Quantity int DEFAULT 1,
    Status int,
    PriceRate float
);

CREATE TABLE Staff (
    StaffID int PRIMARY KEY,
    FullName nvarchar(255),
    Email varchar(255) UNIQUE,
    RoleID int,
    Password varchar(255),
    DateOfBirth date,
    Gender char(1),
    Address nvarchar(255),
    PhoneNumber int UNIQUE,
    StaffCode varchar(255),
    Counterid int,
    isActive int
);

CREATE TABLE Role (
    RoleID int PRIMARY KEY,
    RoleName nvarchar(255),
    isActive int
);

CREATE TABLE Counter (
    CounterID int PRIMARY KEY,
    CounterName nvarchar(255),
    isActive int
);

CREATE TABLE [Order] (
    OrderID int PRIMARY KEY,
    OrderCode varchar(255),
    StatusID int,
    DetailID int
);

CREATE TABLE Status (
    StatusID int PRIMARY KEY,
    Name nvarchar(255)
);

CREATE TABLE OrderDetail (
    DetailID int PRIMARY KEY,
    StaffID int,
    ProductID int,
    CustomerID int,
    PromotionID int,
	Quantity int,
    Date date,
    Total money
);

CREATE TABLE Customer (
    CustomerID int PRIMARY KEY,
    CustomerName nvarchar(255),
    Phone int UNIQUE,
    LoyalCode varchar(255),
    LoyalPoint int,
    Rank nvarchar(255)
);

CREATE TABLE Promotion (
    PromotionID int PRIMARY KEY,
    PromotionCode varchar(255),
    PromotionName nvarchar(255),
    DiscountRate float,
    StartDate date,
    EndDate date,
    ApplyFor varchar(255)
);

CREATE TABLE PurchaseOrder (
    PurchaseID int PRIMARY KEY,
    StaffID int,
    CustomerID int,
    Code varchar(255),
    Date date,
    PromotionID int,
    StatusID int,
    Total money
);

CREATE TABLE PurchaseDetailID (
    PDetailID int PRIMARY KEY,
    PurchaseID int,
    Name nvarchar(255),
    ProductID int,
    MaterialID int,
    Weight float,
    Origin varchar(255),
    Carat float,
    Color varchar(255),
    Clarity varchar(255),
    Cut varchar(255)
);
