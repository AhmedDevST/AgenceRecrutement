-- phpMyAdmin SQL Dump
-- version 5.2.0
-- https://www.phpmyadmin.net/
--
-- Hôte : 127.0.0.1
-- Généré le : sam. 18 mai 2024 à 17:21
-- Version du serveur : 10.4.24-MariaDB
-- Version de PHP : 8.1.6

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de données : `agencerecrutement`
--

DELIMITER $$
--
-- Procédures
--
CREATE DEFINER=`root`@`localhost` PROCEDURE `UpdateEtatOffres` (IN `p_idOffreUpdate` INT)   BEGIN
        UPDATE offre
        SET offre.EtatOffre = 0
        WHERE offre.IdOffre = p_idOffreUpdate
          AND NbPostOffers <= (SELECT COUNT(*) FROM recrutement r WHERE r.IdOff = p_idOffreUpdate);
        
        UPDATE offre
        SET offre.EtatOffre = 1
        WHERE offre.IdOffre = p_idOffreUpdate
          AND NbPostOffers > (SELECT COUNT(*) FROM recrutement r WHERE r.IdOff = p_idOffreUpdate);
END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `update_abonnemnet_etat` ()   BEGIN
    UPDATE abonnement
    SET EtatAbon = 0
    WHERE DateExpiration < CURDATE();
END$$

DELIMITER ;

-- --------------------------------------------------------

--
-- Structure de la table `abonnement`
--

