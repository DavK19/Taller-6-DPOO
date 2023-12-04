package restaurante;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;

import exceptions.HamburguesaException;
import exceptions.IngredienteRepetidoException;
import exceptions.ProductoRepetidoException;
import exceptions.SobreprecioPedidoException;
import exceptions.*;

public class Restaurante {

	private ArrayList<ProductoMenu> listaProductos = new ArrayList<ProductoMenu>();
	private ArrayList<Ingrediente> listaIngredientes = new ArrayList<Ingrediente>();
	private ArrayList<Combo> listaCombos = new ArrayList<Combo>();
	private ArrayList<Bebida> listaBebidas = new ArrayList<Bebida>();
	private ArrayList<Pedido> listaPedidos = new ArrayList<Pedido>();
	private File archivoProductos;
	private File archivoIngredientes;
	private File archivoCombos;
	private Pedido pedidoActual;
	private int modificacion;

	public Restaurante(int modificacion) throws Exception, HamburguesaException{
		this.modificacion = modificacion;
		if (modificacion == 1) {
			this.archivoProductos = new File("data/menu.txt");
			this.archivoIngredientes = new File("data/ingredientes.txt");
			this.archivoCombos = new File("data/combos.txt");
			
			cargarInformacionRestaurante(this.archivoProductos, this.archivoIngredientes, this.archivoCombos);
		} else if (modificacion == 2) {
			File archivoBebidas;
			this.archivoProductos = new File("data/menuPlatos.txt");
			this.archivoIngredientes = new File("data/ingredientes.txt");
			this.archivoCombos = new File("data/combos.txt");
			archivoBebidas = new File("data/menuBebidas.txt");
				cargarInformacionModificada(this.archivoProductos, this.archivoIngredientes, this.archivoCombos,
						archivoBebidas);
		}
	}

	public void cargarInformacionRestaurante(File archivoProductos, File archivoIngredientes, File archivoCombos)
			throws Exception, HamburguesaException {
		cargarMenu(archivoProductos);
		cargarIngredientes(archivoIngredientes);
		cargarCombos(archivoCombos);
	}

	public void cargarInformacionModificada(File archivoProductos, File archivoIngredientes, File archivoCombos,
			File archivoBebidas) throws Exception, HamburguesaException {
		cargarMenu(archivoProductos);
		cargarIngredientes(archivoIngredientes);
		cargarBebidas(archivoBebidas);
		cargarCombos(archivoCombos);

	}

	public void cargarBebidas(File archivoBebidas) throws Exception, HamburguesaException {
		FileReader fr = new FileReader(archivoBebidas);
		BufferedReader in = new BufferedReader(fr);
		String linea = in.readLine();

		while (linea != null) {
			String[] palabras = linea.split(";");
			Bebida nuevoProducto = new Bebida(palabras[0], Integer.parseInt(palabras[1]));
			if (listaBebidas.contains(nuevoProducto)) {
				throw new ProductoRepetidoException(nuevoProducto.getNombre());
			}else {
				listaBebidas.add(nuevoProducto);
			}
			linea = in.readLine();
		}

		in.close();
	}

	public void cargarMenu(File archivoProductos) throws Exception, HamburguesaException {
		FileReader fr = new FileReader(archivoProductos);
		BufferedReader in = new BufferedReader(fr);
		String linea = in.readLine();

		while (linea != null) {
			String[] palabras = linea.split(";");
			ProductoMenu nuevoProducto = new ProductoMenu(palabras[0], Integer.parseInt(palabras[1]));
			
			if (listaProductos.contains(nuevoProducto)) {
				throw new ProductoRepetidoException(nuevoProducto.getNombre());
			}else {
				listaProductos.add(nuevoProducto);
				linea = in.readLine();
			}
			
		}
		in.close();
	}

