import { Result, Button } from 'antd';
import React from "react";

export class SuccessView extends React.Component{
    render() {
        return(
            <Result
                status="success"
                title="Successfully Purchased!"
                subTitle="Please enjoy your wonderful journey."
                extra={[
                    <Button type="primary" key="console" href="/">
                        回到首页
                    </Button>,
                    <Button key="buy" href="/detail">再次购买</Button>,
                ]}
            />
        )
    }
}