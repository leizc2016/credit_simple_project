drop table if exists vc_task;
drop table if exists vc_user;

create table vc_task (
	id bigint auto_increment,
	title varchar(128) not null,
	description varchar(255),
	user_id bigint not null,
    primary key (id)
) engine=InnoDB;

create table vc_user (
	id bigint auto_increment,
	login_name varchar(64) not null unique,
	name varchar(64) not null,
	password varchar(255) not null,
	salt varchar(64) not null,
	roles varchar(255) not null,
	register_date timestamp not null,
	ins_code varchar(45) not null,
	allow int(1) NOT NULL DEFAULT 1,
	last_login_time datetime,
	uuid varchar(64),
	primary key (id)
) engine=InnoDB;


drop table if exists bc_institution;

CREATE TABLE IF NOT EXISTS `bc_institution` (
  `ins_code` VARCHAR(45) NOT NULL,
  `name` VARCHAR(45) NULL,
  `line_of_credit` INT NULL DEFAULT 10000,
  `balance` DECIMAL(20,3) NULL DEFAULT 0,
  `cash_credit` INT NULL DEFAULT 0,
  `cash_balance` decimal(20,2) DEFAULT 0,
  `product_code` VARCHAR(1500) NULL,
  PRIMARY KEY (`ins_code`))
ENGINE = InnoDB;

DROP TABLE IF EXISTS `bc_ins_data_upload` ;

CREATE TABLE IF NOT EXISTS `bc_ins_data_upload` (
  `id` BIGINT auto_increment,
  `upload_datetime` VARCHAR(45) NULL,
  `ins_code` VARCHAR(45) NULL,
  `ins_acc_id` BIGINT NULL,
  `filename` VARCHAR(45) NULL,
  `file_uq_key` VARCHAR(250) NULL,
  `val_amt` INT NULL,
  `file_type` INT NULL,
  `file_status` INT NULL,
  `validation_log` VARCHAR(5000) NULL,
  `isenter` int(1) DEFAULT '0',
  PRIMARY KEY (`id`),
  UNIQUE INDEX `file_uq_key_UNIQUE` (`file_uq_key` ASC))
ENGINE = InnoDB;

