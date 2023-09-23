public class ClienteFactory extends UsuarioFactory {
	public Usuario crearUsuario(String id, String contraseña, String url, String salt) {
		return new Cliente(id, contraseña, url, salt);
	}
}