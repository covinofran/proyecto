import javax.swing.JFrame;

public class Sesion {
	public Sesion(String usuario) {
		JFrame ventanaPrincipal = new JFrame("Sesión Iniciada - Usuario: " + usuario);
		ventanaPrincipal.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		ventanaPrincipal.setSize(800, 600);
		/*
		 * 
		 * FALTA CORREGIR TODOS LOS ERRORES ANTES DE IMPLEMENTAR ESTO, COMO CARGAR BIEN
		 * LOS USUARIO E IMPLEMENTAR LOCALES CON SUS RESPECTIVOS ARTICULOS
		 * 
		 */
		ventanaPrincipal.setLocationRelativeTo(null);
		ventanaPrincipal.setVisible(true);
		/*
		 * Cliente cliente= new Cliente(conexion.traerCliente());
		 * cliente.agregarArticulo("Tomate");
		 */
	}
}
