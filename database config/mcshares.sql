-- phpMyAdmin SQL Dump
-- version 5.0.3
-- https://www.phpmyadmin.net/
--
-- Hôte : 127.0.0.1
-- Généré le : lun. 19 oct. 2020 à 13:55
-- Version du serveur :  10.4.14-MariaDB
-- Version de PHP : 7.2.34

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";

CREATE DATABASE IF NOT EXISTS mcshare_test;
use mcshare_test;
/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de données : `mcshares`
--

-- --------------------------------------------------------

--
-- Structure de la table `contact_details`
--

CREATE TABLE `contact_details` (
  `ID` int(11) NOT NULL,
  `Contact_Name` varchar(50) NOT NULL,
  `Contact_Number` varchar(30) DEFAULT NULL,
  `Customer_ID` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Déchargement des données de la table `contact_details`
--


-- --------------------------------------------------------

--
-- Structure de la table `customer`
--

CREATE TABLE `customer` (
  `ID` int(11) NOT NULL,
  `Customer_id` varchar(10) NOT NULL,
  `Customer_Type` varchar(20) NOT NULL,
  `Date_Of_Birth` varchar(25) DEFAULT NULL,
  `Date_Incorp` varchar(30) DEFAULT NULL,
  `Registration_No` varchar(25) DEFAULT NULL,
  `RequestDoc_Doc_Data` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Déchargement des données de la table `customer`
--


-- --------------------------------------------------------

--
-- Structure de la table `error`
--

CREATE TABLE `error` (
  `ID` int(11) NOT NULL,
  `Error_message` varchar(200) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Structure de la table `fileuploaded`
--

CREATE TABLE `fileuploaded` (
  `ID` int(11) NOT NULL,
  `DateUpload` datetime DEFAULT current_timestamp(),
  `Filename` varchar(150) DEFAULT NULL,
  `EncodedStringBase64` blob DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Déchargement des données de la table `fileuploaded`
--
 
-- --------------------------------------------------------

--
-- Structure de la table `mailing_address`
--

CREATE TABLE `mailing_address` (
  `ID` int(11) NOT NULL,
  `Address_Line1` varchar(40) NOT NULL,
  `Address_Line2` varchar(40) NOT NULL,
  `Town_City` varchar(40) DEFAULT NULL,
  `Country` varchar(40) DEFAULT NULL,
  `Customer_ID` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Déchargement des données de la table `mailing_address`
--


-- --------------------------------------------------------

--
-- Structure de la table `requestdoc`
--

CREATE TABLE `requestdoc` (
  `ID` int(11) NOT NULL,
  `Doc_Date` varchar(25) NOT NULL,
  `Doc_Ref` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Déchargement des données de la table `requestdoc`
--

-- --------------------------------------------------------

--
-- Structure de la table `shares_details`
--

CREATE TABLE `shares_details` (
  `ID` int(11) NOT NULL,
  `Num_Shares` varchar(10) NOT NULL,
  `Share_Price` varchar(20) NOT NULL,
  `Customer_ID` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Déchargement des données de la table `shares_details`
--

--
-- Index pour les tables déchargées
--

--
-- Index pour la table `contact_details`
--
ALTER TABLE `contact_details`
  ADD PRIMARY KEY (`ID`),
  ADD KEY `Customer_ID` (`Customer_ID`);

--
-- Index pour la table `customer`
--
ALTER TABLE `customer`
  ADD PRIMARY KEY (`ID`),
  ADD KEY `RequestDoc_Doc_Data` (`RequestDoc_Doc_Data`);

--
-- Index pour la table `error`
--
ALTER TABLE `error`
  ADD PRIMARY KEY (`ID`);

--
-- Index pour la table `fileuploaded`
--
ALTER TABLE `fileuploaded`
  ADD PRIMARY KEY (`ID`);

--
-- Index pour la table `mailing_address`
--
ALTER TABLE `mailing_address`
  ADD PRIMARY KEY (`ID`),
  ADD KEY `Customer_ID` (`Customer_ID`);

--
-- Index pour la table `requestdoc`
--
ALTER TABLE `requestdoc`
  ADD PRIMARY KEY (`ID`);

--
-- Index pour la table `shares_details`
--
ALTER TABLE `shares_details`
  ADD PRIMARY KEY (`ID`),
  ADD KEY `Customer_ID` (`Customer_ID`);

--
-- AUTO_INCREMENT pour les tables déchargées
--

--
-- AUTO_INCREMENT pour la table `contact_details`
--
ALTER TABLE `contact_details`
  MODIFY `ID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=71;

--
-- AUTO_INCREMENT pour la table `customer`
--
ALTER TABLE `customer`
  MODIFY `ID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=77;

--
-- AUTO_INCREMENT pour la table `error`
--
ALTER TABLE `error`
  MODIFY `ID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT pour la table `fileuploaded`
--
ALTER TABLE `fileuploaded`
  MODIFY `ID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- AUTO_INCREMENT pour la table `mailing_address`
--
ALTER TABLE `mailing_address`
  MODIFY `ID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=78;

--
-- AUTO_INCREMENT pour la table `requestdoc`
--
ALTER TABLE `requestdoc`
  MODIFY `ID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=70;

--
-- AUTO_INCREMENT pour la table `shares_details`
--
ALTER TABLE `shares_details`
  MODIFY `ID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=70;

--
-- Contraintes pour les tables déchargées
--

--
-- Contraintes pour la table `contact_details`
--
ALTER TABLE `contact_details`
  ADD CONSTRAINT `contact_details_ibfk_1` FOREIGN KEY (`Customer_ID`) REFERENCES `customer` (`ID`);

--
-- Contraintes pour la table `customer`
--
ALTER TABLE `customer`
  ADD CONSTRAINT `customer_ibfk_1` FOREIGN KEY (`RequestDoc_Doc_Data`) REFERENCES `requestdoc` (`ID`);

--
-- Contraintes pour la table `mailing_address`
--
ALTER TABLE `mailing_address`
  ADD CONSTRAINT `mailing_address_ibfk_1` FOREIGN KEY (`Customer_ID`) REFERENCES `customer` (`ID`);

--
-- Contraintes pour la table `shares_details`
--
ALTER TABLE `shares_details`
  ADD CONSTRAINT `shares_details_ibfk_1` FOREIGN KEY (`Customer_ID`) REFERENCES `customer` (`ID`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
