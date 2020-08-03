import React from "react";
import "../css/mycarousel.css"
import {Carousel} from "antd";


export class Mycarousel extends React.Component{


    onChange(a,b,c){
        // console.log(a,b,c)
    }

    render() {
        return (
            <div className="wow fadeInDown" data-wow-duration="2s"  style={{marginTop:-40,marginBottom:60}} data-wow-delay="1s">
            <Carousel autoplay afterChange={this.onChange.bind(this)} effect="fade">
                <img src={require('../resources/1.jpg')} alt="carousel1" className="carouselImg"/>
                <img src={require('../resources/2.jpg')} alt="carousel1" className="carouselImg"/>
                <img src={require('../resources/3.jpg')} alt="carousel2" className="carouselImg"/>
                <img src={require('../resources/4.jpg')} alt="carousel2" className="carouselImg"/>
                <img src={require('../resources/5.jpg')} alt="carousel2" className="carouselImg"/>
            </Carousel>
            </div>
        );
    }
}