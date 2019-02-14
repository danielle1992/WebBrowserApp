package com.example.trewoodani.webbrowser;

public class Bookmark {

    private String bookMarkTitle;
    private String bookMarkUrl;

    public Bookmark(String bookMarkTitle, String bookMarkUrl){
        this.bookMarkTitle = bookMarkTitle;
        this.bookMarkUrl = bookMarkUrl;
    }

    public String getTitle() {
        return bookMarkTitle;
    }

    public String getUrl() {
        return bookMarkUrl;
    }
}
