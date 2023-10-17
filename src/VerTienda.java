import javax.swing.*;

import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class VerTienda {
	private Connection db = DatabaseSingleton.getConexion();

	public VerTienda(Tienda tiendaActual, JPanel panelTienda, String nombreUsuario) {
		// System.out.println("Entro a la tienda " + tiendaActual.getnombreTienda());

		// Crea un nuevo JPanel para mostrar los datos de la tienda
		JPanel panelTiendaActual = new JPanel();
		panelTiendaActual.setLayout(new BoxLayout(panelTiendaActual, BoxLayout.Y_AXIS));

		
		
		
		
		ImageIcon imagenTienda = new ImageIcon(tiendaActual.getUrl());
		Image rTiendaImagen = imagenTienda.getImage().getScaledInstance(135, 135, Image.SCALE_SMOOTH);
		ImageIcon imagenTiendaF = new ImageIcon(rTiendaImagen);
		JLabel imageLabel = new JLabel(imagenTiendaF);
		panelTiendaActual.add(imageLabel);
		JLabel titulo = new JLabel("Tienda: " + tiendaActual.getnombreTienda());
		panelTiendaActual.add(titulo);

		// Carga los articulos en el array
		ArrayList<Articulo> articulos = cargarArticulos(tiendaActual.getnombreTienda());

		// Recorrer y mostrar los datos de los artículos
		for (Articulo articulo : articulos) {
			JLabel nombreArticulo = new JLabel("Nombre: " + articulo.getNombreArt());
			JLabel precioArticulo = new JLabel("Precio: " + articulo.getPrecio());

			panelTiendaActual.add(nombreArticulo);
			panelTiendaActual.add(precioArticulo);
		}

		// Agregar el panel de la tienda actual al panel principal
		panelTienda.add(panelTiendaActual);
		JButton volverButton = new JButton("Volver");
		volverButton.addActionListener((ActionListener) new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				panelTienda.removeAll();
				panelTienda.revalidate();
				panelTienda.repaint();
				Sesion sesion = Sesion.getInstancia(nombreUsuario);
				sesion.cargarTiendas();
			}
		});
		panelTienda.add(volverButton);
	}

	private ArrayList<Articulo> cargarArticulos(String nombreTienda) {
		ArrayList<Articulo> articulos = new ArrayList<>();
		try {
			PreparedStatement statement = db.prepareStatement(

					"SELECT nombreArt, precio, url,cantidad FROM articulo Where nombreTienda= ?");
			statement.setString(1, nombreTienda);
			ResultSet resultSet = statement.executeQuery();
			{

				while (resultSet.next()) {
					String nombreArt = resultSet.getString("nombreArt");
					double precio = resultSet.getDouble("precio");
					String url = resultSet.getString("url");
					int cantidad = resultSet.getInt("cantidad");
					articulos.add(new Articulo(nombreTienda, nombreArt, precio, cantidad, url));

				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return articulos;
	}
}
