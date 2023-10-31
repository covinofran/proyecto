
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class TiendaTest {
	@Test
	void test() {
		Tienda tienda = new Tienda("tiedapau","pau","logo");
		
		assertAll(() -> assertEquals("tiendapau", tienda.getNombreTienda()), () -> assertEquals("pau", tienda.getNombreUsuario()),
				() -> assertEquals("logo", tienda.getUrl()));
	}
}
