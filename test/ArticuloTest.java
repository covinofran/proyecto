import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class ArticuloTest {

	@Test
	void test() {
		Articulo a = new Articulo("tienda", "papa", 1.2, 1, "imagen.jpg");
		assertAll(() -> assertEquals("papa", a.getNombreArt()), () -> assertEquals(1.2, a.getPrecio()),
				() -> assertEquals("tienda", a.getNombreTienda()), () -> assertEquals("imagen.jpg", a.getUrl()));
	}
}
