CREATE DATABASE "ConstruTec";

\c "ConstruTec"	

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
Id_Project  bigint    REFERENCES PROJECT(Id_Project),
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

INSERT INTO STAGE (Stage_Id,Name,Description) VALUES (0, 'Trabajo Preliminar', 'Consiste de solicitud de permisos y otras labores administrativas'), (1, 'Cimientos', ' Elementos estructurales encargados de transmitir el peso del edificio al suelo'), (2, 'Paredes', 'Divide distintas partes de la edificación y provee soporte al techo'), (3, 'Concreto Reforzado', 'Consiste en utilizar varillas para darle soporte al muro'), (4, 'Techos', 'Protege al edificio de lluvia y otros problemas atmosféricos'), (5, 'Cielos', 'Soporte para diferentes estructuras en el techo'), (6, 'Repello', 'Cubierta para la pared'), (7, 'Entrepisos', 'Piso entre los distintos niveles de una edificación'), (8, 'Pisos', 'Cubierta sobre el suelo'), (9, 'Enchapes', 'Relleno para las grietas en el piso'), (10, 'Instalación Pluvial', 'Utilizado para evacuar aguas provenientes de la lluvia'), (11, 'Instalación Sanitaria', 'Evacuación de las aguas negras generadas en la casa'), (12, 'Puertas', 'Provee acceso a los distintos cuartos'), (13, 'Cerrajería', 'Permite mayor seguridad'), (14, 'Ventanas', 'Permite ver afuera de la casa'), (15, 'Closets', 'Utilizado para guardar vestimenta'), (16, 'Mueble de Cocina', 'Utilizado para guardar comida'), (17, 'Escaleras', 'Utilizadas para accesar distintos niveles');

--Protects from deletions on the default values of the stage table
CREATE FUNCTION protectDefault() RETURNS trigger AS $protectDefault$
    BEGIN            
        IF (OLD.Stage_Id < 18) THEN
			INSERT INTO STAGE (Stage_Id, Name, Description) VALUES (OLD.Stage_Id, Old.Name, Old.Description);
        END IF;
		RETURN NULL;
    END;
$protectDefault$ LANGUAGE plpgsql;

CREATE TRIGGER protectDefault
  AFTER DELETE
  ON STAGE
  FOR EACH ROW
  EXECUTE PROCEDURE protectDefault();
  
  
--Avoids creation of stages that when a previous stage hasn't finished
CREATE FUNCTION stageOrder() RETURNS trigger AS $stageOrder$
    BEGIN            
        IF ( (SELECT COUNT(*) FROM DIVIDED_IN WHERE End_Date > NEW.Start_Date AND Status = 0 AND Id_Project = New.Id_Project) > 0 ) THEN
			RAISE NOTICE "Previous stages must be finished before starting a new one";
			RETURN NULL;
		END IF;
    END;
$stageOrder$ LANGUAGE plpgsql;

CREATE TRIGGER stageOrder
  BEFORE INSERT
  ON DIVIDED_IN
  FOR EACH ROW
  EXECUTE PROCEDURE stageOrder();

  Buscar todos los proyectos donde exista una etapa que inicie en los siguientes
15 días con el fin de llamar y ofrecer los materiales.
  
--Procedure that returns all projects that have a stage that begins on the next 15 days
CREATE FUNCTION nextProyect() RETURNS PROJECT(
	Id_Project  bigserial
	Id_Client   bigint
	Id_Engineer bigint
	Location    varchar(255)
	Name        varchar
) 
AS $$
    BEGIN            
		RETURN QUERY SELECT
		p.Id_Project, p.Id_Client, p.Id_Engineer, p.Location, p.Name
		FROM
		PROJECT AS p JOIN DIVIDED_IN as d ON PROJECT.Id_Project = DIVIDED_IN.Id_Project
		WHERE
		d.Start_Date > current_date + interval '15' day;        
    END;
LANGUAGE plpgsql;

--Procedure that returns all projects that have a stage that begins in the next 15 days and need certain material
CREATE FUNCTION nextProyectMaterial(material varchar(50)) RETURNS PROJECT(
	Id_Project  bigserial
	Id_Client   bigint
	Id_Engineer bigint
	Location    varchar(255)
	Name        varchar
) 
AS $$
    BEGIN            
		RETURN QUERY SELECT
		p.Id_Project, p.Id_Client, p.Id_Engineer, p.Location, p.Name
		FROM
		PROJECT AS p 
		NATURAL JOIN DIVIDED_IN as d
		NATURAL JOIN POSSESES as pos
		NATURAL JOIN MATERIAL as mat
		WHERE
		d.Start_Date > current_date + interval '15' day
		AND mat.Name = material;      
    END;
LANGUAGE plpgsql;


--Procedure that Deletes stages by Name
CREATE FUNCTION deleteStage(StageName varchar(255)) RETURNS void AS $$
    BEGIN
		IF ((SELECT 1 FROM STAGE WHERE Name = StageName) > 0)
			IF( (SELECT Stage_Id FROM STAGE WHERE Name = StageName) < 18  )--This is also checked by a trigger
				DELETE FROM COMMENTARY as c using DIVIDED_IN as d NATURAL JOIN STAGE s WHERE d.Divided_Id = c.Divided_Id AND s.Name = StageName;
				DELETE FROM DIVIDED_IN as d using STAGE s WHERE d.Stage_Id = s.Stage_Id AND s.Name = StageName;
				DELETE FROM POSSESES as p using STAGE s WHERE p.Stage_Id = s.Stage_Id AND s.Name = StageName;
				DELETE FROM STAGE WHERE Name = StageName;
			END IF;
		END IF;
    END;
LANGUAGE plpgsql;

--Procedure that Projects by Name
CREATE FUNCTION deleteProject(ProjectName varchar(255)) RETURNS void AS $$
    BEGIN
		DELETE FROM COMMENTARY as c using DIVIDED_IN as d NATURAL JOIN PROJECT p WHERE d.Divided_Id = c.Divided_Id AND p.Name = ProjectName;
		DELETE FROM DIVIDED_IN as d using PROJECT p WHERE d.Id_Project = p.Id_Project AND p.Name = ProjectName;
		DELETE FROM PROJECT WHERE Name = ProjectName;
    END;
LANGUAGE plpgsql;

--Procedure that Clientes by Id
CREATE FUNCTION deleteClient(Id bigint) RETURNS void AS $$
    BEGIN
		SELECT * from deleteProject(SELECT 1 FROM PROJECT WHERE Id_Client = Id);
		DELETE FROM CLIENT WHERE Id_Number = Id;
    END;
LANGUAGE plpgsql;


--Procedure that Engineers by ID
CREATE FUNCTION deleteEngineer(Id bigint) RETURNS void AS $$
    BEGIN
		SELECT * from deleteProject(SELECT 1 FROM PROJECT WHERE Id_Engineer = Id);
		DELETE FROM ENGINEER WHERE Id_Number = Id;
    END;
LANGUAGE plpgsql;




  
  
  
  
  
  
  