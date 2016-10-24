﻿using System;
using System.Collections.Generic;
using System.Linq;
using System.Net;
using System.Net.Http;
using System.Web.Http;

namespace ConstruTec.Controllers
{
    [RoutePrefix("Presupuesto")]
    public class PresupuestoController : ApiController
    {
        [Route("{namep}")]
        [HttpGet]
        public IHttpActionResult getPresupuesto(long namep)
        {
            var dato = ConnectionDB.Instance.get_Presupuesto(namep);
            if(dato == null)
            {
                return NotFound();
            }
            return Ok(dato);
        }

    }
}
