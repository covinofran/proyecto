import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class VistaTiendas extends JFrame {
//ATRIBUTOS
	private List<Tienda> tiendaList;
	private int currentIndex;
	private JPanel carouselPanel;
	private Timer carouselTimer;
	private JButton prevButton;
	private JButton nextButton;
	Connection db = DatabaseSingleton.getConexion();

//CONSTRUCTOR
	public VistaTiendas() {

		tiendaList = new ArrayList<>();// CREA UNA INSTANCIA DE imageList de tipo ArrayList
		loadImagesFromDatabase();// TRAE LOS DATOS DE LA DB

		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setTitle("Lista de Juegos");
		setLayout(new BorderLayout());

		carouselPanel = new JPanel(new FlowLayout());
		carouselPanel.setPreferredSize(new Dimension(600, 400));

		prevButton = new JButton("Anterior");
		nextButton = new JButton("Siguiente");

		JPanel buttonPanel = new JPanel(new FlowLayout());
		buttonPanel.add(prevButton);
		buttonPanel.add(nextButton);

		add(carouselPanel, BorderLayout.CENTER);
		add(buttonPanel, BorderLayout.SOUTH);

		createImageCarousel();

		prevButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				previousImage(); // AGREGO UN LISTENER QUE LLAMA A ESTE METODO AL PRESIONAR
			}
		});

		nextButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				nextImage(); // AGREGO UN LISTENER QUE LLAMA A ESTE METODO AL PRESIONAR
			}
		});

		setSize(500, 350);
		setVisible(true);

		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				closeCarousel(); // AGREGO UN LISTENER QUE LLAMA A ESTE METODO AL PRESIONAR X DE LA VENTANA
			}
		});
	}

//METODOS   
	private void createImageCarousel() {// CREA EL CAROUSEL
		currentIndex = 0; // INDICE DEL ARRAYLIST
		// Crea un temporizador para cambiar automáticamente las imágenes
		carouselTimer = new Timer(2000, e -> {
			nextImage();
		});
		restartTimer();

		// Muestra la imagen inicial
		mostrarJuego(currentIndex);// MUESTRA EL JUEGO
	}

	private void mostrarJuego(int index) {// TOMA LOS DATOS DEL JUEGO Y LOS MUESTRA EN LA ENTANA
		carouselPanel.removeAll();

		Tienda imageData = tiendaList.get(index);
		ImageIcon imageIcon = new ImageIcon(imageData.getUrl());
		JLabel imageLabel = new JLabel(imageIcon);

		carouselPanel.add(imageLabel);

		revalidate();
		repaint();
	}

	private void previousImage() {// MODIFICA EL INDICE DEL ARRAYLIST AL ANTERIOR
		currentIndex = (currentIndex - 1 + tiendaList.size()) % tiendaList.size();
		mostrarJuego(currentIndex);
		restartTimer();
	}

	private void nextImage() {// MODIFICA EL INDICE DEL ARRAYLIST AL SIGUIENTE
		currentIndex = (currentIndex + 1) % tiendaList.size();
		mostrarJuego(currentIndex);
		restartTimer();
	}

	private void restartTimer() {// REINICIA EL TIMER
		carouselTimer.restart();
	}

	private void closeCarousel() {// DETIENE EL CAROUSEL Y CIERRA LA VENTANA
		carouselTimer.stop();
		dispose();
	}

	private void loadImagesFromDatabase() {// TRAE LOS DATOS DE LA DB Y AGREGA AL ARRAYLIST UN OBJETO JUEGO
		// Establecer la conexión a biblioteca y realizar la consulta
		try (PreparedStatement statement = db.prepareStatement("SELECT nombreTienda, nombreUsuario, url FROM tienda");
				ResultSet resultSet = statement.executeQuery()) {

			while (resultSet.next()) {
				String nombreTienda = resultSet.getString("nombreTienda");
				String nombreUsuario = resultSet.getString("nombreUsuario");
				String url = resultSet.getString("url");
				tiendaList.add(new Tienda(nombreTienda, nombreUsuario, url));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
