jest.mock('node-fetch');
import React from 'react'
import {DetailView} from "../src/view/DetailView";
import fetch from 'node-fetch';

const {Response} = jest.requireActual('node-fetch');

describe("test profile",() => {
    const wrapper=shallow(<DetailView/>);
    it("test initialized state",()=>{
        expect(wrapper.state().notice).toBeTruthy();
        expect(wrapper.state().isSearch).toBeFalsy();
        expect(wrapper.state().search).toBeNull();
        expect(wrapper.state().logOut).toBeFalsy;
        expect(wrapper.state)
    });

    it("test componentDidMount function",async()=>{
        await wrapper.instance().componentDidMount();
        expect(wrapper).toMatchSnapshot();
    });

    it("test click and search",async()=>{
        await wrapper.instance().onSearch("周杰伦");
        expect(wrapper.state().search).toEqual("周杰伦");
        await wrapper.instance().handleClickNotice1();
        expect(wrapper.state().notice).toBeTruthy();
    });

});