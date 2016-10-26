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
        /// <summary>
        /// Gets a Stage regarding her id.
        /// </summary>
        /// <param name="id">
        /// Value of the id associated to a one Stage.
        /// </param>
        /// <returns>
        /// Json with parameters of Stage
        /// </returns>
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

        /// <summary>
        /// Function for obtain all the Stages
        /// </summary>
        /// <returns>
        /// Returns a list of all the Stages
        /// </returns>
        [Route("~/getAllStage")]
        [HttpGet]
        public IHttpActionResult getAllStage()
        {
            var dato = ConnectionDB.Instance.get_allStage();
            return Ok(dato);
        }

        /// <summary>
        /// Function for change a one specific value of one Stage
        /// </summary>
        /// <param name="id">
        /// Value of the id associated to a one Stage.
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
        public IHttpActionResult PutStage(long id, string campo, string newValue)
        {
            
            if (campo == "Description")
            {

                ConnectionDB.Instance.update_Stage(id, campo, newValue);
            }
            return Ok();
        }

        /// <summary>
        /// Function for create a new Stage in the database
        /// </summary>
        /// <param name="stage">
        /// Json with all the caracteristics of a Stage for the database
        /// </param>
        /// <returns>
        /// returns a correct if the Stage it is create in the correct way
        /// </returns>
        [Route("")]
        [HttpPost]
        public IHttpActionResult postStage([FromBody] Models.Stage stage)
        {
            ConnectionDB.Instance.crear_Stage(stage);
            return Ok();
        }

        /// <summary>
        /// Function for delete one specific Stage
        /// </summary>
        /// <param name="id">
        /// Id value of one of existing Stage
        /// </param>
        /// <returns>
        /// returns a correct if the Stage it is deleted in the correct way
        /// </returns>
        [Route("{id}")]
        [HttpDelete]
        public IHttpActionResult deleteStage(long id)
        {
            ConnectionDB.Instance.eliminar_Stage(id);
            return Ok();
        }
    }
}
