import {HeaderInfo} from "../src/component/Header";

jest.mock('node-fetch');
import React from 'react'
import {LoginForm} from "../src/component/LoginForm";
import {login} from "../src/service/userService";
import fetch from 'node-fetch';

const {Response} = jest.requireActual('node-fetch');

describe("test loginform",()=>{
    const wrapper=shallow(<HeaderInfo/>);
    it("test initialized state",()=>{
        expect(wrapper.state().login).toBeFalsy();
        expect(wrapper.state().username).toBeNull();
        expect(wrapper.state().usertype).toBeNull();
        // expect(wrapper.state().ifsearch).toBeFalsy();
    });

    // it("test Search",async()=>{
    //     await wrapper.instance().toggleSearch("周杰伦");
    //     expect(wrapper.state().ifsearch).toBeTruthy();
    // });

    it("test logOut",async()=>{
        wrapper.setState({login:true,username:"test",usertype:"User"})
        await wrapper.instance().logOut();
        expect(wrapper.state().login).toBeFalsy();
        expect(wrapper.state().username).toBeNull();
        expect(wrapper.state().usertype).toBeNull();
    });
});