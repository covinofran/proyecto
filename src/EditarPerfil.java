import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import org.mindrot.jbcrypt.BCrypt;

public class EditarPerfil {
	private JComboBox<String> tipoComboBox;
	private JPasswordField contrasenaField;
	private JButton cargarImagenButton;
	private JButton enviarButton;
	private JButton volverButton;
	private JButton eliminarButton;
	private String url;
	private JPanel panelSesionInferior;

	public EditarPerfil(JPanel panelSesion, Usuario userActual) {

		
		this.url=userActual.getUrl();
		
		// Crea un nuevo JPanel para mostrar los datos de la tienda
		panelSesionInferior = new JPanel();
		panelSesionInferior.setLayout(new GridLayout(5, 2));
		panelSesionInferior.add(new JLabel("Editar Perfil del usuario: "+userActual.getNombreUsuario()));
		panelSesionInferior.add(new JLabel());
		panelSesionInferior.add(new JLabel("Tipo de Usuario:"));
		tipoComboBox = new JComboBox<>(new String[] { "Cliente", "Tienda" });
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

				Usuario datosUsuario = new Usuario(userActual.getNombreUsuario(), hashed, url, salt, tipo);
				datosUsuario.update();
				Tienda tienda = new Tienda(userActual.getNombreUsuario(), userActual.getNombreUsuario(), url);
				if (tipo == "Tienda") {
					// CARGARLA EN LA BASE DE DATOS, ANTES PREGUNTAR SI EXISTE
					tienda.create();
					System.out.println(tienda.toString());
				} else if (tipo == "Cliente") {
					tienda.delete();
				}

				System.out.println(datosUsuario.toString());
			}
		});

		volverButton = new JButton("Volver");
		volverButton.addActionListener((ActionListener) new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				panelSesion.removeAll();
				panelSesion.revalidate();
				panelSesion.repaint();
				Sesion sesion = Sesion.getInstancia(userActual);
				sesion.cargarTiendas();
			}
		});
		eliminarButton = new JButton("Borrar");
		eliminarButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int confirmacion = JOptionPane.showConfirmDialog(null,
						"¿Estás seguro de que deseas eliminar el usuario? Sera eliminada su tienda en caso de poseer una", "Confirmar Eliminación",
						JOptionPane.YES_NO_OPTION);
				if (confirmacion == JOptionPane.YES_OPTION) {

					if ("Tienda".equals(userActual.getTipo())) {
						Tienda tienda = new Tienda(null, userActual.getNombreUsuario(), null);
						tienda.delete();

					}
					userActual.delete();
					Sesion sesion = Sesion.getInstancia(userActual);
					sesion.salir();
					new Menu();

				}
			}
		});

		panelSesionInferior.add(cargarImagenButton);
		panelSesionInferior.add(enviarButton);
		panelSesionInferior.add(eliminarButton);
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
