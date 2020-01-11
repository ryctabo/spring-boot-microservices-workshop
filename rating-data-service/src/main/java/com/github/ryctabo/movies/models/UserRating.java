package com.github.ryctabo.movies.models;

import java.util.List;

/**
 * @author Gustavo Pacheco (ryctabo at gmail.com)
 */
public class UserRating {

    private List<Rating> userRating;

    public List<Rating> getUserRating() {
        return userRating;
    }

    public void setUserRating(List<Rating> userRating) {
        this.userRating = userRating;
    }
}
