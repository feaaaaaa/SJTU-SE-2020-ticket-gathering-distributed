/**
 *@param this.state.userInfo.balance {number}
 */
import React from 'react';
import {Descriptions, Badge, message,Button,InputNumber, Modal} from "antd";
import "../css/profile.css"
import {HeaderInfo} from "../component/Header";
import {getPersonInfo} from "../service/userService";
import {Redirect} from "react-router-dom";
import {joinAuctions} from "../service/AuctionService";
import {recharge} from "../service/userService";

export class ProfileView extends React.Component{
    constructor(props) {
        super(props);
        this.state={
            increment:0,
            visible:false,
            userInfo:null,
            ifauthen:false,
            logOut:false,
            isSearch:false,
            search:null,
            clickAuction:true,
            balance:0,
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
            if(data.status===-100 ||data.status===201){
                await this.setState({ifauthen: true});
                localStorage.clear();
            } else await this.setState({
                userInfo: data.data,
                balance:data.data.balance
            })
        };
        let userId = localStorage.getItem("userId");
        await getPersonInfo(userId, localStorage.getItem("token"), callback);
    }

    showModal = () => {
        this.setState({
            visible: true,
        });
    };

    handleOk = e => {
        if(this.state.clickAuction){
            this.setState({clickAuction:false});
            const callback = data => {
                if (data != null) {
                    if(data.status!==200)
                         message.error(data.msg);
                    this.setState({visible: false});
                    this.setState({balance:data.data});
                }
            }
            recharge(parseInt(localStorage.getItem("userId")),this.state.increment,localStorage.getItem("token"),callback);
            setTimeout(()=>{this.setState({clickAuction:true})}, 2000);
        }else message.error("点击太快了，休息一会吧")
    };

    handleCancel = e => {
        console.log(e);
        this.setState({
            visible: false,
        });
    };

    onChange = value => {
        this.setState({increment:value});
        console.log('changed', value);
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
                                    <Descriptions.Item label="账户余额">
                                        ${this.state.balance}
                                        <Button onClick={this.showModal}>充值</Button>
                                        <Modal
                                            title="充值"
                                            visible={this.state.visible}
                                            onOk={this.handleOk}
                                            onCancel={this.handleCancel}
                                        >
                                            <InputNumber
                                                defaultValue={this.state.increment}
                                                formatter={value => `$ ${value}`.replace(/\B(?=(\d{3})+(?!\d))/g, ',')}
                                                parser={value => value.replace(/\$\s?|(,*)/g, '')}
                                                onChange={this.onChange}
                                            />
                                        </Modal>
                                    </Descriptions.Item>
                                    {/*<Descriptions.Item label="Discount">$20.00</Descriptions.Item>*/}
                                    {/*<Descriptions.Item label="Official Receipts">$60.00</Descriptions.Item>*/}
                                    <Descriptions.Item label="头像">
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