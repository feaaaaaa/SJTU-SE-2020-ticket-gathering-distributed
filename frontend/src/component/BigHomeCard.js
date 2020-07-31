import { Card } from 'antd';
import React from "react";
import "../css/BigHomeCard.css"
import {Link} from "react-router-dom";

const { Meta } = Card;

export class BigHomeCard extends React.Component{
    componentDidMount() {
        console.log(this.props.info.actitems);
    }

    render(){
        return(
            <Link to="/sortPage" onClick={()=>localStorage.setItem("cardInfo",JSON.stringify(this.props.info))}>
            <Card  bordered={false} hoverable={true} style={{height:420}}>
                <img alt="example" src={this.props.info.activityIcon} style={{width:180,height:260,float:"none"}}/>
                <div>
                    <p className="Box">{this.props.info.title}</p>
                    <p id="Venue">{this.props.info.venue}</p>
                    <p id="Venue">{this.props.info.timescale}</p>
                    <p id="Pricestr">{'¥'+this.props.info.actitems[0].price[0].class[0].price+'-'
                    +this.props.info.actitems[0].price[0].class[this.props.info.actitems[0].price[0].class.length-1].price}</p>
                </div>
            </Card>
            </Link>
        )
    }
}