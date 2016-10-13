using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace ConstruTec.Models
{
    public class Posseses
    {
        public long Posseses_Id { get; set; }
        public long Id_Material { get; set; }
        public long Divided_Id { get; set; }
        public int Quantity { get; set; }
    }
}