import React from "react";
import {Card, List, message} from 'antd';
import "../css/recommendList.css"
import { sports} from "../const/activity";
import {getRecommend} from "../service/userService";
import {Link} from "react-router-dom";
import {RecommendCard} from "./RecommendCard";

export class RecommendList extends React.Component{
    constructor(props) {
        super(props);
        this.state={
            activity:sports
        }
    }
    
    async componentDidMount() {
        await getRecommend(localStorage.getItem("userId"), localStorage.getItem("activityId"), localStorage.getItem("token"),
            (res) => {
                console.log("commend return:"+res);
                if (res != null) {
                    if(res.status!==200)
                        message.error(res.msg);
                    this.setState({activity: res.data})
                }
            });
        console.log("recommondList:"+JSON.stringify(this.state.activity));
    }

    render(){
        return(
            <div className="site-card-wrapper" id="recommendList">
                <p id="recommendp">猜你喜欢</p>
                <List
                    dataSource={this.state.activity}
                    renderItem={item => (
                        <List.Item>
                            <RecommendCard info={item}/>
                        </List.Item>
                    )}
                />
                {/*<Link to="/sortPage" onClick={()=>localStorage.setItem("cardInfo",JSON.stringify(this.state.activity[0]))}>*/}
                {/*<Card className="recommendCard"  hoverable={true}>*/}
                {/*    <img alt="example" src={this.state.activity[0].activityIcon} className="img"/>*/}
                {/*        <p className="box">{this.state.activity[0].title}</p>*/}
                {/*        <p className="venue">{this.state.activity[0].venue}</p>*/}
                {/*        <p className="venue">{this.state.activity[0].timescale}</p>*/}
                {/*        <p className="pricestr">{'¥'+this.state.activity[0].actitems[0].price[0].class[0].price+'-'*/}
                {/*        +this.state.activity[0].actitems[0].price[0].class[this.state.activity[0].actitems[0].price[0].class.length-1].price}</p>*/}
                {/*</Card>*/}
                {/*</Link>*/}
                {/*<Link to="/sortPage" onClick={()=>localStorage.setItem("cardInfo",JSON.stringify(this.state.activity[1]))}>*/}
                {/*<Card className="recommendCard"  hoverable={true}>*/}
                {/*    <img alt="example" src={this.state.activity[1].activityIcon} className="img"/>*/}
                {/*    <p className="box">{this.state.activity[1].title}</p>*/}
                {/*    <p className="venue">{this.state.activity[1].venue}</p>*/}
                {/*    <p className="venue">{this.state.activity[1].timescale}</p>*/}
                {/*    <p className="pricestr">{'¥'+this.state.activity[1].actitems[0].price[0].class[0].price+'-'*/}
                {/*    +this.state.activity[1].actitems[0].price[0].class[this.state.activity[1].actitems[0].price[0].class.length-1].price}</p>*/}
                {/*</Card>*/}
                {/*</Link>*/}
                {/*<Link to="/sortPage" onClick={()=>localStorage.setItem("cardInfo",JSON.stringify(this.state.activity[2]))}>*/}
                {/*<Card className="recommendCard"  hoverable={true}>*/}
                {/*    <img alt="example" src={this.state.activity[2].activityIcon} className="img"/>*/}
                {/*    <p className="box">{this.state.activity[2].title}</p>*/}
                {/*    <p className="venue">{this.state.activity[2].venue}</p>*/}
                {/*    <p className="venue">{this.state.activity[2].timescale}</p>*/}
                {/*    <p className="pricestr">{'¥'+this.state.activity[2].actitems[0].price[0].class[0].price+'-'*/}
                {/*    +this.state.activity[2].actitems[0].price[0].class[this.state.activity[2].actitems[0].price[0].class.length-1].price}</p>*/}
                {/*</Card>*/}
                {/*</Link>*/}
                {/*<Link to="/sortPage" onClick={()=>localStorage.setItem("cardInfo",JSON.stringify(this.state.activity[3]))}>*/}
                {/*<Card className="recommendCard"  hoverable={true}>*/}
                {/*    <img alt="example" src={this.state.activity[3].activityIcon} className="img"/>*/}
                {/*    <p className="box">{this.state.activity[3].title}</p>*/}
                {/*    <p className="venue">{this.state.activity[3].venue}</p>*/}
                {/*    <p className="venue">{this.state.activity[3].timescale}</p>*/}
                {/*    <p className="pricestr">{'¥'+this.state.activity[3].actitems[0].price[0].class[0].price+'-'*/}
                {/*    +this.state.activity[3].actitems[0].price[0].class[this.state.activity[3].actitems[0].price[0].class.length-1].price}</p>*/}
                {/*</Card>*/}
                {/*</Link>*/}
            </div>
        )
    }
}