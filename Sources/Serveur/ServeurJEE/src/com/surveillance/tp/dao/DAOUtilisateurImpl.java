package com.surveillance.tp.dao;

import static com.surveillance.tp.dao.DAOUtilitaire.fermeturesSilencieuses;
import static com.surveillance.tp.dao.DAOUtilitaire.initialisationRequetePreparee;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.surveillance.tp.beans.Utilisateur;

/**
 * Implémentation des méthodes de recherche et mise à jour dans la table Utilisateur
 */
public class DAOUtilisateurImpl implements DAOUtilisateur {

	private DAOFactory daoFactory;

	DAOUtilisateurImpl( DAOFactory daoFactory ) {
		this.daoFactory = daoFactory;
	}

	/*
	 * Simple méthode utilitaire permettant de faire la correspondance (le
	 * mapping) entre une ligne issue de la table des utilisateurs (un
	 * ResultSet) et un bean Utilisateur.
	 */
	private static Utilisateur map( ResultSet resultSet ) throws SQLException {
		Utilisateur utilisateur = new Utilisateur();

		utilisateur.setId(resultSet.getInt("id_user"));
		utilisateur.setNom(resultSet.getString("nom_user"));
		utilisateur.setPrenom(resultSet.getString("prenom"));
		utilisateur.setPassword(resultSet.getString("password"));
		utilisateur.setMail(resultSet.getString("mail"));
		utilisateur.setGroupe(resultSet.getString("groupe"));
		utilisateur.setCleRecup(resultSet.getString("cle_reset_mail"));

		return utilisateur;
	}

	//Requête permettant d'insérer un utilisateur dans la BDD
	private static final String SQL_INSERT_ETUD = "INSERT INTO Utilisateur (prenom, nom_user, password, mail, groupe) VALUES (?, ?, MD5(?), ?, ?)";

	@Override
	public void creer(Utilisateur utilisateur) throws DAOException {
		Connection connexion = null;
		PreparedStatement preparedStatement = null;
		ResultSet valeursAutoGenerees = null;
		try {
			/* Récupération d'une connexion depuis la Factory */
			connexion = daoFactory.getConnection();
			preparedStatement = initialisationRequetePreparee( connexion, SQL_INSERT_ETUD, true, utilisateur.getPrenom(), utilisateur.getNom(), utilisateur.getPassword(), utilisateur.getMail(), "eleve");
			int statut = preparedStatement.executeUpdate();
			/* Analyse du statut retourné par la requête d'insertion */
			if ( statut == 0 ) {
				throw new DAOException( "Échec de la création de l'utilisateur, aucune ligne ajoutée dans la table." );
			}
			/* Récupération de l'id auto-généré par la requête d'insertion */
			valeursAutoGenerees = preparedStatement.getGeneratedKeys();
			if ( valeursAutoGenerees.next() ) {
				/* Puis initialisation de la propriété id du bean Utilisateur avec sa valeur */
				utilisateur.setId(valeursAutoGenerees.getInt(1));
			} else {
				throw new DAOException( "Échec de la création de l'utilisateur en base, aucun ID auto-généré retourné." );
			}
		} catch ( SQLException e ) {
			throw new DAOException( e );
		} finally {
			fermeturesSilencieuses( valeursAutoGenerees, preparedStatement, connexion );
		}
	}

	//Requête permettant de rechercher un utilisateur dans la BDD par son ID
	private static final String SQL_SELECT_UTILISATEUR_MDP = "SELECT id_user, prenom, nom_user, password, mail, groupe, cle_reset_mail FROM Utilisateur WHERE mail = ? AND password = MD5(?)";

	@Override
	public Utilisateur trouverMdp(String mail, String password) throws DAOException {
		Connection connexion = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		Utilisateur utilisateur = null;

		try {
			/* Récupération d'une connexion depuis la Factory */
			connexion = daoFactory.getConnection();
			preparedStatement = initialisationRequetePreparee(connexion, SQL_SELECT_UTILISATEUR_MDP, false, mail, password);
			resultSet = preparedStatement.executeQuery();
			/* Parcours de la ligne de données de l'éventuel ResulSet retourné */
			if ( resultSet.next() ) {
				utilisateur = map( resultSet );
			}
		} catch ( SQLException e ) {
			throw new DAOException( e );
		} finally {
			fermeturesSilencieuses( resultSet, preparedStatement, connexion );
		}
		return utilisateur;
	}
	
	//Requête permettant de rechercher un utilisateur dans la BDD par son ID
		private static final String SQL_SELECT_UTILISATEUR_CLE_RESET = "SELECT id_user, prenom, nom_user, password, mail, groupe, cle_reset_mail FROM Utilisateur WHERE cle_reset_mail = ?";

		@Override
		public Utilisateur trouverCleReset(String cle) throws DAOException {
			Connection connexion = null;
			PreparedStatement preparedStatement = null;
			ResultSet resultSet = null;
			Utilisateur utilisateur = null;

			try {
				/* Récupération d'une connexion depuis la Factory */
				connexion = daoFactory.getConnection();
				preparedStatement = initialisationRequetePreparee(connexion, SQL_SELECT_UTILISATEUR_CLE_RESET, false, cle);
				resultSet = preparedStatement.executeQuery();
				/* Parcours de la ligne de données de l'éventuel ResulSet retourné */
				if ( resultSet.next() ) {
					utilisateur = map( resultSet );
				}
			} catch ( SQLException e ) {
				throw new DAOException( e );
			} finally {
				fermeturesSilencieuses( resultSet, preparedStatement, connexion );
			}
			return utilisateur;
		}

