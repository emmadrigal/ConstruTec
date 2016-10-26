using ConstruTec;
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
        /// <summary>
        ///  Gets a Roles regarding her id.
        /// </summary>
        /// <param name="id">
        /// Value of the id associated to a one Roles.
        /// </param>
        /// <returns>
        /// Json with parameters of Roles
        /// </returns>
        [Route("{id}")]
        [HttpGet]
        public IHttpActionResult getRoles(long id)
        {
            var dato = ConnectionDB.Instance.get_Roles(id);
            if (dato == null)
            {
                return NotFound();
            }
            return Ok(dato);
        }

        /// <summary>
        /// Function for obtain all the Roles
        /// </summary>
        /// <returns>
        /// Returns a list of all the Roles
        /// </returns>
        [Route("~/getAllRoles")] 
        [HttpGet]
        public IHttpActionResult getAllRoles()
        {
            var dato = ConnectionDB.Instance.get_allRoles();
            return Ok(dato);
        }

        /// <summary>
        /// Function for change a one specific value of one Roles
        /// </summary>
        /// <param name="id">
        /// Value of the id associated to a one Rol.
        /// </param>
        /// <param name="campo">
        /// The parameter that be changed
        /// </param>
        /// <param name="newValue">
        /// The new value of the specific parameter
        /// </param>
        /// <returns>
        /// returns a correct if the parameter puts in the correct way
        /// </returns>
        [Route("{id}/{campo}/{newValue}")]
        [HttpPut]
        public IHttpActionResult PutRoles(long id, string campo, string newValue)
        {
            if (campo == "nombre")
            {
                ConnectionDB.Instance.update_Roles(id, campo, newValue);
            }
            return Ok();
        }

        /// <summary>
        /// Function for create a new Rol in the database
        /// </summary>
        /// <param name="roles">
        /// Json with all the caracteristics of a Roles for the database
        /// </param>
        /// <returns>
        /// returns a correct if the Rol it is create in the correct way
        /// </returns>
        [Route ("")]
        [HttpPost]
        public IHttpActionResult postRoles([FromBody] Models.Roles roles )
        {
            ConnectionDB.Instance.crear_Roles(roles);
            return Ok();
        }

        /// <summary>
        /// Function for delete one specific Rol
        /// </summary>
        /// <param name="id">
        /// Id value of one of existing Rol
        /// </param>
        /// <returns>
        /// returns a correct if the Rol it is deleted in the correct way
        /// </returns>
        [Route("{id}")]
        [HttpDelete]
        public IHttpActionResult deleteRoles(long id)
        {
            ConnectionDB.Instance.eliminar_Roles(id);
            return Ok();
        }
    }
}
