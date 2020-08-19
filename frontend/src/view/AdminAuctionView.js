import React from 'react';
import {HeaderInfo} from "../component/Header";
import {FooterInfo} from "../component/Footer";
import {AdminAuction} from "../component/AdminAuction";
import {Redirect} from "react-router-dom";
import "../css/Detail.css";
import {RecommendList} from "../component/RecommendList";


export class AdminAuctionView extends React.Component{
    constructor(props) {
        super(props);
        this.state={
            isSearch:false,
            search:null
        };
        this.onSearch=this.onSearch.bind(this);
    }

    onSearch(value){
        this.setState({isSearch:true,search:value});
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
        return (
            <div>
                <HeaderInfo search={this.onSearch}/>
                <div id="Detail" style={{paddingTop:150,float:"left",marginLeft:-100,width:"80%"}}>
                    <AdminAuction />
                </div>
                <div style={{paddingTop:1000}}>
                    <FooterInfo />
                </div>
            </div>
        );
    }
}