using ConstruTec.Models;
using Newtonsoft.Json;
using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Net;
using System.Net.Http;
using System.Text;
using System.Web;

namespace ConstruTec
{
    public class EPATECConnection
    {
        static string urlBase = @"http://localhost:50594/";

        public EPATECConnection() { }

        static public async void populateMaterials()
        {
            string responseString;
            using (var client = new HttpClient())
            {
                responseString = await client.GetStringAsync(urlBase + "getAllProducts");
                System.Diagnostics.Debug.Print("");
                System.Diagnostics.Debug.Print(responseString);
                System.Diagnostics.Debug.Print("");
            }

            //Parses the response
            List<EPATECMaterial> input = JsonConvert.DeserializeObject<List<EPATECMaterial>>(responseString);

            foreach (EPATECMaterial mat in input)
            {
                Material material = new Material();
                material.Name = mat.nombre;
                material.Price = mat.Precio;
                material.Description = mat.Descripcion;

                ConnectionDB.Instance.crear_Material(material);
            }
        }

        static public void sendPedido(long dividedID)
        {
            //Creates the requested Response
            List<Posseses> posseses = new List<Posseses>();
            posseses = ConnectionDB.Instance.get_Posseses_By_Divided(dividedID);

            //Creates the request json in the correct format
            List<EPATECProductoPedido> listaProductos = new List<EPATECProductoPedido>();
            EPATECPedido pedido = new EPATECPedido()
            {
                Cedula_Cliente = 0,
                id_Sucursal = 1,
                Telefono = "911",
                productos = listaProductos
            };

            foreach (Posseses pos in posseses)
            {
                long id_Material = pos.Id_Material;
                int cantidad = pos.Quantity;
                Material material = ConnectionDB.Instance.get_Material(id_Material);

                string nombre_Material = material.Name;

                EPATECProductoPedido productoPedido = new EPATECProductoPedido()
                {
                    nombre = nombre_Material,
                    Quantity = cantidad
                };
                listaProductos.Add(productoPedido);
            }
            System.Diagnostics.Debug.Print(JsonConvert.SerializeObject(pedido).ToString());

            using (var client = new HttpClient())
            {
                var response = client.PostAsync(urlBase + "Pedido",
                    new StringContent(JsonConvert.SerializeObject(pedido).ToString(),
                        Encoding.UTF8, "application/json"))
                        .Result;
            }
            ConnectionDB.Instance.update_Divided_in(dividedID, "Status", "true");
        }
    }

    public class EPATECMaterial
    {
        public string nombre { get; set; }
        public long id_Sucursal { get; set; }
        public long Cedula_Provedor { get; set; }
        public string Descripcion { get; set; }
        public bool Exento { get; set; }
        public int Precio { get; set; }
        public int Cantidad_Disponible { get; set; }
        public string categoria { get; set; }
    }

    public class EPATECProductoPedido{
        public string nombre { get; set; }
        public int Quantity { get; set; }
    }


    public class EPATECPedido{
        /// Propiedad de Cedula
        public long id_Pedido { get; set; }
        /// Propiedad de Nombre
        public long Cedula_Cliente { get; set; }
        /// Propiedad de Apellidos
        public long id_Sucursal { get; set; }
        /// Propiedad de Nombre
        public string Telefono { get; set; }
        /// Propiedad de Nombre
        public string Hora { get; set; }
        public List<EPATECProductoPedido> productos { get; set; }
    }

}