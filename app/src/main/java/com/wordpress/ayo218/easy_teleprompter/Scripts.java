package com.wordpress.ayo218.easy_teleprompter;

public class Scripts {
    private String title;
    private String sample_content;

    public Scripts() {
    }

    public Scripts(String title) {
        this.title = title;
    }

    public Scripts(String title, String sample_content) {
        this.title = title;
        this.sample_content = sample_content;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return sample_content;
    }

    public void setContent(String sample_content) {
        this.sample_content = sample_content;
    }
}
