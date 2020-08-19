import React from 'react';
import { sports} from "../const/activity";
import {Avatar, Button, Divider, Dropdown, Input, Layout, List, Menu,message,Radio,Collapse,Tag,BackTop} from "antd";
import {SortPageCard} from "../component/SortPageCard";
import "../css/sortPage.css"
import "../css/headerInfo.css"
import {category_search, search} from "../service/searchService";
import { CaretRightOutlined } from '@ant-design/icons';
import { UserOutlined } from '@ant-design/icons';
const {Header} = Layout;
const { Search } = Input;
const { Panel } = Collapse;

export class Choice extends React.Component{
    constructor(props) {
        super(props);
        this.state={
            city:"全国",
            category:"全部",
            type:"category",
            clickCity:true,
            clickCategory:true
        }
    }
    onChange=(e)=> {
        if(this.state.clickCity) {
            this.setState({clickCity:false});
            this.setState({city: e.target.value});
            category_search(this.state.type, this.state.category, e.target.value, (res) => {
                console.log(res);
                if (res != null)
                    // this.setState({activity: res})
                    this.props.onChoose(res);
            })
            setTimeout(()=>{this.setState({clickCity:true})}, 2000);
        }
        else message.info("点击太快了，休息一会吧")
    };

    clearCategory=(e)=>{
        e.stopPropagation();
        if(this.state.clickCategory) {
            this.setState({category: "全部", type: "category",clickCategory:false});
            category_search("category", "全部", this.state.city, (res) => {
                console.log(res);
                if (res != null)
                    // this.setState({activity: res})
                    this.props.onChoose(res);
            })
            setTimeout(()=>{this.setState({clickCategory:true})}, 2000);
        }
        else message.info("点击太快了，休息一会吧");
    };

    clearCity=(e)=>{
        e.stopPropagation();
        if(this.state.clickCity) {
            this.setState({city: "全国",clickCity:false});
            category_search(this.state.type, this.state.category, "全国", (res) => {
                console.log(res);
                if (res != null)
                    // this.setState({activity: res})
                    this.props.onChoose(res);
            })
            setTimeout(()=>{this.setState({clickCity:true})}, 2000);
        }
        else message.info("点击太快了，休息一会吧");
    };

    handleCategoryClick = (key) => {
        if(this.state.clickCategory) {
            this.setState({category: key, type: "category",clickCategory:false});
            category_search("category", key, this.state.city, (res) => {
                console.log(res);
                if (res != null)
                    this.props.onChoose(res);
                // this.setState({activity: res})
            })
            setTimeout(()=>{this.setState({clickCategory:true})}, 2000);
        }
        else message.info("点击太快了，休息一会吧");
    };

    handleSubcategoryClick = (e) => {
        this.setState({category:e.key,type:"subcategory"});
        category_search("subcategory",e.key,this.state.city,(res) => {
            console.log(res);
            if (res != null)
                this.props.onChoose(res);
                // this.setState({activity: res})
        })
    };

