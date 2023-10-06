import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;

public class Sesion {
	//Ventana de sesion ya inciada
	public Sesion(String nombreUsuario) {
		Connection conexion = DatabaseSingleton.getConexion();
		UserOperation operacionesUsuario = new UserOperation(conexion);
		Usuario userActual= operacionesUsuario.readUsuario(nombreUsuario);
		JFrame vSesion = new JFrame("Sesión Iniciada - Usuario: " + userActual.getId());
		vSesion.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		
		ImageIcon logo = new ImageIcon("images\\logo.png");
		vSesion.setIconImage(logo.getImage());
		
		/*
		 * 
		 * FALTA CORREGIR TODOS LOS ERRORES ANTES DE IMPLEMENTAR ESTO, COMO CARGAR BIEN
		 * LOS USUARIO E IMPLEMENTAR LOCALES CON SUS RESPECTIVOS ARTICULOS
		 * 
		 */
		
		JButton cerrarButton = new JButton("Cerrar Sesion");
		cerrarButton.addActionListener((ActionListener) new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				vSesion.dispose();
				new Menu();
			}
		});
		
		vSesion.add(cerrarButton);
		vSesion.setLayout(null);
		vSesion.setBounds(0,0,800,600);
		vSesion.setLocationRelativeTo(null);
		vSesion.setVisible(true);
		cerrarButton.setBounds(650,10,120,40);
		cerrarButton.setFocusable(false);
		/*
		 * Cliente cliente= new Cliente(conexion.traerCliente());
		 * cliente.agregarArticulo("Tomate");
		 */
	}
}
