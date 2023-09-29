import java.util.List;

public abstract class Usuario {
	private String id;
	private String contraseña;
	private String url;
	private String salt;

	public Usuario(String id, String contraseña, String url, String salt) {
		this.id = id;
		this.contraseña = contraseña;
		this.url = url;
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
	public abstract void agregarArticulo(Articulo articulo);
	public abstract List<Articulo> getArticulos();
	public abstract void setArticulos(List<Articulo> articulos);
}
