import React from "react";
import { Typography, Divider } from 'antd';
const { Title, Paragraph, Text } = Typography;

export class WatchNotice extends React.Component{

    render() {
        return(
            <Typography>
                <Title>观影须知</Title>
                <Divider/>
                <br/>
                <Title level={4}>演出时长</Title>
                <Paragraph>以现场为准</Paragraph>
                <br/>
                <Title level={4}>入场时间</Title>
                <Paragraph>提前60分钟，仅供参考，最终以现场实际入场时间为准</Paragraph>
                <br/>
                <Title level={4}>禁止携带物品</Title>
                <Paragraph>由于安保和版权的原因，大多数演出、展览及比赛场所禁止携带食品、饮料、专业摄录设备、打火机等物品，请您注意现场工作人员和广播的提示，予以配合。</Paragraph>
                <br/>
                <Title level={4}>寄存说明</Title>
                <Paragraph>无寄存处,请自行保管携带物品，谨防贵重物品丢失。</Paragraph>
                <br/>
                <Title level={4}>转送说明</Title>
                <Paragraph>本项目支持电子票转送，转送功能将在演出前24小时关闭。（注：当前仅支持大麦下单用户，暂不支持淘宝／天猫下单用户发起转送，仅支持大麦APP发起和接收转送票品）</Paragraph>
            </Typography>
        )
    }
}