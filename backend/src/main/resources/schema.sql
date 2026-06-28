create table if not exists user(
    userid varchar(255),
    password varchar(255) not null,
    username varchar(20) not null,
    birthday date not null,
    primary key(userid)
);


CREATE TABLE IF NOT EXISTS depositlog (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    accountnum VARCHAR(255) NOT NULL,
    iotype TINYINT NOT NULL,
    savingtype TINYINT NOT NULL,
    amount INT NOT NULL
);

create table if not exists account(
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    accountnum VARCHAR(255) NOT NULL,
    userid VARCHAR(255) NOT NULL,
    savingtype TINYINT NOT NULL,
    interest INT NOT NULL,
    interestrate INT NOT NULL,
    amount INT NOT NULL
);