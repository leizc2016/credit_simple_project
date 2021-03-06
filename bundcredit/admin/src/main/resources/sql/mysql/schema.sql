drop table if exists vc_task;
drop table if exists vc_admin_user;


create table vc_task (
	id bigint auto_increment,
	title varchar(128) not null,
	description varchar(255),
	user_id bigint not null,
    primary key (id)
) engine=InnoDB;

create table vc_admin_user (
	id bigint auto_increment,
	login_name varchar(64) not null unique,
	name varchar(64) not null,
	password varchar(255) not null,
	salt varchar(64) not null,
	roles varchar(255) not null,
	register_date timestamp not null,
	allow int(1) NOT NULL DEFAULT '1',
	last_login_time date,
	uuid varchar(64),
	primary key (id)
) engine=InnoDB;