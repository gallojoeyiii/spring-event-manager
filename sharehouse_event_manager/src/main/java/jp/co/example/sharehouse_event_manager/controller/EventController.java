package jp.co.example.sharehouse_event_manager.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jp.co.example.sharehouse_event_manager.dao.impl.EventDaoImpl;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import jakarta.servlet.http.HttpSession;
import jp.co.example.sharehouse_event_manager.entity.Event;
import jp.co.example.sharehouse_event_manager.entity.Participants;
import jp.co.example.sharehouse_event_manager.entity.User;
import jp.co.example.sharehouse_event_manager.form.EventForm;
import jp.co.example.sharehouse_event_manager.form.UserForm;
import jp.co.example.sharehouse_event_manager.service.EventService;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class EventController {

    private final EventDaoImpl eventDaoImpl;
    private final EventService eventService;

    public EventController(EventService eventService, EventDaoImpl eventDaoImpl) {
        this.eventService = eventService;
        this.eventDaoImpl = eventDaoImpl;
    }

    @GetMapping("/eventDetails")
    public String showEventDetails(@RequestParam("id") Integer id, Model model, HttpSession session) {

        model.addAttribute("id", id);

        List<Event> eventList = (List<Event>) session.getAttribute("events");

        // Event selectedEvent = eventList.get(id - 1);

        Event selectedEvent = null;

        for (Event e : eventList) {
            if (e.getEventId().equals(id)) {
                selectedEvent = e;
            }
        }

        session.setAttribute("mySelectedEvent", selectedEvent);

        model.addAttribute("mySelectedEvent", selectedEvent);

        return "eventDetails";
    }

    @GetMapping("/eventList")
    public String showEventList(Model model, HttpSession session) {
        List<Event> eventList = eventService.findAll();
        List<Participants> participants = eventService.findAllParticipants();
        for (Event event : eventList) {
            event.setParticipantCount(0);
            for (Participants participant : participants) {
                if (event.getEventId().equals(participant.getEventId())) {

                    int currentCount = (event.getParticipantCount() != null) ? event.getParticipantCount() : 0;
                    event.setParticipantCount(currentCount + 1);
                }
            }

        }

        Event largestEvent = Collections.max(eventList, Comparator.comparingInt(Event::getParticipantCount));

        String mostPopular = largestEvent.getTitle();

        session.setAttribute("mostPopular", mostPopular);

        session.setAttribute("events", eventList);

        return "eventList";
    }

    @PostMapping("/process-action")
    public String showSelectedEventDetails(@RequestParam("selectedEventId") Integer selectedId,
            @RequestParam("action") String action, @ModelAttribute("eventForm") EventForm form, Model model,
            HttpSession session) {

        ArrayList<Event> eventList = (ArrayList<Event>) session.getAttribute("events");
        Event selectedEvent = null;
        User user = (User) session.getAttribute("user");

        for (Event e : eventList) {
            if (e.getEventId().equals(selectedId)) {
                selectedEvent = e;
            }
        }
        session.setAttribute("eventId", selectedEvent.getEventId());
        if (action.equals("view")) {
            session.setAttribute("selectedEvent", selectedEvent);
            model.addAttribute("selectedEvent", selectedEvent);

            return "eventDetails";
        } else if (action.equals("delete")) {

            if (user.getUserId().equals(selectedEvent.getUserId())) {
                eventService.delete(selectedId);
                return "redirect:/eventList";
            }

            model.addAttribute("notCreatorError", "イベントの制作したユーザーだけ削除できます");
            return "eventList";
        } else if (action.equals("update")) {

            if (!user.getUserId().equals(selectedEvent.getUserId())) {
                model.addAttribute("notCreatorError", "イベントの制作したユーザーだけ削除できます");
                return "eventList";
            }

            form.setTitle(selectedEvent.getTitle());
            form.setLocation(selectedEvent.getLocation());
            form.setEventDate(selectedEvent.getEventDate());
            form.setDescription(selectedEvent.getDescription());
            form.setCategory(selectedEvent.getCategory());
            form.setCapacity(selectedEvent.getCapacity());

            session.setAttribute("create", false);

            return "eventForm";
        } else {

            return "myPage";
        }

    }

    @PostMapping("/process-create")
    public String create(@ModelAttribute("eventForm") EventForm form, Model model,
            HttpSession session) {

        boolean create = true;

        session.setAttribute("create", create);

        return "eventForm";
    }

    @GetMapping("/eventForm")
    public String getEventForm(@Validated @ModelAttribute("eventForm") EventForm form, BindingResult bindingResult,
            Model model, HttpSession session) {
        return "eventForm";

    }

    @PostMapping("/employeeForm")
    public String showEventForm(@Validated @ModelAttribute("eventForm") EventForm form, BindingResult bindingResult,
            Model model, HttpSession session) {

        if (bindingResult.hasErrors()) {
            return "eventForm";
        }

        User user = (User) session.getAttribute("user");
        Integer userId = user.getUserId();

        ArrayList<Event> eventList = (ArrayList<Event>) session.getAttribute("events");

        boolean create = false;

        create = (boolean) session.getAttribute("create");

    

        if (create) {
            Event event = new Event(null, form.getTitle(), form.getCategory(), form.getEventDate(), form.getLocation(),
                    form.getCapacity(), form.getDescription(), userId);
            eventService.insert(event);
            return "redirect:/eventList";
        } else {

            Integer eventId = (Integer) session.getAttribute("eventId");

            Event event = new Event(eventId, form.getTitle(), form.getCategory(), form.getEventDate(),
                    form.getLocation(),
                    form.getCapacity(), form.getDescription(), userId);

            eventService.updateEvent(event);

            return "redirect:/eventList";
        }

    }

    @GetMapping("/process-action")
    public String handleProcessActionGet() {

        return "redirect:/eventList";
    }
    @GetMapping("/logout")
    public String handleProcessLogOutGet() {

        return "redirect:/login";
    }

    @GetMapping("/process-create")
    public String handleProcessCreateGet() {

        return "redirect:/eventList";
    }

    @PostMapping("/process-user-action")
    public String userJoinOrLeaveEvent(@RequestParam("userAction") String userAction, Model model,
            HttpSession session) {

        List<Event> eventList = (ArrayList<Event>) session.getAttribute("events");
        Event event = (Event) session.getAttribute("selectedEvent");
        User user = (User) session.getAttribute("user");

        if (user == null) {
            session.setAttribute("loginError", "セッションの有効期限が切れました。再度ログインしてください");
            return "redirect:/login"; 
        }
        List<Participants> participants = eventService.findAllParticipants();

        Participants participant = new Participants(null, event.getEventId(), user.getUserId());
        if (userAction.equals("join")) {

            for (Participants part : participants) {

                if (part.getEventId().equals(participant.getEventId()) && part.getUserId().equals(user.getUserId())) {
                    session.setAttribute("joinError", "すでにこのイベントに入っています");
                    return "redirect:/eventDetails?id=" + participant.getEventId();
                }

            }

            for (Event evnt : eventList) {
                if (evnt.equals(event)) {
                    if (evnt.getParticipantCount() + 1 > evnt.getCapacity()) {
                        session.setAttribute("joinError", "このイベントは満員です");
                        return "redirect:/eventDetails?id=" + participant.getEventId();
                    }
                }
            }

            eventService.insertParticipants(participant);

            return "redirect:/myPage";

        } else { // userAction == "leave"

            for (Participants part : participants) {

                if (part.getEventId().equals(participant.getEventId()) && part.getUserId().equals(user.getUserId())) {

                    eventService.deleteParticipant(event.getEventId(), user.getUserId());
                    return "redirect:/myPage";

                }

            }

            session.setAttribute("joinError", "このイベントに入っていません");
            return "redirect:/eventDetails?id=" + participant.getEventId();

        }
    }

    @GetMapping("/myPage")
    public String menu(HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");

        if (user == null) {
            return "redirect:/login";
        }

        List<Event> updatedEvents = eventService.findEventsByUserId(user.getUserId());

        Event nextEvent = null;

        if (updatedEvents != null && !updatedEvents.isEmpty()) {
            nextEvent = updatedEvents.get(0);

            for (Event event : updatedEvents) {
                if (event.getEventDate().isBefore(nextEvent.getEventDate())) {
                    nextEvent = event;
                }
            }
        }

        model.addAttribute("eventSize", updatedEvents.size());

        model.addAttribute("nextEvent", nextEvent);

        model.addAttribute("userEvents", updatedEvents);

        return "myPage";
    }

    @GetMapping({ "/newUser" })
    public String showNewUser(@ModelAttribute("userForm") UserForm form, Model model) {
        return "newUser";
    }

    @PostMapping("login")
    public String postMethodName(@Validated @ModelAttribute("userForm") UserForm form, BindingResult bindingResult,
            Model model, HttpSession session) {

        if (bindingResult.hasErrors()) {
            return "newUser";
        }

        User user = new User(null, form.getUserName(), form.getPassword(), form.getDisplayName());

        eventService.createNewUser(user);

        return "redirect:/login";
    }

    // @PostMapping(value = "/deleteAccount")
    // public String logout(Model model, HttpSession session) {

    // session.invalidate();
    // return "index";

    // }

}
