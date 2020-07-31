import {UploadImage} from "../src/component/UploadImage";

jest.mock('node-fetch');
import React from 'react'
import fetch from 'node-fetch';

const {Response} = jest.requireActual('node-fetch');

describe("test profile",() => {
    const wrapper=shallow(<UploadImage/>);
    const file1={
        type:"picture",
        size:100
    };
    const file2={
        type:"image/jpeg",
        size:4*1024*1024
    };
    const file3={
        type:"image/jpeg",
        size:1024*1024
    };
    it("test beforeUpload function",async()=>{
        const instance=wrapper.instance();
        expect(instance.beforeUpload(file1)).toBeFalsy();
        expect(instance.beforeUpload(file2)).toBeFalsy();
        expect(instance.beforeUpload(file3)).toBeTruthy();
        expect(wrapper).toMatchSnapshot();
    });
});