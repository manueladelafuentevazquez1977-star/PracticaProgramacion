package es.studium;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.Menu;
import java.awt.MenuBar;
import java.awt.MenuItem;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import pertenece.AltaPertenece;
import pertenece.BajaPertenece;

public class MenuPrincipal implements WindowListener, ActionListener
{

	// He cambiado el system.exit(0), por ventana.setVisible(false);para que al
	// darle a la cruz roja no se me cierren todas las ventanas, solo una por una
	// En el Menú Principal es el único que no se le cambia el system.exit(0),
	// porque queremos que se nos cierre del todo.
	// También he eliminado el public static void main, parq que se ejecute uno por
	// uno, clase por clase.En el login no se quita porque queremos que se ejecute
	// directamente.
	// Por último se ha modificado el resultado==1, resultado==0, con los numeros 1
	// y 2 parq que se me conecte a mi base de datos, también se lo he añadido a la
	// clase MenuPrincipal para que se me habra desde el login la pagina principal.

	Frame ventana = new Frame("Menu");

	MenuBar menuBar = new MenuBar();
	Menu mnuPaises = new Menu("Paises");
	Menu mnuSatelites = new Menu("Satélites");
	Menu mnuAstronautas = new Menu("Astronautas");
	Menu mnuPertenece = new Menu("Pertenecer");
	Menu mnuAyuda = new Menu("Ayuda"); // botón de ayuda

	MenuItem mniPAlta = new MenuItem("Alta");
	MenuItem mniPBaja = new MenuItem("Baja");
	MenuItem mniPModificacion = new MenuItem("Modificación");
	MenuItem mniPConsultas = new MenuItem("Consultas");

	MenuItem mniSAlta = new MenuItem("Alta");
	MenuItem mniSBaja = new MenuItem("Baja");
	MenuItem mniSConsultas = new MenuItem("Consultas");
	MenuItem mniSModificacion = new MenuItem("Modificación");

	MenuItem mniAAlta = new MenuItem("Alta");
	MenuItem mniABaja = new MenuItem("Baja");
	MenuItem mniAModificacion = new MenuItem("Modificación");
	MenuItem mniAConsultas = new MenuItem("Consultas");

	MenuItem mniPeAlta = new MenuItem("Alta");
	MenuItem mniPeBaja = new MenuItem("Baja");
	MenuItem mniPeModificacion = new MenuItem("Modificación");
	MenuItem mniPeConsultas = new MenuItem("Consultas");

	MenuItem mniAyuda = new MenuItem("Tutorial"); // botón dentro de ayuda

	public MenuPrincipal(int respuesta)
	{
		// meter los action lostener de cada clase
		ventana.setLayout(new FlowLayout());
		ventana.setSize(400, 200);
		ventana.setBackground(Color.CYAN);

		// paises
		mnuPaises.add(mniPAlta);
		mniPAlta.addActionListener(this);
		mnuPaises.add(mniPBaja);
		mniPBaja.addActionListener(this);
		mnuPaises.add(mniPModificacion);
		mniPModificacion.addActionListener(this);
		mnuPaises.add(mniPConsultas);
		mniPConsultas.addActionListener(this);

		// satelites
		mnuSatelites.add(mniSAlta);
		mniSAlta.addActionListener(this);
		mnuSatelites.add(mniSBaja);
		mniSBaja.addActionListener(this);
		mnuSatelites.add(mniSConsultas);
		mniSConsultas.addActionListener(this);
		mnuSatelites.add(mniSModificacion);
		mniSModificacion.addActionListener(this);

		// astronautas
		mnuAstronautas.add(mniAAlta);
		mniAAlta.addActionListener(this);
		mnuAstronautas.add(mniABaja);
		mniABaja.addActionListener(this);
		mnuAstronautas.add(mniAModificacion);
		mniAModificacion.addActionListener(this);
		mnuAstronautas.add(mniAConsultas);
		mniAConsultas.addActionListener(this);

		// ayuda
		mnuAyuda.add(mniAyuda);
		mniAyuda.addActionListener(this);

		menuBar.add(mnuPaises);
		menuBar.add(mnuSatelites);
		menuBar.add(mnuAstronautas);
		menuBar.add(mnuPertenece);
		menuBar.add(mnuAyuda);

		ventana.addWindowListener(this);
		ventana.setMenuBar(menuBar);

		ventana.setResizable(false);
		ventana.setLocationRelativeTo(null);
		ventana.setVisible(true);
	}

	// añadir cada clase a los botones
	@Override
	public void actionPerformed(ActionEvent e)
	{
		// altas
		if (e.getSource().equals(mniPAlta))
		{
			new AltaPais();
		} else if (e.getSource().equals(mniSAlta))
		{
			new AltaSatelite();
		} else if (e.getSource().equals(mniAAlta))
		{
			new AltaAstronauta();
		} else if (e.getSource().equals(mniPeAlta))
		{
			new AltaPertenece();
		}

		// bajas
		else if (e.getSource().equals(mniPBaja))
		{
			new BajaPais();
		} else if (e.getSource().equals(mniSBaja))
		{
			new BajaSatelite();
		} else if (e.getSource().equals(mniABaja))
		{
			new BajaAstronauta();
		}

		// Modificaciones
		else if (e.getSource().equals(mniPModificacion))
		{
			new ModificacionPais();
		} else if (e.getSource().equals(mniAModificacion))
		{
			new ModificacionAstronauta();
		} else if (e.getSource().equals(mniSModificacion))
		{
			new ModificacionSatelite();
		}

		// Consultas
		else if (e.getSource().equals(mniPConsultas))
		{
			new ConsultaPais();
		} else if (e.getSource().equals(mniSConsultas))
		{
			new ConsultaSatelite();
		} else if (e.getSource().equals(mniAConsultas))
		{
			new ConsultaAstronauta();
		}

		// ayuda
		else if (e.getSource().equals(mniAyuda))
		{
			new Ayuda();
		}

	}

	@Override
	public void windowOpened(WindowEvent e)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void windowClosing(WindowEvent e)
	{
		
		Log.registrar("usuarioPractica", "Salida del Sistema");
		ventana.setVisible(false);

	}

	@Override
	public void windowClosed(WindowEvent e)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void windowIconified(WindowEvent e)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void windowDeiconified(WindowEvent e)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void windowActivated(WindowEvent e)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void windowDeactivated(WindowEvent e)
	{
		// TODO Auto-generated method stub

	}

}