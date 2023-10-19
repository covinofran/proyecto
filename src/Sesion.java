import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Sesion {
	private List<Tienda> tiendaList;
	private JFrame vSesion;
	private Connection db = DatabaseSingleton.getConexion();
	private JPanel panelSesion;
	private static Sesion sesion;
	private Usuario userActual;

	// Ventana de sesion ya inciada
	private Sesion(String nombreUsuario) {

		UserOperation operacionesUsuario = new UserOperation(db);
		userActual = operacionesUsuario.readUsuario(nombreUsuario);
		vSesion = new JFrame("Sesión Iniciada - Usuario: " + userActual.getNombreUsuario());
		vSesion.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

		ImageIcon logo = new ImageIcon("images\\logo.png");
		vSesion.setIconImage(logo.getImage());
		ImageIcon userImagen = new ImageIcon(userActual.getUrl());

		Image rUserImagen = userImagen.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH);
		ImageIcon imagenFinal = new ImageIcon(rUserImagen);
		JLabel imagenPerfil = new JLabel(imagenFinal);

		JButton cerrarButton = new JButton("Cerrar Sesion");
		cerrarButton.addActionListener((ActionListener) new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				vSesion.dispose();
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
				new Perfil(panelSesion, userActual);
			}
		});

		vSesion.add(cerrarButton);
		vSesion.setLayout(null);
		vSesion.setBounds(0, 0, 800, 600);
		vSesion.setLocationRelativeTo(null);
		vSesion.setVisible(true);
		cerrarButton.setBounds(650, 10, 120, 40);
		cerrarButton.setFocusable(false);

		tiendaList = obtenerDatosDeTiendas();

		panelSesion = new JPanel(new FlowLayout());

		cargarTiendas();
		// Agregar el panel de Sesion al JFrame
		vSesion.add(panelSesion);
		panelSesion.setBounds(0, 150, 800, 400);

		JButton modificarButton = new JButton("Tu Tienda");

		modificarButton.addActionListener((ActionListener) new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("Editar Tienda");
				// AGREGAR LAS CARACTERISTICAS DE UNA TIENDA
			}
		});

		modificarButton.setBounds(500, 10, 120, 40);
		modificarButton.setFocusable(false);
		vSesion.add(modificarButton);

		if ("Local".equals(userActual.getTipo())) {
			modificarButton.setVisible(true);

		} else {
			modificarButton.setVisible(false);
		}

	}

//METODOS   
	public void cargarTiendas() {
		for (Tienda datos : tiendaList) {
			ImageIcon imagenTienda = new ImageIcon(datos.getUrl());
			Image rTiendaImagen = imagenTienda.getImage().getScaledInstance(135, 135, Image.SCALE_SMOOTH);
			ImageIcon imagenTiendaF = new ImageIcon(rTiendaImagen);
			JLabel imageLabel = new JLabel(imagenTiendaF);
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
			JPanel tiendaPanel = new JPanel();
			tiendaPanel.setLayout(new BoxLayout(tiendaPanel, BoxLayout.Y_AXIS));
			tiendaPanel.add(imageLabel);
			tiendaPanel.add(nombreLabel);

			panelSesion.add(tiendaPanel);
		}
	}

	private ArrayList<Tienda> obtenerDatosDeTiendas() {
		ArrayList<Tienda> tiendas = new ArrayList<>();
		try (PreparedStatement statement = db.prepareStatement("SELECT nombreTienda, nombreUsuario, url FROM tienda");
				ResultSet resultSet = statement.executeQuery()) {

			while (resultSet.next()) {
				String nombreTienda = resultSet.getString("nombreTienda");

				String url = resultSet.getString("url");
				tiendas.add(new Tienda(nombreTienda, null, url));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return tiendas;
	}

	public static synchronized Sesion getInstancia(String nombreUsuario) {
		if (sesion == null) {
			sesion = new Sesion(nombreUsuario);
		}
		return sesion;
	}
}
