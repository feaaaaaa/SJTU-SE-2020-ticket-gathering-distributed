/**
 * @param this.state.info.activityicon {String}
 * @param this.state.info.website {String}
 * @param this.state.info.timescale {String}
 * @param this.state.info.actor {String}
 * @param data.prices {Array}
 * @param data.prices[].class {Array}
 * @param value.num {number}
 *
 */

import {Button, InputNumber, message, notification, Radio} from 'antd';
import React from "react";
// import "../css/sortPage.css";
import "../css/Detail.css"
import {getDetail} from "../service/ActitemService";
import {addOrder} from "../service/orderServcie";
import moment from 'moment'
import {Redirect} from "react-router-dom";

export class DetailCard extends React.Component{
    constructor(props) {
        super(props);
        this.state = {
            info:{},
            tickets:[],
            times:[],
            time:"",
            prices:[],
            price:"",
            numbers:[],
            number:0,
            success:false,
            chosenNum:1,
            authentication:false,
        }
    }

    renderDetail = async () => {
        const callback = res => {
            if(res!=null&&res.message==="authentication failure") {
                message.error("请先登录");
                localStorage.clear();
                this.setState({authentication:true})
            }
            else {
                console.log("detail:"+JSON.stringify(res));
                let data = res;
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
            }
        }
        let id = await window.localStorage.getItem("actitemid");
        let userid = await window.localStorage.getItem("userId");
        // let id2=this.props.location.query.id;
        console.log(JSON.stringify(this.props));
        console.log(id);
        const token = localStorage.getItem("token");
        console.log("token:" + token);
        //getDetail(id,token,callback);
        getDetail(id, userid, token, callback);
    }


    componentDidMount() {
        this.renderDetail();
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
        });
    }

    openNotificationBeyondStock = type => {
        notification[type]({
            message: 'Notification Title',
            description:
                '余票不足，请减少购票量',
        });
    };

    openNotificationWithoutLogin = type => {
        notification[type]({
            message: 'Notification Title',
            description:
                '未登录，请先登录',
        });
    };

    handleNumberChange = value =>{
        this.setState({chosenNum:value});
    }

    handlePurchase = () =>{
        if(window.localStorage.getItem("userId")===null)
            this.openNotificationWithoutLogin("warning")
        else if(this.state.chosenNum > this.state.number)
            this.openNotificationBeyondStock('warning')
        else{
            const callback = data => {
                console.log("result:"+data);
                if(data.message==="authentication failure") {
                    message.error("请先登录");
                    localStorage.clear();
                    this.setState({authentication:true})
                }
                else this.setState({success:true});
            }
            console.log(parseInt(this.state.price));
            addOrder(parseInt(localStorage.getItem("userId")),parseInt(localStorage.getItem("actitemid")),parseInt(this.state.price),0,
                parseInt(this.state.chosenNum),this.state.time,moment().format('YYYY-MM-DD HH:mm:ss'),localStorage.getItem("token"),callback);
        }
    }

    render() {
        if(this.state.success)
            return <Redirect to={{pathname: "/success"}}/>;
        if(this.state.authentication)
            return <Redirect to={{pathname: "/login"}}/>;
        return(
            <div>
                <img id='Dimg' alt="example" src={this.state.info.activityicon} />
                <p id="Dtitle">{this.state.info.title}</p>
                <p id="Dinfo">地点:{this.state.info.venue}</p>
                <p id="Dinfo">表演者:{this.state.info.actor}</p>
                <p id="Dinfo">票源:{this.state.info.website}</p>
                <p id="Dinfo">时间范围:{this.state.info.timescale}</p>
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
                <p id="Dnote">票价选择：</p>
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
                <div id="Radio">
                <InputNumber min={1} max={10} defaultValue={1} onChange={this.handleNumberChange} />
                </div>
                <br/>
                <div id="Dnote">
                    <Button type="primary" shape="round" id="PurchaseButton" onClick={this.handlePurchase}>立即购买</Button>
                </div>
            </div>
        )
    }
}