DROP TABLE IF EXISTS `dmp_search`;
CREATE TABLE `dmp_search` (
  `id` int(8) NOT NULL AUTO_INCREMENT,
  `infoid` varchar(32) NOT NULL,
  `match_` varchar(32) DEFAULT NULL,
  `time` varchar(32) DEFAULT NULL,
  `consumption` varchar(32) DEFAULT NULL,
  `office` varchar(32) DEFAULT NULL,
  `rest` varchar(32) DEFAULT NULL,
  `rate` varchar(100) DEFAULT NULL,
  `search_date` datetime DEFAULT NULL,
  `CID` varchar(11) NOT NULL,
  `CID2` varchar(11) DEFAULT NULL,
  `CIDName` varchar(32) DEFAULT NULL,
  `authID` varchar(32) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB;

DROP TABLE IF EXISTS `bc_ins_transaction_log`;
CREATE TABLE IF NOT EXISTS `bc_ins_transaction_log` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `trans_type` VARCHAR(45) NULL,
  `ins_acc_id` BIGINT NULL,
  `description` VARCHAR(500) NULL,
  `trans_datetime` TIMESTAMP NULL,
  `fee` decimal(13,2) DEFAULT NULL,
  `device` VARCHAR(45) NULL,
  `comments` VARCHAR(4500) NULL,
  `ins_code` VARCHAR(45) NULL,
  `op_name` VARCHAR(45) NULL,
  `ip_addr` VARCHAR(45) NULL,  
  `balance` decimal(13,2) DEFAULT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;

DROP TABLE IF EXISTS `bc_acc_query_history` ;

CREATE TABLE IF NOT EXISTS `bc_acc_query_history` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `query_datetime` VARCHAR(45) NULL,
  `query_type` VARCHAR(45) NULL,
  `id_card_num` VARCHAR(100) NULL,
  `ins_acc_id` BIGINT NULL,
  `page_id` TINYINT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;

drop table if exists bc_person_basic_info;

CREATE TABLE IF NOT EXISTS `bc_person_basic_info` (
  `id_card_num` VARCHAR(100) NOT NULL,
  `full_name` VARCHAR(45) NULL,
  `location` VARCHAR(45) NULL,
  `report_datetime` DATETIME NULL,
  `last_update_datetime` DATETIME NULL,
  PRIMARY KEY (`id_card_num`))
ENGINE = InnoDB;

drop table if exists bc_bund_person_comment;
CREATE TABLE IF NOT EXISTS `bc_bund_person_comment` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `full_name` VARCHAR(45) NULL,
  `id_card_num` VARCHAR(100) NULL,
  `id_card_type` VARCHAR(45) NULL,
  `comment_type` VARCHAR(45) NULL,
  `comment_content` VARCHAR(250) NULL,
  `ins_code` VARCHAR(45) NULL,
  `comment_time` DATETIME NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;

drop table if exists bc_person_acc_detail;

CREATE TABLE IF NOT EXISTS `bc_person_acc_detail` (
  `id_card_type` VARCHAR(45) not NULL,
  `full_name` VARCHAR(45) NULL,
  `loan_acc_id` VARCHAR(45) NOT NULL,
  `loan_balance` decimal(13,2) DEFAULT NULL,
  `next_loan_repay_amt` decimal(13,2) DEFAULT NULL,
  `next_loan_repay_date` DATETIME NULL,
  `loan_status` VARCHAR(45) NULL,
  `ins_code` VARCHAR(45) NOT NULL,
  `id_card_num` VARCHAR(100) NOT NULL,
  `update_date` DATETIME NULL,
  `total_allowed_amount` decimal(13,2) DEFAULT NULL,
  `loan_begin_date` DATETIME NULL,
  `loan_end_date` DATETIME NULL,
  `loan_balance_1` decimal(13,2) DEFAULT NULL,
  `loan_status_1` VARCHAR(45) NULL,
  `next_loan_repay_amt_1` decimal(13,2) DEFAULT NULL,
  `total_allowed_amount_1` decimal(13,2) DEFAULT NULL, 
  latest_24mon_status VARCHAR(100) NULL,
  `comment1` VARCHAR(5000) NULL,
  `comment2` VARCHAR(5000) NULL,
  upload_file_id BIGINT null,
  PRIMARY KEY (`ins_code`, `id_card_num`,`loan_acc_id`,`id_card_type`)
  )
ENGINE = InnoDB;

drop table if exists bc_person_apply_detail;

CREATE TABLE IF NOT EXISTS `bc_person_apply_detail` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `id_card_type` VARCHAR(45) not NULL,
  `full_name` VARCHAR(45) NULL,
  `apply_datetime` DATETIME NULL,
  `apply_amount` decimal(13,2) DEFAULT NULL,
  `apply_type` VARCHAR(45) NULL,
  `approval_amount` decimal(13,2) DEFAULT NULL,
  `loan_account` VARCHAR(45) NULL,
  `loan_start_date` DATETIME NULL,
  `loan_end_date` DATETIME NULL,
  `apply_pro_city` VARCHAR(45) NULL,
  `home_addr` VARCHAR(45) NULL,
  `sel_phone_num` VARCHAR(45) NULL,
  `apply_ip` VARCHAR(45) NULL,
  `ins_code` VARCHAR(45) NOT NULL,
  `id_card_num` VARCHAR(100) NOT NULL,
  `apply_amount_1` decimal(13,2) DEFAULT NULL,
  `approval_amount_1` decimal(13,2) DEFAULT NULL,
  `ass_type` VARCHAR(45) NULL,
  `ass_id_num` VARCHAR(100) NULL,
  `ass_id_type` VARCHAR(45) NULL,
  `ass_name` VARCHAR(45) NULL,
  `comment1` VARCHAR(5000) NULL,
  `comment2` VARCHAR(5000) NULL,
  PRIMARY KEY (`id`)
  )
ENGINE = InnoDB;

drop table if exists bc_person_summary_report;

CREATE TABLE IF NOT EXISTS `bc_person_summary_report` (
  `loan_cnt` INT NULL,
  `open_loan_cnt` INT NULL,
  `open_loan_total_amount` INT NULL,
  `next_loan_repay_amount` INT NULL,
  `overdue_loan_acc_cnt` INT NULL,
  `overdue_90_loan_acc_cnt` INT NULL,
  `in_90_loan_apply_cnt` INT NULL,
  `id_card_num` VARCHAR(100) NOT NULL,
  `open_loan_total_amount_1` INT NULL,
  `next_loan_repay_amount_1` INT NULL,  
  PRIMARY KEY (`id_card_num`)
 )
ENGINE = InnoDB;

drop table if exists bc_ins_prod_price;

CREATE TABLE IF NOT EXISTS `bc_ins_prod_price` (
  `ins_code` VARCHAR(45) NOT NULL,
  `prod_code` VARCHAR(45) NOT NULL,
  `query_discount` FLOAT NULL,
  `default_flag` int(1) NOT NULL DEFAULT 0 COMMENT '1:是 0:否',
  PRIMARY KEY (`ins_code`, `prod_code`)
)
ENGINE = InnoDB;

