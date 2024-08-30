create table users (
    id          bigserial,
    password    varchar(80) not null,
    email       varchar(50) unique,
    profile_id  bigserial,
    foreign key(profile_id) references profiles(id),
    primary key(id)

);

create table categories (
    id          bigserial,
    name        varchar(30) not null,
    description varchar,
    user_id     bigserial,
    primary key(id),
    foreign key(user_id) references users(id)
)

create table profiles (
    id          serial,
    first_name  varchar(30),
    last_name   varchar(55),
    age         int,
    description varchar,
    primary key(id)
)

create table habits (
    id          serial,
    name        varchar(30),
    outcome     varchar,
    progress    int,
    user_id     bigserial,
    category_id bigserial,
    primary key (id),
    foreign key(user_id) references users(id),
    foreign  key(category_id) references categories(id)
)
create table habit_tracks (
    id   serial,
    done boolean,
    date date,
    habit_id serial,
    primary key(id),
    foreign key(habit_id) references habits(id)
)