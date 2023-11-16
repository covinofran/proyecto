import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

public class Articulo {
	private String nombreTienda;
	private String nombreArt;
	private double precio;
	private int cantidad;
	private String url;
	private Connection db;

	public Articulo(String nombreTienda, String nombreArt, double precio, int cantidad, String url) {
		this.nombreTienda = nombreTienda;
		this.nombreArt = nombreArt;
		this.precio = precio;
		this.cantidad = cantidad;
		this.url = url;
		this.db = DatabaseSingleton.getConexion();
	}

	@Override
	public String toString() {
		return "Articulo [nombreTienda=" + nombreTienda + ", nombreArt=" + nombreArt + ", precio=" + precio
				+ ", cantidad=" + cantidad + ", url=" + url + "]";
	}

	public String getNombreTienda() {
		return nombreTienda;
	}

	public void setNombreTienda(String nombreTienda) {
		this.nombreTienda = nombreTienda;
	}

	public int getCantidad() {
		return cantidad;
	}

	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getNombreArt() {
		return nombreArt;
	}

	public void setNombreArt(String nombreArt) {
		this.nombreArt = nombreArt;
	}

	public double getPrecio() {
		return precio;
	}

	public void setPrecio(double precio) {
		this.precio = precio;
	}

	public String getUrl() {
		return url;
	}

	public void setImagen(String url) {
		this.url = url;
	}

	public void create() {
		try {
			String insertQuery = "INSERT INTO articulo (nombreTienda, nombreArt, precio, cantidad, url) VALUES (?, ?, ?, ?, ?)";
			PreparedStatement preparedStatement = db.prepareStatement(insertQuery);
			preparedStatement.setString(1, nombreTienda);
			preparedStatement.setString(2, nombreArt);
			preparedStatement.setDouble(3, precio);
			preparedStatement.setInt(4, cantidad);
			preparedStatement.setString(5, url);
			preparedStatement.executeUpdate();
			preparedStatement.close();
			JOptionPane.showMessageDialog(null, "Articulo cargado exitosamente.", "Alerta", JOptionPane.WARNING_MESSAGE);
		} catch (SQLException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Error al crear el articulo.", "Alerta", JOptionPane.WARNING_MESSAGE);
		}
	}

	public Articulo read() {
		try {
			String selectQuery = "SELECT * FROM articulo WHERE nombreArt = ?";
			PreparedStatement preparedStatement = db.prepareStatement(selectQuery);
			preparedStatement.setString(1, nombreArt);
			ResultSet resultSet = preparedStatement.executeQuery();

			if (resultSet.next()) {
				this.nombreTienda = resultSet.getString("nombreTienda");
				this.nombreArt = resultSet.getString("nombreArt");
				this.precio = resultSet.getDouble("precio");
				this.cantidad = resultSet.getInt("cantidad");
				this.url = resultSet.getString("url");
			}

			resultSet.close();
			preparedStatement.close();
		} catch (SQLException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Error al leer el articulo.", "Alerta", JOptionPane.WARNING_MESSAGE);
		}

		return this; // Devuelve la instancia de Articulo actualizada
	}

	public void update() {
		try {
			String updateQuery = "UPDATE articulo SET precio = ?, cantidad = ?, url = ? WHERE nombreArt = ?";
			PreparedStatement preparedStatement = db.prepareStatement(updateQuery);
			preparedStatement.setDouble(1, precio);
			preparedStatement.setInt(2, cantidad);
			preparedStatement.setString(3, url);
			preparedStatement.setString(4, nombreArt);
			preparedStatement.executeUpdate();
			preparedStatement.close();
			JOptionPane.showMessageDialog(null, "Articulo actualizado correctamente.", "Alerta", JOptionPane.WARNING_MESSAGE);
		} catch (SQLException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Error al actualizar el articulo.", "Alerta", JOptionPane.WARNING_MESSAGE);
		}
	}

	public void delete() {
		try {
			String deleteQuery = "DELETE FROM articulo WHERE nombreArt = ?";
			PreparedStatement preparedStatement = db.prepareStatement(deleteQuery);
			preparedStatement.setString(1, nombreArt);
			preparedStatement.executeUpdate();
			preparedStatement.close();
			JOptionPane.showMessageDialog(null, "Articulo eliminado correctamente.", "Alerta", JOptionPane.WARNING_MESSAGE);
		} catch (SQLException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Error al borrar el articulo.", "Alerta", JOptionPane.WARNING_MESSAGE);
		}
	}

	public List<Articulo> getAll() {
		List<Articulo> articulos = new ArrayList<>();

		try {
			PreparedStatement statement = db.prepareStatement(

					"SELECT nombreTienda, nombreArt, precio, url, SUM(cantidad) AS cant FROM articulo WHERE nombreTienda = ? GROUP BY nombreTienda, nombreArt, precio, url");
			statement.setString(1, nombreTienda);
			ResultSet resultSet = statement.executeQuery();
			{
				while (resultSet.next()) {
					Articulo articulo = new Articulo(resultSet.getString("nombreTienda"),
							resultSet.getString("nombreArt"), resultSet.getDouble("precio"), resultSet.getInt("cant"),
							resultSet.getString("url"));
					articulos.add(articulo);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return articulos;
	}

}