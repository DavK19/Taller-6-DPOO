package consola;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;

import exceptions.HamburguesaException;
import exceptions.SobreprecioPedidoException;
import restaurante.ProductoMenu;
import restaurante.ProductoAjustado;
import restaurante.Bebida;
import restaurante.Combo;
import restaurante.Ingrediente;
import restaurante.Restaurante;

public class Aplicacion {

	private Restaurante restaurante;
	private ArrayList<ProductoMenu> listaProductos;
	private ArrayList<Ingrediente> listaIngredientes;
	private ArrayList<Combo> listaCombos;
	private ArrayList<Bebida> listaBebidas;

	public void ejecutarAplicacion() {

		boolean working = true;
		int opcion;
		int modificacion = 0;
		
		while(modificacion == 0) {
			System.out.println("Desea utilizar la version base del programa o la version modificada?");
			System.out.println("1. Version base");
			System.out.println("2. Version Modificada");
			
			try {
			modificacion = Integer.parseInt(input(""));
			} catch (NumberFormatException e) {
				modificacion = 0;
				System.out.println("No ingresaste una opción válida");
			}
		}

		
		try {
			restaurante = new Restaurante(modificacion);
		} catch (HamburguesaException e) {
			System.out.println("Se encontraron productos repetidos al cargar los datos, la información detallada a continuación:\n" + e.getLocalizedMessage());
		} catch (Exception e) {
			System.out.println("Ocurrio un error cargando los datos " + e.getLocalizedMessage());
			e.printStackTrace();
		}
		
		listaProductos = restaurante.getListaProductos();
		listaIngredientes = restaurante.getListaIngredientes();
		listaCombos = restaurante.getListaCombos();

		if (modificacion == 1) {

			try {
				while (working) {
					mostrarMenu();
					opcion = Integer.parseInt(input("Ingrese la opcion a elegir"));
					if (opcion == 1) {
						String nombreCliente = input("Ingrese el nombre del cliente");
						String direccionCliente = input("Ingrese la direccion del cliente");

						restaurante.iniciarPedido(nombreCliente, direccionCliente);
					} else if (opcion == 2) {
						agregarProducto();

					} else if (opcion == 3) {
						cerrarPedido(modificacion);

					} else if (opcion == 4) {
						buscarPedido();
					} else if (opcion == 5) {
						working = false;
						System.out.println("Gracias por utilizar el asistente del restaurante");

					}

				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else if (modificacion == 2) {
			listaBebidas = restaurante.getListaBebidas();
			try {
				while (working) {
					mostrarMenu();
					opcion = Integer.parseInt(input("Ingrese la opcion a elegir"));
					if (opcion == 1) {
						String nombreCliente = input("Ingrese el nombre del cliente");
						String direccionCliente = input("Ingrese la direccion del cliente");

						restaurante.iniciarPedido(nombreCliente, direccionCliente);
					} else if (opcion == 2) {
						agregarProductoModificacion();

					} else if (opcion == 3) {
						cerrarPedido(modificacion);

					} else if (opcion == 4) {
						buscarPedido();
					} else if (opcion == 5) {
						working = false;
						System.out.println("Gracias por utilizar el asistente del restaurante");

					} else {
						System.out.println("No ingreso una opcion correcta, vuelva a intentarlo");
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}

	private void buscarPedido() {
		System.out.println("Estas seguro de buscar otro pedido? El pedido actual se borrará");
		System.out.println("1. Si");
		System.out.println("2. No");
		int numero = Integer.parseInt(input("Ingrese la opcion que desea elegir"));
		if (numero == 1) {
			int idPedido = Integer.parseInt(input("Ingrese el id del pedido a buscar"));
			boolean existe = restaurante.cambiarPedido(idPedido);

			if (existe) {
				System.out.println(String.format("El pedido %d ha sido cargado", idPedido));
				System.out.println(restaurante.getFactura());
			} else {
				System.out.println("El pedido no esta registrado en la base de datos");
			}

		} else if (numero == 2) {
			System.out.println("Solicitud cancelada");
		} else {
			System.out.println("No ha ingresado una opcion adecuada");
		}
	}

	private void cerrarPedido(int modificacion) {
		System.out.println("Esta seguro de que desea cerrar el pedido");
		System.out.println("1. Si");
		System.out.println("2. No");

		int opcionElegida = Integer.parseInt(input("Ingrese la opcion que desea elegir"));

		if (opcionElegida == 1) {
			String factura = restaurante.getFactura();

			try {
				PrintWriter archivo = new PrintWriter(
						String.format("facturas/%d.txt", restaurante.getIdPedidoActual()));
				archivo.println(factura);
				archivo.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			System.out.println("El pedido guardado es el siguiente:\n");
			System.out.println(factura);
			boolean existe = restaurante.cerraryGuardarPedido();
			if (modificacion == 2 && existe) {
				System.out.println("Ademas, existe al menos un pedido igual\n");
			}
		}
	}

	private void agregarProducto() {
		System.out.println("Que tipo de producto desea agregar: ");
		System.out.println("1. Producto del menu");
		System.out.println("2. Combo");

		int opcion = Integer.parseInt(input("Ingrese la opcion a elegir"));

		if (opcion == 1) {
			mostrarProductos();
			int productoElegido = Integer.parseInt(input("Ingrese el producto que desea elegir"));

			if (productoElegido >= 0 && productoElegido < listaProductos.size()) {
				ProductoMenu productoBase = listaProductos.get(productoElegido);
				System.out.println("Desea modificar el producto: ");
				System.out.println("1.Si");
				System.out.println("2.No");

				opcion = Integer.parseInt(input("Ingrese la opcion a elegir"));

				if (opcion == 1) {
					ProductoAjustado productoFinal = new ProductoAjustado(productoBase);
					boolean modificar = true;

					while (modificar) {

						System.out.println("Desea agregar o eliminar un ingrediente?");
						System.out.println("1.Agregar");
						System.out.println("2.Eliminar");

						int opcionIngrediente = Integer.parseInt(input("Ingrese la opcion a elegir que desea elegir"));

						if (opcionIngrediente == 1) {
							mostrarIngredientes();

							int numeroIngrediente = Integer.parseInt(input("Ingrese el ingrediente que desea elegir"));

							Ingrediente agregar = listaIngredientes.get(numeroIngrediente);

							productoFinal.agregarIngrediente(agregar);

						} else if (opcionIngrediente == 2) {
							mostrarIngredientes();

							int numeroIngrediente1 = Integer.parseInt(input("Ingrese el ingrediente que desea elegir"));

							Ingrediente quitar = listaIngredientes.get(numeroIngrediente1);

							productoFinal.eliminarIngredientes(quitar);

						} else {
							System.out.println("No ingreso una opcion valida");
						}
						System.out.println("Desea seguir modificando el producto?");
						System.out.println("1.Si");
						System.out.println("2.No");

						int continuar = Integer.parseInt(input("Ingrese la opcion a elegir"));

						if (continuar == 2) {
							modificar = false;
							try {
								restaurante.agregarProducto(productoBase);
							} catch (SobreprecioPedidoException e) {
								System.out.println(e.getLocalizedMessage());
							}

						} else {
							System.out.println("No ingreso una opcion valida");
						}
					}

				} else if (opcion == 2) {
					try {
						restaurante.agregarProducto(productoBase);
					} catch (SobreprecioPedidoException e) {
						System.out.println(e.getLocalizedMessage());
					}

				} else {
					System.out.println("Ingrese una opcion valida");
				}

			} else {
				System.out.println("No es un producto valido");
			}
		} else if (opcion == 2) {
			mostrarCombos();
			int comboElegido = Integer.parseInt(input("Ingrese el producto que desea elegir"));

			if (comboElegido >= 0 && comboElegido < listaCombos.size()) {
				Combo comboAgregar = listaCombos.get(comboElegido);

				try {
					restaurante.agregarProducto(comboAgregar);
				} catch (SobreprecioPedidoException e) {
					System.out.println(e.getLocalizedMessage());
				}

			}
		} else {
			System.out.println("No has ingresado una opcion valida");
		}

	}

	private void agregarProductoModificacion() {
		System.out.println("Que tipo de producto desea agregar: ");
		System.out.println("1. Producto del menu");
		System.out.println("2. Bebida");
		System.out.println("3. Combo");

		int opcion = Integer.parseInt(input("Ingrese la opcion a elegir"));

		if (opcion == 1) {
			mostrarProductos();
			int productoElegido = Integer.parseInt(input("Ingrese el producto que desea elegir"));

			if (productoElegido >= 0 && productoElegido < listaProductos.size()) {
				ProductoMenu productoBase = listaProductos.get(productoElegido);
				System.out.println("Desea modificar el producto: ");
				System.out.println("1.Si");
				System.out.println("2.No");

				opcion = Integer.parseInt(input("Ingrese la opcion a elegir"));

				if (opcion == 1) {
					ProductoAjustado productoFinal = new ProductoAjustado(productoBase);
					boolean modificar = true;

					while (modificar) {

						System.out.println("Desea agregar o eliminar un ingrediente?");
						System.out.println("1.Agregar");
						System.out.println("2.Eliminar");

						int opcionIngrediente = Integer.parseInt(input("Ingrese la opcion a elegir que desea elegir"));

						if (opcionIngrediente == 1) {
							mostrarIngredientes();

							int numeroIngrediente = Integer.parseInt(input("Ingrese el ingrediente que desea elegir"));

							Ingrediente agregar = listaIngredientes.get(numeroIngrediente);

							productoFinal.agregarIngrediente(agregar);

						} else if (opcionIngrediente == 2) {
							mostrarIngredientes();

							int numeroIngrediente1 = Integer.parseInt(input("Ingrese el ingrediente que desea elegir"));

							Ingrediente quitar = listaIngredientes.get(numeroIngrediente1);

							productoFinal.eliminarIngredientes(quitar);

						} else {
							System.out.println("No ingreso una opcion valida");
						}
						System.out.println("Desea seguir modificando el producto?");
						System.out.println("1.Si");
						System.out.println("2.No");

						int continuar = Integer.parseInt(input("Ingrese la opcion a elegir"));

						if (continuar == 2) {
							modificar = false;
							try {
								restaurante.agregarProducto(productoFinal);
							} catch (SobreprecioPedidoException e) {
								System.out.println(e.getLocalizedMessage());
							}

						} else {
							System.out.println("No ingreso una opcion valida");
						}
					}

				} else if (opcion == 2) {
					try {
						restaurante.agregarProducto(productoBase);
					} catch (SobreprecioPedidoException e) {
						System.out.println(e.getLocalizedMessage());
					}

				} else {
					System.out.println("Ingrese una opcion valida");
				}

			} else {
				System.out.println("No es un producto valido");
			}
		} else if (opcion == 2) {
			mostrarBebidas();
			int bebidaElegida = Integer.parseInt(input("Ingrese el producto que desea elegir"));

			if (bebidaElegida >= 0 && bebidaElegida < listaBebidas.size()) {
				Bebida bebidaAgregar = listaBebidas.get(bebidaElegida);

				try {
					restaurante.agregarProducto(bebidaAgregar);
				} catch (SobreprecioPedidoException e) {
					System.out.println(e.getLocalizedMessage());
				}

			} else {
				System.out.println("No es un producto valido");
			}

		} else if (opcion == 3) {
			mostrarCombos();
			int comboElegido = Integer.parseInt(input("Ingrese el producto que desea elegir"));

			if (comboElegido >= 0 && comboElegido < listaCombos.size()) {
				Combo comboAgregar = listaCombos.get(comboElegido);

				try {
					restaurante.agregarProducto(comboAgregar);
				} catch (SobreprecioPedidoException e) {
					System.out.println(e.getLocalizedMessage());
				}

			} else {
				System.out.println("No es un producto valido");
			}
		} else {
			System.out.println("No has ingresado una opcion valida");
		}

	}

	public void mostrarMenu() {
		System.out.println("\nBienvenido al asistente para tomar pedidos del restaurante ¿Que desea hacer?");
		System.out.println("1.Tomar un nuevo pedido");
		System.out.println("2.Agregar un nuevo elemento al pedido");
		System.out.println("3.Cerrar y guardar el pedido");
		System.out.println("4.Consultar un pedido dado su Id");
		System.out.println("5.Salir del programa");

	}

	public void mostrarProductos() {
		for (int i = 0; i < listaProductos.size(); i++) {
			String nombre = listaProductos.get(i).getNombre();
			System.out.println(String.format("%d. %s", i, nombre));
		}
	}

	public void mostrarIngredientes() {
		for (int i = 0; i < listaIngredientes.size(); i++) {
			String nombre = listaIngredientes.get(i).getNombre();
			System.out.println(String.format("%d. %s", i, nombre));
		}
	}

	public void mostrarCombos() {
		for (int i = 0; i < listaCombos.size(); i++) {
			String nombre = listaCombos.get(i).getNombre();
			System.out.println(String.format("%d. %s", i, nombre));
		}
	}

	public void mostrarBebidas() {
		for (int i = 0; i < listaBebidas.size(); i++) {
			String nombre = listaBebidas.get(i).getNombre();
			System.out.println(String.format("%d. %s", i, nombre));
		}
	}

	/**
	 * Este método sirve para imprimir un mensaje en la consola pidiéndole
	 * información al usuario y luego leer lo que escriba el usuario.
	 * 
	 * @param mensaje El mensaje que se le mostrará al usuario
	 * @return La cadena de caracteres que el usuario escriba como respuesta.
	 */

	public String input(String mensaje) {
		try {
			System.out.print(mensaje + ": ");
			BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
			return reader.readLine();
		} catch (IOException e) {
			System.out.println("Error leyendo de la consola");
			e.printStackTrace();
		}
		return null;
	}

	public static void main(String[] args) {

		Aplicacion aplicacion = new Aplicacion();
		aplicacion.ejecutarAplicacion();

	}
}
