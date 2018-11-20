package com.example.mmcke.campuscoupons.model;

/**
 *
 * A class to represent a coupon for a school.
 * Created by mmcke on 11/12/2018.
 */

public class Coupon {
    private School school;
    private String business;
    private boolean isUsed;
    private String name;
    private String details;

    public Coupon(School _school, String _business, boolean _isUsed, String _name, String _details) {
        school = _school;
        business = _business;
        isUsed = _isUsed;
        name = _name;
        details = _details;
    }

    public void setUsed(boolean _used) { isUsed = _used;}

    public boolean getIsUsed() { return isUsed;}

    public void setSchool(School _school) { school = _school;}

    public School getSchool() { return school;}

    public void setBusiness(String _business) {business = _business;}

    public String getBusiness() { return business;}
}
