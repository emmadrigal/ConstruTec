﻿using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace ConstruTec.Models
{
    public class Divided_in
    {
        public long Divided_Id { get; set; }
        public long Id_Project { get; set; }
        public long Stage_Id { get; set; }
        public string Start_Date { get; set; }
        public string End_Date { get; set; }
        public bool Status { get; set; }
    }
}