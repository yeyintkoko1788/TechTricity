package com.yeyintkoko.techtricity.model;

public class ArticleDetailModel {
    private ArticleModel article;
    private String fbsharelink;

    public ArticleModel getArticle() {
        return article;
    }

    public void setArticle(ArticleModel article) {
        this.article = article;
    }

    public String getFbsharelink() {
        return fbsharelink;
    }

    public void setFbsharelink(String fbsharelink) {
        this.fbsharelink = fbsharelink;
    }
}
