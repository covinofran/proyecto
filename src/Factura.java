import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.swing.JOptionPane;

public class Factura {
	private String nombreUsario;
	private String nombreTienda;
	private double total;
	private Connection db;

	public Factura(String nombreUsuario, String nombreTienda, double total) {
		this.nombreTienda = nombreTienda;
		this.nombreUsario = nombreUsuario;
		this.total = total;
		this.db = DatabaseSingleton.getConexion();
	}

	public String getNombreUsario() {
		return nombreUsario;
	}

	public void setNombreUsario(String nombreUsario) {
		this.nombreUsario = nombreUsario;
	}

	public String getNombreTienda() {
		return nombreTienda;
	}

	public void setNombreTienda(String nombreTienda) {
		this.nombreTienda = nombreTienda;
	}

	public double getTotal() {
		return total;
	}

	public void setTotal(double total) {
		this.total = total;
	}

	public void cargarDB() {

		String query = "INSERT INTO factura (nombre_usuario, nombre_tienda, total) VALUES (?, ?, ?)";

		try (PreparedStatement preparedStatement = db.prepareStatement(query)) {
			preparedStatement.setString(1, nombreUsario);
			preparedStatement.setString(2, nombreTienda);
			preparedStatement.setDouble(3, total);

			// Ejecutar la actualización
			preparedStatement.executeUpdate();

			JOptionPane.showMessageDialog(null, "Factura cargada en el sistema.", "", JOptionPane.INFORMATION_MESSAGE);
		} catch (SQLException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Error al crear el Usuario.", "Alerta", JOptionPane.WARNING_MESSAGE);
		}
	}
}
