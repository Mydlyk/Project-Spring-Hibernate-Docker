package com.example.frontend.backend;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.relational.core.mapping.MappedCollection;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    private int id;
    private String login;
    private String password;
    private String email;

    @MappedCollection(keyColumn = "user_id", idColumn = "user_id")
    private List<Task> tasks;
}
