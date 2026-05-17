package es.studium;

import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class ModificacionSatelite extends WindowAdapter implements ActionListener
{
	Frame ventana = new Frame("Modificación Satélite");
	Choice choSatelites = new Choice();
	Button btnEditar = new Button("Editar");

	// Diálogo de Edición
	Dialog dlgEdicion = new Dialog(ventana, "Editando Satélite...", true);
	Label lblSatelite = new Label("# # # Editando Satélite # # #");

	Label lblNombre = new Label("Nombre:");
	TextField txtNombre = new TextField(15);

	Label lblTipo = new Label("Tipo:");
	TextField txtTipo = new TextField(15);

	Label lblFecha = new Label("Fecha Lanzamiento (YYYY-MM-DD):");
	TextField txtFecha = new TextField(15);

	// se crea el choice para lo que es el fk
	Label lblPais = new Label("País Propietario:");
	Choice choPaises = new Choice();

	Button btnAceptar = new Button("Aceptar");
	Button btnLimpiar = new Button("Limpiar");

	
	Dialog dlgMensaje = new Dialog(ventana, "Respuesta", true);
	Label lblMensaje = new Label("");

	// esto la coneñión con la base de dats
	String driver = "com.mysql.cj.jdbc.Driver";
	String url = "jdbc:mysql://localhost:3306/ejerciciopracticapro";
	String login = "usuarioPractica";
	String password = "basico";

	String idSateliteSeleccionado = "";

	public ModificacionSatelite()
	{
		ventana.setLayout(new FlowLayout());
		ventana.setSize(300, 120);
		ventana.addWindowListener(this);
		btnEditar.addActionListener(this);

		rellenarChoiceSatelites();

		ventana.add(choSatelites);
		ventana.add(btnEditar);
		ventana.setLocationRelativeTo(null);

		
		dlgEdicion.setLayout(new FlowLayout());
		dlgEdicion.setSize(250, 350);
		dlgEdicion.addWindowListener(this);

		dlgEdicion.add(lblSatelite);
		dlgEdicion.add(lblNombre);
		dlgEdicion.add(txtNombre);
		dlgEdicion.add(lblTipo);
		dlgEdicion.add(txtTipo);
		dlgEdicion.add(lblFecha);
		dlgEdicion.add(txtFecha);
		dlgEdicion.add(lblPais);
		dlgEdicion.add(choPaises); // para añadir el despegable de paises

		btnAceptar.addActionListener(this);
		btnLimpiar.addActionListener(this);
		dlgEdicion.add(btnAceptar);
		dlgEdicion.add(btnLimpiar);
		dlgEdicion.setLocationRelativeTo(null);

		
		dlgMensaje.setLayout(new FlowLayout());
		dlgMensaje.setSize(200, 100);
		dlgMensaje.addWindowListener(this);
		dlgMensaje.add(lblMensaje);
		dlgMensaje.setLocationRelativeTo(null);

		ventana.setVisible(true);
	}

	private void rellenarChoiceSatelites()
	{
		try
		{
			Class.forName(driver);
			Connection con = DriverManager.getConnection(url, login, password);
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery("SELECT * FROM satelites");
			choSatelites.removeAll();
			choSatelites.add("Selecciona satélite...");
			while (rs.next())
			{
				choSatelites.add(rs.getInt("idSatelite") + " - " + rs.getString("nombreSatelite"));
			}
			con.close();
		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	private void rellenarChoicePaises()
	{
		try
		{
			Class.forName(driver);
			Connection con = DriverManager.getConnection(url, login, password);
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery("SELECT idPais, nombrePais FROM paises");
			choPaises.removeAll();
			while (rs.next())
			{
				choPaises.add(rs.getInt("idPais") + " - " + rs.getString("nombrePais"));
			}
			con.close();
		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	@Override
	public void actionPerformed(ActionEvent e)
	{
		if (e.getSource() == btnEditar)
		{
			if (choSatelites.getSelectedIndex() != 0)
			{
				idSateliteSeleccionado = choSatelites.getSelectedItem().split(" - ")[0];
				rellenarChoicePaises(); // aqui salen los paises que estan disponibles, los que estan dentro del alta ya 
				cargarDatosSatelite(idSateliteSeleccionado);
				dlgEdicion.setVisible(true);
			}
		} else if (e.getSource() == btnLimpiar)
		{
			txtNombre.setText("");
			txtTipo.setText("");
			txtFecha.setText("");
		} else if (e.getSource() == btnAceptar)
		{
			modificarSatelite();
		}
	}

	private void cargarDatosSatelite(String id)
	{
		try
		{
			// ESTO HA SIDO CON AYUDA!!
			Connection con = DriverManager.getConnection(url, login, password);
			Statement st = con.createStatement();
			// para tener los datos del satelite y si tiene un pais también
			String sql = "SELECT s.*, p.idPaisFK FROM satelites s "
					+ "LEFT JOIN pertenece p ON s.idSatelite = p.idSateliteFK " + "WHERE s.idSatelite = " + id;
			ResultSet rs = st.executeQuery(sql);
			if (rs.next())
			{
				txtNombre.setText(rs.getString("nombreSatelite"));
				txtTipo.setText(rs.getString("tipoSatelite"));
				txtFecha.setText(rs.getString("fechaLanzamientoSatelite"));

				// si tiene un pais en el choice si lo tiene 
				String idPaisActual = rs.getString("idPaisFK");
				for (int i = 0; i < choPaises.getItemCount(); i++)
				{
					if (choPaises.getItem(i).startsWith(idPaisActual + " -"))
					{
						choPaises.select(i);
					}
				}
			}
			con.close();
		} catch (SQLException ex)
		{
			ex.printStackTrace();
		}
	}

	private void modificarSatelite()
	{
		try
		{
			Connection con = DriverManager.getConnection(url, login, password);
			Statement st = con.createStatement();

			// si se meten nuevos datos se actualiza con lo nuevo
			String sqlSatelite = "UPDATE satelites SET nombreSatelite='" + txtNombre.getText() + "', tipoSatelite='"
					+ txtTipo.getText() + "', fechaLanzamientoSatelite='" + txtFecha.getText() + "' WHERE idSatelite="
					+ idSateliteSeleccionado;
			st.executeUpdate(sqlSatelite);

			// esta la de pertenece
			String idPaisNuevo = choPaises.getSelectedItem().split(" - ")[0];
			//se borra la de antes y se pone la nueva
			st.executeUpdate("DELETE FROM pertenece WHERE idSateliteFK = " + idSateliteSeleccionado);
			st.executeUpdate("INSERT INTO pertenece (idSateliteFK, idPaisFK) VALUES (" + idSateliteSeleccionado + ", "
					+ idPaisNuevo + ")");

			lblMensaje.setText("Modificado con éxito");
			dlgMensaje.setVisible(true);
			dlgEdicion.setVisible(false);
			con.close();
		} catch (SQLException ex)
		{
			lblMensaje.setText("Error en la base de datos");
			dlgMensaje.setVisible(true);
		}
	}

	@Override
	public void windowClosing(WindowEvent e)
	{
		if (dlgMensaje.isActive())
			dlgMensaje.setVisible(false);
		else if (dlgEdicion.isActive())
			dlgEdicion.setVisible(false);
		else
			ventana.dispose();
	}
}