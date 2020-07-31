use ticket_gathering  //创建并进入数据库ticket_gathering
db.createCollection("tg_users")
db.createCollection("tg_activities")
db.createCollection("tg_actitems")
db.tg_users.insert({'userid':1,'usericon':'i-am-an-icon'})
db.tg_activities.insert({'activityid':1,'description':'i-am-description'})
db.tg_actitems.insert({'actitemid':1,'prices':{ timecnt: 3, tickets: [ {time:’2020-07-08’, classcnt: 3, class: [{price: 300, num: 100} , {} , {} ] } , {} , {} ] }
})
