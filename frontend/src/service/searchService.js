import {postRequest,fetchPost1} from "../utils/ajax";

export const search = async (value, page, callback) => {
    const data = {search: value, page: page};
    const url = '/activity/search';
    await postRequest(url, data, callback);
};

export const getSearchPageNum= async (value, callback) => {
    const data = {search: value};
    const url = '/activity/searchPageNum';
    await postRequest(url, data, callback);
}

// export const category_search = (value,callback) => {
//     const data = value;
//     const url = '/Activity/FindActivityByCategory';
//     console.log(value);
//     fetchPost1(url,data,callback);
// }

export const getSelectSearchPageNum= async (type, name, city, callback) => {
    const data = {type: type, name: name, city: city};
    const url = '/activity/FindActivityByCategoryPageNum';
    await postRequest(url, data, callback);
}

export const category_search = async (type, name, city, page, callback) => {
    console.log(type);
    console.log(name);
    console.log(city);
    const data = {type: type, name: name, city: city, page: page};
    const url = '/activity/FindActivityByCategory';
    await postRequest(url, data, callback);
}

export const homeSearch= async (callback) => {
    const url = '/activity/FindActivityByCategoryHome';
    const data = {};
    await postRequest(url, data, callback);
}
