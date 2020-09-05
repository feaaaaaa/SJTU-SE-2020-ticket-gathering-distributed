import {Order} from "../src/component/Order";

jest.mock('node-fetch');
import React from 'react'
import fetch from 'node-fetch';

const {Response} = jest.requireActual('node-fetch');

describe("test order",() => {
    const wrapper=shallow(<Order/>);
    it("test initialized state",()=>{
        expect(wrapper.state().orderInfo).toBeNull();
        expect(wrapper.state().ifauthen).toBeFalsy();
        expect(wrapper.state().avatar).toEqual('https://zos.alipayobjects.com/rmsportal/ODTLcjxAfvqbxHnVXCYX.png');
        expect(wrapper).toMatchSnapshot();
    });

    it("test componentDidMount function",async()=>{
        const json={
            status:200,
            data:[{
            'title':'activity1',
            'price':100,
            'amount':2
            },{
            'title':'activity2',
            'price':150,
            'amount':1
            }]}

        ;
        fetch.mockReturnValue(Promise.resolve(new Response(JSON.stringify(json))));
        await wrapper.instance().componentDidMount();
        expect(wrapper.state().orderInfo[0].title).toEqual('activity1');
        expect(wrapper.state().orderInfo[0].price).toEqual(100);
        expect(wrapper.state().orderInfo[1].amount).toEqual(1);
        // expect(wrapper).toMatchSnapshot();
    });

});