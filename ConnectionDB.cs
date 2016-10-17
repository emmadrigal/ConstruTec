using ConstruTec.Models;
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
                String query = "INSERT INTO ROLES VALUES (@role_id, @role_name);";
                NpgsqlCommand command = new NpgsqlCommand(query, connection);
                //Adds the parametes of the command
                command.Parameters.AddWithValue("@role_id", rol.Role_id);
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
        public void update_nombre_Roles(long id, String newValue)
        {
            //The object responsible of the connection is created 
            NpgsqlConnection connection = new NpgsqlConnection();
            connection.ConnectionString = connectionString;
            try
            {
                connection.Open();
                System.Diagnostics.Debug.WriteLine("Sucessful Connection");
                String query = "UPDATE ROLES SET (role_name) = (@role_name) WHERE role_id = @id;";
                NpgsqlCommand command = new NpgsqlCommand(query, connection);
                command.Parameters.AddWithValue("@id", id);
                command.Parameters.AddWithValue("@role_name", newValue);
                command.ExecuteNonQuery();
                connection.Close();
            }
            catch (Exception e)
            {
                //It is notified in the output that the connection failed 
                System.Diagnostics.Debug.WriteLine("Failed Connection in update_nombre_roles: " + e.Message);
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
                rol.Role_id = reader.GetInt64(0);
                rol.Role_name = reader.GetString(1);
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
                    rol.Role_id = reader.GetInt64(0);
                    rol.Role_name = reader.GetString(1);
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
        public void update_nombre_Usuario(long id, String newValue)
        {
            //The object responsible of the connection is created 
            NpgsqlConnection connection = new NpgsqlConnection();
            connection.ConnectionString = connectionString;
            try
            {
                connection.Open();
                System.Diagnostics.Debug.WriteLine("Sucessful Connection");
                String query = "UPDATE ROLES SET (role_name) = (@role_name) WHERE role_id = @id;";
                NpgsqlCommand command = new NpgsqlCommand(query, connection);
                command.Parameters.AddWithValue("@id", id);
                command.Parameters.AddWithValue("@role_name", newValue);
                command.ExecuteNonQuery();
                connection.Close();
            }
            catch (Exception e)
            {
                //It is notified in the output that the connection failed 
                System.Diagnostics.Debug.WriteLine("Failed Connection in update_nombre_roles: " + e.Message);
            }//End catch

        }//End od method


        /// <summary>
        /// Recovers the user with the id_user id.
        /// </summary>
        /// <param name="id"></param>
        /// <returns>Return the user who is being requested.</returns>
        public Usuario get_Usuarios(long id)
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
                user.Id_Number = reader.GetInt64(0);
                user.Code = reader.GetString(1);
                user.Name = reader.GetString(2);
                user.Phone_Number = reader.GetInt32(3);
                user.Role_usuario = reader.GetInt64(4);
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
        public List<Usuario> get_allUsuarios()
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
                    user.Id_Number = reader.GetInt64(0);
                    user.Code = reader.GetString(1);
                    user.Name = reader.GetString(2);
                    user.Phone_Number = reader.GetInt32(3);
                    user.Role_usuario = reader.GetInt64(4);
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
        public void crear_Commentary(Commentary commentary) { 
     
            //The object responsible of the connection is created 
            NpgsqlConnection connection = new NpgsqlConnection();
            connection.ConnectionString = connectionString;
            try
            {
                connection.Open();
                System.Diagnostics.Debug.WriteLine("Sucessful Connection");
                String query = "INSERT INTO COMMENTARY VALUES (@comment_id, @divided_id, @commentary);";
                NpgsqlCommand command = new NpgsqlCommand(query, connection);
                //Adds the parameter of the command
                command.Parameters.AddWithValue("@comment_id", commentary.Comment_Id);
                command.Parameters.AddWithValue("@divided_in", commentary.Divided_Id);
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
                commentary.Comment_Id = reader.GetInt64(0);
                commentary.Divided_Id = reader.GetInt64(1);
                commentary.Comentary = reader.GetString(2);
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
        public List<Commentary> get_allCommentaries()
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
                    commentary.Comment_Id = reader.GetInt64(0);
                    commentary.Divided_Id = reader.GetInt64(1);
                    commentary.Comentary = reader.GetString(2);
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




        //####### COMMENTARY METHODS #########
        
        /// <summary>
        /// This method add the divided_in in the ConstruTec database.
        /// </summary>
        /// <param name="divided_in">The divided_in that will be added to the database</param>
        public void crear_Commentary(Divided_in div)
        {

            //The object responsible of the connection is created 
            NpgsqlConnection connection = new NpgsqlConnection();
            connection.ConnectionString = connectionString;
            try
            {
                connection.Open();
                System.Diagnostics.Debug.WriteLine("Sucessful Connection");
                String query = "INSERT INTO DIVIDED_IN VALUES " + 
                    "(@divided_id, @id_project, @stage_id, @start_date, @end_date, @status);";
                NpgsqlCommand command = new NpgsqlCommand(query, connection);
                //Adds the parameter of the command
                command.Parameters.AddWithValue("@divided_id", div.Divided_Id);
                command.Parameters.AddWithValue("@id_project", div.Id_Project);
                command.Parameters.AddWithValue("@stage_id", div.Stage_Id);
                command.Parameters.AddWithValue("@start_date", div.Start_Date);
                command.Parameters.AddWithValue("@end_date", div.End_Date);
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










    }//End of ConnectionDB class
}//End of namespace