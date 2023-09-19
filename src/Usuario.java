public abstract class Usuario {
	private String id;
	private String contraseña;
	private Imagen imagen;
	private String salt;

	public Usuario(String id, String contraseña, Imagen imagen, String salt) {
		this.id = id;
		this.contraseña = contraseña;
		this.imagen = imagen;
		this.salt = salt;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getContraseña() {
		return contraseña;
	}

	public void setContraseña(String contraseña) {
		this.contraseña = contraseña;
	}

	public Imagen getImagen() {
		return imagen;
	}

	public void setImagen(Imagen imagen) {
		this.imagen = imagen;
	}

	public String getSalt() {
		return salt;
	}

	public void setSalt(String salt) {
		this.salt = salt;
	}

}
