import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseSingleton {
	private static DatabaseSingleton instancia;
	private static Connection conexion;

	private DatabaseSingleton() {
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
	
	public static synchronized DatabaseSingleton getInstancia() {
		if (instancia == null) {
			instancia = new DatabaseSingleton();
		}
		return instancia;
	}

	public static Connection getConexion() {
		return conexion;
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

}
