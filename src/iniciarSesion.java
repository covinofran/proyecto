import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;

public class IniciarSesion {
	private JFrame frame;
	private JTextField usuarioTextField;
	private JPasswordField contraseñaField;
	private Connection conexion = DatabaseSingleton.getConexion();

	public IniciarSesion() {

		frame = new JFrame("Iniciar Sesión");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(300, 150);
		frame.setResizable(false);
		frame.setLayout(new BorderLayout());
		ImageIcon logo = new ImageIcon("images\\logo.png");
		frame.setIconImage(logo.getImage());

		JPanel panel = new JPanel(new GridLayout(3, 2));

		JLabel usuarioLabel = new JLabel("Usuario:");
		usuarioTextField = new JTextField();

		JLabel contraseñaLabel = new JLabel("Contraseña:");
		contraseñaField = new JPasswordField();

		JButton iniciarSesionButton = new JButton("Iniciar Sesión");
		iniciarSesionButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String usuario = usuarioTextField.getText();
				String contra = new String(contraseñaField.getPassword());

				// ACA COAMPRUEBA SI EL USUARIO Y LA CONTRASEÑA ESTAN BIEN
				UserOperation userOp= new UserOperation(conexion);
				if (userOp.autenticarUsuario(usuario, contra, frame)) {

					frame.dispose();
					new Sesion(usuario);
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
				frame.dispose();
				new Menu();
			}
		});

		panel.add(usuarioLabel);
		panel.add(usuarioTextField);
		panel.add(contraseñaLabel);
		panel.add(contraseñaField);
		panel.add(volverButton);
		panel.add(iniciarSesionButton);

		frame.add(panel, BorderLayout.CENTER);
		frame.setVisible(true);
		frame.setLocationRelativeTo(null);
	}

}
