import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import org.mindrot.jbcrypt.BCrypt;

public class DatabaseConnection {
	private static DatabaseConnection instancia;
	private Connection conexion;

	private DatabaseConnection() {
		try {
			String url = "jdbc:mysql://localhost:3306/proyecto";
			String user = "root";
			String password = "123123";
			conexion = DriverManager.getConnection(url, user, password);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("Error al conectar a la base de datos");
		}
	}

	public static synchronized DatabaseConnection getInstancia() {
		if (instancia == null) {
			instancia = new DatabaseConnection();
		}
		return instancia;
	}

	public Connection getConexion() {
		return conexion;
	}

	public boolean autenticarUsuario(String nombreUsuario, String contraseña, JFrame frame) {
		try {
			String consulta = "SELECT passwd FROM usuario WHERE idusuario = ?";
			PreparedStatement statement = conexion.prepareStatement(consulta);
			statement.setString(1, nombreUsuario.trim());

			ResultSet resultSet = statement.executeQuery();

			if (resultSet.next()) {
				String hashAlmacenado = resultSet.getString("passwd");

				if (BCrypt.checkpw(contraseña.trim(), hashAlmacenado)) {
					System.out.println("Autenticación exitosa");
					return true;
				} else {
					System.out.println("Contraseña incorrecta");
					JOptionPane.showMessageDialog(frame, "Contraseña incorrecta", "Error", JOptionPane.ERROR_MESSAGE);
					return false;
				}
			} else {
				System.out.println("Usuario no encontrado");
				JOptionPane.showMessageDialog(frame, "Usuario no encontrado", "Error", JOptionPane.ERROR_MESSAGE);
				return false;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			// Muestra un mensaje de error al usuario
			JOptionPane.showMessageDialog(frame,
					"Error al acceder a la base de datos. Por favor, inténtelo de nuevo más tarde.", "Error",
					JOptionPane.ERROR_MESSAGE);
			return false;
		}
	}

	public void close() {
		try {

			if (conexion != null && !conexion.isClosed()) {
				conexion.close();
				System.out.println("Conexión a la base de datos cerrada.");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void guardarUser(Usuario usuario) {
		try {
			// inserta el usuario en la tabla de usuarios
			String sqlUser = "INSERT INTO usuario (idusuario, passwd, url, salt) VALUES (?, ?, ?, ?)";
			PreparedStatement preparedStatementUser = conexion.prepareStatement(sqlUser);

			preparedStatementUser.setString(1, usuario.getId().trim());
			preparedStatementUser.setString(2, usuario.getContraseña().trim());
			preparedStatementUser.setString(3, usuario.getUrl());
			preparedStatementUser.setString(4, usuario.getSalt());
			preparedStatementUser.executeUpdate();

			System.out.println("Usuario guardado en la base de datos correctamente.");
		} catch (SQLException e) {
			e.printStackTrace();
			System.err.println("Error al guardar el usuario en la base de datos.");
		}
	}

}
