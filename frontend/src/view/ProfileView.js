import React from 'react';
import {Descriptions, Badge, message} from "antd";
import "../css/profile.css"
import {HeaderInfo} from "../component/Header";
import {getPersonInfo} from "../service/userService";
import {Redirect} from "react-router-dom";

export class ProfileView extends React.Component{
    constructor(props) {
        super(props);
        this.state={
            userInfo:null,
            ifauthen:false,
            logOut:false,
            isSearch:false,
            search:null
        }
        this.onSearch=this.onSearch.bind(this);
        this.logOut=this.logOut.bind(this);
    }

    logOut(){
        this.setState({logOut:true});
    }

    onSearch(value){
        this.setState({isSearch:true,search:value});
    }

    async componentDidMount() {
        const callback = async (data) => {
            if (data.message === "authentication failure") {
                await this.setState({ifauthen: true});
                localStorage.clear();
            } else await this.setState({userInfo: data})
        };
        let userId = localStorage.getItem("userId");
        await getPersonInfo(userId, localStorage.getItem("token"), callback);
    }

    render() {
        if(this.state.isSearch){
            console.log("jumping...");
            return <Redirect to={{
                pathname: "/sortPage",
                state:{
                    search:this.state.search
                }
            }}/>;
        }
        if(this.state.logOut){
            return <Redirect to={{pathname: "/"}}/>;
        }
        else{
            if(this.state.ifauthen){
                message.error("请先登录");
                return <Redirect to={{pathname: "/login"}}/>;
            }
            else if (this.state.userInfo == null) {
                return (
                    <p>404</p>
                )
            }
            else {
                return (
                    <div>
                        <HeaderInfo logOut={this.logOut} search={this.onSearch}/>
                        <div style={{paddingTop:150}}>
                            <div id="profileDiv">
                                <Descriptions title="个人信息" bordered>
                                    <Descriptions.Item label="用户名">{this.state.userInfo.username}</Descriptions.Item>
                                    <Descriptions.Item label="性别">{this.state.userInfo.gender}</Descriptions.Item>
                                    <Descriptions.Item label="用户类型">{this.state.userInfo.type?("用户"):("管理员")}</Descriptions.Item>
                                    <Descriptions.Item label="电话号码">{this.state.userInfo.phone}</Descriptions.Item>
                                    <Descriptions.Item label="邮箱" span={2}>
                                        {this.state.userInfo.email}
                                    </Descriptions.Item>
                                    {/*<Descriptions.Item label="Status" span={3}>*/}
                                    {/*    <Badge status="processing" text="Running" />*/}
                                    {/*</Descriptions.Item>*/}
                                    <Descriptions.Item label="账户余额">$80.00</Descriptions.Item>
                                    {/*<Descriptions.Item label="Discount">$20.00</Descriptions.Item>*/}
                                    {/*<Descriptions.Item label="Official Receipts">$60.00</Descriptions.Item>*/}
                                    <Descriptions.Item label="Config Info">
                                        <img alt="userIcon" style={{height:100}} src={this.state.userInfo.personIcon}/>
                                    </Descriptions.Item>
                                </Descriptions>
                            </div>
                        </div>
                    </div>
                )
            }
        }
    }
}