    render(){
        const menu1 = (
            <Menu onClick={this.handleSubcategoryClick}>
                <Menu.Item key="展会">展会</Menu.Item>
                <Menu.Item key="特色体验">特色体验</Menu.Item>
                <Menu.Item key="其他展览休闲">其他展览休闲</Menu.Item>
            </Menu>
        );
        const menu2 = (
            <Menu onClick={this.handleSubcategoryClick}>
                <Menu.Item key="话剧">话剧</Menu.Item>
                <Menu.Item key="音乐剧">音乐剧</Menu.Item>
                <Menu.Item key="其他话剧歌剧">其他话剧歌剧</Menu.Item>
            </Menu>
        );
        const menu3 = (
            <Menu onClick={this.handleSubcategoryClick}>
                <Menu.Item key="摇滚">摇滚</Menu.Item>
                <Menu.Item key="流行">流行</Menu.Item>
                <Menu.Item key="音乐节">音乐节</Menu.Item>
                <Menu.Item key="其他演唱会">其他演唱会</Menu.Item>
            </Menu>
        );
        const menu4 = (
            <Menu onClick={this.handleSubcategoryClick}>
                <Menu.Item key="室内乐及古乐">室内乐及古乐</Menu.Item>
                <Menu.Item key="独奏">独奏</Menu.Item>
                <Menu.Item key="管弦乐">管弦乐</Menu.Item>
                <Menu.Item key="其他音乐会">其他音乐会</Menu.Item>
            </Menu>
        );
        const menu5 = (
            <Menu onClick={this.handleSubcategoryClick}>
                <Menu.Item key="戏曲">戏曲</Menu.Item>
                <Menu.Item key="相声">相声</Menu.Item>
                <Menu.Item key="魔术">魔术</Menu.Item>
                <Menu.Item key="其他曲苑杂坛">其他曲苑杂坛</Menu.Item>
            </Menu>
        );
        const menu6 = (
            <Menu onClick={this.handleSubcategoryClick}>
                <Menu.Item key="球类运动">球类运动</Menu.Item>
                <Menu.Item key="田径">田径</Menu.Item>
                <Menu.Item key="篮球">篮球</Menu.Item>
                <Menu.Item key="足球">足球</Menu.Item>
                <Menu.Item key="其他体育">其他体育</Menu.Item>
            </Menu>
        );
        const menu7 = (
            <Menu onClick={this.handleSubcategoryClick}>
                <Menu.Item key="舞蹈">舞蹈</Menu.Item>
                <Menu.Item key="其他舞蹈芭蕾">其他舞蹈芭蕾</Menu.Item>
            </Menu>
        );
        const menu8 = (
            <Menu onClick={this.handleSubcategoryClick}>
                <Menu.Item key="其他儿童亲子">其他儿童亲子</Menu.Item>
            </Menu>
        );
        const genExtra1 = () => (
            <a onClick={this.clearCategory}>清除</a>
        );
        const genExtra2 = () => (
            <a onClick={this.clearCity}>清除</a>
        );
        function callback(key) {
            console.log(key);
        }
        return(
            <Collapse onChange={callback} bordered={false} style={{paddingTop:40}}
                      expandIcon={({ isActive }) => <CaretRightOutlined rotate={isActive ? 90 : 0} />}>
                <Panel key="1" header={"分类："+this.state.category} className="site-collapse-custom-panel" extra={genExtra1()}>
                    <div>
                        <Dropdown.Button type="primary" overlay={menu1} onClick={()=>this.handleCategoryClick("展览休闲")}>展览休闲</Dropdown.Button>
                        &nbsp;&nbsp;&nbsp;
                        <Dropdown.Button type="primary" overlay={menu2} onClick={()=>this.handleCategoryClick("话剧歌剧")}>话剧歌剧</Dropdown.Button>
                        &nbsp;&nbsp;&nbsp;
                        <Dropdown.Button type="primary" overlay={menu3} onClick={()=>this.handleCategoryClick("演唱会")}>演唱会</Dropdown.Button>
                        &nbsp;&nbsp;&nbsp;
                        <Dropdown.Button type="primary" overlay={menu4} onClick={()=>this.handleCategoryClick("音乐会")}>音乐会</Dropdown.Button>
                        &nbsp;&nbsp;&nbsp;
                        <div style={{paddingTop:20}}> </div>
                        <Dropdown.Button type="primary" overlay={menu5} onClick={()=>this.handleCategoryClick("曲苑杂坛")}>曲苑杂坛</Dropdown.Button>
                        &nbsp;&nbsp;&nbsp;
                        <Dropdown.Button type="primary" overlay={menu6} onClick={()=>this.handleCategoryClick("体育")}>体育</Dropdown.Button>
                        &nbsp;&nbsp;&nbsp;
                        <Dropdown.Button type="primary" overlay={menu7} onClick={()=>this.handleCategoryClick("舞蹈芭蕾")}>舞蹈芭蕾</Dropdown.Button>
                        &nbsp;&nbsp;&nbsp;
                        <Dropdown.Button type="primary" overlay={menu8} onClick={()=>this.handleCategoryClick("儿童亲子")}>儿童亲子</Dropdown.Button>
                    </div>
                </Panel >
                <Panel header={"城市选择："+this.state.city} key="2" className="site-collapse-custom-panel" extra={genExtra2()}>
                    <Radio.Group onChange={this.onChange}  buttonStyle="solid" >
                        {/*<Radio.Button value="全国">全国</Radio.Button>*/}
                        <div><Tag color="volcano">a~g</Tag></div>
                        <Radio.Button value="北京">北京</Radio.Button>
                        <Radio.Button value="成都">成都</Radio.Button>
                        <Radio.Button value="重庆">重庆</Radio.Button>
                        <Radio.Button value="长春">长春</Radio.Button>
                        <Radio.Button value="长沙">长沙</Radio.Button>
                        <Radio.Button value="大连">大连</Radio.Button>
                        <Radio.Button value="东莞">东莞</Radio.Button>
                        <Radio.Button value="佛山">佛山</Radio.Button>
                        <Radio.Button value="福州">福州</Radio.Button>
                        <Radio.Button value="广州">广州</Radio.Button>
                        <Radio.Button value="贵阳">贵阳</Radio.Button>
                        <Radio.Button value="国际及港澳台">国际及港澳台</Radio.Button>
                        <div style={{paddingTop:10}}><Tag color="volcano">h~n</Tag></div>
                        <Radio.Button value="哈尔滨">哈尔滨</Radio.Button>
                        <Radio.Button value="海口">海口</Radio.Button>
                        <Radio.Button value="杭州">杭州</Radio.Button>
                        <Radio.Button value="合肥">合肥</Radio.Button>
                        <Radio.Button value="呼和浩特">呼和浩特</Radio.Button>
                        <Radio.Button value="济南">济南</Radio.Button>
                        <Radio.Button value="昆明">昆明</Radio.Button>
                        <Radio.Button value="南昌">南昌</Radio.Button>
                        <Radio.Button value="南京">南京</Radio.Button>
                        <Radio.Button value="南宁">南宁</Radio.Button>
                        <Radio.Button value="宁波">宁波</Radio.Button>
                        <div style={{paddingTop:10}}><Tag color="volcano">q~w</Tag></div>
                        <Radio.Button value="青岛">青岛</Radio.Button>
                        <Radio.Button value="厦门">厦门</Radio.Button>
                        <Radio.Button value="上海">上海</Radio.Button>
                        <Radio.Button value="深圳">深圳</Radio.Button>
                        <Radio.Button value="沈阳">沈阳</Radio.Button>
                        <Radio.Button value="石家庄">石家庄</Radio.Button>
                        <Radio.Button value="苏州">苏州</Radio.Button>
                        <Radio.Button value="太原">太原</Radio.Button>
                        <Radio.Button value="天津">天津</Radio.Button>
                        <Radio.Button value="无锡">无锡</Radio.Button>
                        <Radio.Button value="武汉">武汉</Radio.Button>
                        <div style={{paddingTop:10}}><Tag color="volcano">x~z</Tag></div>
                        <Radio.Button value="西安">西安</Radio.Button>
                        <Radio.Button value="郑州">郑州</Radio.Button>
                        <Radio.Button value="中国香港">中国香港</Radio.Button>
                        <Radio.Button value="珠海">珠海</Radio.Button>
                    </Radio.Group>
                </Panel>
            </Collapse>
        )
    }
}