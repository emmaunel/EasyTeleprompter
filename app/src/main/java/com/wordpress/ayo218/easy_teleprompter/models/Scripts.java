package com.wordpress.ayo218.easy_teleprompter.models;

public class Scripts {
    private String title;
    private String content;
    private String date_created;

    public Scripts() {
    }

    public Scripts(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public Scripts(String title, String content, String date_created) {
        this.title = title;
        this.content = content;
        this.date_created = date_created;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public String getDate_created() {
        return date_created;
    }
}
