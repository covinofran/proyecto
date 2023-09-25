import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Menu {
	public Menu() {
		JFrame frame = new JFrame("Menú de Usuario");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(null);
		frame.setResizable(false);
		ImageIcon logo = new ImageIcon("images\\logo.png");
		frame.setIconImage(logo.getImage());

		JButton crearUsuarioButton = new JButton("Crear Usuario");
		crearUsuarioButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				new CrearUsuario();
				frame.dispose();
			}
		});

		JButton iniciarSesionButton = new JButton("Iniciar Sesión");
		iniciarSesionButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				new IniciarSesion();
				frame.dispose();
			}
		});
		crearUsuarioButton.setBounds(5, 50, 130, 40);
		crearUsuarioButton.setFocusable(false);
		frame.add(crearUsuarioButton);
		iniciarSesionButton.setBounds(150, 50, 130, 40);
		iniciarSesionButton.setFocusable(false);
		frame.add(iniciarSesionButton);
		frame.setBounds(0, 0, 300, 200);
		frame.setLocationRelativeTo(null);

		frame.setVisible(true);

	}
}
