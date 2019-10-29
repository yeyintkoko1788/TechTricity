package com.yeyintkoko.techtricity.model;

import com.yeyintkoko.techtricity.common.Pageable;

import java.io.Serializable;

public class ArticleListModel implements Pageable, Serializable {
    private ArticleModel article;
    private UserModel user;

    public ArticleModel getArticle() {
        return article;
    }

    public void setArticle(ArticleModel article) {
        this.article = article;
    }

    public UserModel getUser() {
        return user;
    }

    public void setUser(UserModel user) {
        this.user = user;
    }
}
