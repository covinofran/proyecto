import javax.swing.*;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;

public class VerTienda {
	private Connection db = DatabaseSingleton.getConexion();

	public VerTienda(Tienda tiendaActual, JPanel panelTienda, Usuario userActual) {
		// System.out.println("Entro a la tienda " + tiendaActual.getnombreTienda());

		// Crea un nuevo JPanel para mostrar los datos de la tienda
		JPanel panelTiendaActual = new JPanel();

		panelTiendaActual.setLayout(new BoxLayout(panelTiendaActual, BoxLayout.Y_AXIS));

		ImageIcon imagenTienda = new ImageIcon(tiendaActual.getUrl());
		Image rTiendaImagen = imagenTienda.getImage().getScaledInstance(135, 135, Image.SCALE_SMOOTH);
		ImageIcon imagenTiendaF = new ImageIcon(rTiendaImagen);
		JLabel imageLabel = new JLabel(imagenTiendaF);
		panelTiendaActual.add(imageLabel);
		JLabel titulo = new JLabel("Tienda: " + tiendaActual.getNombreTienda());
		panelTiendaActual.add(titulo);

		Map<String, Integer> stock = new HashMap<>();
		cargarArticulos(tiendaActual);

		// Recorrer y mostrar los datos de los artículos
		for (Articulo articulo : tiendaActual.getArticulos()) {

			JLabel nombreArticulo = new JLabel("Nombre: " + articulo.getNombreArt());
			JLabel precioArticulo = new JLabel("Precio: " + articulo.getPrecio());
			stock.put(articulo.getNombreArt(), articulo.getCantidad());
			JButton addButton = new JButton("+");
			addButton.addActionListener((ActionListener) new ActionListener() {
				public void actionPerformed(ActionEvent e) {

					if (stock.get(articulo.getNombreArt()) > 0) {

						userActual.agregarArticulo(articulo);
						stock.put(articulo.getNombreArt(), stock.get(articulo.getNombreArt()) - 1);

						for (Articulo articulo : userActual.getCarrito()) {
							System.out.println(articulo.getNombreArt());
							System.out.println("Quedan: " + stock.get(articulo.getNombreArt()));

						}
						System.out.println("------------------------------------------------");
					}
				}
			});
			JButton removeButton = new JButton("-");
			removeButton.addActionListener((ActionListener) new ActionListener() {
				public void actionPerformed(ActionEvent e) {

					if (stock.get(articulo.getNombreArt()) < articulo.getCantidad()) {

						userActual.eliminarArticulo(articulo);
						stock.put(articulo.getNombreArt(), stock.get(articulo.getNombreArt()) + 1);

						for (Articulo articulo : userActual.getCarrito()) {

							System.out.println(articulo.getNombreArt());
							System.out.println("Quedan: " + stock.get(articulo.getNombreArt()));

						}
						System.out.println("------------------------------------------------");
					}
				}
			});

			panelTiendaActual.add(nombreArticulo);
			panelTiendaActual.add(precioArticulo);
			panelTiendaActual.add(addButton);
			panelTiendaActual.add(removeButton);
		}

		// Agregar el panel de la tienda actual al panel principal

		JButton volverButton = new JButton("Volver");
		volverButton.addActionListener((ActionListener) new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				userActual.setCarrito(new ArrayList<>());
				panelTienda.removeAll();
				panelTienda.revalidate();
				panelTienda.repaint();
				tiendaActual.setArticulos(new ArrayList<>());
				Sesion sesion = Sesion.getInstancia(userActual.getNombreUsuario());
				sesion.cargarTiendas();
			}
		});
		panelTiendaActual.add(volverButton);
		panelTienda.add(panelTiendaActual);
	}

	private void cargarArticulos(Tienda tienda) {

		try {
			PreparedStatement statement = db.prepareStatement(

					"SELECT nombreArt, precio, url,cantidad FROM articulo Where nombreTienda= ?");
			statement.setString(1, tienda.getNombreTienda());
			ResultSet resultSet = statement.executeQuery();
			{
				while (resultSet.next()) {

					String nombreArt = resultSet.getString("nombreArt");
					double precio = resultSet.getDouble("precio");
					String url = resultSet.getString("url");
					int cantidad = resultSet.getInt("cantidad");

					tienda.agregarArticulo(new Articulo(tienda.getNombreTienda(), nombreArt, precio, cantidad, url));
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}