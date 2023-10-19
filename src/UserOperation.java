import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import org.mindrot.jbcrypt.BCrypt;

public class UserOperation {
	private Connection conexion;

	public UserOperation(Connection conexion) {
		this.conexion = conexion;
	}

	// Metodo para iniciar sesion, comprueba si existe el usuario y compara la
	// contraseña con la almacenada
	public boolean autenticarUsuario(String nombreUsuario, String contraseña, JFrame frame) {
		try {
			String consulta = "SELECT passwd FROM usuario WHERE idusuario = ?";
			PreparedStatement statement = conexion.prepareStatement(consulta);
			statement.setString(1, nombreUsuario.trim());

			ResultSet resultSet = statement.executeQuery();

			if (resultSet.next()) {
				String hashAlmacenado = resultSet.getString("passwd");

				if (BCrypt.checkpw(contraseña.trim(), hashAlmacenado)) {

					return true;
				} else {

					JOptionPane.showMessageDialog(frame, "Usuario o contraseña incorrecta", "Error al iniciar",
							JOptionPane.ERROR_MESSAGE);
					
					return false;
				}
			} else {

				JOptionPane.showMessageDialog(frame, "Usuario o contraseña incorrecta", "Error al iniciar",
						JOptionPane.ERROR_MESSAGE);
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

	// Almacena un usuario en la base de datos
	// TODAVIA NO COMPRUEBA SI EL USUARIO EXISTE
	public void guardarUser(Usuario usuario) {
		try {

			String sqlUser = "INSERT INTO usuario (idusuario, passwd, url, salt, tipo) VALUES (?, ?, ?, ?)";
			PreparedStatement preparedStatementUser = conexion.prepareStatement(sqlUser);

			preparedStatementUser.setString(1, usuario.getId().trim());
			preparedStatementUser.setString(2, usuario.getContraseña().trim());
			preparedStatementUser.setString(3, usuario.getUrl());
			preparedStatementUser.setString(4, usuario.getSalt());
			preparedStatementUser.setString(5, usuario.getTipo());
			preparedStatementUser.executeUpdate();

			System.out.println("Usuario guardado en la base de datos correctamente.");

		} catch (SQLException e) {
			e.printStackTrace();
			System.err.println("Error al guardar el usuario en la base de datos.");

		}
	}

	public Usuario readUsuario(String nombreUsuario) {
		Usuario usuarioTraido = null;
		try {
			String consulta = "SELECT * FROM usuario WHERE idusuario = ?";
			PreparedStatement statement = conexion.prepareStatement(consulta);
			statement.setString(1, nombreUsuario.trim());

			ResultSet resultSet = statement.executeQuery();
			if (resultSet.next()) {

				String contra = resultSet.getString("passwd");
				String url = resultSet.getString("url");
				String salt = resultSet.getString("salt");
				String tipo = resultSet.getString("tipo");

				usuarioTraido = new Usuario(nombreUsuario, contra, url, salt, tipo);

			}
		} catch (SQLException e) {
			e.printStackTrace();

		}
		return usuarioTraido;
	}

	public void updateUser(Usuario usuario) {
		try {
			String sqlUpdate = "UPDATE usuario SET passwd = ?,url= ? ,salt = ?,tipo = ?  WHERE idusuario = ?";
			PreparedStatement preparedStatementUpdate = conexion.prepareStatement(sqlUpdate);

			preparedStatementUpdate.setString(1, usuario.getContraseña().trim());
			preparedStatementUpdate.setString(2, usuario.getUrl());
			preparedStatementUpdate.setString(2, usuario.getSalt());
			preparedStatementUpdate.setString(2, usuario.getTipo());
			preparedStatementUpdate.setString(3, usuario.getId());

			int filasActualizadas = preparedStatementUpdate.executeUpdate();

			if (filasActualizadas > 0) {
				System.out.println("Usuario actualizado en la base de datos correctamente.");
			} else {
				System.out.println(
						"Error al actualizar");
			}

		} catch (SQLException e) {
			e.printStackTrace();
			System.err.println("Error al actualizar el usuario en la base de datos.");
		}
	}

}
