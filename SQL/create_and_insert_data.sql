create table roles(id int auto_increment primary key, role varchar(40) not null unique, description varchar(150) not null);
insert into roles(role, description) values("UNEMPLOYED", "");
insert into roles(role, description) values("OAED_EMPLOYEE", "");
insert into roles(role, description) values("TRANSPORTATION_EMPLOYEE", "");
insert into roles(role, description) values("ADMIN", "");
