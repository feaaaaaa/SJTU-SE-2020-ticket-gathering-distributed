import React from 'react';
import {AuctionDetail} from "../component/AuctionDetail";
import {HeaderInfo} from "../component/Header";


export class AuctionDetailView extends React.Component{
    render(){
        return(
            <div>
                <HeaderInfo/>
                <div style={{paddingTop: 120, width: "80%", margin: "auto"}}>
                    <AuctionDetail/>
                </div>
            </div>
        )
    }
}