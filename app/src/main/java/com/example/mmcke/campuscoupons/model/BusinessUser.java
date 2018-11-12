package com.example.mmcke.campuscoupons.model;

import java.util.ArrayList;

/**
 *
 * User class representing a business
 * Created by mmcke on 11/12/2018.
 */

public class BusinessUser {
    private String name;
    private School school;
    private ArrayList<Coupon> coupons = new ArrayList<Coupon>();

    public BusinessUser(String _name, School _school) {
        name = _name;
        school = _school;
    }

    public void addCoupon(Coupon coupon) {
        coupons.add(coupon);
    }

    public ArrayList<Coupon> getCoupons() {
        return coupons;
    }
}
