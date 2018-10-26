import javax.swing.SwingUtilities;

public class TestFenetre {

	public static void main(String[] args)
	{
		SwingUtilities.invokeLater(new Runnable()
		{
			public void run()
			{
				Connexion fenetre = new Connexion();
				fenetre.setVisible(true);
			}
		});
	}
}

