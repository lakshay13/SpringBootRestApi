package com.suri.springboot.model;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * @Data annotation allows us to put the getters, setters, hashcode, toString method. It is provided by Lombok
 * @AllArgsConstructor annotation allows us to provide by default all arg constructor. It is provided by Lombok
 * @NoArgsConstructor annotation allows us to provide a by default no argument constructor. It is provided by Lombok
 * @Entity is created to indicate that each entity instance corresponds to a row in a table.
 *
 * @author lakshay13@gmail.com & jackjohnson43@gmail.co
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Users {
    @Id
    private Integer id;
    private String empName;
    private Integer salary;
}
