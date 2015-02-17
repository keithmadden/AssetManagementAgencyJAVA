DROP TABLE IF EXISTS `customer`;
CREATE TABLE  `customer` (
  `CustomerID` int(11) NOT NULL AUTO_INCREMENT,
  `Name` varchar(50) NOT NULL,
  `Address` varchar(20) NOT NULL,
  `Phone` varchar(20) NOT NULL,
  `Email` varchar(12) NOT NULL,
  PRIMARY KEY (`CustomerID`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

INSERT INTO `customer` VALUES
 (1,'keith madden','87 killester','085777779','keith@gmail.com'),
 (2,'grace purcell','78 glenn ellen','024879112','grace@gmail.com'),
 (3,'john hanks','78 harry park','059823212','john@gmail.com');

