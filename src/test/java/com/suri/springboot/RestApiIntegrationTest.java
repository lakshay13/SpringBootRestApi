package com.suri.springboot;

import java.util.List;

import com.suri.springboot.controller.RestApiController;
import com.suri.springboot.model.Users;
import com.suri.springboot.repository.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static com.suri.springboot.util.UserHelper.getUserInserted;
import static com.suri.springboot.util.UserHelper.getUserListBySalaryDescending;
import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.anyInt;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * 1) This class is used to perform rest api integration test using MockMvc concept.
 *
 *  @author lakshay13@gmail.com & jackjohnson43@gmail.com
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class RestApiIntegrationTest {

    @MockBean
    private UserRepository userRepository;

    @InjectMocks
    private RestApiController restApiController;

    private MockMvc mockMvc;

    @Before
    public void setUp() {

        MockitoAnnotations.initMocks(this);
        // standaloneSetup gives the user the ability to mock the dependencies as needed. It does not load the spring configuration.
        // webAppContextSetup loads the spring configuration and injects the WebApplicationContext into the test.
        mockMvc = MockMvcBuilders.standaloneSetup(restApiController).build();
    }

    /**
     * Test get the user by specific Id.
     * @throws Exception throws exception if it fails.
     */
    @Test
    public void testGetUserById() throws Exception {

        given(userRepository.findOne(anyInt())).willReturn(getUserInserted().get(0));
        Users user = getUserInserted().get(0);

        // perform() performs a request and return a type that allows chaining further asserts

        mockMvc.perform(get("/users/{id}", "123"))
                                             .andExpect(jsonPath("$.id", is(user.getId())))
                                             .andExpect(jsonPath("$.empName", is(user.getEmpName())))
                                             .andExpect(jsonPath("$.salary", is(user.getSalary())))
                                             .andExpect(status().isOk());
    }

    /**
     * Test get the user list in descending order of salary.
     * @throws Exception throws exception if it fails.
     */
    @Test
    public void testGetUserByHighestSalary() throws Exception {

        given(userRepository.findAllByOrderBySalaryDesc()).willReturn(getUserListBySalaryDescending());
        List<Users> user = getUserInserted();

        // perform() performs a request and return a type that allows chaining further asserts

        mockMvc.perform(get("/users/maxsalary"))
                                             .andExpect(jsonPath("$.[0].id", is(user.get(3).getId())))
                                             .andExpect(jsonPath("$.[0].empName", is(user.get(3).getEmpName())))
                                             .andExpect(jsonPath("$.[0].salary", is(user.get(3).getSalary())))
                                             .andExpect(status().isOk());
    }
}
