using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace ConstruTec.Models
{
    /// <summary>
    /// Function that handles table Usuario simulate its functions to obtain and or save the data.
    /// </summary>
    public class Usuario
    {
        public long Id_Number { get; set; }
        public string Code { get; set; }
        public string Name { get; set; }
        public int Phone_Number { get; set; }
        public long Role_usuario { get; set; }
    }
}