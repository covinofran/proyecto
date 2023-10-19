import java.util.ArrayList;

public class Tienda {
	private ArrayList<Articulo> articulos;
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

	public ArrayList<Articulo> getArticulos() {
		return articulos;
	}

	public void setArticulos(ArrayList<Articulo> articulos) {
		this.articulos = articulos;
	}

	public String getNombreTienda() {
		return nombreTienda;
	}

	public void setNombreTienda(String nombreTienda) {
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

	@Override
	public String toString() {
		return "Tienda [nombreTienda=" + nombreTienda + ", nombreUsuario=" + nombreUsuario + ", url=" + url + "]";
	}
}
