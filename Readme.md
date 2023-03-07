# Banking Service

#### Version 1.0.0

## Java  17

Make sure to use Java 17 building project.

## Build Project

 `mvn clean install`

### Actuator:

It uses HTTP endpoints to expose operational information about any running application. The main benefit of using this library is that we get health and
monitoring metrics from production-ready applications.

The only two available by default are /health and /info.

* /health:  summarizes the health status of our application.
* /info:  returns general information. It might be custom data, build information or details about the latest commit.
* /metrics:  details metrics of our application. This might include generic metrics as well as custom ones.
* /configprops: allows us to fetch all @ConfigurationProperties beans.
* /threaddump: dumps the thread information of the underlying JVM.
* /loggers: enables us to query and modify the logging level of our application.

#### Local:

```
http://localhost:8061/actuator
http://localhost:8061/actuator/health
http://localhost:8061/actuator/info
http://localhost:8061/actuator/metrics
http://localhost:8061/actuator/configprops/{prefix}
http://localhost:8061/actuator/threaddump
http://localhost:8061/actuator/loggers
```

### Things can be done
Adding actuator, swagger, validation for request body, security implementation