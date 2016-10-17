﻿using ConstruTec;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Net;
using System.Net.Http;
using System.Web.Http;

namespace ConstruTec.Controllers
{
    [RoutePrefix("Roles")]
    public class RolesController : ApiController
    {
        [Route("{id}")]
        [HttpGet]
        public IHttpActionResult getRoles(long id)
        {
            var dato = ConnectionDB.Instance.get_Roles(id);
            if (dato == null)
            {
                return NotFound();
            }
            System.Diagnostics.Debug.WriteLine("HOLI");
            return Ok(dato);
        }

        [Route("~/getAllRoles")] 
        [HttpGet]
        public IHttpActionResult getAllClients()
        {
            var dato = ConnectionDB.Instance.get_allRoles();
            return Ok(dato);
        }

        [Route("{id}/{campo}/{newValue}")]
        [HttpPut]
        public IHttpActionResult PutRoles(long id, string campo, string newValue)
        {
            if (campo == "nombre")
            {
                ConnectionDB.Instance.update_nombre_Roles(id, newValue);
            }
            return Ok();
        }

        [Route ("")]
        [HttpPost]
        public IHttpActionResult postRoles([FromBody] Models.Roles roles )
        {
            System.Diagnostics.Debug.WriteLine("ANTES LALALALA");
            ConnectionDB.Instance.crear_Roles(roles);
            return Ok();
        }

        [Route("{id}")]
        [HttpDelete]
        public IHttpActionResult deleteRoles(long id)
        {
            ConnectionDB.Instance.eliminar_Roles(id);
            return Ok();
        }
    }
}
