# General Architecture Backend

> _Remove any line with this kind of formatting and use your own_

## About This Repo

> _This repo will serve as a basic template when you creating new backend project using Spring Boot.  
> Please follow project structure in this repo  
> Provide what this repo capability/domain it served in this section_

## Getting Started

> _This is an example of how you may give instructions on setting up your project locally._

To get a local copy up and running follow these steps.

### Prerequisites

This repo use these in order to run properly:

* Java 21
* Redis
* Kafka
* Docker
* Docker Compose
* PostgreSQL

### Setting Up

> _Define step needed in order to run this repo  
> Example:  
> 1\. Set port forwarding to_ [_keycloak_](https://bankmas.atlassian.net/wiki/spaces/EN/pages/64978945/how+to+aws+cli+k8s+for+port-forward+or+check+log+etc)_  
> 2\. Set database, redis, and kafka connection_

### Usage Example

> _Define how to run the project here_

**Rest API**  
Run it using this command in repo root folder

```
docker-compose up -d
```

can be accessed from [http://localhost:8083](http://localhost:8083/)

**Background service/task**  
[https://docs.spring.io/spring-cloud-task/docs/2.3.0-M1/reference/](https://docs.spring.io/spring-cloud-task/docs/2.3.0-M1/reference/)  
[https://github.com/spring-cloud/spring-cloud-task/tree/main/spring-cloud-task-samples](https://github.com/spring-cloud/spring-cloud-task/tree/main/spring-cloud-task-samples)

**Test coverage**

```
./mvnw clean test jacoco:report
```

check dir “target/jacoco.exec” and “target/site/index.html”  
[reference](https://www.baeldung.com/jacoco)

**API Documentation**  
We’re using OpenAPI 3, follow [this link](https://www.baeldung.com/spring-rest-openapi-documentation) for reference  
Can be accessed from [http://localhost:8083/swagger-ui/index.html](http://localhost:8083/swagger-ui/index.html)

**Database Migration**  
[https://javabydeveloper.com/spring-boot-loading-initial-data/](https://javabydeveloper.com/spring-boot-loading-initial-data/)  
[https://stackoverflow.com/questions/42135114/how-does-spring-jpa-hibernate-ddl-auto-property-exactly-work-in-spring](https://stackoverflow.com/questions/42135114/how-does-spring-jpa-hibernate-ddl-auto-property-exactly-work-in-spring)

## Notes

* Health check using Spring Actuator
* Use Kafka for messaging based use case \(example: sending notification\)
* Use Redis for data that is read heavy and rarely change