DROP TABLE IF EXISTS list_of_rule;
DROP TABLE IF EXISTS Rule;
DROP TABLE IF EXISTS Watcher;
DROP TABLE IF EXISTS niveau_rule;
DROP TABLE IF EXISTS Examen;
DROP TABLE IF EXISTS Utilisateur;

CREATE TABLE Utilisateur (
id_user INTEGER NOT NULL AUTO_INCREMENT,
prenom VARCHAR(50),
nom_user VARCHAR(50),
password VARCHAR(255),
mail VARCHAR(100) UNIQUE,
groupe VARCHAR(50),
cle_reset_mail VARCHAR(30),
PRIMARY KEY (id_user)
) DEFAULT CHARSET=utf8;

CREATE TABLE Examen (
id_examen INTEGER NOT NULL AUTO_INCREMENT,
id_user INTEGER,
matiere VARCHAR(100),
duree TIME,
heure_debut TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,
etat VARCHAR(3) DEFAULT 'on',
PRIMARY KEY (id_examen),
FOREIGN KEY (id_user) REFERENCES Utilisateur(id_user)
) DEFAULT CHARSET=utf8;

CREATE TABLE niveau_rule (
id_niveau_rule INTEGER NOT NULL AUTO_INCREMENT,
nom VARCHAR(100),
PRIMARY KEY (id_niveau_rule)
) DEFAULT CHARSET=utf8;

INSERT INTO niveau_rule (nom) VALUES ("grave");
INSERT INTO niveau_rule (nom) VALUES ("normal");
INSERT INTO niveau_rule (nom) VALUES ("anecdotique");

CREATE TABLE Watcher (
id_watcher INTEGER NOT NULL AUTO_INCREMENT,
description VARCHAR(100),
PRIMARY KEY (id_watcher)
) DEFAULT CHARSET=utf8;

INSERT INTO Watcher (description) VALUES ("USB");
INSERT INTO Watcher (description) VALUES ("Fichier");
INSERT INTO Watcher (description) VALUES ("Video");
INSERT INTO Watcher (description) VALUES ("Net");
INSERT INTO Watcher (description) VALUES ("Clavier");

CREATE TABLE Rule (
id_rule INTEGER NOT NULL AUTO_INCREMENT,
description VARCHAR(100),
id_niveau INTEGER,
id_watcher INTEGER,
PRIMARY KEY (id_rule),
FOREIGN KEY (id_niveau) REFERENCES niveau_rule(id_niveau_rule),
FOREIGN KEY (id_watcher) REFERENCES Watcher(id_watcher)
) DEFAULT CHARSET=utf8;

INSERT INTO Rule (description, id_niveau, id_watcher) VALUES ("connexion_usb", 1, 1);
INSERT INTO Rule (description, id_niveau, id_watcher) VALUES ("deconnexion_usb", 1, 1);
INSERT INTO Rule (description, id_niveau, id_watcher) VALUES ("creation_fichier", 2, 2);
INSERT INTO Rule (description, id_niveau, id_watcher) VALUES ("modification_fichier", 2, 2);
INSERT INTO Rule (description, id_niveau, id_watcher) VALUES ("suppression_fichier", 3, 2);
INSERT INTO Rule (description, id_niveau, id_watcher) VALUES ("lancement_video", 1, 3);
INSERT INTO Rule (description, id_niveau, id_watcher) VALUES ("network", 1, 4);
INSERT INTO Rule (description, id_niveau, id_watcher) VALUES ("touche_appuyee", 3, 5);
INSERT INTO Rule (description, id_niveau, id_watcher) VALUES ("etudiant_deconnexion", 1, 5);

CREATE TABLE list_of_rule (
id_rule INTEGER,
id_examen INTEGER,
attributs VARCHAR(200),
FOREIGN KEY (id_rule) REFERENCES Rule(id_rule),
FOREIGN KEY (id_examen) REFERENCES Examen(id_examen)
) DEFAULT CHARSET=utf8;

INSERT INTO Utilisateur (prenom, nom_user, password, mail, groupe) VALUES ("prof","nomProf",MD5("987654"),"prof@univ","professeur");
INSERT INTO Utilisateur (prenom, nom_user, password, mail, groupe) VALUES ("remplacant","genial",MD5("123456"),"profremp@univ","professeur");
INSERT INTO Utilisateur (prenom, nom_user, password, mail, groupe) VALUES ("Bastien","Pigache",MD5("azerty"),"proz@etud","eleve");
INSERT INTO Utilisateur (prenom, nom_user, password, mail, groupe) VALUES ("Anaïs","Mohr",MD5("qsdfgh"),"erinyth@etud","eleve");
INSERT INTO Utilisateur (prenom, nom_user, password, mail, groupe) VALUES ("Riad","Guenane",MD5("wxcvbn"),"riad@etud","eleve");
