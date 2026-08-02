package jp.co.example.sharehouse_event_manager.entity;

import java.time.LocalDateTime;

public class Event {
    private Integer eventId;
    private String title;
    private String category;
    private LocalDateTime eventDate;
    private String location;
    private Integer capacity;
    private String description;
    private Integer userId;
    private Integer participantCount;


    public Event() {
    }


    public Event(Integer eventId, String title, String category, LocalDateTime eventDate, String location,
            Integer capacity, String description, Integer userId) {
        this.eventId = eventId;
        this.title = title;
        this.category = category;
        this.eventDate = eventDate;
        this.location = location;
        this.capacity = capacity;
        this.description = description;
        this.userId = userId;
    }


    public Integer getEventId() {
        return eventId;
    }


    public void setEventId(Integer eventId) {
        this.eventId = eventId;
    }


    public String getTitle() {
        return title;
    }


    public void setTitle(String title) {
        this.title = title;
    }


    public String getCategory() {
        return category;
    }


    public void setCategory(String category) {
        this.category = category;
    }


    public LocalDateTime getEventDate() {
        return eventDate;
    }


    public void setEventDate(LocalDateTime eventDate) {
        this.eventDate = eventDate;
    }


    public String getLocation() {
        return location;
    }


    public void setLocation(String location) {
        this.location = location;
    }


    public Integer getCapacity() {
        return capacity;
    }


    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }


    public String getDescription() {
        return description;
    }


    public void setDescription(String description) {
        this.description = description;
    }


    public Integer getUserId() {
        return userId;
    }


    public void setUserId(Integer userId) {
        this.userId = userId;
    }


    public Integer getParticipantCount() {
        return participantCount;
    }


    public void setParticipantCount(Integer participantCount) {
        this.participantCount = participantCount;
    }


    @Override
    public String toString() {
        return "Events [eventId=" + eventId + ", title=" + title + ", category=" + category + ", eventDate=" + eventDate
                + ", location=" + location + ", capacity=" + capacity + ", description=" + description + ", userId="
                + userId + ", participantCount=" + participantCount + "]";
    }

    

    


    
}
