jest.mock('node-fetch');
import React from 'react'
import {SortPageView} from "../src/view/SortPageView";
import fetch from 'node-fetch';
import {category_search, getSearchPageNum, getSelectSearchPageNum, search} from "../src/service/searchService";

const {Response} = jest.requireActual('node-fetch');

describe("test profile",() => {
    const wrapper=shallow(<SortPageView/>);
    it("test initialized state",()=>{
        expect(wrapper.state().search).toEqual("");
        expect(wrapper.state().username).toBeNull();
        expect(wrapper.state().usertype).toBeNull();
        expect(wrapper.state().searchMode).toBeNull();
        expect(wrapper.state().city).toEqual("全国");
        expect(wrapper.state().category).toEqual("全部");
        expect(wrapper.state().type).toEqual("category");
    });

    it("test category_search ",async()=>{
        const json={data:[{
                'title':'activity1',
            },{
                'title':'activity2',
            }]}
            ;
        fetch.mockReturnValue(Promise.resolve(new Response(JSON.stringify(json))));
        // fetch.mockReturnValue(Promise.resolve(new Response(true)));
        await category_search("category","全部","全国",1,(res)=>{wrapper.setState({activity: res.data})});
        // console.log(wrapper.state().activity);
        expect(wrapper.state().activity[0].title).toEqual('activity1');
    });

    it("test getSelectSearchPageNum ",async()=>{
        const json={
            data:5,
            status:200
            }
        ;
        fetch.mockReturnValue(Promise.resolve(new Response(JSON.stringify(json))));
        // fetch.mockReturnValue(Promise.resolve(new Response(true)));
        await getSelectSearchPageNum("category","全部","全国",(res)=>{wrapper.setState({pageNum: res.data})});
        // console.log(wrapper.state().activity);
        expect(wrapper.state().pageNum).toEqual(5);
    });

    it("test search ",async()=>{
        const json={data:[{
                'title':'activity1',
            },{
                'title':'activity2',
            }]}
        ;
        fetch.mockReturnValue(Promise.resolve(new Response(JSON.stringify(json))));
        // fetch.mockReturnValue(Promise.resolve(new Response(true)));
        await search("周杰伦",1,(res)=>{wrapper.setState({activity: res.data})});
        // console.log(wrapper.state().activity);
        expect(wrapper.state().activity[0].title).toEqual('activity1');
    });

    it("test getSearchPageNum ",async()=>{
        const json={
                data:5,
                status:200
            }
        ;
        fetch.mockReturnValue(Promise.resolve(new Response(JSON.stringify(json))));
        // fetch.mockReturnValue(Promise.resolve(new Response(true)));
        await getSearchPageNum("周杰伦",(res)=>{wrapper.setState({pageNum: res.data})});
        // console.log(wrapper.state().activity);
        expect(wrapper.state().pageNum).toEqual(5);
    });

});