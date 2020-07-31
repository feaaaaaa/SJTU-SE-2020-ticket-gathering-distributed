jest.mock('node-fetch');
import React from 'react'
import {ProfileView} from "../src/view/ProfileView";
import fetch from 'node-fetch';

const {Response} = jest.requireActual('node-fetch');

describe("test profile",() => {
    const wrapper=shallow(<ProfileView/>);
    it("test initialized state",()=>{
        expect(wrapper.state().userInfo).toBeNull();
        expect(wrapper).toMatchSnapshot();
    });

    it("test componentDidMount function",async()=>{
        const json={
            'username':'oligei',
            'gender':'Male',
            'type':'user',
            'phone':'123456',
            'email':'oligei@gmail.com'
        };
        fetch.mockReturnValue(Promise.resolve(new Response(JSON.stringify(json))));
        await wrapper.instance().componentDidMount();
        console.log("test");
        expect(wrapper.state().userInfo.username).toEqual('oligei');
        expect(wrapper.state().userInfo.gender).toEqual('Male');
        expect(wrapper.state().userInfo.type).toEqual('user');
        expect(wrapper.state().userInfo.phone).toEqual('123456');
        expect(wrapper.state().userInfo.email).toEqual('oligei@gmail.com');
        expect(wrapper).toMatchSnapshot();
    });

});