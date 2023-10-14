import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Connection;
import java.sql.SQLException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class Sesion {
	// Ventana de sesion ya inciada
	public Sesion(String nombreUsuario) {
		Connection db = DatabaseSingleton.getConexion();
		UserOperation operacionesUsuario = new UserOperation(db);
		Usuario userActual = operacionesUsuario.readUsuario(nombreUsuario);
		JFrame vSesion = new JFrame("Sesión Iniciada - Usuario: " + userActual.getId());
		vSesion.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

		ImageIcon logo = new ImageIcon("images\\logo.png");
		vSesion.setIconImage(logo.getImage());
		ImageIcon urlImagen = new ImageIcon(userActual.getUrl());
		System.out.println(userActual.getUrl());
		Image reescalado = urlImagen.getImage().getScaledInstance(80, 80, Image.SCALE_SMOOTH);
		ImageIcon imagenFinal = new ImageIcon(reescalado);
		JLabel imagenPerfil = new JLabel(imagenFinal);

		JButton cerrarButton = new JButton("Cerrar Sesion");
		cerrarButton.addActionListener((ActionListener) new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				vSesion.dispose();
				new Menu();
			}
		});
		vSesion.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				try {
					db.close();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				System.exit(0);
			}
		});
		new VistaTiendas();
		vSesion.add(imagenPerfil);
		imagenPerfil.setBounds(0, 0, 80, 80);

		vSesion.add(cerrarButton);
		vSesion.setLayout(null);
		vSesion.setBounds(0, 0, 800, 600);
		vSesion.setLocationRelativeTo(null);
		vSesion.setVisible(true);
		cerrarButton.setBounds(650, 10, 120, 40);
		cerrarButton.setFocusable(false);

	}
}
