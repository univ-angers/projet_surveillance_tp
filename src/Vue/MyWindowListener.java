package Vue;

import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

class MyWindowListener implements WindowListener 
{  
	public void windowClosing(WindowEvent arg0) 
	{
		System.out.println("Fermé 1");
		Quitter quit = new Quitter();
		quit.setModal(true);
		quit.show();
	}
	
	public void windowOpened(WindowEvent arg0) 
	{
		System.out.println("Ouvert");
	}
	
	public void windowClosed(WindowEvent arg0) 
	{
		System.out.println("Fermé 2");
	}
	
	public void windowIconified(WindowEvent arg0) 
	{}
	
	public void windowDeiconified(WindowEvent arg0) 
	{}
	
	public void windowActivated(WindowEvent arg0) 
	{}
	
	public void windowDeactivated(WindowEvent arg0) 
	{}
}