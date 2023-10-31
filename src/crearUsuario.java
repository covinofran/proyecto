import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import org.mindrot.jbcrypt.BCrypt;
import java.sql.Connection;
import java.sql.SQLException;

public class CrearUsuario {

	private JComboBox<String> tipoComboBox;
	private JTextField nombreTextField;
	private JPasswordField contrasenaField;
	private JButton cargarImagenButton;
	private JButton enviarButton;
	private JButton volverButton;
	private String url;
	private JFrame vCrearUsuario;
	private Connection db = DatabaseSingleton.getConexion();

	// Ventana de creacion del usuario
	public CrearUsuario() {

		vCrearUsuario = new JFrame("Crear Usuario");
		vCrearUsuario.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		vCrearUsuario.setLayout(new GridLayout(5, 2));
		vCrearUsuario.setResizable(false);

		ImageIcon logo = new ImageIcon("images\\logo.png");
		vCrearUsuario.setIconImage(logo.getImage());

		// Etiqueta y campo de selección para el tipo de usuario
		vCrearUsuario.add(new JLabel("Tipo de Usuario:"));
		tipoComboBox = new JComboBox<>(new String[] { "Cliente", "Tienda" });
		vCrearUsuario.add(tipoComboBox);

		// Etiqueta y campo de texto para el nombre de usuario
		vCrearUsuario.add(new JLabel("Nombre de Usuario:"));
		nombreTextField = new JTextField();
		vCrearUsuario.add(nombreTextField);

		// Etiqueta y campo de contraseña
		vCrearUsuario.add(new JLabel("Contraseña:"));
		contrasenaField = new JPasswordField();
		vCrearUsuario.add(contrasenaField);

		// Botón para cargar una imagen
		cargarImagenButton = new JButton("Cargar Imagen");
		cargarImagenButton.addActionListener((ActionListener) new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				browseImage();
			}
		});

		enviarButton = new JButton("Enviar");
		enviarButton.addActionListener((ActionListener) new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Nombre del usuario
				String nombre = nombreTextField.getText();
				// Salt generado para agregar al encriptado
				String salt = BCrypt.gensalt(12);
				// contraseña del usuario
				String contra = new String(contrasenaField.getPassword());
				// Contraseña + salt encriptado
				String hashed = BCrypt.hashpw(contra, salt);
				// Tipo de usuario(Cliente/Local)
				String tipo = (String) tipoComboBox.getSelectedItem();
				/*
				 * LA VARIABLE CONTRA Y HASHED SON USADAS PARA TESTEAR SI EL ENCRIPTADO
				 * FUNCIONA, HAY QUE ACOMODAR LA IMPLEMENTACION DE ESTO, EL IF SIGUIENTE ESTA
				 * PARA IMPRIMIR EN CONSOLA UNICAMENTE
				 */

				/*
				 * if (BCrypt.checkpw(contra, hashed)) System.out.println("It matches"); else
				 * System.out.println("It does not match");
				 */
				Usuario datosUsuario = new Usuario(nombre, hashed, url, salt, tipo);
				datosUsuario.create();
				if (tipo == "Tienda") {
					Tienda tienda = new Tienda(nombre, nombre, url);

					tienda.create();
					System.out.println(tienda.toString());
				}
				System.out.println(datosUsuario.toString());
				vCrearUsuario.dispose();
				new Menu();
			}
		});

		volverButton = new JButton("Volver");
		volverButton.addActionListener((ActionListener) new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				vCrearUsuario.dispose();
				new Menu();
			}
		});
		vCrearUsuario.addWindowListener(new WindowAdapter() {
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

		vCrearUsuario.add(cargarImagenButton);
		vCrearUsuario.add(enviarButton);
		vCrearUsuario.add(volverButton);

		vCrearUsuario.setVisible(true);
		vCrearUsuario.setSize(400, 300);
		vCrearUsuario.setLocationRelativeTo(null);
	}

	// Metodo para abrir una ventana y obtener el path de un archivo
	private void browseImage() {
		JFileChooser fileChooser = new JFileChooser();
		int result = fileChooser.showOpenDialog(vCrearUsuario);
		if (result == JFileChooser.APPROVE_OPTION) {
			url = fileChooser.getSelectedFile().getPath();
		}
	}
}