package com.surveillance.tp.beans;

public class Examen {
	
	private int idExam;		//PRIMARY KEY
	private int nomExam;	//FOREIGN KEY
	private int idProf;
	
	/* Getters */
	public int getIdExam()
	{
		return idExam;
	}
	
	public int getNomExam()
	{
		return nomExam;
	}
	
	public int getIdProf()
	{
		return idProf;
	}
	
	
	/* Setters */
	public void setIdExam(int id)
	{
		this.idExam = id;
	}
	
	public void setNomExam(int nom)
	{
		this.nomExam = nom;
	}
	
	public void setIdProf(int id)
	{
		this.idProf = id;
	}
}
