import {authRequest, postRequest} from "../utils/ajax";

export const getAuctions = (token,callback) => {
    const data={searching:null};
    const url = `/Auction/get`;
    authRequest(url, data,token, callback);
};

export const addAuction = (actitemid,ddl,showtime,initprice,orderprice,amount,token,callback) => {
    const data={actitemid:actitemid,ddl:ddl,showtime:showtime,initprice:initprice,orderprice:orderprice,amount:amount};
    const url = `/Auction/add`;
    console.log(data);
    authRequest(url, data,token, callback);
};

export const joinAuctions = (auctionid,userid,price,token,callback) => {
    const data={auctionid:auctionid,userid:userid,price:price};
    const url = `/Auction/join`;
    authRequest(url, data,token, callback);
};
