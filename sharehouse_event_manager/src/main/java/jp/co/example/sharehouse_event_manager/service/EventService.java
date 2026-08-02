package jp.co.example.sharehouse_event_manager.service;

import java.util.List;

import jp.co.example.sharehouse_event_manager.entity.Event;
import jp.co.example.sharehouse_event_manager.entity.Participants;
import jp.co.example.sharehouse_event_manager.entity.User;

public interface EventService {
    public List<Event> findEventsByUserId(Integer userId);


    public List<Event> findAll();

    public void insert(Event event);

    public void delete(Integer eventId);

    public void updateEvent(Event event);

    public void insertParticipants(Participants participant);

    public void deleteParticipant(Integer eventId, Integer userId);

    public List<Participants> findAllParticipants();

    public void createNewUser(User user);

}
