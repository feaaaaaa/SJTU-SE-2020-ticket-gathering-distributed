import {InputNumber} from "antd";

jest.mock('node-fetch');
import React from 'react'
import {AdminAuction} from "../src/component/AdminAuction";
import fetch from 'node-fetch';
import {addAuction} from "../src/service/AuctionService";

const {Response} = jest.requireActual('node-fetch');

describe("test profile",() => {
    const wrapper=shallow(<AdminAuction/>);
    it("test initialized state",()=>{
        expect(wrapper.state().authentication).toBeFalsy();
        expect(wrapper.state().authorization).toBeFalsy();
        expect(wrapper.state().flag1).toBeFalsy();
        expect(wrapper.state().flag2).toBeFalsy();
        expect(wrapper.state().flag3).toBeFalsy();
    });

    it("test InputNumber",()=>{
        wrapper.find(InputNumber).at(0).simulate('change',{target:{value: 2}});
        expect(wrapper.state().flag1).toBeTruthy();
        wrapper.find(InputNumber).at(1).simulate('change',{target:{value: 2}});
        expect(wrapper.state().flag2).toBeTruthy();
    });

    it("test AddAuction",async ()=>{
        const json={status:200,
            data: true
        };
        fetch.mockReturnValue(Promise.resolve(new Response(JSON.stringify(json))));
        wrapper.find('[type="primary"]').simulate('click');
        await addAuction(1,"2020-10-10 00:00:00","2020-10-15",100,200,2,"token",()=>{
            wrapper.setState({success:true});
        })
        expect(wrapper.state().success).toBeTruthy();
    })

});