drop database if exists friendmanagement;

create database friendmanagement;

use friendmanagement;

create table friend_info(
    email varchar(64) not null,
    primary key(email)
);

create table updates(
    update_id int not null auto_increment,
    update_content varchar(400) not null,
    sender_email varchar(64) not null,

    primary key(update_id),
    
    constraint fk_sender_email
        foreign key(sender_email)
        references friend_info(email)
);

create table friend_relation(
    friendship_id int not null auto_increment,
    primary_email varchar(64) not null,
    friend_email varchar(64) not null,
    
    primary key(friendship_id),
	foreign key(primary_email)
        references friend_info(email),
	foreign key(friend_email)
        references friend_info(email),
    constraint UC_friendship unique (primary_email, friend_email)
    
);

create table subscribe_relation(
    subscribe_id int not null auto_increment,
    primary_email varchar(64) not null,
    friend_email varchar(64) not null,
	
    primary key(subscribe_id),
	foreign key(primary_email)
        references friend_info(email),
	foreign key(friend_email)
        references friend_info(email),
    constraint UC_subscribe unique (primary_email, friend_email)

);

create table block_relation(
    block_id int not null auto_increment,
    primary_email varchar(64) not null,
    friend_email varchar(64) not null,
    
    primary key(block_id),    
	foreign key(primary_email)
        references friend_info(email),
	foreign key(friend_email)
        references friend_info(email),
    constraint UC_block unique (primary_email, friend_email)

);