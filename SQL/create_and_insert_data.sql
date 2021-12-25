create database if not exists free_transportation_system;

create table free_transportation_system.free_transportation_applications (
    id bigint not null auto_increment,
    approved bit not null,
    date_submitted datetime not null,
    photo varchar(255) not null,
    status varchar(15) not null,
    validated bit not null,
    primary key (id)
) engine=InnoDB;

create table free_transportation_system.roles (
    id integer not null auto_increment,
    description varchar(200) not null,
    name varchar(25) unique not null,
    primary key (id)
) engine=InnoDB;

create table free_transportation_system.unemployment_applications (
    id bigint not null auto_increment,
    date_submitted datetime not null,
    photo varchar(255),
    reason varchar(500),
    status varchar(15) not null,
    primary key (id)
) engine=InnoDB;

create table free_transportation_system.users (
    id integer not null auto_increment,
    email varchar(64) unique not null,
    enabled bit not null,
    first_name varchar(46) not null,
    last_name varchar(46) not null,
    password varchar(64) not null,
    role_id integer not null,
    primary key (id),
    constraint FK_user_role foreign key (role_id) references free_transportation_system.roles (id)
) engine=InnoDB;

create table free_transportation_system.users_free_transportation_applications (
    user_id integer not null,
    application_id bigint not null,
    primary key (user_id, application_id),
    constraint FK_app_id foreign key (application_id) references free_transportation_system.free_transportation_applications (id),
    constraint FK_user_id foreign key (user_id) references free_transportation_system.users (id)
) engine=InnoDB;

create table free_transportation_system.users_unemployment_applications (
    user_id integer not null,
    application_id bigint not null,
    primary key (user_id, application_id),
    constraint FK_appl_id foreign key (application_id) references free_transportation_system.unemployment_applications (id),
    constraint FK_usr_id foreign key (user_id) references free_transportation_system.users (id)
) engine=InnoDB;

insert into free_transportation_system.roles(name, description) values("DEFAULT_USER", "CAN ONLY APPLY FOR UNEMPLOYMENT");
insert into free_transportation_system.roles(name, description) values("UNEMPLOYED", "CAN APPLY FOR FREE TRANSPORTATION");
insert into free_transportation_system.roles(name, description) values("OAED_EMPLOYEE", "APPROVES / REJECTS UNEMPLOYMENT APPLICATIONS. VALIDATES OR NOT APPLICATIONS FOR FREE TRANSPORTATION");
insert into free_transportation_system.roles(name, description) values("TRANSPORTATION_EMPLOYEE", "APPROVES / REJECTS APPLICATIONS FOR FREE TRANSPORTATION");
insert into free_transportation_system.roles(name, description) values("ADMIN", "CREATES, EDITS 'OAED_EMPLOYEE' AND 'TRANSPORTATION_EMPLOYEE', DELETES ALL USERS");

insert into free_transportation_system.users(email, enabled, first_name, last_name, password, role_id) values("it21902@hua.gr", true, "Panagiotis", "Alimisis", "123", 5);
