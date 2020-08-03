import React from 'react';
import {HeaderInfo} from "../component/Header";
import {AdminAuction} from "../component/AdminAuction";
import {Redirect} from "react-router-dom";


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
                <div style={{paddingTop:40}}>
                    <AdminAuction />
                </div>
            </div>
        );
    }
}