import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class VentanaInicioSesion {
	private JFrame frame;
	private JTextField usuarioTextField;
	private JPasswordField contraseñaField;

	public VentanaInicioSesion() {
		frame = new JFrame("Iniciar Sesión");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(300, 150);
		frame.setLayout(new BorderLayout());

		JPanel panel = new JPanel(new GridLayout(3, 2));

		JLabel usuarioLabel = new JLabel("Usuario:");
		usuarioTextField = new JTextField();

		JLabel contraseñaLabel = new JLabel("Contraseña:");
		contraseñaField = new JPasswordField();

		JButton iniciarSesionButton = new JButton("Iniciar Sesión");
		iniciarSesionButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String usuario = usuarioTextField.getText();
				char[] contraseña = contraseñaField.getPassword();

				/*
				 *  POR AHORA SOLO TESTEA SI EL USUARIO QUE INGRESO ES ADMIN ADMIN, FALTA IMPLEMENTAR QUE VERIFIQUE EL USER
				 * EN LA BASE DE DATOS, TRAERLO, DESENCRIPTAR Y HACER LA COMPROBACION DE DATOS.
				 * 
				 * */
				if (usuario.equals("admin") && new String(contraseña).equals("admin")) {
					mostrarVentanaPrincipal(usuario);
				} else {
					JOptionPane.showMessageDialog(frame, "Credenciales incorrectas", "Error",
							JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		contraseñaField.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				iniciarSesionButton.doClick(); // Simula la acción del botón al presionar "Enter" en el campo de texto
			}
		});

		panel.add(usuarioLabel);
		panel.add(usuarioTextField);
		panel.add(contraseñaLabel);
		panel.add(contraseñaField);
		panel.add(new JLabel()); // Espacio en blanco
		panel.add(iniciarSesionButton);

		frame.add(panel, BorderLayout.CENTER);
		frame.setVisible(true);
		frame.setLocationRelativeTo(null);
	}

	private void mostrarVentanaPrincipal(String usuario) {
		frame.dispose(); // Cierra la ventana de inicio de sesión

		JFrame ventanaPrincipal = new JFrame("Sesión Iniciada - Usuario: " + usuario);
		ventanaPrincipal.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		ventanaPrincipal.setSize(800, 600);
		/*
		 * 
		 * FALTA CORREGIR TODOS LOS ERRORES ANTES DE IMPLEMENTAR ESTO, COMO CARGAR BIEN LOS USUARIO E IMPLEMENTAR LOCALES
		 * CON SUS RESPECTIVOS ARTICULOS
		 * 
		 * */
		// Aquí puedes agregar los elementos que deseas mostrar en la ventana principal,
		// como la imagen y otros componentes.
		ventanaPrincipal.setLocationRelativeTo(null);
		ventanaPrincipal.setVisible(true);
	}

}
