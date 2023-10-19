import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import org.mindrot.jbcrypt.BCrypt;

public class Perfil {
	private JComboBox<String> tipoComboBox;
	private JPasswordField contrasenaField;
	private JButton cargarImagenButton;
	private JButton enviarButton;
	private JButton volverButton;
	private String url;
	private Connection db = DatabaseSingleton.getConexion();
	private JPanel panelSesionInferior;
	public Perfil(JPanel panelSesion, Usuario userActual) {
		// System.out.println("Entro a la tienda " + tiendaActual.getnombreTienda());
		
		// Crea un nuevo JPanel para mostrar los datos de la tienda
		panelSesionInferior = new JPanel();
		panelSesionInferior.setLayout(new GridLayout(5, 2));

		panelSesionInferior.add(new JLabel("Tipo de Usuario:"));
		tipoComboBox = new JComboBox<>(new String[] { "Cliente", "Local" });
		panelSesionInferior.add(tipoComboBox);


		// Etiqueta y campo de contraseña
		panelSesionInferior.add(new JLabel("Contraseña:"));
		contrasenaField = new JPasswordField();
		panelSesionInferior.add(contrasenaField);

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
				
				// Salt generado para agregar al encriptado
				String salt = BCrypt.gensalt(12);
				// contraseña del usuario
				String contra = new String(contrasenaField.getPassword());
				// Contraseña + salt encriptado
				String hashed = BCrypt.hashpw(contra, salt);
				
				// Tipo de usuario(Cliente/Local)
				String tipo = (String) tipoComboBox.getSelectedItem();

				Usuario datosUsuario = new Usuario(userActual.getId(), hashed, url, salt, tipo);
				UserOperation operacionesUsuario = new UserOperation(db);
				operacionesUsuario.updateUser(datosUsuario);
				if (tipo == "local") {
					Tienda tienda = new Tienda("Tienda Pepito", userActual.getId(), url);
					//CARGARLA EN LA BASE DE DATOS, ANTES PREGUNTAR SI EXISTE
				}
				datosUsuario.toString();

				
			}
		});

		volverButton = new JButton("Volver");
		volverButton.addActionListener((ActionListener) new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				panelSesion.removeAll();
				panelSesion.revalidate();
				panelSesion.repaint();
				Sesion sesion = Sesion.getInstancia(userActual.getId());
				sesion.cargarTiendas();
			}
		});
		panelSesionInferior.add(cargarImagenButton);
		panelSesionInferior.add(enviarButton);
		panelSesionInferior.add(volverButton);
		panelSesion.add(panelSesionInferior);
	}

	private void browseImage() {
		JFileChooser fileChooser = new JFileChooser();
		int result = fileChooser.showOpenDialog(panelSesionInferior);
		if (result == JFileChooser.APPROVE_OPTION) {
			url = fileChooser.getSelectedFile().getPath();
		}
	}
}
