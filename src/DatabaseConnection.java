import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DatabaseConnection {
	private static DatabaseConnection instancia;
	private Connection conexion;
	
	
	/*
	 * 
	 * DECLARADA COMO CONSTANTES LOS DATOS DE ACCESO A MYSQL
	 * FALTARIA ENCRIPTARLOS A TODOS!?!?
	 * SI ES NECESARIO O NO, AVERIGUAR Y PREGUNTAR ESO
	 * 
	 * */
	
	
	
	private static final String URL = "jdbc:mysql://localhost:3306/proyecto";
	private static final String USUARIO = "root";
	private static final String CONTRASEÑA = "123123";

	private DatabaseConnection() {

		try {

			conexion = DriverManager.getConnection(URL, USUARIO, CONTRASEÑA);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("Error al conectar a la base de datos");
		}
	}

	public static DatabaseConnection getInstancia() {
		if (instancia == null) {
			instancia = new DatabaseConnection();
		}
		return instancia;
	}

	public Connection getConexion() {
		return conexion;
	}

	public boolean autenticarUsuario(String nombreUsuario, String contraseña) {
		// Realiza una consulta a la base de datos para verificar las credenciales del
		// usuario
		// Retorna true si la autenticación es exitosa, de lo contrario false
		try {
			String consulta = "SELECT * FROM usuarios WHERE nombre_usuario = ? AND contraseña = ?";
			PreparedStatement statement = conexion.prepareStatement(consulta);
			statement.setString(1, nombreUsuario);
			statement.setString(2, contraseña);
			ResultSet resultSet = statement.executeQuery();

			return resultSet.next(); // Si hay un resultado, las credenciales son válidas
		} catch (SQLException e) {
			e.printStackTrace();
			return false; // En caso de error, la autenticación falla
		}
	}

	public void close() {
		try {
			// Tu código de acceso a la base de datos aquí

			// Cerrar la conexión cuando hayas terminado
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
			 * ACA FALLA PORQUE SI AGREGO UN ID QUE YA EXISTE ME AGREGA LA IMAGEN Y DESPUES CONTROLA SI EXISTE EL ID,
			 * POR LO TANTO LA IMAGEN SE AGREGA PERO NO SE AGREGA EL USUARIO. QUEDARIA UNA IMAGEN AL PEDO CARGADA
			 * SOLUCIONAR ESTE PROBLEMA DE ALGUNA FORMA, LA MAS PRACTICA SERIA AGRENDANDO LA RUTA DE LA IMAGEN
			 * DIRECTAMENTE AL USUARIO PERO PARA HACERLO MAS DIFICIL Y PROBAR DISTINTAS FORMAS 
			 * BUSCAR COMO SE SOLUCIONA ASI
			 * 
			 * */
			// Primero, inserta la imagen en la tabla de imágenes
			String sqlImagen = "INSERT INTO imagen (nombre, ruta) VALUES (?, ?)";
			PreparedStatement preparedStatementImagen = conexion.prepareStatement(sqlImagen);

			Imagen imagen = usuario.getImagen();
			preparedStatementImagen.setString(1, imagen.getNombre());
			preparedStatementImagen.setString(2, imagen.getUrl());

			preparedStatementImagen.executeUpdate();

			// Luego, obtén el ID de la imagen recién insertada
			String sqlMaxId = "SELECT MAX(idimagen) AS max_id FROM imagen";
			PreparedStatement preparedStatementMaxId = conexion.prepareStatement(sqlMaxId);
			ResultSet resultSet = preparedStatementMaxId.executeQuery();

			int idImagen = 0;
			if (resultSet.next()) {
				idImagen = resultSet.getInt("max_id");
			}

			// Ahora, inserta el usuario en la tabla de usuarios
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
