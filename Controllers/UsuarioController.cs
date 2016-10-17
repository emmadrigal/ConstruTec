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

        [Route("~/getAllUsuario")]
        [HttpGet]
        public IHttpActionResult getAllUsuario()
        {
            var dato = ConnectionDB.Instance.get_allUsuario();
            return Ok(dato);
        }

        [Route("{id}/{campo}/{newValue}")]
        [HttpPut]
        public IHttpActionResult PutUsuario(long id, string campo, string newValue)
        {
            if (campo == "Code" || campo == "Name" || campo== "Phone_Number")
            {
                ConnectionDB.Instance.update_Usuario(id, campo , newValue);
            }
            return Ok();
        }

        [Route("")]
        [HttpPost]
        public IHttpActionResult postUsuario([FromBody] Models.Usuario usuario)
        {
            ConnectionDB.Instance.crear_Usuario(usuario);
            return Ok();
        }

        [Route("{id}")]
        [HttpDelete]
        public IHttpActionResult deleteUsuario(long id)
        {
            ConnectionDB.Instance.eliminar_Usuario(id);
            return Ok();
        }
    }
}
