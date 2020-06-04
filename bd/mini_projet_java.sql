-- phpMyAdmin SQL Dump
-- version 5.0.2
-- https://www.phpmyadmin.net/
--
-- Hôte : localhost
-- Généré le : ven. 05 juin 2020 à 00:26
-- Version du serveur :  10.4.11-MariaDB
-- Version de PHP : 7.4.4

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de données : `mini_projet_java`
--

-- --------------------------------------------------------

--
-- Structure de la table `adresse`
--

CREATE TABLE `adresse` (
  `id` bigint(20) NOT NULL,
  `continent` enum('EUR','AME','AFR','ASI','AUS') NOT NULL,
  `pays` varchar(30) NOT NULL,
  `gouvernorat` varchar(40) NOT NULL,
  `ville` varchar(40) NOT NULL,
  `rue` varchar(60) NOT NULL,
  `codePostal` bigint(5) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Déchargement des données de la table `adresse`
--

INSERT INTO `adresse` (`id`, `continent`, `pays`, `gouvernorat`, `ville`, `rue`, `codePostal`) VALUES
(1, 'AFR', 'Bizerte', '5165159 rue rev el alia', 'El Alia', '5165159 rue rev el alia', 7016),
(11, 'AFR', 'Tunisia', 'zriba', 'el alia', 'zriba', 7016),
(12, 'AFR', 'Bizerte', 'Tunisia', 'El alia', 'zo4ba', 7016),
(14, 'AFR', 'Bizerte', 'Tunisia', 'el alia', 'zriba', 7016),
(15, 'AFR', 'Tunisia', 'tunisie', 'bizerte', 'fawara', 100),
(16, 'AFR', 'Tunisia', 'bizerte', 'el alia', 'fawara', 110),
(17, 'AFR', 'Tunisia', 'bizerte', 'el alia', 'fawara', 112),
(18, 'AFR', 'Tunisia', 'bizerte', 'el alia', 'fawara', 120),
(19, 'AFR', 'Tunisia', 'bizerte', 'el alia', 'tastour', 112),
(20, 'AFR', 'Tunisia', 'bizerte', 'el alia', 'fawara', 110),
(21, 'AFR', 'Tunisia', 'bizerte', 'el alia', 'tastour', 110),
(22, 'AFR', 'bizerte', 'tastour', 'el alia', 'tastour', 120),
(23, 'AFR', 'Tunisia', 'bizerte', 'el alia', 'el bi2a', 103);

-- --------------------------------------------------------

--
-- Structure de la table `agence`
--

CREATE TABLE `agence` (
  `id` bigint(20) NOT NULL,
  `adresse` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Structure de la table `carte`
--

CREATE TABLE `carte` (
  `numero` bigint(20) NOT NULL,
  `codeInternet` int(11) NOT NULL,
  `codeDab` int(11) NOT NULL,
  `valableJusqua` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Déchargement des données de la table `carte`
--

INSERT INTO `carte` (`numero`, `codeInternet`, `codeDab`, `valableJusqua`) VALUES
(1000000001, 12548796, 1254, '2020-06-01 00:09:01'),
(1000000003, 10000002, 1000, '2022-05-18 15:19:59'),
(1000000005, 10000005, 1003, '2024-05-17 21:44:18'),
(1000000006, 10000007, 1005, '2022-06-03 12:27:38'),
(1000000007, 10000008, 1006, '2024-06-02 14:58:29'),
(1000000010, 10000008, 1006, '2022-06-03 15:22:14'),
(1000000011, 10000008, 1006, '2024-06-02 15:23:51'),
(1000000012, 10000009, 1007, '2022-06-04 15:49:46'),
(1000000013, 10000010, 1008, '2022-06-04 16:24:06');

-- --------------------------------------------------------

--
-- Structure de la table `compte`
--

CREATE TABLE `compte` (
  `id` bigint(20) NOT NULL,
  `solde` double NOT NULL,
  `dateCreation` timestamp NOT NULL DEFAULT current_timestamp(),
  `dateFermeture` timestamp NULL DEFAULT NULL,
  `estValable` bit(1) NOT NULL DEFAULT b'1',
  `seuil` double NOT NULL,
  `TYPE` enum('COURANT','EPARGNE') NOT NULL,
  `client` bigint(20) DEFAULT NULL,
  `guichetier` bigint(20) DEFAULT NULL,
  `carte` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Déchargement des données de la table `compte`
--

INSERT INTO `compte` (`id`, `solde`, `dateCreation`, `dateFermeture`, `estValable`, `seuil`, `TYPE`, `client`, `guichetier`, `carte`) VALUES
(3, 1500, '2020-05-18 15:19:59', '2022-06-09 03:47:12', b'1', 50, 'COURANT', 7, 1, 1000000003),
(6, 1800, '2020-06-03 12:27:39', '2022-06-03 12:27:38', b'1', 50, 'COURANT', NULL, 1, 1000000006),
(7, 5000, '2020-06-03 14:58:29', '2022-06-03 12:27:38', b'1', 200, 'EPARGNE', 11, 1, 1000000007),
(8, 8200, '2020-06-03 15:22:15', '2022-06-03 15:22:14', b'1', 50, 'COURANT', 14, 1, 1000000010),
(9, 6000, '2020-06-03 15:23:51', '2024-06-02 15:23:51', b'1', 200, 'EPARGNE', 14, 1, 1000000011),
(10, 12000, '2020-06-04 15:49:46', '2022-06-04 15:49:46', b'1', 50, 'COURANT', 16, 1, 1000000012),
(11, 1900, '2020-06-04 16:24:06', '2022-06-04 16:24:06', b'1', 50, 'COURANT', 17, 1, 1000000013);

-- --------------------------------------------------------

--
-- Structure de la table `guichet`
--

CREATE TABLE `guichet` (
  `id` bigint(20) NOT NULL,
  `agence` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Structure de la table `operation`
--

CREATE TABLE `operation` (
  `id` int(11) NOT NULL,
  `type` enum('VER','RET','VIR') NOT NULL,
  `dateExec` timestamp NOT NULL DEFAULT current_timestamp(),
  `compteSource` bigint(20) DEFAULT NULL,
  `compteDestination` bigint(20) DEFAULT NULL,
  `createur` int(100) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Déchargement des données de la table `operation`
--

INSERT INTO `operation` (`id`, `type`, `dateExec`, `compteSource`, `compteDestination`, `createur`) VALUES
(1, 'VER', '2020-05-31 21:58:39', 3, NULL, 0),
(2, 'RET', '2020-05-31 21:59:19', 3, NULL, 0),
(3, 'VIR', '2020-05-31 22:00:19', NULL, 3, 0),
(4, 'VIR', '2020-05-31 22:02:35', 3, NULL, 0),
(5, 'VIR', '2020-05-31 22:03:07', NULL, 3, 0),
(6, 'VIR', '2020-05-31 22:03:34', NULL, 3, 0),
(7, 'RET', '2020-05-31 22:04:05', 3, NULL, 0),
(8, 'RET', '2020-06-01 00:46:40', 3, NULL, 0),
(9, 'RET', '2020-06-01 00:47:08', 3, NULL, 0),
(10, 'VER', '2020-06-03 19:31:09', 6, NULL, NULL),
(11, 'RET', '2020-06-03 19:32:58', 6, NULL, NULL),
(12, 'VIR', '2020-06-03 19:34:23', 6, 3, 1),
(13, 'VER', '2020-06-03 19:49:44', 6, NULL, 1),
(14, 'RET', '2020-06-03 19:50:13', 6, NULL, 1),
(15, 'RET', '2020-06-03 19:50:17', 6, NULL, 1),
(16, 'VIR', '2020-06-03 19:51:03', 6, 3, 1),
(17, 'VER', '2020-06-03 20:01:48', 6, NULL, 15),
(18, 'RET', '2020-06-03 20:02:00', 6, NULL, 15),
(19, 'VIR', '2020-06-03 20:02:13', 3, 6, 15),
(20, 'VER', '2020-06-04 21:23:38', 3, NULL, 1),
(21, 'VIR', '2020-06-04 21:23:50', 3, 6, 1);

-- --------------------------------------------------------

--
-- Structure de la table `personne`
--

CREATE TABLE `personne` (
  `id` bigint(20) NOT NULL,
  `email` varchar(50) NOT NULL,
  `password` varchar(50) NOT NULL,
  `cin` bigint(20) NOT NULL,
  `nom` varchar(50) NOT NULL,
  `address` bigint(20) DEFAULT NULL,
  `prenom` varchar(50) NOT NULL,
  `dateNaiss` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp(),
  `salaire` double DEFAULT NULL,
  `dateEmbauche` timestamp NULL DEFAULT NULL,
  `TYPE` enum('GUICHETIER','CLIENT') NOT NULL,
  `guichet` bigint(20) DEFAULT NULL,
  `tel` varchar(13) NOT NULL,
  `sex` enum('MAL','FEM') NOT NULL DEFAULT 'MAL',
  `etatCivil` enum('MAR','DIV','SING') NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Déchargement des données de la table `personne`
--

INSERT INTO `personne` (`id`, `email`, `password`, `cin`, `nom`, `address`, `prenom`, `dateNaiss`, `salaire`, `dateEmbauche`, `TYPE`, `guichet`, `tel`, `sex`, `etatCivil`) VALUES
(1, 'nasreddinbenhamouda@gmail.com', 'hamzawi1996', 11394842, 'Ben Hamouda ', 1, 'Nasreddine', '2020-05-31 22:00:00', 1200, '2020-05-11 22:00:00', 'GUICHETIER', NULL, '+21654808162', 'MAL', 'DIV'),
(7, 'anouar@gmail.com', 'azerty12', 11988755, 'Ben hamza', 11, 'anouar', '2020-05-31 22:00:00', NULL, NULL, 'CLIENT', NULL, '+21654879521', 'MAL', 'DIV'),
(11, 'mouin@gmail.com', '96E7V1IG', 12458798, 'lahbib', 15, 'mouin', '1997-06-09 22:00:00', NULL, NULL, 'CLIENT', NULL, '+21654879632', 'MAL', 'DIV'),
(14, 'emna@gmail.com', 'LXU48OJI', 11258974, 'lahbib', 18, 'emna', '1999-06-06 22:00:00', NULL, NULL, 'CLIENT', NULL, '+21658963258', 'FEM', 'DIV'),
(16, 'nour@gmail.com', 'HO6G95OD', 11548237, 'limem', 19, 'nour', '1997-06-18 22:00:00', NULL, NULL, 'CLIENT', NULL, '+21657623942', 'FEM', 'DIV'),
(17, 'sami@gmail.com', 'XG8A25G5', 54875269, 'limem', 20, 'sami', '1993-06-15 22:00:00', NULL, NULL, 'CLIENT', NULL, '+21659874621', 'MAL', 'DIV'),
(18, 'saber@gmail.com', '4T90A9A2', 11659875, 'ben hamouda', 21, 'saber', '2020-06-04 17:39:38', 1000, '2020-06-11 17:39:35', 'GUICHETIER', NULL, '+21698756325', 'MAL', 'DIV'),
(19, 'donya@gmail.com', 'BFXEA8EW', 11259865, 'ben hamouda', 22, 'donya', '1990-06-09 20:00:00', 1500, '2019-06-23 22:00:00', 'GUICHETIER', NULL, '+12658963254', 'FEM', 'DIV'),
(20, 'radhia@gmail.com', '8G2JZ188', 15879632, 'ben hamouda', 23, 'radhia', '1983-06-15 22:00:00', 55555, '2010-06-24 22:00:00', 'GUICHETIER', NULL, '+21658963258', 'MAL', 'DIV');

--
-- Index pour les tables déchargées
--

--
-- Index pour la table `adresse`
--
ALTER TABLE `adresse`
  ADD PRIMARY KEY (`id`);

--
-- Index pour la table `agence`
--
ALTER TABLE `agence`
  ADD PRIMARY KEY (`id`),
  ADD KEY `adresse` (`adresse`);

--
-- Index pour la table `carte`
--
ALTER TABLE `carte`
  ADD PRIMARY KEY (`numero`);

--
-- Index pour la table `compte`
--
ALTER TABLE `compte`
  ADD PRIMARY KEY (`id`),
  ADD KEY `client` (`client`),
  ADD KEY `guichetier` (`guichetier`),
  ADD KEY `carte` (`carte`);

--
-- Index pour la table `guichet`
--
ALTER TABLE `guichet`
  ADD PRIMARY KEY (`id`),
  ADD KEY `agence` (`agence`);

--
-- Index pour la table `operation`
--
ALTER TABLE `operation`
  ADD PRIMARY KEY (`id`),
  ADD KEY `compteSource` (`compteSource`),
  ADD KEY `compteDestination` (`compteDestination`);

--
-- Index pour la table `personne`
--
ALTER TABLE `personne`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `cin` (`cin`),
  ADD UNIQUE KEY `password` (`password`),
  ADD KEY `guichet` (`guichet`),
  ADD KEY `address` (`address`);

--
-- AUTO_INCREMENT pour les tables déchargées
--

--
-- AUTO_INCREMENT pour la table `adresse`
--
ALTER TABLE `adresse`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=24;

--
-- AUTO_INCREMENT pour la table `agence`
--
ALTER TABLE `agence`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT pour la table `carte`
--
ALTER TABLE `carte`
  MODIFY `numero` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=1000000014;

--
-- AUTO_INCREMENT pour la table `compte`
--
ALTER TABLE `compte`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=12;

--
-- AUTO_INCREMENT pour la table `guichet`
--
ALTER TABLE `guichet`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT pour la table `operation`
--
ALTER TABLE `operation`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=22;

--
-- AUTO_INCREMENT pour la table `personne`
--
ALTER TABLE `personne`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=21;

--
-- Contraintes pour les tables déchargées
--

--
-- Contraintes pour la table `agence`
--
ALTER TABLE `agence`
  ADD CONSTRAINT `agence_ibfk_1` FOREIGN KEY (`adresse`) REFERENCES `adresse` (`id`) ON DELETE SET NULL ON UPDATE CASCADE;

--
-- Contraintes pour la table `compte`
--
ALTER TABLE `compte`
  ADD CONSTRAINT `compte_ibfk_2` FOREIGN KEY (`client`) REFERENCES `personne` (`id`) ON DELETE SET NULL ON UPDATE CASCADE,
  ADD CONSTRAINT `compte_ibfk_4` FOREIGN KEY (`guichetier`) REFERENCES `personne` (`id`) ON DELETE SET NULL ON UPDATE CASCADE,
  ADD CONSTRAINT `compte_ibfk_5` FOREIGN KEY (`carte`) REFERENCES `carte` (`numero`) ON DELETE SET NULL ON UPDATE CASCADE;

--
-- Contraintes pour la table `guichet`
--
ALTER TABLE `guichet`
  ADD CONSTRAINT `guichet_ibfk_1` FOREIGN KEY (`agence`) REFERENCES `agence` (`id`) ON DELETE SET NULL ON UPDATE CASCADE;

--
-- Contraintes pour la table `operation`
--
ALTER TABLE `operation`
  ADD CONSTRAINT `operation_ibfk_1` FOREIGN KEY (`compteSource`) REFERENCES `compte` (`id`) ON DELETE SET NULL ON UPDATE CASCADE,
  ADD CONSTRAINT `operation_ibfk_2` FOREIGN KEY (`compteDestination`) REFERENCES `compte` (`id`) ON DELETE SET NULL ON UPDATE CASCADE;

--
-- Contraintes pour la table `personne`
--
ALTER TABLE `personne`
  ADD CONSTRAINT `personne_ibfk_1` FOREIGN KEY (`guichet`) REFERENCES `guichet` (`id`) ON DELETE SET NULL ON UPDATE CASCADE,
  ADD CONSTRAINT `personne_ibfk_2` FOREIGN KEY (`address`) REFERENCES `adresse` (`id`) ON DELETE SET NULL ON UPDATE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
