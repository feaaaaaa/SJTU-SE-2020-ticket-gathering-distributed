jest.mock('node-fetch');
import React from 'react'
import {ProfileView} from "../src/view/ProfileView";
import {recharge} from "../src/service/userService";
import fetch from 'node-fetch';

const {Response} = jest.requireActual('node-fetch');

describe("test profile",() => {
    const wrapper=shallow(<ProfileView/>);
    it("test initialized state",()=>{
        expect(wrapper.state().userInfo).toBeNull();
    });

    it("test componentDidMount function",async()=>{
        const json={data: {
                'username': 'oligei',
                'gender': 'Male',
                'type': 'user',
                'phone': '123456',
                'email': 'oligei@gmail.com',
                'balance':'500'
            }
        };
        fetch.mockReturnValue(Promise.resolve(new Response(JSON.stringify(json))));
        await wrapper.instance().componentDidMount();
        console.log("test");
        expect(wrapper.state().userInfo.username).toEqual('oligei');
        expect(wrapper.state().userInfo.gender).toEqual('Male');
        expect(wrapper.state().userInfo.type).toEqual('user');
        expect(wrapper.state().userInfo.phone).toEqual('123456');
        expect(wrapper.state().userInfo.email).toEqual('oligei@gmail.com');
    });

    it("test handleOk",async()=>{
        const json={data: 100

        };
        fetch.mockReturnValue(Promise.resolve(new Response(JSON.stringify(json))));
        await recharge(1,100,"token",(data)=>{wrapper.setState({balance:data.data})});
        expect(wrapper.state().balance).toEqual(100);
    });

});