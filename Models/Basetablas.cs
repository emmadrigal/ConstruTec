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
        private long Role_usuario;
        
        //Funciones de Id_Number
        public long id_number
        {
            get { return Id_Number; }
            set { Id_Number = value; }
        }

        //Funciones de Code
        public string code
        {
            get { return Code; }
            set { Code = value; }
        }

        //Funciones de Name 
        public string name
        {
            get { return Name; }
            set { Name = value; }
        }

        //Funciones de Phone_Number
        public int phone_number
        {
            get { return Phone_Number; }
            set { Phone_Number = value; }
        }

        //Funciones de Role_usuario
        public long role_usuario
        {
            get { return Role_usuario; }
            set { Role_usuario = value; }
        }
    }

    public class Project
    {
        private long Id_Proyect;
        private long Id_Client;
        private long Id_Enginner;
        private string Location;
        private string Name; 

        //Funciones de Id_Proyect
        public long id_Proyect
        {
            get { return Id_Proyect; }
            set { Id_Proyect = value; }
        }

        //Funciones de Id_Client
        public long id_Client
        {
            get { return Id_Client; }
            set { Id_Client = value; }
        }

        //Funciones de Id_Enginner
        public long id_Enginner
        {
            get { return Id_Enginner; }
            set { Id_Enginner = value; }
        }

        //Funciones de Location
        public string location
        {
            get { return Location; }
            set { Location = value; }
        }

        //Funciones de Name
        public string name
        {
            get { return Name; }
            set { Name = value; }
        }
    }

    public class Material
    {
        private long Id_Material;
        private string Name;
        private int Price;
        private string Description;

        //Funciones de Id_Material
        public long id_Material
        {
            get { return Id_Material; }
            set { Id_Material = value; }
        }

        //Funciones de Name
        public string name
        {
            get { return Name; }
            set { Name = value; }
        }

        // Funciones de Price
        public int price
        {
            get { return Price; }
            set { Price = value; }
        }

        //Funciones de Description
        public string decription
        {
            get { return Description; }
            set { Description = value; }
        }
    }

    public class Posseses
    {
        private long Posseses_Id;
        private long Id_Material;
        private long Divided_Id;
        private int Quantity;

        //Funciones de Posseses_Id
        public long posseses_id
        {
            get { return Posseses_Id; }
            set { Posseses_Id = value; }
        }

        //Funciones de Id_Material
        public long id_material
        {
            get { return Id_Material; }
            set { Id_Material = value; }
        }

        //Funciones de Divided_Id
        public long divided_Id
        {
            get { return Divided_Id; }
            set { Divided_Id = value; }
        }

        //Funciones de Quantity
        public int quantity
        {
            get { return Quantity; }
            set { Quantity = value; }
        }
    }

    public class Stage
    {
        private long Stage_Id;
        private string Name;
        private string Description;

        //Funciones de Stage_Id
        public long stage_Id
        {
            get { return Stage_Id; }
            set { Stage_Id = value; }
        }

        //Funciones de Name
        public string name
        {
            get { return Name; }
            set { Name = value; }
        }

        //Funciones de Description
        public string description
        {
            get { return Description; }
            set { Description = value; }
        }
    }

    public class Divided_in
    {
        private long Divided_Id;
        private long Id_Project;
        private long Stage_Id;
        private string Start_Date;
        private string End_Date;
        private bool Status;

        //Funciones de Divided_Id
        public long divided_Id
        {
            get { return Divided_Id; }
            set { Divided_Id = value; }
        }

        //Funciones de Id_Project
        public long id_Project
        {
            get { return Id_Project; }
            set { Id_Project = value; }
        }

        //Funciones de Stage_Id
        public long stage_Id
        {
            get { return Stage_Id; }
            set { Stage_Id = value; }
        }

        //Funciones de Start_Date
        public string start_Date
        {
            get { return Start_Date; }
            set { Start_Date = value; }
        }

        //Funciones de End_Date
        public string end_Date
        {
            get { return End_Date; }
            set { End_Date = value; }
        }

        //Funciones de Status
        public bool status
        {
            get { return Status; }
            set { Status = value; }
        }
    }

    public class Commentary
    {
        private long Comment_Id;
        private long Divided_Id;
        private string Comentary;

        //Funciones de Comment_Id
        public long comment_id
        {
            get { return Comment_Id; }
            set { Comment_Id = value; }
        }

        //Funciones de Divided_Id
        public long divided_Id
        {
            get { return Divided_Id; }
            set { Divided_Id = value; }
        }

        //Funciones de Comentary
        public string comentary
        {
            get { return Comentary; }
            set { Comentary = value; }
        }
    }
}