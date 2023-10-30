import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import org.mindrot.jbcrypt.BCrypt;

public class Usuario implements dbOperation {
	private Connection db;
	private String nombreUsuario;
	private String contraseña;
	private String url;
	private String salt;
	private String tipo;
	private List<Articulo> carrito;

	public Usuario(String nombreUsuario, String contraseña, String url, String salt, String tipo) {
		this.nombreUsuario = nombreUsuario;
		this.contraseña = contraseña;
		this.url = url;
		this.salt = salt;
		this.tipo = tipo;
		this.carrito = new ArrayList<>();
		this.db = DatabaseSingleton.getConexion();
	}

	public String getNombreUsuario() {
		return nombreUsuario;
	}

	public void setNombreUsuario(String nombreUsuario) {
		this.nombreUsuario = nombreUsuario;
	}

	public String getContraseña() {
		return contraseña;
	}

	public void setContraseña(String contraseña) {
		this.contraseña = contraseña;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getSalt() {
		return salt;
	}

	public void setSalt(String salt) {
		this.salt = salt;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public void agregarArticulo(Articulo articulo) {
		carrito.add(articulo);
	}

	public void eliminarArticulo(Articulo articulo) {
		carrito.remove(articulo);
	}

	public List<Articulo> getCarrito() {
		return carrito;
	}

	public void setCarrito(List<Articulo> carrito) {
		this.carrito = carrito;
	}

	@Override
	public String toString() {
		return "Usuario [nombre Usuario=" + nombreUsuario + ", contraseña=" + contraseña + ", url=" + url + ", salt="
				+ salt + ", tipo=" + tipo + "]";
	}

	@Override
	public void create() {
		try {

			String sqlUser = "INSERT INTO usuario (nombreUsuario, passwd, url, salt, tipo) VALUES (?, ?, ?, ?,?)";
			PreparedStatement preparedStatementUser = db.prepareStatement(sqlUser);

			preparedStatementUser.setString(1, nombreUsuario.trim());
			preparedStatementUser.setString(2, contraseña.trim());
			preparedStatementUser.setString(3, url);
			preparedStatementUser.setString(4, salt);
			preparedStatementUser.setString(5, tipo);
			preparedStatementUser.executeUpdate();

			System.out.println("Usuario guardado en la base de datos correctamente.");

		} catch (SQLException e) {
			e.printStackTrace();
			System.err.println("Error al guardar el usuario en la base de datos.");

		}
	}

	@Override
	public void update() {
		try {
			String sqlUpdate = "UPDATE usuario SET passwd = ?,url= ? ,salt = ?,tipo = ?  WHERE nombreUsuario = ?";
			PreparedStatement preparedStatementUpdate = db.prepareStatement(sqlUpdate);

			preparedStatementUpdate.setString(1, this.contraseña.trim());
			preparedStatementUpdate.setString(2, this.url);
			preparedStatementUpdate.setString(2, this.salt);
			preparedStatementUpdate.setString(2, this.tipo);
			preparedStatementUpdate.setString(3, this.nombreUsuario);

			int filasActualizadas = preparedStatementUpdate.executeUpdate();

			if (filasActualizadas > 0) {
				System.out.println("Usuario actualizado en la base de datos correctamente.");
			} else {
				System.out.println("Error al actualizar");
			}

		} catch (SQLException e) {
			e.printStackTrace();
			System.err.println("Error al actualizar el usuario en la base de datos.");
		}
	}

	@Override
	public Usuario read() {

		try {
			String consulta = "SELECT * FROM usuario WHERE nombreUsuario = ?";
			PreparedStatement statement = db.prepareStatement(consulta);
			statement.setString(1, nombreUsuario.trim());

			ResultSet resultSet = statement.executeQuery();
			if (resultSet.next()) {

				this.contraseña = resultSet.getString("passwd");
				this.url = resultSet.getString("url");
				this.salt = resultSet.getString("salt");
				this.tipo = resultSet.getString("tipo");

			}
		} catch (SQLException e) {
			e.printStackTrace();

		}
		return this;
	}

	@Override
	public void delete() {
		// TODO Auto-generated method stub

	}

	public boolean autenticarUsuario(String contra, JFrame frame) {

		if (BCrypt.checkpw(contra.trim(), this.contraseña)) {
			return true;
		} else {

			JOptionPane.showMessageDialog(frame, "Usuario o contraseña incorrecta", "Error al iniciar",
					JOptionPane.ERROR_MESSAGE);

			return false;
		}

	}
}
