package com.example.projecte;

public class PdfItem {
    private String title;
    private String url;
    private long timestamp;
    public PdfItem() {
    }

    public PdfItem(String title, String url, long timestamp) {
        this.title = title;
        this.url = url;
        this.timestamp = timestamp;
    }

    public String getTitle() {
        return title;
    }

    public String getUrl() {
        return url;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
}
