public class LocalFactory extends UsuarioFactory {
	public Usuario crearUsuario(String id, String contraseña, Imagen imagen, String salt) {
		return new Local(id, contraseña, imagen, salt);
	}
}
