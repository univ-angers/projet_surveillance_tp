package Model;

import java.util.ArrayList;

import Vue.Observer;

/**
 * Classe récupérant le temps restant à l'examen et mettant à jour
 * l'interface graphique de l'étudiant
 */
public class TempsSingleton implements Sujet {
	private String tempsRestantExamen;
	private ArrayList<Observer> listObs;
	static TempsSingleton instance=null;

	private TempsSingleton() {
		tempsRestantExamen="Acquisition en cours...";
		listObs=new ArrayList<Observer>();
	}

	public static TempsSingleton getInstance() {
		if(instance==null) instance=new TempsSingleton();
		return instance;
	}

	public void setTemps(String tps) {
		tempsRestantExamen=tps;
		notifierObserver();
	}
	public String getTemps() {
		return tempsRestantExamen;
	}

	@Override
	public void ajouterObserver(Observer obs) {
		listObs.add(obs);
	}

	@Override
	public void supprimerObserver(Observer obs) {
		listObs.remove(obs);
	}

	@Override
	public void notifierObserver() {
		for(int i=0; i<listObs.size(); i++) listObs.get(i).actualiser();
	}
}