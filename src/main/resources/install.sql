CREATE TABLE student (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NULL,
  `language_name` VARCHAR(45) NULL,
  PRIMARY KEY (`id`));

CREATE TABLE age (
  `id` INT NOT NULL AUTO_INCREMENT,
  `age` INT NULL,
  `student_id` INT NULL,
  PRIMARY KEY (`id`));
