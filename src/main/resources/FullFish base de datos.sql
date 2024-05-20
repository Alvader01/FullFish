CREATE TABLE User (
    username VARCHAR(120) PRIMARY KEY,
    name VARCHAR(150),
    password VARCHAR(125),
    email VARCHAR(140)
);

CREATE TABLE Fishtank (
    id INT(7) unsigned AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(30),
    capacity INT(4),
    lengthy FLOAT(6,2) unsigned,
    width FLOAT(6,2) unsigned,
    height FLOAT(6,2) unsigned,
    user_username VARCHAR(30),
    FOREIGN KEY (user_username) REFERENCES User(username)
);

CREATE TABLE Species (
    id INT(7) unsigned AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(30),
    dimension INT(3) unsigned,
    longevity INT(2) unsigned,
);

CREATE TABLE Holds (
    fishtankId INT(7) unsigned,
    speciesId INT(7) unsigned,
    PRIMARY KEY (fishtankId, speciesId),
    FOREIGN KEY (fishtankId) REFERENCES Fishtank(id),
    FOREIGN KEY (speciesId) REFERENCES Species(id)
);








