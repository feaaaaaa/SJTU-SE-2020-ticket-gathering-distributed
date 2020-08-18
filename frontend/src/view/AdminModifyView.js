import React from "react";
import {AdminModify} from "../component/AdminModify";
import {HeaderInfo} from "../component/Header";
import {DetailCard} from "../component/DetailCard";
import {Menu} from "antd";
import {RecommendList} from "../component/RecommendList";
import {FooterInfo} from "../component/Footer";

export class AdminModifyView extends React.Component{
    render() {
        return (
            // <div>
            //     <HeaderInfo/>
            //     <AdminModify/>
            // </div>
            <div>
                <HeaderInfo logOut={this.logOut} search={this.onSearch}/>
                <div id="Detail" style={{paddingTop:150,float:"left",marginLeft:-100,width:"80%"}}>
                    <DetailCard info={this.state.info}/>
                </div>
                <RecommendList info={this.state.info}/>
                <div style={{paddingTop:2000}}>
                    <FooterInfo />
                </div>
            </div>
        );
    }
}