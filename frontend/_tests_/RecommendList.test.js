jest.mock('node-fetch');
import React from 'react'
import {RecommendList} from "../src/component/RecommendList";
import {getRecommend} from "../src/service/userService";
import fetch from 'node-fetch';

const {Response} = jest.requireActual('node-fetch');

describe("test profile",() => {
    const wrapper=shallow(<RecommendList/>);

    it("test componentDidMount function",async()=>{
        const json={status:200,
            data:[{
                'title':'activity1',
            },{
                'title':'activity2',
            }]}
        ;
        fetch.mockReturnValue(Promise.resolve(new Response(JSON.stringify(json))));
        await getRecommend(1,1,"token",(data)=>{wrapper.setState({activity:data.data})})
        expect(wrapper.state().activity[0].title).toEqual('activity1');
    });

});