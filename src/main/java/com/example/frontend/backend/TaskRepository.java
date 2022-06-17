package com.example.frontend.backend;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class TaskRepository {
    @Autowired
    JdbcTemplate jdbcTemplate;

    public List<Task> getAll(){
        List<Task> query = jdbcTemplate.query("SELECT * FROM task", BeanPropertyRowMapper.newInstance(Task.class));
        return query;
    }

    public Task getById(int id){
        return jdbcTemplate.queryForObject("SELECT * FROM task WHERE id=?", BeanPropertyRowMapper.newInstance(Task.class),id);
    }

    public int save(Task task){
         jdbcTemplate.update("INSERT INTO task(title,content,added,deadline,finished,status,priority,user_id) VALUES (?, ?, ?, ?, ?, ?, ?, ?)",
                task.getTitle(),task.getContent(),task.getAdded(), task.getDeadline(),task.getFinished(),task.getStatus(),task.getPriority(),task.getUser_id()
        );
        return 1;
    }

    public int update(Task task){
        return jdbcTemplate.update("UPDATE task SET title=?, content=?, added=?, deadline=?, finished=?, status=?, priority=? WHERE id=?",
                    task.getTitle(),task.getContent(),task.getAdded(),task.getDeadline(),task.getFinished(), task.getStatus(),task.getPriority(),task.getId());
    }
    public int delete(int id){
        return jdbcTemplate.update("DELETE FROM task WHERE id = ?",id);
    }

    public List<Task> getByUserId(int userId){
        return jdbcTemplate.query("SELECT * FROM task WHERE user_id = ?",BeanPropertyRowMapper.newInstance(Task.class),userId);
    }

}
