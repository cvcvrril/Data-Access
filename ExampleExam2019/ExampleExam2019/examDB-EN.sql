create table if not exists weapons
(
    id int auto_increment primary key,
    wname varchar(200) not null,
    wprice double not null
);

create table if not exists spies
(
	id int auto_increment primary key,
    sname varchar(200) not null,
    srace   varchar(200) not null
);

create table if not exists faction
(
    fname  varchar(200) not null primary key,
    contact  varchar(200) not null,
    planet varchar(200) not null,
    number_controlled_systems int not null,
    date_last_purchase  date null
);

create table if not exists weapons_factions
(
    id int auto_increment primary key,
    name_faction varchar(200) not null,
    id_weapon        int          not null,
    constraint weapons_factions_1__fk
        foreign key (name_faction) references faction (fname),
    constraint weapons_factions__2_fk
        foreign key (id_weapon) references weapons (id)
);

create table if not exists battles
(
    id int auto_increment primary key,
    bname  varchar(200) not null,
    faction_one varchar(200) not null,
    faction_two varchar(200) not null,
    bplace       varchar(200) not null,
    bdate      date         not null,
    id_spy    int          not null,
    constraint battles_spies_id_fk
        foreign key (id_spy) references spies (id),
    constraint battles_faction_2_fk
        foreign key (faction_two) references faction (fname),
    constraint battles_faction_1_fk
        foreign key (faction_one) references faction (fname)
);

create table if not exists sales
(
    id int auto_increment primary key,
    id_weapons_faction int  not null,
    units int  not null,
    sldate  date not null,
    constraint sales_weapons_factions_id_fk
        foreign key (id_weapons_faction) references weapons_factions (id)
);
