drop TABLE IF EXISTS `bc_apply_detail_tmp`;
drop TABLE IF EXISTS `bc_acc_detail_tmp`;
drop TABLE IF EXISTS `bc_his_rec_tmp`;
drop TABLE IF EXISTS `bc_bund_person_comment_tmp`;

CREATE TABLE IF NOT EXISTS `bc_bund_person_comment_tmp` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `full_name` VARCHAR(45) NULL,
  `id_card_num` VARCHAR(100) NULL,
  `id_card_type` VARCHAR(45) NULL,
  `comment_type` VARCHAR(45) NULL,
  `comment_content` VARCHAR(250) NULL,
  `ins_code` VARCHAR(45) NULL,
  `comment_time` VARCHAR(45) NULL,
  `upload_file_id` BIGINT NULL,
  PRIMARY KEY (`id`))
ENGINE=FEDERATED DEFAULT CHARSET=utf8 CONNECTION='mysql://remote:123456@192.168.1.99:3306/bctmp/bc_bund_person_comment_tmp';

CREATE TABLE IF NOT EXISTS `bc_apply_detail_tmp` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `id_card_type` VARCHAR(45) NULL,
  `full_name` VARCHAR(45) NULL,
  `apply_datetime` VARCHAR(45) NULL,
  `apply_amount_1` INT NULL,
  `apply_type` VARCHAR(45) NULL,
  `approval_amount_1` INT NULL,
  `loan_account` VARCHAR(45) NULL,
  `loan_start_date` VARCHAR(45) NULL,
  `loan_end_date` VARCHAR(45) NULL,
  `apply_pro_city` VARCHAR(45) NULL,
  `home_addr` VARCHAR(45) NULL,
  `sel_phone_num` VARCHAR(45) NULL,
  `apply_ip` VARCHAR(45) NULL,
  `ins_code` VARCHAR(45) NULL,
  `id_card_num` VARCHAR(100) NULL,
  `upload_file_id` BIGINT NULL,
  `apply_amount_2` INT NULL,
  `approval_amount_2` INT NULL,
  PRIMARY KEY (`id`))
ENGINE=FEDERATED DEFAULT CHARSET=utf8 CONNECTION='mysql://remote:123456@192.168.1.99:3306/bctmp/bc_apply_detail_tmp';

CREATE TABLE IF NOT EXISTS `bc_acc_detail_tmp` (
  `id_card_type` VARCHAR(45) NULL,
  `full_name` VARCHAR(45) NULL,
  `loan_account` VARCHAR(45) NULL,
  `loan_balance_1` INT NULL,
  `next_loan_reply_amt_1` INT NULL,
  `next_loan_reply_date` VARCHAR(45) NULL,
  `loan_status_1` VARCHAR(45) NULL,
   `loan_status_2` VARCHAR(45) NULL,
  `ins_code` VARCHAR(45) NULL,
  `id_card_num` VARCHAR(100) NULL,
  `loan_balance_2` INT NULL,
  `next_loan_reply_amt_2` VARCHAR(45) NULL,
  `upload_file_id` BIGINT NULL,
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`id`))
ENGINE=FEDERATED DEFAULT CHARSET=utf8 CONNECTION='mysql://remote:123456@192.168.1.99:3306/bctmp/bc_acc_detail_tmp';


CREATE TABLE IF NOT EXISTS `bc_his_rec_tmp` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `ins_code` VARCHAR(45) NULL,
  `upload_file_id` BIGINT NULL,
  `id_card_num` VARCHAR(100) NULL,
  `id_card_type` VARCHAR(45) NULL,
  `full_name` VARCHAR(45) NULL,
  `apply_date` VARCHAR(45) NULL,
  `apply_amount_1` INT NULL,
  `apply_amount_2` INT NULL,
  `apply_type` VARCHAR(45) NULL,
  `approval_amount_1` VARCHAR(45) NULL,
  `approval_amount_2` VARCHAR(45) NULL,
  `loan_account` VARCHAR(45) NULL,
  `loan_start_date` DATE NULL,
  `loan_end_date` DATE NULL,
  `apply_pro_city` VARCHAR(45) NULL,
  `home_addr` VARCHAR(45) NULL,
  `sel_phone_num` VARCHAR(45) NULL,
  `apply_ip` VARCHAR(45) NULL,
  `loan_balance_1` INT NULL,
  `loan_balance_2` INT NULL,
  `next_loan_reply_date` VARCHAR(45) NULL,
  `next_loan_reply_amt_1` INT NULL,
  `next_loan_reply_amt_2` INT NULL,
  `loan_status_1` VARCHAR(45) NULL,
  `loan_status_2` VARCHAR(45) NULL,
  PRIMARY KEY (`id`))
ENGINE=FEDERATED DEFAULT CHARSET=utf8 CONNECTION='mysql://remote:123456@192.168.1.99:3306/bctmp/bc_his_rec_tmp';