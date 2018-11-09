package com.example.mmcke.campuscoupons.model;

import java.util.ArrayList;
import com.example.mmcke.campuscoupons.model.School;

/**
 * Created by mmcke on 11/1/2018.
 *
 * Abstract class that has information relevant to all users
 */

public abstract class User {
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String phoneNumber;
    private String school;

    public User(String _firstName, String _lastName, String _email, String _password, String _phoneNumber,
                String _school) {
        firstName = _firstName;
        lastName = _lastName;
        email = _email;
        password = _password;
        phoneNumber = _phoneNumber;
        school = _school;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getSchoolName() { return school;}
}
