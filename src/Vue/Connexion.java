package Vue;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.*;

import Controller.ConnexionController;
import Model.EtudiantExamenInfoSingleton;

public class Connexion extends JFrame
{
	Connexion instance = this;
	ConnexionController controller;
	EtudiantExamenInfoSingleton etudiant;

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
		JPanel panel = new JPanel();
		panel.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();

		
		
		JButton b_option = new JButton(); 
		Image img;
		try 
		{
			img = ImageIO.read(getClass().getResource("o.png"));
			b_option.setIcon(new ImageIcon(img));
		} catch (IOException e) {

			e.printStackTrace();
		}
		
		b_option.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) 
			{
				if(e.getSource() == b_option)
				   {
						Configuration fenetre = new Configuration();
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
			


			
		JLabel l_idExam = new JLabel("Identifiant de l'examen");
			c.fill = GridBagConstraints.HORIZONTAL;
			c.weightx = 0.3;
			c.gridx = 1;
			c.gridy = 1;
			c.gridwidth = 1;
			c.insets = new Insets(0, -30, 20, 20);
			panel.add(l_idExam, c);

		JTextField tf_idExam = new JTextField();
			c.fill = GridBagConstraints.HORIZONTAL;
			c.weightx = 0.7;
			c.gridx = 2;
			c.gridy = 1;
			c.gridwidth = 2;
			c.insets = new Insets(0, 0, 20, 20);
			panel.add(tf_idExam, c);
			

		JButton b_valider = new JButton("Ok");
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
							controller.lancementSurveillance();
							controller.logIn();
							
							Examen fenetre = new Examen();
							fenetre.setVisible(true);
							instance.dispose();
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
