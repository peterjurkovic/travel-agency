# travel-agency

Travel Agency integrated flow with call center. The system consits of three services

1. Client service - avaiable to a clients using which can do a booking, comunicate with an agent and call the agent
2. Conversation service - is responsible for communication 
3. Admin service - Used by agent and booking management


## Stack
- MongoDB 3.6
- Spring Boot 2
- Spring Data Key
- Spring Security
- STOMP
- Thymeleaf & Bootstrap & jQuery
- Nexmo API platform
 

## Develompent setup

### MongoDB setup
Install MongoDB 3.6 [follow official doc](https://docs.mongodb.com/manual/installation/) and setup mongo enviroment
    
```
    $ mongo
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
 NEXMO_APPLICATIONID=<APPLICATION_ID>
 NEXMO_JWTPRIVATEKEY=<ABSOLUTE_PATH_TO_A_APPLICATION_PRIVATE_KEY>
 VOICE_AGENTNUMBER=<AGENTS_PHONE>
 ```
### How to build applications

```
./gradlew travel-agency-client:bootJar
./gradlew travel-agency-admin:bootJar
./gradlew travel-agency-conversation:bootJar

```
