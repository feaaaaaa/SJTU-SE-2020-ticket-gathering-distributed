/**
 * @param actitemid {number}
 * @param this.props.info.activityId {String}
 */
import {Card, Collapse, Menu, Dropdown, Button, message} from 'antd';
import React from "react";
import "../css/HomeCard.css"
import "../css/sortPage.css"
import {deleteActivity} from "../service/AdminService";
import {history} from "../utils/history";
import {Link, Redirect} from "react-router-dom";
// import Link from "react-router-dom/modules/Link";
const { Panel } = Collapse;
const { Meta } = Card;


export class SortPageCard extends React.Component{

    constructor(props) {
        super(props);
        this.state={
            ifdetail:false,
            ifauthen:false,
            ifauthor:false
        }
    }

    requestData = {
        "image": this.props.info.imgurl,
    };

    componentDidMount() {
        console.log(this.props.usertype);
    }

    handleDetail = (id,activityId) =>{
        // console.log(website);
        // let request = {
        //     "imgurl":this.props.info.activityIcon,
        //     "title":this.props.info.title,
        //     "city":this.props.info.city,
        //     "venue":this.props.info.venue,
        //     "category":this.props.info.category,
        //     "timescale":this.props.info.timescale,
        //     "price":this.props.info.price,
        //     "website":website,
        // };
        window.localStorage.setItem("actitemid",id);
        window.localStorage.setItem("activityId",activityId);
        console.log("detail:"+id);
        console.log("???");
        // this.setState({
        //     ifdetail:true
        // })
    }

    handleDelete=(info)=>{
        console.log("delete:");
        // console.log(info);
        console.log(info.activityId);
        for(let i=0;i<info.actitems.length;++i)
            console.log(info.actitems[i].actitemId);

        deleteActivity(info.activityId,localStorage.getItem("token"),(res)=>{
            console.log(res);
            if(res===true)
                message.success("删除成功！");
            else if(res.message==="authentication failure"){this.setState({ifauthen:true});localStorage.clear();}
            else if(res.message==="authorization failure")this.setState({ifauthor:true});
            else message.error("错误，请重试！");
        })
    }


