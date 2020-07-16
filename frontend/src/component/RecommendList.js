import React from "react";
import {Card} from 'antd';
import "../css/recommendList.css"
import { sports} from "../const/activity";

export class RecommendList extends React.Component{

    render(){
        return(
            <div className="site-card-wrapper" id="recommendList">
                    <p id="recommendp">猜你喜欢</p>
                <Card className="recommendCard"  hoverable={true}>
                    <img alt="example" src={sports[0].activityIcon} className="img"/>
                        <p className="box">{sports[0].title}</p>
                        <p className="venue">{sports[0].venue}</p>
                        <p className="venue">{sports[0].timescale}</p>
                        <p className="pricestr">{sports[0].price_str}</p>
                </Card>
                <Card className="recommendCard"  hoverable={true}>
                    <img alt="example" src={sports[1].activityIcon} className="img"/>
                    <p className="box">{sports[1].title}</p>
                    <p className="venue">{sports[1].venue}</p>
                    <p className="venue">{sports[1].timescale}</p>
                    <p className="pricestr">{sports[1].price_str}</p>
                </Card>
                <Card className="recommendCard"  hoverable={true}>
                    <img alt="example" src={sports[2].activityIcon} className="img"/>
                    <p className="box">{sports[2].title}</p>
                    <p className="venue">{sports[2].venue}</p>
                    <p className="venue">{sports[2].timescale}</p>
                    <p className="pricestr">{sports[2].price_str}</p>
                </Card>
                <Card className="recommendCard"  hoverable={true}>
                    <img alt="example" src={sports[3].activityIcon} className="img"/>
                    <p className="box">{sports[3].title}</p>
                    <p className="venue">{sports[3].venue}</p>
                    <p className="venue">{sports[3].timescale}</p>
                    <p className="pricestr">{sports[3].price_str}</p>
                </Card>
            </div>
        )
    }
}