import React from 'react';
import { sports} from "../const/activity";
import {Avatar, Button, Divider, Dropdown, Input, Layout, List, Menu, message} from "antd";
import "../css/sortPage.css"
import "../css/headerInfo.css"
import {RecommendList} from "../component/RecommendList";
import {category_search, search} from "../service/searchService";
import {async} from "fast-glob";
import Redirect from "react-router-dom/es/Redirect";
import { UserOutlined } from '@ant-design/icons';
import {getAuctions} from "../service/AuctionService";
import {SortPageCard} from "../component/SortPageCard";
import {AuctionCard} from "../component/AuctionCard";
import {HeaderInfo} from "../component/Header";
const {Header} = Layout;
const { Search } = Input;


export class AuctionView extends React.Component{
    constructor(props) {
        super(props);
        this.state={
            auctions:[],
            search:null,
            login:false,
            username:null,
            ifauthen:false,
            ifauthor:false,
            isSearch:false,
            interval:2000,
            updateInterval: null
        }
    }

    toggleSearch=(value)=>{
        this.setState({isSearch:true,search:value});
    }

    dateFormat(fmt, date) {
        let ret;
        const opt = {
            "Y+": date.getFullYear().toString(),        // 年
            "m+": (date.getMonth() + 1).toString(),     // 月
            "d+": date.getDate().toString(),            // 日
            "H+": date.getHours().toString(),           // 时
            "M+": date.getMinutes().toString(),         // 分
            "S+": date.getSeconds().toString()          // 秒
            // 有其他格式化字符需求可以继续添加，必须转化成字符串
        };
        for (let k in opt) {
            ret = new RegExp("(" + k + ")").exec(fmt);
            if (ret) {
                fmt = fmt.replace(ret[1], (ret[1].length == 1) ? (opt[k]) : (opt[k].padStart(ret[1].length, "0")))
            };
        };
        return fmt;
    }

    componentDidMount() {
        // this.handleRender();
        const callback = data =>{
            if (data != null) {
                let trueData = data.data;
                console.log("auctionView return:" + JSON.stringify(data));
                let array = [];
                for (let key in trueData) {
                    let obj = trueData[key];
                    let ddl=obj.ddl.substr(0,19).replace('T',' ');
                    let date = new Date();
                    // let test=new Date();
                    // test.setDate(25);
                    // test.setHours(23);
                    // test.setMinutes(0);
                    // test.setSeconds(0);
                    // let fmtDateTest=this.dateFormat("YYYY-mm-dd HH:MM:SS", test);
                    let fmtDateNow=this.dateFormat("YYYY-mm-dd HH:MM:SS", date);
                    // console.log("test:");console.log(fmtDateTest);
                    // console.log("ddl:");console.log(ddl);
                    if(fmtDateNow<ddl)
                        array.push(obj);
                }
                console.log("map to array:");
                console.log(array);
                if (data.status === 200) this.setState({auctions: array});
                else message.error(data.msg);
            }
        }
        getAuctions(localStorage.getItem("token"),callback);
        const token=localStorage.getItem("token");
        let errorCnt=0;
        this.state.updateInterval=setInterval(()=>{
            getAuctions(token,(data)=>{
                if (data != null) {
                    let trueData = data.data;
                    console.log("fresh:" + JSON.stringify(data));
                    let array = [];
                    for (let key in trueData) {
                        let obj = trueData[key];
                        let ddl=obj.ddl.substr(0,19).replace('T',' ');
                        let date = new Date();
                        let fmtDateNow=this.dateFormat("YYYY-mm-dd HH:MM:SS", date);
                        if(fmtDateNow<ddl)
                            array.push(obj);
                    }
                    if (data.status === 200) this.setState({auctions: array});
                    else {
                        message.error(data.msg);
                        errorCnt++;
                        if(errorCnt>3)
                            this.setState({interval:3000});
                        if(errorCnt>10)
                            this.setState({interval:2*60*1000});
                    }
                }
            })
        },this.state.interval);
    }

    componentWillUnmount() {
        clearInterval(this.state.updateInterval);
    }

    // handleRender = () =>{
    //     const callback = data =>{
    //         if (data != null) {
    //             let trueData = data.data;
    //             console.log("auctionView return:" + JSON.stringify(data));
    //             let array = [];
    //             for (let key in trueData) {
    //                 let obj = trueData[key];
    //                 obj.id = key;
    //                 array.push(obj);
    //             }
    //             console.log("map to array:");
    //             console.log(array);
    //             if (data.status === 200) this.setState({auctions: array});
    //             else message.error(data.msg);
    //         }
    //     }
    //     getAuctions(localStorage.getItem("token"),callback);
    // }

    getRenderCallback = (value) =>{
        if(value ===1)
            console.log("render again");
            this.handleRender();
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
        return(
            <div >
                <HeaderInfo search={value => this.toggleSearch(value)}/>
                <Divider plain className="divider"> </Divider>
                <div id="sortPageDiv" style={{paddingBottom:100,paddingTop:150}}>
                    <List
                        grid={{gutter: 10, column: 1}}
                        pagination={{
                            onChange: page => {
                                console.log(page);
                            },
                            pageSize: 10,
                        }}
                        footer={
                            <div style={{textAlign:"center"}}>
                                <b>O_ligei ! </b> footer part
                            </div>
                        }
                        dataSource={this.state.auctions}
                        renderItem={item => (
                            <List.Item>
                                <AuctionCard info={item} getRenderCallback ={this.getRenderCallback.bind(this)}/>
                            </List.Item>
                        )}
                    />
                </div>
            </div>
        )
    }
}