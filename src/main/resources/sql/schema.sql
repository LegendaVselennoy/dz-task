create table if not exists micro.tasks
(
   id bigserial primary key not null ,
    heading varchar(25),
    status varchar(30),
    priority varchar(30),
    comment varchar(100),
    author varchar(25),
    executor varchar(30)
);

create table if not exists micro.users
(
  id bigserial primary key not null ,
    email varchar(30),
    password varchar(100),
    role varchar(25) default 'ROLE_USER'
);