/**
 * @param userId {number}
 *
 *
 */
import {authRequest} from "../utils/ajax";

export const getOrderInfoByUser= async (value, token, callback) => {
    const apiUrl = '/order/GetOrderInfoByUser';
    const data = {userId: value};
    await authRequest(apiUrl, data, token, callback);
}

export const addOrder=(userId,actitemId,initprice,orderprice,amount,showtime,orderTime,token,callback)=>
{
    const apiUrl='/order/addOrder';
    const data={userId:userId,actitemId:actitemId,price:initprice,orderprice:orderprice,amount:amount,showtime:showtime,orderTime:orderTime};
    console.log(data);
    authRequest(apiUrl,data,token,callback);
}