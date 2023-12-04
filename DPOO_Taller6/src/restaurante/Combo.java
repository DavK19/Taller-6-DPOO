package restaurante;

import java.util.ArrayList;

public class Combo implements Producto {

	private String nombreCombo;
	private double descuento;
	private int precioBase;
	private Double precioFinal;
	private ArrayList<Producto> itemsCombo = new ArrayList<Producto>();

	public Combo(String nombre, double descuento) {
		this.nombreCombo = nombre;
		this.descuento = descuento / 100;
	}

	public void agregarItemCombo(Producto producto) {
		this.itemsCombo.add(producto);
		precioBase = precioBase + producto.getPrecio();
		precioFinal = precioBase - (precioBase * descuento);

	}

	public String getNombre() {
		return this.nombreCombo;
	}

	public int getPrecio() {
		int retorno = this.precioFinal.intValue();
		return retorno;
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
		if (!(otro instanceof Combo)) {
			return false;
		} else {
			Combo otroCombo = (Combo) otro;
			if (!(nombreCombo.equals(otroCombo.getNombre()))) {
				return false;

			} else {
				return true;

			}
		}
	}
}
