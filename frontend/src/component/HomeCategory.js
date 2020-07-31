import {Col, List, PageHeader, Row} from 'antd';
import React from "react";
import "../css/HomeCard.css"
import {Link} from "react-router-dom";
import {HomeCard} from "./HomeCard";
import "../css/home.css"
import {BigHomeCard} from "./BigHomeCard";

export class HomeCategory extends React.Component{

    render(){


        return(
            <div className="category" >
                <PageHeader className="site-page-header animated infinite flipInX" title={this.props.name} subTitle={[this.props.icon]}
                            extra={<Link to="/sortPage" onClick={()=>localStorage.setItem("category",this.props.name)}>
                                <p className="all">查看全部</p>
                            </Link>}/>
                <Row>
                    <Col span={18} push={6}>
                        <div style={{backgroundColor:"white",height:460,width:900}}>
                            <List  grid={{gutter: 64, column: 4}}
                                   dataSource={this.props.info.slice(0,8)}
                                   renderItem={item => (<List.Item><HomeCard info={item} /></List.Item>)}
                            />
                        </div>
                    </Col>
                    <Col span={6} pull={18}>
                        <div style={{backgroundColor:"white",height:460,}}>
                            <BigHomeCard info={this.props.info[8]}/>
                        </div>
                    </Col>
                </Row>
            </div>
        )
    }
}