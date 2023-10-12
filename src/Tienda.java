import java.util.ArrayList;
import java.util.List;

public class Tienda {
	private List<Articulo> articulos;
	private int id;
	private String nombreUsuario;

	public Tienda(int id, String nombreUsuario) {
		this.id = id;
		this.nombreUsuario = nombreUsuario;
		this.articulos = new ArrayList<>();

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

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getnombreUsuario() {
		return nombreUsuario;
	}

	public void setnombreUsuario(String nombreUsuario) {
		this.nombreUsuario = nombreUsuario;
	}
}
