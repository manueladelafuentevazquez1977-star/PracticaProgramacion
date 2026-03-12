package es.studium;

import java.awt.Button;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class Principal extends WindowAdapter implements ActionListener
{
	Frame ventana = new Frame("Principal");
	Button btnSalir = new Button("Salir");

	int tipo;

	public Principal(int t)
	{
		tipo = t;
		ventana.setLayout(new FlowLayout());
		btnSalir.addActionListener(this);
		ventana.addWindowListener(this);
		ventana.setSize(500, 200);
		if (tipo == 0)
		{
			ventana.add(btnSalir);
		}
		ventana.setBackground(Color.cyan);
		ventana.setLocationRelativeTo(null);
		ventana.setResizable(false);
		ventana.setVisible(true);
	}

	@Override
	public void windowClosing(WindowEvent e)
	{
		System.exit(0);
	}

	@Override
	public void actionPerformed(ActionEvent evento)
	{
		System.exit(0);
	}
}