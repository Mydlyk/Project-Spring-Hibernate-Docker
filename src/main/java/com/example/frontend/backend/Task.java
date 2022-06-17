package com.example.frontend.backend;



import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Task {

    public int id;
    public String title;
    public String content;
    public LocalDateTime added;
    public LocalDateTime deadline;
    public LocalDateTime finished;
    public String status;
    public String priority;
    public int user_id;
    //public eStatus status;
    //public ePriority priority;

    @Override
    public String toString() {
        return "Task{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", added=" + added +
                ", deadline=" + deadline +
                ", finished=" + finished +
                ", status=" + status +
                ", priority=" + priority +
                '}';
    }
}


