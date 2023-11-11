public interface Subject {
	void registrarObservador(Observer observador);

	void eliminarObservador(Observer observador);

	void notificarObservadores();
}