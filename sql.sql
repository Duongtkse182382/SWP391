USE [master]
GO
/****** Object:  Database [fullstackapp]    Script Date: 6/13/2024 8:11:29 AM ******/
CREATE DATABASE [fullstackapp]
 CONTAINMENT = NONE
 ON  PRIMARY 
( NAME = N'fullstackapp', FILENAME = N'C:\Program Files\Microsoft SQL Server\MSSQL16.MSSQLSERVER11\MSSQL\DATA\fullstackapp.mdf' , SIZE = 8192KB , MAXSIZE = UNLIMITED, FILEGROWTH = 65536KB )
 LOG ON 
( NAME = N'fullstackapp_log', FILENAME = N'C:\Program Files\Microsoft SQL Server\MSSQL16.MSSQLSERVER11\MSSQL\DATA\fullstackapp_log.ldf' , SIZE = 8192KB , MAXSIZE = 2048GB , FILEGROWTH = 65536KB )
 WITH CATALOG_COLLATION = DATABASE_DEFAULT, LEDGER = OFF
GO
ALTER DATABASE [fullstackapp] SET COMPATIBILITY_LEVEL = 160
GO
IF (1 = FULLTEXTSERVICEPROPERTY('IsFullTextInstalled'))
begin
EXEC [fullstackapp].[dbo].[sp_fulltext_database] @action = 'enable'
end
GO
ALTER DATABASE [fullstackapp] SET ANSI_NULL_DEFAULT OFF 
GO
ALTER DATABASE [fullstackapp] SET ANSI_NULLS OFF 
GO
ALTER DATABASE [fullstackapp] SET ANSI_PADDING OFF 
GO
ALTER DATABASE [fullstackapp] SET ANSI_WARNINGS OFF 
GO
ALTER DATABASE [fullstackapp] SET ARITHABORT OFF 
GO
ALTER DATABASE [fullstackapp] SET AUTO_CLOSE OFF 
GO
ALTER DATABASE [fullstackapp] SET AUTO_SHRINK OFF 
GO
ALTER DATABASE [fullstackapp] SET AUTO_UPDATE_STATISTICS ON 
GO
ALTER DATABASE [fullstackapp] SET CURSOR_CLOSE_ON_COMMIT OFF 
GO
ALTER DATABASE [fullstackapp] SET CURSOR_DEFAULT  GLOBAL 
GO
ALTER DATABASE [fullstackapp] SET CONCAT_NULL_YIELDS_NULL OFF 
GO
ALTER DATABASE [fullstackapp] SET NUMERIC_ROUNDABORT OFF 
GO
ALTER DATABASE [fullstackapp] SET QUOTED_IDENTIFIER OFF 
GO
ALTER DATABASE [fullstackapp] SET RECURSIVE_TRIGGERS OFF 
GO
ALTER DATABASE [fullstackapp] SET  DISABLE_BROKER 
GO
ALTER DATABASE [fullstackapp] SET AUTO_UPDATE_STATISTICS_ASYNC OFF 
GO
ALTER DATABASE [fullstackapp] SET DATE_CORRELATION_OPTIMIZATION OFF 
GO
ALTER DATABASE [fullstackapp] SET TRUSTWORTHY OFF 
GO
ALTER DATABASE [fullstackapp] SET ALLOW_SNAPSHOT_ISOLATION OFF 
GO
ALTER DATABASE [fullstackapp] SET PARAMETERIZATION SIMPLE 
GO
ALTER DATABASE [fullstackapp] SET READ_COMMITTED_SNAPSHOT OFF 
GO
ALTER DATABASE [fullstackapp] SET HONOR_BROKER_PRIORITY OFF 
GO
ALTER DATABASE [fullstackapp] SET RECOVERY FULL 
GO
ALTER DATABASE [fullstackapp] SET  MULTI_USER 
GO
ALTER DATABASE [fullstackapp] SET PAGE_VERIFY CHECKSUM  
GO
ALTER DATABASE [fullstackapp] SET DB_CHAINING OFF 
GO
ALTER DATABASE [fullstackapp] SET FILESTREAM( NON_TRANSACTED_ACCESS = OFF ) 
GO
ALTER DATABASE [fullstackapp] SET TARGET_RECOVERY_TIME = 60 SECONDS 
GO
ALTER DATABASE [fullstackapp] SET DELAYED_DURABILITY = DISABLED 
GO
ALTER DATABASE [fullstackapp] SET ACCELERATED_DATABASE_RECOVERY = OFF  
GO
EXEC sys.sp_db_vardecimal_storage_format N'fullstackapp', N'ON'
GO
ALTER DATABASE [fullstackapp] SET QUERY_STORE = ON
GO
ALTER DATABASE [fullstackapp] SET QUERY_STORE (OPERATION_MODE = READ_WRITE, CLEANUP_POLICY = (STALE_QUERY_THRESHOLD_DAYS = 30), DATA_FLUSH_INTERVAL_SECONDS = 900, INTERVAL_LENGTH_MINUTES = 60, MAX_STORAGE_SIZE_MB = 1000, QUERY_CAPTURE_MODE = AUTO, SIZE_BASED_CLEANUP_MODE = AUTO, MAX_PLANS_PER_QUERY = 200, WAIT_STATS_CAPTURE_MODE = ON)
GO
USE [fullstackapp]
GO
/****** Object:  Table [dbo].[category]    Script Date: 6/13/2024 8:11:29 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[category](
	[categoryid] [int] IDENTITY(1,1) NOT NULL,
	[cat_name] [varchar](255) NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[categoryid] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[counter]    Script Date: 6/13/2024 8:11:30 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[counter](
	[counterid] [int] IDENTITY(1,1) NOT NULL,
	[counter_name] [varchar](255) NULL,
PRIMARY KEY CLUSTERED 
(
	[counterid] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[customer]    Script Date: 6/13/2024 8:11:30 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[customer](
	[customerid] [int] IDENTITY(1,1) NOT NULL,
	[customer_name] [varchar](255) NULL,
	[loyal_point] [int] NOT NULL,
	[phone] [varchar](255) NULL,
	[rank] [varchar](255) NULL,
PRIMARY KEY CLUSTERED 
(
	[customerid] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[gem]    Script Date: 6/13/2024 8:11:30 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[gem](
	[gemid] [int] IDENTITY(1,1) NOT NULL,
	[carat] [real] NOT NULL,
	[clarity] [varchar](255) NULL,
	[color] [varchar](255) NULL,
	[cut] [varchar](255) NULL,
	[gem_code] [varchar](255) NOT NULL,
	[gem_name] [varchar](255) NULL,
	[origin] [varchar](255) NULL,
PRIMARY KEY CLUSTERED 
(
	[gemid] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[gem_price_list]    Script Date: 6/13/2024 8:11:30 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[gem_price_list](
	[gem_price_listid] [int] IDENTITY(1,1) NOT NULL,
	[apply_date] [datetime2](6) NULL,
	[buy_price] [real] NOT NULL,
	[gemid] [int] NULL,
	[sell_price] [real] NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[gem_price_listid] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[material]    Script Date: 6/13/2024 8:11:30 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[material](
	[materialid] [int] IDENTITY(1,1) NOT NULL,
	[name] [varchar](255) NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[materialid] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[material_price_list]    Script Date: 6/13/2024 8:11:30 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[material_price_list](
	[material_price_listid] [int] IDENTITY(1,1) NOT NULL,
	[apply_date] [datetime2](6) NULL,
	[buy_price] [real] NOT NULL,
	[materialid] [int] NULL,
	[sell_price] [real] NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[material_price_listid] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[order]    Script Date: 6/13/2024 8:11:30 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[order](
	[orderid] [int] IDENTITY(1,1) NOT NULL,
	[order_code] [varchar](255),
	[orderstatusid] [int] NULL,
	[customerid] [int] NULL,
	[date] [datetime2](6) NULL,
	[promotion_percent] [float] NULL,
	[quantity] [int] NOT NULL,
	[staffid] [int] NULL,
	[total] [float] NULL,
	[type] [bit] NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[orderid] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[order_detail]    Script Date: 6/13/2024 8:11:30 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[order_detail](
	[detailid] [int] IDENTITY(1,1) NOT NULL,
	[note] [varchar](255) NULL,
	[orderid] [int] NULL,
	[productid] [int] NOT NULL,
	[total] [real] NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[detailid] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[order_status]    Script Date: 6/13/2024 8:11:30 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[order_status](
	[orderstatusid] [int] IDENTITY(1,1) NOT NULL,
	[name] [varchar](255) NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[orderstatusid] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[purchase_detail] Script Date: 6/13/2024 8:11:30 AM ******/
