package com.techment.OtrsSystem.Controller;

public class TicketDto {
    private String title;

    private String description;

    private String status;

    private String category;

    private long userId;

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public TicketDto(String title, String description,  String category, long userId) {
        this.title = title;
        this.description = description;
        this.category = category;
        this.userId = userId;
    }

    public TicketDto(String title, String description, String category) {
        this.title = title;
        this.description = description;
        this.category = category;
    }

    protected TicketDto() {}

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
