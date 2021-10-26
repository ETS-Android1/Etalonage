-- phpMyAdmin SQL Dump
-- version 5.1.0
-- https://www.phpmyadmin.net/
--
-- Hôte : 127.0.0.1
-- Généré le : jeu. 17 juin 2021 à 23:36
-- Version du serveur :  10.4.19-MariaDB
-- Version de PHP : 8.0.6

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de données : `eleonetech`
--

-- --------------------------------------------------------

--
-- Structure de la table `certif`
--

CREATE TABLE `certif` (
  `id` int(11) NOT NULL,
  `code_equipement` varchar(30) NOT NULL,
  `certificat` varchar(500) NOT NULL,
  `date_certificat` date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Déchargement des données de la table `certif`
--

INSERT INTO `certif` (`id`, `code_equipement`, `certificat`, `date_certificat`) VALUES
(1, 'BTUECME_0301_E', 'certifs//BTUECME_0301_E-305-05-02-21.pdf', '2021-02-05'),
(2, 'BTUECME_0301_E', 'certifs//BTUECME_0301_E-305S-05-02-21.pdf', '2021-02-05'),
(3, 'BTUECME_0373_T', 'certifs//BTUECME_0373_T-380-11-03-21 (00000002).pdf', '2021-03-11'),
(4, 'BTUECME_0400_T', 'certifs//BTUECME_0400_T-400-05-02-21.pdf', '2021-02-05'),
(5, 'BTUECME_0401_E', 'certifs//BTUECME_0401_E-300S-05-02-21.pdf', '2021-02-05'),
(6, 'BTUECME_0402_E', 'certifs//BTUECME_0402_E-301-05-02-21.pdf', '2021-02-05'),
(7, 'BTUECME_0403_E', 'certifs//BTUECME_0403_E-302-05-02-21.pdf', '2021-02-05'),
(8, 'BTUECME_0405_E', 'certifs//BTUECME_0405_E-303-05-02-21.pdf', '2021-02-05'),
(9, 'BTUECME_0406_E', 'certifs//BTUECME_0406_E-304-05-02-21.pdf', '2021-02-05');

-- --------------------------------------------------------

--
-- Structure de la table `equipement`
--

CREATE TABLE `equipement` (
  `Code_equipement` varchar(30) NOT NULL,
  `Description_equipement` varchar(49) DEFAULT NULL,
  `Date_fin_validite` date DEFAULT NULL,
  `Emplacement` varchar(50) DEFAULT NULL,
  `Responsable` varchar(17) DEFAULT NULL,
  `Num_serie` varchar(21) DEFAULT NULL,
  `Date_mise_en_service` date DEFAULT NULL,
  `Date_creation` date DEFAULT NULL,
  `Num_modele` varchar(37) DEFAULT NULL,
  `Etat` varchar(4) DEFAULT NULL,
  `Niveau` varchar(6) DEFAULT NULL,
  `Zone` varchar(4) DEFAULT NULL,
  `Fonction` varchar(8) DEFAULT NULL,
  `Famille` varchar(7) DEFAULT NULL,
  `Centre_de_charge` varchar(17) DEFAULT NULL,
  `ECME` varchar(4) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Déchargement des données de la table `equipement`
--

INSERT INTO `equipement` (`Code_equipement`, `Description_equipement`, `Date_fin_validite`, `Emplacement`, `Responsable`, `Num_serie`, `Date_mise_en_service`, `Date_creation`, `Num_modele`, `Etat`, `Niveau`, `Zone`, `Fonction`, `Famille`, `Centre_de_charge`, `ECME`) VALUES
('BTUECME005', 'BNL-HV UBA3 / External connection /TAxxx / PS Mod', NULL, 'SECTION FINITION', 'BEN FADHEL Faouzi', '9608010', '2004-05-12', '2019-12-16', '', 'V', '1', 'BAT', 'BAT001', 'BAT', '1511', 'Oui'),
('BTUECME04', 'EOT-Multimètre numérique', '2022-05-30', 'DEPANNAGE', 'BEN FADHEL Faouzi', '1301819', '2011-03-18', '2019-12-16', '', 'A', '1', 'BAT', 'BAT001', 'BAT', '2307', 'Oui'),
('BTUECME06', 'Thermo hygromètre', '2021-07-03', 'MAINTENANCE', 'BEN FADHEL Faouzi', '', '2013-10-10', '2019-11-20', '', 'A', '1', 'BAT', 'BAT001', 'BAT', '2504', 'Oui'),
('BTUECME09', 'EOT_WELLER WCB2', '2021-06-04', 'Maintenance', 'BEN FADHEL Faouzi', '53118099 / 0058748065', '2006-07-08', '2019-12-16', 'WELLER_WCB 2 / K', 'A', '1', 'BAT', 'BAT001', 'BAT', '1511', 'Oui'),
('BTUECME104', 'LCR Mètre', '2021-06-08', 'DEPANNAGE', 'BEN FADHEL Faouzi', 'NA', '2018-04-01', '2019-12-16', 'Metrix/TCXOI', 'A', '1', 'BAT', 'BAT001', 'BAT', '2310', 'Oui'),
('BTUECME105', 'Thermo Hygrométre', '2021-06-11', 'Intégration', 'BEN FADHEL Faouzi', '', '2012-03-02', '2019-12-16', 'Temp: In -10 à 50°C / Out :-50 à 70°C', 'A', '1', 'BAT', 'BAT001', 'BAT', '1511', 'Oui'),
('BTUECME113', 'Enceinte climatique', '2021-12-15', 'CMS', 'BEN FADHEL Faouzi', 'AA09082358', '2010-01-01', '2019-12-16', 'T40w-1200-6', 'A', '1', 'CMS', 'BAT001', 'BAT', '2301', 'Oui'),
('BTUECME114', 'Multimetre numerique', '2021-06-06', 'FCT X98 Caman', 'BEN FADHEL Faouzi', 'MY47022842', '2012-01-01', '2019-12-16', '34410A', 'A', '1', 'BAT', 'BAT001', 'BAT', '2310', 'Oui'),
('BTUECME115', 'Multimetre numerique', '2021-06-06', 'FCT X98 Caman', 'BEN FADHEL Faouzi', 'MY47022349', '2012-01-01', '2019-12-16', '34410A', 'A', '1', 'BAT', 'BAT001', 'BAT', '2310', 'Oui'),
('BTUECME116', 'WATT METER', '2021-06-11', 'FCT BEC V3', 'BEN FADHEL Faouzi', '13241831', '2012-12-01', '2019-12-16', 'H8115-2', 'A', '1', 'BAT', 'BAT001', 'BAT', '2310', 'Oui'),
('BTUECME117', 'WATT METER', '2021-06-11', 'FCT BEC V3', 'BEN FADHEL Faouzi', '2717249', '2012-12-01', '2019-12-16', 'HM 8115', 'A', '1', 'BAT', 'BAT001', 'BAT', '2310', 'Oui'),
('BTUECME118', 'Multimetre Numerique', '2021-06-11', 'FCT BEC V3', 'BEN FADHEL Faouzi', 'US36095896', '2012-12-01', '2019-12-16', '34401A', 'A', '1', 'BAT', 'BAT001', 'BAT', '2310', 'Oui'),
('BTUECME119', 'Comparateur a tige rentrante radiale', '2021-06-11', 'METHODE', 'BEN FADHEL Faouzi', '11573', '2012-01-01', '2019-12-16', 'ID-S1012', 'A', '1', 'BAT', 'BAT001', 'BAT', '1511', 'Oui'),
('BTUECME121', 'Compteur de composant', '2021-03-03', 'MAG', 'WISSEM DAKWANI', '221178', '2013-01-22', '2019-12-16', 'County 8301.072', 'P', '1', 'MAG', 'BAT001', 'BAT', '1511', 'Oui'),
('BTUECME122', 'Electrostatic Fieldmeter', '2021-06-11', 'Maintenance', 'BEN FADHEL Faouzi', '', '2013-01-29', '2019-12-16', 'EOS2001', 'A', '1', 'BAT', 'BAT001', 'BAT', '1511', 'Oui'),
('BTUECME132', 'Multimètre du banc BMW', '2021-08-13', 'MEZANNINE', 'BEN FADHEL Faouzi', 'MY47060093', '2013-08-01', '2019-12-16', 'AGILENT 34401A', 'A', '1', 'BAT', 'BAT001', 'BAT', '2307', 'Oui'),
('BTUECME133', 'Pied à coulisse numérique', '2021-07-03', 'Mesure CML T001', 'BEN FADHEL Faouzi', '', '2013-07-10', '2019-12-16', '', 'A', '1', 'BAT', 'BAT001', 'BAT', '1511', 'Oui'),
('BTUECME137', 'Plaque chauffante Weller', '2021-03-03', 'Finition', 'WISSEM DAKWANI', '', '2013-09-09', '2019-12-16', 'Weller - WHP3000', 'V', '1', 'BAT', 'BAT001', 'BAT', '2307', 'Oui'),
('BTUECME138', 'Thermohygromètre', '2021-12-14', 'Magasin', 'BEN FADHEL Faouzi', 'NA', '2013-10-10', '2019-12-16', 'NA', 'A', '1', 'BAT', 'BAT001', 'BAT', '1511', 'Oui'),
('BTUECME14', 'BSE-Multimètre numérique', '0000-00-00', 'TES CONVERTISSEUR', 'BEN FADHEL Faouzi', '2048965', '0000-00-00', '2019-12-16', 'TENMA_72-2055', 'R', '1', 'BAT', 'BAT001', 'BAT', '2310', 'Oui'),
('BTUECME141', 'Thermomètre infrarouge', '2021-08-03', 'Maintenance', 'BEN FADHEL Faouzi', '1131060863', '2014-05-01', '2019-12-16', 'IR-730-EUR', 'A', '1', 'BAT', 'BAT001', 'BAT', '1511', 'Oui'),
('BTUECME146', 'LCR METER HANDHELD', '2021-08-27', 'Méthode', 'BEN FADHEL Faouzi', 'MY54120013', '2014-07-01', '2019-12-16', 'U1731C', 'A', '1', 'BAT', 'BAT001', 'BAT', '1511', 'Oui'),
('BTUECME147', 'Tensiometer G-CHECK', '2021-12-11', 'Méthode', 'BEN FADHEL Faouzi', '617370', '2014-07-01', '2019-12-16', '', 'A', '1', 'BAT', 'BAT001', 'BAT', '1511', 'Oui'),
('BTUECME15', 'EOT_Chronomètre', '2021-06-06', 'METHODE', 'BEN FADHEL Faouzi', 'IN', '2009-07-08', '2019-12-16', 'XL-009A', 'A', '1', 'BAT', 'BAT001', 'BAT', '1511', 'Oui'),
('BTUECME153', 'Multimètre numérique', '2021-07-08', 'Testeur Y.X', 'BEN FADHEL Faouzi', '112.798.2', '2008-06-01', '2019-12-16', '', 'A', '1', 'BAT', 'BAT001', 'BAT', '2310', 'Oui'),
('BTUECME157', 'Multimètre numérique', '2021-07-08', 'Testeurx98RS', 'BEN FADHEL Faouzi', 'MY52250025', '2012-09-01', '2019-12-16', '344005A', 'A', '1', 'BAT', 'BAT001', 'BAT', '2310', 'Oui'),
('BTUECME158', 'Multimètre numérique', '2021-12-11', 'Fini F-VS20 Kroschu', 'BEN FADHEL Faouzi', 'MY47035395', '2012-12-21', '2019-12-16', 'AGILENT34401A', 'A', '1', 'BAT', 'BAT001', 'BAT', '2307', 'Oui'),
('BTUECME162', 'Alimentation stabilisée', '2021-10-24', 'Testeur BMW', 'BEN FADHEL Faouzi', '347363', '2014-07-20', '2019-12-16', 'pl 155-p', 'A', '1', 'BAT', 'BAT001', 'BAT', '2310', 'Oui'),
('BTUECME163', 'Multimetre Numerique', '2021-07-02', 'Testeur SIAM', 'BEN FADHEL Faouzi', 'US36103290', '2014-07-20', '2019-12-16', '34401A', 'A', '1', 'BAT', 'BAT001', 'BAT', '2310', 'Oui'),
('BTUECME164', 'Oscilloscope Numerique', NULL, 'Testeur Canon', 'BEN FADHEL Faouzi', '25D035G1', '2015-07-10', '2019-12-16', '', 'R', '1', 'BAT', 'BAT001', 'BAT', '2310', 'Oui'),
('BTUECME165', 'Multimetre Numérique', NULL, 'Testeur Canon', 'BEN FADHEL Faouzi', 'My49439019', '2015-07-10', '2019-12-16', '', 'R', '1', 'BAT', 'BAT001', 'BAT', '2310', 'Oui'),
('BTUECME166', 'Testeur ESD tourniquet', '3000-01-01', 'ADMINISTRATION', 'BEN FADHEL Faouzi', 'WST100', '2015-06-01', '2019-11-20', 'WST-100-2015', 'A', '1', 'BAT', 'ESD', 'BAT', '1511', 'Oui'),
('BTUECME167', 'Testeur ESD tourniquet', '2021-05-31', 'Entrée personnel', 'WISSEM DAKWANI', '', '2015-06-01', '2019-12-16', 'CT-8720', 'P', '1', 'BAT', 'ESD', 'BAT', '1511', 'Oui'),
('BTUECME168', 'Testeur ESD Tourniquet', '2021-05-31', 'Entrée personel', 'WISSEM DAKWANI', '', '2015-06-01', '2019-12-16', 'WST100', 'P', '1', 'BAT', 'ESD', 'BAT', '1511', 'Oui'),
('BTUECME169', 'Alimentation programmable', '2021-10-24', 'EOT', 'BEN FADHEL Faouzi', '430517', '2015-09-01', '2019-12-16', 'EL302P Programmable PSU 30V.2A', 'A', '1', 'BAT', 'BAT001', 'BAT', '2310', 'Oui'),
('BTUECME170', 'Alimentation programmable', '2021-10-24', 'EOT', 'BEN FADHEL Faouzi', '430516', '2015-09-01', '2019-12-16', 'EL302P ProgrammablePSU 30V.2A', 'A', '1', 'BAT', 'BAT001', 'BAT', '2310', 'Oui'),
('BTUECME171', 'Alimentation programmable', '2021-10-24', 'EOT', 'BEN FADHEL Faouzi', '430510', '2015-09-01', '2019-12-16', 'EL302P 30V.2A', 'A', '1', 'BAT', 'BAT001', 'BAT', '2310', 'Oui'),
('BTUECME172', 'Alimentation', '2021-12-11', 'Enceinte CMS', 'BEN FADHEL Faouzi', '370393', '2015-09-01', '2019-12-16', 'CPX400SP', 'A', '1', 'BAT', 'BAT001', 'BAT', '2310', 'Oui'),
('BTUECME173', 'Multimètre numérique', '2021-09-21', 'Enceinte CMS', 'BEN FADHEL Faouzi', 'MY47027028', '2015-09-01', '2019-12-16', '34410A', 'A', '1', 'BAT', 'BAT001', 'BAT', '2310', 'Oui'),
('BTUECME175', 'MULTIMETRE NUMERIQUE', '2021-09-23', 'Testeur Fileri', 'BEN FADHEL Faouzi', 'SG470001508', '2012-09-01', '2019-12-16', '34401A', 'A', '1', 'BAT', 'BAT001', 'BAT', '2310', 'Oui'),
('BTUECME176', 'OCCILOSCOPE', NULL, 'BSE-F-29-ADEUNIS', 'BEN FADHEL Faouzi', '829682/012', '2010-09-01', '2019-12-16', '', 'R', '1', 'BAT', 'BAT001', 'BAT', '2310', 'Oui'),
('BTUECME177', 'Multimètre numérique', NULL, 'BSE-F-29-ADEUNIS', 'BEN FADHEL Faouzi', 'MY47016014', '2010-09-01', '2019-12-16', '', 'R', '1', 'BAT', 'BAT001', 'BAT', '2310', 'Oui'),
('BTUECME178', 'Alimentation', NULL, 'BSE-F-29-ADEUNIS', 'BEN FADHEL Faouzi', 'E3631A', '2010-09-01', '2019-12-16', '', 'R', '1', 'BAT', 'BAT001', 'BAT', '2310', 'Oui'),
('BTUECME179', 'Générateur basse fréquence', NULL, 'BSE-F-29-ADEUNIS', 'BEN FADHEL Faouzi', 'VS36048240', '2010-09-01', '2019-12-16', '', 'R', '1', 'BAT', 'BAT001', 'BAT', '2310', 'Oui'),
('BTUECME180', 'Multimètre numérique', '2021-06-11', 'Zeiffer testeur', 'BEN FADHEL Faouzi', 'MY54500105', '2015-03-01', '2019-12-16', '34450A', 'A', '1', 'BAT', 'BAT001', 'BAT', '2310', 'Oui'),
('BTUECME182', 'ALIMENTATION', '2021-10-24', 'Testeur Starvox', 'BEN FADHEL Faouzi', 'MY50010007', '2014-01-24', '2019-12-16', 'E3646A', 'A', '1', 'BAT', 'BAT001', 'BAT', '2310', 'Oui'),
('BTUECME183', 'Analyseur de spectre STARVOX', '2021-10-24', 'Testeur starvox', 'BEN FADHEL Faouzi', '55300008', '2014-01-24', '2019-12-16', 'Spectrum Analyzer HM5530', 'A', '1', 'BAT', 'BAT001', 'BAT', '2310', 'Oui'),
('BTUECME184', 'Programmable Synthesizer', '2021-09-21', 'Testeur starvox', 'BEN FADHEL Faouzi', '13265166', '2014-01-24', '2019-12-16', 'HM8134-3', 'A', '1', 'BAT', 'BAT001', 'BAT', '2310', 'Oui'),
('BTUECME185', 'COMPTEUR PROGRAMMABLE', '2021-09-21', 'Testeur starvox', 'BEN FADHEL Faouzi', '54570005', '2014-01-24', '2019-12-16', 'HM8123', 'A', '1', 'BAT', 'BAT001', 'BAT', '2310', 'Oui'),
('BTUECME190', 'Alimentation', '2021-10-24', 'testeur adeunis', 'BEN FADHEL Faouzi', 'MY40025334', '2010-09-01', '2019-12-16', 'E3631A', 'A', '1', 'BAT', 'BAT001', 'BAT', '2310', 'Oui'),
('BTUECME191', 'BSE Multimètre numérique', '2021-10-24', 'TEST (Varferro)', 'BEN FADHEL Faouzi', '18990038', '2015-01-01', '2019-12-16', '177', 'A', '1', 'BAT', 'BAT001', 'BAT', '2310', 'Oui'),
('BTUECME197', 'ALIMENTATION', NULL, 'ADEUNIS TESTEUR', 'BEN FADHEL Faouzi', '366165', '2015-01-01', '2019-12-16', '', 'R', '1', 'BAT', 'BAT001', 'BAT', '2310', 'Oui'),
('BTUECME198', 'WATTMètre', '2021-10-22', 'Testeur VARFERRO BSE', 'BEN FADHEL Faouzi', '15298900', '2015-01-01', '2019-12-16', 'HM8115-2', 'A', '1', 'BAT', 'BAT001', 'BAT', '2310', 'Oui'),
('BTUECME199', 'Multimètre numérique', '2021-07-09', 'BANC TEST BSE 084', 'BEN FADHEL Faouzi', 'NA', '2004-08-23', '2019-12-16', 'FL30 Française d instrumentation', 'A', '1', 'BAT', 'BAT001', 'BAT', '2310', 'Oui'),
('BTUECME20', 'Alimentation stabilisée', NULL, 'DEPANNAGE', 'BEN FADHEL Faouzi', '', '0000-00-00', '2019-12-16', '', 'R', '1', 'BAT', 'BAT001', 'BAT', '1511', 'Oui'),
('BTUECME200', 'ALIMENTATION', '2021-12-11', 'TEST', 'BEN FADHEL Faouzi', '344794', '2014-07-20', '2019-12-16', 'PL155-P', 'A', '1', 'BAT', 'BAT001', 'BAT', '2310', 'Oui'),
('BTUECME201', 'Multimètre numérique', '2021-07-02', 'dépannage', 'BEN FADHEL Faouzi', 'MY45026230', '2015-12-01', '2019-12-16', '34401A', 'A', '1', 'BAT', 'BAT001', 'BAT', '1511', 'Oui'),
('BTUECME21', 'EOT_Mesureur de surface', '2021-08-13', 'TEST', 'BEN FADHEL Faouzi', 'NA', '2005-09-21', '2019-12-16', 'EUROSTAT', 'A', '1', 'BAT', 'BAT001', 'BAT', '2310', 'Oui'),
('BTUECME22', 'TESTEUR DE SOL ESD', '2021-10-23', 'maintenance', 'BEN FADHEL Faouzi', 'NA', '2005-09-20', '2019-12-16', 'SRM-100/3_EN 100 015', 'A', '1', 'BAT', 'ESD', 'BAT', '1511', 'Oui'),
('BTUECME223', 'Alimentation stabilisée', '2021-09-01', 'Testeur W097 CML', 'BEN FADHEL Faouzi', '432920', '2016-01-25', '2019-12-16', '', 'A', '1', 'BAT', 'BAT001', 'BAT', '2310', 'Oui'),
('BTUECME224', 'Multimetre numerique', '2021-07-02', 'Testeur W097 CML', 'BEN FADHEL Faouzi', 'MY53014359', '2016-01-25', '2019-12-16', '34401A', 'A', '1', 'BAT', 'BAT001', 'BAT', '2310', 'Oui'),
('BTUECME225', 'Multimetre numerique', '2021-07-08', 'Rolls Royce', 'BEN FADHEL Faouzi', 'MY56130087', '2016-06-21', '2019-12-16', '34450A', 'A', '1', 'BAT', 'BAT001', 'BAT', '2310', 'Oui'),
('BTUECME226', 'Multimetre numerique', NULL, 'FCT ELECTROLUX', 'BEN FADHEL Faouzi', 'US36037851', '2015-11-18', '2019-12-16', '', 'R', '1', 'BAT', 'BAT001', 'BAT', '2310', 'Oui'),
('BTUECME227', 'PH METER', '2021-07-03', 'Cadre vague', 'BEN FADHEL Faouzi', 'HI98103', '2016-07-29', '2019-12-16', 'PH METER Portatif', 'A', '1', 'BAT', 'BAT001', 'BAT', '2307', 'Oui'),
('BTUECME232', 'Multimetre numerique', '2021-07-08', 'SWS MO', 'BEN FADHEL Faouzi', 'MY47027012', '2016-07-29', '2019-12-16', '34410A-AGILENT', 'A', '1', 'BAT', 'BAT001', 'BAT', '2310', 'Oui'),
('BTUECME236', 'Duromètre', '2021-07-03', 'MAINTENANCE', 'BEN FADHEL Faouzi', '8020163', '2016-01-01', '2019-12-16', 'PCE-DD-A', 'A', '1', 'BAT', 'BAT001', 'BAT', '2310', 'Oui'),
('BTUECME237', 'Wattmeter', '2021-07-03', 'Testeur becled', 'BEN FADHEL Faouzi', '', '2016-05-01', '2019-12-16', 'Metrix model PX110', 'A', '1', 'BAT', 'BAT001', 'BAT', '2310', 'Oui'),
('BTUECME238', 'Multimetre numerique', '2021-07-02', 'Testeur becled', 'BEN FADHEL Faouzi', '1425000', '2016-05-01', '2019-12-16', '2110 51/2 KEITHLEY', 'A', '1', 'BAT', 'BAT001', 'BAT', '2310', 'Oui'),
('BTUECME241', 'Alimentation stabilisée', '2021-07-09', 'Testeur Conteneur', 'BEN FADHEL Faouzi', 'NA', '2003-01-01', '2019-12-16', '6612C', 'A', '1', 'BAT', 'BAT001', 'BAT', '2310', 'Oui'),
('BTUECME243', 'Alimentation stabilisée', '2021-07-09', 'Testeur Conteneur', 'BEN FADHEL Faouzi', 'MY40002697', '2003-01-01', '2019-12-16', 'EL3642A', 'A', '1', 'BAT', 'BAT001', 'BAT', '2310', 'Oui'),
('BTUECME244', 'Multimètre numérique', '2021-06-03', 'ELEMASTER/SMEG', 'BEN FADHEL Faouzi', 'GEQ883225', '2017-03-15', '2019-12-16', 'G WINSTEK GDM-8341', 'A', '1', 'BAT', 'BAT001', 'BAT', '2310', 'Oui'),
('BTUECME245', 'Balance Electronique', '2021-06-12', 'Magasin-reception', 'BEN FADHEL Faouzi', '7123104T3011', '2010-01-20', '2019-12-16', 'JCE-3K', 'A', '1', 'BAT', 'BAT001', 'BAT', '1511', 'Oui'),
('BTUECME247', 'DATA PAQ Q18', '2021-06-04', 'CMS', 'BEN FADHEL Faouzi', '374406', '2017-02-01', '2019-12-16', 'DQ1860C', 'A', '1', 'CMS', 'BAT001', 'BAT', '2301', 'Oui'),
('BTUECME248', 'LCR Mètre', '2021-06-05', 'CMS', 'BEN FADHEL Faouzi', 'N179789KMC', '2017-04-15', '2019-12-16', 'TCXOI', 'A', '1', 'CMS', 'BAT001', 'BAT', '2301', 'Oui'),
('BTUECME249', 'LCR Mètre', '2021-06-05', 'CMS', 'BEN FADHEL Faouzi', 'N103061MBC', '2017-04-15', '2019-12-16', 'TCXOI', 'A', '1', 'CMS', 'BAT001', 'BAT', '2301', 'Oui'),
('BTUECME251', 'Multimètre numérique', '2021-06-03', 'DEPANNAGE', 'BEN FADHEL Faouzi', '36761055WS', '2017-06-10', '2019-12-16', '', 'A', '1', 'BAT', 'BAT001', 'BAT', '2307', 'Oui'),
('BTUECME252', 'Testeur mesure de consommation', '2021-06-11', 'CML', 'BEN FADHEL Faouzi', '111642', '2017-01-01', '2019-12-16', 'AL-C701A', 'A', '1', 'BAT', 'BAT001', 'BAT', '2310', 'Oui'),
('BTUECME253', 'Testeur mesure de consommation', '2021-06-11', 'CML', 'BEN FADHEL Faouzi', '110851', '2016-01-01', '2019-12-16', 'AL-C701A', 'A', '1', 'BAT', 'BAT001', 'BAT', '2310', 'Oui'),
('BTUECME254', 'Testeur mesure de consommation', '2021-06-12', 'CML', 'BEN FADHEL Faouzi', '110407', '2016-01-01', '2019-12-16', 'AL-C701A', 'A', '1', 'BAT', 'BAT001', 'BAT', '2310', 'Oui'),
('BTUECME260', 'Multimètre numérique', '2021-07-08', 'Testeur MO bach3', 'BEN FADHEL Faouzi', 'MY4702697', '2018-08-15', '2019-12-16', '34410A', 'A', '1', 'BAT', 'BAT001', 'BAT', '2310', 'Oui'),
('BTUECME261', 'ALIMENTATION', '2021-07-09', 'Test mezzanine CML', 'BEN FADHEL Faouzi', '107574', '2017-08-01', '2019-12-16', 'AL-C701A', 'A', '1', 'BAT', 'BAT001', 'BAT', '2310', 'Oui'),
('BTUECME262', 'Multimètre numérique', '2021-07-08', 'Testeur F142', 'BEN FADHEL Faouzi', 'MY53011720', '2015-12-30', '2019-12-16', '34401 A', 'A', '1', 'BAT', 'BAT001', 'BAT', '2310', 'Oui'),
('BTUECME263', 'Multimètre numérique', '2021-07-08', 'Testeur x73', 'BEN FADHEL Faouzi', '54506755', '2017-08-30', '2019-12-16', '34465 A', 'A', '1', 'BAT', 'BAT001', 'BAT', '2310', 'Oui'),
('BTUECME264', 'Multimètre numérique', '2022-01-18', 'Testeur CMFB', 'BEN FADHEL Faouzi', 'MY56059012', '2017-03-01', '2019-12-16', '34450A', 'A', '1', 'BAT', 'BAT001', 'BAT', '2310', 'Oui'),
('BTUECME265', '2CH OSCILLOSCOPE', '2021-09-21', 'LABO TEST', 'BEN FADHEL Faouzi', 'CN57114584', '2017-08-01', '2019-12-16', 'DSOX1102A', 'A', '1', 'BAT', 'BAT001', 'BAT', '2310', 'Oui'),
('BTUECME266', 'Multimètre numérique', '2021-09-21', 'X-73', 'BEN FADHEL Faouzi', 'MY57100231', '2017-08-01', '2019-12-16', '34460A', 'A', '1', 'BAT', 'BAT001', 'BAT', '2310', 'Oui'),
('BTUECME267', 'ALIMENTATION', '2021-09-21', 'TEST', 'BEN FADHEL Faouzi', '8250127130', '2017-08-01', '2019-12-16', 'TENMA.72-2935', 'A', '1', 'BAT', 'BAT001', 'BAT', '2310', 'Oui'),
('BTUECME268', 'ALIMENTATION', '2021-10-24', 'TEST', 'BEN FADHEL Faouzi', '08.250127148', '2017-08-01', '2019-12-16', 'TENMA-722935', 'A', '1', 'BAT', 'BAT001', 'BAT', '2310', 'Oui'),
('BTUECME269', 'ALIMENTATION', '2021-09-21', 'Test', 'BEN FADHEL Faouzi', '8250127124', '2018-08-15', '2019-12-16', 'TENMA722935', 'A', '1', 'BAT', 'BAT001', 'BAT', '2310', 'Oui'),
('BTUECME27', 'Boite de piges', '2021-10-25', 'AQF', 'BEN FADHEL Faouzi', '48380181', '2011-03-01', '2019-12-16', '', 'A', '1', 'BAT', 'BAT001', 'BAT', '2310', 'Oui'),
('BTUECME271', 'Multimètre numérique', '2021-09-23', 'TEST-RIAHD', 'BEN FADHEL Faouzi', '37491684WS', '2017-08-01', '2019-12-16', 'FLUKE115-TRUE-RMS', 'A', '1', 'BAT', 'BAT001', 'BAT', '2310', 'Oui'),
('BTUECME272', 'Multimètre numérique', '2021-09-24', 'Test/Chedli', 'BEN FADHEL Faouzi', '37432584WS', '2017-08-01', '2019-12-16', 'FLUKE115', 'A', '1', 'BAT', 'BAT001', 'BAT', '2310', 'Oui'),
('BTUECME273', 'Multimètre numérique', '2021-09-23', 'Test/MChrayti', 'BEN FADHEL Faouzi', '37432581WS', '2017-08-01', '2019-12-16', 'FLUKE115', 'A', '1', 'BAT', 'BAT001', 'BAT', '2310', 'Oui'),
('BTUECME274', 'Multimètre numérique', '2021-08-27', 'TEST AHMED', 'BEN FADHEL Faouzi', '37432582WS', '2017-08-01', '2019-12-16', 'FLUKE115', 'A', '1', 'BAT', 'BAT001', 'BAT', '2310', 'Oui'),
('BTUECME275', 'Multimètre numérique', '2021-09-23', 'TEST/HOUSSEM', 'BEN FADHEL Faouzi', '37432583WS', '2017-08-01', '2019-12-16', 'FLUKE115', 'A', '1', 'BAT', 'BAT001', 'BAT', '2310', 'Oui'),
('BTUECME276', 'Multimètre numérique', '2021-10-22', 'VH-BOSCH NKW', 'BEN FADHEL Faouzi', 'MY53102603', '2017-12-01', '2019-12-16', '34460A KEYSIGHT', 'A', '1', 'BAT', 'BAT001', 'BAT', '2310', 'Oui'),
('BTUECME278', 'Milliohmmètre', '2021-12-15', 'VBA-3S-VALEO', 'BEN FADHEL Faouzi', '385371', '2018-01-01', '2019-12-16', 'Resistomat2316', 'A', '1', 'BAT', 'BAT001', 'BAT', '2310', 'Oui'),
('BTUECME279', 'Thermo hygromètre', '2021-06-06', 'CMS', 'BEN FADHEL Faouzi', 'WS8471', '2018-02-27', '2019-12-16', 'Velleman/po 1043210', 'A', '1', 'CMS', 'BAT001', 'BAT', '2310', 'Oui'),
('BTUECME280', 'ALIMENTATION', NULL, 'BSE/ Adeunis2', 'BEN FADHEL Faouzi', 'KR83914681', '2017-06-01', '2019-12-16', '', 'R', '1', 'BAT', 'BAT001', 'BAT', '2310', 'Oui'),
('BTUECME281', 'ALIMENTATION', NULL, 'BSE/ Adeunis2', 'BEN FADHEL Faouzi', '350345', '2017-06-01', '2019-12-16', '', 'R', '1', 'BAT', 'BAT001', 'BAT', '2310', 'Oui'),
('BTUECME282', 'Générateur de fréquence', NULL, 'BSE/ Adeunis2', 'BEN FADHEL Faouzi', 'US36005886', '2017-06-01', '2019-12-16', '', 'R', '1', 'BAT', 'BAT001', 'BAT', '2310', 'Oui'),
('BTUECME283', 'Multimètre numérique', NULL, 'BSE/ Adeunis2', 'BEN FADHEL Faouzi', 'US36026783', '2017-06-01', '2019-12-16', '', 'R', '1', 'BAT', 'BAT001', 'BAT', '2310', 'Oui'),
('BTUECME284', 'Radiocommunication', NULL, 'BSE/ Adeunis2', 'BEN FADHEL Faouzi', '841427/004', '2017-06-01', '2019-12-16', '', 'R', '1', 'BAT', 'BAT001', 'BAT', '2310', 'Oui'),
('BTUECME285', 'OSCILOSCOPE', NULL, 'BSE/ Adeunis2', 'BEN FADHEL Faouzi', 'B093294', '2017-06-01', '2019-12-16', '', 'R', '1', 'BAT', 'BAT001', 'BAT', '2310', 'Oui'),
('BTUECME288', 'Multimètre numérique', '2021-06-12', 'Testeue 3S DAG VALEO', 'BEN FADHEL Faouzi', 'MY57202172', '2018-02-01', '2019-12-16', 'KEYSIGHT Technologies ModelNo:34461A', 'A', '1', 'BAT', 'BAT001', 'BAT', '2310', 'Oui'),
('BTUECME290', 'Multimètre numérique', '2021-06-11', 'Testeur BOSH', 'BEN FADHEL Faouzi', 'SDM35CA1L1404', '2018-06-01', '2019-12-16', 'SDM3055/SIGLENT', 'A', '1', 'BAT', 'BAT001', 'BAT', '2310', 'Oui'),
('BTUECME291', 'Multimètre numérique', '2021-12-09', 'FCT HUD Bench VALEO', 'BEN FADHEL Faouzi', 'MY57208826', '2018-04-01', '2019-12-16', 'KEYSIGHT Technologies Model no:34461A', 'A', '1', 'BAT', 'BAT001', 'BAT', '2310', 'Oui'),
('BTUECME292', 'Analyseur de Spectre FEASA', '0000-00-00', 'METHODE', 'BEN FADHEL Faouzi', 'S-195', '0000-00-00', '2019-12-16', 'FEASAS1', 'R', '1', 'BAT', 'BAT001', 'BAT', '1511', 'Oui'),
('BTUECME293', 'Thermomètre à sonde', '2021-06-15', 'VALEO/P2X', 'BEN FADHEL Faouzi', '', '2018-06-01', '2019-12-16', 'STC-200/ELITECH', 'A', '1', 'BAT', 'BAT001', 'BAT', '2310', 'Oui'),
('BTUECME294', 'ALIMENTATION', '2021-06-03', 'VALEO/P2X', 'BEN FADHEL Faouzi', '491728', '2018-06-01', '2019-12-16', 'PL303-QMD-P', 'A', '1', 'BAT', 'BAT001', 'BAT', '2310', 'Oui'),
('BTUECME295', 'Multimètre numérique', '0000-00-00', 'Testeur valeo P2X', 'BEN FADHEL Faouzi', 'SDM35FAC2R2129', '0000-00-00', '2019-12-16', 'SDM3055/SIGLENT TECHNOLOGIES', 'D', '1', 'BAT', 'BAT001', 'BAT', '2310', 'Oui'),
('BTUECME299', 'Réfrigirateur', '2021-08-14', 'Magasin consommable', 'BEN FADHEL Faouzi', '20234049', '2018-09-15', '2019-12-16', 'Hygroclip HC2A-s', 'A', '1', 'BAT', 'BAT001', 'BAT', '1511', 'Oui'),
('BTUECME300', 'Réfrigirateur', '2021-08-14', 'CMS', 'BEN FADHEL Faouzi', '20234066', '2018-09-15', '2019-12-16', 'Hygroclip HC2A-s', 'A', '1', 'CMS', 'BAT001', 'BAT', '2301', 'Oui'),
('BTUECME301', 'Multimètre numérique', NULL, 'Testeur Y191/TOYOTA', 'BEN FADHEL Faouzi', 'MY57215777', '2018-10-30', '2019-12-16', 'Keysight/34461A', 'A', '1', 'BAT', 'BAT001', 'BAT', '2310', 'Oui'),
('BTUECME302', 'ALIMENTATION', '2021-10-22', 'Testeur Y191/TOYOTA', 'BEN FADHEL Faouzi', '493531', '2018-10-30', '2019-12-16', 'TTI/PL303-P', 'A', '1', 'BAT', 'BAT001', 'BAT', '2310', 'Oui'),
('BTUECME303', 'Multimètre numérique', '2021-10-22', 'Testeur Y191/TOYOTA', 'BEN FADHEL Faouzi', 'MY57211537', '2018-10-30', '2019-12-16', 'KEYSIGHT/34461A', 'A', '1', 'BAT', 'BAT001', 'BAT', '2310', 'Oui'),
('BTUECME304', 'ALIMENTATION', NULL, 'Testeur Y191/TOYOTA', 'BEN FADHEL Faouzi', '494914', '2018-10-30', '2019-12-16', 'TTI-PL303-P', 'A', '1', 'BAT', 'BAT001', 'BAT', '2310', 'Oui'),
('BTUECME305', 'Pince ampèremétrique TRMS', '2021-09-21', 'MAINTENANCE', 'BEN FADHEL Faouzi', '41080467WS', '2018-10-15', '2019-12-16', 'FLUKE 323', 'A', '1', 'BAT', 'BAT001', 'BAT', '1511', 'Oui'),
('BTUECME306', 'Pied à coulisse numérique', '2021-11-14', 'MAINTENANCE', 'BEN FADHEL Faouzi', '1309573', '2018-11-01', '2019-12-16', 'KRAFTWERK', 'A', '1', 'BAT', 'BAT001', 'BAT', '1511', 'Oui'),
('BTUECME307', 'Pied à coulisse numérique', '2021-11-14', 'MAINTENANCE', 'BEN FADHEL Faouzi', '1309417', '2018-11-01', '2019-12-16', 'KRAFTWERK', 'A', '1', 'BAT', 'BAT001', 'BAT', '1511', 'Oui'),
('BTUECME308', 'Pied à coulisse numérique', '2021-11-14', 'MAINTENANCE', 'BEN FADHEL Faouzi', '1309447', '2018-11-01', '2019-12-16', 'KRAFTWERK', 'A', '1', 'BAT', 'BAT001', 'BAT', '1511', 'Oui'),
('BTUECME309', 'Pied à coulisse numérique', '2021-11-14', 'MAINTENANCE', 'BEN FADHEL Faouzi', '1309318', '2018-11-01', '2019-12-16', 'KRAFTWERK', 'A', '1', 'BAT', 'BAT001', 'BAT', '1511', 'Oui'),
('BTUECME310', 'Balance électronique', '2021-12-15', 'Magasin-OF CMS', 'BEN FADHEL Faouzi', '575386', '2018-11-01', '2019-12-16', 'RADWAG/PS4500.R2', 'A', '1', 'BAT', 'BAT001', 'BAT', '1511', 'Oui'),
('BTUECME311', 'Balance électronique', '2021-12-15', 'Magasin-reception', 'BEN FADHEL Faouzi', '575383', '2018-11-01', '2019-11-20', 'RADWAG-PS 4500.R2', 'A', '1', 'MAG', 'BAT001', 'BAT', '1511', 'Oui'),
('BTUECME312', 'Balance Electronique', '2021-10-27', 'Magasin', 'BEN FADHEL Faouzi', '575385', '2018-11-01', '2019-12-16', 'RADWAG/model PS4500 R2', 'A', '1', 'BAT', 'BAT001', 'BAT', '1511', 'Oui'),
('BTUECME313', 'Balance Electronique', '2021-08-14', 'MEZANNINE', 'BEN FADHEL Faouzi', '596267', '2018-11-01', '2019-12-16', 'RADWAG/Model ps4500 R2', 'A', '1', 'BAT', 'BAT001', 'BAT', '2307', 'Oui'),
('BTUECME314', 'COMPTEUSE', '2021-05-31', 'Magasin', 'BEN FADHEL Faouzi', '291549', '2018-11-12', '2019-12-16', 'County-s (8301.131)', 'P', '1', 'BAT', 'BAT001', 'BAT', '1511', 'Oui'),
('BTUECME317', 'Balance Electronique', '2021-08-14', 'Magasin', 'BEN FADHEL Faouzi', '596254', '2018-12-01', '2019-12-16', 'RADWAG /MODELPS1000-R1', 'A', '1', 'BAT', 'BAT001', 'BAT', '1511', 'Oui'),
('BTUECME318', 'Oscilloscope', '2021-12-15', 'Novexia Translateur', 'BEN FADHEL Faouzi', 'TDS1002C069644', '2018-12-01', '2019-12-16', 'TDS1002/TEKTRONIK', 'A', '1', 'BAT', 'BAT001', 'BAT', '2310', 'Oui'),
('BTUECME319', 'Alimentation Programmable', '2021-12-12', 'LIGNE MO B3', 'BEN FADHEL Faouzi', '493634', '2018-12-01', '2019-12-16', 'TTI /CPX200DP', 'A', '1', 'BAT', 'BAT001', 'BAT', '2310', 'Oui'),
('BTUECME321', 'Alimentation /BSE', '2021-12-11', 'banc SURTEC Cent F', 'BEN FADHEL Faouzi', '8250159076', '2018-12-01', '2019-12-16', '72-2535/TENMA', 'A', '1', 'BAT', 'BAT001', 'BAT', '2310', 'Oui'),
('BTUECME322', 'Power meter', '2021-09-17', 'BSE/banc SURTEC C F', 'BEN FADHEL Faouzi', 'SN104Q', '2018-12-01', '2019-12-16', 'TELEMAKUS/TED6000-50', 'A', '1', 'BAT', 'BAT001', 'BAT', '2310', 'Oui'),
('BTUECME323', 'Multimètre', '2021-12-11', 'BSE/banc SURTEC C F', 'BEN FADHEL Faouzi', '1049', '2019-01-01', '2019-12-16', 'KEITHLEY/2110', 'A', '1', 'BAT', 'BAT001', 'BAT', '2310', 'Oui'),
('BTUECME324', 'Alimentation', '2020-01-01', 'ELE/ABB', 'BEN FADHEL Faouzi', '', '2019-01-01', '2019-12-16', 'CPX400SP', 'E', '1', 'BAT', 'BAT001', 'BAT', '2310', 'Oui'),
('BTUECME325', 'Fréquence meter', '2020-01-01', 'ELE/ABB', 'BEN FADHEL Faouzi', '480686', '2019-01-01', '2019-12-16', 'TTI/MODEL-EL302P', 'E', '1', 'BAT', 'BAT001', 'BAT', '2310', 'Oui'),
('BTUECME326', 'Alimentation', '2020-01-01', 'ELE/ABB', 'BEN FADHEL Faouzi', '', '2019-01-01', '2019-12-16', 'PCR.500M/KIKUSUI', 'E', '1', 'BAT', 'BAT001', 'BAT', '2310', 'Oui'),
('BTUECME327', 'Alimentation', '2020-01-01', 'ELE/ABB', 'BEN FADHEL Faouzi', '', '2019-01-01', '2019-12-16', 'Multimétéix /XAI525', 'E', '1', 'BAT', 'BAT001', 'BAT', '2310', 'Oui'),
('BTUECME328', 'COMPEUR Programmable', '2020-01-01', 'ELE/ABB', 'BEN FADHEL Faouzi', '', '2019-01-01', '2019-12-16', 'TTI /TF930', 'V', '1', 'BAT', 'BAT001', 'BAT', '2310', 'Oui'),
('BTUECME329', 'Oscilloscope', '2022-01-18', 'ELE/CALIO', 'BEN FADHEL Faouzi', 'MY-54021860', '2019-01-01', '2019-12-16', 'AGILENT TECHNOLOGIE/DSO-X2012A', 'A', '1', 'BAT', 'BAT001', 'BAT', '2310', 'Oui'),
('BTUECME330', 'Multimètre', '2022-01-18', 'ELE/CALIO', 'BEN FADHEL Faouzi', 'MY53209737', '2019-01-01', '2019-12-16', 'KEYSIGHT /MODEL 34461A', 'A', '1', 'BAT', 'BAT001', 'BAT', '2310', 'Oui'),
('BTUECME331', 'Alimentation', '2022-01-19', 'ELE/CALIO', 'BEN FADHEL Faouzi', '4210046', '2019-01-01', '2019-12-16', 'POWER TECHNOLOGIE/MODEL 400XAC', 'A', '1', 'BAT', 'BAT001', 'BAT', '2310', 'Oui'),
('BTUECME332', 'Alimentation', '2022-01-18', 'ELE/CALIO', 'BEN FADHEL Faouzi', '336A091-0003', '2019-01-01', '2019-12-16', 'AGILENT E3649A', 'A', '1', 'BAT', 'BAT001', 'BAT', '2310', 'Oui'),
('BTUECME333', 'Alimentation', '2022-01-18', 'ELE/CALIO', 'BEN FADHEL Faouzi', '', '2019-01-01', '2019-12-16', 'GCM600-2,6/cdk LNMBDA', 'A', '1', 'BAT', 'BAT001', 'BAT', '2310', 'Oui'),
('BTUECME334', 'DATA PAQ', '2021-06-07', 'CMS', 'BEN FADHEL Faouzi', '443903', '2019-02-01', '2019-12-16', 'DQ1860C', 'A', '1', 'CMS', 'BAT001', 'BAT', '2301', 'Oui'),
('BTUECME335', 'QUICK Scope', '2021-06-12', '2D/CMS', 'BEN FADHEL Faouzi', '500141710', '2018-04-17', '2019-12-16', 'Mitutoyo /QS L2010 ZOOM AF (B)', 'A', '1', 'BAT', 'BAT001', 'BAT', '2301', 'Oui'),
('BTUECME338', 'REGLETTE METALIQUE-R1- -L100_cm', '2021-06-08', 'Magasin-reception', 'BEN FADHEL Faouzi', 'R-D102.23.03.19', '2019-03-25', '2019-12-16', 'Mesureur de longueur', 'A', '1', 'BAT', 'BAT001', 'BAT', '1511', 'Oui'),
('BTUECME339', 'REGLETTE METALIQUE-R2-L100_cm', '2021-06-08', 'BEN FADHEL Faouzi', 'BEN FADHEL Faouzi', 'R-D103.23.03.19', '2019-03-25', '2019-12-16', 'Mesureur de longueur', 'A', '1', 'BAT', 'BAT001', 'BAT', '1511', 'Oui'),
('BTUECME341', 'PIED à coulisse numérique', '2021-07-03', 'Méthode', 'BEN FADHEL Faouzi', 'A18169103', '2019-07-01', '2019-12-16', 'CD-15APX/MITUTOYO CORP', 'A', '1', 'BAT', 'BAT001', 'BAT', '1511', 'Oui'),
('BTUECME342', 'Alimentation', '2021-07-08', 'ROOFLINER Testeur', 'BEN FADHEL Faouzi', '502250', '2019-07-01', '2019-12-16', 'TTI/PL303QMD-P', 'A', '1', 'BAT', 'BAT001', 'BAT', '2310', 'Oui'),
('BTUECME343', 'Multimètre', '2021-09-01', 'ZKS Testeur', 'BEN FADHEL Faouzi', 'SDM34 FAX2R0803', '2019-07-01', '2019-12-16', 'SIGLENT-SDM3045X', 'A', '1', 'BAT', 'BAT001', 'BAT', '2310', 'Oui'),
('BTUECME344', 'Multimètre', '2021-07-02', 'ZKS Testeur', 'BEN FADHEL Faouzi', 'SDM.35.FAC.2R.2128', '2019-07-01', '2019-12-16', 'SIGLENT-SDM3055', 'A', '1', 'BAT', 'BAT001', 'BAT', '2310', 'Oui'),
('BTUECME345', 'Niveau à eau', '3000-01-01', 'VAGUE', 'WISSEM DAKWANI', '', '2019-07-01', '2019-12-16', 'PROFESSIONAL', 'A', '1', 'BAT', 'BAT001', 'BAT', '2306', 'Oui'),
('BTUECME346', 'Multimètre', '2022-01-19', 'FCT /CMFB', 'BEN FADHEL Faouzi', 'MY59030128', '2019-10-01', '2019-12-16', 'KEYSIGHT/34450A', 'A', '1', 'BAT', 'BAT001', 'BAT', '2310', 'Oui'),
('BTUECME347', 'Etuve MPC-Bleue', '2021-08-14', 'Préparation', 'BEN FADHEL Faouzi', 'MPC21794', '2018-08-28', '2019-12-16', '', 'A', '1', 'BAT', 'BAT001', 'BAT', '2302', 'Oui'),
('BTUECME351', 'Dynamomètre', '2021-09-02', 'Direction méthodes', 'BEN FADHEL Faouzi', '62079', '2019-09-01', '2019-12-16', '', 'A', '1', 'BAT', 'BAT001', 'BAT', '1511', 'Oui'),
('BTUECME352', 'Micromètre digital', '2021-07-05', 'AQF', 'BEN FADHEL Faouzi', '19276003', '2019-08-26', '2019-12-16', '', 'A', '1', 'BAT', 'BAT001', 'BAT', '1511', 'Oui'),
('BTUECME353', 'Multimètre digital', '2021-07-02', 'Roofliner', 'BEN FADHEL Faouzi', 'SDM34FAX3R0857', '2019-08-07', '2019-12-16', 'SDM3045X', 'A', '1', 'BAT', 'BAT001', 'BAT', '2310', 'Oui'),
('BTUECME354', 'Multimètre digital', '2021-07-08', 'Roofliner', 'BEN FADHEL Faouzi', 'SDM35FAX3R0754', '2019-08-07', '2019-12-16', 'SDM3055', 'A', '1', 'BAT', 'BAT001', 'BAT', '2310', 'Oui'),
('BTUECME355', 'Multimètre digital', '2021-07-02', 'Roofliner', 'BEN FADHEL Faouzi', 'SDM34FAX3R0858', '2019-08-07', '2019-12-16', 'SDM3045X', 'A', '1', 'BAT', 'BAT001', 'BAT', '2310', 'Oui'),
('BTUECME356', 'Marbre de contrôle en granit', '2021-10-27', 'Magasin-reception', 'BEN FADHEL Faouzi', 'MF54622', '2019-11-14', '2019-12-16', '', 'A', '1', 'BAT', 'BAT001', 'BAT', '2307', 'Oui'),
('BTUECME358', 'Cale .EPAI : 0.3 mm', '2021-11-28', 'CMFB', 'BEN FADHEL Faouzi', 'NA', '2019-12-01', '2019-12-16', 'REF 770L/SAM', 'A', '1', 'BAT', 'BAT001', 'BAT', '1511', 'Oui'),
('BTUECME359', 'Cale .EPAI : 0.3 mm', '2021-11-28', 'CMFB', 'BEN FADHEL Faouzi', 'NA', NULL, '2019-12-16', 'REF 770L /SAM', 'A', '1', 'BAT', 'BAT001', 'BAT', '1511', 'Oui'),
('BTUECME52', 'BALANCE ELECTRONIQUE', '2021-06-12', 'MAG', 'BEN FADHEL Faouzi', '10053113T1986', '2011-06-21', '2019-12-16', 'COUNTRY-W', 'A', '1', 'MAG', 'BAT001', 'BAT', '1511', 'Oui'),
('BTUECME54', 'Balance électronique', '2021-12-15', 'VAGUE 330', 'BEN FADHEL Faouzi', '14905932', '2005-02-20', '2019-12-16', 'SARTOURIUS_GP3202', 'A', '1', 'BAT', 'BAT001', 'BAT', '2306', 'Oui'),
('BTUECME61', 'EOT_TESTEUR DE COUPLE', '2021-12-15', 'Maintenance', 'BEN FADHEL Faouzi', '32540-5', '2009-06-15', '2019-12-16', 'SPEC 2640C', 'A', '1', 'BAT', 'BAT001', 'BAT', '1511', 'Oui'),
('BTUECME67', 'Compteuse', '2021-05-31', 'Magasin', 'WISSEM DAKWANI', '10053113T1986', '2011-06-21', '2019-11-20', '', 'P', '1', 'MAG', 'BAT001', 'MAG', '2504', 'Oui'),
('BTUECME76', 'EOT_THERMOMETRE A MERCURE', '2021-10-26', 'AQF', 'BEN FADHEL Faouzi', 'IN', '2008-01-12', '2019-12-16', 'Thermometre a mercure', 'A', '1', 'BAT', 'BAT001', 'BAT', '1511', 'Oui'),
('BTUECME82', 'EOT-Thermocouple Meter', '2021-10-26', 'METHODE', 'BEN FADHEL Faouzi', '', '2011-07-14', '2019-12-16', 'ACCURA', 'A', '1', 'BAT', 'BAT001', 'BAT', '1511', 'Oui'),
('BTUECME88', 'EOT_PIED A COULISSE NUMERIQUE', '2021-10-25', 'AQF', 'BEN FADHEL Faouzi', '6021945', '2009-07-08', '2019-12-16', 'RUPAC 299115N', 'A', '1', 'BAT', 'BAT001', 'BAT', '1511', 'Oui'),
('BTUECME93', 'EOT_Mesureur epaisseur vernis', '2021-07-09', 'VERNISSAGE', 'BEN FADHEL Faouzi', '822190', '2009-08-25', '2019-12-16', 'BYK BT 8500B', 'A', '1', 'BAT', 'BAT001', 'BAT', '2303', 'Oui'),
('BTUECME97', 'EOT-Thermocouple Meter', '2021-10-30', 'Lab TEST', 'BEN FADHEL Faouzi', '', '2011-07-14', '2019-12-16', 'ACCURA', 'A', '1', 'BAT', 'BAT001', 'BAT', '2310', 'Oui'),
('BTUECME_0301_E', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL),
('BTUECME_0373_T', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL),
('BTUECME_0400_T', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL),
('BTUECME_0401_E', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL),
('BTUECME_0402_E', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL),
('BTUECME_0403_E', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL),
('BTUECME_0405_E', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL),
('BTUECME_0406_E', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL),
('BTUECME_256', 'Testeur mesure de consommation', NULL, 'Mezzanine-CML', 'BEN FADHEL Faouzi', '', NULL, '2020-06-17', 'AL-C701A', 'A', '1', 'BAT', 'BAT001', 'BAT', '2307', 'Oui'),
('BTUECME_357', 'Marbre de controle Vague', '2021-10-27', 'Local Cadre Vague', 'BEN FADHEL Faouzi', 'N/A', '2019-11-29', '2020-09-15', '', 'A', '1', 'THT', 'BAT001', 'BAT', '2307', 'Oui'),
('BTUECME_360', 'Multimètre numérique', '2021-09-21', 'TE-ZKS', 'BEN FADHEL Faouzi', 'SDM34FAQ3R-0963', '2020-01-21', '2020-02-11', 'SDM34FAQ3R-0963', 'A', '1', 'BAT', 'BAT001', 'BAT', '2307', 'Oui'),
('BTUECME_361', 'Multimètre numérique', '2021-09-21', 'TE-ZKS', 'BEN FADHEL Faouzi', 'SDM34FAQ3R-1456', '2019-09-20', '2020-02-11', '', 'A', '1', 'MEZ', 'BAT001', 'BAT', '2307', 'Oui'),
('BTUECME_362', 'Multimètre numérique', '2021-06-12', 'Prod- CMFB02', 'BEN FADHEL Faouzi', 'SDM34FAX3R0759', '2019-06-24', '2020-02-11', 's.e.s.i', 'A', '1', 'MEZ', 'BAT001', 'BAT', '2307', 'Oui'),
('BTUECME_363', 'Multimètre numérique', '2021-06-11', 'PROD-CMFB02', 'BEN FADHEL Faouzi', 'SDM34FAX3R 0770', '2019-06-24', '2020-06-10', '', 'A', '1', 'BAT', 'BAT001', 'BAT', '2307', 'Oui'),
('BTUECME_364', 'CAPTEUR DE FRIGO CMS', '2022-01-18', 'CMS', 'BEN FADHEL Faouzi', 'EOT2019BFF01', '2019-11-18', '2020-06-10', 'EOT2019BFF01', 'A', '1', 'BAT', 'BAT001', 'BAT', '2207', 'Oui'),
('BTUECME_366', 'Multimètre numérique', '2021-06-03', 'Mezzanine- SUV', 'BEN FADHEL Faouzi', 'SDM35FAX3R-0413', '2019-04-26', '2020-06-12', '', 'A', '1', 'MEZ', 'BAT001', 'BAT', '2307', 'Oui'),
('BTUECME_367', 'Multimètre numérique', '2021-06-11', 'X52 MIRROR', 'BEN FADHEL Faouzi', 'SDM35FFAC3R 0278', '2020-02-28', '2020-02-28', 'SDM3055', 'A', '1', 'MEZ', 'BAT001', 'BAT', '2310', 'Oui'),
('BTUECME_368', 'Multimètre numérique', '2027-06-10', 'Mezzanine', 'BEN FADHEL Faouzi', 'SDM35FAX3R-0414', '2019-04-26', '2020-06-12', 'SDM3055', 'A', '1', 'MEZ', 'BAT001', 'BAT', '2307', 'Oui'),
('BTUECME_369', 'Alimentation Stabilisé', '2022-02-01', 'Mezzanine -CMFB-NV', 'BEN FADHEL Faouzi', '517514', '2020-01-30', '2020-06-15', '', 'A', '1', 'BAT', 'BAT001', 'BAT', '2307', 'Oui'),
('BTUECME_371', 'Alimentation Stabilisé', '2022-02-05', 'Mezzanine-X52 Mirror', 'BEN FADHEL Faouzi', '513868', '2020-01-30', '2020-06-12', '', 'A', '1', 'MEZ', 'BAT001', 'BAT', '2307', 'Oui'),
('BTUECME_372', 'Alimentation Stabilisé', '2022-02-01', 'PRODUCTION-SQUARE', 'BEN FADHEL Faouzi', '512035', '2020-01-30', '2020-06-15', '', 'A', '1', 'BAT', 'BAT001', 'BAT', '2307', 'Oui'),
('BTUECME_373', 'Etuves sous vide', '2021-02-26', 'Local Vernissage', 'BEN FADHEL Faouzi', 'XFM020', '2019-10-18', '2020-02-11', '', 'P', '1', 'BAT', 'BAT001', 'BAT', '2307', 'Oui'),
('BTUECME_374', 'Multimètre numérique', '2021-06-04', 'LIGNE SQUARE-VALEO', 'BEN FADHEL Faouzi', 'SDM35FAC3R 0261', '2020-06-03', '2020-06-16', 'SDM3055', 'A', '1', 'BAT', 'BAT001', 'BAT', '2302', 'Oui'),
('BTUECME_375', 'Multimètre numérique', '2021-06-03', 'LIGNE SQUARE-VALEO', 'BEN FADHEL Faouzi', 'SDM35FAC3R-0233', '2020-06-03', '2020-06-16', 'SDM3055', 'A', '1', 'BAT', 'BAT001', 'BAT', '2307', 'Oui'),
('BTUECME_376', 'Multimètre numérique', '2021-06-11', 'LIGNE SQUARE-VALEO', 'BEN FADHEL Faouzi', 'SDM35FAQ3R-0884', '2019-08-17', '2020-06-17', 'SDM3055', 'A', '1', 'BAT', 'BAT001', 'BAT', '2307', 'Oui'),
('BTUECME_377', 'Multimètre numérique', '2021-09-21', 'Ligne ODW', 'BEN FADHEL Faouzi', 'SDM34FAQ3R-1410', '2019-09-20', '2020-06-17', 'SDM3045X', 'A', '1', 'BAT', 'BAT001', 'BAT', '2307', 'Oui'),
('BTUECME_378', 'Alimentation Stabilisé', '2021-06-12', 'Ligne ODW', 'BEN FADHEL Faouzi', '517-826', '2020-06-17', '2020-06-17', '', 'A', '1', 'BAT', 'BAT001', 'BAT', '2307', 'Oui'),
('BTUECME_379', 'Multimètre numérique', '2021-09-21', 'Ligne ODW', 'BEN FADHEL Faouzi', 'SDM34FAQ3R-1412', '2019-09-20', '2020-06-17', 'SDM3045X', 'A', '1', 'BAT', 'BAT001', 'BAT', '2307', 'Oui'),
('BTUECME_380', 'Multimètre numérique', '2021-06-17', 'LIGNE M0 BACH 03', 'BEN FADHEL Faouzi', 'SDM35FAC3R-0264', '2020-06-17', '2020-06-17', 'SDM3055', 'A', '1', 'BAT', 'BAT001', 'BAT', '2307', 'Oui'),
('BTUECME_381', 'Multimètre numérique', '2021-06-17', 'LIGNE X52-PUSH BAR', 'BEN FADHEL Faouzi', 'SDM36FAC3R-0043', '2020-06-17', '2020-06-17', '', 'A', '1', 'BAT', 'BAT001', 'BAT', '2307', 'Oui'),
('BTUECME_382', 'Multimètre numérique', '2021-06-17', 'LIGNE X52-PUSH BAR', 'BEN FADHEL Faouzi', 'SDM36FAC3R-0046', '2020-06-17', '2020-06-17', '', 'A', '1', 'BAT', 'BAT001', 'BAT', '2307', 'Oui'),
('BTUECME_383', 'Alimentation Stabilisé', '2021-06-17', 'LIGNE X52-PUSH BAR', 'BEN FADHEL Faouzi', '517-511', '2020-06-17', '2020-06-17', '', 'A', '1', 'BAT', 'BAT001', 'BAT', '2307', 'Oui'),
('BTUECME_384', 'JEUX DE CALE- X_TAILLE', '2021-06-20', 'AQF-MAGASIN', 'BEN FADHEL Faouzi', 'N/A', '2019-06-20', '2020-06-17', 'N/A', 'A', '1', 'MAG', 'BAT001', 'BAT', '1511', 'Oui'),
('BTUECME_385', 'JEUX DE CALE-ETAGERE', '2021-07-06', 'PREPARATION', 'BEN FADHEL Faouzi', 'N/A', '2019-06-20', '2020-06-23', 'NIV-4-5-6-7-8-10-12-15-17', 'A', '1', 'THT', 'BAT001', 'BAT', '2307', 'Oui'),
('BTUECME_386', 'JEUX DE 12 CALES-1mm', '2021-06-20', 'PREPARATION', 'BEN FADHEL Faouzi', 'N/A', '2019-06-20', '2020-06-23', '12-cales de 1 mm', 'A', '1', 'BAT', 'BAT001', 'BAT', '2307', 'Oui'),
('BTUECME_387', 'DATA LOGGER-', '2021-07-06', 'MAINTENANCE', 'BEN FADHEL Faouzi', 'NO 140504132', '2019-06-20', '2020-06-23', 'F184ED', 'A', '1', 'BAT', 'BAT001', 'BAT', '2307', 'Oui'),
('BTUECME_388', 'Tetsteur ESD étage 1', '2021-02-28', 'étage', 'WISSEM DAKWANI', '', '2020-08-24', '2020-08-24', 'ITECO 9264.960', 'P', '1', 'BAT', 'ESD', 'BAT', '1511', 'Oui'),
('BTUECME_389', 'Tetsteur ESD étage 1', '2021-05-01', 'étage', 'WISSEM DAKWANI', '', '2020-08-24', '2020-08-24', 'ITECO 9264.960', 'A', '1', 'BAT', 'ESD', 'BAT', '1511', 'Oui'),
('BTUECME_390', 'Testeur ESD étage 3', '2021-05-01', 'étage', 'WISSEM DAKWANI', '', '2020-08-24', '2020-08-24', 'iteco 9264.960', 'P', '1', 'BAT', 'ESD', 'BAT', '1511', 'Oui'),
('BTUECME_391', 'Caméra infrarouge 1', '2021-09-02', 'RH', 'BEN FADHEL Faouzi', '', '2020-04-01', '2020-09-01', 'FLUKE PT120', 'A', '1', 'BAT', 'BAT001', 'BAT', '1511', 'Oui'),
('BTUECME_392', 'Caméra infrarouge 2', '2021-09-07', 'RH', 'BEN FADHEL Faouzi', '', '2020-04-01', '2020-09-01', 'FLUKE PT120', 'A', '1', 'BAT', 'BAT001', 'BAT', '1511', 'Oui'),
('BTUECME_393', 'THermometre médical infrarouge', '2021-09-01', 'RH', 'BEN FADHEL Faouzi', '', '2019-06-24', '2020-09-01', 'SANITAS', 'A', '1', 'BAT', 'BAT001', 'BAT', '1511', 'Oui'),
('BTUECME_394', 'Gabarit longueur GA', '2021-09-17', 'Mezanine Group Ant', 'BEN FADHEL Faouzi', '', '2020-11-09', '2020-09-16', '', 'A', '1', 'MEZ', 'TEST', 'ASM', '2307', 'Oui'),
('BTUECME_395', 'Gabarit de vérification_BVMPA9', '2021-09-22', 'Ligne BVMPA9', 'BEN FADHEL Faouzi', '', '2020-09-24', '2020-09-24', '', 'A', '1', 'BAT', 'BAT001', 'BAT', '2307', 'Oui'),
('BTUECME_397', 'Thèrmomètre type K avec Sonde', '2022-01-19', 'AQF-MAGASIN', 'BEN FADHEL Faouzi', 'E9042016162', '2020-12-30', '2020-12-30', 'type K', 'A', '1', 'BAT', 'BAT001', 'BAT', '2307', 'Oui'),
('BTUECME_398', 'Pied à coulisse numérique', '2022-01-19', 'AQF-MAGASIN', 'BEN FADHEL Faouzi', '106224', '2020-12-30', '2020-12-30', 'sylvac', 'A', '1', 'BAT', 'BAT001', 'BAT', '2307', 'Oui'),
('BTUECME_399', 'Etuve INTEGRA-2', '2022-02-05', 'THT', 'BEN FADHEL Faouzi', '20180000004405', '2021-01-16', '2021-01-19', '9020-0197', 'A', '1', 'THT', 'ETUVE', 'ETUVE', '2307', 'Oui'),
('BTUECME_400', 'Etuve INTEGRA-3', '2022-02-08', 'THT', 'BEN FADHEL Faouzi', '20180000007534', '2021-01-16', '2021-01-19', '9020-0197', 'A', '1', 'THT', 'ETUVE', 'ETUVE', '2307', 'Oui'),
('BTUECME_401', 'Multimètre numérique', '2022-02-05', 'Ligne Valeo DAF', 'BEN FADHEL Faouzi', 'SDM34FAQ3R-1411', '2021-02-05', '2021-02-01', '', 'A', '1', 'MEZ', 'BAT001', 'BAT', '2307', 'Oui'),
('BTUECME_402', 'Multimètre numérique', '2022-02-11', 'Ligne Valeo DAF', 'BEN FADHEL Faouzi', 'SDM34FAQ3R-1413', '2021-02-05', '2021-02-01', '', 'A', '1', 'BAT', 'BAT001', 'BAT', '2307', 'Oui'),
('BTUECME_403', 'Alimentation Stabilisé', '2022-02-05', 'Ligne Valeo DAF', 'BEN FADHEL Faouzi', 'SN: 517-923', '2021-02-05', '2021-02-01', '', 'A', '1', 'BAT', 'BAT001', 'BAT', '2307', 'Oui'),
('BTUECME_404', 'Afficheur de Température', '2021-02-01', 'Ligne Valeo DAF', 'BEN FADHEL Faouzi', 'DELTA DTK', '2021-02-05', '2021-02-01', 'DELTA DTK', 'P', '1', 'BAT', 'BAT001', 'BAT', '2307', 'Oui'),
('BTUECME_405', 'Alimentation Stabilisé', '2022-02-05', 'MEZ-AM800', 'BEN FADHEL Faouzi', '528954', '2021-02-05', '2021-02-03', 'MACE4-DA-20-7B-65', 'A', '1', 'BAT', 'BAT001', 'BAT', '2307', 'Oui'),
('BTUECME_406', 'Alimentation Stabilisé', '2022-02-05', 'TEST-FIRAS', 'BEN FADHEL Faouzi', '491058', '2021-02-05', '2021-02-05', '', 'A', '1', 'THT', 'TEST', 'BAT', '2310', 'Oui'),
('EOT020014', 'Monte charge EOT', NULL, 'FINITION-MEZZANINE', 'BEN FADHEL Faouzi', '', '2005-10-18', '2020-02-26', '', 'A', '1', 'BAT', 'LEVAGE', 'BAT', '2307', 'Oui'),
('EOT04007', 'Etuve INTEGRA-2', '2020-08-11', 'THT', 'BEN FADHEL Faouzi', '20180000004405', '2020-08-10', '2020-08-10', '9020-0197', 'D', '1', 'THT', 'ETUVE', 'ETUVE', '2307', 'Oui'),
('INF-CAMERA-GA', 'Caméra GA', '2021-09-01', '', 'BEN FADHEL Faouzi', '', '2020-01-02', '2020-09-16', '', 'A', '1', 'MEZ', 'TEST', 'ASM', '2310', 'Oui'),
('INF-LAMPE_LOUPE', 'Lampe loupe', '2021-09-01', 'ELEONETECH', 'WISSEM DAKWANI', '', NULL, '2020-09-07', '', 'A', '1', 'BAT', 'BAT001', 'BAT', '1511', 'Oui'),
('MANO DEK JUKI', 'Manomètre de pression', NULL, 'CMS', 'BEN FADHEL Faouzi', 'NA', '2018-04-14', '2019-12-16', 'Prévost à carbon', 'A', '1', 'CMS', 'BAT001', 'BAT', '2301', 'Oui'),
('MANO DEK PANA', 'Manomètre de pression', NULL, 'CMS', 'BEN FADHEL Faouzi', 'P/500.14.18/AS', '2018-04-14', '2019-12-16', 'Prevost /à carbon', 'A', '1', 'CMS', 'BAT001', 'BAT', '2301', 'Oui'),
('test1', NULL, '2024-04-04', NULL, NULL, NULL, NULL, NULL, NULL, 'V', NULL, NULL, NULL, NULL, NULL, NULL),
('test33', NULL, '2021-04-05', NULL, NULL, NULL, NULL, NULL, NULL, 'V', NULL, NULL, NULL, NULL, NULL, NULL);

-- --------------------------------------------------------

--
-- Structure de la table `rapport`
--

CREATE TABLE `rapport` (
  `id` int(11) NOT NULL,
  `description` varchar(49) NOT NULL,
  `emplacement` varchar(50) NOT NULL,
  `num_serie` varchar(21) NOT NULL,
  `technicien` varchar(50) NOT NULL,
  `societe` varchar(50) NOT NULL,
  `remarque` varchar(100) NOT NULL,
  `etat` varchar(4) DEFAULT NULL,
  `date_visite` datetime NOT NULL,
  `categorie` varchar(100) NOT NULL,
  `exigence` varchar(10) NOT NULL,
  `lieu` varchar(25) NOT NULL,
  `code_equipement` varchar(30) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Déchargement des données de la table `rapport`
--

INSERT INTO `rapport` (`id`, `description`, `emplacement`, `num_serie`, `technicien`, `societe`, `remarque`, `etat`, `date_visite`, `categorie`, `exigence`, `lieu`, `code_equipement`) VALUES
(1, 'a', 'b', 'c', 'd', 'e', 'f', 'V', '2021-05-27 22:24:40', '', 'g', 'h', 'BTUECME005');

-- --------------------------------------------------------

--
-- Structure de la table `utilisateur`
--

CREATE TABLE `utilisateur` (
  `email` varchar(50) NOT NULL,
  `password` varchar(100) DEFAULT NULL,
  `nom` varchar(20) DEFAULT NULL,
  `prenom` varchar(20) DEFAULT NULL,
  `poste` varchar(30) DEFAULT NULL,
  `mobile` int(8) DEFAULT NULL,
  `adresse` varchar(50) DEFAULT NULL,
  `naissance` date DEFAULT NULL,
  `image` varchar(500) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Déchargement des données de la table `utilisateur`
--

--
-- Index pour les tables déchargées
--

--
-- Index pour la table `certif`
--
ALTER TABLE `certif`
  ADD PRIMARY KEY (`id`),
  ADD KEY `code_equipement` (`code_equipement`);

--
-- Index pour la table `equipement`
--
ALTER TABLE `equipement`
  ADD PRIMARY KEY (`Code_equipement`),
  ADD KEY `Etat` (`Etat`),
  ADD KEY `Etat_2` (`Etat`);

--
-- Index pour la table `rapport`
--
ALTER TABLE `rapport`
  ADD PRIMARY KEY (`id`),
  ADD KEY `etat` (`etat`),
  ADD KEY `code_equipement` (`code_equipement`);

--
-- Index pour la table `utilisateur`
--
ALTER TABLE `utilisateur`
  ADD PRIMARY KEY (`email`);

--
-- AUTO_INCREMENT pour les tables déchargées
--

--
-- AUTO_INCREMENT pour la table `certif`
--
ALTER TABLE `certif`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=10;

--
-- AUTO_INCREMENT pour la table `rapport`
--
ALTER TABLE `rapport`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=13;

--
-- Contraintes pour les tables déchargées
--

--
-- Contraintes pour la table `certif`
--
ALTER TABLE `certif`
  ADD CONSTRAINT `certif_ibfk_1` FOREIGN KEY (`code_equipement`) REFERENCES `equipement` (`Code_equipement`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Contraintes pour la table `rapport`
--
ALTER TABLE `rapport`
  ADD CONSTRAINT `rapport_ibfk_1` FOREIGN KEY (`code_equipement`) REFERENCES `equipement` (`Code_equipement`) ON DELETE CASCADE ON UPDATE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
