package test;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import exceptions.SobreprecioPedidoException;
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
		
		assertThrows(SobreprecioPedidoException.class, ()->{
			for (int i = 0; i <= 10; i++) {
				restaurante.agregarProducto(wrap);
			}
		}
		);
		
	}
	
	@Test
	public void romperLimite2() {
		restaurante.iniciarPedido("pepito", "loquesea");
		ProductoMenu mediaLibra = productos.get(5); 
		
		assertThrows(SobreprecioPedidoException.class, ()->{
			for (int i = 0; i <= 6; i++) {
				restaurante.agregarProducto(mediaLibra);
			}
		}
		);
		
	}
	
	
}
