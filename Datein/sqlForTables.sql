CREATE TABLE `book` (
  `idbook` int NOT NULL AUTO_INCREMENT,
  `author` varchar(255) NOT NULL,
  `title` varchar(255) NOT NULL,
  `description` mediumtext, 
  `pagecount` int NOT NULL,
  PRIMARY KEY (`idbook`),
  UNIQUE KEY `idbook_UNIQUE` (`idbook`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `loan` (
  `idloan` int NOT NULL AUTO_INCREMENT,
  `idbook` int NOT NULL,
  PRIMARY KEY (`idloan`),
  UNIQUE KEY `idloan_UNIQUE` (`idloan`),
  UNIQUE KEY `idbook_UNIQUE` (`idbook`),
  KEY `idbook_idx` (`idbook`),
  CONSTRAINT `idbook` FOREIGN KEY (`idbook`) REFERENCES `book` (`idbook`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


CREATE TABLE `loanhistory` (
  `idloanhistory` int NOT NULL AUTO_INCREMENT,
  `idbook` int NOT NULL,
  PRIMARY KEY (`idloanhistory`),
  UNIQUE KEY `idloanhistory_UNIQUE` (`idloanhistory`),
  KEY `historybookid_idx` (`idbook`),
  CONSTRAINT `historybookid` FOREIGN KEY (`idbook`) REFERENCES `book` (`idbook`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