CREATE TABLE [dbo].[purchase_detail](
    [purchaseid] [int] IDENTITY(1,1) NOT NULL,
    [orderid] [int] NOT NULL,
    [gemid] [int] NOT NULL,
    [gem_price] [real] NULL,
    [product_name] [varchar](255) NULL,
    [materialid] [int] NOT NULL,
    [weight] [real] NULL,
    [material_price] [real] NULL,
PRIMARY KEY CLUSTERED 
(
    [purchaseid] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[product]    Script Date: 6/13/2024 8:11:30 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[product](
	[productid] [int] IDENTITY(1,1) NOT NULL,
	[categoryid] [int] NULL,
	[gem_price_listid] [int] NULL,
	[image] [varchar](255) NULL,
	[is_active] [bit] NOT NULL,
	[material_price_listid] [int] NULL,
	[order_type] [varchar](255) NULL,
	[price_rate] [real] NOT NULL,
	[product_code] [varchar](255) NULL,
	[product_name] [varchar](255) NULL,
	[typeid] [int] NULL,
	[weight] [real] NOT NULL,
	[counterid] [int] NULL,
PRIMARY KEY CLUSTERED 
(
	[productid] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
/****** Object:  Table [dbo].[promotion]    Script Date: 6/13/2024 8:11:30 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[promotion](
	[promotionid] [int] IDENTITY(1,1) NOT NULL,
	[apply_for] [varchar](255) NULL,
	[end_date] [datetime2](6) NULL,
	[promotion_code] [varchar](255) NOT NULL,
	[promotion_name] [varchar](255) NULL,
	[promotion_percent] [real] NOT NULL,
	[start_date] [datetime2](6) NULL,
PRIMARY KEY CLUSTERED 
(
	[promotionid] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[role]    Script Date: 6/13/2024 8:11:30 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[role](
	[role_id] [int] IDENTITY(1,1) NOT NULL,
	[role_name] [varchar](255) NULL,
PRIMARY KEY CLUSTERED 
(
	[role_id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[staff]    Script Date: 6/13/2024 8:11:30 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[staff](
	[staffid] [int] IDENTITY(1,1) NOT NULL,
	[address] [varchar](255) NULL,
	[date_of_birth] [date] NULL,
	[email] [varchar](255) NULL,
	[full_name] [varchar](255) NULL,
	[gender] [char](1) NOT NULL,
	[is_active] [bit] NOT NULL,
	[password] [varchar](255) NULL,
	[phone_number] [varchar](255) NULL,
	[role_id] [int] NULL,
	[counterid] [int] NULL,
PRIMARY KEY CLUSTERED 
(
	[staffid] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[type]    Script Date: 6/13/2024 8:11:30 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[type](
	[typeid] [int] IDENTITY(1,1) NOT NULL,
	[type_name] [varchar](255) NOT NULL,
	[warranty_time] [int] NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[typeid] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
ALTER TABLE [dbo].[gem_price_list]  WITH CHECK ADD  CONSTRAINT [FK3loml4db0td4h96jn30t6s23] FOREIGN KEY([gemid])
REFERENCES [dbo].[gem] ([gemid])
GO
ALTER TABLE [dbo].[gem_price_list] CHECK CONSTRAINT [FK3loml4db0td4h96jn30t6s23]
GO
ALTER TABLE [dbo].[material_price_list]  WITH CHECK ADD  CONSTRAINT [FKdkoummrfw4tu1ebtucl1k3pkb] FOREIGN KEY([materialid])
REFERENCES [dbo].[material] ([materialid])
GO
ALTER TABLE [dbo].[material_price_list] CHECK CONSTRAINT [FKdkoummrfw4tu1ebtucl1k3pkb]
GO
ALTER TABLE [dbo].[order]  WITH CHECK ADD  CONSTRAINT [FK1t7f2e7ygr5axjy3500yofr4s] FOREIGN KEY([customerid])
REFERENCES [dbo].[customer] ([customerid])
GO
ALTER TABLE [dbo].[order] CHECK CONSTRAINT [FK1t7f2e7ygr5axjy3500yofr4s]
GO
ALTER TABLE [dbo].[order]  WITH CHECK ADD  CONSTRAINT [FK5mmf4s5g0k00w0jt8bran1rdo] FOREIGN KEY([staffid])
REFERENCES [dbo].[staff] ([staffid])
GO
ALTER TABLE [dbo].[order] CHECK CONSTRAINT [FK5mmf4s5g0k00w0jt8bran1rdo]
GO
ALTER TABLE [dbo].[order]  WITH CHECK ADD  CONSTRAINT [FKbkwm1jik6a377ke752obwbyue] FOREIGN KEY([orderstatusid])
REFERENCES [dbo].[order_status] ([orderstatusid])
GO
ALTER TABLE [dbo].[order] CHECK CONSTRAINT [FKbkwm1jik6a377ke752obwbyue]
GO
ALTER TABLE [dbo].[order_detail]  WITH CHECK ADD  CONSTRAINT [FKs3xd85qnmrwhiy3qnwnm9dee3] FOREIGN KEY([orderid])
REFERENCES [dbo].[order] ([orderid])
GO
ALTER TABLE [dbo].[order_detail] CHECK CONSTRAINT [FKs3xd85qnmrwhiy3qnwnm9dee3]
GO
ALTER TABLE [dbo].[product]  WITH CHECK ADD  CONSTRAINT [FK4ort9abhumpx4t2mlngljr1vi] FOREIGN KEY([categoryid])
REFERENCES [dbo].[category] ([categoryid])
GO
ALTER TABLE [dbo].[product] CHECK CONSTRAINT [FK4ort9abhumpx4t2mlngljr1vi]
GO
ALTER TABLE [dbo].[product]  WITH CHECK ADD  CONSTRAINT [FK60fbii95p79e2xmoa1vhev4dj] FOREIGN KEY([typeid])
REFERENCES [dbo].[type] ([typeid])
GO
ALTER TABLE [dbo].[product] CHECK CONSTRAINT [FK60fbii95p79e2xmoa1vhev4dj]
GO
ALTER TABLE [dbo].[product]  WITH CHECK ADD  CONSTRAINT [FK9lskagc58o34dje8rey6ex4en] FOREIGN KEY([gem_price_listid])
REFERENCES [dbo].[gem_price_list] ([gem_price_listid])
GO
ALTER TABLE [dbo].[product] CHECK CONSTRAINT [FK9lskagc58o34dje8rey6ex4en]
GO
ALTER TABLE [dbo].[product]  WITH CHECK ADD  CONSTRAINT [FKroj1lpm68ua5n352xl99vverw] FOREIGN KEY([material_price_listid])
REFERENCES [dbo].[material_price_list] ([material_price_listid])
GO
ALTER TABLE [dbo].[product] CHECK CONSTRAINT [FKroj1lpm68ua5n352xl99vverw]
GO
ALTER TABLE [dbo].[staff]  WITH CHECK ADD  CONSTRAINT [FK5bbdfuitxii0b63v2v3f0r22x] FOREIGN KEY([role_id])
REFERENCES [dbo].[role] ([role_id])
GO
ALTER TABLE [dbo].[staff] CHECK CONSTRAINT [FK5bbdfuitxii0b63v2v3f0r22x]
GO
ALTER TABLE [dbo].[product] ADD CONSTRAINT FK_product_counter
FOREIGN KEY (counterid) REFERENCES [dbo].counter;
GO
ALTER TABLE [dbo].[staff] ADD CONSTRAINT FK_staff_counter
FOREIGN KEY (counterid) REFERENCES [dbo].counter;
GO
ALTER TABLE [dbo].[purchase_detail]  WITH CHECK ADD  CONSTRAINT [FK_purchase_detail_order]
FOREIGN KEY([orderid])
REFERENCES [dbo].[order] ([orderid])
GO
ALTER TABLE [dbo].[purchase_detail] CHECK CONSTRAINT [FK_purchase_detail_order]
GO

ALTER TABLE [dbo].[purchase_detail]  WITH CHECK ADD  CONSTRAINT [FK_purchase_detail_gem]
FOREIGN KEY([gemid])
REFERENCES [dbo].[gem] ([gemid])
GO
ALTER TABLE [dbo].[purchase_detail] CHECK CONSTRAINT [FK_purchase_detail_gem]
GO

ALTER TABLE [dbo].[purchase_detail]  WITH CHECK ADD  CONSTRAINT [FK_purchase_detail_material]
FOREIGN KEY([materialid])
REFERENCES [dbo].[material] ([materialid])
GO
ALTER TABLE [dbo].[purchase_detail] CHECK CONSTRAINT [FK_purchase_detail_material]
GO
USE [master]
GO
ALTER DATABASE [fullstackapp] SET  READ_WRITE 
GO
