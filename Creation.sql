CREATE USER tom WITH PASSWORD 'Password';

CREATE DATABASE "ConstruTec";

GRANT ALL PRIVILEGES ON DATABASE "ConstruTec" to tom;

\c "ConstruTec"	


CREATE TABLE ROLES(
	Role_Id bigserial     PRIMARY KEY,
	Role_Name varchar(50) UNIQUE
);

INSERT INTO ROLES (Role_Id, Role_Name) VALUES (0, 'Client'), (1, 'Engineer'), (2, 'General User');

CREATE TABLE USUARIO(
	Id_Number    bigint      PRIMARY KEY,
	Code         varchar(50),
	Name         varchar(50) NOT NULL,
	Phone_Number integer,
	Role_Id      bigint      REFERENCES ROLES(Role_Id)
);



CREATE TABLE PROJECT(
	Id_Project  bigserial    PRIMARY KEY,
	Id_Client   bigint       REFERENCES USUARIO(Id_Number) ON DELETE CASCADE,
	Id_Engineer bigint       REFERENCES USUARIO(Id_Number), 
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
	Id_Project  bigint    REFERENCES PROJECT(Id_Project) ON DELETE CASCADE,
	Stage_Id    bigint    REFERENCES STAGE(Stage_Id) ON DELETE CASCADE,
	Start_Date  date      NOT NULL,
	End_Date    date      NOT NULL,
	Status      boolean   NOT NULL
);

CREATE TABLE COMMENTARY(
	Comment_Id bigserial PRIMARY KEY,
	ID_PROJECT bigint    REFERENCES PROJECT(ID_PROJECT) ON DELETE CASCADE,
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
	Id_Material bigint    REFERENCES MATERIAL(Id_Material) ON DELETE CASCADE,
	Divided_Id  bigint    REFERENCES DIVIDED_IN(Divided_Id) ON DELETE CASCADE,
	Quantity    integer   NOT NULL CHECK (Quantity > 0)
);


--Creates the defualt stages, this cannot be deleted and is secured by the protectDefault Trigger
INSERT INTO STAGE (Stage_Id,Name,Description) VALUES (0, 'Trabajo Preliminar', 'Consiste de solicitud de permisos y otras labores administrativas'), (1, 'Cimientos', ' Elementos estructurales encargados de transmitir el peso del edificio al suelo'), (2, 'Paredes', 'Divide distintas partes de la edificación y provee soporte al techo'), (3, 'Concreto Reforzado', 'Consiste en utilizar varillas para darle soporte al muro'), (4, 'Techos', 'Protege al edificio de lluvia y otros problemas atmosféricos'), (5, 'Cielos', 'Soporte para diferentes estructuras en el techo'), (6, 'Repello', 'Cubierta para la pared'), (7, 'Entrepisos', 'Piso entre los distintos niveles de una edificación'), (8, 'Pisos', 'Cubierta sobre el suelo'), (9, 'Enchapes', 'Relleno para las grietas en el piso'), (10, 'Instalación Pluvial', 'Utilizado para evacuar aguas provenientes de la lluvia'), (11, 'Instalación Sanitaria', 'Evacuación de las aguas negras generadas en la casa'), (12, 'Puertas', 'Provee acceso a los distintos cuartos'), (13, 'Cerrajería', 'Permite mayor seguridad'), (14, 'Ventanas', 'Permite ver afuera de la casa'), (15, 'Closets', 'Utilizado para guardar vestimenta'), (16, 'Mueble de Cocina', 'Utilizado para guardar comida'), (17, 'Escaleras', 'Utilizadas para accesar distintos niveles');

--Protects from deletions on the default values of the stage table
CREATE FUNCTION protectDefault() RETURNS trigger AS $protectDefault$
    BEGIN            
        IF (OLD.Stage_Id < 18) THEN
			INSERT INTO STAGE (Stage_Id, Name, Description) VALUES (OLD.Stage_Id, Old.Name, Old.Description);
        END IF;
		RETURN NULL;
    END;
$protectDefault$ LANGUAGE sql;

CREATE TRIGGER protectDefault
  AFTER DELETE
  ON STAGE
  FOR EACH ROW
  EXECUTE PROCEDURE protectDefault();
  
  
--Avoids creation of stages that when a previous stage hasn't finished
CREATE FUNCTION stageOrder() RETURNS trigger AS $stageOrder$
    BEGIN            
        IF ( New.Start_Date > New.End_Date) THEN
			RAISE NOTICE 'End Date must be after Start Date';
			RETURN NULL;
		END IF;
		RETURN NEW;
    END;
