package com.example.gestion_rhbackend.dtos;

public class ChangePasswordRequest {

    private String oldPassword;
    private String newPassword;

    // Getters et Setters

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getOldPassword() {
        return oldPassword;
    }


    public String getNewPassword() {
        return newPassword;
    }


}
