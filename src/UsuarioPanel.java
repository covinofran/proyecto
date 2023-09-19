import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import org.mindrot.jbcrypt.BCrypt;

public class UsuarioPanel extends JPanel {
	/*
	 * NO SABEMOS PORQUE USUARIOPANEL DA ESE ERROR AUN, SI SACAMOS EL EXTENDS JPANEL NO LO TIRA Y DEBERIAMOS AGREGAR FRAME. 
	 * A TODOS LOS METODOS PERO DA ERROR EL METODO BROWSEIMAGE()
	 * */
	private JComboBox<String> tipoComboBox;
	private JTextField nombreTextField;
	private JPasswordField contrasenaField;
	private JButton cargarImagenButton;
	private JButton enviarButton;
	private Imagen img;
	private JFrame frame;
	public UsuarioPanel() {
		
		
		DatabaseConnection conexion = DatabaseConnection.getInstancia();
		
		frame = new JFrame("Iniciar Sesión");
	    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(new GridLayout(5, 2));
		
		// Etiqueta y campo de selección para el tipo de usuario
		add(new JLabel("Tipo de Usuario:"));
		tipoComboBox = new JComboBox<>(new String[] { "Cliente", "Local" });
		add(tipoComboBox);

		// Etiqueta y campo de texto para el nombre de usuario
		add(new JLabel("Nombre de Usuario:"));
		nombreTextField = new JTextField();
		add(nombreTextField);

		// Etiqueta y campo de contraseña
		add(new JLabel("Contraseña:"));
		contrasenaField = new JPasswordField();
		add(contrasenaField);

		// Botón para cargar una imagen (puedes implementar esto por separado)
		cargarImagenButton = new JButton("Cargar Imagen");
		cargarImagenButton.addActionListener((ActionListener) new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				browseImage();
			}
		});
		
		add(cargarImagenButton);
		enviarButton = new JButton("Enviar");
		enviarButton.addActionListener((ActionListener) new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Implementa la lógica para cargar una imagen aquí
				// Puedes usar JFileChooser u otra biblioteca para cargar imágenes

				String usuario = nombreTextField.getText();
				String salt =BCrypt.gensalt(12);
				String contra = new String(contrasenaField.getPassword()); 
				String hashed = BCrypt.hashpw(contra, salt);
				String seleccion = (String) tipoComboBox.getSelectedItem();
				/*
				 * LA VARIABLE CONTRA Y HASHED SON USADAS PARA TESTEAR SI EL ENCRIPTADO FUNCIONA, HAY QUE ACOMODAR
				 * LA IMPLEMENTACION DE ESTO, EL IF SIGUIENTE ESTA PARA IMPRIMIR EN CONSOLA UNICAMENTE
				 * 
				 * */
				if (BCrypt.checkpw(contra, hashed))
					System.out.println("It matches");
				else
					System.out.println("It does not match");
				
				

				
				/*
				 * PRINTS PARA VER EN CONSOLA LOS DATOS CARGADOS UNICAMENTE
				 * VER COMO CARGA LOS DATOS Y LAS MODIFICACIONES QUE SE HACEN EN QUE AFECTAN, TENER EN CUENTA ESTO PARA 
				 * EDITAR LOS MISMO
				 * 
				 * */
				
				
				if ("Cliente".equals(seleccion)) {
					// Acciones para el tipo de usuario "Cliente"
					UsuarioFactory clienteFactory = new ClienteFactory();
					Usuario cliente = clienteFactory.crearUsuario(usuario, hashed, img, salt);

					// Imprimir información de los usuarios
					System.out.println("Usuario Cliente:");
					System.out.println("ID: " + cliente.getId());
					System.out.println("Contraseña: " + cliente.getContraseña());
					Imagen imagenCliente = cliente.getImagen();
					System.out.println("Imagen:");
					System.out.println("  Nombre: " + imagenCliente.getNombre());
					System.out.println("  Ruta: " + imagenCliente.getUrl());
					conexion.guardarUser(cliente);
				} else if ("Local".equals(seleccion)) {
					// Acciones para el tipo de usuario "Local"
					System.out.println("Acciones para Local");
					UsuarioFactory localFactory = new LocalFactory();
					Usuario local = localFactory.crearUsuario(usuario, hashed, img, salt);
					System.out.println("\nUsuario Local:");
					System.out.println("ID: " + local.getId());
					System.out.println("Contraseña: " + local.getContraseña());
					Imagen imagenLocal = local.getImagen();
					System.out.println("Imagen:");
					System.out.println("  Nombre: " + imagenLocal.getNombre());
					System.out.println("  Ruta: " + imagenLocal.getUrl());
					conexion.guardarUser(local);
				}

			}
		});
		add(cargarImagenButton);
		add(enviarButton);
	}
	
	private void browseImage() {
        JFileChooser fileChooser = new JFileChooser();
        int result = fileChooser.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            String imagePath = fileChooser.getSelectedFile().getPath();
            String imageName = fileChooser.getSelectedFile().getName();
            // Eliminar la extensión del nombre del archivo
            imageName = removerExtension(imageName);
            img = new Imagen(imageName, imagePath);
        }
    }
	private String removerExtension(String imagen) {
		int ultimoPunto = imagen.lastIndexOf('.');
        if (ultimoPunto > 0) {
        	imagen = imagen.substring(0, ultimoPunto);
        }
        return imagen;
	}
}