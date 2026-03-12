package pertenece;

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

public class ModificacionPertenece extends WindowAdapter implements ActionListener
{
    Frame ventana = new Frame("Modificación");

    Choice choDepartamentos = new Choice();
    Button btnEditar = new Button("Editar");

    Dialog dlgEdicion = new Dialog(ventana, "Editando...", true);
    Label lblDepartamento = new Label("# # # Editando la Pertenencia X # # #");

    Label lblSatelite = new Label("ID Satélite:");
    TextField txtSatelite = new TextField(10);

    Label lblPais = new Label("ID País:");
    TextField txtPais = new TextField(10);

    Button btnAceptar = new Button("Aceptar");
    Button btnLimpiar = new Button("Limpiar");

    Dialog dlgMensaje = new Dialog(ventana, "Respuesta", true);
    Label lblMensaje = new Label("Error en Modificación");

    String driver = "com.mysql.cj.jdbc.Driver";
    String url = "jdbc:mysql://localhost:3306/ejercicioPractica2PR";
    String login = "adminPractica";
    String password = "admin";
    
    String sentenciaSQL = "SELECT * FROM pertenece";
    String idPertenece = "";

    Connection connection = null;
    Statement statement = null;
    ResultSet rs = null;

    public ModificacionPertenece()
    {
        ventana.setLayout(new FlowLayout());
        ventana.setSize(250, 100);
        ventana.addWindowListener(this);
        btnEditar.addActionListener(this);

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
        dlgEdicion.setSize(280, 160);
        dlgEdicion.addWindowListener(this);
        btnAceptar.addActionListener(this);
        btnLimpiar.addActionListener(this);

        dlgEdicion.add(lblDepartamento);
        dlgEdicion.add(lblSatelite);
        dlgEdicion.add(txtSatelite);
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

            choDepartamentos.add("Seleccionar una pertenencia...");

            while (rs.next())
            {
                choDepartamentos.add(rs.getInt("idPertenece") +
                                   " " + rs.getInt("idSateliteFK") +
                                   " " + rs.getInt("idPaisFK"));
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

    public static void main(String[] args)
    {
        new ModificacionPertenece();
    }

    @Override
    public void windowClosing(WindowEvent e)
    {
        if (dlgMensaje.isActive())
        {
            dlgMensaje.setVisible(false);
            new ModificacionPertenece();
        }
        else if (dlgEdicion.isActive())
        {
            dlgEdicion.setVisible(false);
            new ModificacionPertenece();
        }
        else
        {
            System.exit(0);
        }
    }

    @Override
    public void actionPerformed(ActionEvent evento)
    {
        if (evento.getSource().equals(btnEditar))
        {
            if (choDepartamentos.getSelectedIndex() != 0)
            {
                idPertenece = choDepartamentos.getSelectedItem().split(" ")[0];
                sentenciaSQL = "SELECT * FROM pertenece WHERE idPertenece = " + idPertenece;

                lblDepartamento.setText("# # # Editando la Pertenencia " + idPertenece + " # # #");

                try
                {
                    Class.forName(driver);
                    connection = DriverManager.getConnection(url, login, password);
                    System.out.println("Conexión establecida");

                    statement = connection.createStatement();
                    rs = statement.executeQuery(sentenciaSQL);

                    rs.next();

                    txtSatelite.setText(rs.getString("idSateliteFK"));
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
                choDepartamentos.requestFocus();
            }
        }
        else if (evento.getSource().equals(btnAceptar))
        {
            sentenciaSQL = "UPDATE pertenece SET idSateliteFK = " + txtSatelite.getText()
                         + ", idPaisFK = " + txtPais.getText()
                         + " WHERE idPertenece = " + idPertenece;

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
            txtSatelite.setText("");
            txtPais.setText("");
            txtSatelite.requestFocus();
        }
    }
}
