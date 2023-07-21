CREATE DATABASE workshop3 CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

CREATE table users ( id int(11) not null auto_increment, primary key(id),email varchar(255) not null UNIQUE , username varchar(255) not null, password varchar(60) not null);