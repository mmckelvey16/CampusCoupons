package com.example.mmcke.campuscoupons.model;

/**
 * Created by mmcke on 11/5/2018.
 */

public class StudentUser extends User {
    private boolean onCampus;
    private String studIDNum;

    public StudentUser(String _firstName, String _lastName, String _email, String _password, String _phone,
                       boolean _onCampus, String _studIDNum) {
        super(_firstName, _lastName, _email, _password, _phone);
        onCampus = _onCampus;
        studIDNum = _studIDNum;
    }

    public StudentUser() {
        this("Generic", "User", "Genericemail", "password", "1234567890", true, "123456789");
    }
}
