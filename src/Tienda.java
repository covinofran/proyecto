import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Tienda implements dbOperation {
	private ArrayList<Articulo> articulos;
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

	public ArrayList<Articulo> getArticulos() {
		return articulos;
	}

	public void setArticulos(ArrayList<Articulo> articulos) {
		this.articulos = articulos;
	}

	public String getNombreTienda() {
		return nombreTienda;
	}

	public void setNombreTienda(String nombreTienda) {
		this.nombreTienda = nombreTienda;
	}

	public String getnombreUsuario() {
		return nombreUsuario;
	}

	public void setnombreUsuario(String nombreUsuario) {
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

	@Override
	public void create() {
		try {
			String insertQuery = "INSERT INTO tienda (nombreTienda, nombreUsuario, url) VALUES (?, ?, ?)";
			PreparedStatement preparedStatement = db.prepareStatement(insertQuery);
			preparedStatement.setString(1, nombreTienda);
			preparedStatement.setString(2, nombreUsuario);
			preparedStatement.setString(3, url);
			preparedStatement.executeUpdate();
			preparedStatement.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public Tienda read() {
		try {
			String selectQuery = "SELECT * FROM tienda WHERE nombreTienda = ?";
			PreparedStatement preparedStatement = db.prepareStatement(selectQuery);
			preparedStatement.setString(1, nombreTienda);
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

	@Override
	public void update() {
		try {
			String updateQuery = "UPDATE tienda SET nombreUsuario = ?, url = ? WHERE nombreTienda = ?";
			PreparedStatement preparedStatement = db.prepareStatement(updateQuery);
			preparedStatement.setString(1, nombreUsuario);
			preparedStatement.setString(2, url);
			preparedStatement.setString(3, nombreTienda);
			preparedStatement.executeUpdate();
			preparedStatement.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void delete() {
		try {
			String deleteQuery = "DELETE FROM tienda WHERE nombreTienda = ?";
			PreparedStatement preparedStatement = db.prepareStatement(deleteQuery);
			preparedStatement.setString(1, nombreTienda);
			preparedStatement.executeUpdate();
			preparedStatement.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}