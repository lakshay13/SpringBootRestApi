package com.suri.springboot.util;

import java.util.ArrayList;
import java.util.List;

import com.suri.springboot.model.Users;

/**
 * @author lakshay13@gmail.com
 */
public class UserHelper {

    public static List<Users> getUserInserted() {

        List<Users> list = new ArrayList<Users>();
        Users u1 = new Users(123, "Lakshay Suri", 27000);
        Users u2 = new Users( 124, "Borris Becker", 27500);
        Users u3 = new Users( 125, "Jack Johnson",27700);
        Users u4 = new Users(126, "Lakshay Suri", 60000);

        list.add(u1);
        list.add(u2);
        list.add(u3);
        list.add(u4);

        return list;
    }


    public static List<Users> getUserListBySalaryDescending() {
        List<Users> list = new ArrayList<Users>();
        Users u1 = new Users(126, "Lakshay Suri", 60000);
        Users u2= new Users( 125, "Jack Johnson",27700);
        Users u3 = new Users( 124, "Borris Becker", 27500);
        Users u4 = new Users(123, "Lakshay Suri", 27000);

        list.add(u1);
        list.add(u2);
        list.add(u3);
        list.add(u4);

        return list;
    }
}
