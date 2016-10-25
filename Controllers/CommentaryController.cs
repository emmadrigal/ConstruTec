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
        /// <summary>
        /// Gets a comment regarding her id.
        /// </summary>
        /// <param name="id">
        /// Value of the id associated to a one commentary.
        /// </param>
        /// <returns>
        /// Json with parameters of Commentary: Commentary id, id of its associated project
        /// y the body of the commmentary.
        /// </returns>
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

        /// <summary>
        /// Function that is responsible for obtaining all comments.
        /// </summary>
        /// <returns>
        /// Json with a list of all comments with her three characteristics.
        /// </returns>
        [Route("~/getAllCommentary")]
        [HttpGet]
        public IHttpActionResult getAllCommentary()
        {
            var dato = ConnectionDB.Instance.get_allCommentary();
            return Ok(dato);
        }


        /// <summary>
        /// Function that gets a comment with respect to the Project id to which it is associated.
        /// </summary>
        /// <param name="id">
        /// Id value of the project that is associated with the comment
        /// </param>
        /// <returns>
        /// Json with parameters of Commentary
        /// </returns>
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


        /// <summary>
        /// Function that update a specific value of one parameter of the commentary
        /// </summary>
        /// <param name="id"></param>
        /// Id value of one of existing commentaries
        /// <param name="campo"></param>
        /// The parameter that be changed
        /// <param name="newValue">
        /// The new value of the specific parameter
        /// </param>
        /// <returns>
        /// returns a correct if the parameter puts in the correct way
        /// </returns>
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

        /// <summary>
        /// Function for create a new Commentary in the database
        /// </summary>
        /// <param name="commentary">
        /// Json with all the caracteristics of a Commentary for the database
        /// </param>
        /// <returns>
        /// returns a correct if the Commentary it is create in the correct way 
        /// </returns>
        [Route("")]
        [HttpPost]
        public IHttpActionResult postCommentary([FromBody] Models.Commentary commentary)
        {
            ConnectionDB.Instance.crear_Commentary(commentary);
            return Ok();
        }

        /// <summary>
        /// Function for delete one specific commentary
        /// </summary>
        /// <param name="id">
        /// Id value of one of existing commentary
        /// </param>
        /// <returns>
        /// returns a correct if the Commentary it is deleted in the correct way
        /// </returns>
        [Route("{id}")]
        [HttpDelete]
        public IHttpActionResult deleteCommentary(long id)
        {
            ConnectionDB.Instance.eliminar_Commentary(id);
            return Ok();
        }

    }
}
