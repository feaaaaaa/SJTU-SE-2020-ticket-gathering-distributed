import {postRequest,fetchPost1,authRequest} from "../utils/ajax";

export const checkUser = async (value, callback) => {
    const data = {username: value};
    const url = '/user/ExistsByUsername';
    await postRequest(url, data, callback);
};


export const login = async (value, callback) => {
    const data = {username: value.username, password: value.password};
    const url = '/user/Login';
    await postRequest(url, data, callback);
};

export const register = async (username, password, email, personicon, phone, gender, callback) => {
    const url = '/user/Register';
    const data = {
        'username': username,
        'password': password,
        'type': "User",
        'gender': gender,
        'email': email,
        'phone': phone,
        'personIcon': personicon
    };
    await fetchPost1(url, data, callback);
};

export const getPersonInfo= async (userId, token, callback) => {
    const url = '/user/FindByUserId';
    const data = {userId: userId};
    await authRequest(url, data, token, callback);
};

export const getRecommend= async(userId,activityId,token,callback)=>{
    const url='/activity/RecommendOnContent';
    const data={userId:userId,activityId:activityId};
    await authRequest(url,data,token,callback);
}

export const recharge= async(userId,increment,token,callback)=>{
    const url='/user/rechargeOrDeduct';
    const data={userId:userId,increment:increment};
    await authRequest(url,data,token,callback);
}



