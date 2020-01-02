package com.yeyintkoko.techtricity.model;

import java.util.ArrayList;

public class AuthorReturnModel {
    private int Pagesize;
    private int TotalCount;
    private int TotalPages;
    private ArrayList<AuthorListModel> Results;

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

    public ArrayList<AuthorListModel> getResults() {
        return Results;
    }

    public void setResults(ArrayList<AuthorListModel> results) {
        Results = results;
    }
}
