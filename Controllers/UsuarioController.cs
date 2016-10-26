using System;
using System.Collections.Generic;
using System.Linq;
using System.Net;
using System.Net.Http;
using System.Web.Http;

namespace ConstruTec.Controllers
{
    [RoutePrefix("Usuario")]
    public class UsuarioController : ApiController
    {
        /// <summary>
        /// Gets a User regarding her id.
        /// </summary>
        /// <param name="id">
        /// Value of the id associated to a one User
        /// </param>
        /// <returns>
        /// Json with parameters of User
        /// </returns>
        [Route("{id}")]
        [HttpGet]
        public IHttpActionResult getUsuario(long id)
        {
            var dato = ConnectionDB.Instance.get_Usuario(id);
            if (dato == null)
            {
                return NotFound();
            }
            return Ok(dato);
        }

        /// <summary>
        ///  Function for obtain all the Users
        /// </summary>
        /// <returns>
        /// Returns a list of all the Users
        /// </returns>
        [Route("~/getAllUsuario")]
        [HttpGet]
        public IHttpActionResult getAllUsuario()
        {
            var dato = ConnectionDB.Instance.get_allUsuario();
            return Ok(dato);
        }

        /// <summary>
        /// Function for change a one specific value of one User
        /// </summary>
        /// <param name="id">
        /// Value of the id associated to a one User.
        /// </param>
        /// <param name="campo">
        /// The parameter that be changed
        /// </param>
        /// <param name="newValue">
        /// The new value of the specific parameter
        /// </param>
        /// <returns>
        ///  returns a correct if the parameter puts in the correct way
        /// </returns>
        [Route("{id}/{campo}/{newValue}")]
        [HttpPut]
        public IHttpActionResult PutUsuario(long id, string campo, string newValue)
        {
            System.Diagnostics.Debug.WriteLine("Entra al if");
            if (campo == "Code" || campo == "Name" || campo== "Phone_Number")
            {
                ConnectionDB.Instance.update_Usuario(id, campo , newValue);
            }
            return Ok();
        }

        /// <summary>
        /// Function for create a new User in the database
        /// </summary>
        /// <param name="usuario">
        ///  Json with all the caracteristics of a User for the database
        /// </param>
        /// <returns>
        ///  returns a correct if the User it is create in the correct way
        /// </returns>
        [Route("")]
        [HttpPost]
        public IHttpActionResult postUsuario([FromBody] Models.Usuario usuario)
        {
            ConnectionDB.Instance.crear_Usuario(usuario);
            return Ok();
        }

        /// <summary>
        /// Function for delete one specific User
        /// </summary>
        /// <param name="id">
        /// Id value of one of existing User
        /// </param>
        /// <returns>
        /// returns a correct if the User it is deleted in the correct way
        /// </returns>
        [Route("{id}")]
        [HttpDelete]
        public IHttpActionResult deleteUsuario(long id)
        {
            ConnectionDB.Instance.eliminar_Usuario(id);
            return Ok();
        }
    }
}
