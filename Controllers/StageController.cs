using System;
using System.Collections.Generic;
using System.Linq;
using System.Net;
using System.Net.Http;
using System.Web.Http;

namespace ConstruTec.Controllers
{
    [RoutePrefix("Stage")]
    public class StageController : ApiController
    {
        [Route("{id}")]
        [HttpGet]
        public IHttpActionResult getStage(long id)
        {
            var dato = ConnectionDB.Instance.get_Stage(id);
            if (dato == null)
            {
                return NotFound();
            }
            return Ok(dato);
        }

        [Route("~/getAllStage")]
        [HttpGet]
        public IHttpActionResult getAllStage()
        {
            var dato = ConnectionDB.Instance.get_allStage();
            return Ok(dato);
        }

        [Route("{id}/{campo}/{newValue}")]
        [HttpPut]
        public IHttpActionResult PutStage(long id, string campo, string newValue)
        {
            if (campo == "Description")
            {
                ConnectionDB.Instance.update_Stage(id, campo, newValue);
            }
            return Ok();
        }

        [Route("")]
        [HttpPost]
        public IHttpActionResult postStage([FromBody] Models.Stage stage)
        {
            ConnectionDB.Instance.crear_Stage(stage);
            return Ok();
        }

        [Route("{id}")]
        [HttpDelete]
        public IHttpActionResult deleteStage(long id)
        {
            ConnectionDB.Instance.eliminar_Stage(id);
            return Ok();
        }
    }
}
