import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import org.mindrot.jbcrypt.BCrypt;

public class CrearUsuario {

	private JComboBox<String> tipoComboBox;
	private JTextField nombreTextField;
	private JPasswordField contrasenaField;
	private JButton cargarImagenButton;
	private JButton enviarButton;
	private JButton volverButton;
	private String url;
	private JFrame frame;
	private DatabaseConnection conexion = DatabaseConnection.getInstancia();

	public CrearUsuario() {
		frame = new JFrame("Crear Usuario");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(new GridLayout(5, 2));
		frame.setResizable(false);
		ImageIcon logo = new ImageIcon("images\\logo.png");
		frame.setIconImage(logo.getImage());

		// Etiqueta y campo de selección para el tipo de usuario
		frame.add(new JLabel("Tipo de Usuario:"));
		tipoComboBox = new JComboBox<>(new String[] { "Cliente", "Local" });
		frame.add(tipoComboBox);

		// Etiqueta y campo de texto para el nombre de usuario
		frame.add(new JLabel("Nombre de Usuario:"));
		nombreTextField = new JTextField();
		frame.add(nombreTextField);

		// Etiqueta y campo de contraseña
		frame.add(new JLabel("Contraseña:"));
		contrasenaField = new JPasswordField();
		frame.add(contrasenaField);

		// Botón para cargar una imagen
		cargarImagenButton = new JButton("Cargar Imagen");
		cargarImagenButton.addActionListener((ActionListener) new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				browseImage();
			}
		});
		frame.add(cargarImagenButton);
		enviarButton = new JButton("Enviar");
		enviarButton.addActionListener((ActionListener) new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				String usuario = nombreTextField.getText();
				String salt = BCrypt.gensalt(12);
				String contra = new String(contrasenaField.getPassword());
				String hashed = BCrypt.hashpw(contra, salt);
				String seleccion = (String) tipoComboBox.getSelectedItem();
				/*
				 * LA VARIABLE CONTRA Y HASHED SON USADAS PARA TESTEAR SI EL ENCRIPTADO
				 * FUNCIONA, HAY QUE ACOMODAR LA IMPLEMENTACION DE ESTO, EL IF SIGUIENTE ESTA
				 * PARA IMPRIMIR EN CONSOLA UNICAMENTE
				 */
				if (BCrypt.checkpw(contra, hashed))
					System.out.println("It matches");
				else
					System.out.println("It does not match");
				/*
				 * PRINTS PARA VER EN CONSOLA LOS DATOS CARGADOS UNICAMENTE VER COMO CARGA LOS
				 * DATOS Y LAS MODIFICACIONES QUE SE HACEN EN QUE AFECTAN, TENER EN CUENTA ESTO
				 * PARA EDITAR LOS MISMOS
				 */
				if ("Cliente".equals(seleccion)) {
					UsuarioFactory clienteFactory = new ClienteFactory();
					Usuario cliente = clienteFactory.crearUsuario(usuario, hashed, url, salt);
					
					System.out.println("Usuario Cliente:");
					System.out.println("ID: " + cliente.getId());
					System.out.println("Contraseña: " + cliente.getContraseña());
					System.out.println("Ruta Iamagen: " + cliente.getUrl());

					conexion.guardarUser(cliente);
				} else if ("Local".equals(seleccion)) {
					UsuarioFactory localFactory = new LocalFactory();
					Usuario local = localFactory.crearUsuario(usuario, hashed, url, salt);
					System.out.println("Usuario Local:");
					System.out.println("ID: " + local.getId());
					System.out.println("Contraseña: " + local.getContraseña());
					System.out.println("Ruta Imangen: " + local.getUrl());
					conexion.guardarUser(local);
				}
			}
		});
		volverButton = new JButton("Volver");
		volverButton.addActionListener((ActionListener) new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
				new Menu();
			}
		});
		frame.add(cargarImagenButton);
		frame.add(enviarButton);
		frame.add(volverButton);
		frame.setVisible(true);
		frame.setSize(400, 300);
		frame.setLocationRelativeTo(null);
	}

	private void browseImage() {
		JFileChooser fileChooser = new JFileChooser();
		int result = fileChooser.showOpenDialog(frame);
		if (result == JFileChooser.APPROVE_OPTION) {
			url = fileChooser.getSelectedFile().getPath();
		}
	}
}