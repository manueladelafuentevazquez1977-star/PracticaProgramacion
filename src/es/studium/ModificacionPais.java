package es.studium;

import java.awt.Button;
import java.awt.Choice;
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
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ModificacionPais extends WindowAdapter implements ActionListener
{
	Frame ventana = new Frame("Modificación");

	Choice choDepartamentos = new Choice(); // ← SIN CAMBIAR
	Button btnEditar = new Button("Editar");

	Dialog dlgEdicion = new Dialog(ventana, "Editando...", true);
	Label lblDepartamento = new Label("# # # Editando el País X # # #");

	Label lblNombre = new Label("Nombre:");
	TextField txtNombre = new TextField(10);

	Label lblContinente = new Label("Continente:");
	TextField txtContinente = new TextField(10);

	Label lblCapital = new Label("Capital:");
	TextField txtCapital = new TextField(10);

	Button btnAceptar = new Button("Aceptar");
	Button btnLimpiar = new Button("Limpiar");

	Dialog dlgMensaje = new Dialog(ventana, "Respuesta", true);
	Label lblMensaje = new Label("Error en Modificación");

	String driver = "com.mysql.cj.jdbc.Driver";
	String url = "jdbc:mysql://localhost:3306/ejerciciopracticapro";
	String login = "usuarioPractica";
	String password = "basico";
	String sentenciaActualizacionSQL = "";
	String sentenciaSQL = "SELECT * FROM paises";
	String idPais = "";

	Connection connection = null;
	Statement statement = null;
	ResultSet rs = null;

	public ModificacionPais()
	{
		ventana.setLayout(new FlowLayout());
		ventana.setSize(250, 100);
		ventana.addWindowListener(this);
		btnEditar.addActionListener(this);

		// Rellenar el Choice
		rellenarChoice();

		ventana.add(choDepartamentos);
		ventana.add(btnEditar);
		ventana.setResizable(false);
		ventana.setLocationRelativeTo(null);

		dlgMensaje.setLayout(new FlowLayout());
		dlgMensaje.setSize(200, 80);
		dlgMensaje.addWindowListener(this);
		dlgMensaje.setResizable(false);
		dlgMensaje.setLocationRelativeTo(null);
		dlgMensaje.add(lblMensaje);

		dlgEdicion.setLayout(new FlowLayout());
		dlgEdicion.setSize(280, 200);
		dlgEdicion.addWindowListener(this);
		btnAceptar.addActionListener(this);
		btnLimpiar.addActionListener(this);

		dlgEdicion.add(lblDepartamento);
		dlgEdicion.add(lblNombre);
		dlgEdicion.add(txtNombre);
		dlgEdicion.add(lblContinente);
		dlgEdicion.add(txtContinente);
		dlgEdicion.add(lblCapital);
		dlgEdicion.add(txtCapital);
		dlgEdicion.add(btnAceptar);
		dlgEdicion.add(btnLimpiar);

		dlgEdicion.setResizable(false);
		dlgEdicion.setLocationRelativeTo(null);

		ventana.setVisible(true);
	}

	private void rellenarChoice()
	{
		try
		{
			Class.forName(driver);
			connection = DriverManager.getConnection(url, login, password);
			System.out.println("Conexión establecida");

			statement = connection.createStatement();
			rs = statement.executeQuery(sentenciaSQL);

			choDepartamentos.add("Seleccionar un país...");

			while (rs.next())
			{
				choDepartamentos.add(
						rs.getInt("idPais") + " " + rs.getString("nombrePais") + " " + rs.getString("capitalPais"));
			}
		} catch (ClassNotFoundException cnfe)
		{
			System.err.println("Error de driver");
		} catch (SQLException se)
		{
			System.err.println("Error de conexión: url, usuario o clave");
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
	}


	@Override
	public void windowClosing(WindowEvent e)
	{
		if (dlgMensaje.isActive())
		{
			dlgMensaje.setVisible(false);
			new ModificacionPais();
		} else if (dlgEdicion.isActive())
		{
			dlgEdicion.setVisible(false);
			new ModificacionPais();
		} else
		{
			ventana.setVisible(false);
		}
	}

	@Override
	public void actionPerformed(ActionEvent evento)
	{
		if (evento.getSource().equals(btnEditar))
		{
			if (choDepartamentos.getSelectedIndex() != 0)
			{
				idPais = choDepartamentos.getSelectedItem().split(" ")[0];
				sentenciaSQL = "SELECT * FROM paises WHERE idPais = " + idPais;

				lblDepartamento.setText("# # # Editando el País " + idPais + " # # #");

				try
				{
					Class.forName(driver);
					connection = DriverManager.getConnection(url, login, password);
					System.out.println("Conexión establecida");

					statement = connection.createStatement();
					rs = statement.executeQuery(sentenciaSQL);

					rs.next();

					txtNombre.setText(rs.getString("nombrePais"));
					txtContinente.setText(rs.getString("continentePais"));
					txtCapital.setText(rs.getString("capitalPais"));
				} catch (ClassNotFoundException cnfe)
				{
					System.err.println("Error de driver");
				} catch (SQLException se)
				{
					System.err.println("Error de conexión: url, usuario o clave");
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

				dlgEdicion.setVisible(true);
				ventana.dispose();
			} else
			{
				choDepartamentos.requestFocus();
			}
		} else if (evento.getSource().equals(btnAceptar))
		{
			sentenciaSQL = "UPDATE paises SET nombrePais = '" + txtNombre.getText() + "', continentePais = '"
					+ txtContinente.getText() + "', capitalPais = '" + txtCapital.getText() + "' WHERE idPais = "
					+ idPais;

			try
			{
				Class.forName(driver);
				connection = DriverManager.getConnection(url, login, password);
				System.out.println("Conexión establecida");

				statement = connection.createStatement();
				statement.executeUpdate(sentenciaSQL);

				lblMensaje.setText("Modificación correcta");
				dlgMensaje.setVisible(true);
			} catch (ClassNotFoundException cnfe)
			{
				System.err.println("Error de driver");
			} catch (SQLException se)
			{
				System.err.println("Error de conexión: url, usuario o clave");
				lblMensaje.setText("Error en Modificación");
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
			dlgEdicion.setVisible(false);
		} else if (evento.getSource().equals(btnLimpiar))
		{
			txtNombre.setText("");
			txtContinente.setText("");
			txtCapital.setText("");
			txtNombre.requestFocus();
		}
	}
}
