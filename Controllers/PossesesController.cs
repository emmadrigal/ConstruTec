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
        /// <summary>
        /// Gets a Posseses regarding her id.
        /// </summary>
        /// <param name="id">
        /// Value of the id associated to a one Posseses.
        /// </param>
        /// <returns>
        /// Json with parameters of Posseses
        /// </returns>
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

        /// <summary>
        /// Gets a Posseses for each the Divided_in id
        /// </summary>
        /// <param name="id">
        ///  Value of the id associated to a one Posseses.
        /// </param>
        /// <returns>
        /// Json with parameters of Posseses
        /// </returns>
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

        /// <summary>
        /// Function for obtain all the Posseses
        /// </summary>
        /// <returns>
        /// Returns a list of all the Posseses
        /// </returns>
        [Route("~/getAllPosseses")]
        [HttpGet]
        public IHttpActionResult getAllPosseses()
        {
            var dato = ConnectionDB.Instance.get_allPosseses();
            return Ok(dato);
        }

        /// <summary>
        /// Function for change a one specific value of one Posseses
        /// </summary>
        /// <param name="id">
        /// Value of the id associated to a one posseses.
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
        public IHttpActionResult PutPosseses(long id, string campo, string newValue)
        {
            if (campo == "Quantity")
            {
                ConnectionDB.Instance.update_Posseses(id, campo, newValue);
            }
            return Ok();
        }

        /// <summary>
        /// Function for create a new Posseses in the database
        /// </summary>
        /// <param name="posseses">
        /// Json with all the caracteristics of a posseses for the database
        /// </param>
        /// <returns>
        /// returns a correct if the Posseses it is create in the correct way
        /// </returns>
        [Route("")]
        [HttpPost]
        public IHttpActionResult postPosseses([FromBody] Models.Posseses posseses)
        {
            ConnectionDB.Instance.crear_Posseses(posseses);
            return Ok();
        }

        /// <summary>
        /// Function for delete one specific Posseses
        /// </summary>
        /// <param name="id">
        /// Id value of one of existing Posseses
        /// </param>
        /// <returns>
        /// returns a correct if the Posseses it is deleted in the correct way
        /// </returns>
        [Route("{id}")]
        [HttpDelete]
        public IHttpActionResult deletePosseses(long id)
        {
            ConnectionDB.Instance.eliminar_Posseses(id);
            return Ok();
        }
    }
}
