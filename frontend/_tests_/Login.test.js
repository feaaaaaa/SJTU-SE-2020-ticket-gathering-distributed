jest.mock('node-fetch');
import React from 'react'
import {LoginForm} from "../src/component/LoginForm";
import {login} from "../src/service/userService";
import fetch from 'node-fetch';

const {Response} = jest.requireActual('node-fetch');

describe("test loginform",()=>{
    const wrapper=shallow(<LoginForm/>);
    it("test initialized state",()=>{
        expect(wrapper.state().user).toBeNull();
        expect(wrapper.state().firstLogin).toBeTruthy();
    });

    it("test login function",async()=>{
        const json={
            'userId':1,
            'username':'oligei'
        };
        fetch.mockReturnValue(Promise.resolve(new Response(JSON.stringify(json))));
        await login({username:'oligei',password:'123456'},(data)=>{wrapper.setState({user:data,firstLogin:false})});
        expect(wrapper.state().user.userId).toEqual(1);
        expect(wrapper.state().user.username).toEqual('oligei');
    });

});