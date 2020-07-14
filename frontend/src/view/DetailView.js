import { Card,Collapse,Menu, Dropdown, Button } from 'antd';
import React from "react";
import {DetailCard} from "../component/DetailCard";
import {PurchaseNotice} from "../component/PurchaseNotice";
import {WatchNotice} from "../component/WatchNotice";
import {RecommendList} from "../component/RecommendList";
import {HeaderInfo} from "../component/Header";
import {FooterInfo} from "../component/Footer";
import "../css/Detail.css";

export class DetailView extends React.Component{
    constructor(props) {
        super(props);
        this.state = {
            notice:true,
            info:{},
            // info:    {actors:"  ",	category:"话剧歌剧 ",	city:"上海 ",	title:"上海话剧艺术中心·后浪新潮演出季 原创话剧 《谎言背后》 ",	price:180.0 ,	price_str:"180-280 ",	time:"2020.07.07-07.12 ",	subcategory:"话剧 ",	venue:"上海话剧艺术中心-戏剧沙龙 ",	imgurl:"https://img.alicdn.com/bao/uploaded/https://img.alicdn.com/imgextra/i4/2251059038/O1CN018wyvEj2GdSDYJ4raf_!!2251059038.jpg"},
        }
    }

    componentDidMount() {
        console.log(JSON.parse(window.localStorage.getItem("detail")));
        this.setState({info:JSON.parse(window.localStorage.getItem("detail"))});
        console.log(this.state.info.title);
    }

    handleClick = e => {
        console.log('click ');
    };

    handleClickNotice1 = e => {
        console.log('click notice 1');
        this.setState({notice:true});
    };

    handleClickNotice2 = e => {
        console.log('click notice 2');
        this.setState({notice:false});
    };

    Noting = () => {
        if(this.state.notice)
            return <PurchaseNotice/>;
        else
            return <WatchNotice/>;
    }
    render() {
        const Noting = this.Noting;
        return(
            <div>
                <HeaderInfo/>
                <div id="Detail" style={{paddingTop:100,float:"left",marginLeft:-300}}>
                    <DetailCard info={this.state.info}/>
                    <Menu onClick={this.handleClick} mode="horizontal">
                        <Menu.Item onClick={this.handleClickNotice1}>
                            购票须知
                        </Menu.Item>
                        <Menu.Item onClick={this.handleClickNotice2}>
                            观影须知
                        </Menu.Item>
                    </Menu>
                    <Noting />
                </div>
                <RecommendList/>
                <div style={{paddingTop:1500}}>
                    <FooterInfo />
                </div>
            </div>
        )
    }
}