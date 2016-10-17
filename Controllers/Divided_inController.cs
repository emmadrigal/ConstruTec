using System;
using System.Collections.Generic;
using System.Linq;
using System.Net;
using System.Net.Http;
using System.Web.Http;

namespace ConstruTec.Controllers
{
    [RoutePrefix("Divided_in")]
    public class Divided_inController : ApiController
    {
        [Route("{id}")]
        [HttpGet]
        public IHttpActionResult getDivided_in(long id)
        {
            var dato = ConnectionDB.Instance.get_Divided_in(id);
            if (dato == null)
            {
                return NotFound();
            }
            return Ok(dato);
        }

        [Route("~/getAllDivided_in")]
        [HttpGet]
        public IHttpActionResult getAllDivided_in()
        {
            var dato = ConnectionDB.Instance.get_allDivided_in();
            return Ok(dato);
        }

        [Route("{id}/{campo}/{newValue}")]
        [HttpPut]
        public IHttpActionResult PutDivided_in(long id, string campo, string newValue)
        {
            if (campo == "Start_Date" || campo == "End_Date" || campo== "Status")
            {
                ConnectionDB.Instance.update_Divided_in(id, campo, newValue);
            }
            return Ok();
        }

        [Route("")]
        [HttpPost]
        public IHttpActionResult postDivided_in([FromBody] Models.Divided_in divided_in)
        {
            ConnectionDB.Instance.crear_Divided_in(divided_in);
            return Ok();
        }

        [Route("{id}")]
        [HttpDelete]
        public IHttpActionResult deleteDivided_in(long id)
        {
            ConnectionDB.Instance.eliminar_Divided_in(id);
            return Ok();
        }
    }
}
