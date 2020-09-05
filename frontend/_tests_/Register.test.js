jest.mock('node-fetch');
import React from 'react'
import {RegisterView} from "../src/view/RegisterView";
import {checkUser,register} from "../src/service/userService";
import fetch from 'node-fetch';

const {Response} = jest.requireActual('node-fetch');

describe("test register",()=>{
    const wrapper=shallow(<RegisterView/>);
    it("test initialized state",()=>{
        expect(wrapper.state().username).toBeNull();
        expect(wrapper.state().password).toBeNull();
        expect(wrapper.state().gender).toEqual(0);
        expect(wrapper.state().usernameErrorVisible).toBeFalsy();
        expect(wrapper.state().registered).toBeFalsy();
        expect(wrapper).toMatchSnapshot();
    });

    it("test input operation",()=>{
        wrapper.find('[placeholder="Enter your password"]').at(0).simulate('blur',{target:{value: '123456'}});
        expect(wrapper.state().password).toEqual('123456');
        wrapper.find('[placeholder="Enter your password again"]').at(0).simulate('blur',{target:{value: '12345'}});
        expect(wrapper.state().passwordErrorVisible).toBeTruthy();
        wrapper.find('[placeholder="Enter your email"]').at(0).simulate('blur',{target:{value: '123456'}});
        expect(wrapper.state().emailErrorVisible).toBeTruthy();
        wrapper.find('[placeholder="Enter your email"]').at(0).simulate('blur',{target:{value: 'oligei@gmail.com'}});
        expect(wrapper.state().emailErrorVisible).toBeFalsy();
        expect(wrapper.state().email).toEqual('oligei@gmail.com');
        // expect(wrapper).toMatchSnapshot();
    });

    it("test input username", async () => {
        const json= {
                status: 200,
            }
        ;
        fetch.mockReturnValue(Promise.resolve(new Response(JSON.stringify(json))));
        // fetch.mockReturnValue(Promise.resolve(new Response(true)));
        // await checkUser('oligei', (data) => {
        //     wrapper.setState({usernameErrorVisible: data})
        // });
        await wrapper.instance().handleUsername({target: {value: 'oligei'}});

        // new Promise(function(resolve){
        //     setTimeout(() => {
        //         wrapper.find('[placeholder="Enter your username"]').at(0).simulate('blur', {target: {value: 'oligei'}});
        //         resolve('success')
        //     }, 2000)
        // }).then(
        //     () => {
        //         expect(wrapper.state().usernameErrorVisible).toBeTruthy();
        //         console.log('test');
        //     },
        // ).catch(error=>{
        //     console.log(error);
        // });
        // await wrapper.find('[placeholder="Enter your username"]').at(0).simulate('blur', {target: {value: 'oligei'}});
        // console.log("test");
        expect(wrapper.state().usernameErrorVisible).toBeTruthy();
        // expect(wrapper).toMatchSnapshot();
    });

    it("test handle" , async ()=>{
        fetch.mockReturnValue(Promise.resolve(new Response(true)));
        await register("1","1","1","1","1",1,(data)=>{wrapper.setState({registerd:data})});
        expect(wrapper.state().registerd).toBeTruthy();
        // expect(wrapper).toMatchSnapshot();
    });
});




