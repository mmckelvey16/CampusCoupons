package com.example.mmcke.campuscoupons.model;

/**
 * Created by mmcke on 11/4/2018.
 */

public final class Model {
    private static final Model _instance = new Model();

    public static Model getInstance() {
        return _instance;
    }

    private User currentUser;

    public void setCurrentUser(User user) {
        currentUser = user;
    }
    public User getCurrentUser() {
        return currentUser;
    }
}
