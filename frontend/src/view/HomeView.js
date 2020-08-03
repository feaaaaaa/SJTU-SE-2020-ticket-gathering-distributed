import React from 'react';
import {HeaderInfo} from "../component/Header";
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

    onChange(a,b,c){
        // console.log(a,b,c)
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
                <Divider plain className="divider"> </Divider>
                <div id="carousel" className="animated flip">
                    <Carousel autoplay afterChange={this.onChange.bind(this)} className="animated pulse infinite slower">
                        <div id="carouselDiv">
                            <img src={require('../resources/carousel.png')} alt="carousel1" id="carouselImg"/>
                        </div>
                        <div>
                            <img src={require('../resources/carousel2.jpg')} alt="carousel2" id="carouselImg"/>
                        </div>
                    </Carousel>
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
