package com.example.android12features.model;

public class UserModel {

    public int userId;
    public String username, designation, userImage, dateOfJoining;

    // constructors
    public UserModel(int userId, String username, String designation, String userImage, String dateOfJoining) {
        this.userId = userId;
        this.username = username;
        this.designation = designation;
        this.userImage = userImage;
        this.dateOfJoining = dateOfJoining;
    }

    // getters and setters
    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public String getUserImage() {
        return userImage;
    }

    public void setUserImage(String userImage) {
        this.userImage = userImage;
    }

    public String getDateOfJoining() {
        return dateOfJoining;
    }

    public void setDateOfJoining(String dateOfJoining) {
        this.dateOfJoining = dateOfJoining;
    }
}
