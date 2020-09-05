jest.mock('node-fetch');
import React from 'react'
import {ProfileView} from "../src/view/ProfileView";
import {addActivity, deleteActivity} from "../src/service/AdminService";
import fetch from 'node-fetch';


const {Response} = jest.requireActual('node-fetch');

describe("test profile",() => {
    it("test componentDidMount function",async()=>{
        let result=false;
        const json={data: true
        };
        fetch.mockReturnValue(Promise.resolve(new Response(JSON.stringify(json))));
        await deleteActivity("value","token",(data)=>{result=data.data});
        expect(result).toBeTruthy();
    });



});