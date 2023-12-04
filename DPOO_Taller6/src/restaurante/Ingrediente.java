package restaurante;

public class Ingrediente {
	private String nombre;
	private int costoAdicional;

	public Ingrediente(String nombre, int costoAdicional) {
		this.nombre = nombre;
		this.costoAdicional = costoAdicional;
	}

	public String getNombre() {
		return this.nombre;
	}

	public int getCosto() {
		return this.costoAdicional;
	}
	
	@Override
	public boolean equals(Object o) {
		if (o instanceof Ingrediente) {
			Ingrediente comparar = (Ingrediente) o;
			if (this.nombre.equals(comparar.getNombre())) {
				return true;
			} else {
				return false;
			}
			
		} else {
			return false;
		}
	}
}
