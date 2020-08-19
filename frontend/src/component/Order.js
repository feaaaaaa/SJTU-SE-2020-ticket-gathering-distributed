import React from 'react';
import {List, Avatar, Space, message} from 'antd';
import { MessageOutlined, LikeOutlined, StarOutlined } from '@ant-design/icons';
import "../css/order.css"
import {getOrderInfoByUser} from "../service/orderServcie";
import {sports} from "../const/activity";
import {Redirect} from "react-router-dom";

// const listData = [];
// for (let i = 0; i < 5; i++) {
//     // listData.push({
//     //     href: 'https://ant.design',
//     //     title: `user test${i}`,
//     //     avatar: 'https://zos.alipayobjects.com/rmsportal/ODTLcjxAfvqbxHnVXCYX.png',
//     //     description:
//     //         'Ant Design, a design language for background applications, is refined by Ant UED Team.',
//     //     content:
//     //         'We supply a series of design principles, practical patterns and high quality design resources (Sketch and Axure), to help people create their product prototypes beautifully and efficiently.',
//     // });
//     listData.push({
//         href: '',
//         title: sports[i].title,
//         avatar: 'https://zos.alipayobjects.com/rmsportal/ODTLcjxAfvqbxHnVXCYX.png',
//         description:
//             sports[i].time+sports[i].city,
//         venue:
//             sports[i].venue,
//         url:
//             sports[i].imgurl,
//         cnt:
//             2,
//         totPrice:
//             sports[i].price*2,
//         orderTime:
//             "2020.7.11 17:47"
//     });
// }
//
// const IconText = ({ icon, text }) => (
//     <Space>
//         {React.createElement(icon)}
//         {text}
//     </Space>
// );

export class Order extends React.Component{
    constructor(props) {
        super(props);
        this.state={
            orderInfo:null,
            avatar: 'https://zos.alipayobjects.com/rmsportal/ODTLcjxAfvqbxHnVXCYX.png',
            ifauthen:false,
        }
    }

    async componentDidMount() {
        let userId = localStorage.getItem("userId");
        const callback = async (data) => {
            // if (data.message === "authentication failure") {
            if(data.status===-100 ||data.status===201){
                await this.setState({ifauthen: true});
                localStorage.clear();
            } else await this.setState({orderInfo: data.data});
        };
        await getOrderInfoByUser(userId, localStorage.getItem("token"), callback);
    }

    render() {
        if(this.state.ifauthen){
            message.error("请先登录");
            return <Redirect to={{pathname: "/login"}}/>;
        }
        // console.log(this.state.orderInfo);
        else if (this.state.orderInfo === null) {
            return (
                <p>404 error</p>
            )
        }
        else {
            return (
                <div>
                    <div id="orderDiv">
                        <List
                            itemLayout="vertical"
                            size="large"
                            pagination={{
                                onChange: page => {
                                    console.log(page);
                                },
                                pageSize: 2,
                            }}
                            dataSource={this.state.orderInfo}
                            footer={
                                <div style={{textAlign: "center"}}>
                                    <b>O_ligei ! </b> footer part
                                </div>
                            }
                            renderItem={item => (
                                <List.Item
                                    key={item.title}
                                    extra={
                                        <img width={150} alt="activity image" src={item.activityIcon}/>
                                    }
                                >
                                    <List.Item.Meta
                                        avatar={<Avatar src={this.state.avatar} />}
                                        // title={<a href={item.href}>{item.title}</a>}
                                        title={<p>{item.title}</p>}
                                        description={"日期： "+item.showtime}
                                    />
                                    <div id="contentDiv">
                                        <p>地点：{item.venue}</p>
                                        <p>数量：{item.amount}张</p>
                                        <p>总价: ￥{item.price*item.amount}</p>
                                        <p>下单时间: {item.orderTime}</p>
                                    </div>
                                </List.Item>
                            )}
                        />
                    </div>
                </div>
            )
        }
    }
}