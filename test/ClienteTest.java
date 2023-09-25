import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class ClienteTest {
	@Test
	void test() {
		UsuarioFactory clienteFactory = new ClienteFactory();
		Usuario cliente = clienteFactory.crearUsuario("pau", "0109", "logo", "2606");
		assertAll(() -> assertEquals("pau", cliente.getId()), () -> assertEquals("0109", cliente.getContraseña()),
				() -> assertEquals("logo", cliente.getUrl()), () -> assertEquals("2606", cliente.getSalt()));
	}
}
