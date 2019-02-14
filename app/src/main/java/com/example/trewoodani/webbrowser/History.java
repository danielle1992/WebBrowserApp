package com.example.trewoodani.webbrowser;

import java.util.Date;

public class History {
    private String currentDate;
    private String currentUrl;

    public History(String currentDate, String currentUrl){
        this.currentDate = currentDate;
        this.currentUrl = currentUrl;
    }

    public String getCurrentDate() {
        return currentDate;
    }

    public String getCurrentUrl() {
        return currentUrl;
    }
}
