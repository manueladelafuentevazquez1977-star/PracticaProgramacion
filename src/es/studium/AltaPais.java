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

public class AltaPais extends WindowAdapter implements ActionListener

{
 // TextField y Label para las etiquetas y escribir los nombres
	Frame ventana = new Frame("Alta");

	Label lblNombre = new Label("Nombre:");

	TextField txtNombre = new TextField(10);

	Label lblContinente = new Label("Continente:");

	TextField txtContinente = new TextField(10);

	Label lblCapital = new Label("Capital:");

	TextField txtCapital = new TextField(10);
	
//Para añadir los botones 
	Button btnAceptar = new Button("Aceptar");

	Button btnLimpiar = new Button("Limpiar");

	//Con esto conectamos la base de datos.
	String driver = "com.mysql.cj.jdbc.Driver";
	String url = "jdbc:mysql://localhost:3306/ejerciciopracticapro";
	String login = "usuarioPractica";
	String password = "basico";

	// para escribir algo luego
	String sentenciaSQL = "";

	Connection connection = null;

	Statement statement = null; // para procesar la cadena 

	Dialog dlgMensaje = new Dialog(ventana, "Respuesta", true); // ventana emergente

	Label lblMensaje = new Label("Error en Alta");

	public AltaPais()

	{
		// Ventanas y Botones 
		ventana.setLayout(new FlowLayout());

		ventana.setSize(250, 180);

		ventana.addWindowListener(this);

		btnAceptar.addActionListener(this);

		btnLimpiar.addActionListener(this);

		ventana.add(lblNombre);

		ventana.add(txtNombre);

		ventana.add(lblContinente);

		ventana.add(txtContinente);

		ventana.add(lblCapital);

		ventana.add(txtCapital);

		ventana.add(btnAceptar);

		ventana.add(btnLimpiar);

		ventana.setResizable(false);

		ventana.setLocationRelativeTo(null);

		// para configurar la ventana emergente
		dlgMensaje.setLayout(new FlowLayout());

		dlgMensaje.setSize(50, 80);

		dlgMensaje.addWindowListener(this);

		dlgMensaje.setResizable(false);

		dlgMensaje.setLocationRelativeTo(null);

		dlgMensaje.add(lblMensaje);

		ventana.setVisible(true);

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

			ventana.setVisible(false);

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

				sentenciaSQL = "INSERT INTO paises VALUES(null, '" + txtNombre.getText() + "','"
						+ txtContinente.getText() + "','" + txtCapital.getText() + "')";

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

			txtContinente.setText("");

			txtCapital.setText("");

			txtNombre.requestFocus();

		}

	}

}
