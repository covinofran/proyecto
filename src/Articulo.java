public class Articulo {
	private String nombre;
	private double precio;
	private int id;
	private Imagen imagen;

	public Articulo(String nombre, double precio, int id, Imagen imagen) {
		this.nombre = nombre;
		this.precio = precio;
		this.id = id;
		this.imagen = imagen;
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

	public Imagen getImagen() {
		return imagen;
	}

	public void setImagen(Imagen imagen) {
		this.imagen = imagen;
	}

}
