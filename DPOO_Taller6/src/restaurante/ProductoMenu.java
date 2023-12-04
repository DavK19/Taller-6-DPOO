package restaurante;

public class ProductoMenu implements Producto {
	private String nombre;
	private int precioBase;

	public ProductoMenu(String nombre, int precioBase) {
		this.nombre = nombre;
		this.precioBase = precioBase;
	}

	public String getNombre() {
		return this.nombre;
	}

	public int getPrecio() {
		return this.precioBase;
	}

	public String getFactura() {
		int L;
		String precioString = String.valueOf(getPrecio());

		L = 60 - (getNombre().length() + precioString.length());

		String factura = String.format("%s" + ".".repeat(L) + "%s" + "\n", getNombre(), precioString);

		return factura;
	}
	@Override
	public boolean equals(Object otro) {
		if (!(otro instanceof ProductoMenu)) {
			return false;
		}else {
			ProductoMenu otroProducto = (ProductoMenu) otro;
			if (!(nombre.equals(otroProducto.getNombre()))) {
				return false;
			}else {
				return true;
			}
		}
	}
}
