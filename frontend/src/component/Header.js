import React from 'react';
import {Layout, Input, Dropdown, Menu, Avatar, Button, message,Result} from 'antd';
import "../css/headerInfo.css"
import { UserOutlined } from '@ant-design/icons';
import { Redirect } from 'react-router-dom'
// import Redirect from "react-router-dom/es/Redirect";
const {Header} = Layout;
const { Search } = Input;
// import {withRouter} from "react-router-dom";

export class HeaderInfo extends React.Component {
    constructor(props) {
        super(props);
        this.state={
            login:false,
            username:null,
            usertype:null,
            ifsearch:false
        }
    }

    componentDidMount() {
        let username=localStorage.getItem("username");
        let usertype=localStorage.getItem("usertype");
        if(username!=null){
            this.setState({username:username,login:true});
            this.setState({usertype:usertype})
        }
        console.log(username);
    }

    toggleSearch=(value)=>{
        console.log("搜索内容："+value+"!!!");
        localStorage.setItem("search",value);
        this.setState({
            ifsearch:true
        })
    }


    logOut(){
        localStorage.clear();
        message.success("登出成功");
        this.setState({login:false,username:null,usertype:null});
        if(this.props.logOut!=null){
            this.props.logOut();
        }
    }


    render() {
        if(this.state.ifsearch){
            console.log("jumping...");
            return <Redirect to={{pathname: "/sortPage"}}/>;
        }
        return (
            <Header className="site-layout-background" style={{padding: 0}}>
                <div id="header-content">
                    <div id="oligei" className="animated rollIn">
                        <img src={require('../resources/oligei.png')} width="200px" height="80px"/>
                    </div>
                    <div id="menusortDiv">
                        <Button id="menuButton" href="/" type={"primary"}>首页</Button>
                        <Button id="sortButton" href="/sortPage" type={"primary"}>分类</Button>
                        <Button id="auctionButton" href="/auction" type={"primary"}>竞价</Button>
                    </div>
                    <div id="searchDiv">
                        <Search
                            id="searchInput"
                            placeholder="搜索明星、演出、体育赛事"
                            onSearch={value => this.toggleSearch(value)}
                            enterButton="搜索"
                            size="large"
                        />
                    </div>
                    <Avatar id="profileOperate" icon={<UserOutlined />} />
                    <Dropdown
                        overlay={(
                        <Menu>
                            <Menu.Item>
                                <a href={this.state.login?"/profile":"/login"}>个人信息</a>
                            </Menu.Item>
                            <Menu.Item>
                                <a href={this.state.login?"/order":"/login"}>订单管理</a>
                            </Menu.Item>
                            <Menu.Item onClick={this.logOut.bind(this)}>
                                    <p className="menuItem" >登出</p>
                            </Menu.Item>
                        </Menu>
                    )}>
                                {!this.state.login?(
                                    <Button id="profileOperate" href="/login">登录</Button>
                                ):(
                                    <Button id="profileOperate" href="/profile">{this.state.username}</Button>
                                )}
                    </Dropdown>

                    {this.state.usertype==="Admin" &&
                    <div style={{paddingLeft:1100}}>
                        <Dropdown
                            overlay={(
                                <Menu visible={false}>
                                    <Menu.Item>
                                        <a href="/admin">添加活动</a>
                                    </Menu.Item>
                                    <Menu.Item>
                                        <a href= "/login">订单管理</a>
                                    </Menu.Item>
                                </Menu>
                            )}>
                            <Button> Admin </Button>
                        </Dropdown>
                    </div>
                    }

                    </div>

            </Header>
        )
    }
}
