package com.todolist.authentification;

import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.TaskViewHolder> {
    public interface TaskListener {
        void onEdit(int position);
        void onDelete(int position);
        void onToggleDone(int position);
    }

    private ArrayList<TaskListFragment.Task> tasks;
    private TaskListener listener;

    public TaskAdapter(ArrayList<TaskListFragment.Task> tasks, TaskListener listener) {
        this.tasks = tasks;
        this.listener = listener;
    }

    @NonNull
    @Override
    public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_task, parent, false);
        return new TaskViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TaskViewHolder holder, int position) {
        TaskListFragment.Task task = tasks.get(position);
        holder.taskText.setText(task.getText());
        holder.taskText.setPaintFlags(task.isDone() ? holder.taskText.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG : holder.taskText.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
        holder.doneButton.setImageResource(task.isDone() ? android.R.drawable.checkbox_on_background : android.R.drawable.checkbox_off_background);
        holder.doneButton.setOnClickListener(v -> listener.onToggleDone(position));
        holder.editButton.setOnClickListener(v -> listener.onEdit(position));
        holder.deleteButton.setOnClickListener(v -> listener.onDelete(position));
    }

    @Override
    public int getItemCount() {
        return tasks.size();
    }

    static class TaskViewHolder extends RecyclerView.ViewHolder {
        TextView taskText;
        ImageButton doneButton, editButton, deleteButton;
        TaskViewHolder(@NonNull View itemView) {
            super(itemView);
            taskText = itemView.findViewById(R.id.taskText);
            doneButton = itemView.findViewById(R.id.doneButton);
            editButton = itemView.findViewById(R.id.editButton);
            deleteButton = itemView.findViewById(R.id.deleteButton);
        }
    }
} 