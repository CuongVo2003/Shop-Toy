CREATE DATABASE Toy_Store;
USE Toy_Store;



GO
CREATE TABLE Roles (
  roleId VARCHAR(255) PRIMARY KEY NOT NULL,
  roleName VARCHAR(255) NOT NULL
  );



GO
CREATE TABLE Accounts (
  Username VARCHAR(255) PRIMARY KEY NOT NULL,
  password VARCHAR(255) NOT NULL,
  fullname NVARCHAR(255) NOT NULL,
  email VARCHAR(255) NOT NULL,
  account_adress NVARCHAR(255),
  phone VARCHAR(11)NOT NULL,
  photo VARCHAR(255)
);


GO
CREATE TABLE Authorities (
  authId INT PRIMARY KEY IDENTITY(1,1) NOT NULL,
  userName VARCHAR(255) NOT NULL,
  roleId VARCHAR(255) NOT NULL,
  CONSTRAINT UK_Authories_Username_RoleId UNIQUE (userName, roleId),
  FOREIGN KEY (roleId) REFERENCES roles(roleId),
  FOREIGN KEY (userName) REFERENCES Accounts(Username)
 );


GO
CREATE TABLE Categories(
	cateId NVARCHAR(255) PRIMARY KEY NOT NULL,
	cateName NVARCHAR(255) NOT NULL
); 



GO
CREATE TABLE Products (
  proId INT PRIMARY KEY NOT NULL IDENTITY(1,1),
  prodNname NVARCHAR(255) NOT NULL,
  image1 VARCHAR(255),
  image2 VARCHAR(255),
  image3 VARCHAR(255),
  imageIcon VARCHAR(255),
  price DECIMAL(15, 3) NOT NULL,
  createDate DATE,
  quantity INT NOT NULL,
  describe NVARCHAR(900),
  year_old NVARCHAR(255),
  trademark NVARCHAR(255),
  origin NVARCHAR(255),
  categoryId NVARCHAR(255),
  FOREIGN KEY (categoryId) REFERENCES Categories(cateId)
);



GO
CREATE TABLE Vouchers (
  vouId VARCHAR(255) PRIMARY KEY NOT NULL,
  discount_percentage FLOAT NOT NULL,
  expiry_date DATE NOT NULL,
  quantity int NOT NULL
);

GO
CREATE TABLE Orders (
  ordId INT PRIMARY KEY NOT NULL identity(1,1),
  username VARCHAR(255) NOT NULL,
  create_date DATE NOT NULL,
  order_address NVARCHAR(255),
  ord_phone VARCHAR(10) NOT NULL,
  priceSum DECIMAL(15, 3) NOT NULL,
  ghiChu NVARCHAR(255)NOT NULL,
  tinhTrang bit NOT NULL,
  voucher_id VARCHAR(255),
  FOREIGN KEY (username) REFERENCES Accounts(Username),
  FOREIGN KEY (voucher_id) REFERENCES Vouchers(vouId)
);


GO
CREATE TABLE OrderDetails (
 ordetailId INT PRIMARY KEY NOT NULL identity(1,1),
  order_id INT NOT NULL,
  product_id INT NOT NULL,
  quantity INT NOT NULL,
  price DECIMAL(15, 3) NOT NULL,
  FOREIGN KEY (product_id) REFERENCES Products(proId),
  FOREIGN KEY (order_id) REFERENCES Orders(ordId)
);


GO

CREATE TABLE Comments (
  commentId INT PRIMARY KEY IDENTITY(1,1) NOT NULL,
  username VARCHAR(255) NOT NULL,
  productId INT NOT NULL,
  content NVARCHAR(MAX) NOT NULL,
  createdDate DATETIME DEFAULT CURRENT_TIMESTAMP,
  FOREIGN KEY (username) REFERENCES Accounts(Username),
  FOREIGN KEY (productId) REFERENCES Products(proId)
);
go



-- INSERT DATA
INSERT INTO Roles (roleId, roleName) VALUES 
('CUST', 'Customer'),
('DIRE', 'Director'),
('STAF', 'Staff');


