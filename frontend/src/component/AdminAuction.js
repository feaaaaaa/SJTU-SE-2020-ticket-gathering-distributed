import {Button, InputNumber, message, notification, Radio,DatePicker } from 'antd';
import React from "react";
import "../css/Detail.css"
import {getDetail} from "../service/ActitemService";
import {addAuction} from "../service/AuctionService";
import moment from 'moment'
import {Redirect} from "react-router-dom";
const { RangePicker } = DatePicker;

export class AdminAuction extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            authentication:false,
            authorization:false,
            info:{},
            tickets:[],
            times:[],
            time:"",
            prices:[],
            targetprice:0,
            numbers:[],
            number:0,
            chosenNum:1,
            ddl:"",
            success:false,
            flag1:false,
            flag2:false,
            flag3:false,
        }
    }

    async componentDidMount() {
        const callback = res => {
            if(res!=null) {
                // if(res!=null&&res.message==="authentication failure") {
                if (res.status === -100) {
                    message.error("请先登录");
                    localStorage.clear();
                    this.setState({authentication: true});
                }
                // if(res!=null&&res.message==="authorization failure"){
                else if (res.status === -101) {
                    message.error("没有权限");
                    localStorage.clear();
                    this.setState({authorization: true});
                } else if (res.status === 200) {
                    console.log("detail:" + JSON.stringify(res));
                    let data = res.data;
                    this.setState({
                        info: data,
                        tickets: data.prices
                    });
                    let times = [];
                    this.state.tickets.map(function (value, key) {
                        times.push(value.time);
                    })
                    let prices = [];
                    let numbers = [];
                    this.state.tickets[0].class.map(function (value, key) {
                        prices.push(value.price.toString());
                        numbers.push(value.num.toString());
                    })
                    this.setState({
                        times: times,
                        time: times[0],
                        prices: prices,
                        numbers: numbers,
                        price: prices[0],
                        number: numbers[0],
                    });
                    console.log(this.state);
                } else
                    message.error(res.msg);
            }
        }
        let id = await window.localStorage.getItem("actitemid");
        let userid = await window.localStorage.getItem("userId");
        // let id2=this.props.location.query.id;
        // console.log(JSON.stringify(this.props));
        // console.log(id);
        const token = localStorage.getItem("token");
        // console.log("token:" + token);
        //getDetail(id,token,callback);
        console.log("actitemid:"+id);
        getDetail(id, userid, token, callback);
    }

    onChangeTime = e => {
        console.log(e);
        console.log('radio4 checked', e.target.value);
        let i=0;
        this.state.times.map(function (value,key) {
            if(value === e.target.value)
                i = key;
        })
        let prices=[];
        let numbers=[];
        this.state.tickets[i].class.map(function (value,key) {
            prices.push(value.price.toString());
            numbers.push(value.num);
        })
        this.setState({
            time: e.target.value,
            prices:prices,
            numbers:numbers,
            price:prices[0],
            targetprice:prices[0],
            number:numbers[0],
        });
    };

    onChangePrice = e =>{
        let i=0;
        this.state.prices.map(function (value,key) {
            if(value === e.target.value)
                i = key;
        })
        this.setState({
            price:e.target.value,
            number:this.state.numbers[i],
            targetprice:e.target.value,
        });
    }

    handleNumberChange = value =>{
        this.setState({
            chosenNum:value,
            flag1:true,
        });
    }

    handlePriceChange = value =>{
        this.setState({
            targetprice:value,
            flag2:true,
        });
    }

    openNotificationSuccess = type => {
        notification[type]({
            message: 'Notification Title',
            description:
                '发布竞价成功',
        });
    };

    openNotificationFailure = type => {
        notification[type]({
            message: 'Notification Title',
            description:
                '发布竞价失败',
        });
    };

    handleAddAuction = () =>{
        const callback = data =>{
            if(!data)
                this.openNotificationFailure("warning");
            else {
                this.openNotificationSuccess("success");
                this.setState({success: true});
            }
        }
        if(!(this.state.flag1&&this.state.flag2&&this.state.flag3))
            this.openNotificationFailure("warning")
        else
            addAuction(parseInt(localStorage.getItem("actitemid")),this.state.ddl,this.state.time,parseInt(this.state.price),this.state.targetprice,this.state.chosenNum,localStorage.getItem("token"),callback);
        // {
        //     console.log("add!!!"+localStorage.getItem("actitemid"),this.state.ddl,this.state.time,parseInt(this.state.price),this.state.targetprice,this.state.chosenNum);
        // }
    }

    disabledDate = current => {
        return current && current < moment().endOf('day');
    }

    onTimeChange = (date,dateString) =>{
        console.log(date,dateString);
        this.setState({
            ddl:dateString,
            flag3:true,
        });
    }

    render() {
        if(this.state.success)
            return <Redirect to={{pathname: "/sortPage"}}/>;
        if(this.state.authentication||this.state.authorization)
            return <Redirect to={{pathname: "/login"}}/>;
        return(
            <div >
                <img id='Dimg' alt="example" src={this.state.info.activityicon} />
                <p id="Dtitle">{this.state.info.title}</p>
                <p id="Dinfo">地点：{this.state.info.venue}</p>
                <p id="Dinfo">表演者：{this.state.info.actor}</p>
                <p id="Dinfo">票源：{this.state.info.website}</p>
                <p id="Dinfo">时间范围：{this.state.info.timescale}</p>
                <p id="Dinfo">剩余票数:{this.state.number}</p>
                <p id="Dnote">时间选择:</p>
                <div id="Radio">
                    <Radio.Group
                        options={this.state.times}
                        onChange={this.onChangeTime}
                        value={this.state.time}
                        optionType="button"
                        buttonStyle="solid"
                    />
                </div>
                <br/>
                <p id="Dnote">原始票价选择：</p>
                <div id="Radio">
                    <Radio.Group
                        options={this.state.prices}
                        onChange={this.onChangePrice}
                        value={this.state.price}
                        optionType="button"
                        buttonStyle="solid"
                    />
                </div>
                <br/>
                <p id="Dnote">拍卖票数:</p>
                <div id="Radio">
                    <InputNumber min={1} max={this.state.number} defaultValue={1} onChange={this.handleNumberChange} />
                </div>
                <br/>
                <p id="Dnote">起拍价格:</p>
                <div id="Radio">
                    <InputNumber min={this.state.price} defaultValue={1} onChange={this.handlePriceChange} />
                </div>
                <br/>
                <p id="Dnote">截止日期:</p>
                <div id="Radio">
                    <DatePicker
                    onChange={this.onTimeChange}
                    format="YYYY-MM-DD HH:mm:ss"
                    disabledDate={this.disabledDate}
                    showTime={{ defaultValue: moment('00:00:00', 'HH:mm:ss') }}
                    />
                </div>
                <br/>
                <div id="Dnote">
                    <Button type="primary" shape="round" id="PurchaseButton" onClick={this.handleAddAuction}>开放竞价</Button>
                </div>
            </div>
        )
    }
}