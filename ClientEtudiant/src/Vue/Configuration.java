package Vue;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.swing.*;

import Controller.ConfigController;
import Model.EtudiantExamenInfoSingleton;

/**
 * Fenetre où l'utilisateur peut entrer ses identifiants ainsi que, si nécessaire, l'adresse du serveur
 */
public class Configuration extends JDialog {
	private Configuration instance=this;
	private ConfigController controller;
	private EtudiantExamenInfoSingleton etudiant;
	private JPanel panel;
	private GridBagConstraints c;
	private JLabel l_adrServ;
	private JLabel l_mail;
	private JLabel l_mdp;
	private JButton b_valider;
	private JTextField tf_mail;
	private JPasswordField tf_mdp;
	private JTextField tf_adrServ;

	public Configuration() throws FileNotFoundException {
		super();
		controller=new ConfigController(this);
		etudiant=EtudiantExamenInfoSingleton.getInstanceExistante();
		// Initialisation de la fenêtre
		build();
	}

	private void build() throws FileNotFoundException {
		setSize(420, 210);
		setLocationRelativeTo(null);
		setResizable(false);
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setContentPane(buildContentPane());
	}

	private JPanel buildContentPane() throws FileNotFoundException {
		panel=new JPanel();
		panel.setLayout(new GridBagLayout());
		c=new GridBagConstraints();
		c.fill=GridBagConstraints.NONE;
		c.weightx=0;
		c.gridx=0;
		c.gridy=0;
		c.gridwidth=1;
		c.insets=new Insets(10, 10, 20, 10);

		l_adrServ=new JLabel("Adresse serveur");
		c.fill=GridBagConstraints.HORIZONTAL;
		c.weightx=0.3;
		c.gridx=1;
		c.gridy=1;
		c.gridwidth=1;
		c.insets=new Insets(0, -30, 20, 20);
		panel.add(l_adrServ, c);

		tf_adrServ=new JTextField();
		c.fill=GridBagConstraints.HORIZONTAL;
		c.weightx=0.7;
		c.gridx=2;
		c.gridy=1;
		c.gridwidth=2;
		c.insets=new Insets(0, 0, 20, 20);
		panel.add(tf_adrServ, c);

		try(BufferedReader br=new BufferedReader(new InputStreamReader(Configuration.class.getResourceAsStream("/Ressources/adrServ")))) {
			String line;
			while((line=br.readLine())!=null) tf_adrServ.setText(line);
			br.close();
		} catch(IOException e1) {
			e1.printStackTrace();
		}

		l_mail=new JLabel("Mail");
		c.fill=GridBagConstraints.HORIZONTAL;
		c.weightx=0.3;
		c.gridx=1;
		c.gridy=2;
		c.gridwidth=1;
		c.insets=new Insets(0, -30, 20, 20);
		panel.add(l_mail, c);

		tf_mail=new JTextField();
		c.fill=GridBagConstraints.HORIZONTAL;
		c.weightx=0.7;
		c.gridx=2;
		c.gridy=2;
		c.gridwidth=2;
		c.insets=new Insets(0, 0, 20, 20);
		panel.add(tf_mail, c);
		if(etudiant!=null) tf_mail.setText(etudiant.getIdentifiant()); // l'identifiant du singleton étudiant correspond au mail étudiant

		l_mdp=new JLabel("Mot de passe");
		c.fill=GridBagConstraints.HORIZONTAL;
		c.weightx=0.3;
		c.gridx=1;
		c.gridy=3;
		c.gridwidth=1;
		c.insets=new Insets(0, -30, 20, 20);
		panel.add(l_mdp, c);

		tf_mdp=new JPasswordField();
		c.fill=GridBagConstraints.HORIZONTAL;
		c.weightx=0.7;
		c.gridx=2;
		c.gridy=3;
		c.gridwidth=2;
		c.insets=new Insets(0, 0, 20, 20);
		panel.add(tf_mdp, c);

		b_valider=new JButton("Valider");
		b_valider.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(e.getSource()==b_valider) {
					String identifiant=tf_mail.getText();
					String mdp=new String(tf_mdp.getPassword());
					String adrServ=tf_adrServ.getText();
					if(!identifiant.isEmpty() && mdp.length()!=0 && !adrServ.isEmpty()) {
						controller.receptionEtudiant(identifiant, mdp, adrServ);
						instance.dispose();
					}
					else JOptionPane.showMessageDialog(instance, "Veuillez renseigner tous les champs.");
				}
			}
		});
		c.fill=GridBagConstraints.CENTER;
		c.weightx=0.2;
		c.gridx=3;
		c.gridy=4;
		c.gridwidth=1;
		c.insets=new Insets(0, 100, 15, 35);
		panel.add(b_valider, c);
		return panel;
	}
}