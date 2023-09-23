public  class ClienteFactory extends UsuarioFactory  {
	public Usuario crearUsuario(String id, String contraseña, Imagen imagen, String salt) {
		return new Cliente(id, contraseña, imagen, salt);
	}
}