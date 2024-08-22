create table public.blast
(
    id      uuid not null
        primary key,
    orderid varchar(255),
    no_telp varchar(255),
    message text,
    status  varchar(255),
    userid  varchar
);


