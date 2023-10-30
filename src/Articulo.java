import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Articulo implements dbOperation {
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

	@Override
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
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
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
		}

		return this; // Devuelve la instancia de Articulo actualizada
	}

	@Override
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
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void delete() {
		try {
			String deleteQuery = "DELETE FROM articulo WHERE nombreArt = ?";
			PreparedStatement preparedStatement = db.prepareStatement(deleteQuery);
			preparedStatement.setString(1, nombreArt);
			preparedStatement.executeUpdate();
			preparedStatement.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}