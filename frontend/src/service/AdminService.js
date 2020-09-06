import {authRequest} from "../utils/ajax";

export const addActivity = async (value, token, callback) => {
    const data = {activity: value};
    const url = '/activity/add';
    await authRequest(url, data, token, callback);
};
// export const addActivity = (id,value,token,callback) => {
//     const data={id:id,activity:value};
//     const url = '/activity/add';
//     authRequest(url, data,token, callback);
// };

export const deleteActivity= async (value, token, callback) => {
    const data = {activityId: value};
    const url = '/activity/delete';
    await authRequest(url, data, token, callback);
}