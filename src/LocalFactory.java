public class LocalFactory extends UsuarioFactory {
	public Usuario crearUsuario(String id, String contraseña, String url, String salt) {
		return new Local(id, contraseña, url, salt);
	}
}
