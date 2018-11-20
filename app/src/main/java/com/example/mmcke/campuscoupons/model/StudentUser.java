package com.example.mmcke.campuscoupons.model;

import com.example.mmcke.campuscoupons.model.School;

/**
 *
 * Class for a student user type
 * Created by mmcke on 11/5/2018.
 */

public class StudentUser extends User {
    private boolean onCampus;
    private String studIDNum;
    private String address;
    private String country;
    private String state;
    private String city;
    private String zip;
    private School school;

    public StudentUser(String _firstName, String _lastName, String _email, String _password, String _phone,
                       String _school, String _userType, boolean _onCampus, String _studIDNum, String _address, String _country,
                       String _state, String _city, String _zip) {
        super(_firstName, _lastName, _email, _password, _phone, _school, _userType);
        onCampus = _onCampus;
        studIDNum = _studIDNum;
        address = _address;
        country = _country;
        state = _state;
        city = _city;
        zip = _zip;
    }

    public StudentUser() {
        this("Generic", "User", "Genericemail", "password", "1234567890", School.GEORGIASTATE.getTitle(), "student", true, "123456789", "123 Main Street", "USA", "State", "Anytown", "00000");
    }

    public void setOnCampus(boolean _onCampus) {
        onCampus = _onCampus;
    }

    public boolean getOnCampus() {return onCampus;}

    public void setStudIDNum(String _studIDNum) {
        studIDNum = _studIDNum;
    }

    public String getStudIDNum() { return studIDNum;}

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
