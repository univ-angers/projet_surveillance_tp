package Model;

import java.util.ArrayList;

import Vue.Observer;

public class TempsSingleton implements Sujet{

	String tempsRestantExamen;
	ArrayList<Observer> listObs;
	static TempsSingleton instance = null;

	private TempsSingleton()
	{
		this.tempsRestantExamen = "Acquisition en cours...";
		listObs = new ArrayList<Observer>();
	}

	public static TempsSingleton getInstance()
	{
		if (instance == null)
			instance =  new TempsSingleton();
		return instance;
	}

	public void setTemps(String tps)
	{
		this.tempsRestantExamen = tps;
		notifierObserver();
	}
	public String getTemps()
	{
		return this.tempsRestantExamen;
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
		for (int i=0; i<listObs.size(); i++)
			listObs.get(i).actualiser();
	}
}
