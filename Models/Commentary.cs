using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace ConstruTec.Models
{
    public class Commentary
    {
        public long Comment_Id { get; set; }
        public long Id_Project { get; set; }
        public string Comentary { get; set; }
    }
}