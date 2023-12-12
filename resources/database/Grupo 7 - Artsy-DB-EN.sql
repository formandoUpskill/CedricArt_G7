
DROP Database if exists artsydb;
 
 create database artsydb;

use  artsydb;

CREATE TABLE User
(
  name VARCHAR(150),
  Email VARCHAR(100) NOT NULL,
  id_User INT NOT NULL AUTO_INCREMENT,
  username VARCHAR(100) NOT NULL,
  password VARCHAR(100) NOT NULL,
  PRIMARY KEY (id_User) ,
  UNIQUE (Email),
  UNIQUE (username)
);

CREATE TABLE Gallerist
(
  start_at DATE NOT NULL,
  end_at DATE,
  id_Gallerist INT NOT NULL,
  PRIMARY KEY (id_Gallerist),
  FOREIGN KEY (id_Gallerist) REFERENCES User(id_User)
);


CREATE TABLE Coordinator
(
  start_at DATE NOT NULL,
  end_at DATE,
  id_Coordinator INT NOT NULL,
  PRIMARY KEY (id_Coordinator),
  FOREIGN KEY (id_Coordinator) REFERENCES User(id_User)
);



CREATE TABLE Artist
(
  location VARCHAR(250),
  hometown VARCHAR(250),
  id_Artist CHAR(40) NOT NULL,
  name VARCHAR(500),
  biography VARCHAR(2500),
  slug VARCHAR(500),
  birthyear VARCHAR(4) NOT NULL,
  deathyear VARCHAR(4),
  thumbnail VARCHAR(500),
  url VARCHAR(250),
  nationality VARCHAR(50) NOT NULL,
  PRIMARY KEY (id_Artist)
);

CREATE TABLE Gene
(
  id_Gene CHAR(40) NOT NULL,
  name VARCHAR(500) NOT NULL,
  description VARCHAR(2500),
  PRIMARY KEY (id_Gene)
);



CREATE TABLE Exhibition_Status
(
  Id_Exhibition_Status INT NOT NULL AUTO_INCREMENT,
  Status VARCHAR(15) NOT NULL,
  PRIMARY KEY (Id_Exhibition_Status)
);

CREATE TABLE Partner
(
  region VARCHAR(250),
  url VARCHAR(250),
  name VARCHAR(500) NOT NULL,
  id_Partner CHAR(40) NOT NULL,
  website VARCHAR(500),
  id_Gallerist INT NOT NULL,
  id_Coordinator INT NOT NULL,
  PRIMARY KEY (id_Partner),
  FOREIGN KEY (id_Gallerist) REFERENCES Gallerist(id_Gallerist),
  FOREIGN KEY (id_Coordinator) REFERENCES Coordinator(id_Coordinator)
);

CREATE TABLE Artwork
(
  title VARCHAR(500) NOT NULL,
  date VARCHAR(100) NOT NULL,
  id_Artwork CHAR(40) NOT NULL,
  thumbnail VARCHAR(500) NOT NULL,
  url VARCHAR(250),
  created_at TIMESTAMP NOT NULL,
  updated_at TIMESTAMP NOT NULL,
  id_Partner CHAR(40),
  PRIMARY KEY (id_Artwork),
  FOREIGN KEY (id_Partner) REFERENCES Partner(id_Partner)
);

CREATE TABLE Exhibition
(
  end_at TIMESTAMP,
  start_at TIMESTAMP NOT NULL,
  thumbnail VARCHAR(500),
  description VARCHAR(2500),
  id_Exhibition CHAR(40) NOT NULL,
  name VARCHAR(500) NOT NULL,
  url VARCHAR(500),
  id_Partner CHAR(40) NOT NULL,
  Id_Exhibition_Status INT NOT NULL,
  PRIMARY KEY (id_Exhibition),
  FOREIGN KEY (id_Partner) REFERENCES Partner(id_Partner),
  FOREIGN KEY (Id_Exhibition_Status) REFERENCES Exhibition_Status(Id_Exhibition_Status)
);

CREATE TABLE Artwork_Gene
(
  id_Artwork CHAR(40) NOT NULL,
  id_Gene CHAR(40) NOT NULL,
  FOREIGN KEY (id_Artwork) REFERENCES Artwork(id_Artwork),
  FOREIGN KEY (id_Gene) REFERENCES Gene(id_Gene)
);

CREATE TABLE Created_By
(
  id_Artist CHAR(40) NOT NULL,
  id_Artwork CHAR(40) NOT NULL,
  FOREIGN KEY (id_Artist) REFERENCES Artist(id_Artist),
  FOREIGN KEY (id_Artwork) REFERENCES Artwork(id_Artwork)
);

CREATE TABLE Exhibition_Artwork
(
  id_Artwork CHAR(40) NOT NULL,
  id_Exhibition CHAR(40) NOT NULL,
  FOREIGN KEY (id_Artwork) REFERENCES Artwork(id_Artwork),
  FOREIGN KEY (id_Exhibition) REFERENCES Exhibition(id_Exhibition)
);

INSERT INTO User (Email,username,password) values ('gallerist@cedricArt.pt', 'gallerist', 'password' );
INSERT INTO User (Email,username,password) values ('coordinator@cedricArt.pt', 'coordinator', 'password' );

insert into Gallerist (id_Gallerist,start_at) values (1, '2023-11-20');
insert into Coordinator (id_Coordinator,start_at) values (2,'2023-11-21');


insert into Exhibition_Status(Status) values ('upcoming'), ('running'), ('closed'), ('current');

/*
Insert into Exhibition_Artwork (id_Artwork, id_Exhibition) 
SELECT artwork.id_Artwork, exhibition.id_Exhibition
FROM artwork 
JOIN partner ON artwork.id_Partner = partner.Id_partner 
JOIN exhibition ON artwork.id_Partner = exhibition.Id_partner
LIMIT 10;

*/
