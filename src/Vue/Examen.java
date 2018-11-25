package Vue;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.Timer;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import Model.RefreshTimer;
import Model.TempsSingleton;

public class Examen extends JFrame implements Observer
{
	private JPanel panel;
	private GridBagConstraints c;
	private JLabel l_enCours;
	private JLabel l_tempsRestant;
	private JLabel l_decompte;
	private static int TEMPS_REFRESH = 10*1000;	//10 secondes par défaut
	
	private TempsSingleton tps;
    Timer timer;
    RefreshTimer tache;
	
	public Examen()
	{
		super();
		
		// Initialisation de la fenêtre
		build();
		
		// Permet le refresh toutes les TEMPS_REFRESH secondes
		timer = new Timer(true);
		tache = new RefreshTimer();
		tps = TempsSingleton.getInstance();
		tps.ajouterObserver(this);	

		tache.run();	//Première acquisition du timer
        timer.scheduleAtFixedRate(tache, 0, TEMPS_REFRESH);
	}

	private void build()
	{
		setSize(420, 200);
		setLocationRelativeTo(null);
		setResizable(false);
		this.addWindowListener(new MyWindowListener());
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

		
		setContentPane(buildContentPane());
	}

	private JPanel buildContentPane()
	{
		panel = new JPanel();
		panel.setLayout(new GridBagLayout());
		c = new GridBagConstraints();

		l_enCours = new JLabel("Examen en cours");
			c.fill = GridBagConstraints.CENTER;
			c.weightx = 0.;
			c.gridx = 0;
			c.gridy = 0;
			c.gridwidth = 2;
			c.insets = new Insets(0, 0, 20, 0);
			panel.add(l_enCours, c);

		l_tempsRestant = new JLabel("Temps restant : ");
			c.fill = GridBagConstraints.CENTER;
			c.weightx = 0.;
			c.gridx = 0;
			c.gridy = 1;
			c.gridwidth = 1;
			c.insets = new Insets(0, 0, 0, 0);
			panel.add(l_tempsRestant, c);
	
		l_decompte = new JLabel("Acquisition du temps depuis le serveur...");
			c.fill = GridBagConstraints.CENTER;
			c.weightx = 0.;
			c.gridx = 1;
			c.gridy = 1;
			c.gridwidth = 1;
			panel.add(l_decompte, c);
			
		return panel;
	}

	@Override
	public void actualiser() {
		System.out.println("DEBUG: MAJ INTERFACE");
		tps = TempsSingleton.getInstance();
		l_decompte.setText(tps.getTemps());
	}
}
