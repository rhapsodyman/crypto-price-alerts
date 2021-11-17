# crypto-price-alerts

### Helpful resources for Vue JS and Spring Integration with JWT

*  [jwt-vue-vuex-authentication](https://www.bezkoder.com/jwt-vue-vuex-authentication/)
*  [spring-boot-jwt-authentication](https://www.bezkoder.com/spring-boot-jwt-authentication/)
*  [spring-boot-vue-js-authentication-jwt-spring-security](https://www.bezkoder.com/spring-boot-vue-js-authentication-jwt-spring-security/)

And same resources **github**:
*  [vue-vuex-jwt-auth](https://github.com/bezkoder/vue-vuex-jwt-auth)
*  [spring-boot-spring-security-jwt-authentication](https://github.com/bezkoder/spring-boot-spring-security-jwt-authentication)


### Example of Vue and Spring in the same build
*  [video](https://www.youtube.com/watch?v=2G6r2f40Lps)
*  [git repo](https://github.com/danvega/full-stack-java-vue)



### Notes
This project uses H2 database - and after ```Application```.java is started you will need to create a record in the ```roles``` table

```sh 
INSERT INTO roles(name) VALUES('USER');
```


We are using Twilio to be able to send SMS messages and Coinmarketcap API to get quotes
See below APIs
* https://coinmarketcap.com/api/documentation/v1/#tag/cryptocurrency
* https://www.twilio.com/docs/sms/quickstart/java


These API parameters will be stored in ```application.properties``` file 

```sh
sms.from=<from phone number> # get it from Twillio: twilio.com/user/account
sms.sid=<SID> # get it from Twillio account page>
sms.authToken=<auth token> # secret, get it from Twillio account page
quotes.baseUrl=https://pro-api.coinmarketcap.com
quotes.apiKey=<api key> # get it from coinmarketcap API
```


### Swagger
Swagger will be available on http://localhost:9000/swagger-ui/index.html


## Build
To generate a deployable jar file (API + UI) run following
```sh 
mvn clean package
```