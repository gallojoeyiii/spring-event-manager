package jp.co.example.sharehouse_event_manager.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import jakarta.servlet.http.HttpSession;
import jp.co.example.sharehouse_event_manager.entity.User;
import jp.co.example.sharehouse_event_manager.form.LoginForm;
import jp.co.example.sharehouse_event_manager.form.UserForm;

@Controller
public class IndexController {

    @GetMapping({ "/", "/index" })
    public String index(Model model) {
        return "index";
    }


}