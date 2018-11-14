package Vue;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Examen extends JFrame
{
	private JPanel panel;
	private GridBagConstraints c;
	private JLabel l_enCours;
	private JLabel l_tempsRestant;
	private JLabel l_decompte;
	
	public Examen()
	{
		super();
		
		// Initialisation de la fenêtre
		build();
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
	
		l_decompte = new JLabel("En attendant un vrai décompte...");
			c.fill = GridBagConstraints.CENTER;
			c.weightx = 0.;
			c.gridx = 1;
			c.gridy = 1;
			c.gridwidth = 1;
			panel.add(l_decompte, c);
			
		return panel;
	}
}
