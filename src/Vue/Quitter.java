package Vue;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

import Controller.Main;

public class Quitter extends JDialog
{
	private Quitter instance = this;

	private JPanel panel;
	private GridBagConstraints c;
	private JLabel l_quitter;
	private JButton b_valider;
	private JButton b_refuser;

	public Quitter()
	{
		super();

		// Initialisation de la fenêtre
		build();
	}

	private void build()
	{
		setSize(420, 90);
		setLocationRelativeTo(null);
		setResizable(false);
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);

		setContentPane(buildContentPane());
	}

	private JPanel buildContentPane()
	{
		panel = new JPanel();
		panel.setLayout(new GridBagLayout());
		c = new GridBagConstraints();

		l_quitter = new JLabel("Voulez-vous vraiment quitter l'examen ?");
		c.fill = GridBagConstraints.CENTER;
		c.weightx = 0.3;
		c.gridx = 1;
		c.gridy = 0;
		c.gridwidth = 1;
		c.insets = new Insets(10, -150, 20, 40);
		panel.add(l_quitter, c);


		b_valider = new JButton("Oui");
		b_valider.setBackground(new Color(225, 65, 54));
		b_valider.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) 
			{
				if(e.getSource() == b_valider)
				{
					Main.surveillanceEnCours = false;

					JOptionPane.showMessageDialog(instance, "Une alerte a été envoyée à votre professeur.");

					Thread t = new Thread() {
						public void run() {
							try {
								sleep(2000);	//Permet aux watchers de bien de terminer
								System.exit(0);
							} catch (InterruptedException e) {
								e.printStackTrace();
							}
						}
					};
					t.run();
				}
			}
		});
		c.fill = GridBagConstraints.CENTER;
		c.weightx = 0.2;
		c.gridx = 0;
		c.gridy = 1;
		c.gridwidth = 1;
		c.insets = new Insets(0, 100, 15, 35);
		panel.add(b_valider, c);

		b_refuser = new JButton("Non");
		b_refuser.setBackground(new Color(161, 225, 88));
		b_refuser.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) 
			{
				if(e.getSource() == b_refuser)
				{
					instance.dispose();
				}
			}
		});
		c.fill = GridBagConstraints.CENTER;
		c.weightx = 0.2;
		c.gridx = 1;
		c.gridy = 1;
		c.gridwidth = 1;
		c.insets = new Insets(0, 50, 15, 100);
		panel.add(b_refuser, c);


		return panel;
	}
}   
