import React from "react";
import {Card} from 'antd';
import "../css/recommendList.css"
import {Link} from "react-router-dom";

export class RecommendCard extends React.Component{
    
    render() {
        return (
            <Link to="/sortPage" onClick={()=>localStorage.setItem("cardInfo",JSON.stringify(this.props.info))}>
                <Card className="recommendCard"  hoverable={true}>
                    <img alt="example" src={this.props.info.activityIcon} className="img"/>
                    <p className="box">{this.props.info.title}</p>
                    <p className="venue">{this.props.info.venue}</p>
                    <p className="venue">{this.props.info.timescale}</p>
                    <p className="pricestr">{'¥'+this.props.info.actitems[0].price[0].class[0].price+'-'
                    +this.props.info.actitems[0].price[0].class[this.props.info.actitems[0].price[0].class.length-1].price}</p>
                </Card>
            </Link>
        );
    }
}