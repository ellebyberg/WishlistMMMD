DROP DATABASE IF EXISTS WishListDB;
CREATE DATABASE WishListDB DEFAULT CHARACTER SET utf8;
USE WishListDB;

DROP TABLE IF EXISTS WishList;
DROP TABLE IF EXISTS Wish;
DROP TABLE IF EXISTS CombiWishList;
DROP TABLE IF EXISTS UserProfile;

CREATE TABLE UserProfile (
                             userID INT NOT NULL AUTO_INCREMENT,
                             name VARCHAR(50) NOT NULL,
                             birthdate DATE,
                             username VARCHAR(50) NOT NULL UNIQUE,
                             password VARCHAR(50) NOT NULL,
                             PRIMARY KEY (userID)
);

CREATE TABLE WishList (
                          wishListID INT NOT NULL AUTO_INCREMENT,
                          listName VARCHAR(50) NOT NULL,
                          expireDate DATE,
                          userID INT NOT NULL,
                          PRIMARY KEY (wishListID),
                          FOREIGN KEY (userID) REFERENCES UserProfile(userID) ON DELETE CASCADE
);

CREATE TABLE Wish (
                      wishID INT NOT NULL AUTO_INCREMENT,
                      wishName VARCHAR(50),
                      description VARCHAR(500),
                      link VARCHAR(500),
                      price DOUBLE,
                      PRIMARY KEY (wishID)
);

CREATE TABLE CombiWishList (
                               wishID INT NOT NULL,
                               wishListID INT NOT NULL,
                               PRIMARY KEY (wishID, wishListID),
                               FOREIGN KEY (wishID) REFERENCES Wish(wishID) ON DELETE CASCADE,
                               FOREIGN KEY (wishListID) REFERENCES WishList(wishListID) ON DELETE CASCADE
);