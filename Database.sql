CREATE TABLE Material (
    MaterialID int PRIMARY KEY identity,
    Name nvarchar(255) NOT NULL
);

CREATE TABLE MaterialPriceList (
	MPid int PRIMARY KEY identity,
	MaterialID int NOT NULL,
	BuyPrice money NOT NULL,
	SellPrice money NOT NULL,
	ApplyDate datetime
);

CREATE TABLE ProductMaterial (
    ProductMaterialID int PRIMARY KEY identity,
    ProductID int NOT NULL,
    MaterialID int NOT NULL,
    Weight float NOT NULL
);

CREATE TABLE Type (
    TypeID int PRIMARY KEY identity,
    TypeName nvarchar(255) NOT NULL,
    WarantyTime int,
    isActive bit
);

CREATE TABLE Category (
    CategoryID int PRIMARY KEY identity,
    CatName nvarchar(255) NOT NULL,
    isActive bit
);

CREATE TABLE Gem (
    GemID int PRIMARY KEY identity,
    ProductID int,
    GemCode varchar(255) NOT NULL,
    GemName nvarchar(255),
    Origin varchar(255),
    Carat float,
    Color varchar(255),
    Clarity varchar(255),
    Cut varchar(255)
);

CREATE TABLE GemPriceList (
    GemPriceListID int PRIMARY KEY identity,
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
    ProductID int PRIMARY KEY identity,
    ProductName nvarchar(255) NOT NULL,
    ProductCode varchar(255) NOT NULL,
    GemCost money,
    CounterID int,
    CategoryID int,
    TypeID int,
    ProductionCost money,
    Quantity int DEFAULT 1,
    Status bit,
    PriceRate float,
    Image varbinary(MAX)
);

CREATE TABLE Staff (
    StaffID int PRIMARY KEY identity,
    FullName nvarchar(255),
    Email varchar(255) UNIQUE NOT NULL,
    RoleID int NOT NULL,
    Password varchar(32) UNIQUE NOT NULL,
    DateOfBirth date,
    Gender char(1),
    Address nvarchar(255),
    PhoneNumber int UNIQUE NOT NULL,
    StaffCode varchar(255),
    CounterID int,
    isActive bit
);

CREATE TABLE Role (
    RoleID int PRIMARY KEY identity,
    RoleName nvarchar(255) NOT NULL,
    isActive bit
);

CREATE TABLE Counter (
    CounterID int PRIMARY KEY identity,
    CounterName nvarchar(255),
    CounterType nvarchar(255),
    isActive int
);

CREATE TABLE [Order] (
    OrderID int PRIMARY KEY identity,
    OrderCode varchar(255) NOT NULL,
    StatusID int,
    DetailID int
);

CREATE TABLE Status (
    StatusID int PRIMARY KEY identity,
    Name nvarchar(255) NOT NULL
);

CREATE TABLE OrderDetail (
    DetailID int PRIMARY KEY identity,
    StaffID int,
    ProductID int,
    CustomerID int,
    PromotionID int,
    Quantity int,
    Date date,
    Total money
);

CREATE TABLE Customer (
    CustomerID int PRIMARY KEY identity,
    CustomerName nvarchar(255),
    Phone int UNIQUE NOT NULL,
    LoyalCode varchar(255),
    LoyalPoint int,
    Rank nvarchar(255)
);

CREATE TABLE Promotion (
    PromotionID int PRIMARY KEY identity,
    PromotionCode varchar(255) NOT NULL,
    PromotionName nvarchar(255),
    DiscountRate float,
    StartDate date,
    EndDate date,
    ApplyFor varchar(255)
);

CREATE TABLE PurchaseOrder (
    PurchaseID int PRIMARY KEY identity,
    StaffID int,
    CustomerID int,
    Code varchar(255) NOT NULL,
    Date date,
    PromotionID int,
    StatusID int,
    Total money
);

CREATE TABLE PurchaseDetails (
    PDetailID int PRIMARY KEY identity,
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

--REFERENCES
-- MaterialPriceList references Material
ALTER TABLE MaterialPriceList
ADD FOREIGN KEY (MaterialID) REFERENCES Material(MaterialID);

-- ProductMaterial references Product and Material
ALTER TABLE ProductMaterial
ADD FOREIGN KEY (ProductID) REFERENCES Product(ProductID),
    FOREIGN KEY (MaterialID) REFERENCES Material(MaterialID);

-- Gem references Product
ALTER TABLE Gem
ADD FOREIGN KEY (ProductID) REFERENCES Product(ProductID);

-- GemPriceList references Gem
ALTER TABLE GemPriceList
ADD FOREIGN KEY (GemID) REFERENCES Gem(GemID);

-- Product references Category and Type
ALTER TABLE Product
ADD FOREIGN KEY (CategoryID) REFERENCES Category(CategoryID),
    FOREIGN KEY (TypeID) REFERENCES Type(TypeID);

-- Staff references Role and Counter
ALTER TABLE Staff
ADD FOREIGN KEY (RoleID) REFERENCES Role(RoleID),
    FOREIGN KEY (CounterID) REFERENCES Counter(CounterID);

-- Order references Status and OrderDetail
ALTER TABLE [Order]
ADD FOREIGN KEY (StatusID) REFERENCES Status(StatusID),
    FOREIGN KEY (DetailID) REFERENCES OrderDetail(DetailID);

-- OrderDetail references Staff, Product, Customer, and Promotion
ALTER TABLE OrderDetail
ADD FOREIGN KEY (StaffID) REFERENCES Staff(StaffID),
    FOREIGN KEY (ProductID) REFERENCES Product(ProductID),
    FOREIGN KEY (CustomerID) REFERENCES Customer(CustomerID),
    FOREIGN KEY (PromotionID) REFERENCES Promotion(PromotionID);

-- PurchaseOrder references
ALTER TABLE PurchaseOrder
ADD FOREIGN KEY (StaffID) REFERENCES Staff(StaffID),
    FOREIGN KEY (CustomerID) REFERENCES Customer(CustomerID),
    FOREIGN KEY (PromotionID) REFERENCES Promotion(PromotionID),
    FOREIGN KEY (StatusID) REFERENCES Status(StatusID);

-- PurchaseDetails references
ALTER TABLE PurchaseDetails
ADD FOREIGN KEY (PurchaseID) REFERENCES PurchaseOrder(PurchaseID),
    FOREIGN KEY (ProductID) REFERENCES Product(ProductID),
    FOREIGN KEY (MaterialID) REFERENCES Material(MaterialID);
