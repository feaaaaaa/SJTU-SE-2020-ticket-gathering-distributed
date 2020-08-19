import React  from "react";
import "../css/logo.css"


export class Logo extends React.Component{
    render() {
        return (
            <div className="header-bottom">
                <div className="container">
                    <div className="logo wow fadeInDown" data-wow-duration=".8s" data-wow-delay=".2s">
                        <h1 style={{fontFamily:"STLiTi"}}>OLIGEI TICKET</h1>
                        <p><label className="of" id="of"></label>给 你 不 一 样 的 精 彩<label className="on"></label></p>
                    </div>
                </div>
            </div>
        );
    }
}