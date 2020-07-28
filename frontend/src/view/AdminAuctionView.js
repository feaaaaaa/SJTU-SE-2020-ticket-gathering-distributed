import React from 'react';
import {HeaderInfo} from "../component/Header";
import {AdminAuction} from "../component/AdminAuction";


export class AdminAuctionView extends React.Component{
    render() {
        return (
            <div>
                <HeaderInfo/>
                <div style={{paddingTop:40}}>
                    <AdminAuction />
                </div>
            </div>
        );
    }
}