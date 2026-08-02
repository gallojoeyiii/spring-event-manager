package jp.co.example.sharehouse_event_manager.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import jp.co.example.sharehouse_event_manager.dao.EventDao;
import jp.co.example.sharehouse_event_manager.entity.Event;
import jp.co.example.sharehouse_event_manager.entity.Participants;
import jp.co.example.sharehouse_event_manager.entity.User;
import jp.co.example.sharehouse_event_manager.service.EventService;

@Service
public class EventServiceimpl implements EventService{
    
    private EventDao eventDao;

    public EventServiceimpl(EventDao eventDao){
        this.eventDao = eventDao;
    }

    public List<Event> findEventsByUserId(Integer userId){
        return eventDao.findEventsByUserId(userId);
    }

    public List<Event> findAll(){
        return eventDao.findAll();
    }

    public void insert(Event event){
        eventDao.insert(event);
    }

    public void delete(Integer eventId){
        eventDao.delete(eventId);
    }

    public void updateEvent(Event event){
        eventDao.updateEvent(event);
    }

    public void insertParticipants(Participants participant){
        eventDao.insertParticipants(participant);
    }

    public void deleteParticipant(Integer eventId, Integer userId){
        eventDao.deleteParticipant(eventId,userId);
    }

    public List<Participants> findAllParticipants(){
        return eventDao.findAllParticipants();
    }

    public void createNewUser(User user){
        eventDao.createNewUser(user);
    }
}
