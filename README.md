# SJTU-SE-2020-ticket-gathering-distributed
## Spring Cloud: Eureka + Feign


## Database Design: Mysql+MongoDB+Neo4j
### Mysql+MongoDB

  tg_users(userid,username,gender,email,phone,password,type,usericon(MongoDB))
  
  tg_activities(activityid,title,actor,timescale,venue,imgurl,description(MongoDB))
  
  tg_actitems(actitemid,activityid,website,prices(MongoDB))
  
    prices: { timecnt: 3, tickets: [ {time:’2020-07-08’, classcnt: 3, class: [{price: 300, num: 100} , {} , {} ] } , {} , {} ] }
    
  tg_orders(orderid,userid,actitemid,price,amount,showtime,ordertime)
  
### Neo4j for recommendation and classification

  Tripartite graph：User,Activity,Property
  
  Property: city,category(subcategory),actor
  
  (Activity,Property) for classification and similarity recommendation: easy to achieve in neo4j
  
  (User,Activity) for personalized recommendation: Personal Ranks Algorithm 
  
    https://blog.csdn.net/qq_40006058/article/details/83444131 for more
