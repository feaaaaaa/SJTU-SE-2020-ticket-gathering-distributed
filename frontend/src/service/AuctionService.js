import {authRequest, postRequest} from "../utils/ajax";

export const getAuctions = (token,callback) => {
    const data={searching:null};
    const url = `/auction/get`;
    authRequest(url, data,token, callback);
};

export const addAuction = (actitemid,ddl,showtime,initprice,orderprice,amount,token,callback) => {
    const data={actitemid:actitemid,ddl:ddl,showtime:showtime,initprice:initprice,orderprice:orderprice,amount:amount};
    const url = `/auction/add`;
    console.log(data);
    authRequest(url, data,token, callback);
};

export const joinAuctions = (auctionid,userid,price,token,callback) => {
    const data={auctionid:auctionid,userid:userid,price:price};
    const url = `/auction/join`;
    authRequest(url, data,token, callback);
};

export const enterAuction=(userId,auctionId,token,callback)=>{
    const data={userid:userId,auctionid:auctionId};
    const url = `/auction/canEnter`;
    authRequest(url,data,token,callback);
}
