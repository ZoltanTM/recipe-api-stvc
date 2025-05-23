CREATE TABLE `recipe_schema`.`recipe` (
 `id` BINARY(16) NOT NULL,
 `name` VARCHAR(255) NOT NULL,
 `description` TEXT NULL,
 `prep_time_min` INT NULL,
 `cook_time_min` INT NULL,
 `date_created` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,
CONSTRAINT chk_prep_time CHECK (prep_time_min >= 0),
CONSTRAINT chk_cook_time CHECK (cook_time_min >= 0),
PRIMARY KEY (`id`)
) ENGINE=InnoDB;

CREATE TABLE `recipe_schema`.`ingredient` (
 `id` BIGINT NOT NULL AUTO_INCREMENT,
 `name` VARCHAR(255) NOT NULL,
 UNIQUE (`name`),
 PRIMARY KEY (`id`)
) ENGINE=InnoDB;

CREATE TABLE `recipe_schema`.`recipe_ingredient` (
 `id` BIGINT NOT NULL AUTO_INCREMENT,
 `recipe_id` BINARY(16) NOT NULL,
 `ingredient_id` BIGINT NOT NULL,
 `quantity` VARCHAR(50) NULL,
 `unit` VARCHAR(50) NULL,
 FOREIGN KEY (`recipe_id`) REFERENCES `recipe`(`id`) ON DELETE CASCADE,
 FOREIGN KEY (`ingredient_id`) REFERENCES `ingredient`(`id`),
 UNIQUE (`recipe_id`, `ingredient_id`),
 PRIMARY KEY (`id`)
) ENGINE=InnoDB;
