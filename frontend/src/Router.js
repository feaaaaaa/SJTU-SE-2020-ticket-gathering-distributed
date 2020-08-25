import React from 'react';
import { BrowserRouter as Router,Route,Switch } from 'react-router-dom';
import {LoginView} from "./view/LoginView";
import {HomeView} from "./view/HomeView";
import {RegisterView} from "./view/RegisterView";
import {SortPageView} from "./view/SortPageView";
import {OrderView} from "./view/OrderView";
import {ProfileView} from "./view/ProfileView";
import {DetailView} from "./view/DetailView";
import {SuccessView} from "./view/SuccessView";
import {AdminView} from "./view/AdminView";
import {AuctionView} from "./view/AuctionView";
import {AdminAuctionView} from "./view/AdminAuctionView";
import {AdminModifyView} from "./view/AdminModifyView";
import {AuctionDetailView} from "./view/AuctionDetailView";

class BasicRoute extends React.Component{


    render(){
        return(
            <Router>
                <Switch>
                    <Route path="/auctionDetail" component={AuctionDetailView}/>
                    <Route path="/adminModify" component={AdminModifyView}/>
                    <Route path="/adminAuction" component={AdminAuctionView}/>
                    <Route path="/admin" component={AdminView}/>
                    <Route path="/auction" component={AuctionView}/>
                    <Route path="/success" component={SuccessView}/>
                    <Route path="/detail" component={DetailView}/>
                    <Route path="/order" component={OrderView}/>
                    <Route path="/profile" component={ProfileView}/>
                    <Route path="/sortPage" component={SortPageView}/>
                    <Route path="/register" component={RegisterView}/>
                    <Route path="/login" component={LoginView}/>
                    <Route path="/" component={HomeView}/>
                </Switch>

            </Router>
        )
    }


}

export default BasicRoute;
