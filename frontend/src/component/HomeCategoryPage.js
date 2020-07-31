import {Card, Col, List, PageHeader, Row} from 'antd';
import React from "react";
import {concerts, sports} from "../const/activity";
import {HomeCard} from "./HomeCard";
import "../css/home.css"
import {homeSearch} from "../service/searchService";
import {BigHomeCard} from "./BigHomeCard";
import Icon from "@ant-design/icons";
import {Link} from "react-router-dom";
import {PandaIcon,BallIcon,MusicIcon,TourIcon,SingIcon,SongIcon,QuIcon,DanceIcon} from "../const/Icon";
import {HomeCategory} from "./HomeCategory";

export class HomeCategoryPage extends React.Component{
    constructor(props) {
        super(props);
        this.state={
            children:sports,
            opera:sports,
            tour:sports,
            song:sports,
            sports:sports,
            dance:sports,
            concerts:sports,
            vocal:sports
        }
    }

    componentDidMount() {
        homeSearch((res)=>{
            console.log(JSON.stringify(res));
            if(res!=null) {
                console.log("children:",res[0]);
                console.log("opera:",res[10]);
                console.log("song:",res[20]);
                console.log("tour:",res[30]);
                console.log("sports:",res[40]);
                console.log("dance:",res[50]);
                console.log("concerts:",res[60]);
                console.log("vocal:",res[70]);
                this.setState({
                    children: res.slice(0, 10),
                    opera: res.slice(10, 20),
                    song: res.slice(20, 30),
                    tour: res.slice(30, 40),
                    sports: res.slice(40, 50),
                    dance: res.slice(50, 60),
                    concerts: res.slice(60, 70),
                    vocal: res.slice(70, 80)
                })
            }
        })
    }

