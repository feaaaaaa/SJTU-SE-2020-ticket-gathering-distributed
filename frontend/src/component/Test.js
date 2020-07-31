import Animated from 'animated/lib/targets/react-dom';
import Easing from 'animated/lib/Easing';
import React from 'react';
import { VelocityComponent } from 'velocity-react';
import {HomeCard} from "./HomeCard";

const info={actors:"  ",	category:"话剧歌剧 ",	city:"上海 ",	title:"上海话剧艺术中心·后浪新潮演出季 原创话剧 《谎言背后》 ",	price:180.0 ,	price_str:"180-280 ",	timescale:"2020.07.07-07.12 ",	subcategory:"话剧 ",	venue:"上海话剧艺术中心-戏剧沙龙 ",	activityIcon:"https://img.alicdn.com/bao/uploaded/https://img.alicdn.com/imgextra/i4/2251059038/O1CN018wyvEj2GdSDYJ4raf_!!2251059038.jpg","actitems":[{"actitemid":5,"website":"大麦网","price":[{"classcnt":3,"time":"2020.07.10","class":[{"price":100,"num":100},{"price":560,"num":100},{"price":1020,"num":100}]},{"classcnt":3,"time":"2020.07.11","class":[{"price":100,"num":100},{"price":560,"num":100},{"price":1020,"num":100}]}]},{"actitemid":5,"website":"聚橙网","price":[{"classcnt":3,"time":"2020.07.10","class":[{"price":100,"num":100},{"price":566,"num":100},{"price":1020,"num":100}]},{"classcnt":3,"time":"2020.07.11","class":[{"price":100,"num":100},{"price":566,"num":100},{"price":1020,"num":100}]}]},{"actitemid":5,"website":"永乐票务","price":[{"classcnt":3,"time":"2020.07.10","class":[{"price":100,"num":100},{"price":601,"num":100},{"price":1020,"num":100}]},{"classcnt":3,"time":"2020.07.11","class":[{"price":100,"num":100},{"price":601,"num":100},{"price":1020,"num":100}]}]}]};


export  class Test extends React.Component {
    // animatedValue = new Animated.Value(0)
    // animate = () => {
    //     this.animatedValue.setValue(0)
    //     Animated.timing(
    //         this.animatedValue,
    //         {
    //             toValue: 1,
    //             duration: 1000,
    //             easing: Easing.elastic(1)
    //         }
    //     ).start();
    // }
    // render() {
    //     const marginLeft = this.animatedValue.interpolate({
    //         inputRange: [0, 1],
    //         outputRange: [-120, 0],
    //     })
    //     return (
    //         <div>
    //             <div style={styles.button} onClick={this.animate}>Animate</div>
    //             <Animated.div
    //                 style={
    //                     Object.assign(
    //                         {},
    //                         styles.box,
    //                         { opacity: this.animatedValue, marginLeft })}>
    //                 <p>Thanks for your submission!</p>
    //             </Animated.div>
    //         </div>
    //     );
    // }




    // state = { anim: new Animated.Value(0) };
    // click = () => {
    //     Animated.timing(this.state.anim, {
    //         toValue: 100,
    //         duration: 500
    //     }).start();
    // };
    //
    // render() {
    //     return (
    //         <div className="App" style={{backgroundColor: "yellow"}}>
    //             <Animated.div
    //                 className="box"
    //                 style={{ left: this.state.anim }}
    //                 onClick={this.click}
    //             />
    //         </div>
    //     );
    // }


    constructor(props) {
        super(props);
        this.state={
            hovering:false,
        }
    }



    renderTopState=()=>{
        // return <p style={{backgroundColor:"yellow"}}>123</p>
        // return <HomeCard info={info}/>
        return <div style={{backgroundColor:"yellow",height:100,width:100}}>1111111111 </div>
    }

    renderUnderneathStats=()=>{
        // return <p style={{backgroundColor:"blue"}}>456</p>
        return <div style={{backgroundColor:"blue",height:100,width:100,marginTop:-100}}>222222222222 </div>

    }

    render(){
        var animationProps;
        if (this.state.hovering) {
            animationProps = {
                duration: 200,
                animation: {
                    rotateX: 160
                }
            };
        } else {
            animationProps = {
                duration: 1100, // longer due to swinging
                animation: {
                    rotateX: [0, 'spring']
                }
            };
        }



            return (
            <div onMouseEnter={() =>{ this.setState({hovering: true}); }}
                 onMouseLeave={()=> { this.setState({hovering: false}); }}>
                <VelocityComponent {...animationProps}>
                    {this.renderTopState()}
                </VelocityComponent>
                {this.renderUnderneathStats()}
            </div>
        );
    }
}

