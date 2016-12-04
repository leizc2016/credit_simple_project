alter table bc_ins_prod_price add `default_flag` int(1) NOT NULL DEFAULT 0 COMMENT '1:是 0:否'
alter table bc_provider_log modify column query_result varchar(1500) 
alter table bc_partner_info add column identify_type varchar(50) COMMENT '证件类型'
alter table bc_partner_info add column identify_no varchar(200) COMMENT '证件号'
alter table bc_partner_info add column real_capi varchar(100) COMMENT '实缴金额'
alter table bc_partner_info add column capi_date varchar(20) COMMENT '实缴日期'