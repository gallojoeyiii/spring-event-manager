package jp.co.example.sharehouse_event_manager.dao.impl;

import java.util.List;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import jp.co.example.sharehouse_event_manager.dao.LoginDao;
import jp.co.example.sharehouse_event_manager.entity.User;

@Repository
public class LoginDaoImpl implements LoginDao {

    private static final String SELECT_USERNAME_AND_PASS = "SELECT * FROM users WHERE user_name = :userName AND password = :password";

    private final NamedParameterJdbcTemplate jdbcTemplate;
    public LoginDaoImpl(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public User findByUserNameAndPass(String userName, String password) {
        
        String sql = SELECT_USERNAME_AND_PASS;

        MapSqlParameterSource param = new MapSqlParameterSource();
        param.addValue("userName", userName);
        param.addValue("password", password);

        List<User> resultList = jdbcTemplate.query(sql, param, new BeanPropertyRowMapper<User>(User.class));

        return resultList.isEmpty() ? null : resultList.get(0);
    }
    
}
