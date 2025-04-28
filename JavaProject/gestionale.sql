CREATE DATABASE `gestionale` /*!40100 DEFAULT CHARACTER SET utf16 COLLATE utf16_general_ci */

-- gestionale.prodotto definition

CREATE TABLE `prodotto` (
  `id` varchar(100) NOT NULL,
  `nome` varchar(100) NOT NULL,
  `descrizione` varchar(100) DEFAULT NULL,
  `prezzo` float NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf16 COLLATE=utf16_general_ci;

-- gestionale.cliente definition

CREATE TABLE `cliente` (
  `id` varchar(100) NOT NULL,
  `nome` varchar(100) NOT NULL,
  `email` varchar(100) NOT NULL,
  `telefono` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf16 COLLATE=utf16_general_ci;

-- gestionale.fornitore definition

CREATE TABLE `fornitore` (
  `id` varchar(100) NOT NULL,
  `nome` varchar(100) NOT NULL,
  `email` varchar(100) NOT NULL,
  `telefono` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf16 COLLATE=utf16_general_ci;

-- gestionale.prodotto_conto_lavoro definition

CREATE TABLE `prodotto_conto_lavoro` (
  `id` varchar(100) NOT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `prodotto_conto_lavoro_prodotto_fk` FOREIGN KEY (`id`) REFERENCES `prodotto` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf16 COLLATE=utf16_general_ci;

-- gestionale.materia_prima definition

CREATE TABLE `materia_prima` (
  `id` varchar(100) NOT NULL,
  `id_fornitore` varchar(100) NOT NULL,
  `prezzo` float NOT NULL,
  `nome` varchar(100) NOT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `materia_prima_fornitore_fk` FOREIGN KEY (`id`) REFERENCES `fornitore` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf16 COLLATE=utf16_general_ci;

-- gestionale.prodotto_conto_vendita definition

CREATE TABLE `prodotto_conto_vendita` (
  `id` varchar(100) NOT NULL,
  `id_materia_prima` varchar(100) NOT NULL,
  `quantita_materia_prima` varchar(100) NOT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `prodotto_conto_vendita_materia_prima_fk` FOREIGN KEY (`id`) REFERENCES `materia_prima` (`id`),
  CONSTRAINT `prodotto_conto_vendita_prodotto_fk` FOREIGN KEY (`id`) REFERENCES `prodotto` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf16 COLLATE=utf16_general_ci;

-- gestionale.ordine definition

CREATE TABLE `ordine` (
  `id` varchar(100) NOT NULL,
  `id_prodotto` varchar(100) NOT NULL,
  `id_cliente` varchar(100) NOT NULL,
  `data_ordine` date NOT NULL,
  `data_scadenza` date NOT NULL,
  `data_completamento` date DEFAULT NULL,
  `quantità` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `ordine_cliente_fk` FOREIGN KEY (`id`) REFERENCES `cliente` (`id`),
  CONSTRAINT `ordine_prodotto_fk` FOREIGN KEY (`id`) REFERENCES `prodotto` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf16 COLLATE=utf16_general_ci;