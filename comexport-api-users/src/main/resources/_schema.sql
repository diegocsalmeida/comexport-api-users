DROP TABLE IF EXISTS PRODUCTS;
DROP TABLE IF EXISTS ORDERS;
DROP TABLE IF EXISTS USERS;
  
CREATE TABLE PRODUCTS (
  ID BIGINT AUTO_INCREMENT  PRIMARY KEY NOT NULL,
  DESCRIPTION VARCHAR(250) NOT NULL,
  PRICE DECIMAL(19,2) NOT NULL,
  CREATED_AT TIMESTAMP,
  UPDATED_AT TIMESTAMP
);

CREATE TABLE ORDERS (
	ID BIGINT AUTO_INCREMENT PRIMARY KEY NOT NULL,
	CREATED_AT TIMESTAMP,
	ID_PRODUCT BIGINT NOT NULL,
	ID_USER BIGINT NOT NULL,
	PRICE DECIMAL(19,2) NOT NULL,
	SALES_CHANNEL VARCHAR(255) NOT NULL,
	STATUS VARCHAR(255),
	UPDATED_AT TIMESTAMP
);

CREATE TABLE USERS (
	ID BIGINT AUTO_INCREMENT  PRIMARY KEY NOT NULL,
	BIRTH_DATE DATE NOT NULL,
	CREATED_AT TIMESTAMP,
	EMAIL VARCHAR(255),
	ENABLE BOOLEAN NOT NULL,
	NAME VARCHAR(255),
	UPDATED_AT TIMESTAMP
);