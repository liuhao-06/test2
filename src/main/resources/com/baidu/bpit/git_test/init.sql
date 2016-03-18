create table users(
id int auto_increment,
username varchar(200) not null,
password varchar(200) not null,
usertype char(1) default 0,
modify_time timestamp,
primary key (id)
);

create table logs(
id int auto_increment,
log_type varchar(200) not null,
log_info varchar(200),
log_time varchar(20),
log_ip varchar(20),
host_ip varchar(200),
server_id varchar(200),
primary key (id)
);


insert into users (username,password,usertype) values ('admin','123123','1');
insert into users (username,password) values ('liuhao','123123');