--create type cat_color as enum ('white', 'semi_color', 'black', 'grey');

create table if not exists owners
(
    owner_id   serial primary key unique,
    owner_birthday date not null,
    owner_name varchar(100) not null
);

create table if not exists cats_main_info
(
    cat_id       serial primary key unique,
    owner_id     int not null,
    cat_name     varchar(50) not null,
    cat_breed    varchar(50) not null,
    cat_birthday date        not null,
    cat_color    varchar(20)   not null,
    foreign key (owner_id) references owners (owner_id)
);

create table if not exists owners_with_cats
(
    owner_id int not null,
    cat_id   int not null unique,
    PRIMARY KEY (owner_id),
    foreign key (cat_id) references cats_main_info (cat_id),
    foreign key (owner_id) references owners (owner_id)
);

create table if not exists cats_friends
(
    cat_id    int primary key,
    friend_id int not null references cats_main_info (cat_id)
);