package jp.co.example.sharehouse_event_manager.entity;

public class Participants {
    private Integer participantId;
    private Integer eventId;
    private Integer userId;

    public Participants() {
    }

    public Participants(Integer participantId, Integer eventId, Integer userId) {
        this.participantId = participantId;
        this.eventId = eventId;
        this.userId = userId;
    }

    public Integer getParticipantId() {
        return participantId;
    }

    public void setParticipantId(Integer participantId) {
        this.participantId = participantId;
    }

    public Integer getEventId() {
        return eventId;
    }

    public void setEventId(Integer eventId) {
        this.eventId = eventId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "Participants [participantId=" + participantId + ", eventId=" + eventId + ", userId=" + userId + "]";
    }

    

    

    
}
