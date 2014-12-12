--
-- Database: `ferreteria`
--

-- Drop obsolete database
DROP DATABASE IF EXISTS `ferreteria`;

-- Create new schema
CREATE DATABASE IF NOT EXISTS `ferreteria` DEFAULT CHARACTER SET=utf8;

-- Load the database
use `ferreteria`;


-- DDL


CREATE TABLE IF NOT EXISTS `users` (
  `IdUser` INT NOT NULL AUTO_INCREMENT,
  `Username` VARCHAR(50) NOT NULL UNIQUE,
  `Password` CHAR(255) NOT NULL,
  `Admin` bool NOT NULL DEFAULT FALSE,
  PRIMARY KEY (`IdUser`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ;


CREATE TABLE IF NOT EXISTS `products` (
  `IdProduct` INT NOT NULL AUTO_INCREMENT,
  `Product` VARCHAR(50) NOT NULL UNIQUE,
  `Price` INT NOT NULL,
  `Stock` INT NOT NULL,
  PRIMARY KEY (`IdProduct`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ;


CREATE TABLE IF NOT EXISTS `purchases` (
  `IdPurchase` INT NOT NULL AUTO_INCREMENT,
  -- `FechaIngreso` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `IdUser` INT NOT NULL,
  `Done` bool NOT NULL DEFAULT FALSE,
  PRIMARY KEY (`IdPurchase`),
  FOREIGN KEY (`IdUser`)
    REFERENCES users(`IdUser`)
    ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ;


CREATE TABLE IF NOT EXISTS `details` (
  `IdDetail` INT NOT NULL AUTO_INCREMENT,
  `Amount` INT NOT NULL,
  `Price` INT NOT NULL,
  `IdProduct` INT NOT NULL,
  `IdPurchase` INT NOT NULL,
  PRIMARY KEY (`IdDetail`),
  FOREIGN KEY (`IdProduct`)
    REFERENCES products(`IdProduct`)
    ON DELETE CASCADE ON UPDATE CASCADE,
  FOREIGN KEY (`IdPurchase`)
    REFERENCES purchases(`IdPurchase`)
    ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ;

