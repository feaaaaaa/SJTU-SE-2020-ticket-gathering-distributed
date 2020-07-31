import React from 'react';
import '../App.css'
import '../css/home.css'
import {Button, Input, Layout, message, Alert, Result, Card, Upload} from 'antd';
import {UploadOutlined} from '@ant-design/icons';

export class UploadImage extends React.Component{
    constructor(props) {
        super(props);
        this.customRequest=this.customRequest.bind(this);
    }
    customRequest(option) {
        const callback =  (data) => {
            this.props.onCommitImage(data);
            // this.setState({image:data});
        };
        const formData = new FormData();
        formData.append("files[]", option.file);
        const reader = new FileReader();
        reader.readAsDataURL(option.file);
        reader.onloadend = function(e) {
            callback(e.target.result);
        };
    }

    beforeUpload(file) {
        const isJpgOrPng = file.type === "image/jpeg" || file.type === "image/png";
        if (!isJpgOrPng) {
            message.error("只能上传JPG或PNG文件!");
            return false;
        }
        const isLt2M = file.size / 1024 / 1024 < 2;
        if (!isLt2M) {
            message.error("图片大小需小于2MB!");
            return false;
        }
        return isJpgOrPng && isLt2M;
    }

    render(){
        return(
            <Upload {...{
                customRequest: this.customRequest,
                showUploadList: false, // 不展示文件列表
                beforeUpload: this.beforeUpload
            }}>
                <Button>
                    <UploadOutlined /> Click to Upload
                </Button>
            </Upload>
        )
    }
}
