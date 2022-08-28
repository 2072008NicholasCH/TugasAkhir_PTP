-- MySQL Script generated by MySQL Workbench
-- Sun Aug 28 21:20:36 2022
-- Model: New Model    Version: 1.0
-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema PT_TA_Agenda
-- -----------------------------------------------------
DROP SCHEMA IF EXISTS `PT_TA_Agenda` ;

-- -----------------------------------------------------
-- Schema PT_TA_Agenda
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `PT_TA_Agenda` DEFAULT CHARACTER SET utf8 ;
USE `PT_TA_Agenda` ;

-- -----------------------------------------------------
-- Table `PT_TA_Agenda`.`Category`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `PT_TA_Agenda`.`Category` (
  `idCategory` INT NOT NULL AUTO_INCREMENT,
  `categoryName` VARCHAR(100) NOT NULL,
  PRIMARY KEY (`idCategory`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `PT_TA_Agenda`.`user`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `PT_TA_Agenda`.`user` (
  `userName` VARCHAR(100) NOT NULL,
  `userPassword` VARCHAR(100) NOT NULL,
  PRIMARY KEY (`userName`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `PT_TA_Agenda`.`Event`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `PT_TA_Agenda`.`Event` (
  `idEvent` INT NOT NULL AUTO_INCREMENT,
  `eventName` VARCHAR(500) NOT NULL,
  `eventTimeStart` DATETIME NOT NULL,
  `eventTimeStop` DATETIME NOT NULL,
  `eventTrash` TINYINT NOT NULL,
  `Category_idCategory` INT NOT NULL,
  `user_userName` VARCHAR(100) NOT NULL,
  PRIMARY KEY (`idEvent`),
  INDEX `fk_Event_Category1_idx` (`Category_idCategory` ASC) ,
  INDEX `fk_Event_user1_idx` (`user_userName` ASC) ,
  CONSTRAINT `fk_Event_Category1`
    FOREIGN KEY (`Category_idCategory`)
    REFERENCES `PT_TA_Agenda`.`Category` (`idCategory`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Event_user1`
    FOREIGN KEY (`user_userName`)
    REFERENCES `PT_TA_Agenda`.`user` (`userName`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `PT_TA_Agenda`.`Task`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `PT_TA_Agenda`.`Task` (
  `idTask` INT NOT NULL AUTO_INCREMENT,
  `taskName` VARCHAR(300) NOT NULL,
  `taskTime` DATETIME NOT NULL,
  `Category_idCategory` INT NOT NULL,
  `user_userName` VARCHAR(100) NOT NULL,
  PRIMARY KEY (`idTask`),
  INDEX `fk_Task_Category_idx` (`Category_idCategory` ASC) ,
  INDEX `fk_Task_user1_idx` (`user_userName` ASC) ,
  CONSTRAINT `fk_Task_Category`
    FOREIGN KEY (`Category_idCategory`)
    REFERENCES `PT_TA_Agenda`.`Category` (`idCategory`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Task_user1`
    FOREIGN KEY (`user_userName`)
    REFERENCES `PT_TA_Agenda`.`user` (`userName`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `PT_TA_Agenda`.`Reminder`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `PT_TA_Agenda`.`Reminder` (
  `idReminder` INT NOT NULL AUTO_INCREMENT,
  `reminderName` VARCHAR(100) NOT NULL,
  `reminderTime` DATETIME NOT NULL,
  `user_userName` VARCHAR(100) NOT NULL,
  PRIMARY KEY (`idReminder`),
  INDEX `fk_Reminder_user1_idx` (`user_userName` ASC) ,
  CONSTRAINT `fk_Reminder_user1`
    FOREIGN KEY (`user_userName`)
    REFERENCES `PT_TA_Agenda`.`user` (`userName`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;

-- -----------------------------------------------------
-- Data for table `PT_TA_Agenda`.`Category`
-- -----------------------------------------------------
START TRANSACTION;
USE `PT_TA_Agenda`;
INSERT INTO `PT_TA_Agenda`.`Category` (`idCategory`, `categoryName`) VALUES (1, 'Family');
INSERT INTO `PT_TA_Agenda`.`Category` (`idCategory`, `categoryName`) VALUES (2, 'Holiday');
INSERT INTO `PT_TA_Agenda`.`Category` (`idCategory`, `categoryName`) VALUES (3, 'Work');

COMMIT;


-- -----------------------------------------------------
-- Data for table `PT_TA_Agenda`.`user`
-- -----------------------------------------------------
START TRANSACTION;
USE `PT_TA_Agenda`;
INSERT INTO `PT_TA_Agenda`.`user` (`userName`, `userPassword`) VALUES ('admin', 'admin');
INSERT INTO `PT_TA_Agenda`.`user` (`userName`, `userPassword`) VALUES ('user1', 'user');

COMMIT;


-- -----------------------------------------------------
-- Data for table `PT_TA_Agenda`.`Event`
-- -----------------------------------------------------
START TRANSACTION;
USE `PT_TA_Agenda`;
INSERT INTO `PT_TA_Agenda`.`Event` (`idEvent`, `eventName`, `eventTimeStart`, `eventTimeStop`, `eventTrash`, `Category_idCategory`, `user_userName`) VALUES (1, 'Liburan', '2022-08-23 00:00:00', '2022-08-27 00:00:00', 0, 2, 'user1');
INSERT INTO `PT_TA_Agenda`.`Event` (`idEvent`, `eventName`, `eventTimeStart`, `eventTimeStop`, `eventTrash`, `Category_idCategory`, `user_userName`) VALUES (2, 'Acara Pernikahan', '2022-08-15 19:00:00', '2022-08-15 21:00:00', 0, 1, 'user1');

COMMIT;


-- -----------------------------------------------------
-- Data for table `PT_TA_Agenda`.`Task`
-- -----------------------------------------------------
START TRANSACTION;
USE `PT_TA_Agenda`;
INSERT INTO `PT_TA_Agenda`.`Task` (`idTask`, `taskName`, `taskTime`, `Category_idCategory`, `user_userName`) VALUES (1, 'Mengerjakan PR', '2022-08-24 20:00:00', 3, 'user1');
INSERT INTO `PT_TA_Agenda`.`Task` (`idTask`, `taskName`, `taskTime`, `Category_idCategory`, `user_userName`) VALUES (2, 'Belajar', '2022-08-25 20:00:00', 3, 'user1');

COMMIT;


-- -----------------------------------------------------
-- Data for table `PT_TA_Agenda`.`Reminder`
-- -----------------------------------------------------
START TRANSACTION;
USE `PT_TA_Agenda`;
INSERT INTO `PT_TA_Agenda`.`Reminder` (`idReminder`, `reminderName`, `reminderTime`, `user_userName`) VALUES (1, 'Mengumpulkan tugas', '2022-08-24 22:00:00', 'user1');
INSERT INTO `PT_TA_Agenda`.`Reminder` (`idReminder`, `reminderName`, `reminderTime`, `user_userName`) VALUES (2, 'Kelas Pemrograman Terapan', '2022-08-25 09:00:00', 'user1');

COMMIT;

