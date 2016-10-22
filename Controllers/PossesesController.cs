using System;
using System.Collections.Generic;
using System.Linq;
using System.Net;
using System.Net.Http;
using System.Web.Http;

namespace ConstruTec.Controllers
{
    [RoutePrefix("Posseses")]
    public class PossesesController : ApiController
    {
        [Route("{id}")]
        [HttpGet]
        public IHttpActionResult getPosseses(long id)
        {
            var dato = ConnectionDB.Instance.get_Posseses(id);
            if (dato == null)
            {
                return NotFound();
            }
            return Ok(dato);
        }

        [Route("Divided_in/{id}")]
        [HttpGet]
        public IHttpActionResult getPossesesByDivided(long id)
        {
            var dato = ConnectionDB.Instance.get_Posseses_By_Divided(id);
            if (dato == null)
            {
                return NotFound();
            }
            return Ok(dato);
        }

        [Route("~/getAllPosseses")]
        [HttpGet]
        public IHttpActionResult getAllPosseses()
        {
            var dato = ConnectionDB.Instance.get_allPosseses();
            return Ok(dato);
        }

        [Route("{id}/{campo}/{newValue}")]
        [HttpPut]
        public IHttpActionResult PutPosseses(long id, string campo, string newValue)
        {
            if (campo == "Quantity")
            {
                ConnectionDB.Instance.update_Posseses(id, campo, newValue);
            }
            return Ok();
        }

        [Route("")]
        [HttpPost]
        public IHttpActionResult postPosseses([FromBody] Models.Posseses posseses)
        {
            ConnectionDB.Instance.crear_Posseses(posseses);
            return Ok();
        }

        [Route("{id}")]
        [HttpDelete]
        public IHttpActionResult deletePosseses(long id)
        {
            ConnectionDB.Instance.eliminar_Posseses(id);
            return Ok();
        }
    }
}