$stageOrder$ LANGUAGE sql;

CREATE TRIGGER stageOrder
  BEFORE INSERT
  ON DIVIDED_IN
  FOR EACH ROW
  EXECUTE PROCEDURE stageOrder();

  --Procedure that returns the budget for each stage 
CREATE OR REPLACE FUNCTION budget(IN ProjectId bigint) RETURNS TABLE(
	StageName varchar(255),
	Price     bigint
)
AS $BODY$
		SELECT s.Name, sum(mat.price * pos.quantity)
		FROM
		Stage as s
		NATURAL JOIN DIVIDED_IN as d
        RIGHT JOIN PROJECT as p ON p.Id_Project = d.Id_Project
        LEFT JOIN POSSESES as pos ON pos.Divided_Id = d.Divided_Id
        LEFT JOIN MATERIAL as mat ON mat.Id_Material = pos.Id_Material
        WHERE p.Id_Project = ProjectId Group By s.Name;
$BODY$ LANGUAGE sql;
  
--Procedure that returns all projects that have a stage that begins on the next 15 days
CREATE OR REPLACE FUNCTION nextProject() RETURNS SETOF PROJECT 
AS $BODY$     
		SELECT p.Id_Project, p.Id_Client, p.Id_Engineer, p.Location, p.Name
		FROM PROJECT AS p NATURAL JOIN DIVIDED_IN as d
		WHERE
		d.Start_Date < current_date + interval '15' day 
        AND d.Start_Date > current_date;       
$BODY$ LANGUAGE sql;


CREATE OR REPLACE FUNCTION nextProjectMaterial(IN material varchar(50)) RETURNS SETOF PROJECT
AS $$       
		SELECT p.Id_Project, p.Id_Client, p.Id_Engineer, p.Location, p.Name
		FROM PROJECT as p
		NATURAL JOIN DIVIDED_IN as d
        LEFT JOIN POSSESES as pos ON pos.Divided_Id = d.Divided_Id
        LEFT JOIN MATERIAL as mat ON mat.Id_Material = pos.Id_Material
		WHERE
		d.Start_Date < current_date + interval '15' day 
        AND d.Start_Date > current_date
		AND mat.Name = material;      
$$ LANGUAGE sql;


--Procedure that Deletes stages by Name
CREATE FUNCTION deleteStage(IN StageName varchar(255)) RETURNS void AS $$
    BEGIN
		IF ((SELECT 1 FROM STAGE WHERE Name = StageName) > 0) THEN
			IF( (SELECT Stage_Id FROM STAGE WHERE Name = StageName) < 18  ) THEN--This is also checked by a trigger
				DELETE FROM COMMENTARY as c using DIVIDED_IN as d NATURAL JOIN STAGE s WHERE d.Divided_Id = c.Divided_Id AND s.Name = StageName;
				DELETE FROM DIVIDED_IN as d using STAGE s WHERE d.Stage_Id = s.Stage_Id AND s.Name = StageName;
				DELETE FROM POSSESES as p using STAGE s WHERE p.Stage_Id = s.Stage_Id AND s.Name = StageName;
				DELETE FROM STAGE WHERE Name = StageName;
			END IF;
		END IF;
    END;
$$ LANGUAGE sql;

--Procedure that Projects by Name
CREATE FUNCTION deleteProject(IN ProjectName varchar(255)) RETURNS void AS $$
    BEGIN
		DELETE FROM COMMENTARY as c using DIVIDED_IN as d NATURAL JOIN PROJECT p WHERE d.Divided_Id = c.Divided_Id AND p.Name = ProjectName;
		DELETE FROM DIVIDED_IN as d using PROJECT p WHERE d.Id_Project = p.Id_Project AND p.Name = ProjectName;
		DELETE FROM PROJECT WHERE Name = ProjectName;
    END;
$$ LANGUAGE sql;

--Procedure that Clientes by Id
CREATE FUNCTION deleteClient(IN Id bigint) RETURNS void AS $$
    BEGIN
		SELECT * from deleteProject((SELECT 1 FROM PROJECT WHERE Id_Client = Id));
		DELETE FROM CLIENT WHERE Id_Number = Id;
    END;
$$ LANGUAGE sql;


--Procedure that Engineers by ID
CREATE FUNCTION deleteEngineer(IN Id bigint) RETURNS void AS $$
    BEGIN
		SELECT * from deleteProject((SELECT 1 FROM PROJECT WHERE Id_Engineer = Id));
		DELETE FROM ENGINEER WHERE Id_Number = Id;
    END;
