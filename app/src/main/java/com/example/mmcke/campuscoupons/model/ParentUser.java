package com.example.mmcke.campuscoupons.model;

import com.example.mmcke.campuscoupons.model.School;

/**
 *
 * Class for a parent user type
 * Created by mmcke on 11/5/2018.
 */

public class ParentUser extends User {
    private String studName;
    private String studEmail;
    private String studPhone;
    private String studIDNum;
    private String address;
    private String country;
    private String state;
    private String city;
    private String zip;
    private School school;
    private String cardName;
    private String cardNum;
    private String expirationDate;
    private int crn;

    public ParentUser(String _firstName, String _lastName, String _email, String _password, String _phoneNumber,
                      String _school, String _studName, String _studEmail, String _studPhone, String _studIDNum,
                      String _address, String _country, String _state, String _city, String _zip, String _cardName,
                      String _cardNum, String _expirationDate, int _crn) {
        super(_firstName, _lastName, _email, _password, _phoneNumber, _school);
        studName = _studName;
        studEmail = _studEmail;
        studPhone = _studPhone;
        studIDNum = _studIDNum;
        address = _address;
        country = _country;
        state = _state;
        city = _city;
        zip = _zip;
        cardName = _cardName;
        cardNum = _cardNum;
        expirationDate = _expirationDate;
        crn = _crn;
    }

    public ParentUser() {
        this("Generic", "User", "genericEmail", "password", "1234567890", School.GEORGIASTATE.getTitle(), "Generic Student", "GenericEmail",
                "1234567890", "123456789", "123 Main Street", "USA", "State", "Anytown", "00000", "Card Name",
                "1234567890123456", "01/00", 123);
    }

    public void setStudName(String _studName) {
        studName = _studName;
    }

    public String getStudName() { return studName;}

    public void setStudEmail(String _studEmail) {
        studEmail = _studEmail;
    }

    public String getStudEmail() { return studEmail;}

    public void setStudPhone(String _studPhone) {
        studPhone = _studPhone;
    }

    public String getStudPhone() { return studPhone;}

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

    public void setCardName(String _cardName) { cardName = _cardName;}

    public String getCardName() { return cardName;}

    public void setCardNum(String _cardNum) { cardNum = _cardNum;}

    public String getCardNum() { return cardNum;}

    public void setExpirationDate(String _expirationDate) { expirationDate = _expirationDate;}

    public String getExpirationDate() { return expirationDate;}

    public void setCrn(int _crn) { crn = _crn;}

    public int getCrn() { return crn;}
}
