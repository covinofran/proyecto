import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Sesion implements Observer {

	private List<Tienda> tiendaList;
	private JFrame vSesion;
	private Connection db = DatabaseSingleton.getConexion();
	private JPanel panelSesion;
	private static Sesion sesion;
	private Usuario userActual;
	private JLabel imagenPerfil;
	private JButton cerrarButton;
	private JButton modificarButton;
	private JPanel tiendaPanel;
	private JLabel carritoImg;
	private JLabel cantCarrito;
	private CarritoCompras carrito;

	// Ventana de sesion ya inciada
	private Sesion(Usuario userActual) {

		this.userActual = userActual;

		vSesion = new JFrame("Sesión Iniciada - Usuario: " + userActual.getNombreUsuario());
		vSesion.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

		ImageIcon logo = new ImageIcon("images\\logo.png");
		vSesion.setIconImage(logo.getImage());

		imagenPerfil = reescalarLabel(userActual.getUrl(), 50, 50);

		carritoImg = reescalarLabel("images\\carrito.png", 30, 30);

		carrito = new CarritoCompras(userActual);
		cantCarrito = new JLabel("0");

		cerrarButton = new JButton("Cerrar Sesion");
		cerrarButton.addActionListener((ActionListener) new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				salir();
				new Menu();
			}
		});
		vSesion.addWindowListener(new WindowAdapter() {
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

		vSesion.add(imagenPerfil);
		imagenPerfil.setBounds(0, 0, 50, 50);
		imagenPerfil.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {

				panelSesion.removeAll();
				panelSesion.revalidate();
				panelSesion.repaint();
				new EditarPerfil(panelSesion, userActual);
			}
		});

		vSesion.add(carritoImg);
		carritoImg.setBounds(51, 10, 30, 30);

		vSesion.add(cantCarrito);
		cantCarrito.setBounds(82, 30, 20, 20);

		vSesion.add(cerrarButton);
		vSesion.setLayout(null);
		vSesion.setBounds(0, 0, 800, 600);
		vSesion.setLocationRelativeTo(null);
		vSesion.setVisible(true);
		cerrarButton.setBounds(650, 10, 120, 40);
		cerrarButton.setFocusable(false);

		panelSesion = new JPanel(new FlowLayout());
		cargarTiendas();
		// Agregar el panel de Sesion al JFrame
		vSesion.add(panelSesion);
		panelSesion.setBounds(0, 150, 800, 400);

		modificarButton = new JButton("Tu Tienda");

		modificarButton.addActionListener((ActionListener) new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				// AGREGAR LAS CARACTERISTICAS DE UNA TIENDA
				panelSesion.removeAll();
				panelSesion.revalidate();
				panelSesion.repaint();
				new EditarTienda(panelSesion, userActual);
			}
		});

		modificarButton.setBounds(500, 10, 120, 40);
		modificarButton.setFocusable(false);
		vSesion.add(modificarButton);

		if ("Tienda".equals(userActual.getTipo())) {
			modificarButton.setVisible(true);

		} else {
			modificarButton.setVisible(false);
		}
	}

	// METODOS
	public void cargarTiendas() {
		Tienda tienda = new Tienda(null, null, null);
		tiendaList = tienda.getAll();
		for (Tienda datos : tiendaList) {

			JLabel imageLabel = reescalarLabel(datos.getUrl(), 125, 125);
			JLabel nombreLabel = new JLabel(datos.getNombreTienda());

			imageLabel.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					panelSesion.removeAll();
					panelSesion.revalidate();
					panelSesion.repaint();
					new VerTienda(datos, panelSesion, userActual);

				}
			});
			tiendaPanel = new JPanel();
			tiendaPanel.setLayout(new BoxLayout(tiendaPanel, BoxLayout.Y_AXIS));
			tiendaPanel.add(imageLabel);
			tiendaPanel.add(nombreLabel);

			panelSesion.add(tiendaPanel);
		}
	}

	public static synchronized Sesion getInstancia(Usuario userActual) {
		if (sesion == null) {
			sesion = new Sesion(userActual);
		}
		return sesion;
	}

	public JLabel reescalarLabel(String path, int x, int y) {
		// Recibe una ruta de imagen, un ancho y un alto. retorna un label con la imagen
		// del path y un alto y ancho de x,y
		ImageIcon imagen = new ImageIcon(path);
		Image resImagen = imagen.getImage().getScaledInstance(x, y, Image.SCALE_SMOOTH);
		ImageIcon imagenFinal = new ImageIcon(resImagen);
		JLabel labelFinal = new JLabel(imagenFinal);
		return labelFinal;
	}

	public void salir() {
		sesion = null;
		vSesion.dispose();
	}

	@Override
	public void actualizarCarrito(List<Articulo> items) {
		// TODO Auto-generated method stub
		this.carrito.setCarrito(items);
		cantCarrito.setText(Integer.toString(carrito.getCarrito().size()));

	}

}