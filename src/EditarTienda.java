import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class EditarTienda {

	private JButton cargarImagenButton;
	private JButton enviarButton;
	private JButton volverButton;
	private JPanel panelSesionInferior;
	private Tienda tiendaActual;
	private JTextField txtNombreTienda;
	private JTextField txtNombreArt;
	private JTextField txtPrecio;
	private JTextField txtCantidad;
	private JTextField txtUrl;

	public EditarTienda(JPanel panelSesion, Usuario userActual) {

		tiendaActual = new Tienda(null, userActual.getNombreUsuario(), null);
		tiendaActual = tiendaActual.read();

		panelSesionInferior = new JPanel();
		panelSesionInferior.setLayout(new GridLayout(5, 2));

		panelSesionInferior.add(new JLabel("Nombre de la Tienda: " + tiendaActual.getNombreTienda()));

		// Crea un nuevo JPanel para mostrar los datos de la tienda

		cargarImagenButton = new JButton("Cargar Imagen");
		cargarImagenButton.addActionListener((ActionListener) new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				browseImage();
			}
		});

		enviarButton = new JButton("Enviar");
		enviarButton.addActionListener((ActionListener) new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				tiendaActual.update();
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

		panelSesionInferior.add(cargarImagenButton);
		panelSesionInferior.add(enviarButton);
		panelSesionInferior.add(volverButton);
		panelSesion.add(panelSesionInferior);

		// Crear componentes
		JLabel lblNombreTienda = new JLabel("Nombre de la Tienda:");
		txtNombreTienda = new JTextField(20);

		JLabel lblNombreArt = new JLabel("Nombre del Artículo:");
		txtNombreArt = new JTextField(20);

		JLabel lblPrecio = new JLabel("Precio:");
		txtPrecio = new JTextField(20);

		JLabel lblCantidad = new JLabel("Cantidad:");
		txtCantidad = new JTextField(20);

		JLabel lblUrl = new JLabel("URL:");
		txtUrl = new JTextField(20);

		JButton btnCrearArticulo = new JButton("Crear Artículo");
		btnCrearArticulo.addActionListener(e -> crudArticulo("nuevo"));

		JButton btnModificarArticulo = new JButton("Modificar Artículo");
		btnModificarArticulo.addActionListener(e -> crudArticulo("modificar"));

		JButton btnBorrarArticulo = new JButton("Borrar Artículo");
		btnBorrarArticulo.addActionListener(e -> crudArticulo("eliminar"));

		// Crear panel y agregar componentes
		JPanel panelArt = new JPanel();
		panelArt.setLayout(new GridLayout(7, 2));
		panelArt.add(lblNombreTienda);
		panelArt.add(txtNombreTienda);
		panelArt.add(lblNombreArt);
		panelArt.add(txtNombreArt);
		panelArt.add(lblPrecio);
		panelArt.add(txtPrecio);
		panelArt.add(lblCantidad);
		panelArt.add(txtCantidad);
		panelArt.add(lblUrl);
		panelArt.add(txtUrl);
		panelArt.add(btnCrearArticulo);
		panelArt.add(btnModificarArticulo);
		panelArt.add(btnBorrarArticulo);
		panelArt.setVisible(true);

		panelSesion.add(panelArt);
	}

	private void browseImage() {
		JFileChooser fileChooser = new JFileChooser();
		int result = fileChooser.showOpenDialog(panelSesionInferior);
		if (result == JFileChooser.APPROVE_OPTION) {
			tiendaActual.setUrl(fileChooser.getSelectedFile().getPath());
		}
	}

	private void crudArticulo(String accion) {
		// Obtener la información de los campos de texto
		String nombreTienda = txtNombreTienda.getText();
		String nombreArt = txtNombreArt.getText();
		double precio = Double.parseDouble(txtPrecio.getText());
		int cantidad = Integer.parseInt(txtCantidad.getText());
		String url = txtUrl.getText();

		// Crear un nuevo objeto Articulo con la información proporcionada
		Articulo nuevoArticulo = new Articulo(nombreTienda, nombreArt, precio, cantidad, url);

		if (accion.equals("nuevo")) {
			nuevoArticulo.create();
		} else {
			if (accion.equals("modificar")) {
				nuevoArticulo.update();
			} else {
				if (accion.equals("eliminar")) {
					nuevoArticulo.delete();
				}

			}
		}

	}

}
