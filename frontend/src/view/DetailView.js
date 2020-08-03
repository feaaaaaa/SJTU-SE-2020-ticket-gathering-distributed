import { Card,Collapse,Menu, Dropdown, Button } from 'antd';
import React from "react";
import {DetailCard} from "../component/DetailCard";
import {PurchaseNotice} from "../component/PurchaseNotice";
import {WatchNotice} from "../component/WatchNotice";
import {RecommendList} from "../component/RecommendList";
import {HeaderInfo} from "../component/Header";
import {FooterInfo} from "../component/Footer";
import "../css/Detail.css";
import {Redirect} from "react-router-dom";

export class DetailView extends React.Component{
    constructor(props) {
        super(props);
        this.state = {
            notice:true,
            info:{},
            isSearch:false,
            search:null,
            logOut:false,
        }
        this.onSearch=this.onSearch.bind(this);
        this.logOut=this.logOut.bind(this);
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
    };

    onSearch(value){
        this.setState({isSearch:true,search:value});
    }

    logOut(){
        this.setState({logOut:true});
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
        const Noting = this.Noting;
        return(
            <div>
                <HeaderInfo logOut={this.logOut} search={this.onSearch}/>
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
                <RecommendList info={this.state.info}/>
                <div style={{paddingTop:2000}}>
                    <FooterInfo />
                </div>
            </div>
        )
    }
}
