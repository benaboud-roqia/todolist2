package com.todolist.authentification;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "tasks")
public class TaskEntity {
    @PrimaryKey(autoGenerate = true)
    public int id;
    public String text;
    public boolean done;

    public TaskEntity(String text, boolean done) {
        this.text = text;
        this.done = done;
    }
} 