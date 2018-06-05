package PantallaLCD;

import java.awt.BorderLayout;
import javax.swing.JFrame;

public class Test {

	public static void main(String[] args) {
		JFrame frame = new JFrame("LCD JAVA-ARDUINO"); // instancia del JFram
		
		frame.setLayout(new BorderLayout()); 							
		frame.setSize(700, 300); 	//Damos tamaño a la ventana									
		frame.setLocationRelativeTo(null); 								
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 	// el programa deja de ejecutarse al Cerrar la ventana
		frame.add(new Contenedora()); 	//se agrega la clase contenedora al JFrame								
		frame.setVisible(true); 	// hacemos la ventana visible									// HACER VENTANA VISIBLE
	}
}