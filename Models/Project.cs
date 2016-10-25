using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace ConstruTec.Models
{
    /// <summary>
    /// Function that handles table Project simulate its functions to obtain and or save the data.
    /// </summary>
    public class Project
    {
        public long Id_Proyect { get; set; }
        public long Id_Client { get; set; }
        public long Id_Enginner { get; set; }
        public string Location { get; set; }
        public string Name { get; set; }
    }
}