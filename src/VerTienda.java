import javax.swing.*;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;

public class VerTienda {
	private JPanel panelTiendaActual;
	private JLabel imageLabel;
	private JLabel titulo;
	private JButton comprarButton;
	private CarritoCompras carrito;

	public VerTienda(Tienda tiendaActual, JPanel panelTienda, Usuario userActual) {

		carrito = new CarritoCompras(userActual);
		carrito.setTienda(tiendaActual);
		Sesion sesion = Sesion.getInstancia(userActual);
		carrito.registrarObservador(sesion);
		// Crea un nuevo JPanel para mostrar los datos de la tienda
		panelTiendaActual = new JPanel();

		panelTiendaActual.setLayout(new GridLayout(0, 3));

		ImageIcon imagenTienda = new ImageIcon(tiendaActual.getUrl());
		Image rTiendaImagen = imagenTienda.getImage().getScaledInstance(135, 135, Image.SCALE_SMOOTH);
		ImageIcon imagenTiendaF = new ImageIcon(rTiendaImagen);
		imageLabel = new JLabel(imagenTiendaF);
		panelTiendaActual.add(imageLabel);
		titulo = new JLabel("Tienda: " + tiendaActual.getNombreTienda());
		panelTiendaActual.add(titulo);

		Map<String, Integer> stock = new HashMap<>();
		Articulo art = new Articulo(tiendaActual.getNombreTienda(), null, 0, 0, null);
		tiendaActual.setArticulos(art.getAll());
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

						carrito.agregarArticulo(articulo);
						stock.put(articulo.getNombreArt(), stock.get(articulo.getNombreArt()) - 1);
						
						//sesion.actualizarCarrito(carrito.getCarrito());
						
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

					if (stock.get(articulo.getNombreArt()) < articulo.getCantidad()) {

						carrito.eliminarArticulo(articulo);
						stock.put(articulo.getNombreArt(), stock.get(articulo.getNombreArt()) + 1);
						
						//sesion.actualizarCarrito(carrito.getCarrito());
						
						for (Articulo articulo : carrito.getCarrito()) {

							System.out.println(
									articulo.getNombreArt() + " - quedan: " + stock.get(articulo.getNombreArt()));

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
		panelTiendaActual.add(comprarButton);
		panelTiendaActual.add(volverButton);
		panelTienda.add(panelTiendaActual);
	}

}