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

public class ModificacionAstronauta extends WindowAdapter implements ActionListener
{
    Frame ventana = new Frame("Modificación");

    Choice choAstronauta = new Choice();
    Button btnEditar = new Button("Editar");

    Dialog dlgEdicion = new Dialog(ventana, "Editando...", true);
    Label lblAstronauta = new Label("# # # Editando el Astronauta X # # #");

    Label lblNombre = new Label("Nombre:");
    TextField txtNombre = new TextField(10);

    Label lblApellidos = new Label("Apellidos:");
    TextField txtApellidos = new TextField(10);

    Label lblFechaNac = new Label("Fecha Nacimiento:");
    TextField txtFechaNac = new TextField(10);

    Label lblExperiencia = new Label("Años Experiencia:");
    TextField txtExperiencia = new TextField(10);

    Label lblPais = new Label("ID País:");
    TextField txtPais = new TextField(10);

    Button btnAceptar = new Button("Aceptar");
    Button btnLimpiar = new Button("Limpiar");

    Dialog dlgMensaje = new Dialog(ventana, "Respuesta", true);
    Label lblMensaje = new Label("Error en Modificación");

    String driver = "com.mysql.cj.jdbc.Driver";
   	String url = "jdbc:mysql://localhost:3306/ejerciciopracticapro";
   	String login = "usuarioPractica";
   	String password = "basico";
    String sentenciaActualizacionSQL = "";  //UPDATE
    String sentenciaSQL = "SELECT * FROM astronautas";
    String idAstronauta = "";

    Connection connection = null;
    Statement statement = null;
    ResultSet rs = null;

    public ModificacionAstronauta()
    {
        ventana.setLayout(new FlowLayout());
        ventana.setSize(250, 100);
        ventana.addWindowListener(this);
        btnEditar.addActionListener(this);

        rellenarChoice();

        ventana.add(choAstronauta);
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
        dlgEdicion.setSize(300, 280);  // Más grande por 5 campos
        dlgEdicion.addWindowListener(this);
        btnAceptar.addActionListener(this);
        btnLimpiar.addActionListener(this);

        dlgEdicion.add(lblAstronauta);
        dlgEdicion.add(lblNombre);
        dlgEdicion.add(txtNombre);
        dlgEdicion.add(lblApellidos);
        dlgEdicion.add(txtApellidos);
        dlgEdicion.add(lblFechaNac);
        dlgEdicion.add(txtFechaNac);
        dlgEdicion.add(lblExperiencia);
        dlgEdicion.add(txtExperiencia);
        dlgEdicion.add(lblPais);
        dlgEdicion.add(txtPais);
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

            choAstronauta.add("Seleccionar un astronauta...");

            while (rs.next())
            {
                choAstronauta.add(rs.getInt("idAstronauta") +
                                   " " + rs.getString("nombreAstronauta") +
                                   " " + rs.getString("apellidosAstronauta"));
            }
        }
        catch (ClassNotFoundException cnfe)
        {
            System.err.println("Error de driver");
        }
        catch (SQLException se)
        {
            System.err.println("Error de conexión: url, usuario o clave");
        }
        finally
        {
            try
            {
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
        if (dlgMensaje.isActive())
        {
            dlgMensaje.setVisible(false);
            new ModificacionAstronauta();
        }
        else if (dlgEdicion.isActive())
        {
            dlgEdicion.setVisible(false);
            new ModificacionAstronauta();
        }
        else
        {
        	ventana.setVisible(false);
        }
    }

    @Override
    public void actionPerformed(ActionEvent evento)
    {
        if (evento.getSource().equals(btnEditar))
        {
            if (choAstronauta.getSelectedIndex() != 0)
            {
                idAstronauta = choAstronauta.getSelectedItem().split(" ")[0];
                sentenciaSQL = "SELECT * FROM astronautas WHERE idAstronauta = " + idAstronauta;

                lblAstronauta.setText("# # # Editando el Astronauta " + idAstronauta + " # # #");

                try
                {
                    Class.forName(driver);
                    connection = DriverManager.getConnection(url, login, password);
                    System.out.println("Conexión establecida");

                    statement = connection.createStatement();
                    rs = statement.executeQuery(sentenciaSQL);

                    rs.next();

                    txtNombre.setText(rs.getString("nombreAstronauta"));
                    txtApellidos.setText(rs.getString("apellidosAstronauta"));
                    txtFechaNac.setText(rs.getString("fechaNacimientoAstronauta"));
                    txtExperiencia.setText(rs.getString("agnosExperienciaAstronauta"));
                    txtPais.setText(rs.getString("idPaisFK"));
                }
                catch (ClassNotFoundException cnfe)
                {
                    System.err.println("Error de driver");
                }
                catch (SQLException se)
                {
                    System.err.println("Error de conexión: url, usuario o clave");
                }
                finally
                {
                    try
                    {
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

                dlgEdicion.setVisible(true);
                ventana.dispose();
            }
            else
            {
                choAstronauta.requestFocus();
            }
        }
        else if (evento.getSource().equals(btnAceptar))
        {
            sentenciaSQL = "UPDATE astronautas SET nombreAstronauta = '" + txtNombre.getText()
                         + "', apellidosAstronauta = '" + txtApellidos.getText()
                         + "', fechaNacimientoAstronauta = '" + txtFechaNac.getText()
                         + "', agnosExperienciaAstronauta = '" + txtExperiencia.getText()
                         + "', idPaisFK = " + txtPais.getText()
                         + " WHERE idAstronauta = " + idAstronauta;

            try
            {
                Class.forName(driver);
                connection = DriverManager.getConnection(url, login, password);
                System.out.println("Conexión establecida");

                statement = connection.createStatement();
                statement.executeUpdate(sentenciaSQL);

                lblMensaje.setText("Modificación correcta");
                dlgMensaje.setVisible(true);
            }
            catch (ClassNotFoundException cnfe)
            {
                System.err.println("Error de driver");
            }
            catch (SQLException se)
            {
                System.err.println("Error de conexión: url, usuario o clave");
                lblMensaje.setText("Error en Modificación");
                dlgMensaje.setVisible(true);
            }
            finally
            {
                try
                {
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
            dlgEdicion.setVisible(false);
        }
        else if (evento.getSource().equals(btnLimpiar))
        {
            txtNombre.setText("");
            txtApellidos.setText("");
            txtFechaNac.setText("");
            txtExperiencia.setText("");
            txtPais.setText("");
            txtNombre.requestFocus();
        }
    }
}
