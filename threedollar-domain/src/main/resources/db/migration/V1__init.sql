create table appearance_day
(
    id         bigint      not null auto_increment,
    created_at datetime(6),
    updated_at datetime(6),
    day        varchar(30) not null,
    store_id   bigint      not null,
    primary key (id)
) engine = InnoDB;

create table menu
(
    id         bigint       not null auto_increment,
    created_at datetime(6),
    updated_at datetime(6),
    category   varchar(30)  not null,
    name       varchar(50)  DEFAULT NULL,
    price      varchar(100) DEFAULT NULL,
    store_id   bigint       not null,
    primary key (id)
) engine = InnoDB;

create table payment_method
(
    id         bigint      not null auto_increment,
    created_at datetime(6),
    updated_at datetime(6),
    method     varchar(30) not null,
    store_id   bigint      not null,
    primary key (id)
) engine = InnoDB;

create table review
(
    id         bigint       not null auto_increment,
    created_at datetime(6),
    updated_at datetime(6),
    contents   varchar(300)  DEFAULT NULL,
    rating     integer      not null,
    status     varchar(255),
    store_id   bigint       not null,
    user_id    bigint       not null,
    primary key (id)
) engine = InnoDB;

create table store
(
    id         bigint           not null auto_increment,
    created_at datetime(6),
    updated_at datetime(6),
    latitude   double precision not null,
    longitude  double precision not null,
    rating     double precision DEFAULT 0,
    status     varchar(30)      NOT NULL  DEFAULT 'ACTIVE',
    store_name varchar(300)     not null,
    store_type varchar(30)      DEFAULT NULL,
    user_id    bigint           not null,
    primary key (id)
) engine = InnoDB;

create table store_delete_request
(
    id         bigint      not null auto_increment,
    created_at datetime(6),
    updated_at datetime(6),
    reason     varchar(30) not null,
    store_id   bigint      not null,
    user_id    bigint      not null,
    primary key (id)
) engine = InnoDB;

create table store_image
(
    id         bigint not null auto_increment,
    created_at datetime(6),
    updated_at datetime(6),
    is_deleted bit    not null DEFAULT FALSE,
    store_id   bigint NOT NULL,
    url        varchar(255),
    user_id    bigint DEFAULT NULL,
    primary key (id)
) engine = InnoDB;

create table user
(
    id          bigint       not null auto_increment,
    created_at  datetime(6),
    updated_at  datetime(6),
    name        varchar(50),
    social_id   varchar(200) not null,
    social_type varchar(30)  not null,
    status      varchar(30)  not null,
    primary key (id)
) engine = InnoDB;

create table withdrawal_user
(
    user_id         bigint       not null,
    created_at      datetime(6),
    updated_at      datetime(6),
    name            varchar(255),
    social_id       varchar(200) not null,
    social_type     varchar(30)  not null,
    user_created_at datetime(6),
    primary key (user_id)
) engine = InnoDB;