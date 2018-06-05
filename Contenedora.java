package PantallaLCD;

import com.panamahitek.PanamaHitek_Arduino;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.Timer;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

public class Contenedora extends JPanel {

	private static final long serialVersionUID = 1L;
	private JFrame frameTemp = new JFrame(); 									
	private JLabel labelTitulo; 												
	private JButton btnTemp, btnHora, btnMensajes; 											
	private Timer timer; 														
	private Font font = new Font("Franklin Gothic Demi Cond", Font.PLAIN, 30); 
        //Instanciamos el objeto PanamaHitek_Arduino  usada para conectar arduino y Java
	private PanamaHitek_Arduino arduino = new PanamaHitek_Arduino(); 
	private Temperatura temp = new Temperatura(arduino);// instancia de clase temperatura
	private Manejadora manejadora = new Manejadora();// instancia de clase manejadora				
	
	public Contenedora() {
		setLayout(new BorderLayout()); 	
		add(getNorte(), BorderLayout.NORTH); 	//se agrega panel norte
		add(getCentro(), BorderLayout.CENTER); 	//se agrega panel centro
		realizarConexionArduino(); 				
	}
	
	private JPanel getNorte() {
		JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER)); 	
		labelTitulo = new JLabel("Elija una opción"); 				
		
		panel.setOpaque(false);
                panel.setBorder(new EmptyBorder(40, 0, 0, 0)); // establece el borde
		labelTitulo.setFont(font); 		     
		labelTitulo.setForeground(Color.BLACK);  //establece el color del negro
		panel.add(labelTitulo); 		 
		return panel; 			// retorna objeto 
	}
	
	//Panel donde se establece el formato del panel y los botonres
	private JPanel getCentro() {
		JPanel panel = new JPanel(new GridLayout(1, 3, 15, 0)); 		
		btnTemp = new JButton("Temperatura"); 	//boton temperatura
		btnHora = new JButton("Hora"); 		// boton hora
		btnMensajes = new JButton("Mensajes"); // boton Mensaje
		
		panel.setOpaque(false); 		
		panel.setBorder(new EmptyBorder(50, 50, 50, 50)); 			
		btnTemp.setOpaque(false); 						
		btnTemp.setContentAreaFilled(false); 					
		btnTemp.setForeground(Color.BLACK); 					
		btnTemp.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR)); 	
		btnTemp.setHorizontalTextPosition(SwingConstants.CENTER); 		
		btnTemp.setVerticalTextPosition(SwingConstants.BOTTOM); 		
		btnTemp.addActionListener(manejadora); 					
		btnHora.setOpaque(false); 						
		btnHora.setContentAreaFilled(false); 					
		btnHora.setForeground(Color.BLACK); 					
		btnHora.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR)); 	
		btnHora.setHorizontalTextPosition(SwingConstants.CENTER); 		
		btnHora.setVerticalTextPosition(SwingConstants.BOTTOM); 		
		btnHora.addActionListener(manejadora); 					
		btnMensajes.setOpaque(false); 						
		btnMensajes.setContentAreaFilled(false); 				
		btnMensajes.setForeground(Color.BLACK); 				
		btnMensajes.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR)); 	
		btnMensajes.setHorizontalTextPosition(SwingConstants.CENTER); 		
		btnMensajes.setVerticalTextPosition(SwingConstants.BOTTOM); 		
		btnMensajes.addActionListener(manejadora); 				
		
                //agrega los botones al panel
		panel.add(btnTemp); 						
		panel.add(btnHora); 						
		panel.add(btnMensajes); 					
		return panel; 						
	}
	
	
	//metodo usado para conectar el arduino
	@SuppressWarnings("deprecation")
	private void realizarConexionArduino() {
		try { 
                        //Raliza la conexión con el arduino con el puerto COM3
			arduino.ArduinoRXTX("COM3", 2000, 9600, temp); 	
		} catch (Exception e) {
			System.out.println(e.getMessage()); 						
		}
	}
	
	//Clase manejador que pone en acción los botones
	private class Manejadora implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent ev) {			
			if(ev.getSource() == btnTemp) 	//si el boton que lazo el evesto es btnTemp
				mostrarTemperatura(); 	// LLAMA al metodo Temperatura mostrarTemperatura
			
			if(ev.getSource() == btnHora)	//si el boton que lazo el evesto es btnHora
				mostrarHora();		//llama al metodo mostrarHora()
			
			if(ev.getSource() == btnMensajes)//si el boton que lazo el evesto es btnMensajes
				mostrarMensajes();	// llama a el metodo mostrarMensajes
		}
		
	}
	
	
	private void mostrarTemperatura() {
		Temperatura.mostrarTemperatura = true; 				
		//si el objeto esta intanciado detenerlo para agregar la hora	
		if(timer != null) 			
			timer.stop(); 						
		
		try {
                    //Se da formato al frame de Temperatura
			frameTemp.setTitle("Temperatura"); 			
			frameTemp.add(temp); 					
			frameTemp.setSize(600, 440); 				
			frameTemp.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); 	
                        frameTemp.setLocationRelativeTo(this); 			
			frameTemp.setVisible(false); 				
		} catch(Exception ex) {
			System.out.println(ex.getMessage()); 			
		}
	}
	
	/*MÉTODO QUE MUESTRA LA HORA EN LCD*/
	private void mostrarHora() {
		Temperatura.mostrarTemperatura = false; 			
		
		try {
			timer = new Timer(1000, new ActionListener() { 	//cada 1000 segundos se mostrara la hora en el arduino
				@Override
				public void actionPerformed(ActionEvent e) {
					try {
                                            //se crea un objeto de tipo date para obtener la hora con dicho formato
						Date date = new Date(); 	                           
						DateFormat hourFormat = new SimpleDateFormat("HH:mm:ss");    
						arduino.sendData(hourFormat.format(date));//se envia el mensaje al arduion
					} catch (Exception e1) {
						System.out.println(e1.getMessage()); 			    
					}
				}
			});
		} catch (Exception e) {
			System.out.println(e.getMessage()); 			
		}
		
		timer.start(); 							
	}
	
	//metodo que muestra el mensaje al usuario
	private void mostrarMensajes() {
            //al oprimir el boton deja de funcionar la temperatura para que se muestre el mensaje
            Temperatura.mostrarTemperatura = false; 				
		String mensaje = JOptionPane.showInputDialog("Ingrese mensaje");
		
		try {
			arduino.sendData(mensaje); 				// el mensaje de envia al arduino
		} catch (Exception e) {
			System.out.println("Error: " + e.getMessage()); 	// 
		} finally {
			if(timer != null) 					// Ssi timer esta instanciado se detiene
				timer.stop(); 					
		}
	}

}