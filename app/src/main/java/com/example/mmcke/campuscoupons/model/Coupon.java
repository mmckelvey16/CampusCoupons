package com.example.mmcke.campuscoupons.model;

/**
 *
 * A class to represent a coupon for a school.
 * Created by mmcke on 11/12/2018.
 */

public class Coupon {
    private School school;
    private String business;

    public Coupon(School _school, String _business) {
        school = _school;
        business = _business;
    }
}
