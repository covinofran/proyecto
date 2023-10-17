public class Articulo {
	private String nombreTienda;
	private String nombreArt;
	private double precio;
	private int cantidad;
	private String url;

	public Articulo(String nombreTienda, String nombreArt, double precio, int cantidad, String url) {
		this.nombreTienda = nombreTienda;
		this.nombreArt = nombreArt;
		this.precio = precio;
		this.cantidad = cantidad;
		this.url = url;
	}

	public String getNombreTienda() {
		return nombreTienda;
	}

	public void setNombreTienda(String nombreTienda) {
		this.nombreTienda = nombreTienda;
	}

	public int getCantidad() {
		return cantidad;
	}

	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getNombreArt() {
		return nombreArt;
	}

	public void setNombreArt(String nombreArt) {
		this.nombreArt = nombreArt;
	}

	public double getPrecio() {
		return precio;
	}

	public void setPrecio(double precio) {
		this.precio = precio;
	}

	public String getUrl() {
		return url;
	}

	public void setImagen(String url) {
		this.url = url;
	}

}
