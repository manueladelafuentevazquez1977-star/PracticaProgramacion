package es.studium;

import java.awt.Button;
import java.awt.Dialog;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.Label;
import java.awt.TextArea;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class Ayuda extends WindowAdapter implements ActionListener
{
	
	Frame ventana = new Frame("Ayuda");


	Button btnUso = new Button("¿Para qué se usa la Aplicación?");
	Button btnTutorial = new Button("Tutorial Básico ");
	Button btnPassword = new Button("Restablecer Contraseña");
	Button btnVolver = new Button("Volver al Menú");

	
	Dialog dlgDetalle = new Dialog(ventana, "Información", true);
	TextArea txaDetalle = new TextArea(30, 45);
	Button btnCerrarDialogo = new Button("Cerrar");

	public Ayuda()
	{
		
		ventana.setLayout(new FlowLayout());
		ventana.setSize(250, 200);
		ventana.addWindowListener(this);

		btnUso.addActionListener(this);
		btnTutorial.addActionListener(this);
		btnPassword.addActionListener(this);
		btnVolver.addActionListener(this);

		ventana.add(btnUso);
		ventana.add(btnTutorial);
		ventana.add(btnPassword);
		ventana.add(btnVolver);

		ventana.setResizable(false);
		ventana.setLocationRelativeTo(null);

		
		dlgDetalle.setLayout(new FlowLayout());
		dlgDetalle.setSize(500, 250);
		dlgDetalle.addWindowListener(this);
		btnCerrarDialogo.addActionListener(this);

		txaDetalle.setEditable(false); 
		dlgDetalle.add(txaDetalle);
		dlgDetalle.add(btnCerrarDialogo);
		dlgDetalle.setResizable(false);
		dlgDetalle.setLocationRelativeTo(null);

		ventana.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e)
	{
		
		if (e.getSource().equals(btnUso))
		{
			dlgDetalle.setTitle("¿Para qué se usa la Aplicación?");
			txaDetalle.setText(""
					+ "Este software está diseñado para la gestión y control integral\n" + "de misiones espaciales.\n\n"
					+ "Permite registrar y modificar datos críticos de astronautas,\n"
					+ "administrar satélites en órbita y vincularlos de forma síncrona\n"
					+ "con sus respectivos países propietarios.");
			dlgDetalle.setVisible(true);
		}
		
		else if (e.getSource().equals(btnTutorial))
		{
			dlgDetalle.setTitle("Tutorial Básico ");
			txaDetalle.setText("Tutorial Básico:"
					+ "PASO 1: Navega a través de las opciones del Menú Principal\n"
					+ "        para acceder a las operaciones CRUD.\n"
					+ "PASO 2: En la sección 'Consulta', pulsa el botón 'Actualizar\n'"
					+ "        para cargar de la BD los datos legibles (JOIN).\n"
					+ "PASO 3: Al editar registros, introduce datos válidos antes\n"
					+ "        de pulsar 'Aceptar' para evitar fallos de integridad.\n");
			dlgDetalle.setVisible(true);
		}
		
		else if (e.getSource().equals(btnPassword))
		{
			dlgDetalle.setTitle("Restablecer Contraseña");
			txaDetalle.setText(
					"PROCEDIMIENTO DE RECUPERACIÓN DE CLAVE:\n\n" + "1. Sal de la sesión actual y regresa al Login.\n"
							+ "2. Solicita al administrador del sistema ('adminPractica')\n"
							+ "   un reinicio de credenciales en la base de datos.\n"
							+ "3. El administrador asignará la clave por defecto 'basico'.\n"
							+ "4. Inicia sesión y modifícala inmediatamente por seguridad.");
			dlgDetalle.setVisible(true);
		}
		
		else if (e.getSource().equals(btnCerrarDialogo))
		{
			dlgDetalle.setVisible(false);
		}
	
		else if (e.getSource().equals(btnVolver))
		{
			ventana.setVisible(false);
		}
	}

	@Override
	public void windowClosing(WindowEvent e)
	{
		if (dlgDetalle.isActive())
		{
			dlgDetalle.setVisible(false);
		} else
		{
			ventana.setVisible(false);
		}
	}
}