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
    cat_name     varchar(50) not null,
    cat_breed    varchar(50) not null,
    cat_birthday date        not null,
    cat_color    varchar(20)   not null
);

CREATE TABLE IF NOT EXISTS owners_with_cats
(
    owner_id INT NOT NULL,
    cat_id   INT NOT NULL,
    PRIMARY KEY (owner_id, cat_id),
    FOREIGN KEY (cat_id) REFERENCES cats_main_info (cat_id),
    FOREIGN KEY (owner_id) REFERENCES owners (owner_id)
);


create table if not exists cats_friends
(
    cat_id    int not null,
    friend_id int not null,
    primary key (cat_id, friend_id),
    foreign key (cat_id) references cats_main_info (cat_id),
    foreign key (friend_id) references cats_main_info (cat_id)
);