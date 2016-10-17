using System;
using System.Collections.Generic;
using System.Linq;
using System.Net;
using System.Net.Http;
using System.Web.Http;

namespace ConstruTec.Controllers
{
    [RoutePrefix("Project")]
    public class ProjectController : ApiController
    {
        [Route("{id}")]
        [HttpGet]
        public IHttpActionResult getProject(long id)
        {
            var dato = ConnectionDB.Instance.get_Project(id);
            if (dato == null)
            {
                return NotFound();
            }
            return Ok(dato);
        }

        [Route("~/getAllProject")]
        [HttpGet]
        public IHttpActionResult getAllProject()
        {
            var dato = ConnectionDB.Instance.get_allProject();
            return Ok(dato);
        }

        [Route("{id}/{campo}/{newValue}")]
        [HttpPut]
        public IHttpActionResult PutProject(long id, string campo, string newValue)
        {
            if (campo == "Location" || campo== "Name")
            {
                ConnectionDB.Instance.update_Project(id, campo , newValue);
            }
            return Ok();
        }

        [Route("")]
        [HttpPost]
        public IHttpActionResult postProject([FromBody] Models.Project project)
        {
            ConnectionDB.Instance.crear_Project(project);
            return Ok();
        }

        [Route("{id}")]
        [HttpDelete]
        public IHttpActionResult deleteProject(long id)
        {
            ConnectionDB.Instance.eliminar_Project(id);
            return Ok();
        }
    }
}
