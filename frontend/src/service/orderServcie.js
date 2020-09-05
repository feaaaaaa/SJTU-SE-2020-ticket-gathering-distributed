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

export const addOrder= async (userId, actitemId, initprice, orderprice, amount, showtime, orderTime, token, callback) => {
    const apiUrl = '/order/addOrder';
    const data = {
        userId: userId,
        actitemId: actitemId,
        initPrice: initprice,
        orderPrice: orderprice,
        amount: amount,
        showtime: showtime,
        orderTime: orderTime
    };
    console.log(data);
    await authRequest(apiUrl, data, token, callback);
}