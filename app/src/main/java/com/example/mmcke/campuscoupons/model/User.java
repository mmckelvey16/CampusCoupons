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
    private int phoneNumber;
    private enum school {
        GEORGIASTATE;
    }
}
