create table if not exists todo
( id  int auto_increment,
  userid varchar(255) not null,
    description varchar(255) not null,
    target_date date not null,
    done boolean,
    primary key(id)
    );

create table if not exists member
( userid varchar(255),
    password varchar(255) not null,
    username varchar(255) not null,
    role varchar(255) default 'USER' not null,
    primary key(userid)
    );