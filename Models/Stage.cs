using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace ConstruTec.Models
{
    /// <summary>
    /// Function that handles table Stage simulate its functions to obtain and or save the data.
    /// </summary>
    public class Stage
    {
        public long Stage_Id { get; set; }
        public string Name { get; set; }
        public string Description { get; set; }
    }
}