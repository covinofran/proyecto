import javax.swing.*;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.List;

public class VerTienda {
	private JPanel panelTiendaActual;
	private JLabel imageLabel;
	private JButton comprarButton;
	private CarritoCompras carrito;
	private Map<String, Integer> stock;

	public VerTienda(Tienda tiendaActual, JPanel panelTienda, Usuario userActual) {

		carrito = new CarritoCompras(userActual);
		carrito.setTienda(tiendaActual);
		Sesion sesion = Sesion.getInstancia(userActual);
		carrito.registrarObservador(sesion);

		// Crea un nuevo JPanel para mostrar los datos de la tienda
		panelTiendaActual = new JPanel();
		panelTiendaActual.setLayout(new GridLayout(0, 3, 10, 10));

		// Configurar la imagen de la tienda
		ImageIcon imagenTienda = new ImageIcon(tiendaActual.getUrl());
		Image rTiendaImagen = imagenTienda.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
		ImageIcon imagenTiendaF = new ImageIcon(rTiendaImagen);
		imageLabel = new JLabel(imagenTiendaF);
		panelTiendaActual.add(imageLabel);

		// Mapa para realizar un seguimiento del stock de cada artículo
		stock = new HashMap<>();
		Articulo art = new Articulo(tiendaActual.getNombreTienda(), null, 0, 0, null);
		tiendaActual.setArticulos(art.getAll());
		// Recorrer y mostrar los datos de los artículos
		for (Articulo articulo : tiendaActual.getArticulos()) {

			if (articulo.getCantidad() > 0) {
				JPanel itemPanel = new JPanel();
				itemPanel.setLayout(new GridBagLayout());
				itemPanel.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10),
						BorderFactory.createLineBorder(Color.BLACK)));
				GridBagConstraints gbc = new GridBagConstraints();
				gbc.anchor = GridBagConstraints.CENTER;
				gbc.insets = new Insets(5, 5, 5, 5);

				JLabel nombreArticulo = new JLabel(articulo.getNombreArt());
				JLabel precioArticulo = new JLabel("$" + articulo.getPrecio());
				// Configurar el estilo del nombre del artículo y el precio
				nombreArticulo.setFont(new Font("Arial", Font.BOLD, 14));
				nombreArticulo.setForeground(Color.BLUE);

				precioArticulo.setFont(new Font("Arial", Font.PLAIN, 12));
				precioArticulo.setForeground(Color.DARK_GRAY);

				stock.put(articulo.getNombreArt(), articulo.getCantidad());

				JButton addButton = new JButton("+");
				addButton.addActionListener((ActionListener) new ActionListener() {
					public void actionPerformed(ActionEvent e) {

						// Lógica para agregar un artículo al carrito
						if (stock.get(articulo.getNombreArt()) > 0) {

							carrito.agregarArticulo(articulo);
							stock.put(articulo.getNombreArt(), stock.get(articulo.getNombreArt()) - 1);

							// sesion.actualizarCarrito(carrito.getCarrito());

							for (Articulo articulo : carrito.getCarrito()) {
								System.out.println(
										articulo.getNombreArt() + " - quedan: " + stock.get(articulo.getNombreArt()));

							}
							System.out.println("------------------------------------------------");
						}
					}
				});
				JButton removeButton = new JButton("-");
				removeButton.addActionListener((ActionListener) new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						// Lógica para eliminar un artículo del carrito
						if (stock.get(articulo.getNombreArt()) < articulo.getCantidad()) {

							carrito.eliminarArticulo(articulo);
							stock.put(articulo.getNombreArt(), stock.get(articulo.getNombreArt()) + 1);

							// sesion.actualizarCarrito(carrito.getCarrito());

							for (Articulo articulo : carrito.getCarrito()) {

								System.out.println(
										articulo.getNombreArt() + " - quedan: " + stock.get(articulo.getNombreArt()));

							}
							System.out.println("------------------------------------------------");
						}
					}
				});

				// Personalizar los botones de agregar y quitar
				addButton.setBackground(Color.GREEN);
				addButton.setForeground(Color.WHITE);
				addButton.setFont(new Font("Arial", Font.BOLD, 14));

				removeButton.setBackground(Color.RED);
				removeButton.setForeground(Color.WHITE);
				removeButton.setFont(new Font("Arial", Font.BOLD, 14));

				itemPanel.add(nombreArticulo, gbc);
				itemPanel.add(precioArticulo, gbc);
				itemPanel.add(addButton, gbc);
				itemPanel.add(removeButton, gbc);
				panelTiendaActual.add(itemPanel);
			}
		}

		panelTienda.add(panelTiendaActual);
		
	

		// Agregar el panel de la tienda actual al panel principal
		comprarButton = new JButton("Comprar");
		comprarButton.addActionListener((ActionListener) new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// AGREGAR LA COMPRA A LA BASE DE DATOS
				Map<String, Integer> compra = new HashMap<>();
				// EN ESTE HASHMAP QUEDA ALMACENADA LA COMPRA DEL USUARIO, ESTO DEBERIA HACER
				// UNA FACTURA Y ENVIARLO A LA BASE DE DATOS, ADEMAS DE AGREGAR EL STOCK
				// NEGATIVO ASI SE RESTA DE LA DB
				for (Articulo articulo : carrito.getCarrito()) {
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
					// System.out.println(articulo.toString());
					// System.out.println(stock);

				}
				System.out.println(compra);
				descontarStock(compra);
				Factura factura = new Factura(userActual.getNombreUsuario(), tiendaActual.getNombreTienda(),
						calcularTotal(carrito.getCarrito()));
				factura.cargarDB();
			}
		});
		JButton volverButton = new JButton("Volver");

		volverButton.addActionListener((ActionListener) new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				carrito.setCarrito(new ArrayList<>());
				panelTienda.removeAll();
				panelTienda.revalidate();
				panelTienda.repaint();
				tiendaActual.setArticulos(new ArrayList<>());

				sesion.cargarTiendas();

			}
		});

		// Añadir un panel para los botones de compra y volver
		JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
		buttonPanel.add(comprarButton);
		buttonPanel.add(volverButton);
		panelTiendaActual.add(buttonPanel);

		panelTienda.add(panelTiendaActual);
	}

	
	private void descontarStock(Map<String, Integer> compra) {
		for (Map.Entry<String, Integer> entry : compra.entrySet()) {
			String nombreArticulo = entry.getKey();
			int cantidad = entry.getValue();
			Articulo articulo = new Articulo(null, nombreArticulo, 0, 0, null);
			articulo.read();
			articulo.setCantidad(-cantidad);
			articulo.create();
		}

	}

	// Método para calcular el precio total de la compra
	private double calcularTotal(List<Articulo> articulos) {
		double precioTotal = 0.0;
		for (Articulo articulo : articulos) {
			precioTotal += articulo.getPrecio();
		}
		return precioTotal;
	}
}
