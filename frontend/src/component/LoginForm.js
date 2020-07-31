import React from 'react';
import { Form, Input, Button } from 'antd';
import {Redirect,Link}  from 'react-router-dom';
import {message} from "antd";
import "../css/Login.css"
import {checkUser} from "../service/userService";
import {userInfo} from "../const/userInfo";
import {login} from "../service/userService";
import { UserOutlined, LockOutlined } from '@ant-design/icons';
import {VelocityComponent} from "velocity-react";

const VelocityLetter = ({ letter }) => (
    <VelocityComponent
        runOnMount
        animation={{ opacity: 1, marginTop: 0 }}
        duration={500}
    >
        <p style={styles.letter}>{letter}</p>
    </VelocityComponent>
);
const layout = {
    labelCol: { span: 8 },
    wrapperCol: { span: 16 },
};
const tailLayout = {
    wrapperCol: { offset: 8, span: 16 },
};

export class LoginForm extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            user:null,
            firstLogin:true,
            token:null,
            letters: [],
        };
    }
    onFinish = values => {
        const callback =  (data) => {
            console.log("???"+JSON.stringify(data.token));
            this.setState({user:data.user,firstLogin:false,token:data.token});
        };
        console.log(values);
        login(values,callback);
        // console.log(values);
        // if(values.username===userInfo.username && values.password===userInfo.password){
        //      this.setState({firstLogin:false,user:userInfo});
        // }
        // this.setState({firstLogin:false});
    };

    onFinishFailed = errorInfo => {
        console.log('Failed:', errorInfo);
    };
    onChange = (e) => {
        const letters = e.target.value.split('');
        const arr = []
        letters.forEach((l, i) => {
            arr.push(<VelocityLetter letter={l} />)
        })
        this.setState(() => ({ letters: arr }))
    };

    render(){
        if(this.state.user==null &&this.state.firstLogin===false){
            message.error("密码错误")
        }
        if(this.state.user!=null) {
            localStorage.clear();
            // console.log(this.state.user.userId);
            localStorage.setItem("userId", this.state.user.userId);
            localStorage.setItem("username",this.state.user.username);
            localStorage.setItem("usertype",this.state.user.type);
            localStorage.setItem("token",this.state.token);
            return <Redirect to={{pathname: "/"}}/>;
        }
        return (
            <div className="animated rotateIn">
            <Form
                id = "login_form"
                {...layout}
                name="basic"
                initialValues={{remember: true}}
                onFinish={this.onFinish}
                onFinishFailed={this.onFinishFailed}
            >
                <Form.Item
                    // label="Username"
                    // labelCol={{font-size:20px}}
                    name="username"
                    rules={[{required: true, message: 'Please input your username!'}]}
                >
                    <Input prefix={<UserOutlined className="site-form-item-icon" />} placeholder="Username" size="large"
                           // style={{fontFamily:"BookMan Old Style",borderRadius: 10,width:400}}
                           onChange={this.onChange} style={styles.input}/>
                </Form.Item>
                {this.state.letters.length>0 && <div style={styles.letters}>
                    name:{this.state.letters}
                </div>}
                <Form.Item
                    // label="Password"
                    name="password"
                    rules={[{required: true, message: 'Please input your password!'}]}
                >
                    <Input
                        prefix={<LockOutlined className="site-form-item-icon" />}
                        type="password"
                        placeholder="Password"
                        size="large"
                        style={{fontFamily:"BookMan Old Style",borderRadius: 10,width:400}}
                    />
                </Form.Item>
                <Form.Item>
                    <div style={{paddingLeft:50}}>
                    <Button type="primary" htmlType="submit" size="large" id="loginBtn">
                        登 录
                    </Button>
                    <div style={{height:40}}> </div>
                    <Link to={{pathname: "/register"}}>
                        <Button size="large" id="regBtn">
                            注册
                        </Button>
                    </Link>
                    </div>
                </Form.Item>
            </Form>

                <div id="buttonDiv">
                </div>

            </div>
        );
    }
}

const styles = {
    input: {
        width: 400,
        border: 'none',
        outline: 'none',
        // fontSize: 22,
        fontFamily:"BookMan Old Style",
        borderRadius: 10,
    },
    letters: {
        fontFamily:"BookMan Old Style",
        // paddingLeft:35,
        marginTop: -20,
        display: 'flex',
        height: 10,
    },
    letter: {
        opacity: 0,
        marginTop: 100,
        fontSize: 16,
        whiteSpace: 'pre',
    }
};


