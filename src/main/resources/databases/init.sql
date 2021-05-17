create table if not exists news
(
    id serial primary key,
    title text,
    content text,
    section_id integer
);
create table if not exists sections
(
    id serial primary key,
    name text
);

create table if not exists users
(
    id serial primary key,
    username text,
    password text
);

create table if not exists comments
(
    id serial primary key,
    content text,
    user_id integer,
    news_id integer
);