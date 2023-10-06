import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;

public class IniciarSesion {
	private JFrame vIniciarSesion;
	private JTextField nombreTextField;
	private JPasswordField contraseñaField;
	private Connection conexion;

	public IniciarSesion() {
		
		conexion = DatabaseSingleton.getConexion();

		vIniciarSesion = new JFrame("Iniciar Sesión");
		vIniciarSesion.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		vIniciarSesion.setSize(300, 150);
		vIniciarSesion.setResizable(false);
		vIniciarSesion.setLayout(new BorderLayout());

		ImageIcon logo = new ImageIcon("images\\logo.png");
		vIniciarSesion.setIconImage(logo.getImage());

		JPanel panel = new JPanel(new GridLayout(3, 2));

		JLabel nombreLabel = new JLabel("Usuario:");
		nombreTextField = new JTextField();

		JLabel contraseñaLabel = new JLabel("Contraseña:");
		contraseñaField = new JPasswordField();

		JButton iniciarSesionButton = new JButton("Iniciar Sesión");
		iniciarSesionButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				String nombreUsuario = nombreTextField.getText();
				String contra = new String(contraseñaField.getPassword());

				UserOperation operacionesUsuario = new UserOperation(conexion);
				if (operacionesUsuario.autenticarUsuario(nombreUsuario, contra, vIniciarSesion)) {

					vIniciarSesion.dispose();
					new Sesion(nombreUsuario);
				}
			}
		});

		contraseñaField.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				iniciarSesionButton.doClick(); // Simula la acción del botón al presionar "Enter" en el campo de texto
			}
		});

		JButton volverButton = new JButton("Volver");
		volverButton.addActionListener((ActionListener) new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				vIniciarSesion.dispose();
				new Menu();
			}
		});
		//EL PANEL SE VA A QUITAR
		panel.add(nombreLabel);
		panel.add(nombreTextField);
		panel.add(contraseñaLabel);
		panel.add(contraseñaField);
		panel.add(volverButton);
		panel.add(iniciarSesionButton);

		vIniciarSesion.add(panel, BorderLayout.CENTER);
		vIniciarSesion.setVisible(true);
		vIniciarSesion.setLocationRelativeTo(null);
	}

}
