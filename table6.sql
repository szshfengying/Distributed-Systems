drop table if exists table6;
create table table6 (
	sn_id bigint(20) not null,
	acc_id bigint(20) unsigned primary key not null,
	curr_type int(3) unsigned  not null,
	rate_incm tinyint(1),
	rate_altf tinyint(1),
	base_rate int(9),
	floa_type tinyint(1),
	floa_rate int(9),
	last_rate_date date ,
	last_organno bigint(10) unsigned,
	last_tellerno varchar(11),
	last_modify_date date 
); 
	insert into table6 values(1234567,1111111,3,1,1,4,0,2,NOW(),123,34,NOW());
	insert into table6 values(2345671,2222222,70,1,70,4,0,2,NOW(),123,34,NOW());
	insert into table6 values(3456712,3333333,90,1,95,4,0,2,NOW(),123,34,NOW());
	insert into table6 values(4567123,4444444,97,2,50,4,0,2,NOW(),123,34,NOW());
	insert into table6 values(5671234,5555555,90,2,80,4,0,2,NOW(),123,34,NOW());
	insert into table6 values(6712345,6666666,90,1,65,4,0,2,NOW(),123,34,NOW());