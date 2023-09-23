public abstract class UsuarioFactory {
	public abstract Usuario crearUsuario(String id, String contraseña, String url, String salt);
}