using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace ConstruTec.Models
{
    public class Usuario
    {
        public long Id_Number { get; set; }
        public string Code { get; set; }
        public string Name { get; set; }
        public int Phone_Number { get; set; }
        public long Role_usuario { get; set; }
    }
}