    render(){

        if(this.state.ifauthen){
            message.error("请先登录");
            return <Redirect to={{pathname: "/login"}}/>;
        }
        else if(this.state.ifauthor){
            message.error("无权限");
            return <Redirect to={{pathname: "/login"}}/>;
        }
        else
        return(
             <div style={{paddingBottom:30}}>
                 {
                     this.props.usertype==="Admin" &&
                     <div style={{float: "right"}}>
                         <Button onClick={()=>this.handleDelete(this.props.info)} >删除</Button>
                     </div>
                 }
                 <div>
                     <img className='image' alt="example" src={this.props.info.activityIcon} />
                     <p id="title">{this.props.info.title}</p>
                     <p id="info">地点： {this.props.info.city} | {this.props.info.venue}</p>
                     <p style={{paddingLeft:200,fontSize:12}}>{this.props.info.category}</p>
                     <p id="info">{this.props.info.timescale}</p>
                     <p id="price">{'¥'+this.props.info.actitems[0].price[0].class[0].price+'-'
                     +this.props.info.actitems[0].price[0].class[this.props.info.actitems[0].price[0].class.length-1].price}</p>
                 </div>
                 <div style={{paddingTop:20}}>
                     <Collapse accordion bordered={false}>
                         <Panel header="票源" className="panel">
                             <ul>
                                 {this.props.info.actitems.map((actitem, index) => (
                                     <li key={index} style={{clear:"both"}}>
                                         {/*<Avatar icon={<UserOutlined />} />*/}
                                         <a href="/detail" onClick={()=>this.handleDetail(actitem.actitemId,actitem.activityId)}>{actitem.website}</a>
                                         {
                                             this.props.usertype==="Admin" &&
                                             <div style={{float: "right"}}>
                                                 <Button href="/adminAuction" onClick={()=>this.handleDetail(actitem.actitemId,actitem.activityId)}>添加竞拍</Button>
                                             </div>
                                         }
                                         <ul>
                                             {actitem.price.map((day,index)=>(
                                                 <li key={index} style={{clear:"both"}}>
                                                     {day.time}
                                                     <ul style={{listStyleType:"none"}}>
                                                         <div style={{clear:"both"}}>
                                                         {day.class.map((price,index)=>(
                                                             <li key={index} style={{float:"left",margin:10}}>
                                                                 ￥{price.price}
                                                             </li>))}
                                                         </div>
                                                     </ul>
                                                 </li>))}
                                         </ul>
                                         {/*- {user.day}*/}
                                     </li>
                                 ))}
                             </ul>

                             {/*<a  href="/detail" onClick={()=>this.handleDetail(parseInt(this.props.info.actitems[0].actitemId)).bind(this)} >*/}
                             {/*    {this.props.info.actitems[0].website}:*/}
                             {/*</a>*/}
                             {/*<p style={{fontFamily:'HeiTi'}}>时间1：{this.props.info.actitems[0].price[0].time}*/}
                             {/*￥{this.props.info.actitems[0].price[0].class[0].price},*/}
                             {/*￥{this.props.info.actitems[0].price[0].class[1].price},*/}
                             {/*￥{this.props.info.actitems[0].price[0].class[2].price}*/}
                             {/*。时间2：{this.props.info.actitems[0].price[1].time}*/}
                             {/*￥{this.props.info.actitems[0].price[1].class[0].price},*/}
                             {/*￥{this.props.info.actitems[0].price[1].class[1].price},*/}
                             {/*￥{this.props.info.actitems[0].price[1].class[2].price}*/}
                             {/*</p>*/}
                             {/*<p> </p>*/}
                             {/*<a href="/detail" onClick={()=>this.handleDetail(parseInt(this.props.info.actitems[1].actitemId)).bind(this)} >*/}
                             {/*    {this.props.info.actitems[1].website}:*/}
                             {/*</a>*/}
                             {/*<p style={{fontFamily:'HeiTi'}}>时间1：{this.props.info.actitems[1].price[0].time}*/}
                             {/*￥{this.props.info.actitems[1].price[0].class[0].price},*/}
                             {/*￥{this.props.info.actitems[1].price[0].class[1].price},*/}
                             {/*￥{this.props.info.actitems[1].price[0].class[2].price}*/}
                             {/*。时间2：{this.props.info.actitems[1].price[1].time}*/}
                             {/*￥{this.props.info.actitems[1].price[1].class[0].price},*/}
                             {/*￥{this.props.info.actitems[1].price[1].class[1].price},*/}
                             {/*￥{this.props.info.actitems[1].price[1].class[2].price}*/}
                             {/*</p>*/}
                             {/*<p> </p>*/}
                             {/*<a href="/detail" onClick={()=>this.handleDetail(parseInt(this.props.info.actitems[2].actitemId)).bind(this)} >*/}
                             {/*    {this.props.info.actitems[2].website}:*/}
                             {/*</a>*/}
                             {/*<p style={{fontFamily:'HeiTi'}}>时间1：{this.props.info.actitems[2].price[0].time}*/}
                             {/*￥{this.props.info.actitems[2].price[0].class[0].price},*/}
                             {/*￥{this.props.info.actitems[2].price[0].class[1].price},*/}
                             {/*￥{this.props.info.actitems[2].price[0].class[2].price}*/}
                             {/*。时间2：{this.props.info.actitems[2].price[1].time}*/}
                             {/*￥{this.props.info.actitems[2].price[1].class[0].price},*/}
                             {/*￥{this.props.info.actitems[2].price[1].class[1].price},*/}
                             {/*￥{this.props.info.actitems[2].price[1].class[2].price}*/}
                             {/*</p>*/}
                         </Panel>
                     </Collapse>
                 </div>
            </div>
        )
    }
}
