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
            isSearch:false
        }
    }

    toggleSearch=(value)=>{
        this.setState({isSearch:true,search:value});
    }

    componentDidMount() {
        this.handleRender();
    }

    handleRender = () =>{
        const callback = data =>{
            if(data!=null) {
                let trueData = data.data;
                console.log("auctionView return:");
                console.log("数据：" + data);
                if (data.status === 200) this.setState({auctions: trueData});
                else message.error(data.msg);
            }
        }
        getAuctions(localStorage.getItem("token"),callback);
    }

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