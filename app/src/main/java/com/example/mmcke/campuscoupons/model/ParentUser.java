package com.example.mmcke.campuscoupons.model;

/**
 * Created by mmcke on 11/5/2018.
 */

public class ParentUser extends User {
    private String studName;
    private String studEmail;
    private String studPhone;
    private String studIDNum;

    public ParentUser(String _firstName, String _lastName, String _email, String _password, String _phoneNumber, String _studName,
                      String _studEmail, String _studPhone, String _studIDNum) {
        super(_firstName, _lastName, _email, _password, _phoneNumber);
        studName = _studName;
        studEmail = _studEmail;
        studPhone = _studPhone;
        studIDNum = _studIDNum;
    }

    public ParentUser() {
        this("Generic", "User", "genericEmail", "password", "1234567890", "Generic Student", "GenericEmail",
                "1234567890", "123456789");
    }

    public void setStudName(String _studName) {
        studName = _studName;
    }

    public void setStudEmail(String _studEmail) {
        studEmail = _studEmail;
    }

    public void setStudPhone(String _studPhone) {
        studPhone = _studPhone;
    }

    public void setStudIDNum(String _studIDNum) {
        studIDNum = _studIDNum;
    }
}
