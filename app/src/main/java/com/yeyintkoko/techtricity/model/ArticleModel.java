package com.yeyintkoko.techtricity.model;

import com.yeyintkoko.techtricity.common.Pageable;

import java.io.Serializable;

public class ArticleModel implements Pageable, Serializable {
    private int ID;
    private String Title;
    private String Description;
    private String BodyHtml;
    private int CategoryID;
    private String CategoryName;
    private int TagsID;
    private String TagsName;
    private int UserID;
    private String UserName;
    private boolean IsActive;
    private boolean IsDeleted;
    private String Accesstime;
    private String CreatedTime;
    private String ArticlePhoto;
    private String ArticlePhotoUrl;
    private int ViewCount;
    private int TagsCount;
    private String Ad1Title;
    private String Ad1WebLink;
    private String Ad1Duration;
    private String Ad1FromDate;
    private String Ad1ToDate;
    private String Ad1Photo;
    private String Ad1PhotoUrl;
    private String Ad2Title;
    private String Ad2WebLink;
    private String Ad2Duration;
    private String Ad2FromDate;
    private String Ad2ToDate;
    private String Ad2Photo;
    private String Ad2PhotoUrl;
    private String Permalink;
    private String ReferenceData;
    private boolean IsHomeBanner;

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getBodyHtml() {
        return BodyHtml;
    }

    public void setBodyHtml(String bodyHtml) {
        BodyHtml = bodyHtml;
    }

    public int getCategoryID() {
        return CategoryID;
    }

    public void setCategoryID(int categoryID) {
        CategoryID = categoryID;
    }

    public String getCategoryName() {
        return CategoryName;
    }

    public void setCategoryName(String categoryName) {
        CategoryName = categoryName;
    }

    public int getTagsID() {
        return TagsID;
    }

    public void setTagsID(int tagsID) {
        TagsID = tagsID;
    }

    public String getTagsName() {
        return TagsName;
    }

    public void setTagsName(String tagsName) {
        TagsName = tagsName;
    }

    public int getUserID() {
        return UserID;
    }

    public void setUserID(int userID) {
        UserID = userID;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public boolean isActive() {
        return IsActive;
    }

    public void setActive(boolean active) {
        IsActive = active;
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

    public String getCreatedTime() {
        return CreatedTime;
    }

    public void setCreatedTime(String createdTime) {
        CreatedTime = createdTime;
    }

    public String getArticlePhoto() {
        return ArticlePhoto;
    }

    public void setArticlePhoto(String articlePhoto) {
        ArticlePhoto = articlePhoto;
    }

    public String getArticlePhotoUrl() {
        return ArticlePhotoUrl;
    }

    public void setArticlePhotoUrl(String articlePhotoUrl) {
        ArticlePhotoUrl = articlePhotoUrl;
    }

    public int getViewCount() {
        return ViewCount;
    }

    public void setViewCount(int viewCount) {
        ViewCount = viewCount;
    }

    public int getTagsCount() {
        return TagsCount;
    }

    public void setTagsCount(int tagsCount) {
        TagsCount = tagsCount;
    }

    public String getAd1Title() {
        return Ad1Title;
    }

    public void setAd1Title(String ad1Title) {
        Ad1Title = ad1Title;
    }

    public String getAd1WebLink() {
        return Ad1WebLink;
    }

    public void setAd1WebLink(String ad1WebLink) {
        Ad1WebLink = ad1WebLink;
    }

    public String getAd1Duration() {
        return Ad1Duration;
    }

    public void setAd1Duration(String ad1Duration) {
        Ad1Duration = ad1Duration;
    }

    public String getAd1FromDate() {
        return Ad1FromDate;
    }

    public void setAd1FromDate(String ad1FromDate) {
        Ad1FromDate = ad1FromDate;
    }

    public String getAd1ToDate() {
        return Ad1ToDate;
    }

    public void setAd1ToDate(String ad1ToDate) {
        Ad1ToDate = ad1ToDate;
    }

    public String getAd1Photo() {
        return Ad1Photo;
    }

    public void setAd1Photo(String ad1Photo) {
        Ad1Photo = ad1Photo;
    }

    public String getAd1PhotoUrl() {
        return Ad1PhotoUrl;
    }

    public void setAd1PhotoUrl(String ad1PhotoUrl) {
        Ad1PhotoUrl = ad1PhotoUrl;
    }

    public String getAd2Title() {
        return Ad2Title;
    }

    public void setAd2Title(String ad2Title) {
        Ad2Title = ad2Title;
    }

    public String getAd2WebLink() {
        return Ad2WebLink;
    }

    public void setAd2WebLink(String ad2WebLink) {
        Ad2WebLink = ad2WebLink;
    }

    public String getAd2Duration() {
        return Ad2Duration;
    }

    public void setAd2Duration(String ad2Duration) {
        Ad2Duration = ad2Duration;
    }

    public String getAd2FromDate() {
        return Ad2FromDate;
    }

    public void setAd2FromDate(String ad2FromDate) {
        Ad2FromDate = ad2FromDate;
    }

    public String getAd2ToDate() {
        return Ad2ToDate;
    }

    public void setAd2ToDate(String ad2ToDate) {
        Ad2ToDate = ad2ToDate;
    }

    public String getAd2Photo() {
        return Ad2Photo;
    }

    public void setAd2Photo(String ad2Photo) {
        Ad2Photo = ad2Photo;
    }

    public String getAd2PhotoUrl() {
        return Ad2PhotoUrl;
    }

    public void setAd2PhotoUrl(String ad2PhotoUrl) {
        Ad2PhotoUrl = ad2PhotoUrl;
    }

    public String getPermalink() {
        return Permalink;
    }

    public void setPermalink(String permalink) {
        Permalink = permalink;
    }

    public String getReferenceData() {
        return ReferenceData;
    }

    public void setReferenceData(String referenceData) {
        ReferenceData = referenceData;
    }

    public boolean isHomeBanner() {
        return IsHomeBanner;
    }

    public void setHomeBanner(boolean homeBanner) {
        IsHomeBanner = homeBanner;
    }
}
