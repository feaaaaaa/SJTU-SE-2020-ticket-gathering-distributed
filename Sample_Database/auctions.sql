DROP TABLE if EXISTS tg_auctions;

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