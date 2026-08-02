package jp.co.example.sharehouse_event_manager.service;

import jp.co.example.sharehouse_event_manager.entity.User;

public interface LoginService {
    public User findByUserNameAndPass(String userName, String password);
}
