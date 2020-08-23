import {postRequest,fetchPost1} from "../utils/ajax";

export const search = (value,page,callback) => {
    const data={search:value,page:page};
    const url = '/activity/search';
    postRequest(url, data, callback);
};

export const getSearchPageNum=(value,callback)=>{
    const data={search:value};
    const url='/activity/searchPageNum';
    postRequest(url,data,callback);
}

// export const category_search = (value,callback) => {
//     const data = value;
//     const url = '/Activity/FindActivityByCategory';
//     console.log(value);
//     fetchPost1(url,data,callback);
// }

export const getSelectSearchPageNum=(type,name,city,callback)=>{
    const data={type:type,name:name,city:city};
    const url='/activity/FindActivityByCategoryPageNum';
    postRequest(url,data,callback);
}

export const category_search = (type,name,city,page,callback) => {
    console.log(type);
    console.log(name);
    console.log(city);
    const data={type:type,name:name,city:city,page:page};
    const url = '/activity/FindActivityByCategory';
    postRequest(url,data,callback);
}

export const homeSearch=(callback)=>{
    const url = '/activity/FindActivityByCategoryHome';
    const data={};
    postRequest(url,data,callback);
}
