package com.example.mmcke.campuscoupons.model;

import java.util.ArrayList;

/**
 * Created by mmcke on 11/8/2018.
 *
 * An enum class to represent all schools and their respective associated coupons
 */

public enum School {
        GEORGIASTATE("Georgia State"),
        GEORGIATECH("Georgia Tech");


        private final String title;
        ArrayList<Coupon> coupons = new ArrayList<Coupon>();
        private School(final String _title) {
            title = _title;
        }

        public ArrayList<Coupon> getCoupons() { return new ArrayList<Coupon>(coupons);}

        public void addCoupon(Coupon coupon) {
            coupons.add(coupon);
        }

        public String getTitle() { return title;}
}