    render(){

        return(
            <div style={{paddingTop:10}}>


                <HomeCategory name="儿童亲子" icon={<PandaIcon style={{fontSize: '36px'}}/>} info={this.state.children}/>
                <HomeCategory name={"话剧歌剧"} icon={<SongIcon style={{fontSize: '36px'}}/>} info={this.state.opera}/>
                <HomeCategory name={"展览休闲"} icon={<TourIcon style={{fontSize: '36px'}}/>} info={this.state.song}/>
                <HomeCategory name={"曲苑杂坛"} icon={<QuIcon style={{fontSize: '36px'}}/>} info={this.state.tour}/>
                <HomeCategory name={"体育"} icon={<BallIcon style={{fontSize: '36px'}}/>} info={this.state.sports}/>
                <HomeCategory name={"舞蹈芭蕾"} icon={<DanceIcon style={{fontSize: '36px'}}/>} info={this.state.dance}/>
                <HomeCategory name={"音乐会"} icon={<MusicIcon style={{fontSize: '36px'}}/>} info={this.state.concerts}/>
                <HomeCategory name={"演唱会"} icon={<SingIcon style={{fontSize: '36px'}}/>} info={this.state.vocal}/>
                
            {/*    <div className="category" >*/}
            {/*    <PageHeader className="site-page-header animated infinite flipInX" title="儿童亲子" subTitle={[<PandaIcon style={{fontSize: '36px'}}/>]}*/}
            {/*                extra={<Link to="/sortPage" onClick={()=>localStorage.setItem("category","儿童亲子")}>*/}
            {/*                    <p className="all">查看全部</p>*/}
            {/*                </Link>}/>*/}
            {/*    <Row>*/}
            {/*        <Col span={18} push={6}>*/}
            {/*            <div style={{backgroundColor:"white",height:460,width:900}}>*/}
            {/*                <List  grid={{gutter: 64, column: 4}}*/}
            {/*                       dataSource={this.state.children.slice(0,8)}*/}
            {/*                       renderItem={item => (<List.Item><HomeCard info={item} /></List.Item>)}*/}
            {/*                />*/}
            {/*            </div>*/}
            {/*        </Col>*/}
            {/*        <Col span={6} pull={18}>*/}
            {/*            <div style={{backgroundColor:"white",height:460,}}>*/}
            {/*                <BigHomeCard info={this.state.children[8]}/>*/}
            {/*            </div>*/}
            {/*        </Col>*/}
            {/*    </Row>*/}
            {/*</div>*/}

            {/*    <div className="category" >*/}
            {/*        <PageHeader className="site-page-header animated infinite flipInX" title="话剧歌剧" subTitle={[<SongIcon style={{fontSize: '36px'}}/>]}*/}
            {/*                    extra={<Link to="/sortPage" onClick={()=>localStorage.setItem("category","话剧歌剧")}>*/}
            {/*                        <p className="all">查看全部</p>*/}
            {/*                    </Link>}/>*/}
            {/*        <Row>*/}
            {/*            <Col span={18} push={6}>*/}
            {/*                <div style={{backgroundColor:"white",height:460,width:900}}>*/}
            {/*                    <List  grid={{gutter: 64, column: 4}}*/}
            {/*                           dataSource={this.state.opera.slice(0,8)}*/}
            {/*                           renderItem={item => (<List.Item><HomeCard info={item} /></List.Item>)}*/}
            {/*                    />*/}
            {/*                </div>*/}
            {/*            </Col>*/}
            {/*            <Col span={6} pull={18}>*/}
            {/*                <div style={{backgroundColor:"white",height:460,}}>*/}
            {/*                    <BigHomeCard info={this.state.opera[8]}/>*/}
            {/*                </div>*/}
            {/*            </Col>*/}
            {/*        </Row>*/}
            {/*    </div>*/}

            {/*    <div className="category" >*/}
            {/*        <PageHeader className="site-page-header animated infinite flipInX" title="旅游展览" subTitle={[<TourIcon style={{fontSize: '36px'}}/>]}*/}
            {/*                    extra={<Link to="/sortPage" onClick={()=>localStorage.setItem("category","展览休闲")}>*/}
            {/*                        <p className="all">查看全部</p>*/}
            {/*                    </Link>}/>*/}
            {/*        <Row>*/}
            {/*            <Col span={18} push={6}>*/}
            {/*                <div style={{backgroundColor:"white",height:460,width:900}}>*/}
            {/*                    <List  grid={{gutter: 64, column: 4}}*/}
            {/*                           dataSource={this.state.tour.slice(0,8)}*/}
            {/*                           renderItem={item => (<List.Item><HomeCard info={item} /></List.Item>)}*/}
            {/*                    />*/}
            {/*                </div>*/}
            {/*            </Col>*/}
            {/*            <Col span={6} pull={18}>*/}
            {/*                <div style={{backgroundColor:"white",height:460,}}>*/}
            {/*                    <BigHomeCard info={this.state.tour[8]}/>*/}
            {/*                </div>*/}
            {/*            </Col>*/}
            {/*        </Row>*/}
            {/*    </div>*/}

            {/*    <div className="category" >*/}
            {/*        <PageHeader className="site-page-header animated infinite flipInX" title="曲苑杂坛" subTitle={[<QuIcon style={{fontSize: '36px'}}/>]}*/}
            {/*                    extra={<Link to="/sortPage" onClick={()=>localStorage.setItem("category","曲苑杂坛")}>*/}
            {/*                        <p className="all">查看全部</p>*/}
            {/*                    </Link>}/>*/}
            {/*        <Row>*/}
            {/*            <Col span={18} push={6}>*/}
            {/*                <div style={{backgroundColor:"white",height:460,width:900}}>*/}
            {/*                    <List  grid={{gutter: 64, column: 4}}*/}
            {/*                           dataSource={this.state.song.slice(0,8)}*/}
            {/*                           renderItem={item => (<List.Item><HomeCard info={item} /></List.Item>)}*/}
            {/*                    />*/}
            {/*                </div>*/}
            {/*            </Col>*/}
            {/*            <Col span={6} pull={18}>*/}
            {/*                <div style={{backgroundColor:"white",height:460,}}>*/}
            {/*                    <BigHomeCard info={this.state.song[8]}/>*/}
            {/*                </div>*/}
            {/*            </Col>*/}
            {/*        </Row>*/}
            {/*    </div>*/}

            {/*    <div className="category" >*/}
            {/*        <PageHeader className="site-page-header animated infinite flipInX" title="体育" subTitle={[<BallIcon style={{fontSize: '36px'}}/>]}*/}
            {/*                    extra={<Link to="/sortPage" onClick={()=>localStorage.setItem("category","体育")}>*/}
            {/*                        <p className="all">查看全部</p>*/}
            {/*                    </Link>}/>*/}
            {/*        <Row>*/}
            {/*            <Col span={18} push={6}>*/}
            {/*                <div style={{backgroundColor:"white",height:460,width:900}}>*/}
            {/*                    <List  grid={{gutter: 64, column: 4}}*/}
            {/*                           dataSource={this.state.sports.slice(0,8)}*/}
            {/*                           renderItem={item => (<List.Item><HomeCard info={item} /></List.Item>)}*/}
            {/*                    />*/}
            {/*                </div>*/}
            {/*            </Col>*/}
            {/*            <Col span={6} pull={18}>*/}
            {/*                <div style={{backgroundColor:"white",height:460,}}>*/}
            {/*                    <BigHomeCard info={this.state.sports[8]}/>*/}
            {/*                </div>*/}
            {/*            </Col>*/}
            {/*        </Row>*/}
            {/*    </div>*/}

            {/*    <div className="category" >*/}
            {/*        <PageHeader className="site-page-header animated infinite flipInX" title="舞蹈芭蕾" subTitle={[<DanceIcon style={{fontSize: '36px'}}/>]}*/}
            {/*                    extra={<Link to="/sortPage" onClick={()=>localStorage.setItem("category","舞蹈芭蕾")}>*/}
            {/*                        <p className="all">查看全部</p>*/}
            {/*                    </Link>}/>*/}
            {/*        <Row>*/}
            {/*            <Col span={18} push={6}>*/}
            {/*                <div style={{backgroundColor:"white",height:460,width:900}}>*/}
            {/*                    <List  grid={{gutter: 64, column: 4}}*/}
            {/*                           dataSource={this.state.dance.slice(0,8)}*/}
            {/*                           renderItem={item => (<List.Item><HomeCard info={item} /></List.Item>)}*/}
            {/*                    />*/}
            {/*                </div>*/}
            {/*            </Col>*/}
            {/*            <Col span={6} pull={18}>*/}
            {/*                <div style={{backgroundColor:"white",height:460,}}>*/}
            {/*                    <BigHomeCard info={this.state.dance[8]}/>*/}
            {/*                </div>*/}
            {/*            </Col>*/}
            {/*        </Row>*/}
            {/*    </div>*/}

            {/*    <div className="category" >*/}
            {/*        <PageHeader className="site-page-header animated infinite flipInX" title="音乐会" subTitle={[<MusicIcon style={{fontSize: '36px'}}/>]}*/}
            {/*                    extra={<Link to="/sortPage" onClick={()=>localStorage.setItem("category","音乐会")}>*/}
            {/*                        <p className="all">查看全部</p>*/}
            {/*                    </Link>}/>*/}
            {/*        <Row>*/}
            {/*            <Col span={18} push={6}>*/}
            {/*                <div style={{backgroundColor:"white",height:460,width:900}}>*/}
            {/*                    <List  grid={{gutter: 64, column: 4}}*/}
            {/*                           dataSource={this.state.concerts.slice(0,8)}*/}
            {/*                           renderItem={item => (<List.Item><HomeCard info={item} /></List.Item>)}*/}
            {/*                    />*/}
            {/*                </div>*/}
            {/*            </Col>*/}
            {/*            <Col span={6} pull={18}>*/}
            {/*                <div style={{backgroundColor:"white",height:460,}}>*/}
            {/*                    <BigHomeCard info={this.state.concerts[8]}/>*/}
            {/*                </div>*/}
            {/*            </Col>*/}
            {/*        </Row>*/}
            {/*    </div>*/}

            {/*    <div className="category" >*/}
            {/*        <PageHeader className="site-page-header animated infinite flipInX" title="演唱会" subTitle={[<SingIcon style={{fontSize: '36px'}}/>]}*/}
            {/*                    extra={<Link to="/sortPage" onClick={()=>localStorage.setItem("category","演唱会")}>*/}
            {/*                        <p className="all">查看全部</p>*/}
            {/*                    </Link>}/>*/}
            {/*        <Row>*/}
            {/*            <Col span={18} push={6}>*/}
            {/*                <div style={{backgroundColor:"white",height:460,width:900}}>*/}
            {/*                    <List  grid={{gutter: 64, column: 4}}*/}
            {/*                           dataSource={this.state.vocal.slice(0,8)}*/}
            {/*                           renderItem={item => (<List.Item><HomeCard info={item} /></List.Item>)}*/}
            {/*                    />*/}
            {/*                </div>*/}
            {/*            </Col>*/}
            {/*            <Col span={6} pull={18}>*/}
            {/*                <div style={{backgroundColor:"white",height:460,}}>*/}
            {/*                    <BigHomeCard info={this.state.vocal[8]}/>*/}
            {/*                </div>*/}
            {/*            </Col>*/}
            {/*        </Row>*/}
            {/*    </div>*/}

            </div>

        )
    }
}
