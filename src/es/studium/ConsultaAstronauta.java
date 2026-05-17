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

public class ConsultaAstronauta extends WindowAdapter implements ActionListener
{
    Frame ventana = new Frame("Consulta");
    TextArea txaDepartamentos = new TextArea(7, 24); // 7 filas y 24 carácteres 
    Button btnActualizar = new Button("Actualizar");
    //url
    String driver = "com.mysql.cj.jdbc.Driver";
	String url = "jdbc:mysql://localhost:3306/ejerciciopracticapro";
	String login = "usuarioPractica";
	String password = "basico";
	String sentenciaSQL = "SELECT nombreAstronauta, apellidosAstronauta, agnosExperienciaAstronauta, nombrePais " + 
            "FROM astronautas " +
            "JOIN paises ON idPaisFK = idPais;";
    
    Connection connection = null;
    Statement statement = null;
    ResultSet rs = null;

    public ConsultaAstronauta()
    {
        ventana.setLayout(new FlowLayout());
        ventana.setSize(400, 250);
        ventana.addWindowListener(this);
        btnActualizar.addActionListener(this);
        ventana.add(txaDepartamentos);
        ventana.add(btnActualizar);
        ventana.setResizable(false);
        ventana.setLocationRelativeTo(null);
        ventana.setVisible(true);
    }

    
    @Override
    public void windowClosing(WindowEvent e)
    {
    	ventana.setVisible(false);
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

            txaDepartamentos.setText("");

            while (rs.next())
            {
                txaDepartamentos.append(
                    rs.getInt("idAstronauta") + " " +
                    rs.getString("nombreAstronauta") + " " + 
                    rs.getString("apellidosAstronauta") + " - Exp: " +
                    rs.getString("agnosExperienciaAstronauta") + " - País: " + 
                    rs.getString("nombrePais") + " " 
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
