import React from 'react';
import {Layout} from 'antd';
import {Link} from "react-router-dom";

const {Footer}=Layout;

export class FooterInfo extends React.Component{
    render() {
        return (
            <Footer style={{textAlign: 'center'}}>Copyright ©2020 Created by o_ligei</Footer>
        )
    }
}