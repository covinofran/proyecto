import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Menu {
	
	// Ventana inicial de menu
	public Menu() {
		DatabaseSingleton db = DatabaseSingleton.getInstancia();
		JFrame vMenu = new JFrame("Bienvenido");
		vMenu.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		vMenu.setLayout(null);
		vMenu.setResizable(false);

		ImageIcon logo = new ImageIcon("images\\logo.png");
		vMenu.setIconImage(logo.getImage());
		
		JButton crearUsuarioButton = new JButton("Crear Usuario");
		crearUsuarioButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				new CrearUsuario();
				vMenu.dispose();
			}
		});

		JButton iniciarSesionButton = new JButton("Iniciar Sesión");
		iniciarSesionButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				new IniciarSesion();
				vMenu.dispose();
			}
		});
		
		JButton salirButton = new JButton("Salir");
		salirButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				db.close();
				System.exit(0);
			}
		});
		
		
		crearUsuarioButton.setBounds(5, 50, 130, 40);
		crearUsuarioButton.setFocusable(false);
		vMenu.add(crearUsuarioButton);

		iniciarSesionButton.setBounds(150, 50, 130, 40);
		iniciarSesionButton.setFocusable(false);
		vMenu.add(iniciarSesionButton);
		
		salirButton.setBounds(85, 100, 130, 40);
		salirButton.setFocusable(false);
		vMenu.add(salirButton);

		vMenu.setBounds(0, 0, 300, 200);
		vMenu.setLocationRelativeTo(null);
		vMenu.setVisible(true);
	}

}