drop table if exists bc_product;

CREATE TABLE IF NOT EXISTS `bc_product` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NULL,
  `type` VARCHAR(45) NULL,
  `query_price` DOUBLE NULL,
  `query_return_price` DOUBLE NULL,
  `query_noreturn_price` DOUBLE NULL,
  `product_code` VARCHAR(10) NULL,
  `cash_flag`  tinyint(1) NOT NULL DEFAULT 1 COMMENT '1:现金支付 0:外滩币支付' ,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;

drop table if exists bc_credit_query_cache;
drop table if exists bc_credit_query_cache_log;
CREATE TABLE IF NOT EXISTS `bc_credit_query_cache` (
  `ins_code` VARCHAR(45) NOT NULL,
  `id_card_num` VARCHAR(100) NOT NULL,
  `query_type` VARCHAR(45) NOT NULL,
  `cache_time` TIMESTAMP NULL,
  `cache_obj` longtext,
  PRIMARY KEY (`ins_code`, `id_card_num`, `query_type`))
ENGINE = InnoDB;

CREATE TABLE IF NOT EXISTS `bc_credit_query_cache_log` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `ins_code` VARCHAR(45) NULL,
  `id_card_num` VARCHAR(100) NULL,
  `query_type` VARCHAR(45) NULL,
  `cache_time` TIMESTAMP NULL,
  `cache_obj` longtext,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;

drop table if exists bc_ins_prod_price;
CREATE TABLE IF NOT EXISTS `bc_ins_prod_price` (
  `ins_code` VARCHAR(45) NOT NULL,
  `prod_code` VARCHAR(45) NOT NULL,
  `query_discount` float NOT NULL,
  `default_flag` int(1) NOT NULL DEFAULT 0 COMMENT '1:是 0:否',
  PRIMARY KEY (`ins_code`, `prod_code`))
ENGINE = InnoDB;

drop table if exists bc_his;
create table if not exists bc_his (
    id bigint not null auto_increment,
    query_date datetime not null,
    query_condition varchar(200),
    product_code varchar(20) not null,
    product_code_2nd varchar(20),
    flag tinyint,
    condition_id bigint not null,
    user_id bigint not null,
    query_type tinyint,
    primary key (id)
)
engine=innoDB;

drop table if exists bc_his_concise;
create table if not exists bc_his_concise (
    id bigint not null auto_increment,
    query_type tinyint not null,
    id_card_num varchar(100),
    primary key (id)
)
engine=innoDB;

drop table if exists bc_his_full;
create table if not exists bc_his_full (
    id bigint not null auto_increment,
    query_type tinyint not null,
    id_card_num varchar(100),
    primary key (id)
)
engine=innoDB;

drop table if exists bc_his_yys_geo;
create table if not exists bc_his_yys_geo (
    id bigint not null auto_increment,
    cell_num varchar(20),
    auth_code varchar(20),
    user_name varchar(20),
    contact_num varchar(20),
    is_match varchar(50),
    net_time varchar(50),
    spending_level varchar(50),
    working_site varchar(50),
    home_addr varchar(50),
    contact_rate varchar(50),
    primary key (id)
)
engine=innoDB;

drop table if exists bc_his_yys_cs;
create table if not exists bc_his_yys_cs (
    id bigint not null auto_increment,
    user_name varchar(20),
    id_card_num varchar(30),
    cell_num varchar(20),
    home_addr varchar(100),
    working_site varchar(50),
    contact_num varchar(20),
    auth_code varchar(20),
    match_level varchar(50),
    match_id_card varchar(50),
    net_time varchar(50),
    cell_account int,
    fixed_account int,
    asyn_data varchar(800),
    asyn_code varchar(50),   
    home_province varchar(50),
    home_city varchar(50),
    home_district varchar(50),
    working_province varchar(50),
    working_city varchar(50),
    working_district varchar(50),
    primary key (id)
)
engine=innoDB;