$$ LANGUAGE sql;



--Procedure is used to insert roles in the database
CREATE OR REPLACE FUNCTION insert_Roles(IN role_name varchar(50)) RETURNS VOID AS $$
        BEGIN
           IF (EXISTS (SELECT 1 FROM ROLES)) THEN
               	INSERT INTO ROLES VALUES ((SELECT MAX(ROLE_ID) FROM ROLES)+1, role_name );
           ELSE
                INSERT INTO ROLES VALUES (0, role_name);
           END IF;
   	    END;
$$ LANGUAGE plpgsql;


--Procedure is used to insert commentaries in the database
CREATE OR REPLACE FUNCTION insert_Commentary(IN divided_id bigint, IN commentary text) RETURNS VOID AS $$
        BEGIN
           IF (EXISTS (SELECT 1 FROM COMMENTARY)) THEN
               	INSERT INTO COMMENTARY VALUES ((SELECT MAX(COMMENT_ID) FROM COMMENTARY)+1, divided_id, commentary);
           ELSE
                INSERT INTO COMMENTARY VALUES (0, divided_id, commentary);
           END IF;
   	    END;
$$ LANGUAGE plpgsql;


--Procedure is used to insert projects in the database
CREATE OR REPLACE FUNCTION insert_Project(IN id_client bigint, IN id_engineer bigint, IN location1 varchar(255), IN name varchar) RETURNS VOID AS $$
        BEGIN
           IF (EXISTS (SELECT 1 FROM PROJECT)) THEN
               	INSERT INTO PROJECT VALUES ((SELECT MAX(ID_PROJECT) FROM PROJECT)+1, id_client, id_engineer, location1, name);
           ELSE
                INSERT INTO PROJECT VALUES (0, id_client, id_engineer, location1, name);
           END IF;
   	    END;
$$ LANGUAGE plpgsql;



--Procedure used to insert divided_in into the database
CREATE OR REPLACE FUNCTION insert_Divided_in(IN id_project bigint, IN stage_id bigint, IN start_date date, IN end_date date,IN status boolean) RETURNS VOID AS $$
        BEGIN
           IF (EXISTS (SELECT 1 FROM DIVIDED_IN)) THEN
               	INSERT INTO DIVIDED_IN VALUES ((SELECT MAX(DIVIDED_ID) FROM DIVIDED_IN)+1, id_project, stage_id, start_date, end_date, status);
           ELSE
                INSERT INTO DIVIDED_IN VALUES (0, id_project, stage_id, start_date, end_date, status);
           END IF;
   	    END;
$$ LANGUAGE plpgsql;


--Procedure used to insert material into the database
CREATE OR REPLACE FUNCTION insert_Material(IN name varchar(50), IN price integer, IN description varchar(255)) RETURNS VOID AS $$
        BEGIN
           IF (EXISTS (SELECT 1 FROM MATERIAL)) THEN
               	INSERT INTO MATERIAL VALUES ((SELECT MAX(ID_MATERIAL) FROM MATERIAL)+1, name, price, description);
           ELSE
                INSERT INTO MATERIAL VALUES (0, name, price, description);
           END IF;
   	    END;
$$ LANGUAGE plpgsql;


--Procedure used to insert posseses into the database
CREATE OR REPLACE FUNCTION insert_Posseses(IN id_material bigint, IN divided_id bigint, IN quantity integer) RETURNS VOID AS $$
        BEGIN
           IF (EXISTS (SELECT 1 FROM POSSESES)) THEN
               	INSERT INTO POSSESES VALUES ((SELECT MAX(POSSESES_ID) FROM POSSESES)+1, id_material, divided_id, quantity);
           ELSE
                INSERT INTO POSSESES VALUES (0, id_material, divided_id, quantity);
           END IF;
   	    END;
$$ LANGUAGE plpgsql;


--Procedure used to insert stages into the database
CREATE OR REPLACE FUNCTION insert_Stage(IN name varchar(255), IN description varchar(255)) RETURNS VOID AS $$
        BEGIN
           IF (EXISTS (SELECT 1 FROM STAGE)) THEN
               	INSERT INTO STAGE VALUES ((SELECT MAX(STAGE_ID) FROM STAGE)+1, name, description);
           ELSE
                INSERT INTO STAGE VALUES (0, name, description);
           END IF;
   	    END;
$$ LANGUAGE plpgsql;