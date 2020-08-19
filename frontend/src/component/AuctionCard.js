import {Card, Collapse, Menu, Dropdown, Button, Radio, notification, Space, InputNumber, Modal, message} from 'antd';
import React from "react";
import "../css/Auction.css"
import {addOrder} from "../service/orderServcie";
import moment from "moment";
import {joinAuctions} from "../service/AuctionService";

export class AuctionCard extends React.Component{

    constructor(props) {
        super(props);
        this.state={
            visible:false,
            price:this.props.info.price,
            clickAuction:true
        }
    }

    showModal = () => {
        this.setState({
            visible: true,
        });
    };

    handleOk = e => {
        if(this.props.info.price >= this.state.price)
            this.openNotificationLowerPrice('warning')
        else if(this.state.clickAuction){
            this.setState({clickAuction:false});
            const callback = data => {
                if (data != null) {
                    if (data.data === -1)
                        this.openNotificationIsOver("warning");
                    else if (data.data === -2)
                        this.openNotificationLowerPrice("warning");
                    else if(data.status===200){
                        this.openNotificationPurchase("success");
                    }else message.error(data.msg);
                    this.setState({visible: false});
                    this.props.getRenderCallback(1);
                }
            }
            joinAuctions(this.props.info.auctionid,parseInt(localStorage.getItem("userId")),this.state.price,localStorage.getItem("token"),callback);
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
        this.setState({price:value});
        console.log('changed', value);
    }

    openNotificationWithoutLogin = type => {
        notification[type]({
            message: 'Notification Title',
            description:
                '未登录，请先登录',
        });
    };

    openNotificationLowerPrice = type => {
        notification[type]({
            message: 'Notification Title',
            description:
                '出价必须高于当前价格',
        });
    };

    openNotificationIsOver = type => {
        notification[type]({
            message: 'Notification Title',
            description:
                '拍卖已结束',
        });
    };

    openNotificationPurchase = type => {
        notification[type]({
            message: 'Notification Title',
            description:
                '拍卖成功',
        });
    };

    // AuctionButton = () => {
    //     if(localStorage.getItem("userId") === null)
    //         return <Button type="primary" onClick={this.openNotificationWithoutLogin("warning")}>未登录</Button>
        // else if (this.props.info.userid === parseInt(localStorage.getItem("userId"))) {
        //     return <Button type="primary" danger onClick={this.showModal}>当前最高，可继续加价</Button>;
        // }
        // return <Button type="primary" onClick={this.showModal}>竞价</Button>;
    // }


    render() {
        // const {AuctionButton} = this.AuctionButton;
        const AuctionButtion = localStorage.getItem("userId") === null ? (
        <Button type="primary" onClick={this.openNotificationWithoutLogin("warning")}>未登录</Button>
        ):(this.props.info.userid === parseInt(localStorage.getItem("userId"))?(
        <Button type="primary" danger onClick={this.showModal}>当前最高，可继续加价</Button>
            ):(<Button type="primary" onClick={this.showModal}>竞价</Button>));
        return(
            <div>
                <img className='A-image' alt="example" src={this.props.info.activityIcon} />
                <p id="A-title">{this.props.info.title}</p>
                <p id="A-info">表演者:{this.props.info.actor}</p>
                <p id="A-info">地点:{this.props.info.venue}</p>
                <p id="A-info">活动时间:{this.props.info.showtime.substr(0,19).replace("T"," ")}</p>
                <p id="A-info">截止日期:{this.props.info.ddl.substr(0,19).replace("T"," ")}</p>
                <p id="A-info">票数:{this.props.info.amount}</p>
                <p id="A-price">当前价格:{'¥'+this.props.info.price}</p>
                <div id="A-button-align">
                    {AuctionButtion}
                    {/*<AuctionButton/>*/}
                    {/*<Button>竞价</Button>*/}
                </div>
                <Modal
                    title="确认竞价"
                    visible={this.state.visible}
                    onOk={this.handleOk}
                    onCancel={this.handleCancel}
                >
                    <InputNumber
                        defaultValue={this.props.info.price}
                        formatter={value => `$ ${value}`.replace(/\B(?=(\d{3})+(?!\d))/g, ',')}
                        parser={value => value.replace(/\$\s?|(,*)/g, '')}
                        onChange={this.onChange}
                    />
                </Modal>
            </div>
        )
    }
}