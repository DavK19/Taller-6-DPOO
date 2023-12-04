package restaurante;

import java.util.ArrayList;

public class Pedido {
	private static int numeroPedidos = 0;
	private int idPedido;
	private String nombreCliente;
	private String direccionCliente;
	private int precioNeto = 0;
	private ArrayList<Producto> listaProductos = new ArrayList<Producto>();

	public Pedido(String nombreCliente, String direccionCliente) {
		this.nombreCliente = nombreCliente;
		this.direccionCliente = direccionCliente;
		this.idPedido = numeroPedidos;
		Pedido.numeroPedidos++;
	}

	public void agregarProducto(Producto producto) {
		listaProductos.add(producto);
		precioNeto = precioNeto + producto.getPrecio();
	}

	public int getIdPedido() {
		return this.idPedido;
	}

	public int getNumeroPedidos() {
		return Pedido.numeroPedidos;
	}

	public String generarFactura() {
		String facturaFinal;
		String id = "Id de la factura: " + String.valueOf(idPedido) + "\n\n";
		String encabezado = String.format(
				"Nombre del cliente: " + "%s" + " ".repeat(5) + "\nDireccion del cliente: " + "%s" + "\n\n",
				nombreCliente, direccionCliente);
		String contenido = "";

		for (Producto producto : listaProductos) {
			contenido = contenido + producto.getFactura();
		}

		int precioSubtotal = this.precioNeto;
		int L = 60 - ("Subtotal".length() + String.valueOf(precioSubtotal).length());
		String subtotal = String.format("Subtotal" + ".".repeat(L) + "%d" + "\n", precioSubtotal);

		int precioIVA = getPrecioIVAPedido();
		L = 60 - ("Precio IVA".length() + String.valueOf(precioIVA).length());
		String IVA = String.format("Precio IVA" + ".".repeat(L) + "%d" + "\n", precioIVA);

		int precioFinal = getPrecioTotalPedido();
		L = 60 - ("Total".length() + String.valueOf(precioFinal).length());
		String total = String.format("Total" + ".".repeat(L) + "%d" + "\n", precioFinal);

		facturaFinal = id + encabezado + contenido + "_".repeat(60) + "\n" + subtotal + IVA + total;

		return facturaFinal;
	}

	private int getPrecioIVAPedido() {
		Double precioIVA = 0.19 * precioNeto;
		return precioIVA.intValue();
	}

	public int getPrecioTotalPedido() {
		return precioNeto + getPrecioIVAPedido();
	}

	public ArrayList<Producto> getListaProductos() {
		return listaProductos;
	}

	@Override
	public boolean equals(Object otro) {

		boolean iguales = true;
		int i = 0;
		while (iguales && i < listaProductos.size()) {
			Producto producto = listaProductos.get(i);
			Pedido otroPedido = (Pedido) otro;
			ArrayList<Producto> otroProductos = otroPedido.getListaProductos();
			if (otroProductos.size() == listaProductos.size()) {
				boolean productosIguales = false;
				int j = 0;
				while (!productosIguales && j < otroProductos.size()) {
					Producto compararProducto = otroProductos.get(j);
					if (producto.equals(compararProducto)) {
						productosIguales = true;
					} else {
						j++;
					}
					if (j == otroProductos.size()) {
						iguales = false;
					}
				}

			} else {
				iguales = false;
			}
			i++;
		}

		return iguales;
	}

}