	//Requête permettant de rechercher un utilisateur dans la BDD par son ID
		private static final String SQL_SELECT_UTILISATEUR_ID = "SELECT id_user, prenom, nom_user, password, mail, groupe, cle_reset_mail FROM Utilisateur WHERE id_user = ?";


	@Override
	public Utilisateur trouverID(int idUtil) {
		Connection connexion = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		Utilisateur utilisateur = null;

		try {
			/* Récupération d'une connexion depuis la Factory */
			connexion = daoFactory.getConnection();
			preparedStatement = initialisationRequetePreparee(connexion, SQL_SELECT_UTILISATEUR_ID, false, idUtil);
			resultSet = preparedStatement.executeQuery();
			/* Parcours de la ligne de données de l'éventuel ResulSet retourné */
			if ( resultSet.next() ) {
				utilisateur = map( resultSet );
			}
		} catch ( SQLException e ) {
			throw new DAOException( e );
		} finally {
			fermeturesSilencieuses( resultSet, preparedStatement, connexion );
		}
		return utilisateur;
	}

	private static final String SQL_SELECT_LISTE_UTILISATEUR = "SELECT id_user, prenom, nom_user, password, mail, groupe, cle_reset_mail  FROM Utilisateur";

	@Override
	public 
	ArrayList<Utilisateur>recupererUtilisateurs() throws DAOException{
		Connection connexion = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		ArrayList<Utilisateur> utilisateurs = new ArrayList<Utilisateur>();


		try {
			/* Récupération d'une connexion depuis la Factory */
			connexion =  daoFactory.getConnection();
			preparedStatement = initialisationRequetePreparee( connexion, SQL_SELECT_LISTE_UTILISATEUR, false);
			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				Utilisateur utilisateur = null;
				utilisateur = map( resultSet );
				utilisateurs.add(utilisateur);
			}
		} catch ( SQLException e ) {
			throw new DAOException( e );
		} finally {
			fermeturesSilencieuses( resultSet, preparedStatement, connexion );
		}
		return utilisateurs;
	}

	private static final String SQL_SELECT_UTILISATEUR = "SELECT id_user, prenom, nom_user, password, mail, groupe, cle_reset_mail FROM Utilisateur WHERE mail = ?";

	@Override
	public Utilisateur trouver(String mail) throws DAOException {
		Connection connexion = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		Utilisateur utilisateur = null;

		try {
			/* Récupération d'une connexion depuis la Factory */
			connexion = daoFactory.getConnection();
			preparedStatement = initialisationRequetePreparee(connexion, SQL_SELECT_UTILISATEUR, false, mail);
			resultSet = preparedStatement.executeQuery();
			/* Parcours de la ligne de données de l'éventuel ResulSet retourné */
			if ( resultSet.next() ) {
				utilisateur = map( resultSet );
			}
		} catch ( SQLException e ) {
			throw new DAOException( e );
		} finally {
			fermeturesSilencieuses( resultSet, preparedStatement, connexion );
		}
		return utilisateur;
	}

	private static final String SQL_DELETE_UTIL = "DELETE FROM Utilisateur WHERE id_user  = ?";

	@Override
	public void supprimer(int idUtil) throws DAOException {
		Connection connexion = null;
		PreparedStatement preparedStatement = null;

		try {
			/* Récupération d'une connexion depuis la Factory */
			connexion = daoFactory.getConnection();
			preparedStatement = initialisationRequetePreparee( connexion, SQL_DELETE_UTIL, false, idUtil );
			preparedStatement.executeUpdate();
		} catch ( SQLException e ) {
			throw new DAOException( e );
		} finally {
			fermeturesSilencieuses(preparedStatement, connexion);
		}
	}

	//Requête permettant de mettre à jour un examen
	private static final String SQL_UPDATE_UTIL = "UPDATE Utilisateur SET prenom = ?, nom_user = ?, password = MD5(?), mail = ?, groupe = ? WHERE id_user = ?";

	@Override
	public void miseAJour(Utilisateur utilisateur) throws DAOException {
		Connection connexion = null;
		PreparedStatement preparedStatement = null;

		try {
			/* Récupération d'une connexion depuis la Factory */
			connexion = daoFactory.getConnection();
			preparedStatement = initialisationRequetePreparee( connexion, SQL_UPDATE_UTIL, false, utilisateur.getPrenom(), utilisateur.getNom(), utilisateur.getPassword(), utilisateur.getMail(), utilisateur.getGroupe(), utilisateur.getId() );
			preparedStatement.executeUpdate();
		} catch ( SQLException e ) {
			throw new DAOException( e );
		} finally {
			fermeturesSilencieuses(preparedStatement, connexion);
		}
	}
	
	//Requête permettant de mettre à jour la clé de reset d'un examen
		private static final String SQL_UPDATE_UTIL_RESET = "UPDATE Utilisateur SET cle_reset_mail = ? WHERE id_user = ?";

		@Override
		public void miseAJourReset(Utilisateur utilisateur, String token) throws DAOException {
			Connection connexion = null;
			PreparedStatement preparedStatement = null;

			try {
				/* Récupération d'une connexion depuis la Factory */
				connexion = daoFactory.getConnection();
				preparedStatement = initialisationRequetePreparee( connexion, SQL_UPDATE_UTIL_RESET, false, token, utilisateur.getId() );
				preparedStatement.executeUpdate();
			} catch ( SQLException e ) {
				throw new DAOException( e );
			} finally {
				fermeturesSilencieuses(preparedStatement, connexion);
			}
		}
}
