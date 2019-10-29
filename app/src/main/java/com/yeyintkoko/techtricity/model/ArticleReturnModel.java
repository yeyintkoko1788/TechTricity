package com.yeyintkoko.techtricity.model;

import java.util.ArrayList;

public class ArticleReturnModel {
    private int Pagesize;
    private int TotalCount;
    private int TotalPages;
    private ArrayList<ArticleListModel> Results;

    public int getPagesize() {
        return Pagesize;
    }

    public void setPagesize(int pagesize) {
        Pagesize = pagesize;
    }

    public int getTotalCount() {
        return TotalCount;
    }

    public void setTotalCount(int totalCount) {
        TotalCount = totalCount;
    }

    public int getTotalPages() {
        return TotalPages;
    }

    public void setTotalPages(int totalPages) {
        TotalPages = totalPages;
    }

    public ArrayList<ArticleListModel> getResults() {
        return Results;
    }

    public void setResults(ArrayList<ArticleListModel> results) {
        Results = results;
    }
}
