package com.suri.springboot;

import java.util.List;

import com.suri.springboot.controller.RestApiController;
import com.suri.springboot.model.Users;
import com.suri.springboot.repository.UserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import static com.suri.springboot.util.UserHelper.getUserInserted;
import static com.suri.springboot.util.UserHelper.getUserListBySalaryDescending;
import static org.junit.Assert.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.anyInt;

/**
 *
 * Spring Test functioning:
 * 1) This class is used to perform rest api unit test using the concept of mocking the repositories.
 * 2) The search algorithm works up from the package that contains the test until it finds a @SpringBootApplication/@SpringBootConfiguration
 * annotated class.
 *
 *  @author lakshay13@gmail.com & jackjohnson43@gmail.com
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class RestApiUnitTest {


    @MockBean
    private UserRepository userRepository;

    @Autowired
    private RestApiController restApiController;


    @Test
    public void testGetUserById() {

        given(userRepository.findOne(anyInt())).willReturn(getUserInserted().get(0));
        ResponseEntity<Users> userFound = restApiController.getUserById(123);

        assertEquals(userFound.getStatusCode(), HttpStatus.OK);
        assertEquals(userFound.getBody(), getUserInserted().get(0));
    }

    @Test
    public void testGetUserByHighestSalary() {
        given(userRepository.findAllByOrderBySalaryDesc()).willReturn(getUserListBySalaryDescending());
        ResponseEntity<List<Users>> usersFound = restApiController.getUserByHighestSalary();

        assertEquals(usersFound.getStatusCode(), HttpStatus.OK);
        assertEquals(usersFound.getBody(), getUserListBySalaryDescending());
    }
}
