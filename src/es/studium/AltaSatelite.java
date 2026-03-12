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


public class AltaSatelite extends WindowAdapter implements ActionListener

{

	Frame ventana = new Frame("Alta");

	Label lblNombre = new Label("Nombre:");

	TextField txtNombre = new TextField(10);

	Label lblTipo = new Label("Tipo:");

	TextField txtTipo = new TextField(10);

	Label lblFechaLanzamiento = new Label("FechaLanzamiento:");

	TextField txtFechaLanzamiento = new TextField(10);

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

	public AltaSatelite()

	{

		ventana.setLayout(new FlowLayout());

		ventana.setSize(250, 180);

		ventana.addWindowListener(this);

		btnAceptar.addActionListener(this);

		btnLimpiar.addActionListener(this);

		ventana.add(lblNombre);

		ventana.add(txtNombre);

		ventana.add(lblTipo);

		ventana.add(txtTipo);

		ventana.add(lblFechaLanzamiento);

		ventana.add(txtFechaLanzamiento);

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

		new AltaSatelite();

	}

	@Override

	public void windowClosing(WindowEvent e)

	{

		if (dlgMensaje.isActive())

		{

			dlgMensaje.setVisible(false);

		}

		else

		{

			// Salir

			System.exit(0);

		}

	}

	@Override

	public void actionPerformed(ActionEvent evento)

	{

		if (evento.getSource().equals(btnAceptar))

		{

			// Conectar a una BD

			try

			{

				// Cargar los drivers

				Class.forName(driver);

				// Establecer la conexión

				connection = DriverManager.getConnection(url, login, password);

				System.out.println("Conexión establecida");

				// Crear la sentencia de consulta o de alta o de baja o de actu...

				statement = connection.createStatement();

				// Ejecutar la instrucción SQL

				// Reemplaza esta línea en actionPerformed():
				
				sentenciaSQL = "INSERT INTO paises VALUES(null, '" + txtNombre.getText() + "','"
						+ txtTipo.getText() + "','" + txtFechaLanzamiento.getText() + "')";
				


				statement.executeUpdate(sentenciaSQL);

				// Alta correcta

				lblMensaje.setText("Alta correcta");

				dlgMensaje.setVisible(true);

			}

			catch (ClassNotFoundException cnfe)

			{

				System.err.println("Error de driver" + cnfe.getMessage());

			}

			catch (SQLException se)

			{

				lblMensaje.setText("Error en Alta");

				dlgMensaje.setVisible(true);

			}

			finally

			{

				try

				{

					// Desconectar de la BD

					if (connection != null)

					{

						connection.close();

					}

				}

				catch (SQLException e)

				{

					System.err.println("Error al cerrar conexión");

				}

				System.out.println("Fin del programa");

			}

		}

		else if (evento.getSource().equals(btnLimpiar))

		{

			txtNombre.setText("");

			txtTipo.setText("");

			txtFechaLanzamiento.setText("");

			txtNombre.requestFocus();

		}

	}

}
