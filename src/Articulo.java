public class Articulo {
	private String nombre;
	private double precio;
	private int id;
	private String url;

	public Articulo(String nombre, double precio, int id, String url) {
		this.nombre = nombre;
		this.precio = precio;
		this.id = id;
		this.url = url;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public double getPrecio() {
		return precio;
	}

	public void setPrecio(double precio) {
		this.precio = precio;
	}

	public double getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUrl() {
		return url;
	}

	public void setImagen(String url) {
		this.url = url;
	}

}