	public void cargarIngredientes(File archivoIngredientes) throws Exception, HamburguesaException {
		FileReader fr = new FileReader(archivoIngredientes);
		BufferedReader in = new BufferedReader(fr);
		String linea = in.readLine();

		while (linea != null) {
			String[] palabras = linea.split(";");
			Ingrediente nuevoIngrediente = new Ingrediente(palabras[0], Integer.parseInt(palabras[1]));
			
			if (listaIngredientes.contains(nuevoIngrediente)) {
				throw new IngredienteRepetidoException(nuevoIngrediente.getNombre());
			} else {
				listaIngredientes.add(nuevoIngrediente);
			}
			linea = in.readLine();
		}
		
		in.close();
	}

	public void cargarCombos(File archivoCombos) throws Exception, HamburguesaException {
		FileReader fr = new FileReader(archivoCombos);
		BufferedReader in = new BufferedReader(fr);
		String linea = in.readLine();

		while (linea != null) {

			String[] palabras = linea.split(";");
			Combo nuevoCombo = new Combo(palabras[0], Double.parseDouble(palabras[1].replace("%", "")));
			
			if(listaCombos.contains(nuevoCombo)) {
				throw new ProductoRepetidoException(nuevoCombo.getNombre());
			} else {
				for (int i = 2; i < palabras.length; i++) {
					String productoAgregar = palabras[i];
					Producto producto = encontrarProducto(productoAgregar);
					nuevoCombo.agregarItemCombo(producto);
				}
				listaCombos.add(nuevoCombo);
			}
			
			linea = in.readLine();

		}

		in.close();
	}

	public Producto encontrarProducto(String nombreProducto) {

		for (ProductoMenu producto : listaProductos) {

			if (producto.getNombre().equals(nombreProducto)) {

				return producto;

			}

		}
		if (modificacion == 2) {
			for (Bebida bebida : listaBebidas) {
				if (bebida.getNombre().equals(nombreProducto)) {

					return bebida;
				}
			}
		}
		return null;
	}

	public Ingrediente encontrarIngrediente(String nombreIngrediente) {

		for (Ingrediente ingrediente : listaIngredientes) {
			if (ingrediente.getNombre().equals(nombreIngrediente)) {

				return ingrediente;

			}
		}

		return null;
	}

	public void iniciarPedido(String nombreCliente, String direccionCliente) {
		pedidoActual = new Pedido(nombreCliente, direccionCliente);
	}

	public void agregarProducto(Producto producto) throws SobreprecioPedidoException {
		if(pedidoActual == null) {
			System.out.println("No tienes ningún pedido inicializado");
		}else {
			
			int precioActual = pedidoActual.getPrecioTotalPedido();
			
			if (precioActual + producto.getPrecio() > 150000) {
				throw new SobreprecioPedidoException();
			} else {
				pedidoActual.agregarProducto(producto);
				System.out.println(producto.getNombre() + "\t" + producto.getPrecio());
				System.out.println("El precio actual del pedido es: " + pedidoActual.getPrecioTotalPedido());
			}
		}
	}

	public boolean cerraryGuardarPedido() {

		boolean repetido = false;
		if (modificacion == 2) {
			for (Pedido pedido : listaPedidos) {
				if (pedido.equals(pedidoActual)) {
					repetido = true;
				}
			}
		}
		listaPedidos.add(pedidoActual);
		pedidoActual = null;
		return repetido;
	}

	public Pedido getPedidoEnCurso() {
		return pedidoActual;
	}

	public ArrayList<ProductoMenu> getListaProductos() {
		return listaProductos;
	}

	public ArrayList<Ingrediente> getListaIngredientes() {
		return listaIngredientes;
	}

	public ArrayList<Combo> getListaCombos() {
		return listaCombos;
	}

	public ArrayList<Bebida> getListaBebidas() {
		return listaBebidas;
	}

	public String getFactura() {
		return pedidoActual.generarFactura();
	}

	public int getIdPedidoActual() {
		return pedidoActual.getIdPedido();
	}

	public boolean cambiarPedido(int idPedido) {
		for (Pedido pedido : listaPedidos) {
			if (pedido.getIdPedido() == idPedido) {
				pedidoActual = pedido;
				return true;
			}
		}
		return false;
	}
}
