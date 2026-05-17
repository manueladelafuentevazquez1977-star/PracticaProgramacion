
package es.studium;

import java.awt.Button;

import java.awt.Choice;

import java.awt.Dialog;

import java.awt.FlowLayout;

import java.awt.Frame;

import java.awt.Label;

import java.awt.Panel;

import java.awt.event.ActionEvent;

import java.awt.event.ActionListener;

import java.awt.event.WindowAdapter;

import java.awt.event.WindowEvent;

import java.sql.Connection;

import java.sql.DriverManager;

import java.sql.ResultSet;

import java.sql.SQLException;

import java.sql.Statement;


public class BajaPais extends WindowAdapter implements ActionListener

{

	Frame ventana = new Frame("Baja");

	Choice choPaises = new Choice();

	Button btnEliminar = new Button("Eliminar");

	Dialog dlgConfirmar = new Dialog(ventana, "Confirmación", true);

	Label lblConfirmar = new Label("¿Estás segur@ de borrar XXXXXXXXXXXX?");

	Panel arriba = new Panel();

	Panel abajo = new Panel();

	Button btnSi = new Button("Sí");

	Button btnNo = new Button("No");

	Dialog dlgMensaje = new Dialog(ventana, "Respuesta", true);

	Label lblMensaje = new Label("Error en Baja");

	String driver = "com.mysql.cj.jdbc.Driver";
	String url = "jdbc:mysql://localhost:3306/ejerciciopracticapro";
	String login = "usuarioPractica";
	String password = "basico";
	String sentenciaSQL = "";

	Connection connection = null;

	Statement statement = null;

	ResultSet rs = null; // Para los SELECT

	public BajaPais()

	{

		ventana.setLayout(new FlowLayout());

		ventana.setSize(250, 100);

		ventana.addWindowListener(this);

		btnEliminar.addActionListener(this);

		// Rellenar el Choice

		rellenarChoice();

		ventana.add(choPaises);

		ventana.add(btnEliminar);

		ventana.setResizable(false);

		ventana.setLocationRelativeTo(null);

		dlgConfirmar.setLayout(new FlowLayout());

		dlgConfirmar.setSize(350, 120);

		dlgConfirmar.addWindowListener(this);

		dlgConfirmar.setResizable(false);

		dlgConfirmar.setLocationRelativeTo(null);

		arriba.add(lblConfirmar);

		dlgConfirmar.add(arriba);

		btnSi.addActionListener(this);

		btnNo.addActionListener(this);

		abajo.add(btnSi);

		abajo.add(btnNo);

		dlgConfirmar.add(abajo);

		dlgMensaje.setLayout(new FlowLayout());

		dlgMensaje.setSize(50, 80);

		dlgMensaje.addWindowListener(this);

		dlgMensaje.setResizable(false);

		dlgMensaje.setLocationRelativeTo(null);

		dlgMensaje.add(lblMensaje);

		ventana.setVisible(true);

	}

	private void rellenarChoice()

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
			// Asegurarme que llegue la sentencia a la tabla 
			sentenciaSQL = "SELECT * FROM paises";

			rs = statement.executeQuery(sentenciaSQL);

			// Sacar información, meter datos, borrar datos, actualizar

			choPaises.add("Seleccionar un pais...");

			while (rs.next())

			{

				choPaises.add(rs.getInt("idPais") +

						" " + rs.getString("nombrePais") +

						" " + rs.getString("continentePais") +

						" " + rs.getString("capitalPais"));

			}

		}

		catch (ClassNotFoundException cnfe)

		{

			System.err.println("Error de driver");

		}

		catch (SQLException se)

		{

			System.err.println("Error de conexión: url, usuario o clave");
			se.printStackTrace();

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


	@Override

	public void windowClosing(WindowEvent e)

	{

		if (dlgConfirmar.isActive())

		{

			dlgConfirmar.setVisible(false);

			new BajaPais();

		}

		else if (dlgMensaje.isActive())

		{

			dlgMensaje.setVisible(false);

			dlgConfirmar.setVisible(false);

			new BajaPais();

		}

		else

		{

			// Salir

			ventana.setVisible(false);

		}

	}

	@Override

	public void actionPerformed(ActionEvent evento)

	{

		if (evento.getSource().equals(btnEliminar))

		{

			if (choPaises.getSelectedIndex() != 0)

			{

				// Mostrar el diálogo de confirmación

				lblConfirmar.setText("¿Estás segur@ de borrar " + choPaises.getSelectedItem() + "?");

				dlgConfirmar.setVisible(true);

				ventana.dispose();

			}

			else

			{

				choPaises.requestFocus();

			}

		}

		else if (evento.getSource().equals(btnSi))

		{

			// Hace la baja

			// Muestra diálogo de feedback

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

				sentenciaSQL = "DELETE FROM paises WHERE idPais = " + choPaises.getSelectedItem().split(" ")[0];

				statement.executeUpdate(sentenciaSQL);

				rellenarChoice();

				// Baja correcta

				lblMensaje.setText("Baja correcta");

				dlgMensaje.setVisible(true);

				dlgConfirmar.setVisible(false);

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

		else if (evento.getSource().equals(btnNo))

		{

			// Ocultar el diálogo de confirmación

			dlgConfirmar.setVisible(false);

			new BajaPais();

		}

	}

}