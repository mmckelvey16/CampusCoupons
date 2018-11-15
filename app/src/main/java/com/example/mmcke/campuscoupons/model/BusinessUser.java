package com.example.mmcke.campuscoupons.model;

import java.util.ArrayList;

/**
 *
 * User class representing a business
 * Created by mmcke on 11/12/2018.
 */

public class BusinessUser extends User{
    private String busName;
    private ArrayList<Coupon> coupons = new ArrayList<Coupon>();

    public BusinessUser(String _fName, String _lName, String _busName, String _email, String _phone,
                        String _address, String _password, String _school) {
        super(_fName, _lName, _email, _password, _phone, _address);
        busName = _busName;
    }

    public void addCoupon(Coupon coupon) {
        coupons.add(coupon);
    }

    public ArrayList<Coupon> getCoupons() {
        return coupons;
    }

    public void setBusName(String _busName) {
        busName = _busName;
    }

    public String getBusName() { return busName;}
}
