package com.suri.springboot;

import java.util.List;

import com.suri.springboot.controller.RestApiController;
import com.suri.springboot.model.Users;
import com.suri.springboot.repository.UserRepository;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import static com.suri.springboot.util.UserHelper.getUserInserted;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.Matchers.is;
import static org.mockito.Matchers.anyInt;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

/**
 *  @author lakshay13@gmail.com & jackjohnson43@gmail.com
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = RANDOM_PORT)
public class RestAssuredTest {

    @MockBean
    private UserRepository userRepository;

    @Autowired
    private RestApiController restApiController;

    @LocalServerPort
    int port;


    @BeforeClass
    public static void setup() {

        RestAssured.authentication = RestAssured.basic("user", "pwd");

    }

    /**
     * Test get the list of users. Do note that the port needs to be defined as its a random port being used.
     * @throws Exception throws exception if it fails.
     */
    @Test
    public void testGetUsers() throws Exception {

        List<Users> user = getUserInserted();

          given().
              contentType(ContentType.JSON).
              port(port).
          when().
              get("/users/").
          then().
              body("id", hasItems(user.get(0).getId(), user.get(1).getId(), user.get(2).getId(), user.get(3).getId())).
              body("empName", hasItems(user.get(0).getEmpName(), user.get(1).getEmpName(), user.get(2).getEmpName(), user.get(3).getEmpName()));
    }

    /**
     * Test get the user by specific Id.
     * @throws Exception throws exception if it fails.
     */
    @Test
    public void testGetUserById() throws Exception {

        org.mockito.BDDMockito.given(userRepository.findOne(anyInt())).willReturn(getUserInserted().get(0));
        List<Users> user = getUserInserted();

        given().
               contentType(ContentType.JSON).
               port(port).
        when().
               get("/users/{id}", "123").
        then().
               body("id", is(user.get(0).getId())).
               body("empName", is(user.get(0).getEmpName())).
               body("salary", is(user.get(0).getSalary()));

    }
}
