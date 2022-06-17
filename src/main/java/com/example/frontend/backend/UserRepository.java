package com.example.frontend.backend;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserRepository {

    @Autowired
    JdbcTemplate jdbcTemplate;

    public List<User> getAll()
    {
        return jdbcTemplate.query("SELECT * FROM user", BeanPropertyRowMapper.newInstance(User.class));
    }

    public User getById(int id)
    {
        return jdbcTemplate.queryForObject("SELECT * FROM user WHERE id= ?",BeanPropertyRowMapper.newInstance(User.class),id);
    }

    public User getUserByLogin(String login){
        return jdbcTemplate.queryForObject("SELECT * FROM user WHERE login= ?",BeanPropertyRowMapper.newInstance(User.class),login);
    }

    public int save(User user)
    {
         jdbcTemplate.update("INSERT INTO user(login,email,password) VALUES (?, ?, ?)",
                        user.getLogin(),user.getEmail(),user.getPassword());

        return 1;
    }

    public int update(User user)
    {
        return jdbcTemplate.update("UPDATE user SET login= ?, email= ?, password= ? WHERE id= ?",
                user.getLogin(),user.getEmail(),user.getPassword());
    }

    public int delete(int id)
    {
        return jdbcTemplate.update("DELETE FROM user WHERE id= ?", id);
    }

}
