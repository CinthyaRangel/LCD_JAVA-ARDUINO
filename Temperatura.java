package PantallaLCD;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import com.panamahitek.PanamaHitek_Arduino;
import gnu.io.SerialPortEvent;
import gnu.io.SerialPortEventListener;

public class Temperatura extends JPanel implements SerialPortEventListener { // IMPLEMENTAR EVENTO PARA ARDUINO Y HEREDAR DE JPANEL
	
	private static final long serialVersionUID = 1L;
	private PanamaHitek_Arduino arduino; 										// DECLARAR OBJETO
	private JPanel ventilacion; 												// DECLARAR JPANEL												// DECLARAR OBJETO TIPO CHARTPANEL
	private Font font = new Font("Franklin Gothic Demi Cond", Font.PLAIN, 20); 	// DECLARAR OBJETO FONT
	private JLabel temperatura, ventilador; 									// DECLARAR OBJETO JLABEL
	private Image imagen;														// DECLARAR IMAGEN
	private int segundos = 0; 													// DECLARAR VARIABLE QUE CONTARÁ LOS SEGUNDOS
	private double tempAux; 													// DECLARAR OBJETO
	private static final int TEMPERATURA_MINIMA = 30; 							// DECLARAR VARIABLE QUE TIENE TEMPERATURA MÍNIMA
	public static boolean mostrarTemperatura = false; 							// DECLARAR VARIABLE BOOLEANA QUE DETERMINA SI SE DEBE MOSTRAR O NO LA TEMPERATURA
	
	/*EL CONSTRUCTOR RECIBE UN OBJETO TOPO PANAMAHITEK*/
	public Temperatura(PanamaHitek_Arduino arduino) {
		setLayout(new BorderLayout()); 				// ESTABLECER LAYOUT DEL PANEL
		this.arduino = arduino; 					// HACER VARIABLE GLOBAL IGUAL A LA LOCAL
		setImagen("img/schwarz.jpg"); 				// ESTABLECER IMAGEN DE FONDO

		add(getVentilacion(), BorderLayout.NORTH); 	// AGREGAR PANEL EN EL NORTE
	}
	
	/*MÉTODO QUE RETORNA PANEL CON LABELS QUE MUESTRAN EL ESTADO DEL VENTILADOR Y LA TEMPERATURA*/
	private JPanel getVentilacion() {
		ventilacion = new JPanel(new FlowLayout(FlowLayout.RIGHT)); // INSTANCIAR JPANEL
		temperatura = new JLabel(" "); 								// INSTANCIAR JLABEL
		ventilador = new JLabel("Ventilador: "); 					// INSTANCIAR JLABEL

		ventilacion.setOpaque(false); 								// QUITAR OPACIDAD
		temperatura.setForeground(Color.WHITE);						// ESTABLACER COLOR DEL TEXTO A BLANCO
		temperatura.setFont(font); 									// ESTABLECER FUENTE
		ventilador.setForeground(Color.WHITE); 						// ESTABLECER COLOR DE TEXTO A BLANCO
		ventilador.setFont(font); 									// ESTABLECER FUENTE

		ventilacion.add(ventilador); 								// AGREGAR LABEL
		ventilacion.add(temperatura); 								// AGREGAR LABEL
		return ventilacion; 										// RETORNAR PANEL
	}
		
	/*MÉTODO QUE ESTABLECE IMAGEN DE FONDO EN LA VENTANA. RECIBE COMO PARÁMETRO LA RUTA DE LA IMAGEN*/
	private void setImagen(String nombreImagen) {
		if (nombreImagen != null) {														// SI EL OBJETO NO SE HA INSTANCIADO
			imagen = new ImageIcon(getClass().getResource(nombreImagen)).getImage();	// INSTANCIAR OBJETO
		} else {																		// SI YA FUE INSTANCIADO
			imagen = null;																// HACER EL OBJETO NULO
		}
		repaint();																		// REPINTAR EL PANEL
	}
	
	/*MÉTODO QUE PINTA EN PANEL*/
	@Override
	public void paint(Graphics g) {
		if (imagen != null) { 											// SI EL OBJETO YA HA SIDO INSTANCIADO
			g.drawImage(imagen, 0, 0, getWidth(), getHeight(), this); 	// DIBUJAR LA IMAGEN EN PANEL
			setOpaque(false); 											// QUITAR OPACIDAD EN PANEL
		} else { 														// SI NO HA SIDO INSTANCIADO
			setOpaque(true); 											// PONER OPACIDAD
		}
		super.paint(g); 												// PINTAR IMAGEN EN PANTALLA
	}

	/*MÉTODO QUE MANEJA EL EVENTO DEL ARDUINO Y RECIBE UN OBJETO DE TIPO SerialPortEvent*/
	@Override
	public void serialEvent(SerialPortEvent ev) {
		if(arduino.isMessageAvailable() && mostrarTemperatura) { 			// SI HAY ALGÚN MENSAJE RECIBIDO Y MOSTRARTEMPERATURA ES VERDADERO
			tempAux = Double.parseDouble(arduino.printMessage()); 			// OBTENER MENSAJE Y CONVERTIRLO A DOUBLE
			temperatura.setText(tempAux + " °C"); 							// ESTABLECER MENSAJE EN JLABEL CON TEMPERATURA
			segundos++; 									// AGREGAR LOS SEGUNDOS Y LA TEMPERATURA EN LA GRÁFICA
			try {
				if(mostrarTemperatura) 										// SI MOSTRAR TEMPERATURA ES VERDADERO
					arduino.sendData(String.format("%.2f °C", tempAux)); 	// ENVIAR A LA PANTALLA LA TEMPERATURA
			} catch (Exception e) {
				System.out.println(e.getMessage());							// MOSTRAR MENSAJE DE ERROR
			}
			
			if(tempAux >= TEMPERATURA_MINIMA) 								// SI LA TEMPERATURA SUPERA LA MÍNIMA
				ventilador.setText("Ventilador: ENCENDIDO "); 				// ESTABLECER EN LABEL QUE EL VENTILADOR ESTÁ ENCENDIDO
			else 															// SI NO ES LA MÍNIMA
				ventilador.setText("Ventilador: APAGADO "); 				// ESTABLECER EN LABEL QUE EL VENTILADOR ESTÁ APAGADO
		}
	}
}