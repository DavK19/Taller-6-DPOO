package restaurante;

import java.util.ArrayList;

public class ProductoAjustado implements Producto {
	private ProductoMenu base;
	private String nombre;
	private int precio;
	private ArrayList<Ingrediente> agregados = new ArrayList<Ingrediente>();
	private ArrayList<Ingrediente> eliminados = new ArrayList<Ingrediente>();

	public ProductoAjustado(ProductoMenu productoBase) {
		this.base = productoBase;
		this.nombre = base.getNombre();
		this.precio = base.getPrecio();
	}

	public String getNombre() {
		return this.nombre;
	}

	public int getPrecio() {
		return this.precio;
	}

	public String getFactura() {
		int L;
		String precioString = String.valueOf(getPrecio());

		L = 60 - (getNombre().length() + precioString.length());

		String nombreFinal = getNombre() + ".".repeat(L) + precioString + "\n";

		if (agregados.size() > 0) {
			nombreFinal = nombreFinal + "\t" + "con adicion de" + "\n";
			for (Ingrediente agregado : agregados) {
				nombreFinal = nombreFinal + "\t\t-" + agregado.getNombre() + "\n";
			}
		}

		if (eliminados.size() > 0) {
			nombreFinal = nombreFinal + "\t" + "sin \n";

			for (Ingrediente eliminado : eliminados) {
				nombreFinal = nombreFinal + "\t\t-" + eliminado.getNombre() + "\n";
			}
		}

		return nombreFinal;
	}

	public void agregarIngrediente(Ingrediente agregado) {
		this.agregados.add(agregado);
		precio = precio + agregado.getCosto();
	}

	public void eliminarIngredientes(Ingrediente eliminar) {

		precio = precio - eliminar.getCosto();

		if (agregados.contains(eliminar)) {
			agregados.remove(eliminar);
		} else {
			this.eliminados.add(eliminar);
		}

	}

	public int sizeAgregados() {
		return agregados.size();
	}

	public int sizeEliminados() {
		return eliminados.size();
	}

	public ArrayList<Ingrediente> getAgregados() {
		return agregados;
	}

	public ArrayList<Ingrediente> getEliminados() {
		return eliminados;
	}

	@Override
	public boolean equals(Object otro) {
		if (!(otro instanceof ProductoAjustado)) {
			return false;
		} else {
			ProductoAjustado otroProducto = (ProductoAjustado) otro;
			if (!(nombre.equals(otroProducto.getNombre()))) {
				return false;
			} else {
				if ((sizeEliminados() == otroProducto.sizeEliminados())
						&& (sizeAgregados() == otroProducto.sizeAgregados())) {
					ArrayList<Ingrediente> otroAgregados = otroProducto.getAgregados();

					boolean encontrado = false;
					int i = 0;
					while (!encontrado && i < agregados.size()) {
						Ingrediente origen = agregados.get(i);
						if (otroAgregados.contains(origen)) {
							encontrado = encontrado || true;
						} else {
							encontrado = encontrado || false;
						}
						i++;
					}

					ArrayList<Ingrediente> otroEliminados = otroProducto.getEliminados();
					i = 0;
					while (!encontrado && i < eliminados.size()) {
						Ingrediente origen = eliminados.get(i);
						if (otroEliminados.contains(origen)) {
							encontrado = encontrado || true;
						} else {
							encontrado = encontrado || false;
						}
					}

					if (!encontrado) {
						return false;
					} else {
						return true;
					}
				} else {
					return false;
				}
			}
		}
	}
}
