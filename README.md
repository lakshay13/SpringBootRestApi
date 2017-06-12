# SpringBootRestApi

The Spring Boot Rest Api Project implements REST API. The Rest API consists of the following
```
// Defines the controller class which consists of the following end points:

    /users: End point will provide the list of all users.
    /users/{id}: End point will return the data of the user searched.
    /users/maxsalary: Return the user list sorted by max salary.
    /users/remove/{id}: Deletes the user with specified Id if it exists.

// Basic spring boot authentication
     Default user name is user while the password is ***

// Uses H2 database for data persistence and retrieval

```

The testing methodology used involves the following:
```
1) Unit Testing by creating the simple mocks.
2) Integration Testing using MockMvc
3) Integration Testing using the RestAssured
```