﻿using ConstruTec.Models;
using Npgsql;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace ConstruTec
{
    /// <summary>
    /// Class is the responsible to the connection with the database.
    /// </summary>
    public class ConnectionDB
    {
        private static ConnectionDB instance;
        private string connectionString;

        /// <summary>
        /// Method is the constructor of the ConnectionDB class
        /// </summary>
        public static ConnectionDB Instance
        {
            get
            {
                if (instance == null)
                {
                    instance = new ConnectionDB();
                }//end if
                return instance;
            }//end get
        }//end 


        private ConnectionDB()
        {
            //They are set the details of the connection with the database
            connectionString = "Server=localhost;Port=5432;User id=postgres; Password=andres96;Database=ConstruTec;";
        }//End of constructor  method

        //##### ROLES METHODS #######

        /// <summary>
        /// This method add the role in the ConstruTec database.
        /// </summary>
        /// <param name="rol">The rol that will be added to the database</param>
        public void crear_Roles(Roles rol)
        {
            //The object responsible of the connection is created 
            NpgsqlConnection connection = new NpgsqlConnection();
            connection.ConnectionString = connectionString;
            try
            {
                connection.Open();
                System.Diagnostics.Debug.WriteLine("Sucessful Connection");
                String query = "SELECT insert_Roles(@role_name);";
                NpgsqlCommand command = new NpgsqlCommand(query, connection);
                //Adds the parametes of the command
                command.Parameters.AddWithValue("@role_name", rol.Role_name);
                //Executes the command
                command.ExecuteNonQuery();
                //Close the connection
                connection.Close();
            }
            catch (Exception e)
            {
                //It is notified in the output that the connection failed 
                System.Diagnostics.Debug.WriteLine("Failed Connection in 'crear_Roles': " + e.Message);
            }//End catch
        }//End of the method


        /// <summary>
        /// This method delete the role whose id attribute is id. 
        /// </summary>
        /// <param name="id">The id of the role to delete.</param>
        public void eliminar_Roles(long id)
        {
            //The object responsible of the connection is created 
            NpgsqlConnection connection = new NpgsqlConnection();
            connection.ConnectionString = connectionString;
            try
            {
                connection.Open();
                System.Diagnostics.Debug.WriteLine("Sucessful Connection");
                String query = "DELETE FROM ROLES WHERE role_id = @id;";
                NpgsqlCommand command = new NpgsqlCommand(query, connection);
                command.Parameters.AddWithValue("@id", id);
                command.ExecuteNonQuery();
                connection.Close();
            }
            catch (Exception e)
            {
                //It is notified in the output that the connection failed 
                System.Diagnostics.Debug.WriteLine("Failed Connection in eliminar_Roles: " + e.Message);
            }//End catch
        }//End of method


        /// <summary>
        /// Method allow to update an attribute of a specific role.
        /// </summary>
        /// <param name="id"> The id of the role that it is wanted to update</param>
        /// <param name="newValue">The new value of the attribute. </param>
        /// <param name="campo">The attribute whose value will be update. </param>
        public void update_Roles(long id, String campo, String newValue)
        {
            //The object responsible of the connection is created 
            NpgsqlConnection connection = new NpgsqlConnection();
            connection.ConnectionString = connectionString;
            try
            {
                connection.Open();
                System.Diagnostics.Debug.WriteLine("Sucessful Connection");
                if (campo.Equals("nombre"))
                {
                    String query = "UPDATE ROLES SET role_name = @newValue WHERE role_id = @id;"; ;
                    NpgsqlCommand command = new NpgsqlCommand(query, connection);
                    var valor =  newValue;
                    command.Parameters.AddWithValue("@newValue", valor);
                    command.Parameters.AddWithValue("@id", id);
                    //Executes the command
                    command.ExecuteNonQuery();
                }
                
                connection.Close();
            }
            catch (Exception e)
            {
                //It is notified in the output that the connection failed 
                System.Diagnostics.Debug.WriteLine("Failed Connection in update_roles: " + e.Message);
            }//End catch

        }//End od method


        /// <summary>
        /// Recovers the role with the id_user id.
        /// </summary>
        /// <param name="id"></param>
        /// <returns></returns>
        public Roles get_Roles(long id)
        {
            //The object responsible of the connection is created 
            NpgsqlConnection con = new NpgsqlConnection();
            con.ConnectionString = connectionString;
            //The output object is created
            Roles rol = new Roles();
            try
            {
                con.Open();
                System.Diagnostics.Debug.WriteLine("Sucessful Connection");
                String query = "SELECT * FROM ROLES WHERE role_id = @id;";
                NpgsqlCommand command = new NpgsqlCommand(query, con);
                command.Parameters.AddWithValue("@id", id);
                //The query is executed
                var reader = command.ExecuteReader();
                //The values of the attributes are recovered
                reader.Read();
                rol.Role_id = Int64.Parse(reader["role_id"].ToString());
                rol.Role_name = reader["role_name"].ToString();
                con.Close();
            }
            catch (Exception e)
            {
                //It is notified in the output that the connection failed 
                System.Diagnostics.Debug.WriteLine("Failed Connection in get_Roles: " + e.Message);
            }//End catch
            return rol;
        }//End of 


        /// <summary>
        /// Method return all the roles in the database.
        /// </summary>
        /// <returns>List of roles</returns>
        public List<Roles> get_allRoles()
        {
            //The output object 
            List<Roles> roles = new List<Roles>();
            //The object responsible of the connection is created 
            NpgsqlConnection connection = new NpgsqlConnection();
            connection.ConnectionString = connectionString;
            try
            {
                connection.Open();
                //Write in the output window
                System.Diagnostics.Debug.WriteLine("Sucessful Connection");
                String query = "SELECT * FROM ROLES;";
                //The query is executed
                NpgsqlCommand command = new NpgsqlCommand(query, connection);
                var reader = command.ExecuteReader();
                //The roles are recovered from the reader object
                while (reader.Read())
                {
                    Roles rol = new ConstruTec.Models.Roles();
                    //The values of the attributes are recovered
                    rol.Role_id = Int64.Parse(reader["role_id"].ToString());
                    rol.Role_name = reader["role_name"].ToString();
                    //The rol is added to the list
                    roles.Add(rol);
                }// end of while
                //The connection is closed
                connection.Close();
            }
            catch (Exception e)
            {
                //It is notified in the output that the connection failed 
                System.Diagnostics.Debug.WriteLine("Failed Connection in get_allRoles: " + e.Message);
            }//End catch
            return roles;
        }//End of 



        //####### USERS METHODS #########

        /// <summary>
        /// This method add the user in the ConstruTec database.
        /// </summary>
        /// <param name="user">The user that will be added to the database</param>
        public void crear_Usuario(Usuario user)
        {
            //The object responsible of the connection is created 
            NpgsqlConnection connection = new NpgsqlConnection();
            connection.ConnectionString = connectionString;
            try
            {
                connection.Open();
                System.Diagnostics.Debug.WriteLine("Sucessful Connection");
                String query = "INSERT INTO USUARIO VALUES (@id_number, @code, @name, @phone_number, @role_id);";
                NpgsqlCommand command = new NpgsqlCommand(query, connection);
                //Adds the parameter of the command
                command.Parameters.AddWithValue("@id_number", user.Id_Number);
                command.Parameters.AddWithValue("@code", user.Code);
                command.Parameters.AddWithValue("@name", user.Name);
                command.Parameters.AddWithValue("@phone_number", user.Phone_Number);
                command.Parameters.AddWithValue("@role_id", user.Role_usuario);
                //Executes the command
                command.ExecuteNonQuery();
                connection.Close();
            }
            catch (Exception e)
            {
                //Notifies an error in the output
                System.Diagnostics.Debug.WriteLine("Failed Connection in 'crear_Usuario:' " + e.Message);
            }//End catch
        }//End of post_Usuario


        /// <summary>
        /// This method delete the user whose id attribute is id. 
        /// </summary>
        /// <param name="id">The id of the user to delete.</param>
        public void eliminar_Usuario(long id)
        {
            //The object responsible of the connection is created 
            NpgsqlConnection connection = new NpgsqlConnection();
            connection.ConnectionString = connectionString;
            try
            {
                connection.Open();
                System.Diagnostics.Debug.WriteLine("Sucessful Connection");
                String query = "DELETE FROM USUARIO WHERE id_number = @id;";
                NpgsqlCommand command = new NpgsqlCommand(query, connection);
                command.Parameters.AddWithValue("@id", id);
                command.ExecuteNonQuery();
                connection.Close();
            }
            catch (Exception e)
            {
                //It is notified in the output that the connection failed 
                System.Diagnostics.Debug.WriteLine("Failed Connection in 'delete_Usuario': " + e.Message);
            }//End catch
        }//End of method


        /// <summary>
        /// Method allow to update an attribute of a specific user.
        /// </summary>
        /// <param name="id"> The id of the user that it is wanted to update</param>
        /// <param name="newValue">The new value of the attribute. </param>
        public void update_Usuario(long id, String campo, String newValue)
        {
            //The object responsible of the connection is created 
            NpgsqlConnection connection = new NpgsqlConnection();
            connection.ConnectionString = connectionString;
            try
            {
                connection.Open();
                System.Diagnostics.Debug.WriteLine("Sucessful Connection");
                if (campo.Equals("Code"))
                {
                    String query = "UPDATE USUARIO SET (code) = (@newValue) WHERE id_number = @id;";
                    NpgsqlCommand command = new NpgsqlCommand(query, connection);
                    var valor = newValue;
                    command.Parameters.AddWithValue("@newValue", valor);
                    command.Parameters.AddWithValue("@id", id);
                    //Executes the command
                    command.ExecuteNonQuery();
                }
                else if (campo.Equals("Name"))
                {
                    String query = "UPDATE USUARIO SET (name) = (@newValue) WHERE id_number = @id;";
                    NpgsqlCommand command = new NpgsqlCommand(query, connection);
                    var valor = newValue;
                    command.Parameters.AddWithValue("@newValue", valor);
                    command.Parameters.AddWithValue("@id", id);
                    //Executes the command
                    command.ExecuteNonQuery();
                }
                else if (campo.Equals("Phone_Number"))
                {
                    String query = "UPDATE USUARIO SET (phone_number) = (@newValue) WHERE id_number = @id;";
                    NpgsqlCommand command = new NpgsqlCommand(query, connection);
                    var valor = Int32.Parse(newValue);
                    command.Parameters.AddWithValue("@newValue", valor);
                    command.Parameters.AddWithValue("@id", id);
                    //Executes the command
                    command.ExecuteNonQuery();
                }
               
                connection.Close();
            }
            catch (Exception e)
            {
                //It is notified in the output that the connection failed 
                System.Diagnostics.Debug.WriteLine("Failed Connection in update_Usuario: " + e.Message);
            }//End catch

        }//End od method


        /// <summary>
        /// Recovers the user with the id_user id.
        /// </summary>
        /// <param name="id"></param>
        /// <returns>Return the user who is being requested.</returns>
        public Usuario get_Usuario(long id)
        {
            //The object responsible of the connection is created 
            NpgsqlConnection connection = new NpgsqlConnection();
            connection.ConnectionString = connectionString;
            //The output object is created
            Usuario user = new Usuario();
            try
            {
                connection.Open();
                System.Diagnostics.Debug.WriteLine("Sucessful Connection");
                String query = "SELECT * FROM USUARIO WHERE id_number = @id;";
                NpgsqlCommand command = new NpgsqlCommand(query, connection);
                command.Parameters.AddWithValue("@id", id);
                //The query is executed
                var reader = command.ExecuteReader();
                //The values of the attributes are recovered
                reader.Read();
                user.Id_Number = Int64.Parse(reader["id_number"].ToString());
                user.Code = reader["code"].ToString();
                user.Name = reader["name"].ToString();
                user.Phone_Number = Int32.Parse(reader["phone_number"].ToString());
                user.Role_usuario = Int32.Parse(reader["role_id"].ToString());
                //Close the connection
                connection.Close();
            }
            catch (Exception e)
            {
                //It is notified in the output that the connection failed 
                System.Diagnostics.Debug.WriteLine("Failed Connection in get_Usuarios: " + e.Message);
            }//End catch
            return user;
        }//End of 


        /// <summary>
        /// Method return all the users in the database.
        /// </summary>
        /// <returns>List of user</returns>
        public List<Usuario> get_allUsuario()
        {
            //The output object 
            List<Usuario> list = new List<Usuario>();
            //The object responsible of the connection is created 
            NpgsqlConnection connection = new NpgsqlConnection();
            connection.ConnectionString = connectionString;
            try
            {
                connection.Open();
                //Write in the output window
                System.Diagnostics.Debug.WriteLine("Sucessful Connection");
                String query = "SELECT * FROM USUARIO;";
                //The query is executed
                NpgsqlCommand command = new NpgsqlCommand(query, connection);
                var reader = command.ExecuteReader();
                //The roles are recovered from the reader object
                while (reader.Read())
                {
                    Usuario user = new Usuario();
                    //The values of the attributes are recovered
                    user.Id_Number = Int64.Parse(reader["id_number"].ToString());
                    user.Code = reader["code"].ToString();
                    user.Name = reader["name"].ToString();
                    user.Phone_Number = Int32.Parse(reader["phone_number"].ToString());
                    user.Role_usuario = Int64.Parse(reader["role_id"].ToString());
                    //The rol is added to the list
                    list.Add(user);
                }// end of while
                //The connection is closed
                connection.Close();
            }
            catch (Exception e)
            {
                //It is notified in the output that the connection failed 
                System.Diagnostics.Debug.WriteLine("Failed Connection in get_allUsuarios: " + e.Message);
            }//End catch
            return list;
        }//End of 


        //####### COMMENTARY METHODS #########

        /// <summary>
        /// This method add the commentary in the ConstruTec database.
        /// </summary>
        /// <param name="commentary">The commentary that will be added to the database</param>
        public void crear_Commentary(Commentary commentary)
        {
            //The object responsible of the connection is created 
            NpgsqlConnection connection = new NpgsqlConnection();
            connection.ConnectionString = connectionString;
            try
            {
                connection.Open();
                System.Diagnostics.Debug.WriteLine("Sucessful Connection");
                String query = "SELECT insert_Commentary(@divided_id, @commentary);";
                NpgsqlCommand command = new NpgsqlCommand(query, connection);
                //Adds the parameter of the command
                command.Parameters.AddWithValue("@divided_id", commentary.Divided_Id);
                command.Parameters.AddWithValue("@commentary", commentary.Comentary);

                //Executes the command
                command.ExecuteNonQuery();
                connection.Close();
            }
            catch (Exception e)
            {
                //Notifies an error in the output
                System.Diagnostics.Debug.WriteLine("Failed Connection in 'crear_Commentary': " + e.Message);
            }//End catch
        }//End of post_Usuario


        /// <summary>
        /// This method commentary the user whose id attribute is id. 
        /// </summary>
        /// <param name="id">The id of the commentary to delete.</param>
        public void eliminar_Commentary(long id)
        {
            //The object responsible of the connection is created 
            NpgsqlConnection connection = new NpgsqlConnection();
            connection.ConnectionString = connectionString;
            try
            {
                connection.Open();
                System.Diagnostics.Debug.WriteLine("Sucessful Connection");
                String query = "DELETE FROM COMMENTARY WHERE comment_id = @id;";
                NpgsqlCommand command = new NpgsqlCommand(query, connection);
                command.Parameters.AddWithValue("@id", id);
                command.ExecuteNonQuery();
                connection.Close();
            }
            catch (Exception e)
            {
                //It is notified in the output that the connection failed 
                System.Diagnostics.Debug.WriteLine("Failed Connection in 'eliminar_Commentary': " + e.Message);
            }//End catch
        }//End of method


        /// <summary>
        /// Method allow to update an attribute of a specific commentary.
        /// </summary>
        /// <param name="id"> The id of the commentary that it is wanted to update</param>
        /// <param name="newValue">The new value of the attribute. </param>
        /// <param name="campo">The name of attribute that will be update. </param>
        public void update_Commentary(long id, String campo, String newValue)
        {
            //The object responsible of the connection is created 
            NpgsqlConnection connection = new NpgsqlConnection();
            connection.ConnectionString = connectionString;
            try
            {
                connection.Open();
                System.Diagnostics.Debug.WriteLine("Sucessful Connection");
                
                if (campo.Equals("Commentary"))
                {
                    String query = "UPDATE COMMENTARY SET (commentary) = (@newValue) WHERE comment_id = @id;";
                    NpgsqlCommand command = new NpgsqlCommand(query, connection);
                    
                    var valor = newValue;
                    command.Parameters.AddWithValue("@newValue", valor);
                    command.Parameters.AddWithValue("@id", id);
                    //Executes the command
                    command.ExecuteNonQuery();
                }
                connection.Close();
            }
            catch (Exception e)
            {
                //It is notified in the output that the connection failed 
                System.Diagnostics.Debug.WriteLine("Failed Connection in update_Commentary: " + e.Message);
            }//End catch

        }//End od method


        /// <summary>
        /// Recovers the commentary with the comment_id id.
        /// </summary>
        /// <param name="id"></param>
        /// <returns>Return the commentary who is being requested.</returns>
        public Commentary get_Commentary(long id)
        {
            //The object responsible of the connection is created 
            NpgsqlConnection connection = new NpgsqlConnection();
            connection.ConnectionString = connectionString;
            //The output object is created
            Commentary commentary = new Commentary();
            try
            {
                connection.Open();
                System.Diagnostics.Debug.WriteLine("Sucessful Connection");
                String query = "SELECT * FROM COMMENTARY WHERE comment_id = @id;";
                NpgsqlCommand command = new NpgsqlCommand(query, connection);
                command.Parameters.AddWithValue("@id", id);
                //The query is executed
                var reader = command.ExecuteReader();
                //The values of the attributes are recovered
                reader.Read();
                commentary.Comment_Id = Int64.Parse(reader["comment_id"].ToString());
                commentary.Divided_Id = Int64.Parse(reader["divided_id"].ToString());
                commentary.Comentary = reader["commentary"].ToString();
                //Close the connection
                connection.Close();
            }
            catch (Exception e)
            {
                //It is notified in the output that the connection failed 
                System.Diagnostics.Debug.WriteLine("Failed Connection in get_Commentary: " + e.Message);
            }//End catch
            return commentary;
        }//End of method    


        /// <summary>
        /// Method return all the commentaries in the database.
        /// </summary>
        /// <returns>List of commentaries</returns>
        public List<Commentary> get_allCommentary()
        {
            //The output object 
            List<Commentary> list = new List<Commentary>();
            //The object responsible of the connection is created 
            NpgsqlConnection connection = new NpgsqlConnection();
            connection.ConnectionString = connectionString;
            try
            {
                connection.Open();
                //Write in the output window
                System.Diagnostics.Debug.WriteLine("Sucessful Connection");
                String query = "SELECT * FROM COMMENTARY;";
                //The query is executed
                NpgsqlCommand command = new NpgsqlCommand(query, connection);
                var reader = command.ExecuteReader();
                //The roles are recovered from the reader object
                while (reader.Read())
                {
                    Commentary commentary = new Commentary();
                    //The values of the attributes are recovered
                    commentary.Comment_Id = Int64.Parse(reader["comment_id"].ToString());
                    commentary.Divided_Id = Int64.Parse(reader["divided_id"].ToString());
                    commentary.Comentary = reader["commentary"].ToString();
                    //The rol is added to the list
                    list.Add(commentary);
                }// end of while
                 //The connection is closed
                connection.Close();
            }
            catch (Exception e)
            {
                //It is notified in the output that the connection failed 
                System.Diagnostics.Debug.WriteLine("Failed Connection in get_allCommentaries: " + e.Message);
            }//End catch
            return list;
        }//End of  




        //####### DIVIDED_IN METHODS #########

        /// <summary>
        /// This method add the divided_in in the ConstruTec database.
        /// </summary>
        /// <param name="divided_in">The divided_in that will be added to the database</param>
        public void crear_Divided_in(Divided_in div)
        {
            //The object responsible of the connection is created 
            NpgsqlConnection connection = new NpgsqlConnection();
            connection.ConnectionString = connectionString;
            try
            {
                connection.Open();
                System.Diagnostics.Debug.WriteLine("Sucessful Connection");
                String query = "SELECT insert_Divided_in(@id_project, @stage_id, @start_date, @end_date, @status);";
                NpgsqlCommand command = new NpgsqlCommand(query, connection);
                //Adds the parameter of the command
                command.Parameters.AddWithValue("@id_project", div.Id_Project);
                command.Parameters.AddWithValue("@stage_id", div.Stage_Id);
                command.Parameters.AddWithValue("@start_date", NpgsqlTypes.NpgsqlDate.Parse(div.Start_Date));
                command.Parameters.AddWithValue("@end_date", NpgsqlTypes.NpgsqlDate.Parse(div.End_Date));
                command.Parameters.AddWithValue("@status", div.Status);
                //Executes the command
                command.ExecuteNonQuery();
                connection.Close();
            }
            catch (Exception e)
            {
                //Notifies an error in the output
                System.Diagnostics.Debug.WriteLine("Failed Connection in 'crear_Commentary': " + e.Message);
            }//End catch
        }//End of post_Usuario 


        /// <summary>
        /// This method divided_in the user whose id attribute is id. 
        /// </summary>
        /// <param name="id">The id of the divided_in to delete.</param>
        public void eliminar_Divided_in(long id)
        {
            //The object responsible of the connection is created 
            NpgsqlConnection connection = new NpgsqlConnection();
            connection.ConnectionString = connectionString;
            try
            {
                connection.Open();
                System.Diagnostics.Debug.WriteLine("Sucessful Connection");
                String query = "DELETE FROM DIVIDED_IN WHERE divided_id = @id;";
                NpgsqlCommand command = new NpgsqlCommand(query, connection);
                command.Parameters.AddWithValue("@id", id);
                command.ExecuteNonQuery();
                connection.Close();
            }
            catch (Exception e)
            {
                //It is notified in the output that the connection failed 
                System.Diagnostics.Debug.WriteLine("Failed Connection in 'eliminar_Divided_in': " + e.Message);
            }//End catch
        }//End of method


        /// <summary>
        /// Method allow to update an attribute of a specific divided_in.
        /// </summary>
        /// <param name="id"> The id of the divided_in that it is wanted to update</param>
        /// <param name="newValue">The new value of the attribute. </param>
        /// <param name="campo">The name of attribute that will be update. </param>
        public void update_Divided_in(long id, String campo, String newValue)
        {
            //The object responsible of the connection is created 
            NpgsqlConnection connection = new NpgsqlConnection();
            connection.ConnectionString = connectionString;
            try
            {
                connection.Open();
                System.Diagnostics.Debug.WriteLine("Sucessful Connection");

                if (campo.Equals("Start_Date"))
                {
                    String query = "UPDATE DIVIDED_IN SET (start_date) = (@newValue) WHERE divided_id = @id;";
                    NpgsqlCommand command = new NpgsqlCommand(query, connection);
                    var valor = NpgsqlTypes.NpgsqlDate.Parse(newValue);
                    command.Parameters.AddWithValue("@newValue", NpgsqlTypes.NpgsqlDate.Parse(newValue));
                    command.Parameters.AddWithValue("@id", id);
                    //Executes the command
                    command.ExecuteNonQuery();

                }
                else if (campo.Equals("End_Date"))
                {
                    String query = "UPDATE DIVIDED_IN SET (end_date) = (@newValue) WHERE divided_id = @id;";
                    NpgsqlCommand command = new NpgsqlCommand(query, connection);
                    var valor = NpgsqlTypes.NpgsqlDate.Parse(newValue);
                    command.Parameters.AddWithValue("@newValue", NpgsqlTypes.NpgsqlDate.Parse(newValue));
                    command.Parameters.AddWithValue("@id", id);
                    //Executes the command
                    command.ExecuteNonQuery();
                }
                else if (campo.Equals("Status"))
                {
                    String query = "UPDATE DIVIDED_IN SET (status) = (@newValue) WHERE divided_id = @id;";
                    NpgsqlCommand command = new NpgsqlCommand(query, connection);
                    var valor = Boolean.Parse(newValue);
                    command.Parameters.AddWithValue("@newValue", valor);
                    command.Parameters.AddWithValue("@id", id);
                    //Executes the command
                    command.ExecuteNonQuery();
                }
                connection.Close();
            }
            catch (Exception e)
            {
                //It is notified in the output that the connection failed 
                System.Diagnostics.Debug.WriteLine("Failed Connection in update_Divided_in: " + e.Message);
            }//End catch

        }//End od method


        /// <summary>
        /// Recovers the divided_in with the divided_id id.
        /// </summary>
        /// <param name="id"></param>
        /// <returns>Return the divided_in who is being requested.</returns>
        public Divided_in get_Divided_in(long id)
        {
            //The object responsible of the connection is created 
            NpgsqlConnection connection = new NpgsqlConnection();
            connection.ConnectionString = connectionString;
            //The output object is created
            Divided_in div = new Divided_in();
            try
            {
                connection.Open();
                System.Diagnostics.Debug.WriteLine("Sucessful Connection");
                String query = "SELECT * FROM DIVIDED_IN WHERE divided_id = @id;";
                NpgsqlCommand command = new NpgsqlCommand(query, connection);
                command.Parameters.AddWithValue("@id", id);
                //The query is executed
                var reader = command.ExecuteReader();
                //The values of the attributes are recovered
                reader.Read();
                div.Divided_Id = Int64.Parse(reader["divided_id"].ToString());
                div.Id_Project = Int64.Parse(reader["id_project"].ToString());
                div.Stage_Id = Int64.Parse(reader["stage_id"].ToString());
                NpgsqlTypes.NpgsqlDate start_date = reader.GetDate(3);
                NpgsqlTypes.NpgsqlDate end_date = reader.GetDate(4);
                div.Start_Date = start_date.ToString();
                div.End_Date = end_date.ToString();
                div.Status = Boolean.Parse(reader["status"].ToString());

                System.Diagnostics.Debug.WriteLine("DATEEE " + div.Start_Date.ToString());
   
                //Close the connection
                connection.Close();
            }
            catch (Exception e)
            {
                //It is notified in the output that the connection failed 
                System.Diagnostics.Debug.WriteLine("Failed Connection in get_Divided_in: " + e.Message);
            }//End catch
            return div;
        }//End of method 


        /// <summary>
        /// Method return all the divided_in in the database.
        /// </summary>
        /// <returns>List of divided_in</returns>
        public List<Divided_in> get_allDivided_in()
        {
            //The output object 
            List<Divided_in> list = new List<Divided_in>();
            //The object responsible of the connection is created 
            NpgsqlConnection connection = new NpgsqlConnection();
            connection.ConnectionString = connectionString;
            try
            {
                connection.Open();
                //Write in the output window
                System.Diagnostics.Debug.WriteLine("Sucessful Connection");
                String query = "SELECT * FROM DIVIDED_IN;";
                //The query is executed
                NpgsqlCommand command = new NpgsqlCommand(query, connection);
                var reader = command.ExecuteReader();
                //The roles are recovered from the reader object
                while (reader.Read())
                {
                    Divided_in div = new Divided_in();
                    //The values of the attributes are recovered
                    div.Divided_Id = Int64.Parse(reader["divided_id"].ToString());
                    div.Id_Project = Int64.Parse(reader["id_project"].ToString());
                    div.Stage_Id = Int64.Parse(reader["stage_id"].ToString());
                    NpgsqlTypes.NpgsqlDate start_date = reader.GetDate(3);
                    NpgsqlTypes.NpgsqlDate end_date = reader.GetDate(4);
                    div.Start_Date = start_date.ToString();
                    div.End_Date = end_date.ToString();
                    div.Status = Boolean.Parse(reader["status"].ToString());
                    //The div is added to the list
                    list.Add(div);
                }// end of while
                //The connection is closed
                connection.Close();
            }
            catch (Exception e)
            {
                //It is notified in the output that the connection failed 
                System.Diagnostics.Debug.WriteLine("Failed Connection in get_allDivided_in: " + e.Message);
            }//End catch
            return list;
        }//End of


        /// <summary>
        /// This method return the divided_in associated to a specific project
        /// </summary>
        /// <param name="id_project">The id of the project of interest</param>
        /// <returns>Return a list with the divided_in</returns>
        public List<Divided_in> get_Divided_By_Project(long id_project)
        {
            //The output object 
            List<Divided_in> list = new List<Divided_in>();
            //The object responsible of the connection is created 
            NpgsqlConnection connection = new NpgsqlConnection();
            connection.ConnectionString = connectionString;
            try
            {
                connection.Open();
                //Write in the output window
                System.Diagnostics.Debug.WriteLine("Sucessful Connection");
                String query = "SELECT * FROM DIVIDED_IN WHERE ID_PROJECT = @id;";
                //The query is executed
                NpgsqlCommand command = new NpgsqlCommand(query, connection);
                //The id is added to the command
                command.Parameters.AddWithValue("@id", id_project);
                //Executes the command
                var reader = command.ExecuteReader();
                //The roles are recovered from the reader object
                while (reader.Read())
                {
                    Divided_in div = new Divided_in();
                    //The values of the attributes are recovered
                    div.Divided_Id = Int64.Parse(reader["divided_id"].ToString());
                    div.Id_Project = Int64.Parse(reader["id_project"].ToString());
                    div.Stage_Id = Int64.Parse(reader["stage_id"].ToString());
                    NpgsqlTypes.NpgsqlDate start_date = reader.GetDate(3);
                    NpgsqlTypes.NpgsqlDate end_date = reader.GetDate(4);
                    div.Start_Date = start_date.ToString();
                    div.End_Date = end_date.ToString();
                    div.Status = Boolean.Parse(reader["status"].ToString());
                    //The div is added to the list
                    list.Add(div);
                }// end of while
                //The connection is closed
                connection.Close();
            }
            catch (Exception e)
            {
                //It is notified in the output that the connection failed 
                System.Diagnostics.Debug.WriteLine("Failed Connection in get_allDivided_in: " + e.Message);
            }//End catch
            return list;
        }//End of the method


        //#######  MATERIAL METHODS #########

        /// <summary>
        /// This method add the material in the ConstruTec database.
        /// </summary>
        /// <param name="posseses">The material that will be added to the database</param>
        public void crear_Material(Material material)
        {
            //The object responsible of the connection is created 
            NpgsqlConnection connection = new NpgsqlConnection();
            connection.ConnectionString = connectionString;
            try
            {
                connection.Open();
                System.Diagnostics.Debug.WriteLine("Sucessful Connection");
                String query = "SELECT insert_Material(@name, @price, @description);";
                NpgsqlCommand command = new NpgsqlCommand(query, connection);
                //Adds the parameter of the command
                command.Parameters.AddWithValue("@name", material.Name);
                command.Parameters.AddWithValue("@price", material.Price);
                command.Parameters.AddWithValue("@description", material.Description);

                //Executes the command
                command.ExecuteNonQuery();
                connection.Close();
            }
            catch (Exception e)
            {
                //Notifies an error in the output
                System.Diagnostics.Debug.WriteLine("Failed Connection in 'crear_Material': " + e.Message);
            }//End catch
        }//End of post_Usuario 


        /// <summary>
        /// This method material the user whose id attribute is id. 
        /// </summary>
        /// <param name="id">The id of the material to delete.</param>
        public void eliminar_Material(long id)
        {
            //The object responsible of the connection is created 
            NpgsqlConnection connection = new NpgsqlConnection();
            connection.ConnectionString = connectionString;
            try
            {
                connection.Open();
                System.Diagnostics.Debug.WriteLine("Sucessful Connection");
                String query = "DELETE FROM MATERIAL WHERE id_material = @id;";
                NpgsqlCommand command = new NpgsqlCommand(query, connection);
                command.Parameters.AddWithValue("@id", id);
                command.ExecuteNonQuery();
                connection.Close();
            }
            catch (Exception e)
            {
                //It is notified in the output that the connection failed 
                System.Diagnostics.Debug.WriteLine("Failed Connection in 'eliminar_Material': " + e.Message);
            }//End catch
        }//End of method

        /// <summary>
        /// Method allow to update an attribute of a specific Material.
        /// </summary>
        /// <param name="id"> The id of the Material that it is wanted to update</param>
        /// <param name="newValue">The new value of the attribute. </param>
        /// <param name="campo">The name of attribute that will be update. </param>
        public void update_Material(long id, String campo, String newValue)
        {
            //The object responsible of the connection is created 
            NpgsqlConnection connection = new NpgsqlConnection();
            connection.ConnectionString = connectionString;
            try
            {
                connection.Open();
                System.Diagnostics.Debug.WriteLine("Sucessful Connection");
                if (campo.Equals("Price"))
                {
                    String query = "UPDATE MATERIAL SET (price) = (@newValue) WHERE id_material = @id;";
                    NpgsqlCommand command = new NpgsqlCommand(query, connection);
                    var valor = Int32.Parse(newValue);
                    command.Parameters.AddWithValue("@newValue", valor);
                    command.Parameters.AddWithValue("@id", id);
                    //Executes the command
                    command.ExecuteNonQuery();
                }
                else if (campo.Equals("Description"))
                {
                    String query = "UPDATE MATERIAL SET (description) = (@newValue) WHERE id_material = @id;";
                    NpgsqlCommand command = new NpgsqlCommand(query, connection);
                    var valor = newValue;
                    command.Parameters.AddWithValue("@newValue", valor);
                    command.Parameters.AddWithValue("@id", id);
                    //Executes the command
                    command.ExecuteNonQuery();
                }
                connection.Close();
            }
            catch (Exception e)
            {
                //It is notified in the output that the connection failed 
                System.Diagnostics.Debug.WriteLine("Failed Connection in update_Material: " + e.Message);
            }//End catch

        }//End od method


        /// <summary>
        /// Recovers the material with the material_id id
        /// </summary>
        /// <param name="id"></param>
        /// <returns>Return the material who is being requested.</returns>
        public Material get_Material(long id)
        {
            //The object responsible of the connection is created 
            NpgsqlConnection connection = new NpgsqlConnection();
            connection.ConnectionString = connectionString;
            //The output object is created
            Material material = new Material();
            try
            {
                connection.Open();
                System.Diagnostics.Debug.WriteLine("Sucessful Connection");
                String query = "SELECT * FROM MATERIAL WHERE id_material = @id;";
                NpgsqlCommand command = new NpgsqlCommand(query, connection);
                command.Parameters.AddWithValue("@id", id);
                //The query is executed
                var reader = command.ExecuteReader();
                //The values of the attributes are recovered
                reader.Read();
                material.Id_Material = Int64.Parse(reader["id_material"].ToString());
                material.Name = reader["name"].ToString();
                material.Price = Int32.Parse(reader["price"].ToString());
                material.Description = reader["description"].ToString();
                //Close the connection
                connection.Close();
            }
            catch (Exception e)
            {
                //It is notified in the output that the connection failed 
                System.Diagnostics.Debug.WriteLine("Failed Connection in get_Material: " + e.Message);
            }//End catch
            return material;
        }//End of method 


        /// <summary>
        /// Method return all the material in the database.
        /// </summary>
        /// <returns>List of material</returns>
        public List<Material> get_allMaterial()
        {
            //The output object 
            List<Material> list = new List<Material>();
            //The object responsible of the connection is created 
            NpgsqlConnection connection = new NpgsqlConnection();
            connection.ConnectionString = connectionString;
            try
            {
                connection.Open();
                //Write in the output window
                System.Diagnostics.Debug.WriteLine("Sucessful Connection");
                String query = "SELECT * FROM MATERIAL;";
                //The query is executed
                NpgsqlCommand command = new NpgsqlCommand(query, connection);
                var reader = command.ExecuteReader();
                //The roles are recovered from the reader object
                while (reader.Read())
                {
                    Material material = new Material();
                    //The values of the attributes are recovered
                    material.Id_Material = Int64.Parse(reader["id_material"].ToString());
                    material.Name = reader["name"].ToString();
                    material.Price = Int32.Parse(reader["price"].ToString());
                    material.Description = reader["description"].ToString();
                    //The rol is added to the list
                    list.Add(material);
                }// end of while
                //The connection is closed
                connection.Close();
            }
            catch (Exception e)
            {
                //It is notified in the output that the connection failed 
                System.Diagnostics.Debug.WriteLine("Failed Connection in get_allMaterial: " + e.Message);
            }//End catch
            return list;
        }//End of


        //#######  POSSESES METHODS #########

        /// <summary>
        /// This method add the posseses in the ConstruTec database.
        /// </summary>
        /// <param name="posseses">The posseses that will be added to the database</param>
        public void crear_Posseses(Posseses posseses)
        {
            //The object responsible of the connection is created 
            NpgsqlConnection connection = new NpgsqlConnection();
            connection.ConnectionString = connectionString;
            try
            {
                connection.Open();
                System.Diagnostics.Debug.WriteLine("Sucessful Connection");
                String query = "SELECT insert_Posseses(@id_material, @divided_id, @quantity);";
                NpgsqlCommand command = new NpgsqlCommand(query, connection);
                //Adds the parameter of the command
                command.Parameters.AddWithValue("@id_material", posseses.Id_Material);
                command.Parameters.AddWithValue("@divided_id", posseses.Divided_Id);
                command.Parameters.AddWithValue("@quantity", posseses.Quantity);

                //Executes the command
                command.ExecuteNonQuery();
                connection.Close();
            }
            catch (Exception e)
            {
                //Notifies an error in the output
                System.Diagnostics.Debug.WriteLine("Failed Connection in 'crear_Posseses': " + e.Message);
            }//End catch
        }//End of post_Usuario 


        /// <summary>
        /// This method posseses the user whose id attribute is id. 
        /// </summary>
        /// <param name="id">The id of the posseses to delete.</param>
        public void eliminar_Posseses(long id)
        {
            //The object responsible of the connection is created 
            NpgsqlConnection connection = new NpgsqlConnection();
            connection.ConnectionString = connectionString;
            try
            {
                connection.Open();
                System.Diagnostics.Debug.WriteLine("Sucessful Connection");
                String query = "DELETE FROM POSSESES WHERE posseses_id = @id;";
                NpgsqlCommand command = new NpgsqlCommand(query, connection);
                command.Parameters.AddWithValue("@id", id);
                //Executes the command
                command.ExecuteNonQuery();
                connection.Close();
            }
            catch (Exception e)
            {
                //It is notified in the output that the connection failed 
                System.Diagnostics.Debug.WriteLine("Failed Connection in 'eliminar_Posseses': " + e.Message);
            }//End catch
        }//End of method


        /// <summary>
        /// Method allow to update an attribute of a specific posseses.
        /// </summary>
        /// <param name="id"> The id of the posseses that it is wanted to update</param>
        /// <param name="newValue">The new value of the attribute. </param>
        /// <param name="campo">The name of attribute that will be update. </param>
        public void update_Posseses(long id, String campo, String newValue)
        {
            //The object responsible of the connection is created 
            NpgsqlConnection connection = new NpgsqlConnection();
            connection.ConnectionString = connectionString;
            try
            {
                connection.Open();
                System.Diagnostics.Debug.WriteLine("Sucessful Connection");

                if (campo.Equals("Quantity"))
                {
                    String query = "UPDATE Posseses SET (quantity) = (@newValue) WHERE posseses_id = @id;";
                    NpgsqlCommand command = new NpgsqlCommand(query, connection);
                    var valor = Int32.Parse(newValue);
                    command.Parameters.AddWithValue("@newValue", valor);
                    command.Parameters.AddWithValue("@id", id);
                    //Executes the command
                    command.ExecuteNonQuery();
                }
                connection.Close();
            }
            catch (Exception e)
            {
                //It is notified in the output that the connection failed 
                System.Diagnostics.Debug.WriteLine("Failed Connection in update_Posseses: " + e.Message);
            }//End catch

        }//End od method


        /// <summary>
        /// Recovers the posseses with the posseses_id id
        /// </summary>
        /// <param name="id"></param>
        /// <returns>Return the posseses who is being requested.</returns>
        public Posseses get_Posseses(long id)
        {
            //The object responsible of the connection is created 
            NpgsqlConnection connection = new NpgsqlConnection();
            connection.ConnectionString = connectionString;
            //The output object is created
            Posseses posseses = new Posseses();
            try
            {
                connection.Open();
                System.Diagnostics.Debug.WriteLine("Sucessful Connection");
                String query = "SELECT * FROM POSSESES WHERE posseses_id = @id;";
                NpgsqlCommand command = new NpgsqlCommand(query, connection);
                command.Parameters.AddWithValue("@id", id);
                //The query is executed
                var reader = command.ExecuteReader();
                //The values of the attributes are recovered
                reader.Read();
                posseses.Posseses_Id = Int64.Parse(reader["posseses_id"].ToString());
                posseses.Id_Material = Int64.Parse(reader["id_material"].ToString());
                posseses.Divided_Id = Int64.Parse(reader["divided_id"].ToString());
                posseses.Quantity = Int32.Parse(reader["quantity"].ToString());
                //Close the connection
                connection.Close();
            }
            catch (Exception e)
            {
                //It is notified in the output that the connection failed 
                System.Diagnostics.Debug.WriteLine("Failed Connection in get_Material: " + e.Message);
            }//End catch
            return posseses;
        }//End of method 


        /// <summary>
        /// Method return all the posseses in the database.
        /// </summary>
        /// <returns>List of posseses</returns>
        public List<Posseses> get_allPosseses()
        {
            //The output object 
            List<Posseses> list = new List<Posseses>();
            //The object responsible of the connection is created 
            NpgsqlConnection connection = new NpgsqlConnection();
            connection.ConnectionString = connectionString;
            try
            {
                connection.Open();
                //Write in the output window
                System.Diagnostics.Debug.WriteLine("Sucessful Connection");
                String query = "SELECT * FROM POSSESES;";
                //The query is executed
                NpgsqlCommand command = new NpgsqlCommand(query, connection);
                var reader = command.ExecuteReader();
                //The roles are recovered from the reader object
                while (reader.Read())
                {
                    Posseses posseses = new Posseses();
                    //The values of the attributes are recovered
                    posseses.Posseses_Id = Int64.Parse(reader["posseses_id"].ToString());
                    posseses.Id_Material = Int64.Parse(reader["id_material"].ToString());
                    posseses.Divided_Id = Int64.Parse(reader["divided_id"].ToString());
                    posseses.Quantity = Int32.Parse(reader["quantity"].ToString());
                    //The object is added to the list
                    list.Add(posseses);
                }// end of while
                //The connection is closed
                connection.Close();
            }
            catch (Exception e)
            {
                //It is notified in the output that the connection failed 
                System.Diagnostics.Debug.WriteLine("Failed Connection in get_allPosseses: " + e.Message);
            }//End catch
            return list;
        }//End of

        /// <summary>
        /// Return the Posseses associated to a divided_in.
        /// </summary>
        /// <param name="divided_id">The id of the divided of interest</param>
        /// <returns>The list of posseses</returns>
        public List<Posseses> get_Posseses_By_Divided(long divided_id)
        {
            //The output object 
            List<Posseses> list = new List<Posseses>();
            //The object responsible of the connection is created 
            NpgsqlConnection connection = new NpgsqlConnection();
            connection.ConnectionString = connectionString;
            try
            {
                connection.Open();
                //Write in the output window
                System.Diagnostics.Debug.WriteLine("Sucessful Connection");
                String query = "SELECT * FROM POSSESES WHERE divided_id = @id;";
                //The query is executed
                NpgsqlCommand command = new NpgsqlCommand(query, connection);
                //Adds the parameter and executes the command
                command.Parameters.AddWithValue("@id", divided_id);
                var reader = command.ExecuteReader();
                //The roles are recovered from the reader object
                while (reader.Read())
                {
                    Posseses posseses = new Posseses();
                    //The values of the attributes are recovered
                    posseses.Posseses_Id = Int64.Parse(reader["posseses_id"].ToString());
                    posseses.Id_Material = Int64.Parse(reader["id_material"].ToString());
                    posseses.Divided_Id = Int64.Parse(reader["divided_id"].ToString());
                    posseses.Quantity = Int32.Parse(reader["quantity"].ToString());
                    //The object is added to the list
                    list.Add(posseses);
                }// end of while
                //The connection is closed
                connection.Close();
            }
            catch (Exception e)
            {
                //It is notified in the output that the connection failed 
                System.Diagnostics.Debug.WriteLine("Failed Connection in get_allPosseses: " + e.Message);
            }//End catch
            return list;

        }//End of the method

        //#######  PROJECT METHODS #########

        /// <summary>
        /// This method add the project in the ConstruTec database.
        /// </summary>
        /// <param name="project">The project that will be added to the database</param>
        public void crear_Project(Project project)
        {
            //The object responsible of the connection is created 
            NpgsqlConnection connection = new NpgsqlConnection();
            connection.ConnectionString = connectionString;
            try
            {
                connection.Open();
                System.Diagnostics.Debug.WriteLine("Sucessful Connection");
                String query = "SELECT insert_Project(@id_client, @id_engineer, @location, @name);";
                NpgsqlCommand command = new NpgsqlCommand(query, connection);
                //Adds the parameter of the command
                command.Parameters.AddWithValue("@id_client", project.Id_Client);
                command.Parameters.AddWithValue("@id_engineer", project.Id_Enginner);
                command.Parameters.AddWithValue("@location", project.Location);
                command.Parameters.AddWithValue("@name", project.Name);

                //Executes the command
                command.ExecuteNonQuery();
                connection.Close();
            }
            catch (Exception e)
            {
                //Notifies an error in the output
                System.Diagnostics.Debug.WriteLine("Failed Connection in 'crear_Project': " + e.Message);
            }//End catch
        }//End of post_Usuario 


        /// <summary>
        /// This method project the user whose id attribute is id. 
        /// </summary>
        /// <param name="id">The id of the project to delete.</param>
        public void eliminar_Project(long id)
        {
            //The object responsible of the connection is created 
            NpgsqlConnection connection = new NpgsqlConnection();
            connection.ConnectionString = connectionString;
            try
            {
                connection.Open();
                System.Diagnostics.Debug.WriteLine("Sucessful Connection");
                String query = "DELETE FROM PROJECT WHERE id_project = @id;";
                NpgsqlCommand command = new NpgsqlCommand(query, connection);
                command.Parameters.AddWithValue("@id", id);
                //Executes the command
                command.ExecuteNonQuery();
                connection.Close();
            }
            catch (Exception e)
            {
                //It is notified in the output that the connection failed 
                System.Diagnostics.Debug.WriteLine("Failed Connection in 'eliminar_Project': " + e.Message);
            }//End catch
        }//End of method


        /// <summary>
        /// Method allow to update an attribute of a specific project.
        /// </summary>
        /// <param name="id"> The id of the project that it is wanted to update</param>
        /// <param name="newValue">The new value of the attribute. </param>
        /// <param name="campo">The name of attribute that will be update. </param>
        public void update_Project(long id, String campo, String newValue)
        {
            //The object responsible of the connection is created 
            NpgsqlConnection connection = new NpgsqlConnection();
            connection.ConnectionString = connectionString;
            try
            {
                connection.Open();
                System.Diagnostics.Debug.WriteLine("Sucessful Connection");

                if (campo.Equals("Location"))
                {
                    String query = "UPDATE Project SET (location) = (@newValue) WHERE id_project = @id;";
                    NpgsqlCommand command = new NpgsqlCommand(query, connection);
                    var valor = newValue;
                    command.Parameters.AddWithValue("@newValue", valor);
                    command.Parameters.AddWithValue("@id", id);
                    //Executes the command
                    command.ExecuteNonQuery();
                }
                else if (campo.Equals("Name"))
                {
                    String query = "UPDATE Project SET (name) = (@newValue) WHERE id_project = @id;";
                    NpgsqlCommand command = new NpgsqlCommand(query, connection);
                    var valor = newValue;
                    command.Parameters.AddWithValue("@newValue", valor);
                    command.Parameters.AddWithValue("@id", id);
                    //Executes the command
                    command.ExecuteNonQuery();
                }
                connection.Close();
            }
            catch (Exception e)
            {
                //It is notified in the output that the connection failed 
                System.Diagnostics.Debug.WriteLine("Failed Connection in update_Project: " + e.Message);
            }//End catch

        }//End od method


        /// <summary>
        /// Recovers the project with whose id_project is id
        /// </summary>
        /// <param name="id"></param>
        /// <returns>Return the project who is being requested.</returns>
        public Project get_Project(long id)
        {
            //The object responsible of the connection is created 
            NpgsqlConnection connection = new NpgsqlConnection();
            connection.ConnectionString = connectionString;
            //The output object is created
            Project project = new Project();
            try
            {
                connection.Open();
                System.Diagnostics.Debug.WriteLine("Sucessful Connection");
                String query = "SELECT * FROM PROJECT WHERE id_project = @id;";
                NpgsqlCommand command = new NpgsqlCommand(query, connection);
                command.Parameters.AddWithValue("@id", id);
                //The query is executed
                var reader = command.ExecuteReader();
                //The values of the attributes are recovered
                reader.Read();
                project.Id_Proyect = Int64.Parse(reader["id_project"].ToString());
                project.Id_Client = Int64.Parse(reader["id_client"].ToString());
                project.Id_Enginner = Int64.Parse(reader["id_engineer"].ToString());
                project.Location = reader["location"].ToString();
                project.Name = reader["name"].ToString();
                //Close the connection
                connection.Close();
            }
            catch (Exception e)
            {
                //It is notified in the output that the connection failed 
                System.Diagnostics.Debug.WriteLine("Failed Connection in get_Material: " + e.Message);
            }//End catch
            return project;
        }//End of method 


        /// <summary>
        /// Method return all the projects in the database ConstruTec.
        /// </summary>
        /// <returns>List of projects</returns>
        public List<Project> get_allProject()
        {
            //The output object 
            List<Project> list = new List<Project>();
            //The object responsible of the connection is created 
            NpgsqlConnection connection = new NpgsqlConnection();
            connection.ConnectionString = connectionString;
            try
            {
                connection.Open();
                //Write in the output window
                System.Diagnostics.Debug.WriteLine("Sucessful Connection");
                String query = "SELECT * FROM PROJECT;";
                //The query is executed
                NpgsqlCommand command = new NpgsqlCommand(query, connection);
                var reader = command.ExecuteReader();
                //The roles are recovered from the reader object
                while (reader.Read())
                {
                    Project project = new Project();
                    //The values of the attributes are recovered
                    project.Id_Proyect = Int64.Parse(reader["id_project"].ToString());
                    project.Id_Client = Int64.Parse(reader["id_client"].ToString());
                    project.Id_Enginner = Int64.Parse(reader["id_engineer"].ToString());
                    project.Location = reader["location"].ToString();
                    project.Name = reader["name"].ToString();
                    //The object is added to the list
                    list.Add(project);
                }// end of while
                //The connection is closed
                connection.Close();
            }
            catch (Exception e)
            {
                //It is notified in the output that the connection failed 
                System.Diagnostics.Debug.WriteLine("Failed Connection in get_allProject: " + e.Message);
            }//End catch
            return list;
        }//End of


        /// <summary>
        /// This method recovers all the projects that belong to a specific user
        /// </summary>
        /// <param name="id_cliente">The id of the client whose projects they are wanted recovered</param>
        /// <returns>Return the list of the projects of the client</returns>
        public List<Project> get_Project_By_Client(long id_cliente)
        {
            //The output object 
            List<Project> list = new List<Project>();
            //The object responsible of the connection is created 
            NpgsqlConnection connection = new NpgsqlConnection();
            connection.ConnectionString = connectionString;
            try
            {
                connection.Open();
                //Write in the output window
                System.Diagnostics.Debug.WriteLine("Sucessful Connection");
                String query = "SELECT * FROM PROJECT WHERE id_client = @id;";
                //The query is executed
                NpgsqlCommand command = new NpgsqlCommand(query, connection);
                //Adds the id to the query
                command.Parameters.AddWithValue("@id", id_cliente);
                //Executes the command
                var reader = command.ExecuteReader();
                //The roles are recovered from the reader object
                while (reader.Read())
                {
                    Project project = new Project();
                    //The values of the attributes are recovered
                    project.Id_Proyect = Int64.Parse(reader["id_project"].ToString());
                    project.Id_Client = Int64.Parse(reader["id_client"].ToString());
                    project.Id_Enginner = Int64.Parse(reader["id_engineer"].ToString());
                    project.Location = reader["location"].ToString();
                    project.Name = reader["name"].ToString();
                    //The object is added to the list
                    list.Add(project);
                }// end of while
                //The connection is closed
                connection.Close();
            }
            catch (Exception e)
            {
                //It is notified in the output that the connection failed 
                System.Diagnostics.Debug.WriteLine("Failed Connection in get_allProjectClient: " + e.Message);
            }//End catch
            return list;
        }//End of the method


        /// <summary>
        /// Returns all projects that have a stage that begins on the next 15 days
        /// </summary>
        /// <returns>List of projects</returns>
        public List<Project> nextProject()
        {
            //The output object 
            List<Project> list = new List<Project>();
            //The object responsible of the connection is created 
            NpgsqlConnection connection = new NpgsqlConnection();
            connection.ConnectionString = connectionString;
            try
            {
                connection.Open();
                //Write in the output window
                System.Diagnostics.Debug.WriteLine("Sucessful Connection");
                String query = "SELECT nextProject();";
                //The query is executed
                NpgsqlCommand command = new NpgsqlCommand(query, connection);
                var reader = command.ExecuteReader();
                //The roles are recovered from the reader object
                while (reader.Read())
                {
                    Project project = new Project();
                    //The values of the attributes are recovered
                    project.Id_Proyect = Int64.Parse(reader["id_project"].ToString());
                    project.Id_Client = Int64.Parse(reader["id_client"].ToString());
                    project.Id_Enginner = Int64.Parse(reader["id_engineer"].ToString());
                    project.Location = reader["location"].ToString();
                    project.Name = reader["name"].ToString();
                    //The object is added to the list
                    list.Add(project);
                }// end of while
                //The connection is closed
                connection.Close();
            }
            catch (Exception e)
            {
                //It is notified in the output that the connection failed 
                System.Diagnostics.Debug.WriteLine("Failed Connection in get_allProject: " + e.Message);
            }//End catch
            return list;

        }//End of the method

        //#######  STAGE METHODS #########

        /// <summary>
        /// This method add the stage in the ConstruTec database.
        /// </summary>
        /// <param name="stage">The stage that will be added to the database</param>
        public void crear_Stage(Stage stage)
        {
            //The object responsible of the connection is created 
            NpgsqlConnection connection = new NpgsqlConnection();
            connection.ConnectionString = connectionString;
            try
            {
                connection.Open();
                System.Diagnostics.Debug.WriteLine("Sucessful Connection");
                String query = "SELECT insert_Stage(@name, @description);";
                NpgsqlCommand command = new NpgsqlCommand(query, connection);
                //Adds the parameter of the command
                command.Parameters.AddWithValue("@stage_id", stage.Stage_Id);
                command.Parameters.AddWithValue("@name", stage.Name);
                command.Parameters.AddWithValue("@description", stage.Description);

                //Executes the command
                command.ExecuteNonQuery();
                connection.Close();
            }
            catch (Exception e)
            {
                //Notifies an error in the output
                System.Diagnostics.Debug.WriteLine("Failed Connection in 'crear_Stage': " + e.Message);
            }//End catch
        }//End of method


        /// <summary>
        /// This method delete the stage whose id attribute is id. 
        /// </summary>
        /// <param name="id">The id of the stage to delete.</param>
        public void eliminar_Stage(long id)
        {
            //The object responsible of the connection is created 
            NpgsqlConnection connection = new NpgsqlConnection();
            connection.ConnectionString = connectionString;
            try
            {
                connection.Open();
                System.Diagnostics.Debug.WriteLine("Sucessful Connection");
                String query = "DELETE FROM STAGE WHERE stage_id = @id;";
                NpgsqlCommand command = new NpgsqlCommand(query, connection);
                command.Parameters.AddWithValue("@id", id);
                //Executes the command
                command.ExecuteNonQuery();
                connection.Close();
            }
            catch (Exception e)
            {
                //It is notified in the output that the connection failed 
                System.Diagnostics.Debug.WriteLine("Failed Connection in 'eliminar_Stage': " + e.Message);
            }//End catch
        }//End of method


        /// <summary>
        /// Method allow to update an attribute of a specific stage.
        /// </summary>
        /// <param name="id"> The id of the stage that it is wanted to update</param>
        /// <param name="newValue">The new value of the attribute. </param>
        /// <param name="campo">The name of attribute that will be update. </param>
        public void update_Stage(long id, String campo, String newValue)
        {
            //The object responsible of the connection is created 
            NpgsqlConnection connection = new NpgsqlConnection();
            connection.ConnectionString = connectionString;
            try
            {
                connection.Open();
                System.Diagnostics.Debug.WriteLine("Sucessful Connection");

                if (campo.Equals("Description"))
                {
                    String query = "UPDATE STAGE SET (description) = (@newValue) WHERE stage_id = @id;";
                    NpgsqlCommand command = new NpgsqlCommand(query, connection);
                    var valor = newValue;
                    command.Parameters.AddWithValue("@newValue", valor);
                    command.Parameters.AddWithValue("@id", id);
                    //Executes the command
                    command.ExecuteNonQuery();
                }
                connection.Close();
            }
            catch (Exception e)
            {
                //It is notified in the output that the connection failed 
                System.Diagnostics.Debug.WriteLine("Failed Connection in update_Stage: " + e.Message);
            }//End catch

        }//End od method


        /// <summary>
        /// Recovers the stage whose stage_id is id
        /// </summary>
        /// <param name="id"></param>
        /// <returns>Return the stage who is being requested.</returns>
        public Stage get_Stage(long id)
        {
            //The object responsible of the connection is created 
            NpgsqlConnection connection = new NpgsqlConnection();
            connection.ConnectionString = connectionString;
            //The output object is created
            Stage stage = new Stage();
            try
            {
                connection.Open();
                System.Diagnostics.Debug.WriteLine("Sucessful Connection");
                String query = "SELECT * FROM STAGE WHERE stage_id = @id;";
                NpgsqlCommand command = new NpgsqlCommand(query, connection);
                command.Parameters.AddWithValue("@id", id);
                //The query is executed
                var reader = command.ExecuteReader();
                //The values of the attributes are recovered
                reader.Read();
                stage.Stage_Id = Int64.Parse(reader["stage_id"].ToString());
                stage.Name = reader["name"].ToString();
                stage.Description = reader["description"].ToString();

                //Close the connection
                connection.Close();
            }
            catch (Exception e)
            {
                //It is notified in the output that the connection failed 
                System.Diagnostics.Debug.WriteLine("Failed Connection in get_Stage: " + e.Message);
            }//End catch
            return stage;
        }//End of method 


        /// <summary>
        /// Method return all the stages in the database.
        /// </summary>
        /// <returns>List of stages</returns>
        public List<Stage> get_allStage()
        {
            //The output object 
            List<Stage> list = new List<Stage>();
            //The object responsible of the connection is created 
            NpgsqlConnection connection = new NpgsqlConnection();
            connection.ConnectionString = connectionString;
            try
            {
                connection.Open();
                //Write in the output window
                System.Diagnostics.Debug.WriteLine("Sucessful Connection");
                String query = "SELECT * FROM STAGE;";
                //The query is executed
                NpgsqlCommand command = new NpgsqlCommand(query, connection);
                var reader = command.ExecuteReader();
                //The roles are recovered from the reader object
                while (reader.Read())
                {
                    Stage stage = new Stage();
                    //The values of the attributes are recovered
                    stage.Stage_Id = Int64.Parse(reader["stage_id"].ToString());
                    stage.Name = reader["name"].ToString();
                    stage.Description = reader["description"].ToString();

                    //The object is added to the list
                    list.Add(stage);
                }// end of while
                //The connection is closed
                connection.Close();
            }
            catch (Exception e)
            {
                //It is notified in the output that the connection failed 
                System.Diagnostics.Debug.WriteLine("Failed Connection in get_allStage: " + e.Message);
            }//End catch
            return list;
        }//End of


    }//End of ConnectionDB class
}//End of namespace