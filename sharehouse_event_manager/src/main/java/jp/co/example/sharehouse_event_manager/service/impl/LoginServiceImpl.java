package jp.co.example.sharehouse_event_manager.service.impl;
import org.springframework.stereotype.Service;

import jp.co.example.sharehouse_event_manager.dao.LoginDao;
import jp.co.example.sharehouse_event_manager.entity.User;
import jp.co.example.sharehouse_event_manager.service.LoginService;


@Service
public class LoginServiceImpl implements LoginService {
    private LoginDao loginDao;

    public LoginServiceImpl(LoginDao loginDao) {
        this.loginDao = loginDao;
    }

    public User findByUserNameAndPass(String userName, String password){
        return loginDao.findByUserNameAndPass(userName,password);
    }

    
}
