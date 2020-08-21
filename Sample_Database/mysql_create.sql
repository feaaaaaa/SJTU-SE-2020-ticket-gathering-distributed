DROP TABLE if EXISTS tg_auctions;
DROP TABLE if EXISTS tg_orders;
DROP TABLE IF EXISTS tg_users;
DROP TABLE if EXISTS tg_actitems;
DROP TABLE if EXISTS tg_activities;

create table tg_users
(
    userid INT NOT NULL AUTO_INCREMENT,
    username VARCHAR(255),
    gender VARCHAR(255),
    email VARCHAR(255),
    phone VARCHAR(255),
    password VARCHAR(255),
    type VARCHAR(255),
    balance int,

    PRIMARY KEY(userid)
);

create table tg_activities
(
    activityid INT NOT NULL AUTO_INCREMENT,
    title VARCHAR(255),
    actor VARCHAR(255),
    timescale VARCHAR(255),
    venue VARCHAR(255),
    activityicon VARCHAR(255),

    PRIMARY KEY(activityid)
);

create table tg_actitems
(
    actitemid INT NOT NULL AUTO_INCREMENT,
    activityid INT,
    website VARCHAR(255),

    PRIMARY KEY(actitemid),
    FOREIGN KEY(activityid) REFERENCES tg_activities(activityid)
);

create table tg_orders
(
    orderid INT NOT NULL AUTO_INCREMENT,
    userid INT,
    actitemid INT,
    price INT,
    amount INT,
    showtime DATE,
    ordertime DATETIME,

    PRIMARY KEY(orderid),
    FOREIGN KEY(userid) REFERENCES tg_users(userid),
    FOREIGN KEY(actitemid) REFERENCES tg_actitems(actitemid)
);

create table tg_auctions
(
    auctionid INT NOT NULL AUTO_INCREMENT,
    actitemid INT,
    userid INT,
    isover BIT DEFAULT 0,
    initprice INT,
    orderprice INT,
    ddl DATETIME,
    showtime DATE,
    ordertime DATETIME,
    amount INT,

    PRIMARY KEY(auctionid),
    FOREIGN KEY(actitemid) REFERENCES tg_actitems(actitemid)
);