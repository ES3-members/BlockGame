CREATE TABLE user
(
   userName VARCHAR(100) NOT NULL,
   password VARCHAR(100) NOT NULL,
   PRIMARY KEY(userName)
);

CREATE TABLE userscore
(
   userName VARCHAR(100) NOT NULL,
   score VARCHAR(100) NOT NULL
);