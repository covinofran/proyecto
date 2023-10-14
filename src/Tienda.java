import java.util.ArrayList;
import java.util.List;

public class Tienda {
	private List<Articulo> articulos;
	private String nombreTienda;
	private String nombreUsuario;
	private String url;

	public Tienda(String nombreTienda, String nombreUsuario, String url) {
		this.nombreTienda = nombreTienda;
		this.nombreUsuario = nombreUsuario;
		this.articulos = new ArrayList<>();
		this.url = url;

	}

	public void agregarArticulo(Articulo articulo) {
		articulos.add(articulo);
	}

	public void eliminarArticulo(Articulo articulo) {
		articulos.remove(articulo);
	}

	public List<Articulo> getArticulos() {
		return articulos;
	}

	public void setArticulos(List<Articulo> articulos) {
		this.articulos = articulos;
	}

	public String getnombreTienda() {
		return nombreTienda;
	}

	public void setnombreTienda(String nombreTienda) {
		this.nombreTienda = nombreTienda;
	}

	public String getnombreUsuario() {
		return nombreUsuario;
	}

	public void setnombreUsuario(String nombreUsuario) {
		this.nombreUsuario = nombreUsuario;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
}
