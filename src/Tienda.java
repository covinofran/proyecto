import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

public class Tienda {
	private List<Articulo> articulos;
	private String nombreTienda;
	private String nombreUsuario;
	private String url;
	private Connection db;

	public Tienda(String nombreTienda, String nombreUsuario, String url) {
		this.nombreTienda = nombreTienda;
		this.nombreUsuario = nombreUsuario;
		this.articulos = new ArrayList<>();
		this.url = url;
		this.db = DatabaseSingleton.getConexion();
	}

	public void agregarArticulo(Articulo articulo) {
		articulos.add(articulo);
	}

	public void eliminarArticulo(Articulo articulo) {
		articulos.remove(articulo);
	}

	public List<Articulo> getArticulos() {
		return articulos;
	}

	public void setArticulos(List<Articulo> articulos) {
		this.articulos = articulos;
	}

	public String getNombreTienda() {
		return nombreTienda;
	}

	public void setNombreTienda(String nombreTienda) {
		this.nombreTienda = nombreTienda;
	}

	public String getNombreUsuario() {
		return nombreUsuario;
	}

	public void setNombreUsuario(String nombreUsuario) {
		this.nombreUsuario = nombreUsuario;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	@Override
	public String toString() {
		return "Tienda [nombreTienda=" + nombreTienda + ", nombreUsuario=" + nombreUsuario + ", url=" + url + "]";
	}

	public void create() {
		try {
			String insertQuery = "INSERT INTO tienda (nombreTienda, nombreUsuario, url) VALUES (?, ?, ?)";
			PreparedStatement preparedStatement = db.prepareStatement(insertQuery);
			preparedStatement.setString(1, nombreTienda);
			preparedStatement.setString(2, nombreUsuario);
			preparedStatement.setString(3, url);
			preparedStatement.executeUpdate();
			preparedStatement.close();
			JOptionPane.showMessageDialog(null, "Usuario creado exitosamente.", "Alerta", JOptionPane.WARNING_MESSAGE);
		} catch (SQLException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Error al crear la tienda.", "Alerta", JOptionPane.WARNING_MESSAGE);
		}
	}

	public Tienda read() {
		try {
			String selectQuery = "SELECT * FROM tienda WHERE nombreUsuario = ?";
			PreparedStatement preparedStatement = db.prepareStatement(selectQuery);
			preparedStatement.setString(1, nombreUsuario);
			ResultSet resultSet = preparedStatement.executeQuery();

			if (resultSet.next()) {
				// Cargar los datos de la base de datos en la instancia de Tienda
				this.nombreTienda = resultSet.getString("nombreTienda");
				this.nombreUsuario = resultSet.getString("nombreUsuario");
				this.url = resultSet.getString("url");
			}

			resultSet.close();
			preparedStatement.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return this; // Devuelve la instancia de Tienda actualizada
	}

	public void update() {
		try {
			String updateQuery = "UPDATE tienda SET url = ? WHERE nombreUsuario = ?";
			PreparedStatement preparedStatement = db.prepareStatement(updateQuery);
			preparedStatement.setString(1, url);
			preparedStatement.setString(2, nombreUsuario);
			preparedStatement.executeUpdate();
			preparedStatement.close();
			JOptionPane.showMessageDialog(null, "Tienda actualizada exitosamente.", "Alerta",
					JOptionPane.WARNING_MESSAGE);
		} catch (SQLException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Error al actualizar los datos.", "Alerta",
					JOptionPane.WARNING_MESSAGE);
		}
	}

	public void delete() {
		try {
			String deleteQuery = "DELETE FROM tienda WHERE nombreUsuario = ?";
			PreparedStatement preparedStatement = db.prepareStatement(deleteQuery);
			preparedStatement.setString(1, nombreUsuario);
			preparedStatement.executeUpdate();
			preparedStatement.close();
		} catch (SQLException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Error al borrar la tienda.", "Alerta", JOptionPane.WARNING_MESSAGE);
		}
	}

	public List<Tienda> getAll() {
		List<Tienda> tiendas = new ArrayList<>();
		String sql = "SELECT * FROM tienda";
		try {
			PreparedStatement statement = db.prepareStatement(sql);
			ResultSet result = statement.executeQuery();
			while (result.next()) {
				Tienda tienda = new Tienda(result.getString("nombreTienda"), result.getString("nombreUsuario"),
						result.getString("url"));
				tiendas.add(tienda);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return tiendas;
	}

}