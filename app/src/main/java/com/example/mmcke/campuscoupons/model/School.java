package com.example.mmcke.campuscoupons.model;

import java.util.ArrayList;

/**
 * Created by mmcke on 11/8/2018.
 *
 * An enum class to represent all schools and their respective associated coupons
 */

public enum School {
        GEORGIASTATE("Georgia State");

        private final String title;
        ArrayList<String> coupons = new ArrayList<String>();
        private School(final String _title) {
            title = _title;
        }

        public ArrayList<String> getCoupons() { return new ArrayList<String>(coupons);}

        public String getTitle() { return title;}
}
