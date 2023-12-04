package test;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import exceptions.SobrePrecioPedidoExcepition;
import restaurante.ProductoMenu;
import restaurante.Restaurante;

public class TestLimiteProductos {

	private Restaurante restaurante;
	ArrayList<ProductoMenu> productos;

	public TestLimiteProductos() {
		try {
			restaurante = new Restaurante(1);
			productos = restaurante.getListaProductos();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@AfterEach
	public void cerrarPedidos() {
		restaurante.cerraryGuardarPedido();
	}

	@Test
	public void romperLimite1() {
		restaurante.iniciarPedido("juanito", "whatever");
		ProductoMenu wrap = productos.get(12);

		assertThrows(SobrePrecioPedidoExcepition.class, () -> {
			for (int i = 0; i <= 10; i++) {
				restaurante.agregarProducto(wrap);
			}
		});

	}

	@Test
	public void romperLimite2() {
		restaurante.iniciarPedido("pepito", "loquesea");
		ProductoMenu mediaLibra = productos.get(5);

		assertThrows(SobrePrecioPedidoExcepition.class, () -> {
			for (int i = 0; i <= 6; i++) {
				restaurante.agregarProducto(mediaLibra);
			}
		});

	}

	@Test
	public void facturaGuardada() {
		restaurante.iniciarPedido("pepito", "loquesea");
		ProductoMenu mediaLibra = productos.get(5);

		
		for (int i = 0; i <= 2; i++) {
			try {
				restaurante.agregarProducto(mediaLibra);
			} catch (SobrePrecioPedidoExcepition e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		String factura = restaurante.getFactura();

		try {
			PrintWriter archivo = new PrintWriter(String.format("facturas/%d.txt", restaurante.getIdPedidoActual()));
			archivo.println(factura);
			archivo.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		File facturaGuardada = new File("facturas/0.txt");

		assertTrue(facturaGuardada.exists());
	}

}
