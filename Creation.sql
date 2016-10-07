CREATE DATABASE "ConstruTec";
	
CREATE TABLE CLIENT(
Id_Number    bigint      PRIMARY KEY,
Name         varchar(50) NOT NULL,
Phone_Number integer
);

CREATE TABLE ENGINEER(
Id_Number    bigint      PRIMARY KEY,
Code         varchar(50) NOT NULL,
Name         varchar(50) NOT NULL,
Phone_Number integer
);

CREATE TABLE PROJECT(
Id_Project  bigserial    PRIMARY KEY,
Id_Client   bigint       REFERENCES CLIENT(Id_Number),
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
Status      boolean   NOT NULL
);

CREATE TABLE COMMENTARY(
Comment_Id bigserial PRIMARY KEY,
Divided_Id bigint    REFERENCES DIVIDED_IN(Divided_Id),
Commentary text
);

CREATE TABLE MATERIAL(
Id_Material bigserial   PRIMARY KEY,
Name        varchar(50) UNIQUE,
Price       integer     NOT NULL,
Description varchar(255)
);

CREATE TABLE POSSESES(
Posseses_Id bigserial PRIMARY KEY,
Id_Material bigint    REFERENCES MATERIAL(Id_Material),
Stage_Id    bigint    REFERENCES STAGE(Stage_Id),
Quantity    integer   NOT NULL CHECK (Quantity > 0)
);

INSERT INTO STAGE (Stage_Id,Name,Description) VALUES (0, 'Trabajo Preliminar', 'Consiste de solicitud de permisos y otras labores administrativas'), (1, 'Cimientos', ' Elementos estructurales encargados de transmitir el peso del edificio al suelo'), (2, 'Paredes', 'Divide distintas partes de la edificación y provee soporte al techo'), (3, 'Concreto Reforzado', 'Consiste en utilizar varillas para darle soporte al muro'), (4, 'Techos', 'Protege al edificio de lluvia y otros problemas atmosféricos'), (5, 'Cielos', 'Soporte para diferentes estructuras en el techo'), (6, 'Repello', 'Cubierta para la pared'), (7, 'Entrepisos', 'Piso entre los distintos niveles de una edificación'), (8, 'Pisos', 'Cubierta sobre el suelo'), (9, 'Enchapes', 'Relleno para las grietas en el piso'), (10, 'Instalación Pluvial', 'Utilizado para evacuar aguas provenientes de la lluvia'), (11, 'Instalación Sanitaria', 'Evacuación de las aguas negras generadas en la casa'), (12, 'Puertas', 'Provee acceso a los distintos cuartos'), (13, 'Cerrajería', 'Permite mayor seguridad'), (14, 'Ventanas', 'Permite ver afuera de la casa'), (15, 'Closets', 'Utilizado para guardar vestimenta'), (16, 'Mueble de Cocina', 'Utilizado para guardar comida'), (17, 'Escaleras', '');

--Protects from deletions on the default values of the stage table
CREATE TRIGGER protectDefault
  BEFORE DELETE
  ON STAGE
  FOR EACH ROW
  EXECUTE PROCEDURE protectDefault();

  
 CREATE FUNCTION protectDefault() RETURNS trigger AS $protectDefault$
    BEGIN            
        IF OLD.Stage_Id < 20 THEN
            RAISE EXCEPTION 'Cannot delete default stage';
        END IF;

    END;
$protectDefault$ LANGUAGE plpgsql;


