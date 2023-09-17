public abstract class UsuarioFactory {
	public abstract Usuario crearUsuario(String id, String contraseña, Imagen imagen,String salt);
}