using System;
using System.Collections.Generic;
using System.Linq;
using System.Net;
using System.Net.Http;
using System.Web.Http;

namespace ConstruTec.Controllers
{
    [RoutePrefix("Commentary")]
    public class CommentaryController : ApiController
    {
        [Route("{id}")]
        [HttpGet]
        public IHttpActionResult getCommentary(long id)
        {
            var dato = ConnectionDB.Instance.get_Commentary(id);
            if (dato == null)
            {
                return NotFound();
            }
            return Ok(dato);
        }

        [Route("~/getAllCommentary")]
        [HttpGet]
        public IHttpActionResult getAllCommentary()
        {
            var dato = ConnectionDB.Instance.get_allCommentary();
            return Ok(dato);
        }


        [Route("Proyecto/{id}")]
        [HttpGet]
        public IHttpActionResult getCommentaryByProject(long id)
        {
            var dato = ConnectionDB.Instance.get_Commentary_By_Project(id);
            if (dato == null)
            {
                return NotFound();
            }
            return Ok(dato);
        }


        [Route("{id}/{campo}/{newValue}")]
        [HttpPut]
        public IHttpActionResult PutCommentary(long id, string campo, string newValue)
        {
            if (campo == "Commentary")
            {
                ConnectionDB.Instance.update_Commentary(id, campo, newValue);
            }
            return Ok();
        }

        [Route("")]
        [HttpPost]
        public IHttpActionResult postCommentary([FromBody] Models.Commentary commentary)
        {
            ConnectionDB.Instance.crear_Commentary(commentary);
            return Ok();
        }

        [Route("{id}")]
        [HttpDelete]
        public IHttpActionResult deleteCommentary(long id)
        {
            ConnectionDB.Instance.eliminar_Commentary(id);
            return Ok();
        }

    }
}
