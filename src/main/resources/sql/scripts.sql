create table public.books
(
    id         serial primary key,
    title      varchar(100) not null,
    author     varchar(100) not null,
    date_added timestamp    not null
);

drop table public.books;

insert into public.books(title, author, date_added)
values ('Недоросль', 'Д. И. Фонвизин', now());
insert into public.books(title, author, date_added)
values ('Путешествие из Петербурга в Москву', 'А. Н. Радищев', now() - interval '24h');
insert into public.books(title, author, date_added)
values ('Доктор Живаго', 'Б. Л. Пастернак', now() - interval '24h');
insert into public.books(title, author, date_added)
values ('Сестра моя - жизнь', 'Б. Л. Пастернак', now());

drop table public.books;

create table public.books
(
    id         serial primary key,
    title      varchar(100) not null,
    author     varchar(100) not null,
    date_added timestamp    not null
);

insert into public.books(title, author, date_added)
values ('Недоросль', 'Д. И. Фонвизин', now());
insert into public.books(title, author, date_added)
values ('Путешествие из Петербурга в Москву', 'А. Н. Радищев', now() - interval '24h');
insert into public.books(title, author, date_added)
values ('Доктор Живаго', 'Б. Л. Пастернак', now() - interval '24h');
insert into public.books(title, author, date_added)
values ('Сестра моя - жизнь', 'Б. Л. Пастернак', now());

create table if not exists public.reviews
(
    id       bigserial primary key,
    book_id  integer references public.books,
    reviewer varchar(100) not null,
    rating   integer      not null,
    comment  text         null
);

insert into public.reviews(book_id, reviewer, rating, comment)
values (1, 'Петя', 10, 'отличная книга');
insert into public.reviews(book_id, reviewer, rating)
values (1, 'Кирилл', 9);
insert into public.reviews(book_id, reviewer, rating, comment)
values (3, 'Петя', 7, 'ок');
insert into public.reviews(book_id, reviewer, rating, comment)
values (4, 'Иннокентий', 2, 'не понравилась');

alter table public.reviews
    drop constraint reviews_book_id_fkey;

alter table public.reviews
    add constraint reviews_book_id_fkey
        foreign key (book_id)
            references public.books (id)
            on delete cascade on update no action;
