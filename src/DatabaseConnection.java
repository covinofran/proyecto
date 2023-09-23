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
			String usuario = "root";
			String contra = "123123";
			conexion = DriverManager.getConnection(url, usuario, contra);
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
			String consulta = "SELECT passwd FROM user WHERE iduser = ?";
			PreparedStatement statement = conexion.prepareStatement(consulta);
			statement.setString(1, nombreUsuario);

			ResultSet resultSet = statement.executeQuery();

			if (resultSet.next()) {
				String hashAlmacenado = resultSet.getString("passwd");

				if (BCrypt.checkpw(contraseña, hashAlmacenado)) {
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
			/*
			 * ACA FALLA PORQUE SI AGREGO UN ID QUE YA EXISTE ME AGREGA LA IMAGEN Y DESPUES
			 * CONTROLA SI EXISTE EL ID, POR LO TANTO LA IMAGEN SE AGREGA PERO NO SE AGREGA
			 * EL USUARIO. QUEDARIA UNA IMAGEN AL PEDO CARGADA SOLUCIONAR ESTE PROBLEMA DE
			 * ALGUNA FORMA, LA MAS PRACTICA SERIA AGRENDANDO LA RUTA DE LA IMAGEN
			 * DIRECTAMENTE AL USUARIO PERO PARA HACERLO MAS DIFICIL Y PROBAR DISTINTAS
			 * FORMAS BUSCAR COMO SE SOLUCIONA ASI
			 */

			// inserta la imagen en la tabla de imágenes
			String sqlImagen = "INSERT INTO imagen (nombre, ruta) VALUES (?, ?)";
			PreparedStatement preparedStatementImagen = conexion.prepareStatement(sqlImagen);

			Imagen imagen = usuario.getImagen();
			preparedStatementImagen.setString(1, imagen.getNombre());
			preparedStatementImagen.setString(2, imagen.getUrl());

			preparedStatementImagen.executeUpdate();

			// ID de la imagen recién insertada
			String sqlMaxId = "SELECT MAX(idimagen) AS max_id FROM imagen";
			PreparedStatement preparedStatementMaxId = conexion.prepareStatement(sqlMaxId);
			ResultSet resultSet = preparedStatementMaxId.executeQuery();

			int idImagen = 0;
			if (resultSet.next()) {
				idImagen = resultSet.getInt("max_id");
			}

			// inserta el usuario en la tabla de usuarios
			String sqlUser = "INSERT INTO user (iduser, passwd, idimg, salt) VALUES (?, ?, ?, ?)";
			PreparedStatement preparedStatementUser = conexion.prepareStatement(sqlUser);

			preparedStatementUser.setString(1, usuario.getId());
			preparedStatementUser.setString(2, usuario.getContraseña());
			preparedStatementUser.setInt(3, idImagen);
			preparedStatementUser.setString(4, usuario.getSalt());
			preparedStatementUser.executeUpdate();

			System.out.println("Usuario guardado en la base de datos correctamente.");
		} catch (SQLException e) {
			e.printStackTrace();
			System.err.println("Error al guardar el usuario en la base de datos.");
		}
	}

}
