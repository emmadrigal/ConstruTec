using System.Web.Http;

namespace ConstruTec.Controllers
{
    [RoutePrefix("Material")]
    public class MaterialController : ApiController
    {
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

        [Route("~/populateMaterials")]
        [HttpGet]
        public IHttpActionResult populateMaterials()
        {
            EPATECConnection.populateMaterials();
            return Ok();
        }

        [Route("~/getAllMaterial")]
        [HttpGet]
        public IHttpActionResult getAllMaterial()
        {            
            var dato = ConnectionDB.Instance.get_allMaterial();
            return Ok(dato);
        }

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

        [Route("")]
        [HttpPost]
        public IHttpActionResult postMaterial([FromBody] Models.Material material)
        {
            ConnectionDB.Instance.crear_Material(material);
            return Ok();
        }

        [Route("{id}")]
        [HttpDelete]
        public IHttpActionResult deleteMaterial(long id)
        {
            ConnectionDB.Instance.eliminar_Material(id);
            return Ok();
        }
    }
}
