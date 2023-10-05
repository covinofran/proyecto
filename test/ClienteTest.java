import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class UsuarioTest {
	@Test
	void test() {
		Usuario user = new Usuario("pau", "0109", "logo", "2606","Cliente");
		assertAll(() -> assertEquals("pau", user.getId()), () -> assertEquals("0109", user.getContraseña()),
				() -> assertEquals("logo", user.getUrl()), () -> assertEquals("2606", user.getSalt()));
	}
}
