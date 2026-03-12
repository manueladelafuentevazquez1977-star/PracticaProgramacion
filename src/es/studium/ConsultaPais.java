package es.studium;

import java.awt.Button;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.TextArea;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ConsultaPais extends WindowAdapter implements ActionListener
{
    Frame ventana = new Frame("Consulta");
    TextArea txaDepartamentos = new TextArea(7, 24);
    Button btnActualizar = new Button("Actualizar");
    
    String driver = "com.mysql.cj.jdbc.Driver";
	String url = "jdbc:mysql://localhost:3306/ejerciciopracticapro";
	String login = "usuarioPractica";
	String password = "basico";
    String sentenciaSQL = "SELECT * FROM paises";
    
    Connection connection = null;
    Statement statement = null;
    ResultSet rs = null;

    public ConsultaPais()
    {
        ventana.setLayout(new FlowLayout());
        ventana.setSize(300, 200);
        ventana.addWindowListener(this);
        btnActualizar.addActionListener(this);
        ventana.add(txaDepartamentos);
        ventana.add(btnActualizar);
        ventana.setResizable(false);
        ventana.setLocationRelativeTo(null);
        ventana.setVisible(true);
    }

    public static void main(String[] args)
    {
        new ConsultaPais();
    }

    @Override
    public void windowClosing(WindowEvent e)
    {
        System.exit(0);
    }

    @Override
    public void actionPerformed(ActionEvent evento)
    {
        try
        {
            Class.forName(driver);
            connection = DriverManager.getConnection(url, login, password);
            System.out.println("Conexión establecida");

            statement = connection.createStatement();
            rs = statement.executeQuery(sentenciaSQL);

            txaDepartamentos.setText(""); // Limpiar antes

            while (rs.next())
            {
                txaDepartamentos.append(rs.getInt("idPais") +
                                      " " + rs.getString("nombrePais") +
                                      " " + rs.getString("continentePais") +
                                      " " + rs.getString("capitalPais") + "\n");
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
}
