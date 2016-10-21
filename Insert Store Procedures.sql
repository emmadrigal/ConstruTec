

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