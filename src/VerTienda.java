import javax.swing.*;

import java.awt.GridLayout;
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

		panelTiendaActual.setLayout(new GridLayout(0, 3));

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

			// Crea un panel para cada artículo
			JPanel itemPanel = new JPanel();

			itemPanel.setLayout(new BoxLayout(itemPanel, BoxLayout.Y_AXIS));
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

			itemPanel.add(nombreArticulo);
			itemPanel.add(precioArticulo);
			itemPanel.add(addButton);
			itemPanel.add(removeButton);
			panelTiendaActual.add(itemPanel);
		}
		panelTienda.add(panelTiendaActual);
		// Agregar el panel de la tienda actual al panel principal
		JButton comprarButton = new JButton("Comprar");
		comprarButton.addActionListener((ActionListener) new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// AGREGAR LA COMPRA A LA BASE DE DATOS
				Map<String, Integer> compra = new HashMap<>();
				// EN ESTE HASHMAP QUEDA ALMACENADA LA COMPRA DEL USUARIO, ESTO DEBERIA HACER
				// UNA FACTURA Y ENVIARLO A LA BASE DE DATOS, ADEMAS DE AGREGAR EL STOCK
				// NEGATIVO ASI SE RESTA DE LA DB
				for (Articulo articulo : userActual.getCarrito()) {
					// SI MAL NO ENTENDI A SANTIAGO DEBERIA CARGAR LOS ARTICULOS DE A UNO QUE COMPRE
					// PARA QUE CUANDO HAGA UN SUM DE LOS ARTICULOS QUE TRAIGO ME RETORNE EL NUEVO
					// STOCK
					String nombreArticulo = articulo.getNombreArt();

					// Verifica si el artículo ya está en el mapa
					if (compra.containsKey(nombreArticulo)) {
						// Si está en el mapa, incrementa la cantidad
						int cantidadActual = compra.get(nombreArticulo);
						compra.put(nombreArticulo, cantidadActual + 1);
					} else {
						// Si no está en el mapa, agrega el artículo con una cantidad inicial de 1
						compra.put(nombreArticulo, 1);
					}
					System.out.println(articulo.toString());
					// System.out.println(stock);

				}
				System.out.println(compra);
			}
		});
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
		panelTiendaActual.add(comprarButton);
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