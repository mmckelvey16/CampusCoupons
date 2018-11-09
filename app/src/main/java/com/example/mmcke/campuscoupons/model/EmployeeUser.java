package com.example.mmcke.campuscoupons.model;

import com.example.mmcke.campuscoupons.model.User;
import com.example.mmcke.campuscoupons.model.School;

/**
 *
 * Class for an employee user type
 * Created by mmcke on 11/5/2018.
 */

public class EmployeeUser extends User {
    private String employeeIDNum;
    private String address;
    private String country;
    private String state;
    private String city;
    private String zip;
    private School school;

    public EmployeeUser(String _firstName, String _lastName, String _email, String _password, String _phone,
                        String _school, String _employeeIDNum, String _address, String _country,
                        String _state, String _city, String _zip) {
        super(_firstName, _lastName, _email, _password, _phone, _school);
        employeeIDNum = _employeeIDNum;
        address = _address;
        country = _country;
        state = _state;
        city = _city;
        zip = _zip;
    }

    public EmployeeUser() {
        this("Generic", "User", "Genericemail", "password", "1234567890", School.GEORGIASTATE.getTitle(), "123456789", "123 Main Street", "USA", "State", "Anytown", "00000");
    }

    public void setEmployeeIDNum(String _employeeIDNum) { employeeIDNum = _employeeIDNum;}

    public String getEmployeeIDNum() { return employeeIDNum;}

    public void setAddress(String _address) { address = _address;}

    public String getAddress() { return address;}

    public void setCountry(String _country) { country = _country;}

    public String getCountry() { return country;}

    public void setState(String _state) {state = _state;}

    public String getState() { return state;}

    public void setCity(String _city) { city = _city;}

    public String getCity() { return city;}

    public void setZip(String _zip) { zip = _zip;}

    public String getZip() { return zip;}
}
