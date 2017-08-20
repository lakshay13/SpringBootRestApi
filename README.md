# SpringBootRestApi

The Spring Boot Rest Api Project implements REST API. The Rest API consists of the following
```
// Defines the controller class which consists of the following end points:

    GET: /users: End point will provide the list of all users.
    GET: /users/{id}: End point will return the data of the user searched.
    GET: /users/maxsalary: Return the user list sorted by max salary.
    DELETE: /users/remove/{id}: Deletes the user with specified Id if it exists.
    POST: /users/add: This end point adds a user to the list of user. It accepts query parameters empName, salary and Id.

// Basic spring boot authentication
     Default user name is user while the password is ***

// Uses H2 database for data persistence and retrieval

```

The testing methodology used involves the following:
```
1) Unit Testing by creating the simple mocks.
2) Integration Testing using MockMvc
3) Functional Testing using the RestAssured
```
The project can be run using Docker. The docker file has been constructed using the following format:
```
FROM: is used to set the base image for subsequent instructions. It is mandatory to set this in the first line of a Dockerfile. You can use it any number of times though.
MAINTAINER: refers to the author of the docker file
VOLUME: volume is created each time a container is started from an image.
ADD: copies the file from source to add directories/files to the docker image.
ENV: used to se the environment variables specific to services you wish to containerize
ENTRYPOINT: allow you to specify the startup command for an image.
```
