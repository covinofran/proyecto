import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class ArticuloTest {

	@Test
	void test() {
		Articulo a = new Articulo("papa",1.2,1,"imagen.jpg");
		assertAll(() -> assertEquals("papa", a.getNombre()), () -> assertEquals(1.2, a.getPrecio()),
				() -> assertEquals(1, a.getId()), () -> assertEquals("imagen.jpg", a.getUrl()));
	}
}
