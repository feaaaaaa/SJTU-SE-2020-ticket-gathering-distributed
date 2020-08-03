import React from 'react';
import {Order} from "../component/Order";
import {HeaderInfo} from "../component/Header";
import {FooterInfo} from "../component/Footer";
import { Redirect } from 'react-router-dom'


export class OrderView extends React.Component{
    constructor(props) {
        super(props);
        this.state={
            logOut:false,
            isSearch:false,
            search:null
        };
        this.onSearch=this.onSearch.bind(this);
        this.logOut=this.logOut.bind(this);
    }

    logOut(){
        this.setState({logOut:true});
    }

    onSearch(value){
        this.setState({isSearch:true,search:value});
    }

    render(){
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
        return(
            <div>
                <HeaderInfo logOut={this.logOut} search={this.onSearch}/>
                <Order/>
                <p> </p>
                <FooterInfo/>
            </div>
        )
    }
}