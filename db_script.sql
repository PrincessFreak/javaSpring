create table product_category
(
    id            serial
        primary key,
    category_name varchar(255)
);

alter table product_category
    owner to postgres;

create table product
(
    id                  serial
        primary key,
    date_time           timestamp(6),
    product_description text,
    product_price       real         not null
        constraint product_product_price_check
            check (product_price >= (1)::double precision),
    product_title       text         not null
        constraint uk_imq7bepsq078jvt9k0f1t42ct
            unique,
    seller              varchar(255) not null,
    warehouse           varchar(255) not null,
    category_id         integer      not null
        constraint "FKqtl9rdhgv7o69thj61wr4b40r"
            references product_category
);

alter table product
    owner to postgres;

create table image
(
    id         serial
        primary key,
    image_name varchar(255),
    product_id integer not null
        constraint "FKf34pcxeeyw3it5v2rthteef7v"
            references product
);

alter table image
    owner to postgres;

create table "user"
(
    id            serial
        primary key,
    first_name    varchar(20),
    role          varchar(255),
    second_name   varchar(30),
    user_login    varchar(30),
    user_password varchar(255)
);

alter table "user"
    owner to postgres;

create table orders
(
    id         serial
        primary key,
    count      integer not null,
    date_time  timestamp(6),
    number     varchar(255),
    price      real    not null,
    status     smallint,
    product_id integer not null
        constraint "FKsmqvgtbfc38l6sng9s120xiyu"
            references product,
    user_id    integer not null
        constraint "FK9jfrdehac629qd09vmcpsl4m6"
            references "user"
);

alter table orders
    owner to postgres;

create table product_cart
(
    id         serial
        primary key,
    product_id integer
        constraint "FK7saowu3x4ixxqvcramt13dk65"
            references product,
    user_id    integer
        constraint "FKkoyrcx9b2vcyariomnb5w575k"
            references "user"
);

alter table product_cart
    owner to postgres;


