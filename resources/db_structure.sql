-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Hôte : localhost
-- Généré le : lun. 07 oct. 2024 à 19:47
-- Version du serveur : 10.11.4-MariaDB-1~deb12u1-log
-- Version de PHP : 8.2.13

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";

--
-- Base de données : `openclassrooms_projet3`
--

-- --------------------------------------------------------

--
-- Structure de la table `acl_class`
--

CREATE TABLE `acl_class` (
  `id` bigint(20) NOT NULL,
  `class` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Structure de la table `acl_entry`
--

CREATE TABLE `acl_entry` (
  `id` bigint(20) NOT NULL,
  `acl_object_identity` bigint(20) NOT NULL,
  `ace_order` int(11) NOT NULL,
  `sid` bigint(20) NOT NULL,
  `mask` int(11) NOT NULL,
  `granting` tinyint(1) NOT NULL,
  `audit_success` tinyint(1) NOT NULL,
  `audit_failure` tinyint(1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Structure de la table `acl_object_identity`
--

CREATE TABLE `acl_object_identity` (
  `id` bigint(20) NOT NULL,
  `object_id_class` bigint(20) NOT NULL,
  `object_id_identity` bigint(20) NOT NULL,
  `parent_object` bigint(20) DEFAULT NULL,
  `owner_sid` bigint(20) DEFAULT NULL,
  `entries_inheriting` tinyint(1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Structure de la table `acl_sid`
--

CREATE TABLE `acl_sid` (
  `id` bigint(20) NOT NULL,
  `principal` tinyint(1) NOT NULL,
  `sid` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Index pour les tables déchargées
--

--
-- Index pour la table `acl_class`
--
ALTER TABLE `acl_class`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `unique_uk_2` (`class`);

--
-- Index pour la table `acl_entry`
--
ALTER TABLE `acl_entry`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `unique_uk_4` (`acl_object_identity`,`ace_order`),
  ADD KEY `foreign_fk_5` (`sid`);

--
-- Index pour la table `acl_object_identity`
--
ALTER TABLE `acl_object_identity`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `unique_uk_3` (`object_id_class`,`object_id_identity`),
  ADD KEY `foreign_fk_1` (`parent_object`),
  ADD KEY `foreign_fk_3` (`owner_sid`);

--
-- Index pour la table `acl_sid`
--
ALTER TABLE `acl_sid`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `unique_uk_1` (`sid`,`principal`);

--
-- AUTO_INCREMENT pour les tables déchargées
--

--
-- AUTO_INCREMENT pour la table `acl_class`
--
ALTER TABLE `acl_class`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT pour la table `acl_entry`
--
ALTER TABLE `acl_entry`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT pour la table `acl_object_identity`
--
ALTER TABLE `acl_object_identity`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT pour la table `acl_sid`
--
ALTER TABLE `acl_sid`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- Contraintes pour les tables déchargées
--

--
-- Contraintes pour la table `acl_entry`
--
ALTER TABLE `acl_entry`
  ADD CONSTRAINT `foreign_fk_4` FOREIGN KEY (`acl_object_identity`) REFERENCES `acl_object_identity` (`id`),
  ADD CONSTRAINT `foreign_fk_5` FOREIGN KEY (`sid`) REFERENCES `acl_sid` (`id`);

--
-- Contraintes pour la table `acl_object_identity`
--
ALTER TABLE `acl_object_identity`
  ADD CONSTRAINT `foreign_fk_1` FOREIGN KEY (`parent_object`) REFERENCES `acl_object_identity` (`id`),
  ADD CONSTRAINT `foreign_fk_2` FOREIGN KEY (`object_id_class`) REFERENCES `acl_class` (`id`),
  ADD CONSTRAINT `foreign_fk_3` FOREIGN KEY (`owner_sid`) REFERENCES `acl_sid` (`id`);
COMMIT;