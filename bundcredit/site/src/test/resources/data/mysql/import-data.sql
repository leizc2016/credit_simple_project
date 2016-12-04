
insert into bc_institution(ins_code,name,line_of_credit,balance, cash_balance, cash_credit, product_code) values('ora_bundcredit','外滩征信','99999','0','99999','0', 'GRJDCX,QYJDCX,JCCX,XBCX,YYSCX,YLCX');

insert into vc_user (id, login_name, name, password, salt, roles, register_date,ins_code) values(1,'admin','Admin','691b14d79bf0fa2215f155235df5e670b64394cc','7efbd59d9741d34f','admin','2012-06-04 01:00:00','ora_bundcredit');

INSERT INTO bc_product (name, query_price, query_return_price, query_noreturn_price, product_code, cash_flag) VALUES('个人借贷查询', '10', '10', '0', 'GRJDCX', '0');
INSERT INTO bc_product (name, query_price, query_return_price, query_noreturn_price, product_code, cash_flag) VALUES('企业借贷查询', '10', '10', '1', 'QYJDCX', '0');
INSERT INTO bc_product (name, query_price, query_return_price, query_noreturn_price, product_code, cash_flag) VALUES('运营商查询_畅圣', '0', '15', '0', 'YYSCX_CS', 1);
INSERT INTO bc_product (name, query_price, query_return_price, query_noreturn_price, product_code, cash_flag) VALUES('运营商查询_GEO', '0', '5', '0', 'YYSCX_GEO', 1);
INSERT INTO bc_product (name, query_price, query_return_price, query_noreturn_price, product_code,cash_flag) VALUES('银联查询_智策', '0', '15', '0', 'YLCX_ZC',1);
INSERT INTO bc_product (name, query_price, query_return_price, query_noreturn_price, product_code,cash_flag) VALUES('企业查询', '0', '15', '0', 'QYCX',0);




INSERT INTO bc_ins_prod_price (ins_code, prod_code, query_discount,default_flag) VALUES('ora_bundcredit', 'GRJDCX', 0.8,0);
INSERT INTO bc_ins_prod_price (ins_code, prod_code, query_discount,default_flag) VALUES('ora_bundcredit', 'QYJDCX', 0.9,0);
INSERT INTO bc_ins_prod_price (ins_code, prod_code, query_discount,default_flag) VALUES('ora_bundcredit', 'YYSCX_CS', 1,1);
INSERT INTO bc_ins_prod_price (ins_code, prod_code, query_discount,default_flag) VALUES('ora_bundcredit', 'YYSCX_GEO', 1,0);
INSERT INTO bc_ins_prod_price (ins_code, prod_code, query_discount,default_flag) VALUES('ora_bundcredit', 'YLCX_ZC', 1,0);
INSERT INTO bc_ins_prod_price (ins_code, prod_code, query_discount,default_flag) VALUES('ora_bundcredit', 'QYCX', 1,0);
