import React from 'react';
import {HeaderInfo} from "../component/Header";
import {Logo} from "../component/Logo";
import {Mycarousel} from "../component/Mycarousel";
import {FooterInfo} from "../component/Footer";
import { Carousel,Divider,BackTop } from 'antd';
import "../css/home.css"
import {HomeCategoryPage} from "../component/HomeCategoryPage";
import {Navigate} from "../component/Navigate";
import {Redirect} from "react-router-dom";

export class HomeView extends React.Component{
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
                {/*<Divider plain className="divider"> </Divider>*/}
                <Logo/>
                <div id="carousel" >
                    <Mycarousel/>
                </div>
                <Navigate/>
                <div id="homeCategoryPageDiv">
                    <HomeCategoryPage/>
                </div>
                {/*<div style={{paddingTop:1500}}>*/}
                    <FooterInfo/>
                    <BackTop/>
                {/*</div>*/}
            </div>
        )
    }
}
