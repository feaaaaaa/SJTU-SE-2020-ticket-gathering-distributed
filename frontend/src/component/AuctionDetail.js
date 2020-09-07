import React from "react";
import {Button, Card, InputNumber, message, Modal, notification, Radio} from "antd";
import "../css/Detail.css"
import {getPriceById, joinAuctions} from "../service/AuctionService";


export class AuctionDetail extends React.Component{
    constructor(props) {
        super(props);
        this.state={
            activity:[],
            visible:false,
            clickAuction:true,
            price:0,
            interval:1000,
            updateInterval: null// 定时器标识，清除用
        }
    }
    
    componentDidMount() {
        const auctionInfo=localStorage.getItem("auctionInfo");
        console.log("auctionInfo:");
        console.log(auctionInfo);
        let parsedData;

        if(auctionInfo!=null){
            parsedData=JSON.parse(auctionInfo);
            this.setState({
                activity:parsedData
            })
            this.setState({
                price:parsedData.price
            })
        }
        const Id=parsedData.auctionid;
        const token=localStorage.getItem("token");
        let errorCnt=0;
        this.state.updateInterval=setInterval(()=>{
            getPriceById(Id,token,(res)=>{
                console.log("fresh:");
                console.log(res);
                if(res!=null){
                    if(res.status===200){
                        let auctionInfo=this.state.activity;
                        auctionInfo.price=res.data;
                        this.setState({activity:auctionInfo});
                    }
                    else{
                        errorCnt++;
                        message.error(res.msg);
                        if(errorCnt>3)
                            this.setState({interval:2000});
                        if(errorCnt>10)
                            this.setState({interval:60*1000});
                    }
                }
            })
        },this.state.interval)
    }

    componentWillUnmount() {
        clearInterval(this.state.updateInterval);
    }

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

    openNotificationWithoutEnoughMoney = type => {
        notification[type]({
            message: 'Notification Title',
            description:
                '余额不足，请充值',
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

    showModal = () => {
        this.setState({
            visible: true,
        });
    };


    handleOk = () => {
        if(this.state.activity.price >= this.state.price)
            this.openNotificationLowerPrice('warning');
        else if(this.state.clickAuction){//Todo
            this.setState({clickAuction:false});
            const callback = data => {
                if (data != null) {
                    if (data.data === -1)
                        this.openNotificationIsOver("warning");
                    else if (data.data === -2)
                        this.openNotificationLowerPrice("warning");
                    else if (data.data === -3)
                        this.openNotificationWithoutEnoughMoney("warning");
                    else if(data.status===200){
                        this.openNotificationPurchase("success");
                    }else message.error(data.msg);
                    this.setState({visible: false});
                    // this.props.getRenderCallback(1);
                }
            }
            joinAuctions(this.state.activity.auctionid,parseInt(localStorage.getItem("userId")),this.state.price,localStorage.getItem("token"),callback);
            setTimeout(()=>{this.setState({clickAuction:true})}, 2000);
        }else message.error("点击太快了，休息一会吧")
    };

    handleCancel = () => {
        this.setState({
            visible: false,
        });
    };
    
    render() {
        const AuctionButtion = localStorage.getItem("userId") === null ? (
            <Button type="primary" onClick={this.openNotificationWithoutLogin("warning")}>未登录</Button>
        ):(<Button type="primary" onClick={this.showModal}>出价</Button>);
        return (
            <div>
                <img id='Dimg' alt="example" src={this.state.activity.activityIcon} />
                <p id="Dtitle">{this.state.activity.title}</p>
                <p id="Dinfo">地点:{this.state.activity.venue}</p>
                <p id="Dinfo">表演者:{this.state.activity.actor}</p>
                <p id="Dinfo">数量:{this.state.activity.amount}</p>
                <p id="Dinfo">演出时间:{this.state.activity.showtime}</p>
                <p id="Dinfo">竞拍截止时间:{this.state.activity.ddl}</p>
                <p className="price">目前价格: {this.state.activity.price}</p>
                <div style={{paddingLeft:500}}>
                {AuctionButtion}
                </div>
                <Modal
                    title="确认竞价"
                    visible={this.state.visible}
                    onOk={this.handleOk}
                    onCancel={this.handleCancel}
                >
                    <InputNumber
                        defaultValue={this.state.activity.price}
                        formatter={value => ` ${value}`.replace(/\B(?=(\d{3})+(?!\d))/g, ',')}
                        parser={value => value.replace(/\$\s?|(,*)/g, '')}
                        onChange={this.onChange}
                    />
                </Modal>
            </div>

        );
    }
}

// Integer auctionid,
//     Integer price,
//     Integer userid,
//     String activityIcon
