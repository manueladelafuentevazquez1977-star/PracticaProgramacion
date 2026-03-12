package es.studium;

import java.awt.Button;
import java.awt.Color;
import java.awt.Dialog;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.Label;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Connection;

public class Login extends WindowAdapter implements ActionListener
{
// Atributos
	Frame ventana = new Frame("Login");

	Label lblUsuario = new Label("Usuario:");
	Label lblClave = new Label("Clave:");
	TextField txtUsuario = new TextField(20);
	TextField txtClave = new TextField(20);
	Button btnAceptar = new Button("Aceptar");
	Button btnLimpiar = new Button("Limpiar");

	Dialog dlgDialogo = new Dialog(ventana, "Error", true);
	Label lblMensaje = new Label("Credenciales Incorrectas");

	public Login()
	{
		ventana.setLayout(new FlowLayout());

		ventana.addWindowListener(this);
		btnAceptar.addActionListener(this);
		btnLimpiar.addActionListener(this);

		ventana.setSize(350, 130);
		ventana.add(lblUsuario);
		ventana.add(txtUsuario);
		ventana.add(lblClave);
		txtClave.setEchoChar('*');
		ventana.add(txtClave);
		ventana.add(btnAceptar);
		ventana.add(btnLimpiar);
		ventana.setBackground(Color.pink);
		ventana.setLocationRelativeTo(null);
		ventana.setResizable(false);
		ventana.setVisible(true);

		dlgDialogo.setLayout(new FlowLayout());
		dlgDialogo.setSize(200, 80);
		dlgDialogo.add(lblMensaje);
		dlgDialogo.setResizable(false);
		dlgDialogo.setLocationRelativeTo(null);
// Para poder cerrar el Diálogo
		dlgDialogo.addWindowListener(this);
	}

	public static void main(String[] args)
	{
		new Login();
	}

	@Override
	public void windowClosing(WindowEvent e)
	{
		if (dlgDialogo.isActive())
		{
			dlgDialogo.setVisible(false);
		} else
		{
			System.exit(0);
		}
	}

	@Override
	public void actionPerformed(ActionEvent evento)
	{
		if (evento.getSource().equals(btnLimpiar))
		{
			txtUsuario.setText("");
			txtClave.setText("");
			txtUsuario.requestFocus();
		} else if (evento.getSource().equals(btnAceptar))
		{
			String usuarioDado = txtUsuario.getText();
			String claveDada = txtClave.getText();
// Conectar a la BD gestion
			GestionBaseDatos gestionBD = new GestionBaseDatos();
			Connection conexion = gestionBD.conectar();
// Hacer consulta
			int respuesta = gestionBD.comprobarCredenciales(usuarioDado, claveDada);
			
			System.out.println(respuesta);
			
			gestionBD.desconectar(conexion);
			if (respuesta == 0)
			{
				System.out.println("Tipo 0: Administrador");
				ventana.dispose();
				new Principal(respuesta);
			} else if (respuesta == 1)
			{
				System.out.println("Tipo 1: Básico");
				ventana.dispose();
				new Principal(respuesta);
			} else
			{
				dlgDialogo.setVisible(true);
			}
		}
	}
}