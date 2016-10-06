CREATE DATABASE "EPATEC"
    WITH 
    OWNER = postgres
    ENCODING = 'UTF8'
    CONNECTION LIMIT = -1;
	
CREATE TABLE CLIENTE(
Id_Number    bigint      PRIMARY KEY,
Name         varchar(50) NOT NULL,
Phone_Number integer
);

CREATE TABLE ENGINEER(
Id_Number    bigint      PRIMARY KEY,
Code         integer     NOT NULL,
Name         varchar(50) NOT NULL,
Phone_Number integer
);

CREATE TABLE PROJECT(
Id_Project  bigserial    PRIMARY KEY,
Id_Client   bigint       REFERENCES CLIENTE(Id_Number),
Id_Engineer bigint       REFERENCES ENGINEER(Id_Number), 
Location    varchar(255) NOT NULL,
Name        varchar      NOT NULL UNIQUE
);

CREATE TABLE STAGE(
Stage_Id    bigserial    PRIMARY KEY,
Name        varchar(255) UNIQUE,
Description varchar(255)
);

CREATE TABLE DIVIDED_IN(
Divided_Id  bigserial PRIMARY KEY,
Id_Proyect  bigint    REFERENCES PROJECT(Id_Project),
Stage_Id    bigint    REFERENCES STAGE(Stage_Id),
Start_Date  date      NOT NULL,
End_Date    date      NOT NULL,
PRIMARY KEY(Divided_Id)
);

CREATE TABLE MATERIAL(
Id_Material bigserial   PRIMARY KEY,
Name        varchar(50) UNIQUE,
Price       integer     NOT NULL,
Description varchar(255)
);

CREATE TABLE POSSESES(
Posseses_Id bigserial    PRIMARY KEY,
Id_Material bigint       REFERENCES MATERIAL(Id_Material),
Stage_Id    bigint       REFERENCES STAGE(Stage_Id),
Quantity    varchar(255) NOT NULL CHECK (Quantity > 0)
);


