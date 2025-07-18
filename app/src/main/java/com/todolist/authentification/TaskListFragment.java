package com.todolist.authentification;

import android.app.AlertDialog;
import android.os.Bundle;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import android.os.AsyncTask;
import java.util.List;

public class TaskListFragment extends Fragment {
    private ArrayList<Task> tasks = new ArrayList<>();
    private TaskAdapter adapter;
    private AppDatabase db;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_task_list, container, false);
        db = AppDatabase.getInstance(requireContext());
        RecyclerView recyclerView = view.findViewById(R.id.recyclerView);
        adapter = new TaskAdapter(tasks, new TaskAdapter.TaskListener() {
            @Override
            public void onEdit(int position) {
                showEditDialog(position);
            }
            @Override
            public void onDelete(int position) {
                Task task = tasks.get(position);
                deleteTaskFromDb(task);
            }
            @Override
            public void onToggleDone(int position) {
                Task task = tasks.get(position);
                task.setDone(!task.isDone());
                updateTaskInDb(task);
            }
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);
        view.findViewById(R.id.addTaskButton).setOnClickListener(v -> showAddDialog());
        loadTasksFromDb();
        return view;
    }

    private void loadTasksFromDb() {
        AsyncTask.execute(() -> {
            List<TaskEntity> entities = db.taskDao().getAll();
            tasks.clear();
            for (TaskEntity entity : entities) {
                tasks.add(new Task(entity.id, entity.text, entity.done));
            }
            requireActivity().runOnUiThread(() -> adapter.notifyDataSetChanged());
        });
    }

    private void addTaskToDb(String text) {
        AsyncTask.execute(() -> {
            long id = db.taskDao().insert(new TaskEntity(text, false));
            loadTasksFromDb();
        });
    }

    private void updateTaskInDb(Task task) {
        AsyncTask.execute(() -> {
            db.taskDao().update(new TaskEntityWithId(task));
            loadTasksFromDb();
        });
    }

    private void deleteTaskFromDb(Task task) {
        AsyncTask.execute(() -> {
            db.taskDao().delete(new TaskEntityWithId(task));
            loadTasksFromDb();
        });
    }

    private void showAddDialog() {
        EditText input = new EditText(getContext());
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        new AlertDialog.Builder(getContext())
                .setTitle("Nouvelle tâche")
                .setView(input)
                .setPositiveButton("Ajouter", (dialog, which) -> {
                    String text = input.getText().toString().trim();
                    if (!text.isEmpty()) {
                        addTaskToDb(text);
                    } else {
                        Toast.makeText(getContext(), "Le texte ne peut pas être vide", Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton("Annuler", null)
                .show();
    }

    private void showEditDialog(int position) {
        Task task = tasks.get(position);
        EditText input = new EditText(getContext());
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        input.setText(task.getText());
        new AlertDialog.Builder(getContext())
                .setTitle("Modifier la tâche")
                .setView(input)
                .setPositiveButton("Enregistrer", (dialog, which) -> {
                    String text = input.getText().toString().trim();
                    if (!text.isEmpty()) {
                        task.setText(text);
                        updateTaskInDb(task);
                    } else {
                        Toast.makeText(getContext(), "Le texte ne peut pas être vide", Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton("Annuler", null)
                .show();
    }

    // Classe modèle pour une tâche
    public static class Task {
        private int id;
        private String text;
        private boolean done;
        public Task(int id, String text, boolean done) { this.id = id; this.text = text; this.done = done; }
        public Task(String text) { this(0, text, false); }
        public int getId() { return id; }
        public String getText() { return text; }
        public void setText(String text) { this.text = text; }
        public boolean isDone() { return done; }
        public void setDone(boolean done) { this.done = done; }
    }

    // Classe utilitaire pour Room (avec id)
    public static class TaskEntityWithId extends TaskEntity {
        public TaskEntityWithId(Task task) {
            super(task.getText(), task.isDone());
            this.id = task.getId();
        }
    }
} 