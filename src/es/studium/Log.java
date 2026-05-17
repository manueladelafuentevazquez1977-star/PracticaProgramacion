package es.studium;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
// Hemos añadido estos dos imports nuevos para las fechas europeas
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Log 
{
	public static void registrar(String usuario, String movimiento) 
	{
		
		try (BufferedWriter bw = new BufferedWriter(new FileWriter("movimientos.log", true)))
		{
			// día-mes-año
			DateTimeFormatter formateador = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
			
			String fechaEuropea = LocalDateTime.now().format(formateador);

			String lineaLog = "[" + fechaEuropea + "][" + usuario + "][" + movimiento + "]";

			bw.write(lineaLog);
			bw.newLine(); 
		} 
		catch (IOException e) 
		{
			System.err.println("Error al escribir en el fichero de log: " + e.getMessage());
		}
	}
}