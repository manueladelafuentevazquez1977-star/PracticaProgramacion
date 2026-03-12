package es.studium;

import java.awt.Button;
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
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class AltaAstronauta extends WindowAdapter implements ActionListener
{
	Frame ventana = new Frame("Alta Astronauta");

	Label lblNombre = new Label("Nombre:");
	TextField txtNombre = new TextField(10);

	Label lblApellidos = new Label("Apellidos:");
	TextField txtApellidos = new TextField(10);

	Label lblFechaNacimiento = new Label("Fecha Nacimiento:");
	TextField txtFechaNacimiento = new TextField(10);

	Label lblAgnosExperiencia = new Label("Años Experiencia:");
	TextField txtAgnosExperiencia = new TextField(10);

	Label lblIdPais = new Label("ID País:");
	TextField txtIdPais = new TextField(10);

	Button btnAceptar = new Button("Aceptar");
	Button btnLimpiar = new Button("Limpiar");

	String driver = "com.mysql.cj.jdbc.Driver";
	String url = "jdbc:mysql://localhost:3306/ejerciciopracticapro";
	String login = "usuarioPractica";
	String password = "basico";

	String sentenciaSQL = "";
	Connection connection = null;
	Statement statement = null;

	Dialog dlgMensaje = new Dialog(ventana, "Respuesta", true);
	Label lblMensaje = new Label("Error en Alta");

	public AltaAstronauta()
	{
		ventana.setLayout(new FlowLayout());
		ventana.setSize(270, 220);
		ventana.addWindowListener(this);
		btnAceptar.addActionListener(this);
		btnLimpiar.addActionListener(this);

		// Añadir todos los campos de la tabla astronautas
		ventana.add(lblNombre);
		ventana.add(txtNombre);
		ventana.add(lblApellidos);
		ventana.add(txtApellidos);
		ventana.add(lblFechaNacimiento);
		ventana.add(txtFechaNacimiento);
		ventana.add(lblAgnosExperiencia);
		ventana.add(txtAgnosExperiencia);
		ventana.add(lblIdPais);
		ventana.add(txtIdPais);
		ventana.add(btnAceptar);
		ventana.add(btnLimpiar);

		ventana.setResizable(false);
		ventana.setLocationRelativeTo(null);

		dlgMensaje.setLayout(new FlowLayout());
		dlgMensaje.setSize(50, 80);
		dlgMensaje.addWindowListener(this);
		dlgMensaje.setResizable(false);
		dlgMensaje.setLocationRelativeTo(null);
		dlgMensaje.add(lblMensaje);

		ventana.setVisible(true);
	}

	public static void main(String[] args)
	{
		new AltaAstronauta();
	}

	@Override
	public void windowClosing(WindowEvent e)
	{
		if (dlgMensaje.isActive())
		{
			dlgMensaje.setVisible(false);
		} else
		{
			System.exit(0);
		}
	}

	@Override
	public void actionPerformed(ActionEvent evento)
	{
		if (evento.getSource().equals(btnAceptar))
		{
			try
			{
				Class.forName(driver);
				connection = DriverManager.getConnection(url, login, password);
				System.out.println("Conexión establecida");

				statement = connection.createStatement();

				// INSERT para la tabla astronautas
				sentenciaSQL = "INSERT INTO astronautas (nombreAstronauta, apellidosAstronauta, fechaNacimientoAstronauta, agnosExperienciaAstronauta, idPaisFK) "
						+ "VALUES ('" + txtNombre.getText() + "', '" + txtApellidos.getText() + "', '"
						+ txtFechaNacimiento.getText() + "', '" + txtAgnosExperiencia.getText() + "', "
						+ txtIdPais.getText() + ")";

				
				System.out.println(sentenciaSQL);
				statement.executeUpdate(sentenciaSQL);

				lblMensaje.setText("Alta correcta");
				dlgMensaje.setVisible(true);
			} catch (ClassNotFoundException cnfe)
			{
				System.err.println("Error de driver " + cnfe.getMessage());
			} catch (SQLException se)
			{
				lblMensaje.setText("Error en Alta");
				dlgMensaje.setVisible(true);
			} finally
			{
				try
				{
					if (connection != null)
					{
						connection.close();
					}
				} catch (SQLException e)
				{
					System.err.println("Error al cerrar conexión");
				}
				System.out.println("Fin del programa");
			}
		} else if (evento.getSource().equals(btnLimpiar))
		{
			txtNombre.setText("");
			txtApellidos.setText("");
			txtFechaNacimiento.setText("");
			txtAgnosExperiencia.setText("");
			txtIdPais.setText("");
			txtNombre.requestFocus();
		}
	}
}
