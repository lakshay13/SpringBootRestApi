package com.suri.springboot;

import java.util.List;

import com.suri.springboot.controller.RestApiController;
import com.suri.springboot.model.Users;
import com.suri.springboot.repository.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static com.suri.springboot.util.UserHelper.getUserInserted;
import static com.suri.springboot.util.UserHelper.getUserListBySalaryDescending;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.anyInt;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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

    /**
     * Test delete a specific user with its id.
     * @throws Exception throws exception if it fails.
     */
    @Test
    public void testRemoveTheUserById() throws Exception {
        given(userRepository.findOne(Matchers.anyInt())).willReturn(getUserInserted().get(3));
        MvcResult result = mockMvc.perform(
                delete("/users/remove/{id}", 126))
               .andExpect(status().isOk())
               .andReturn();

        assertEquals(result.getResponse().getContentAsString(), "User successfully deleted");
    }

    /**
     * Test trying to add a user with all the parameters needed.
     * @throws Exception if user can not be added.
     */
    @Test
    public void testAddAdditionOfUser() throws Exception {

        String empName = "Andy Murray";
        Users users = new Users(128, "Andy Murray", 50000);

        given(userRepository.save(Matchers.any(Users.class))).willReturn(users);

        MvcResult mvcResult = mockMvc.perform(
                post("/users/add")
               .param("empName", empName)
               .param("salary", "50000")
               .param("Id", "127"))
               .andExpect(status().isOk())
               .andReturn();

        assertEquals(mvcResult.getResponse().getContentAsString(), "User " + users + " successfully created");
    }

    /**
     * Test trying to add a user with empName as null.
     * @throws Exception if user can not be added.
     */
    @Test
    public void testAdditionOfUserWithOneOfParameterAsNull() throws Exception {

        String empName = null;
        Users users = null;

        given(userRepository.save(Matchers.any(Users.class))).willReturn(users);

        MvcResult mvcResult = mockMvc.perform(
                post("/users/add")
                .param("empName", empName)
                .param("salary", "28500")
                .param("Id", "128"))
                .andExpect(status().is(400))
                .andReturn();

        assertEquals(mvcResult.getResponse().getContentAsString(), "User " + users + " cannot be created");
    }
}
