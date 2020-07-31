import { Card } from 'antd';
import React from "react";
import "../css/HomeCard.css"
import {Link} from "react-router-dom";

const { Meta } = Card;

export class HomeCard extends React.Component{
    componentDidMount() {
        console.log(this.props.info.actitems);
    }

    render(){
        return(
    //         <Card
    //     hoverable
    //     style={{ width: 240 }}
    //     cover={<img alt="example" src={this.props.info.imgurl} />}
    // >
    // <Meta title={this.props.info.title} description={'¥'+this.props.info.price} style={{fontFamily:"HeiTi"}}/>
    // </Card>
            <Link to="/sortPage" onClick={()=>localStorage.setItem("cardInfo",JSON.stringify(this.props.info))}>
            <Card  bordered={false} hoverable={true} style={{width:240,height:200}}>
                <img alt="example" src={this.props.info.activityIcon} id="cardImg" style={{marginLeft:-20,marginTop:-20}}/>
                <div id="fontDiv">
                    <p className="box">{this.props.info.title}</p>
                    <p id="venue">{this.props.info.venue}</p>
                    <p id="venue">{this.props.info.timescale}</p>
                    <p id="pricestr">{'¥'+this.props.info.actitems[0].price[0].class[0].price+'-'
                    +this.props.info.actitems[0].price[0].class[this.props.info.actitems[0].price[0].class.length-1].price}</p>
                </div>
            </Card>
            </Link>
        )
    }
}