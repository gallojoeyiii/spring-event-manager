package jp.co.example.sharehouse_event_manager.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import jp.co.example.sharehouse_event_manager.dao.EventDao;
import jp.co.example.sharehouse_event_manager.entity.Event;
import jp.co.example.sharehouse_event_manager.entity.Participants;
import jp.co.example.sharehouse_event_manager.entity.User;

@Repository
public class EventDaoImpl implements EventDao {

    private static final String SELECT_ALL = "SELECT * FROM events ORDER BY event_date ";
    private static final String SELECT_ALL_PARTICIPANTS = "SELECT * FROM participants";
    private static final String INSERT = "INSERT INTO events (title, category, event_date, location, capacity, description, user_id) VALUES (:title, :category, :eventDate, :location, :capacity, :description,:userId )";
    private static final String DELETE = "DELETE FROM events where event_id = :eventId";
    private static final String DELETE_EVENT_PARTICIPANTS = "DELETE FROM participants WHERE event_Id = :eventId"; 
    private static final String UPDATE = "UPDATE events SET title = :title, category = :category, event_date = :eventDate, location = :location, capacity = :capacity, description = :description WHERE event_id = :eventId";
    private static final String INSERT_NEW_PARTICIPANT = "INSERT INTO participants (event_id,user_id) VALUES (:eventId,:userId)";
    private static final String DELETE_PARTICIPANT = "DELETE FROM participants WHERE event_id = :eventId AND user_id = :userId";
    private static final String INSERT_NEW_USER = "INSERT INTO users (user_name,password,display_name) VALUES(:userName,:password,:displayName)";
    private static final String DELETE_USER = "DELETE FROM users where user_id = :user_id";
    private static final String DELETE_PARTICIPANT_FOR_USER = "DELETE FROM participants WHERE user_id = :userId";


    private final NamedParameterJdbcTemplate jdbcTemplate;

    public EventDaoImpl(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }



    public List<Event> findEventsByUserId(Integer userId) {
        String sql = SELECT_ALL;
        String sql2 = SELECT_ALL_PARTICIPANTS;

        List<Event> events = jdbcTemplate.query(sql, new BeanPropertyRowMapper<Event>(Event.class));
        List <Participants> participants = jdbcTemplate.query(sql2, new BeanPropertyRowMapper<Participants>(Participants.class));
        
        List<Event> userEvents = new ArrayList<Event>(); 
       
        for(Participants participant : participants){
            if(participant.getUserId().equals(userId)){
                for(Event event : events){
                    if(event.getEventId().equals(participant.getEventId())){
                        userEvents.add(event);
                        break;
                    }
                }
                }
        }

        return userEvents;
    }

    public List<Event> findAll(){
        String sql = SELECT_ALL;

        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<Event>(Event.class));
    }

    public List<Participants> findAllParticipants(){
        String sql = SELECT_ALL_PARTICIPANTS;
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<Participants>(Participants.class));
    }


    public void insert(Event event) {
        MapSqlParameterSource param = new MapSqlParameterSource();
        param.addValue("title", event.getTitle());
        param.addValue("category", event.getCategory());
        param.addValue("eventDate", event.getEventDate());
        param.addValue("location", event.getLocation());
        param.addValue("capacity", event.getCapacity());
        param.addValue("description", event.getDescription());
        param.addValue("userId", event.getUserId());

        jdbcTemplate.update(INSERT, param);
    }

    public void createNewUser(User user){
        MapSqlParameterSource param = new MapSqlParameterSource();
        param.addValue("userName", user.getUserName());
        param.addValue("password", user.getPassword());
        param.addValue("displayName", user.getDisplayName());

        jdbcTemplate.update(INSERT_NEW_USER, param);
    }

    public void delete(Integer eventId) {
        MapSqlParameterSource param = new MapSqlParameterSource();
        param.addValue("eventId", eventId);
        
        jdbcTemplate.update(DELETE_EVENT_PARTICIPANTS, param);
        jdbcTemplate.update(DELETE, param);
    }

    // public void deleteUser(Integer userId){
    //     MapSqlParameterSource param = new MapSqlParameterSource();
    //     param.addValue("userId", userId);

    //     jdbcTemplate.update(DELETE_PARTICIPANT_FOR_USER,param);
    //     jdbcTemplate.update(DELETE_USER, param);
    // }

    public void deleteParticipant(Integer eventId, Integer userId){
        MapSqlParameterSource param = new MapSqlParameterSource();
        param.addValue("eventId", eventId);
        param.addValue("userId", userId);

        jdbcTemplate.update((DELETE_PARTICIPANT), param);
    }


    
    
    public void updateEvent(Event event) {
        MapSqlParameterSource param = new MapSqlParameterSource();
        param.addValue("eventId", event.getEventId());
        param.addValue("title", event.getTitle());
        param.addValue("category", event.getCategory());
        param.addValue("eventDate", event.getEventDate());
        param.addValue("location", event.getLocation());
        param.addValue("capacity", event.getCapacity());
        param.addValue("description", event.getDescription());
        
        jdbcTemplate.update(UPDATE, param);
    }

    public void insertParticipants(Participants participant){
        MapSqlParameterSource param = new MapSqlParameterSource();
        param.addValue("eventId", participant.getEventId());
        param.addValue("userId", participant.getUserId());

        jdbcTemplate.update(INSERT_NEW_PARTICIPANT, param);
    }
}
