import React from 'react';
import {Admin} from "../component/Admin";
import {HeaderInfo} from "../component/Header";
import {Redirect} from "react-router-dom";


export class AdminView extends React.Component{
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
        return(
            <div>
                <HeaderInfo search={this.onSearch}/>
                <div style={{paddingTop:40}}>
                    <Admin/>
                </div>
            </div>
        );
    }
}