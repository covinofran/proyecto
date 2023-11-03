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
	private JTextField nombreTextField;

	public EditarTienda(JPanel panelSesion, Usuario userActual) {

		tiendaActual = new Tienda(null, userActual.getNombreUsuario(), null);
		tiendaActual = tiendaActual.read();

		panelSesionInferior = new JPanel();
		panelSesionInferior.setLayout(new GridLayout(5, 2));

		panelSesionInferior.add(new JLabel("Nombre de la Tienda:"));
		nombreTextField = new JTextField();
		panelSesionInferior.add(nombreTextField);
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
				if (!"".equals(nombreTextField.getText())) {
					tiendaActual.setNombreTienda(nombreTextField.getText());
				}
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
	}

	private void browseImage() {
		JFileChooser fileChooser = new JFileChooser();
		int result = fileChooser.showOpenDialog(panelSesionInferior);
		if (result == JFileChooser.APPROVE_OPTION) {
			tiendaActual.setUrl(fileChooser.getSelectedFile().getPath());
		}
	}
}
