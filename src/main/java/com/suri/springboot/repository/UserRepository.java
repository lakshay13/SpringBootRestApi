package com.suri.springboot.repository;

import java.util.List;

import com.suri.springboot.model.Users;
import org.springframework.data.repository.CrudRepository;

/**
 *
 * Specifies methods used to obtain and modify data related with the user which is stored in the database. It also provides method such as
 * save to persist data in the database. The by default database used will be H2.
 *
 *  @author lakshay13@gmail.com & jackjohnson43@gmail.com
 */
public interface UserRepository extends CrudRepository<Users, Integer> {

     /**
      * Method finds the user ordered by salary in ascending order.
      * @return the list of users.
      */
     List<Users> findAllByOrderBySalaryDesc();
}
