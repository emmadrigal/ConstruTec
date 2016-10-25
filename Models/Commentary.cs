using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace ConstruTec.Models
{
    /// <summary>
    /// Function that handles table Commentary simulate its functions to obtain and or save the data.
    /// </summary>
    public class Commentary
    {
        public long Comment_Id { get; set; }
        public long Id_Project { get; set; }
        public string Comentary { get; set; }
    }
}