drop table if exists bc_his_yl_zc;
create table if not exists bc_his_yl_zc (
    id bigint not null auto_increment,
    id_card_num varchar(30),
    bank_card_num varchar(30),
	bank_card_type varchar(10),
	auth_code varchar(20),
	common_location varchar(20) NULL COMMENT '常用消费地区',
	recent_location varchar(20) NULL COMMENT '最近消费地区',
	cst_score int NULL COMMENT '活跃度评分',
	chv_score int NULL COMMENT '交易行为特征',
	cot_score int NULL COMMENT '套现风险，仅限信用卡使用',
	cnp_score int NULL COMMENT '消费偏好',
	wlp_score int NULL COMMENT '使用频率',
	rsk_score int NULL COMMENT '风险得分',
	cnt_score int NULL COMMENT '消费趋势得分',
	cna_score int NULL COMMENT '消费能力得分',
	dsi_score int NULL COMMENT '消费自由度得分',
	summary_score bigint null COMMENT '外滩综合评分',
	deposit_money_12_months_detail decimal(13,2) NULL,
	deposit_count_12_months_detail bigint NULL,
	consume_money_12_months_detail decimal(13,2) NULL,
	consume_count_12_months_detail bigint NULL,
	draw_money_12_months_detail decimal(13,2) NULL,
	draw_count_12_months_detail bigint NULL,
	trans_in_12_months_detail decimal(13,2) NULL,
	trans_in_count_12_months_detail bigint NULL,
	trans_out_12_months_detail decimal(13,2) NULL,
	trans_out_count_12_months_detail bigint NULL,
    primary key (id)
)
engine=innoDB;

drop table if exists bc_provider_log;
create table if not exists bc_provider_log (
    id bigint not null auto_increment,
    query_date datetime not null,
    query_condition varchar(800),
    query_result varchar(1500),
    provider varchar(30),
    operator varchar(30),
    primary key (id)
)
engine=innoDB;

-----------------------------脚本更新----------------------------------------------

DROP TABLE IF EXISTS `bc_enterprise_info`;
CREATE TABLE `bc_enterprise_info` (
  `reg_id` varchar(20) NOT NULL COMMENT '企业注册号',
  `name` varchar(1000) NOT NULL COMMENT '公司名称',
  `type` varchar(50) NOT NULL COMMENT '企业类型',
  `legal_person` varchar(50) NOT NULL COMMENT '法人',
  `registered_capital` varchar(50) NOT NULL COMMENT '注册资金',
  `address` longtext COMMENT '注册地址',
  `city` varchar(50) DEFAULT NULL COMMENT '城市',
  `bureau` varchar(100) DEFAULT NULL COMMENT '登记机关',
  `state` varchar(50) NOT NULL COMMENT '登记状态',
  `scope` longtext COMMENT '经营范围',
  `set_up_date` varchar(50) DEFAULT NULL COMMENT '成立日期',
  `operating_period` varchar(50) DEFAULT NULL COMMENT '营业期限',
  `award_date` varchar(50) DEFAULT NULL COMMENT '发证日期',
  `create_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `update_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`reg_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


DROP TABLE IF EXISTS `bc_court_bulletin`;
CREATE TABLE `bc_court_bulletin` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `announcement_type` varchar(50) DEFAULT NULL COMMENT '公告类型',
  `announcement` varchar(50) DEFAULT NULL COMMENT '公告人',
  `litigant` varchar(50) DEFAULT NULL COMMENT '当事人',
  `announcement_date` varchar(50) DEFAULT NULL COMMENT '公告时间',
  `content` longtext COMMENT '公告内容',
  `business_name` varchar(200) DEFAULT NULL COMMENT '公司名称',
  `create_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `update_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='法院公告表';




DROP TABLE IF EXISTS `bc_organization_code`;
CREATE TABLE `bc_organization_code` (
  `org_code` varchar(20) NOT NULL COMMENT '组织机构代码',
  `org_name` varchar(50) DEFAULT NULL COMMENT '机构名称',
  `org_reg_id` varchar(50) DEFAULT NULL COMMENT '机构登记证号',
  `org_type` varchar(50) DEFAULT NULL COMMENT '机构类型',
  `iss_uer` varchar(200) DEFAULT NULL COMMENT '颁发单位',
  `org_address` varchar(200) DEFAULT NULL COMMENT '机构地址',
  `apply_date` varchar(200) DEFAULT NULL COMMENT '机构申请日期',
  `start_date` varchar(200) DEFAULT NULL COMMENT '经营开始时间',
  `end_date` varchar(200) DEFAULT NULL COMMENT '经营结束时间',
  `business_name` varchar(200) DEFAULT NULL,
  `create_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `update_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`org_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


DROP TABLE IF EXISTS `bc_court_executor`;
CREATE TABLE `bc_court_executor` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) DEFAULT NULL COMMENT '被执行人名称',
  `exec_court` varchar(50) DEFAULT NULL COMMENT '执行法院',
  `code` varchar(50) DEFAULT NULL COMMENT '身份证号码/组织机构代码',
  `filing_time` varchar(50) DEFAULT 'CURRENT_TIMESTAMP' COMMENT '立案时间',
  `case_code` varchar(50) DEFAULT NULL COMMENT '案号',
  `executive_subject` varchar(500) DEFAULT NULL COMMENT '执行标的',
  `business_name` varchar(200) DEFAULT NULL COMMENT '公司名称',
  `create_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `update_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8 COMMENT='被执行人表';



