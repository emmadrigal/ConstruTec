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
        /// <summary>
        /// Gets a Project regarding her id.
        /// </summary>
        /// <param name="id">
        /// Value of the id associated to a one Project.
        /// </param>
        /// <returns>
        /// Json with parameters of Project
        /// </returns>
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

        /// <summary>
        /// Gets a Project for each the Client id
        /// </summary>
        /// <param name="id">
        /// Value of the id associated to a one Client.
        /// </param>
        /// <returns>
        ///  Json with parameters of Project
        /// </returns>
        [Route("Cliente/{id}")]
        [HttpGet]
        public IHttpActionResult getProjectByClient(long id)
        {
            var dato = ConnectionDB.Instance.get_Project_By_Client(id);
            if (dato == null)
            {
                return NotFound();
            }
            return Ok(dato);
        }

        /// <summary>
        /// Gets a Project for each the Engineer id
        /// </summary>
        /// <param name="id">
        /// Value of the id associated to a one Enginner.
        /// </param>
        /// <returns>
        /// Json with parameters of Project
        /// </returns>
        [Route("Ingeniero/{id}")]
        [HttpGet]
        public IHttpActionResult getProjectByEngineer(long id)
        {
            var dato = ConnectionDB.Instance.get_Project_By_Engineer(id);
            if (dato == null)
            {
                return NotFound();
            }
            return Ok(dato);
        }

        /// <summary>
        /// Function for obtain the project that have a stage that starts in the next for 15 days
        /// </summary>
        /// <returns>
        /// Returns a list of the Projects
        /// </returns>
        [Route("Proximos_dias")]
        [HttpGet]
        public IHttpActionResult nextProjectdias()
        {
            var dato = ConnectionDB.Instance.nextProject();
            if (dato == null)
            {
                return NotFound();
            }
            return Ok(dato);
        }


        /// <summary>
        ///  Function for obtain the project that have a stage that starts in the next for 15 days by material
        /// </summary>
        /// <param name="material">
        /// The name of the material
        /// </param>
        /// <returns>
        /// Returns a list of the Projects
        /// </returns>
        [Route("Proximos_dias_material/{material}")]
        [HttpGet]
        public IHttpActionResult nextProjectMaterial(String material)
        {
            var dato = ConnectionDB.Instance.nextProjectMaterial(material);
            if (dato == null)
            {
                return NotFound();
            }
            return Ok(dato);
        }

        /// <summary>
        /// Function for obtain all the Projects
        /// </summary>
        /// <returns>
        ///  Returns a list of all the Projects
        /// </returns>
        [Route("~/getAllProject")]
        [HttpGet]
        public IHttpActionResult getAllProject()
        {
            var dato = ConnectionDB.Instance.get_allProject();
            return Ok(dato);
        }

        /// <summary>
        /// Function for change a one specific value of one Project
        /// </summary>
        /// <param name="id">
        /// Value of the id associated to a one Project.
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
        public IHttpActionResult PutProject(long id, string campo, string newValue)
        {
            if (campo == "Location" || campo== "Name")
            {
                ConnectionDB.Instance.update_Project(id, campo , newValue);
            }
            return Ok();
        }

        /// <summary>
        ///  Function for create a new Project in the database
        /// </summary>
        /// <param name="project">
        /// Json with all the caracteristics of a Project for the database
        /// </param>
        /// <returns>
        /// returns a correct if the Project it is create in the correct way
        /// </returns>
        [Route("")]
        [HttpPost]
        public IHttpActionResult postProject([FromBody] Models.Project project)
        {
            ConnectionDB.Instance.crear_Project(project);
            return Ok();
        }

        /// <summary>
        /// Function for delete one specific Project
        /// </summary>
        /// <param name="id">
        /// Id value of one of existing Project
        /// </param>
        /// <returns>
        /// returns a correct if the Project it is deleted in the correct way
        /// </returns>
        [Route("{id}")]
        [HttpDelete]
        public IHttpActionResult deleteProject(long id)
        {
            ConnectionDB.Instance.eliminar_Project(id);
            return Ok();
        }
    }
}
