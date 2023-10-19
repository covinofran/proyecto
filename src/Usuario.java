import java.util.ArrayList;
import java.util.List;

public class Usuario {

	private String nombreUsuario;
	private String contraseña;
	private String url;
	private String salt;
	private String tipo;
	private List<Articulo> carrito;

	public Usuario(String nombreUsuario, String contraseña, String url, String salt, String tipo) {
		this.nombreUsuario = nombreUsuario;
		this.contraseña = contraseña;
		this.url = url;
		this.salt = salt;
		this.tipo = tipo;
		this.carrito = new ArrayList<>();
	}

	public String getNombreUsuario() {
		return nombreUsuario;
	}

	public void setNombreUsuario(String nombreUsuario) {
		this.nombreUsuario = nombreUsuario;
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

	@Override
	public String toString() {
		return "Usuario [nombre Usuario=" + nombreUsuario + ", contraseña=" + contraseña + ", url=" + url + ", salt="
				+ salt + ", tipo=" + tipo + "]";
	}

}
