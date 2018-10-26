import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.*;

public class Quitter extends JFrame
{	
	Quitter instance = this;
	
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
		JPanel panel = new JPanel();
		panel.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();

		JButton b_option = new JButton();
		Image img;
		try 
		{
			img = ImageIO.read(getClass().getResource("/o.png"));
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
			c.insets = new Insets(10, -110, 20, 10);
			panel.add(b_option, c);


		JLabel l_quitter = new JLabel("Voulez-vous vraiment quitter l'examen ?");
			c.fill = GridBagConstraints.CENTER;
			c.weightx = 0.3;
			c.gridx = 1;
			c.gridy = 0;
			c.gridwidth = 1;
			c.insets = new Insets(10, -110, 20, 40);
			panel.add(l_quitter, c);


		JButton b_valider = new JButton("Oui");
		b_valider.setBackground(new Color(225, 65, 54));
		b_valider.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) 
			{
				if(e.getSource() == b_valider)
				   {
					   // AJOUTER ICI ALERTE
					   System.exit(0);
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
		
		JButton b_refuser = new JButton("Non");
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
