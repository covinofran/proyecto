import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Connection;
import java.sql.SQLException;

public class IniciarSesion {
	private JFrame vIniciarSesion;
	private JTextField nombreTextField;
	private JPasswordField contraseñaField;
	private Connection db;

	public IniciarSesion() {

		db = DatabaseSingleton.getConexion();
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

				UserOperation operacionesUsuario = new UserOperation(db);
				if (operacionesUsuario.autenticarUsuario(nombreUsuario, contra, vIniciarSesion)) {

					vIniciarSesion.dispose();
					Sesion.getInstancia(nombreUsuario);
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
		vIniciarSesion.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				try {
					db.close();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				System.exit(0);
			}
		});
		// EL PANEL SE VA A QUITAR
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
