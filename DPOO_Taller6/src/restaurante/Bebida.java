package restaurante;

public class Bebida implements Producto {

	private String nombre;
	private int precio;

	public Bebida(String nombre, int precio) {
		this.nombre = nombre;
		this.precio = precio;
	}

	public String getNombre() {
		return nombre;
	}

	public int getPrecio() {
		return precio;
	}

	public String getFactura() {
		int L = 60 - (nombre.length() + String.valueOf(precio).length());
		String factura = nombre + ".".repeat(L) + String.valueOf(precio) + "\n";

		return factura;
	}
	
	@Override
	public boolean equals(Object otro) {
		if (!(otro instanceof Bebida)) {
			return false;
		}else {
			Bebida otroProducto = (Bebida) otro;
			if (!(nombre.equals(otroProducto.getNombre()))) {
				return false;
			}else {
				return true;
			}
		}
	}

}
