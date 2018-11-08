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

import Controller.ConfigController;
import Model.EtudiantExamenInfoSingleton;

public class Configuration extends JFrame
{
	Configuration instance = this;
	ConfigController controller;
	EtudiantExamenInfoSingleton etudiant;
	
	public Configuration()
	{
		super();
		controller = new ConfigController(this);
		
		etudiant = EtudiantExamenInfoSingleton.getInstanceExistante();
	
		// Initialisation de la fenêtre
		build();
	}
	
	private void build()
	{
		setSize(420, 250);
		setLocationRelativeTo(null);
		setResizable(false);
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		
		setContentPane(buildContentPane());
	}
	
	private JPanel buildContentPane()
	{
		JPanel panel = new JPanel();
		panel.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
				
		
		JButton b_option = new JButton();
		
		// On met l'image dans le bouton
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
					   instance.dispose();
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
	

		JLabel l_adrServ = new JLabel("Adresse serveur");
			c.fill = GridBagConstraints.HORIZONTAL;
			c.weightx = 0.3;
			c.gridx = 1;
			c.gridy = 1;
			c.gridwidth = 1;
			c.insets = new Insets(0, -30, 20, 20);
			panel.add(l_adrServ, c);
			
		JTextField tf_adrServ = new JTextField();
			c.fill = GridBagConstraints.HORIZONTAL;
			c.weightx = 0.7;
			c.gridx = 2;
			c.gridy = 1;
			c.gridwidth = 2;
			c.insets = new Insets(0, 0, 20, 20);
			panel.add(tf_adrServ, c);
			if (etudiant != null)
				tf_adrServ.setText(etudiant.getAdresseServeur());

		
		JLabel l_nomEtud = new JLabel("Nom étudiant");
			c.fill = GridBagConstraints.HORIZONTAL;
			c.weightx = 0.3;
			c.gridx = 1;
			c.gridy = 2;
			c.gridwidth = 1;
			c.insets = new Insets(0, -30, 20, 20);
			panel.add(l_nomEtud, c);
			
			
		JTextField tf_nomEtud = new JTextField();
			c.fill = GridBagConstraints.HORIZONTAL;
			c.weightx = 0.7;
			c.gridx = 2;
			c.gridy = 2;
			c.gridwidth = 2;
			c.insets = new Insets(0, 0, 20, 20);
			panel.add(tf_nomEtud, c);
			if (etudiant != null)
				tf_nomEtud.setText(etudiant.getNomEtudiant());
			
			
		JLabel l_prenomEtud = new JLabel("Prénom étudiant");
			c.fill = GridBagConstraints.HORIZONTAL;
			c.weightx = 0.3;
			c.gridx = 1;
			c.gridy = 3;
			c.gridwidth = 1;
			c.insets = new Insets(0, -30, 20, 20);
			panel.add(l_prenomEtud, c);
			
		JTextField tf_prenomEtud = new JTextField();
			c.fill = GridBagConstraints.HORIZONTAL;
			c.weightx = 0.7;
			c.gridx = 2;
			c.gridy = 3;
			c.gridwidth = 2;
			c.insets = new Insets(0, 0, 20, 20);
			panel.add(tf_prenomEtud, c);
			if (etudiant != null)
				tf_prenomEtud.setText(etudiant.getPrenomEtudiant());
		
			
		JLabel l_INE = new JLabel("N° INE");
			c.fill = GridBagConstraints.HORIZONTAL;
			c.weightx = 0.5;
			c.gridx = 1;
			c.gridy = 4;
			c.gridwidth = 1;
			c.insets = new Insets(0, -30, 20, 20);
			panel.add(l_INE, c);
		
		JTextField tf_INE = new JTextField();
			c.fill = GridBagConstraints.HORIZONTAL;
			c.weightx = 0.5;
			c.gridx = 2;
			c.gridy = 4;
			c.gridwidth = 2;
			c.insets = new Insets(0, 0, 20, 20);
			panel.add(tf_INE, c);
			if (etudiant != null)
				tf_INE.setText(etudiant.getNumeroEtudiant());
		
		
		JButton b_valider = new JButton("Valider");
		b_valider.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) 
			{
				if(e.getSource() == b_valider)
				   {
					   String nomEtud = tf_nomEtud.getText();
					   String prenomEtud = tf_prenomEtud.getText();
					   String INE = tf_INE.getText();
					   String adrServ = tf_adrServ.getText();
					   
					   if (!nomEtud.isEmpty() && !prenomEtud.isEmpty() && !INE.isEmpty() && !adrServ.isEmpty())
					   {
						   controller.receptionEtudiant(prenomEtud, nomEtud, INE, adrServ);
						   instance.dispose();
					   }
					   else 
						   JOptionPane.showMessageDialog(instance, "Veuillez renseigner tous les champs.");
				   }
			}
		});
			c.fill = GridBagConstraints.CENTER;
			c.weightx = 0.2;
			c.gridx = 3;
			c.gridy = 5;
			c.gridwidth = 1;
			c.insets = new Insets(0, 100, 15, 35);
			panel.add(b_valider, c);
					
		return panel;
	}
}

