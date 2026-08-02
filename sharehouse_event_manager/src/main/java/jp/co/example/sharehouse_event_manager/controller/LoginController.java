package jp.co.example.sharehouse_event_manager.controller;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import jakarta.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.PostMapping;

import jp.co.example.sharehouse_event_manager.entity.Event;
import jp.co.example.sharehouse_event_manager.entity.User;
import jp.co.example.sharehouse_event_manager.form.LoginForm;
import jp.co.example.sharehouse_event_manager.service.EventService;
import jp.co.example.sharehouse_event_manager.service.LoginService;

@Controller
public class LoginController {

    private final LoginService loginService;

    private final EventService eventService;

    public LoginController(LoginService loginService, EventService eventService) {
        this.loginService = loginService;
        this.eventService = eventService;
    }

    @GetMapping("/login")
    public String showLogin(@ModelAttribute("loginForm") LoginForm form, Model model) {
        return "login";
    }

    @PostMapping(value = "/myPage")
    public String login(@Validated @ModelAttribute("loginForm") LoginForm form, BindingResult bindingResult,
            Model model, HttpSession session) {
        if (bindingResult.hasErrors()) {
            return "login";
        }

        User user = loginService.findByUserNameAndPass(form.getUserName(), form.getPassword());

        if (user == null) {
            // ログイン失敗
            model.addAttribute("errMsg", "UsernameまたはPasswordが間違っています");
            return "login";
        } else {

            session.setAttribute("user", user);

            List<Event> userEvents = eventService.findEventsByUserId(user.getUserId());

            session.setAttribute("userEvents", userEvents);

            session.setAttribute("eventSize", userEvents.size());

            if (!userEvents.isEmpty()) {
                Event nextEvent = userEvents.get(0);

                for (Event event : userEvents) {
                    if (event.getEventDate().isBefore(nextEvent.getEventDate())) {
                        nextEvent = event;
                    }
                }

                session.setAttribute("nextEvent", nextEvent);
            } else{
                session.setAttribute("nextEvent", null); 
            }

            return "myPage";
        }
    }

    @PostMapping(value = "/logout")
    public String logout(Model model, HttpSession session) {
        session.invalidate();
        return "index";

    }

}
