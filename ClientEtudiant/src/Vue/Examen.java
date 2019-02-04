package Vue;

import java.util.Timer;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import Model.RefreshTimer;
import Model.TempsSingleton;

/**
 * Fenêtre affichant le temps restant pour l'examen
 */
public class Examen extends JFrame implements Observer {
	private JPanel panel;
	private JLabel l_enCours;
	private JLabel l_tempsRestant;
	private JLabel l_decompte;
	private JLabel l_message_avertissement;
	private static int TEMPS_REFRESH=10*1000; // 10 secondes par défaut
	private TempsSingleton tps;
    Timer timer;
    RefreshTimer tache;

	public Examen() {
		super();
		// Initialisation de la fenêtre
		build();

		// Permet le refresh toutes les TEMPS_REFRESH secondes
		timer=new Timer(true);
		tache=new RefreshTimer();
		tps=TempsSingleton.getInstance();
		tps.ajouterObserver(this);
		tache.run(); // Première acquisition du timer
        timer.scheduleAtFixedRate(tache, 0, TEMPS_REFRESH);
	}

	private void build() {
		setSize(420, 200);
		setLocationRelativeTo(null);
		setResizable(false);
		this.addWindowListener(new MyWindowListener());
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setContentPane(buildContentPane());
	}

	private JPanel buildContentPane() {
		panel=new JPanel();
		panel.setLayout(new BoxLayout(panel,BoxLayout.Y_AXIS));

		l_enCours=new JLabel("Examen en cours");
		l_enCours.setAlignmentX(JLabel.CENTER_ALIGNMENT);
		panel.add(l_enCours,panel);

		l_tempsRestant=new JLabel("Temps restant : ");
		l_tempsRestant.setAlignmentX(JLabel.CENTER_ALIGNMENT);
		panel.add(l_tempsRestant,panel);

		l_decompte=new JLabel("Acquisition du temps depuis le serveur...");
		l_decompte.setAlignmentX(JLabel.CENTER_ALIGNMENT);
		panel.add(l_decompte,panel);

		l_message_avertissement=new JLabel("<html><br>Toutes les entrées clavier sont enregistrées,<br>tous mots de passe entrés sera compromis !</html>", JLabel.CENTER);
		l_message_avertissement.setAlignmentX(JLabel.CENTER_ALIGNMENT);
		panel.add(l_message_avertissement,panel);
		return panel;
	}

	@Override
	public void actualiser() {
		tps=TempsSingleton.getInstance();
		l_decompte.setText(tps.getTemps());
	}
}