import {authRequest, postRequest} from "../utils/ajax";

export const getAuctions = async (token, callback) => {
    const data = {searching: null};
    const url = `/auction/get`;
    await authRequest(url, data, token, callback);
};

export const addAuction = async (actitemid, ddl, showtime, initprice, orderprice, amount, token, callback) => {
    const data = {
        actitemid: actitemid,
        ddl: ddl,
        showtime: showtime,
        initprice: initprice,
        orderprice: orderprice,
        amount: amount
    };
    const url = `/auction/add`;
    console.log(data);
    await authRequest(url, data, token, callback);
};

export const joinAuctions = async (auctionid, userid, price, token, callback) => {
    const data = {auctionid: auctionid, userid: userid, price: price};
    const url = `/auction/join`;
    await authRequest(url, data, token, callback);
};

export const enterAuction= async (userId, auctionId, token, callback) => {
    const data = {userid: userId, auctionid: auctionId};
    const url = `/auction/canEnter`;
    await authRequest(url, data, token, callback);
}

export const deposit= async (userId, auctionId, token, callback) => {
    const data = {userid: userId, auctionid: auctionId};
    const url = `/auction/deposit`;
    await authRequest(url, data, token, callback);
}

export const getPriceById= async (auctionId, token, callback) => {
    const data = {auctionid: auctionId};
    const url = `/auction/getPrice`;
    await authRequest(url, data, token, callback);
}
