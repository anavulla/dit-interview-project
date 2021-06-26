--drop table to make sure existing table is gone if schema is different
DROP TABLE IF EXISTS dit.user;


--creating the user table under dit database.
CREATE TABLE dit.user
( 
user_id INT NOT NULL AUTO_INCREMENT,
firstname VARCHAR(30) NOT NULL, 
lastname  VARCHAR(30) NOT NULL, 
username  VARCHAR(25) UNIQUE NOT NULL,
password  VARCHAR(25) NOT NULL, 
email     VARCHAR(30), 
last_login_time TIME, 
PRIMARY KEY (user_id) 
);
