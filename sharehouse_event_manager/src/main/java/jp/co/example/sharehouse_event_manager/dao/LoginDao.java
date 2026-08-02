package jp.co.example.sharehouse_event_manager.dao;

import jp.co.example.sharehouse_event_manager.entity.User;

public interface LoginDao {

    User findByUserNameAndPass(String userName, String password);
    
}