CREATE TABLE `abonnement` (
  `IDAbon` int(11) NOT NULL,
  `IdJr` int(11) NOT NULL,
  `IdEs` int(11) NOT NULL,
  `DateExpiration` date NOT NULL,
  `EtatAbon` bit(1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Déchargement des données de la table `abonnement`
--

INSERT INTO `abonnement` (`IDAbon`, `IdJr`, `IdEs`, `DateExpiration`, `EtatAbon`) VALUES
(14, 7, 11, '2024-05-30', b'1'),
(15, 12, 11, '2024-06-02', b'1'),
(16, 6, 13, '2024-05-31', b'1'),
(17, 11, 13, '2024-06-09', b'1');

-- --------------------------------------------------------

--
-- Structure de la table `admin`
--

CREATE TABLE `admin` (
  `idAdmin` int(11) NOT NULL,
  `UserName` varchar(50) NOT NULL,
  `passwordUser` varchar(10) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Déchargement des données de la table `admin`
--

INSERT INTO `admin` (`idAdmin`, `UserName`, `passwordUser`) VALUES
(1, 'admin', '123456');

-- --------------------------------------------------------

--
-- Structure de la table `annonce`
--

CREATE TABLE `annonce` (
  `IdAnnonce` int(11) NOT NULL,
  `Id_edition` int(11) NOT NULL,
  `Id_offre` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Déchargement des données de la table `annonce`
--

INSERT INTO `annonce` (`IdAnnonce`, `Id_edition`, `Id_offre`) VALUES
(17, 13, 11),
(18, 14, 12),
(19, 14, 10),
(20, 16, 11);

-- --------------------------------------------------------

--
-- Structure de la table `categorie`
--

CREATE TABLE `categorie` (
  `IdCat` int(11) NOT NULL,
  `Libelle` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Déchargement des données de la table `categorie`
--

INSERT INTO `categorie` (`IdCat`, `Libelle`) VALUES
(5, 'Economie'),
(6, 'Marketing'),
(7, 'AT'),
(8, 'Droit'),
(9, 'Medcine');

-- --------------------------------------------------------

--
-- Structure de la table `demandeur`
--

CREATE TABLE `demandeur` (
  `CodeInterneD` int(11) NOT NULL,
  `Nom` varchar(50) NOT NULL,
  `Prenom` varchar(50) NOT NULL,
  `Adresse` varchar(150) NOT NULL,
  `Telephone` varchar(50) NOT NULL,
  `Fax` varchar(50) NOT NULL,
  `NbAnneeExp` int(11) NOT NULL,
  `Salaire` float NOT NULL,
  `diplome` varchar(100) NOT NULL,
  `usernameDem` varchar(50) NOT NULL,
  `passwordDem` varchar(10) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Déchargement des données de la table `demandeur`
--

INSERT INTO `demandeur` (`CodeInterneD`, `Nom`, `Prenom`, `Adresse`, `Telephone`, `Fax`, `NbAnneeExp`, `Salaire`, `diplome`, `usernameDem`, `passwordDem`) VALUES
(10, 'Badr', 'Zahra', '123 Rue de la Liberté, Casablanca', '0601123456', '+212 5 22 33 44 55', 5, 12000, 'Diplôme en Informatique', 'badr_zahra', 'pass1234'),
(11, 'Youssef', 'Arabi', '456 Avenue Hassan II, Marrakech', '0601123456', '+212 5 24 56 78 90', 3, 9500, 'Diplôme en Économie', 'youssef_arabi', 'pass5678'),
(12, 'Fatima', 'Zahra', '789 Boulevard Mohammed V, Rabat', '0602234567', '+212 5 34 67 89 01', 10, 15000, 'Diplôme en Médecine', 'fatima_zahra', 'pass2345'),
(13, 'Mohamed', 'Amrani', '101 Rue de la Presse, Fès', '0603345678', '+212 5 45 78 90 12', 2, 8000, 'Diplôme en Droit', 'mohamed_amrani', 'pass3456'),
(14, 'Aicha', 'Bahmed', '202 Parc de la Nature, Tanger', '0604456789', '+212 5 56 89 01 23', 7, 11000, 'Diplôme en Industrie', 'aicha_bahmed', 'pass4567'),
(15, 'Said', 'Hashimi', '12 Rue des Platanes, Casablanca', '0605567890', '+212 5 66 77 88 99', 5, 9500, 'Diplôme en Informatique', 'said_hashimi', 'pass5678'),
(16, 'Meryem', 'Sharif', '34 Boulevard Al Massira, Rabat', '0606678901', '+212 5 78 89 90 12', 6, 10500, 'Diplôme en Économie', 'meryem_sharif', 'pass6789'),
(17, 'Hassan', 'Bakali', '56 Avenue Mohammed VI, Marrakech', '0607789012', '+212 5 89 90 12 34', 8, 13000, 'Diplôme en Médecine', 'hassan_bakali', 'pass7890'),
(18, 'Lobna', 'Mahdi', '78 Rue des Industries, Fès', '0608890123', '+212 5 90 12 34 56', 1, 7000, 'Diplôme en Industrie', 'lobna_mahdi', 'pass8901'),
(19, 'Sami', 'Alawi', '90 Avenue des Juristes, Tanger', '0609901234', '+212 5 12 34 56 78', 9, 14000, 'Diplôme en Droit', 'sami_alawi', 'pass9012');

-- --------------------------------------------------------

--
-- Structure de la table `editions`
--

CREATE TABLE `editions` (
  `IdEd` int(11) NOT NULL,
  `journalEd` int(11) NOT NULL,
  `DateParution` date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Déchargement des données de la table `editions`
--

INSERT INTO `editions` (`IdEd`, `journalEd`, `DateParution`) VALUES
(13, 6, '2024-05-18'),
(14, 7, '2024-05-18'),
(15, 9, '2024-05-18'),
(16, 11, '2024-05-18');

-- --------------------------------------------------------

--
-- Structure de la table `emission`
--

CREATE TABLE `emission` (
  `IdEmis` int(11) NOT NULL,
  `ID_Ab` int(11) NOT NULL,
  `ID_Offre` int(11) NOT NULL,
  `DateEmis` date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Déchargement des données de la table `emission`
--

INSERT INTO `emission` (`IdEmis`, `ID_Ab`, `ID_Offre`, `DateEmis`) VALUES
(9, 14, 10, '2024-05-18'),
(10, 15, 10, '2024-05-18'),
(11, 14, 12, '2024-05-18'),
(12, 16, 11, '2024-05-18'),
(13, 17, 11, '2024-05-18');

-- --------------------------------------------------------

--
-- Structure de la table `entreprise`
--

CREATE TABLE `entreprise` (
  `CodeInterneEs` int(11) NOT NULL,
  `Adresse` varchar(150) NOT NULL,
  `Telephone` varchar(50) NOT NULL,
  `RaisonSocial` varchar(100) NOT NULL,
  `Activité` varchar(100) NOT NULL,
  `usernameEst` varchar(50) NOT NULL,
  `passwordEst` varchar(10) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Déchargement des données de la table `entreprise`
--

INSERT INTO `entreprise` (`CodeInterneEs`, `Adresse`, `Telephone`, `RaisonSocial`, `Activité`, `usernameEst`, `passwordEst`) VALUES
(11, '123 Rue de la Liberté, Casablanca', '+212 5 22 33 44 55', 'Alpha Technologie', 'Développement de logiciels', 'alpha_tech', 'pass1234'),
(12, '456 Avenue Hassan II, Marrakech', '+212 5 24 56 78 90', 'Boulangerie du Bon Pain', 'Boulangerie et pâtisserie', 'bonpain', 'pain2024'),
(13, '789 Boulevard Mohammed V, Rabat', '+212 5 34 67 89 01', 'EcoNet Services', 'Nettoyage écologique', 'econet', 'eco2024'),
(14, '101 Rue de la Presse, Fès', '+212 5 45 78 90 12', 'MédiPrint', 'Imprimerie et services de reprographie', 'mediprint', 'print2024'),
(15, '202 Parc de la Nature, Tanger', '+212 5 56 89 01 23', 'Jardin Vert', 'Aménagement paysager', 'jardin_vert', 'vert2024'),
(16, '12 Rue des Platanes, Casablanca', '+212 5 66 77 88 99', 'InfoSys Maroc', 'Consultance en informatique', 'infosys_maroc', 'infosys24'),
(17, '34 Boulevard Al Massira, Rabat', '+212 5 78 89 90 12', 'EconoConsult', 'Conseil économique', 'econoconsult', 'eco_cons24'),
(18, '56 Avenue Mohammed VI, Marrakech', '+212 5 89 90 12 34', 'MediCare Maroc', 'Services médicaux', 'medicare_maroc', 'medicare24'),
(19, '78 Rue des Industries, Fès', '+212 5 90 12 34 56', 'Industech', 'Solutions industrielles', 'industech', 'indus2024'),
(20, '90 Avenue des Juristes, Tanger', '+212 5 12 34 56 78', 'LexMaroc', 'Conseil juridique', 'lexmaroc', 'lex2024');

-- --------------------------------------------------------

--
-- Structure de la table `journal`
--

CREATE TABLE `journal` (
  `CodeJr` int(11) NOT NULL,
  `NomJr` varchar(100) NOT NULL,
  `categorie` int(11) NOT NULL,
  `Periodicite` varchar(100) NOT NULL,
  `Langue` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Déchargement des données de la table `journal`
--

INSERT INTO `journal` (`CodeJr`, `NomJr`, `categorie`, `Periodicite`, `Langue`) VALUES
(6, 'NewsEco', 5, 'Quotidienne', 'English'),
(7, 'WorldData', 7, 'Hubdomadaire ', 'english'),
(8, 'VentVent', 6, 'Mensuelle', 'francais'),
(9, 'Jour Droit', 8, 'Quotidienne', 'francais'),
(10, 'صحتي', 9, 'Saisonaire', 'arabe'),
(11, 'Economie journal', 5, 'Quotidienne', 'francais'),
(12, 'AI News', 7, 'Mensuelle', 'english');

-- --------------------------------------------------------

--
-- Structure de la table `offre`
--

CREATE TABLE `offre` (
  `IdOffre` int(11) NOT NULL,
  `Id_Es` int(11) NOT NULL,
  `Titre` varchar(100) NOT NULL,
  `Competences` varchar(200) NOT NULL,
  `NbAnneeEX` int(11) NOT NULL,
  `NbPostOffers` int(11) NOT NULL,
  `EtatOffre` tinyint(1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Déchargement des données de la table `offre`
--

INSERT INTO `offre` (`IdOffre`, `Id_Es`, `Titre`, `Competences`, `NbAnneeEX`, `NbPostOffers`, `EtatOffre`) VALUES
(10, 11, 'Développeur Web ', 'HTML, CSS, JavaScript, laravel, MySQL', 3, 2, 1),
(11, 13, 'Analyste Financier', ' Analyse financière, Excel, Gestion des risques, Modélisation financière', 4, 1, 1),
(12, 11, 'Chef de Projet', ' Gestion de projet, Planification, Suivi de projet, Communication', 5, 2, 0),
(13, 13, 'Comptable', ' Comptabilité générale, Fiscalité, Logiciels comptables, Gestion de trésorerie', 2, 2, 1);

-- --------------------------------------------------------

--
-- Structure de la table `postulation`
--

CREATE TABLE `postulation` (
  `IdPost` int(11) NOT NULL,
  `IdDem` int(11) NOT NULL,
  `IdAnn` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Déchargement des données de la table `postulation`
--

INSERT INTO `postulation` (`IdPost`, `IdDem`, `IdAnn`) VALUES
(9, 10, 17),
(10, 11, 19),
(11, 10, 18),
(12, 15, 18),
(13, 15, 19);

-- --------------------------------------------------------

--
-- Structure de la table `pref_demandeur`
--

CREATE TABLE `pref_demandeur` (
  `ID_Dem` int(11) NOT NULL,
  `ID_Cat` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Déchargement des données de la table `pref_demandeur`
--

INSERT INTO `pref_demandeur` (`ID_Dem`, `ID_Cat`) VALUES
(10, 7),
(11, 5),
(15, 7);

-- --------------------------------------------------------

--
-- Structure de la table `pref_entreprise`
--

CREATE TABLE `pref_entreprise` (
  `ID_Es` int(11) NOT NULL,
  `ID_Cat` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Déchargement des données de la table `pref_entreprise`
--

INSERT INTO `pref_entreprise` (`ID_Es`, `ID_Cat`) VALUES
(11, 7),
(13, 5),
(13, 6);

-- --------------------------------------------------------

--
-- Structure de la table `recrutement`
--

CREATE TABLE `recrutement` (
  `IdDemd` int(11) NOT NULL,
  `IdOff` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Déchargement des données de la table `recrutement`
--

INSERT INTO `recrutement` (`IdDemd`, `IdOff`) VALUES
(10, 12),
(15, 10),
(15, 12);

--
-- Déclencheurs `recrutement`
--
DELIMITER $$
CREATE TRIGGER `UPDATE_ETAT_OFFRE` AFTER INSERT ON `recrutement` FOR EACH ROW BEGIN
        UPDATE offre
        SET EtatOffre = 0
        WHERE  IdOffre =  NEW.IdOff
        AND NbPostOffers = ( SELECT COUNT(*)  FROM recrutement WHERE recrutement.IdOff = NEW.IdOff );
END
$$
DELIMITER ;
DELIMITER $$
CREATE TRIGGER `UpdateEtatOffre` AFTER INSERT ON `recrutement` FOR EACH ROW BEGIN
    DECLARE IdOffreRecruter INT;
    SET IdOffreRecruter = NEW.IdOff;
    
    UPDATE offre
    SET offre.EtatOffre = 0
    WHERE offre.IdOffre = IdOffreRecruter 
    AND offre.NbPostOffers <= ( SELECT COUNT(*) FROM recrutement r WHERE r.IdOff = IdOffreRecruter) ;
END
$$
DELIMITER ;

--
-- Index pour les tables déchargées
--

--
-- Index pour la table `abonnement`
--
ALTER TABLE `abonnement`
  ADD PRIMARY KEY (`IDAbon`),
  ADD KEY `fk_jr` (`IdJr`),
  ADD KEY `fk_es` (`IdEs`);

--
-- Index pour la table `admin`
--
ALTER TABLE `admin`
  ADD PRIMARY KEY (`idAdmin`);

--
-- Index pour la table `annonce`
--
ALTER TABLE `annonce`
  ADD PRIMARY KEY (`IdAnnonce`),
  ADD KEY `fk_ed_an` (`Id_edition`),
  ADD KEY `fk_of_an` (`Id_offre`);

--
-- Index pour la table `categorie`
--
ALTER TABLE `categorie`
  ADD PRIMARY KEY (`IdCat`);

--
-- Index pour la table `demandeur`
--
ALTER TABLE `demandeur`
  ADD PRIMARY KEY (`CodeInterneD`);

--
-- Index pour la table `editions`
--
ALTER TABLE `editions`
  ADD PRIMARY KEY (`IdEd`),
  ADD KEY `fk_edition` (`journalEd`);

--
-- Index pour la table `emission`
--
ALTER TABLE `emission`
  ADD PRIMARY KEY (`IdEmis`),
  ADD KEY `fk_ab_em` (`ID_Ab`),
  ADD KEY `fk_of_em` (`ID_Offre`);

--
-- Index pour la table `entreprise`
--
ALTER TABLE `entreprise`
  ADD PRIMARY KEY (`CodeInterneEs`);

--
-- Index pour la table `journal`
--
ALTER TABLE `journal`
  ADD PRIMARY KEY (`CodeJr`),
  ADD KEY `fk_journal` (`categorie`);

--
-- Index pour la table `offre`
--
ALTER TABLE `offre`
  ADD PRIMARY KEY (`IdOffre`),
  ADD KEY `fk_offre_es` (`Id_Es`);

--
-- Index pour la table `postulation`
--
ALTER TABLE `postulation`
  ADD PRIMARY KEY (`IdPost`),
  ADD KEY `fk_post_dem` (`IdDem`),
  ADD KEY `fk_post_ann` (`IdAnn`);

--
-- Index pour la table `pref_demandeur`
--
ALTER TABLE `pref_demandeur`
  ADD PRIMARY KEY (`ID_Dem`,`ID_Cat`),
  ADD KEY `ID_Cat` (`ID_Cat`);

--
-- Index pour la table `pref_entreprise`
--
ALTER TABLE `pref_entreprise`
  ADD PRIMARY KEY (`ID_Es`,`ID_Cat`),
  ADD KEY `ID_Cat` (`ID_Cat`);

--
-- Index pour la table `recrutement`
--
ALTER TABLE `recrutement`
  ADD PRIMARY KEY (`IdDemd`,`IdOff`),
  ADD KEY `IdOff` (`IdOff`);

--
-- AUTO_INCREMENT pour les tables déchargées
--

--
-- AUTO_INCREMENT pour la table `abonnement`
--
ALTER TABLE `abonnement`
  MODIFY `IDAbon` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=18;

--
-- AUTO_INCREMENT pour la table `admin`
--
ALTER TABLE `admin`
  MODIFY `idAdmin` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT pour la table `annonce`
--
ALTER TABLE `annonce`
  MODIFY `IdAnnonce` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=21;

--
-- AUTO_INCREMENT pour la table `categorie`
--
ALTER TABLE `categorie`
  MODIFY `IdCat` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=10;

--
-- AUTO_INCREMENT pour la table `demandeur`
--
ALTER TABLE `demandeur`
  MODIFY `CodeInterneD` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=20;

--
-- AUTO_INCREMENT pour la table `editions`
--
ALTER TABLE `editions`
  MODIFY `IdEd` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=17;

--
-- AUTO_INCREMENT pour la table `emission`
--
ALTER TABLE `emission`
  MODIFY `IdEmis` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=14;

--
-- AUTO_INCREMENT pour la table `entreprise`
--
ALTER TABLE `entreprise`
  MODIFY `CodeInterneEs` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=21;

--
-- AUTO_INCREMENT pour la table `journal`
--
ALTER TABLE `journal`
  MODIFY `CodeJr` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=13;

--
-- AUTO_INCREMENT pour la table `offre`
--
ALTER TABLE `offre`
  MODIFY `IdOffre` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=14;

--
-- AUTO_INCREMENT pour la table `postulation`
--
ALTER TABLE `postulation`
  MODIFY `IdPost` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=14;

--
-- Contraintes pour les tables déchargées
--

--
-- Contraintes pour la table `abonnement`
--
ALTER TABLE `abonnement`
  ADD CONSTRAINT `fk_es` FOREIGN KEY (`IdEs`) REFERENCES `entreprise` (`CodeInterneEs`),
  ADD CONSTRAINT `fk_jr` FOREIGN KEY (`IdJr`) REFERENCES `journal` (`CodeJr`);

--
-- Contraintes pour la table `annonce`
--
ALTER TABLE `annonce`
  ADD CONSTRAINT `fk_ed_an` FOREIGN KEY (`Id_edition`) REFERENCES `editions` (`IdEd`),
  ADD CONSTRAINT `fk_of_an` FOREIGN KEY (`Id_offre`) REFERENCES `offre` (`IdOffre`);

--
-- Contraintes pour la table `editions`
--
ALTER TABLE `editions`
  ADD CONSTRAINT `fk_edition` FOREIGN KEY (`journalEd`) REFERENCES `journal` (`CodeJr`);

--
-- Contraintes pour la table `emission`
--
ALTER TABLE `emission`
  ADD CONSTRAINT `fk_ab_em` FOREIGN KEY (`ID_Ab`) REFERENCES `abonnement` (`IDAbon`),
  ADD CONSTRAINT `fk_of_em` FOREIGN KEY (`ID_Offre`) REFERENCES `offre` (`IdOffre`);

--
-- Contraintes pour la table `journal`
--
ALTER TABLE `journal`
  ADD CONSTRAINT `fk_Journal_Cat` FOREIGN KEY (`categorie`) REFERENCES `categorie` (`IdCat`) ON DELETE CASCADE,
  ADD CONSTRAINT `fk_journal` FOREIGN KEY (`categorie`) REFERENCES `categorie` (`IdCat`);

--
-- Contraintes pour la table `offre`
--
ALTER TABLE `offre`
  ADD CONSTRAINT `fk_offre_es` FOREIGN KEY (`Id_Es`) REFERENCES `entreprise` (`CodeInterneEs`);

--
-- Contraintes pour la table `postulation`
--
ALTER TABLE `postulation`
  ADD CONSTRAINT `fk_post_ann` FOREIGN KEY (`IdAnn`) REFERENCES `annonce` (`IdAnnonce`),
  ADD CONSTRAINT `fk_post_dem` FOREIGN KEY (`IdDem`) REFERENCES `demandeur` (`CodeInterneD`);

--
-- Contraintes pour la table `pref_demandeur`
--
ALTER TABLE `pref_demandeur`
  ADD CONSTRAINT `pref_demandeur_ibfk_1` FOREIGN KEY (`ID_Dem`) REFERENCES `demandeur` (`CodeInterneD`),
  ADD CONSTRAINT `pref_demandeur_ibfk_2` FOREIGN KEY (`ID_Cat`) REFERENCES `categorie` (`IdCat`);

--
-- Contraintes pour la table `pref_entreprise`
--
ALTER TABLE `pref_entreprise`
  ADD CONSTRAINT `pref_entreprise_ibfk_1` FOREIGN KEY (`ID_Es`) REFERENCES `entreprise` (`CodeInterneEs`),
  ADD CONSTRAINT `pref_entreprise_ibfk_2` FOREIGN KEY (`ID_Cat`) REFERENCES `categorie` (`IdCat`);

--
-- Contraintes pour la table `recrutement`
--
ALTER TABLE `recrutement`
  ADD CONSTRAINT `recrutement_ibfk_1` FOREIGN KEY (`IdDemd`) REFERENCES `demandeur` (`CodeInterneD`),
  ADD CONSTRAINT `recrutement_ibfk_2` FOREIGN KEY (`IdOff`) REFERENCES `offre` (`IdOffre`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
