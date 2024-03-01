create table `user`
(
    id       varchar(36) primary key,
    email    varchar(255),
    password varchar(255),
    role     varchar(255)
);

create table `pet`
(
    id      varchar(36),
    name    varchar(255),
    birth   date,
    sex     varchar(255),
    user_id varchar(36)
);