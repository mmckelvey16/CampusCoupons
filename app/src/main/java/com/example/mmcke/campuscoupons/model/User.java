package com.example.mmcke.campuscoupons.model;

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
    private enum school {
        GEORGIASTATE;
    }

    public User(String _firstName, String _lastName, String _email, String _password, String _phoneNumber) {
        firstName = _firstName;
        lastName = _lastName;
        email = _email;
        password = _password;
        phoneNumber = _phoneNumber;
        school _school = school.GEORGIASTATE;
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
}
