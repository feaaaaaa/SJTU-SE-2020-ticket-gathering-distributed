import React from 'react';
import { sports} from "../const/activity";
import {Avatar, Button, Divider, Dropdown, Input, Layout, List, Menu,message,Radio,Collapse,Tag,BackTop,Pagination} from "antd";
import {SortPageCard} from "../component/SortPageCard";
import "../css/sortPage.css"
import "../css/headerInfo.css"
import {category_search, getSearchPageNum, getSelectSearchPageNum, search} from "../service/searchService";
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
            type:"category",
            clickSearch:true,
            pageNum:0,
            currentPage:1,
            searchMode:null
        }
    }

    toggleSearch=(value)=>{
        this.setState({search:value,searchMode:"search"})
    };

    category_search(type,category,city){
        // if(res.status!==200)
        //     message.error(res.msg);
        // this.setState({activity: res.data,searchMode:"category"})
        this.setState({city:city,category:category,type:type,searchMode:"category"});
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
        // console.log(this.state.usertype);
        // this.setState({activity: sports});
        /**toggle search*/
        let value="";
        if(this.props.location!=null&&this.props.location.state!=null){
            value=this.props.location.state.search;
        }
        console.log("!" +value);

        /**toggle category*/
        const category=localStorage.getItem("category");
        localStorage.removeItem("category");
        this.setState({search: value,type:"category"});
        if(category!=null)this.setState({category:category});
        console.log("!!"+category);

        /**toggle home card*/
        const cardInfo=localStorage.getItem("cardInfo");

        if(cardInfo!=null){
            localStorage.removeItem("cardInfo");
            console.log(JSON.stringify(cardInfo));
            let list=[];
            list.push(JSON.parse(cardInfo));
            this.setState({activity:list})
        }

        else if(category!=null) {
            this.setState({category:category});
            getSelectSearchPageNum("category", category, "全国", (res) => {
                this.setState({pageNum: res.data,searchMode:"category"});
            });
            category_search("category", category, "全国", 1, (res) => {
                console.log("select search return:" + JSON.stringify(res));
                if (res != null) {
                    if (res.status !== 200)
                        message.error(res.msg);
                    this.setState({activity: res.data});
                }
            });
        }

        else {
            getSearchPageNum(value,(res)=>{
                this.setState({pageNum:res.data,searchMode:"search"});
            });
            search(value, 1,(res) => {
                console.log("search return:" + JSON.stringify(res));
                // if(res!=null)
                //     this.setState({activity: res})
                if (res != null) {
                    if (res.status !== 200)
                        message.error(res.msg);
                    this.setState({activity: res.data});
                }
            });
        }
    }

    onChange = page => {
        this.setState({
            currentPage: page,
        });
    };

    componentWillUpdate(nextProps, nextState, nextContext) {
        if(this.state.search!==nextState.search&&nextState.search!=="") {
            // console.log(this.state.search);
            // console.log(nextState.search==="");
            // if(this.state.clickSearch) {
            //     this.setState({
            //         clickSearch:false
            //     });
            this.setState({search: nextState.search});
            console.log("search value:" + nextState.search);
            getSearchPageNum(nextState.search, (res) => {
                this.setState({pageNum: res.data});
            });
            search(nextState.search, 1, (res) => {
                console.log("search return:" + JSON.stringify(res));
                if (res != null) {
                    if (res.status !== 200)
                        message.error(res.msg);
                    this.setState({
                        activity: res.data,
                        type: "category",
                        category: "全部",
                        city: "全国",
                        currentPage: 1
                    })
                }
            });
        }
                // setTimeout(()=>{
                //      this.setState({
                //          clickSearch:true
                //      })
                // }, 2000);
            // }
        // else{
        //         message.info("点击太快了，休息一会吧");
        // }
        else if(this.state.city!==nextState.city||this.state.category!==nextState.category||this.state.type!==nextState.type){
            getSelectSearchPageNum(nextState.type, nextState.category, nextState.city, (res) => {
                this.setState({pageNum: res.data,searchMode:"category"});
            });
            category_search(nextState.type, nextState.category, nextState.city, 1, (res) => {
                console.log("select search return:" + JSON.stringify(res));
                if (res != null) {
                    if (res.status !== 200)
                        message.error(res.msg);
                    this.setState({activity: res.data,currentPage:1,search:""});
                }
            });
        }
        else if(this.state.currentPage!==nextState.currentPage){
            if(this.state.searchMode==="search") {
                search(nextState.search, nextState.currentPage, (res) => {
                    this.setState({activity: res.data});
                });
            }
            if(this.state.searchMode==="category"){
                category_search(nextState.type,nextState.category,nextState.city,nextState.currentPage,(res) => {
                    this.setState({activity: res.data});
                });
            }
        }
    }

    componentWillUnmount() {
        // message.error("?????");
        localStorage.removeItem("search");
        localStorage.removeItem("category");
    }

    render(){
        if(this.state.activity==null){
            return <p>404</p>;
        }
        console.log(this.state.pageNum);
        console.log(this.state.currentPage);
        console.log(this.state.type);
        console.log(this.state.category);
        console.log(this.state.city);

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
                        // pagination={{
                        //     onChange: page => {
                        //         console.log(page);
                        //     },
                        //     pageSize: 10,
                        // }}
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
                    {/*<Pagination defaultCurrent={1} total={50} />*/}
                    <Pagination current={this.state.currentPage} onChange={this.onChange} total={this.state.pageNum*10} />;
                </div>
                <BackTop/>
            </div>
        )
    }
}
