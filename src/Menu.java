import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Menu {
	public Menu() {
		// Crear una ventana principal
		JFrame frame = new JFrame("Menú de Usuario");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(300, 150);

		// Crear un panel para colocar los botones
		JPanel panel = new JPanel();
		frame.add(panel);

		JButton crearUsuarioButton = new JButton("Crear Usuario");
		crearUsuarioButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFrame frame2 = new JFrame("Panel de Usuario");
				frame2.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				frame2.setSize(400, 200);

				UsuarioPanel usuarioPanel = new UsuarioPanel();
				frame2.add(usuarioPanel);
				frame2.setLocationRelativeTo(null);
				frame2.setVisible(true);
				frame.dispose();
			}
		});

		JButton iniciarSesionButton = new JButton("Iniciar Sesión");
		iniciarSesionButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				new VentanaInicioSesion();
				frame.dispose();

			}
		});

		// Agregar los botones al panel
		panel.add(crearUsuarioButton);
		panel.add(iniciarSesionButton);

		// Centrar la ventana en la pantalla
		frame.setLocationRelativeTo(null);

		// Mostrar la ventana
		frame.setVisible(true);
	}

}
