package pertenece;

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

public class BajaPertenece extends WindowAdapter implements ActionListener
{
    Frame ventana = new Frame("Baja Pertenece");
    
    Choice choPertenece = new Choice();
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
    String url = "jdbc:mysql://localhost:3306/ejercicioPractica2PR";
    String login = "adminPractica";
    String password = "admin";
    String sentenciaSQL = "SELECT p.idPertenece, p.idSateliteFK, s.nombreSatelite, p.idPaisFK, pa.nombrePais "
                         + "FROM pertenece p "
                         + "JOIN satelites s ON p.idSateliteFK = s.idSatelite "
                         + "JOIN paises pa ON p.idPaisFK = pa.idPais";

    Connection connection = null;
    Statement statement = null;
    ResultSet rs = null;

    public BajaPertenece()
    {
        ventana.setLayout(new FlowLayout());
        ventana.setSize(350, 120);
        ventana.addWindowListener(this);
        btnEliminar.addActionListener(this);

        rellenarChoice();

        ventana.add(choPertenece);
        ventana.add(btnEliminar);
        ventana.setResizable(false);
        ventana.setLocationRelativeTo(null);

        dlgConfirmar.setLayout(new FlowLayout());
        dlgConfirmar.setSize(450, 120);
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
        try
        {
            Class.forName(driver);
            connection = DriverManager.getConnection(url, login, password);
            System.out.println("Conexión establecida");

            statement = connection.createStatement();
            rs = statement.executeQuery(sentenciaSQL);

            choPertenece.add("Seleccionar una pertenencia...");
            
            while (rs.next())
            {
                choPertenece.add(
                    rs.getInt("idPertenece") + " | " +
                    rs.getInt("idSateliteFK") + " " + rs.getString("nombreSatelite") + " | " +
                    rs.getInt("idPaisFK") + " " + rs.getString("nombrePais")
                );
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
        new BajaPertenece();
    }

    @Override
    public void windowClosing(WindowEvent e)
    {
        if (dlgConfirmar.isActive())
        {
            dlgConfirmar.setVisible(false);
            new BajaPertenece();
        }
        else if (dlgMensaje.isActive())
        {
            dlgMensaje.setVisible(false);
            dlgConfirmar.setVisible(false);
            new BajaPertenece();
        }
        else
        {
            System.exit(0);
        }
    }

    @Override
    public void actionPerformed(ActionEvent evento)
    {
        if (evento.getSource().equals(btnEliminar))
        {
            if (choPertenece.getSelectedIndex() != 0)
            {
                lblConfirmar.setText("¿Estás segur@ de borrar " + choPertenece.getSelectedItem() + "?");
                dlgConfirmar.setVisible(true);
                ventana.dispose();
            }
            else
            {
                choPertenece.requestFocus();
            }
        }
        else if (evento.getSource().equals(btnSi))
        {
            try
            {
                Class.forName(driver);
                connection = DriverManager.getConnection(url, login, password);
                System.out.println("Conexión establecida");

                statement = connection.createStatement();

                sentenciaSQL = "DELETE FROM pertenece WHERE idPertenece = " + 
                              choPertenece.getSelectedItem().split(" \\|")[0].trim();
                statement.executeUpdate(sentenciaSQL);

                rellenarChoice();

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
                lblMensaje.setText("Error en Baja");
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
        }
        else if (evento.getSource().equals(btnNo))
        {
            dlgConfirmar.setVisible(false);
            new BajaPertenece();
        }
    }
}
