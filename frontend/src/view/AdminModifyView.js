import React from "react";
import {AdminModify} from "../component/AdminModify";
import {HeaderInfo} from "../component/Header";

export class AdminModifyView extends React.Component{
    render() {
        return (
            <div>
                <HeaderInfo/>
                <AdminModify/>
            </div>
        );
    }
}