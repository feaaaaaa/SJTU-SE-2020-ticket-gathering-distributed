import React from "react";
import { Typography, Divider } from 'antd';
const { Title, Paragraph, Text } = Typography;

export class PurchaseNotice extends React.Component{

    render() {
        return(
            <Typography>
                <Title>购票须知</Title>
                <Divider/>
                <br/>
                <Title level={4}>限购规则</Title>
                <Paragraph>每笔订单最多购买6张</Paragraph>
                <br/>
                <Title level={4}>退票/换票规则</Title>
                <Paragraph>票品为有价证券，非普通商品，其背后承载的文化服务具有时效性，稀缺性等特征，不支持退换。
                </Paragraph>
                <br/>
                <Title level={4}>入场规则
                </Title>
                <Paragraph>须打开【APP-我的票夹】扫码入场，截图无效。
                </Paragraph>
                <br/>
                <Title level={4}>儿童购票
                </Title>
                <Paragraph>儿童一律凭成人票入场
                </Paragraph>
                <br/>
                <Title level={4}>发票说明
                </Title>
                <Paragraph>演出开始前，去【APP-订单详情页】提交发票申请。演出结束后1个月左右邮寄给你，需自付邮费。
                </Paragraph>
                <br/>
                <Title level={4}>实名购票规则
                </Title>
                <Paragraph>一张门票对应一个证件；证件支持：护照/港澳居民来往内地通行证/台湾居民来往大陆通行证/身份证
                </Paragraph>
                <br/>
                <Title level={4}>异常排单说明
                </Title>
                <Paragraph>对于异常订购行为，大麦网有权在订单成立或者生效之后取消相应订单。异常订购行为包括但不限于以下情形： （1）通过同一ID订购超出限购张数的订单。 （2）经合理判断认为非真实消费者的下单行为，包括但不限于通过批量相同或虚构的支付账号、收货地址（包括下单时填写及最终实际收货地址）、收件人、电话号码订购超出限购张数的订单。
                </Paragraph>
            </Typography>
        )
    }
}