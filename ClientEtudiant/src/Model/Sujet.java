package Model;

import Vue.Observer;

public interface Sujet {
	public void ajouterObserver(Observer obs);
	public void supprimerObserver(Observer obs);
	public void notifierObserver();
}