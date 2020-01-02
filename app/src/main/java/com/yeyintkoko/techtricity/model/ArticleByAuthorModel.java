package com.yeyintkoko.techtricity.model;

import java.util.ArrayList;

public class ArticleByAuthorModel {
    private int Pagesize;
    private int TotalCount;
    private int TotalPages;
    private ArrayList<ArticleModel> Results;

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

    public ArrayList<ArticleModel> getResults() {
        return Results;
    }

    public void setResults(ArrayList<ArticleModel> results) {
        Results = results;
    }
}
