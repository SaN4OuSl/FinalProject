create table IF NOT EXISTS roles
(
    id   bigint generated by default as identity
        constraint roles_pkey
            primary key,
    name varchar(255)
);

create table IF NOT EXISTS users
(
    id       bigint generated by default as identity
        constraint users_pkey
            primary key,
    created  varchar(64),
    login    varchar(40),
    password varchar(255)
);

create table IF NOT EXISTS farms
(
    id                bigint generated by default as identity
        constraint farms_pkey
            primary key,
    address           varchar(255) not null,
    farm_name         varchar(255) not null,
    year_of_statistic varchar(255) not null,
    user_id           bigint       not null
        constraint fkm4yq5qagd4lwf6qournyqckrf
            references users
);

create table IF NOT EXISTS animals
(
    id                       bigint generated by default as identity
        constraint animals_pkey
            primary key,
    animal_name              varchar(255),
    cost_of_feeds            double precision not null,
    cost_of_one_animal       double precision not null,
    number_of_animals        integer          not null,
    other_expenses           double precision not null,
    rental_price_if_building double precision not null,
    farm_id                  bigint           not null
        constraint fkdwalxt39no0gs365bab0gbclm
            references farms
);

create table IF NOT EXISTS plants
(
    id                      bigint generated by default as identity
        constraint plants_pkey
            primary key,
    cost_of_fertilizers     double precision not null,
    cost_of_plant           double precision not null,
    other_expense           double precision not null,
    plant_harvest           double precision not null,
    plant_name              varchar(255),
    rental_price_of_field   double precision not null,
    size_of_field_for_plant double precision not null,
    farm_id                 bigint           not null
        constraint fkaq801lpql5bebq1liyvpjpe6i
            references farms
);

create table IF NOT EXISTS techniques
(
    id                  bigint generated by default as identity
        constraint techniques_pkey
            primary key,
    price_of_lubricants double precision not null,
    price_of_parts      double precision not null,
    type_of_technique   varchar(255)     not null,
    farm_id             bigint           not null
        constraint fkcyasu0q4r91ruvkithidu31km
            references farms
);

create table IF NOT EXISTS user_roles
(
    user_id bigint not null
        constraint fkhfh9dx7w3ubf1co1vdev94g3f
            references users,
    role_id bigint not null
        constraint fkh8ciramu9cc9q3qcqiv4ue8a6
            references roles
);

insert into roles (id, name)
SELECT *
FROM (SELECT 1, 'ROLE_USER') AS tmp
WHERE NOT EXISTS(
        SELECT id FROM roles WHERE id = 1
    )
LIMIT 1;

insert into roles (id, name)
SELECT *
FROM (SELECT 2, 'ROLE_ADMIN') AS tmp
WHERE NOT EXISTS(
        SELECT id FROM roles WHERE id = 2
    )
LIMIT 1;

insert into users(id, created, login, password)
SELECT *
FROM (SELECT 0,
             '2021-11-09 14:55:10.842000',
             'admin',
             '$2a$10$qY9v6fyT8QyoK8.iwyHkO.2FRs26AxAc6NUCM9SbyNbdDsYy4bOaS') AS tmp
WHERE NOT EXISTS(
        SELECT id FROM users WHERE id = 0
    )
LIMIT 1;

insert into user_roles(user_id, role_id)
SELECT *
FROM (SELECT 0, 2) AS tmp
WHERE NOT EXISTS(
        SELECT user_id FROM user_roles WHERE user_id = 0
    )
LIMIT 1;