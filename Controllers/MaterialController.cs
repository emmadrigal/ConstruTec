using System.Web.Http;

namespace ConstruTec.Controllers
{
    [RoutePrefix("Material")]
    public class MaterialController : ApiController
    {
        /// <summary>
        /// Gets a Material regarding her id.
        /// </summary>
        /// <param name="id">
        /// Value of the id associated to a one Material.
        /// </param>
        /// <returns>
        /// Json with parameters of Material
        /// </returns>
        [Route("{id}")]
        [HttpGet]
        public IHttpActionResult getMaterial(long id)
        {
            var dato = ConnectionDB.Instance.get_Material(id);
            if (dato == null)
            {
                return NotFound();
            }
            return Ok(dato);
        }

        /// <summary>
        /// Function for poulateMaterials with the materials of EPATEC
        /// </summary>
        /// <returns>
        /// retruns a correct if the materials are take of the EPATEC
        /// </returns>
        [Route("~/populateMaterials")]
        [HttpGet]
        public IHttpActionResult populateMaterials()
        {
            EPATECConnection.populateMaterials();
            return Ok();
        }

        /// <summary>
        ///  Function for obtain all the Materials
        /// </summary>
        /// <returns>
        /// Returns a list of all the materials
        /// </returns>
        [Route("~/getAllMaterial")]
        [HttpGet]
        public IHttpActionResult getAllMaterial()
        {            
            var dato = ConnectionDB.Instance.get_allMaterial();
            return Ok(dato);
        }

        /// <summary>
        ///  Function for change a one specific value of one Material
        /// </summary>
        /// <param name="id">
        /// Value of the id associated to a one material.
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
        public IHttpActionResult PutMaterial(long id, string campo, string newValue)
        {
            if (campo== "Price" || campo== "Description")
            {
                ConnectionDB.Instance.update_Material(id, campo , newValue);
            }
            return Ok();
        }

        /// <summary>
        /// Function for create a new Material in the database
        /// </summary>
        /// <param name="material">
        /// Json with all the caracteristics of a material for the database
        /// </param>
        /// <returns>
        /// returns a correct if the Material it is create in the correct way
        /// </returns>
        [Route("")]
        [HttpPost]
        public IHttpActionResult postMaterial([FromBody] Models.Material material)
        {
            ConnectionDB.Instance.crear_Material(material);
            return Ok();
        }

        /// <summary>
        /// Function for delete one specific Material
        /// </summary>
        /// <param name="id">
        /// Id value of one of existing Material
        /// </param>
        /// <returns>
        /// returns a correct if the Material it is deleted in the correct way
        /// </returns>
        [Route("{id}")]
        [HttpDelete]
        public IHttpActionResult deleteMaterial(long id)
        {
            ConnectionDB.Instance.eliminar_Material(id);
            return Ok();
        }
    }
}
