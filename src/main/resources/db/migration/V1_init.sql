
CREATE TABLE IF NOT EXISTS users (
    id          bigserial,
    password    varchar(80) not null,
    email       varchar(50) not null unique,
    first_name  varchar(30),
    last_name   varchar(55),
    age         int,
    primary key(id)

);

CREATE TABLE IF NOT EXISTS categories (
    id          bigserial,
    name        varchar(30) not null unique,
    primary key(id)
);

CREATE TABLE IF NOT EXISTS habits (
    id           bigserial,
    name         varchar(50) not null,
    goal         varchar(100),
    category_id  bigint not null,
    user_id      bigint not null,
    primary key  (id),
    foreign key  (category_id)  references categories(id),
    foreign key  (user_id) references users(id)
);

CREATE TABLE IF NOT EXISTS  practices (
    id           bigserial,
    date         date not null,
    done         boolean not null DEFAULT false,
    habit_id     bigint not null,
    primary key  (id),
    foreign key  (habit_id) references habits
);

CREATE TABLE IF NOT EXISTS habit_progress (
    id          bigserial,
    habit_name  varchar(50) NOT NULL,
    progress    INTEGER NOT NULL,
    primary key (id)
);



