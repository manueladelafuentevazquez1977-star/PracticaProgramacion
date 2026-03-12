package es.studium;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class GestionBaseDatos
{
	String driver = "com.mysql.cj.jdbc.Driver";
	String url = "jdbc:mysql://localhost:3306/ejercicioPracticapro";
	String login = "usuarioPractica";
	String password = "basico";
	String sentenciaSQL = "";

	Connection connection = null;
	Statement statement = null;
	ResultSet rs = null;

	public Connection conectar()
	{

		try
		{
			Class.forName(driver);
			connection = DriverManager.getConnection(url, login, password);
			System.out.println("Conexión establecida");
			return connection;
		} catch (ClassNotFoundException cnfe)
		{
			System.err.println("Error de driver");
			return null;
		} catch (SQLException se)
		{
			System.err.println("Error de conexión: url, usuario o clave");
			return null;
		}
	}

	public boolean desconectar(Connection conexion)
	{
		try
		{

			if (conexion != null)
			{
				conexion.close();
			}
			return true;
		} catch (SQLException e)
		{
			System.err.println("Error al cerrar conexión");
			return false;
		}
	}

	public int comprobarCredenciales(String nombreUsuario, String claveUsuario)
	{
		sentenciaSQL = "SELECT * FROM usuarios WHERE nombreUsuario = ? AND claveUsuario = SHA2(?, 256)";
		System.out.println(nombreUsuario);
		try
		{
			PreparedStatement ps = connection.prepareStatement(sentenciaSQL);
			ps.setString(1, nombreUsuario);
			ps.setString(2, claveUsuario);
			rs = ps.executeQuery();
			if (rs.next())
			{
				return (rs.getInt("tipoUsuario"));
			} else
			{
				return -1; 
			}
		} catch (SQLException e)
		{
			e.printStackTrace();
			return -1;
		}
	}
}