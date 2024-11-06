// src/main/java/com/example/projecte/PdfItem.java

package com.example.projecte;

public class PdfItem {
    private String title;
    private String url;
    private long timestamp;
    private String resourceType;
    private String groupName;

    public PdfItem() {}

    public PdfItem(String title, String url, long timestamp, String resourceType, String groupName) {
        this.title = title;
        this.url = url;
        this.timestamp = timestamp;
        this.resourceType = resourceType;
        this.groupName = groupName;
    }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getUrl() { return url; }
    public void setUrl(String url) { this.url = url; }

    public long getTimestamp() { return timestamp; }
    public void setTimestamp(long timestamp) { this.timestamp = timestamp; }

    public String getResourceType() { return resourceType; }
    public void setResourceType(String resourceType) { this.resourceType = resourceType; }

    public String getGroupName() { return groupName; }
    public void setGroupName(String groupName) { this.groupName = groupName; }
}
