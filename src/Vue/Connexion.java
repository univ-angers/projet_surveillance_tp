package Vue;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

import Controller.ConnexionController;
import Model.EtudiantExamenInfoSingleton;

public class Connexion extends JFrame
{
	public static final ImageIcon image = new ImageIcon("Ressources/o.png");

	private Connexion instance = this;
	private ConnexionController controller;
	private EtudiantExamenInfoSingleton etudiant;

	private JPanel panel;
	private GridBagConstraints c;
	private JLabel l_idExam;
	private JButton b_option;
	private JButton b_valider;
	private JTextField tf_idExam;


	public Connexion()
	{
		super();
		controller = new ConnexionController(this);

		// Initialisation de la fenêtre
		build();
	}

	private void build()
	{
		setSize(420, 130);
		setLocationRelativeTo(null);
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		setContentPane(buildContentPane());
	}

	private JPanel buildContentPane()
	{
		panel = new JPanel();
		panel.setLayout(new GridBagLayout());
		c = new GridBagConstraints();



		b_option = new JButton(image); 
		b_option.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) 
			{
				if(e.getSource() == b_option)
				{
					Configuration fenetre = new Configuration();
					fenetre.setModal(true);
					fenetre.setVisible(true);
				}
			}
		});

		c.fill = GridBagConstraints.NONE;
		c.weightx = 0;
		c.gridx = 0;
		c.gridy = 0;
		c.gridwidth = 1;
		c.insets = new Insets(10, 10, 20, 10);
		panel.add(b_option, c);




		l_idExam = new JLabel("Identifiant de l'examen");
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 0.3;
		c.gridx = 1;
		c.gridy = 1;
		c.gridwidth = 1;
		c.insets = new Insets(0, -30, 20, 20);
		panel.add(l_idExam, c);

		tf_idExam = new JTextField();
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 0.7;
		c.gridx = 2;
		c.gridy = 1;
		c.gridwidth = 2;
		c.insets = new Insets(0, 0, 20, 20);
		panel.add(tf_idExam, c);


		b_valider = new JButton("Ok");
		b_valider.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) 
			{
				if(e.getSource() == b_valider)
				{
					String idExam = tf_idExam.getText();
					etudiant = EtudiantExamenInfoSingleton.getInstanceExistante();
					try 
					{
						double id = Double.parseDouble(idExam);

						if (etudiant != null && !idExam.isEmpty() && idExam.length() < 11)
						{
							controller.receptionIdExamen(idExam);
							boolean reussi = controller.logIn();

							if (reussi)
							{
								controller.lancementSurveillance();
								Examen fenetre = new Examen();
								fenetre.setVisible(true);
								instance.dispose();
							}
							else
								JOptionPane.showMessageDialog(instance, "Identifiants non connus. Veuillez réessayer.");
						}
						else 
							JOptionPane.showMessageDialog(instance, "Veuillez renseigner tous les champs correctement.\n\nN'oubliez pas de cliquer sur les petits rouages pour remplir\nvos informations personnelles.\n\nL'identifiant de l'examen doit être un nombre allant de\n0 à 9.999.999.999.");
					}
					catch (NumberFormatException f) 
					{
						JOptionPane.showMessageDialog(instance, "Veuillez renseigner tous les champs correctement.\n\nN'oubliez pas de cliquer sur les petits rouages pour remplir\nvos informations personnelles.\n\nL'identifiant de l'examen doit être un nombre allant de\n0 à 9.999.999.999.");
					}
				}
			}
		});

		c.fill = GridBagConstraints.CENTER;
		c.weightx = 0.2;
		c.gridx = 3;
		c.gridy = 4;
		c.gridwidth = 1;
		c.insets = new Insets(0, 100, 15, 35);
		panel.add(b_valider, c);


		return panel;
	}
}
