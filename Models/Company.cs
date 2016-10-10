using System.Collections.Generic;

//Contiene las clases para el manejo temporal de los datos en memoria
namespace EPATEC2.Company
{
    public class Cliente
    {

        private long cedula_Cliente;
        private string nombre;
        private string apellidos;
        private int penalizacion;
        private string residencia;
        private string nacimiento;
        private string telefono;

        /// Propiedad de Cedula
        public long Cedula_Cliente
        {
            get { return cedula_Cliente; }
            set { cedula_Cliente = value; }
        }

        /// Propiedad de Nombre
        public string Nombre
        {
            get { return nombre; }
            set { nombre = value; }
        }

        /// Propiedad de Apellidos
        public string Apellidos
        {
            get { return apellidos; }
            set { apellidos = value; }
        }

        /// Propiedad de Nombre
        public int Penalizacion
        {
            get { return penalizacion; }
            set { penalizacion = value; }
        }

        /// Propiedad de Nombre
        public string Residencia
        {
            get { return residencia; }
            set { residencia = value; }
        }

        /// Propiedad de Nombre
        public string Nacimiento
        {
            get { return nacimiento; }
            set { nacimiento = value; }
        }

        /// Propiedad de Nombre
        public string Telefono
        {
            get { return telefono; }
            set { telefono = value; }
        }

    }

    public class Sucursal
    {

        private int Id;

        public Sucursal()
        { }

        /// Propiedad de id
        public int id
        {
            get { return Id; }
            set { Id = value; }
        }
    }

    public class Producto
    {
        private string Nombre;
        private long sucursal;
        private long cedula_Provedor;
        private string Categoria;
        private string descripcion;
        private bool exento;
        private int cantidad_Disponible;

        /// Propiedad de nombre
        public string nombre
        {
            get { return Nombre; }
            set { Nombre = value; }
        }

        /// Propiedad de nombre
        public long id_Sucursal
        {
            get { return sucursal; }
            set { sucursal = value; }
        }

        /// Propiedad de nombre
        public long Cedula_Provedor
        {
            get { return cedula_Provedor; }
            set { cedula_Provedor = value; }
        }

        public string Descripcion
        {
            get { return descripcion; }
            set { descripcion = value; }
        }

        public bool Exento
        {
            get { return exento; }
            set { exento = value; }
        }

        public int Cantidad_Disponible
        {
            get { return cantidad_Disponible; }
            set { cantidad_Disponible = value; }
        }

        public string categoria
        {
            get { return Categoria; }
            set { Categoria = value; }
        }

    }

    public class Proovedor
    {
        private long cedula_Provedor;
        private string nombre;
        private string apellidos;
        private string residencia;
        private string nacimiento;

        public Proovedor()
        { }

        /// Propiedad de Cedula
        public long Cedula_Proveedor
        {
            get { return cedula_Provedor; }
            set { cedula_Provedor = value; }
        }

        /// Propiedad de Nombre
        public string Nombre
        {
            get { return nombre; }
            set { nombre = value; }
        }

        /// Propiedad de Apellidos
        public string Apellidos
        {
            get { return apellidos; }
            set { apellidos = value; }
        }

        /// Propiedad de Nombre
        public string Residencia
        {
            get { return residencia; }
            set { residencia = value; }
        }

        /// Propiedad de Nombre
        public string Nacimiento
        {
            get { return nacimiento; }
            set { nacimiento = value; }
        }

    }

    public class ProductoPedido
    {
        private string name;
        private int quantity;

        public string nombre
        {
            get { return name; }
            set { name = value; }
        }
        public int Quantity
        {
            get { return quantity; }
            set { quantity = value; }
        }
    }

    public class Pedido
    {
        private long id;
        private long cedula;
        private long sucursal;
        private string telefono;
        private string hora;
        private List<ProductoPedido> Productos;


        /// Propiedad de Cedula
        public long id_Pedido
        {
            get { return id; }
            set { id = value; }
        }

        /// Propiedad de Nombre
        public long Cedula_Cliente
        {
            get { return cedula; }
            set { cedula = value; }
        }

        /// Propiedad de Apellidos
        public long id_Sucursal
        {
            get { return sucursal; }
            set { sucursal = value; }
        }

        /// Propiedad de Nombre
        public string Telefono
        {
            get { return telefono; }
            set { telefono = value; }
        }

        /// Propiedad de Nombre
        public string Hora
        {
            get { return hora; }
            set { hora = value; }
        }

        public List<ProductoPedido> productos
        {
            get { return Productos; }
            set { Productos = value; }
        }

    }

    public class Empleado
    {
        private long id;
        private long sucursal;
        private string nombre;
        private string Puesto;

        /// Propiedad de id
        public long id_Empleado
        {
            get { return id; }
            set { id = value; }
        }

        /// Propiedad de id
        public long id_Sucursal
        {
            get { return sucursal; }
            set { sucursal = value; }
        }

        /// Propiedad de id
        public string Nombre
        {
            get { return nombre; }
            set { nombre = value; }
        }

        /// Propiedad de id
        public string puesto
        {
            get { return Puesto; }
            set { Puesto = value; }
        }
    }

    public class Categoria
    {
        private string nombre;
        private string descripcion;

        /// Propiedad de id
        public string Nombre
        {
            get { return nombre; }
            set { nombre = value; }
        }

        /// Propiedad de id
        public string Descripcion
        {
            get { return descripcion; }
            set { descripcion = value; }
        }
    }

    public class ProductosVentas
    {
        public string Nombre;
        public long Sucursal;
        public int Cantidad;
    }

    public class SucursalVentas
    {
        public long Sucursal;
        public int CantVentas;
    }
}
