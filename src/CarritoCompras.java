import java.util.ArrayList;
import java.util.List;

public class CarritoCompras implements Subject {

	private List<Observer> observadores;
	private List<Articulo> items;
	private Usuario user;
	private Tienda tienda;

	public CarritoCompras(Usuario user) {
		this.user = user;
		this.observadores = new ArrayList<>();
		this.items = new ArrayList<>();
	}

	public void agregarArticulo(Articulo articulo) {
		items.add(articulo);
		notificarObservadores();
	}

	public void eliminarArticulo(Articulo articulo) {
		items.remove(articulo);
		notificarObservadores();
	}

	public List<Articulo> getCarrito() {
		return items;
	}

	public void setCarrito(List<Articulo> carrito) {
		this.items = carrito;
		
	}

	public Usuario getUser() {
		return user;
	}

	public void setUser(Usuario user) {
		this.user = user;
	}

	public Tienda getTienda() {
		return tienda;
	}

	public void setTienda(Tienda tienda) {
		this.tienda = tienda;
	}

	@Override
	public void registrarObservador(Observer observador) {
		observadores.add(observador);
	}

	@Override
	public void notificarObservadores() {
		for (Observer observador : observadores) {
			observador.actualizarCarrito(items);
		}
	}

	@Override
	public void eliminarObservador(Observer observador) {
		observadores.remove(observador);
	}
}