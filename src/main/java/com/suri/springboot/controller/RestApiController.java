package com.suri.springboot.controller;

import java.util.List;

import com.suri.springboot.exception.CustomisedErrorType;
import com.suri.springboot.model.Users;
import com.suri.springboot.repository.UserRepository;
import com.suri.springboot.service.UserService;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static com.suri.springboot.util.UserHelper.getUserInserted;

/**
 * @author lakshay13@gmail.com
 */
@RestController
public class RestApiController {

    public static final Logger logger = org.slf4j.LoggerFactory.getLogger(RestApiController.class);

    @Autowired
    UserService userService;

    @Autowired
    UserRepository userRepository;

    private List<Users> tempUserList;

    /**
     * Method that gets the entire list of user.
     * @return the list of user.
     */
    @RequestMapping(value = "/users/", method = RequestMethod.GET)
    public ResponseEntity<List<Users>> getAllUsers() {
        tempUserList = getUserInserted();
        userRepository.save(tempUserList);
        return new ResponseEntity<List<Users>>(tempUserList, HttpStatus.OK);
    }

    /**
     * Method that finds the user by id.
     * @param id the value using which search of the user should be done in the database
     * @return the user found or the error message if not found.
     */
    @RequestMapping(value = "/users/{id}", method = RequestMethod.GET)
    public ResponseEntity<Users> getUserById(@PathVariable("id") Integer id) {
        logger.info("Trying to find the user by id given" + id);
        Users userFound = userRepository.findOne(id);
        if (userFound == null) {
            logger.error("User with id {} not found.", id);
            return new ResponseEntity(new CustomisedErrorType("User with id " + id + " not found"), HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<Users>(userFound, HttpStatus.OK);
    }

    /**
     * Method that finds the user by highest salary.
     * @return the user list ordered by salary ascending or the error message if the list is empty.
     */
    @RequestMapping(value = "/users/maxsalary", method = RequestMethod.GET)
    public ResponseEntity<List<Users>> getUserByHighestSalary() {
        logger.info("Trying to find the user sorted by highest salary");
        List<Users> usersFound = userRepository.findAllByOrderBySalaryDesc();

        if (usersFound.size() == 0) {
            logger.error("User list is empty");
            return new ResponseEntity(new CustomisedErrorType("User list is empty"), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<List<Users>>(usersFound, HttpStatus.OK);
    }

    /**
     * Method that deletes the specific user by specifying the user id
     * @param id the user to be deleted
     * @return either an error if the user to be deleted does not exist or a success message.
     */
    @RequestMapping(value="/users/remove/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<String> removeUserById(@PathVariable("id") Integer id) {
        logger.info("Trying to find the user that needs to be deleted");
        Users userToBeDeleted = userRepository.findOne(id);
        if (userToBeDeleted == null) {
            logger.error("User to be deleted with id { } does not exist", id);
            return new ResponseEntity(new CustomisedErrorType("User with id " + id + " not found"), HttpStatus.NOT_FOUND);
        } else {
            userRepository.delete(id);
        }

        return new ResponseEntity<String>("User successfully deleted", HttpStatus.OK);
    }

    @RequestMapping(value = "/users/add", method = RequestMethod.POST)
    public ResponseEntity<String> createAUser(@RequestParam(value ="empName", required = false) String empName,
                                              @RequestParam(value = "salary", required = false) Integer salary,
                                              @RequestParam(value = "Id", required = false) Integer id) {


        logger.info("Creating the user {} with the salary {} and id {} given", empName, salary, id);

        Users userSaved;
        if (empName!= null && salary!= null && id!= null) {
            Users users = new Users(id, empName, salary);
            userSaved = userRepository.save(users);
        } else {
            logger.error("User {} with the salary {} and id {} cannot be created", empName, salary, id);
            return new ResponseEntity<String>("User " + empName + " cannot be created", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<String>("User " + userSaved + " successfully created", HttpStatus.OK);
    }

}
