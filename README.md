# travel-agency
Travel Agency integrated flow with call center.


## Stack
- MongoDB 3.6
- Spring Boot 2
- Spring Data Key
- Spring Security
- Lombock
- STOMP
- Thymeleaf & Bootstrap & jQuery & S Vue.js
- Nexmo API platform
 

## Develompent setup

### MongoDB setup
Install MongoDB 3.6 [follow official doc](https://docs.mongodb.com/manual/installation/) and setup mongo enviroment
    
```
    $ mongo
    $ use travelagency
    $ use travelagency
    $ db.createUser(
        {
            user: "travelagency",
            pwd: "YOUR_PASS",
            roles: [ { role: "userAdmin", db: "travelagency" } ]
        }
    )
```
 Set an env variable `SPRING_DATA_MONGODB_PASSWORD=YOUR_PASS`
    
### Nexmo credentials configuration
Set also following ENV variables
 ```
 NEXMO_APIKEY=<VALUE>
 NEXMO_APISECRET=<VALUE>
 ```
