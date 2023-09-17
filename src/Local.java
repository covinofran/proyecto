import java.util.ArrayList;
import java.util.List;

public class Local extends Usuario {
	private List<Articulo> articulos;

	public Local(String id, String contraseña, Imagen imagen, String salt) {
		super(id, contraseña, imagen, salt);
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
}
