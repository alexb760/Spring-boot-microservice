# Gamification Microservice

This microservice is intended to split the logic in a different microservice just for educational
proposes since it can be done in a single monolithic application, as we can see in the **Multiplication**
project is located all the logic.
the modules we will be developing are:
- Check Result.
- Assign Point.
- Assign Badges.
- Update Leaderboard

## Chapter 6 version

In Chapter 6, you **enter into the world of microservices** with a first approach that uses synchronous processing and _orchestration_ from the Multiplication microservice. As the book explains, **this approach has many issues**: tight coupling, domain pollution, etc. Yet we covered it to learn why it's important to embrace _Asynchronous Processing_ and _Eventual Consistency_ in microservice architectures.

The main concepts included in this chapter are:

* Monolith vs. Microservices: Pros and Cons
* Starting with a _Small Monolith_
* Non-Functional Requirements in Microservices
* Building the Gamification microservice
* Connecting microservices with synchronous interfaces
* Analysis: Transactionality, Eventual Consistency, Fault Tolerance

## Running the app
* JDK 15+
* Node.js v12.16.3+
* npm 6.14.4+

1. To start the Multiplication microservice, you can use the command line with the included Maven wrapper:
    ```bash
    multiplication$ ./mvnw spring-boot:run
    ```
2. To start the Gamification microservice, you do the same from its corresponding folder:
    ```bash
    gamification$ ./mvnw spring-boot:run
    ```
2. The React application can be started with npm. First, you need to download the dependencies with:
    ```bash
    frontend-app$ npm install
    ```
3. Then, you start the server with:
    ```bash
    frontend-app$ npm start

4. Packaging: ``` mvnw package ``` or ``` mvnw build ```


### Reference Documentation
For further reference, please consider the following sections:

* [Learn Microservices with Spring Boot (2nd Edition)](https://tpd.io/book-extra).
  Check the [Book's Web Page](https://tpd.io/book-extra) to see the complete list of chapters.

* [Official Apache Maven documentation](https://maven.apache.org/guides/index.html)
* [Spring Boot Maven Plugin Reference Guide](https://docs.spring.io/spring-boot/docs/2.4.3/maven-plugin/reference/html/)
* [Create an OCI image](https://docs.spring.io/spring-boot/docs/2.4.3/maven-plugin/reference/html/#build-image)
* [Spring Data JPA](https://docs.spring.io/spring-boot/docs/2.4.3/reference/htmlsingle/#boot-features-jpa-and-spring-data)
* [Validation](https://docs.spring.io/spring-boot/docs/2.4.3/reference/htmlsingle/#boot-features-validation)
* [Spring Web](https://docs.spring.io/spring-boot/docs/2.4.3/reference/htmlsingle/#boot-features-developing-web-applications)


