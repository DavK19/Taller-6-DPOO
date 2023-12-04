package test;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.RepeatedTest;

import exceptions.HamburguesaException;
import restaurante.Restaurante;

public class TestCargaDatos{
	
	private File menuOriginal = new File("data/menu.txt");
	private ArrayList<String> datosProductos = new ArrayList<>();
	@SuppressWarnings("unused")
	private Restaurante restaurante;
	
	@BeforeEach
	public void setUp() {
		
		try {
			BufferedReader br = new BufferedReader(new FileReader(menuOriginal));
			String linea = br.readLine();
			
			while (linea != null) {
				datosProductos.add(linea);
				linea = br.readLine();
			}
			
			br.close();

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
				
		
		try {
			BufferedWriter bw = new BufferedWriter(new FileWriter(menuOriginal));
			bw.write("");
			bw.write("piña;2000\n");
			bw.write("piña;2000");
			bw.close();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@AfterEach
	public void retornar() {
		try {
			BufferedWriter bw = new BufferedWriter(new FileWriter(menuOriginal));
			bw.write("");
			for(String dato: datosProductos) {
				bw.write(dato + "\n");
			}
			bw.close();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@RepeatedTest(5)
	//aqui revisa que se produzca la excepcion cuando hay un producto repetido, que en este caso es queso
	public void testCargarDatosPiña() {
		try {
			restaurante = new Restaurante(1);
		} catch(HamburguesaException e) {
			System.out.println(e.getLocalizedMessage());
			assertTrue(e.getLocalizedMessage().equals("Hubo un producto repetido: piña"));
		} catch (Exception e) {
			System.out.println(e.getLocalizedMessage());
			assertTrue(e.getMessage().equals("1"));
		}
	}
}