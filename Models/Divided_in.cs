using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace ConstruTec.Models
{
    /// <summary>
    /// Function that handles table Divided_in simulate its functions to obtain and or save the data.
    /// </summary>
    public class Divided_in
    {
        public long Divided_Id { get; set; }
        public long Id_Project { get; set; }
        public long Stage_Id { get; set; }
        public String Start_Date { get; set; }
        public String End_Date { get; set; }
        public bool Status { get; set; }
    }
}