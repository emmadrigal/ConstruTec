using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

//contiene los clases para el manejo de los datos de la base
namespace ConstruTec.Models
{
    public class Roles
    {
        private long Role_id;
        private string Role_name;
        
        //Funciones de Role_id
        public long role_id
        {
            get { return Role_id; }
            set { Role_id = value; }
        }
        //Funciones de Role_name
        public string role_name
        {
            get { return Role_name; }
            set { Role_name = value; }
        }
    }
    public class Usuario
    {
        private long Id_Number;
        private string Code;
        private string Name;
        private int Phone_Number;
        private long role_usuario;
        
        //Funciones de Id_Number
        public long id_number
        {
            get { return Id_Number; }
            set { Id_Number = value; }
        }
    }
}