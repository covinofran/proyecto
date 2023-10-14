import java.util.ArrayList;
import java.util.List;

public class Usuario {
	@Override
	public String toString() {
		return "Usuario [id=" + id + ", contraseña=" + contraseña + ", url=" + url + ", salt=" + salt + ", tipo=" + tipo
				+ "]";
	}

	private String id;
	private String contraseña;
	private String url;
	private String salt;
	private String tipo;
	private List<Articulo> carrito;

	public Usuario(String id, String contraseña, String url, String salt, String tipo) {
		this.id = id;
		this.contraseña = contraseña;
		this.url = url;
		this.salt = salt;
		this.tipo = tipo;
		this.carrito = new ArrayList<>();
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

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public void agregarArticulo(Articulo articulo) {
		carrito.add(articulo);
	}

	public void eliminarArticulo(Articulo articulo) {
		carrito.remove(articulo);
	}

	public List<Articulo> getCarrito() {
		return carrito;
	}

	public void setCarrito(List<Articulo> carrito) {
		this.carrito = carrito;
	}
}
