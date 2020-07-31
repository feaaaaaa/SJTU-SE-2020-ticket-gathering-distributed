import { Card } from 'antd';
import React from "react";
import "../css/HomeCard.css"
import {Link} from "react-router-dom";

export class NavigateCard extends React.Component {

    render(){
        const gridStyle = {
            width: '12.5%',
            textAlign: 'center',
        };

        return(
            <Link to="/sortPage" onClick={()=>{localStorage.setItem("category",this.props.name)}}>
                <Card.Grid style={gridStyle}>
                    <div>
                        {this.props.icon}
                        <p> </p>
                        <a>{this.props.name}</a>
                    </div>
                </Card.Grid>
            </Link>
        )
    }

}