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
        List<Users> usersFound = userRepository.findAllByOrderBySalaryAsc();

        if (usersFound.size() == 0) {
            logger.error("User list is empty");
            return new ResponseEntity(new CustomisedErrorType("User list is empty"), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<List<Users>>(usersFound, HttpStatus.OK);
    }
}
