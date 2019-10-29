package com.yeyintkoko.techtricity.model;

import com.yeyintkoko.techtricity.common.Pageable;

import java.io.Serializable;

public class UserModel implements Pageable, Serializable {
    private int ID;
    private String Name;
    private String Description;
    private String Address;
    private String Photo;
    private String PhotoUrl;
    private String Gender;
    private String DOB;
    private String Username;
    private String Password;
    private String Role;
    private boolean IsDeleted;
    private String Accesstime;
    private String Email;
    private String Phone;
    private String FacebookUserName;
    private String FacebookUserID;
    private String UserBlockquote;
    private int ArticleCount;

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getPhoto() {
        return Photo;
    }

    public void setPhoto(String photo) {
        Photo = photo;
    }

    public String getPhotoUrl() {
        return PhotoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        PhotoUrl = photoUrl;
    }

    public String getGender() {
        return Gender;
    }

    public void setGender(String gender) {
        Gender = gender;
    }

    public String getDOB() {
        return DOB;
    }

    public void setDOB(String DOB) {
        this.DOB = DOB;
    }

    public String getUsername() {
        return Username;
    }

    public void setUsername(String username) {
        Username = username;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getRole() {
        return Role;
    }

    public void setRole(String role) {
        Role = role;
    }

    public boolean isDeleted() {
        return IsDeleted;
    }

    public void setDeleted(boolean deleted) {
        IsDeleted = deleted;
    }

    public String getAccesstime() {
        return Accesstime;
    }

    public void setAccesstime(String accesstime) {
        Accesstime = accesstime;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

    public String getFacebookUserName() {
        return FacebookUserName;
    }

    public void setFacebookUserName(String facebookUserName) {
        FacebookUserName = facebookUserName;
    }

    public String getFacebookUserID() {
        return FacebookUserID;
    }

    public void setFacebookUserID(String facebookUserID) {
        FacebookUserID = facebookUserID;
    }

    public String getUserBlockquote() {
        return UserBlockquote;
    }

    public void setUserBlockquote(String userBlockquote) {
        UserBlockquote = userBlockquote;
    }

    public int getArticleCount() {
        return ArticleCount;
    }

    public void setArticleCount(int articleCount) {
        ArticleCount = articleCount;
    }
}
