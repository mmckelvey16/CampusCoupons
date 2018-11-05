package com.example.mmcke.campuscoupons.controllers;

import com.example.mmcke.campuscoupons.model.User;

/**
 *
 * Class for an employee user type
 * Created by mmcke on 11/5/2018.
 */

public class EmployeeUser extends User {
    private String employeeIDNum;

    public EmployeeUser(String _firstName, String _lastName, String _email, String _password, String _phone,
                       String _employeeIDNum) {
        super(_firstName, _lastName, _email, _password, _phone);
        employeeIDNum = _employeeIDNum;
    }

    public EmployeeUser() {
        this("Generic", "User", "Genericemail", "password", "1234567890", "123456789");
    }
}
