package com.yeyintkoko.techtricity.model;

import com.yeyintkoko.techtricity.common.Pageable;

import java.io.Serializable;

public class AuthorListModel implements Pageable, Serializable {
    private AuthorModel userlist;
    private int articlecount;

    public AuthorModel getUserlist() {
        return userlist;
    }

    public void setUserlist(AuthorModel userlist) {
        this.userlist = userlist;
    }

    public int getArticlecount() {
        return articlecount;
    }

    public void setArticlecount(int articlecount) {
        this.articlecount = articlecount;
    }
}
