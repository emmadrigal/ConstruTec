using System.Web.Http;

namespace ConstruTec.Controllers
{
    [RoutePrefix("Divided_in")]
    public class Divided_inController : ApiController
    {
        /// <summary>
        /// Gets a divided_in regarding her id.
        /// </summary>
        /// <param name="id">
        /// Value of the id associated to a one divided_in.
        /// </param>
        /// <returns>
        /// Json with parameters of Divided_in: Divided_Id, Id_Project,  
        /// Stage_Id, Start_Date, End_Date, Status. 
        /// </returns>
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

        /// <summary>
        /// Gets a divided_in for each the Project id
        /// </summary>
        /// <param name="id">
        ///  Value of the id associated to a one project.
        /// </param>
        /// <returns>
        /// Json with parameters of Divided_in
        /// </returns>
        [Route("Project/{id}")]
        [HttpGet]
        public IHttpActionResult getDividedByProject(long id)
        {
            var dato = ConnectionDB.Instance.get_Divided_By_Project(id);
            if (dato==null)
            {
                return NotFound();
            }
            return Ok(dato);
        }

        /// <summary>
        /// Function for obtain the pay divide
        /// </summary>
        /// <param name="id">
        /// Value of the id associated to a one divided_in. 
        /// </param>
        /// <returns>
        ///  returns a correct
        /// </returns>
        [Route("~/payDivided/{id}")]
        [HttpGet]
        public IHttpActionResult payDivided(long id)
        {
            EPATECConnection.sendPedido(id);
            return Ok();
        }

        /// <summary>
        /// Function for obtain all the divided_in
        /// </summary>
        /// <returns>
        /// Returns a list of all the divided_in
        /// </returns>
        [Route("~/getAllDivided_in")]
        [HttpGet]
        public IHttpActionResult getAllDivided_in()
        {
            var dato = ConnectionDB.Instance.get_allDivided_in();
            return Ok(dato);
        }

        /// <summary>
        /// Function for change a one specific value of one divided_in
        /// </summary>
        /// <param name="id">
        /// Value of the id associated to a one divided_in. 
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
        public IHttpActionResult PutDivided_in(long id, string campo, string newValue)
        {
            if (campo == "Start_Date" || campo == "End_Date" || campo== "Status")
            {
                ConnectionDB.Instance.update_Divided_in(id, campo, newValue);
            }
            return Ok();
        }

        /// <summary>
        /// Function for create a new Divided_in in the database
        /// </summary>
        /// <param name="divided_in">
        /// Json with all the caracteristics of a divided_in for the database
        /// </param>
        /// <returns>
        /// returns a correct if the Divided_in it is create in the correct way
        /// </returns>
        [Route("")]
        [HttpPost]
        public IHttpActionResult postDivided_in([FromBody] Models.Divided_in divided_in)
        {
            ConnectionDB.Instance.crear_Divided_in(divided_in);
            return Ok();
        }

        /// <summary>
        /// Function for delete one specific divided_in
        /// </summary>
        /// <param name="id">
        /// Id value of one of existing divided_in
        /// </param>
        /// <returns>
        ///  returns a correct if the divided_in it is deleted in the correct way
        /// </returns>
        [Route("{id}")]
        [HttpDelete]
        public IHttpActionResult deleteDivided_in(long id)
        {
            ConnectionDB.Instance.eliminar_Divided_in(id);
            return Ok();
        }
    }
}
