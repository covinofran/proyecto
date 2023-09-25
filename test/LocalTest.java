import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class LocalTest {
	@Test
	void test() {
		UsuarioFactory localFactory = new ClienteFactory();
		Usuario local = localFactory.crearUsuario("pau", "0109", "logo", "2606");
		assertAll(() -> assertEquals("pau", local.getId()), () -> assertEquals("0109", local.getContraseña()),
				() -> assertEquals("logo", local.getUrl()), () -> assertEquals("2606", local.getSalt()));
	}
}