DROP TABLE IF EXISTS `bc_court_dishonest_executor`;
CREATE TABLE `bc_court_dishonest_executor` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) DEFAULT NULL COMMENT '失信人名称',
  `legal_person` varchar(50) DEFAULT NULL COMMENT '法人名称',
  `code` varchar(50) DEFAULT NULL COMMENT '身份证号码/组织机构代码',
  `basis_docNo` varchar(50) DEFAULT NULL COMMENT '执行依据文号',
  `case_code` varchar(50) DEFAULT NULL COMMENT '案号',
  `basis_dept` varchar(200) DEFAULT NULL COMMENT '做出执行依据单位',
  `doc_content` varchar(2000) DEFAULT NULL COMMENT '法律生效文书确定的义务',
  `exec_status` varchar(500) DEFAULT NULL COMMENT '被执行人的履行情况',
  `exec_court` varchar(100) DEFAULT NULL COMMENT '执行法院',
  `province` varchar(50) DEFAULT NULL COMMENT '省份',
  `filing_time` varchar(50) DEFAULT NULL COMMENT '立案时间',
  `publish_date` varchar(50) DEFAULT NULL COMMENT '发布时间',
  `business_name` varchar(200) DEFAULT NULL COMMENT '公司名称',
  `create_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `update_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='失信人表';



DROP TABLE IF EXISTS bc_his_enterprise_qcc;
CREATE TABLE bc_his_enterprise_qcc (
  id int(11) NOT NULL AUTO_INCREMENT,
  register_num varchar(200) DEFAULT NULL,
  idcard_num varchar(200) DEFAULT NULL,
  full_name varchar(200) DEFAULT NULL,
  create_time timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  update_time timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (id)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;

/**
 * 金融维稳导入的测试数据表
 */
DROP TABLE IF EXISTS bc_company_info;
CREATE TABLE bc_company_info (
  id bigint(20) NOT NULL AUTO_INCREMENT,
  company_name varchar(200) DEFAULT NULL,
  reg_address varchar(1000) DEFAULT NULL,
  op_address varchar(1000) DEFAULT NULL,
  business_license_id varchar(50) DEFAULT NULL,
  PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



DROP TABLE IF EXISTS `bc_partner_info`;
CREATE TABLE `bc_partner_info` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `partner_name` varchar(50) DEFAULT NULL COMMENT '股东名称',
  `partner_type` varchar(255) DEFAULT NULL COMMENT '股东类型',
  `identify_type`  varchar(50) DEFAULT NULL COMMENT '证件类型' ,
  `identify_no`  varchar(200)  DEFAULT NULL COMMENT '证件号' ,
  `real_capi`  varchar(100) DEFAULT NULL COMMENT '实缴金额' ,
  `capi_date`  varchar(20) DEFAULT NULL COMMENT '实缴日期' ,
  `reg_id` varchar(20) NOT NULL COMMENT '工商注册号',
  `create_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `update_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;


DROP TABLE IF EXISTS `bc_court_judgment_doc`;
CREATE TABLE `bc_court_judgment_doc` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `judgment_id` varchar(255) DEFAULT NULL,
  `case_code` varchar(255) DEFAULT NULL COMMENT '案号',
  `judgment_type` varchar(255) DEFAULT NULL COMMENT '类型',
  `title` varchar(255) DEFAULT NULL COMMENT '标题 ',
  `court_name` varchar(255) DEFAULT NULL COMMENT '法院',
  `judge_date` varchar(50) DEFAULT NULL COMMENT '时间',
  `judgment_docUrl` varchar(255) DEFAULT NULL COMMENT '判决书url',
  `create_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `update_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=512266 DEFAULT CHARSET=utf8;
/**
 * 企业变更信息表
 */
DROP TABLE IF EXISTS bc_change_record_info;
CREATE TABLE bc_change_record_info(
id  bigint(20) NOT NULL AUTO_INCREMENT ,
project_name  varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
before_content  varchar(2000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
after_content  varchar(2000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
change_date  varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
reg_id  varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL ,
create_time  timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP ,
update_time  timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP ,
PRIMARY KEY (id)
)ENGINE=InnoDB DEFAULT CHARSET=utf8; 

