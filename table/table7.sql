
drop table if exists table7;
create table table7 (
primary key (acc_id, curr_type, begin_date, exec_organno, exec_tellerno),
sn_id bigint(20) not null,
acc_id bigint(20) unsigned not null,
curr_type int(3) unsigned not null,
int_evino varchar (15),
status int(3) unsigned null,
begin_date date,
int_date date,
amount bigint(17),
rate_incn tinyint(1),
rate_altf tinyint(1),
rate_code int(7) unsigned,
base_rate int(9),
floa_type tinyint(1),
floa_rate int(9),
exec_rate int,
exec_organno bigint(10) unsigned,
exec_tellerno varchar(11),
exec_date date,
exec_timestamp datetime(6) not null
  )ENGINE=InnoDB DEFAULT CHARSET=UTF8;