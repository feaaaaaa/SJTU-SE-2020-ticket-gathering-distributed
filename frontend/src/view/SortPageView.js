import React from 'react';
import { sports} from "../const/activity";
import {Avatar, Button, Divider, Dropdown, Input, Layout, List, Menu,message,Radio,Collapse,Tag,BackTop} from "antd";
import {SortPageCard} from "../component/SortPageCard";
import "../css/sortPage.css"
import "../css/headerInfo.css"
import {category_search, search} from "../service/searchService";
import { CaretRightOutlined } from '@ant-design/icons';
// import Redirect from "react-router-dom/es/Redirect";
import { UserOutlined } from '@ant-design/icons';
import {HeaderInfo} from "../component/Header";
import {Choice} from "../component/Choice";
const {Header} = Layout;
const { Search } = Input;
const { Panel } = Collapse;

export class SortPageView extends React.Component{
    constructor(props) {
        super(props);
        this.state={
            activity:[],
            search:null,
            login:false,
            username:null,
            usertype:null,
            city:"全国",
            category:"全部",
            type:"category"
        }
    }

    toggleSearch=(value)=>{
        this.setState({search:value})
    };

    category_search(res){
        this.setState({activity: res})
    }

    logOut(){
        localStorage.clear();
        this.setState({login:false,username:null,usertype:null});
    }

    componentDidMount() {
        let username=localStorage.getItem("username");
        if(username!=null){
            this.setState({username:username,login:true});
            this.setState({usertype:localStorage.getItem("usertype")})
        }
        console.log(this.state.usertype);

        this.setState({activity: sports});
        let value="";
        if(this.props.location.state!=null){
            value=this.props.location.state.search;
        }
        const category=localStorage.getItem("category");
        localStorage.removeItem("category");
        this.setState({search: value,type:"category"});
        if(category!=null)this.setState({category:category});
        console.log("!" +value);
        console.log("!!"+category);
        const cardInfo=localStorage.getItem("cardInfo");
        if(cardInfo!=null){
            localStorage.removeItem("cardInfo");
            console.log(JSON.stringify(cardInfo));
            let list=[];
            list.push(JSON.parse(cardInfo));
            this.setState({activity:list})
        }
        else if(category!=null)
            category_search("category",category,"全国",(res) => {
                console.log("select search return:" + JSON.stringify(res));
                if(res!=null)
                    this.setState({activity: res});
            });
        else search(value, (res) => {
            console.log("search return:" + JSON.stringify(res));
            // if(res!=null)
            //     this.setState({activity: res})
            if(res.status!==200)
                message.error(res.msg);
            this.setState({activity:res.data});
        });
    }

    componentWillUpdate(nextProps, nextState, nextContext) {
        if(this.state.search!==nextState.search&&nextState.search!==""){
            // console.log(this.state.search);
            // console.log(nextState.search==="");
            this.setState({search: nextState.search});
            search(nextState.search, (res) => {
                console.log("search return:" + JSON.stringify(res));
                if (res.status !== 200)
                    message.error(res.msg);
                this.setState({
                    activity: res.data,
                    type: "category",
                    category: "全部",
                    city: "全国"
                })
            });
        }
    }

    componentWillUnmount() {
        // message.error("?????");
        localStorage.removeItem("search");
        localStorage.removeItem("category");
    }

    render(){
        return(
            <div >
                <HeaderInfo search={value => this.toggleSearch(value)}/>
                <Divider plain className="divider"> </Divider>
                <div id="classify">
                    <div id="cityDiv">
                        <Choice onChoose={this.category_search.bind(this)}/>
                    </div>
                </div>
                <div id="sortPageDiv" style={{paddingBottom:100}}>
                    <List
                        grid={{gutter: 10, column: 1}}
                        pagination={{
                            onChange: page => {
                                console.log(page);
                            },
                            pageSize: 10,
                        }}
                        footer={
                            <div style={{textAlign:"center"}}>
                                <b>O_ligei ! </b> footer part
                            </div>
                        }
                        dataSource={this.state.activity}
                        renderItem={item => (
                            <List.Item>
                                <SortPageCard info={item} usertype={this.state.usertype}/>
                            </List.Item>
                        )}
                    />
                </div>
                <BackTop/>
            </div>
        )
    }
}
