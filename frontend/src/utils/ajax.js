import fetch,{Response}from 'node-fetch';
/**用于服务器跨域
 * url:'http://54.83.132.136:8080'
 * 用于跑本地
 * url:'http://localhost:1112'
 * */

const baseUrl='http://localhost:1112';

let postRequest = async (apiUrl, data, callback) => {
    const url = baseUrl + apiUrl;
    let formData = new FormData();

    for (let p in data) {
        if (data.hasOwnProperty(p))
            formData.append(p, data[p]);
    }
    let opts = {
        method: "POST",
        body: formData,
        credentials: "omit"
    };

    await fetch(url, opts)
        .then((response) => {
            if (response.status === 201) {
                console.log("empty response");
                callback(null);
            } else {
                return response.json()
            }
        })
        .then((data) => {
            console.log(data);
            callback(data);
        })
        .catch((error) => {
            callback(null);
            // console.log(error);
        });
};

let fetchPost1= async (apiUrl, json, callback) => {
    const url = baseUrl + apiUrl;
    let opts = {
        method: "POST",
        body: JSON.stringify(json),
        headers: {
            'Content-Type': 'application/json'
        },
    };

    await fetch(url, opts)
        .then((response) => {
            if (response.status === 201) {
                console.log("empty response");
                callback(null);
            } else {
                return response.json()
            }
        })
        .then((data) => {
            callback(data);
        })
        .catch((error) => {
            callback(null);
            // console.log(error);
        });
};

let authRequest = async (apiUrl, data, token, callback) => {
    const url = baseUrl + apiUrl;
    let formData = new FormData();

    for (let p in data) {
        if (data.hasOwnProperty(p))
            formData.append(p, data[p]);
    }
    let opts = {
        method: "POST",
        body: formData,
        credentials: "omit",
        headers: {
            token: token
        }
    };

    await fetch(url, opts)
        .then((response) => {
            if (response.status === 201) {
                console.log("empty response");
                callback(null);
            } else {
                return response.json()
            }
        })
        .then((data) => {
            console.log(data);
            callback(data);
        })
        .catch((error) => {
            callback(null);
            // console.log(error);
        });
};

export {postRequest,fetchPost1,authRequest};
