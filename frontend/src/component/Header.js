import React from 'react';
import {Layout, Input, Dropdown, Menu, Avatar, Button, message,Result} from 'antd';
import "../css/headerInfo.css"
import { UserOutlined,MailOutlined, AppstoreOutlined, SettingOutlined } from '@ant-design/icons';
import {Link, Redirect} from 'react-router-dom'
// import Redirect from "react-router-dom/es/Redirect";
const {Header} = Layout;
const { Search } = Input;
const { SubMenu } = Menu;
// import {withRouter} from "react-router-dom";

export class HeaderInfo extends React.Component {
    constructor(props) {
        super(props);
        this.state={
            login:false,
            username:null,
            usertype:null,
            current:"-1"
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
        // console.log("搜索内容："+value+"!!!");
        // localStorage.setItem("search",value);
        // this.setState({
        //     ifsearch:true,
        //     search:value
        // });
        // console.log(this.props.search);
        if(this.props.search!=null){
            this.props.search(value);
        }
    };


    logOut(){
        localStorage.clear();
        message.success("登出成功");
        this.setState({login:false,username:null,usertype:null});
        if(this.props.logOut!=null){
            this.props.logOut();
        }
    }

    handleClick = e => {
        console.log(e.key);
        this.setState({ current: e.key });
    };


    render() {
        if(this.state.current!=="-1"&&!this.state.login)
            return <Redirect to={{pathname: "/login"}}/>;
        if(this.state.current==="1"&&this.state.login)
            return <Redirect to={{pathname: "/profile"}}/>;
        if(this.state.current==="2"&&this.state.login)
            return <Redirect to={{pathname: "/order"}}/>;
        if(this.state.current==="3")
            this.logOut();
        return (
            // <Header className="site-layout-background" style={{padding: 0}}>
            //     <div id="header-content">
            //         <div id="oligei">
            //             <img src={require('../resources/oligei.png')} width="200px" height="80px"/>
            //         </div>
            //         <div id="menusortDiv">
            //             <Button id="menuButton" href="/" type={"primary"}>首页</Button>
            //             <Button id="sortButton" href="/sortPage" type={"primary"}>分类</Button>
            //             <Button id="auctionButton" href="/auction" type={"primary"}>竞价</Button>
            //         </div>
            //         <div id="searchDiv">
            //             <Search
            //                 id="searchInput"
            //                 placeholder="搜索明星、演出、体育赛事"
            //                 onSearch={value => this.toggleSearch(value)}
            //                 enterButton="搜索"
            //                 size="large"
            //             />
            //         </div>
            //         <Avatar id="profileOperate" icon={<UserOutlined />} />
            //         <Dropdown
            //             overlay={(
            //             <Menu>
            //                 <Menu.Item>
            //                     <a href={this.state.login?"/profile":"/login"}>个人信息</a>
            //                 </Menu.Item>
            //                 <Menu.Item>
            //                     <a href={this.state.login?"/order":"/login"}>订单管理</a>
            //                 </Menu.Item>
            //                 <Menu.Item onClick={this.logOut.bind(this)}>
            //                         <p className="menuItem" >登出</p>
            //                 </Menu.Item>
            //             </Menu>
            //         )}>
            //                     {!this.state.login?(
            //                         <Button id="profileOperate" href="/login">登录</Button>
            //                     ):(
            //                         <Button id="profileOperate" href="/profile">{this.state.username}</Button>
            //                     )}
            //         </Dropdown>
            //
            //         {this.state.usertype==="Admin" &&
            //         <div style={{paddingLeft:1100}}>
            //             <Dropdown
            //                 overlay={(
            //                     <Menu visible={false}>
            //                         <Menu.Item>
            //                             <a href="/admin">添加活动</a>
            //                         </Menu.Item>
            //                         <Menu.Item>
            //                             <a href= "/login">订单管理</a>
            //                         </Menu.Item>
            //                     </Menu>
            //                 )}>
            //                 <Button> Admin </Button>
            //             </Dropdown>
            //         </div>
            //         }
            //
            //         </div>
            //
            // </Header>
            <div id="header">
                <div id="oligei" style={{paddingLeft:150}} className="wow slideInLeft" >
                    <img src={require('../resources/oligei.png')} width="200px" height="80px"/>
                </div>
                <div className="wow slideInLeft" id="bar" data-wow-delay=".5s">
                    <Menu onClick={this.handleClick} mode="horizontal" style={{fontSize: 18, fontFamily: ""}}>
                        <Menu.Item key="mail" href="/">
                            <Link to="/" onClick={() => {this.setState({current: "-1"})}}><p> 首 页 </p></Link>
                        </Menu.Item>
                        <Menu.Item key="app" href="/sortPage">
                            <Link to="/sortPage"><p> 分 类 </p></Link>
                        </Menu.Item>
                        <Menu.Item key="auction">
                            <Link to="/auction"><p> 竞 价 </p></Link>
                        </Menu.Item>
                        <SubMenu onTitleClick={() => {this.setState({current: "1"})}}
                                 title={this.state.login ? this.state.username : "登 录"} style={{paddingBottom: 17}}>
                            <Menu.Item key="1">个人信息</Menu.Item>
                            <Menu.Item key="2">订单管理</Menu.Item>
                            <Menu.Item key="3">登出</Menu.Item>
                        </SubMenu>
                    </Menu>
                </div>
                <div id="searchDiv" style={{paddingTop:24}} className="wow slideInRight" data-wow-delay=".5s">
                    <Search
                        id="searchInput"
                        placeholder="搜索明星、演出、体育赛事"
                        onSearch={value => this.toggleSearch(value)}
                        enterButton="搜索"
                        size="large"
                    />
                </div>
                {this.state.usertype==="Admin" &&
                            <div style={{paddingLeft:1200,paddingTop:30}} className="wow slideInRight" data-wow-delay=".5s">
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
        // https://www.cmdy5.com/guochanju/bailuyuan.html
        )
    }
}
