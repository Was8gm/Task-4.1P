package com.example.task41p;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.TaskViewHolder> {

    private List<Task> tasks;
    private Datadbase database;
    private Context context; // Added context for better flexibility

    public TaskAdapter(Context context, List<Task> tasks) {
        this.context = context;
        this.tasks = tasks;
        this.database = new Datadbase(context); // Assuming Datadbase is your database helper class
    }

    @NonNull
    @Override
    public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_task, parent, false);
        return new TaskViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TaskViewHolder holder, int position) {
        Task task = tasks.get(position);
        holder.taskTitle.setText(task.getTitle());
        holder.taskDetails.setText(task.getDescription());
        holder.taskDueDate.setText("Due date: " + task.getDueDate());
        holder.taskDetails.setVisibility(task.isExpanded() ? View.VISIBLE : View.GONE);

        holder.itemView.setOnClickListener(v -> {
            task.setExpanded(!task.isExpanded());
            notifyItemChanged(position);
        });
    }

    @Override
    public int getItemCount() {
        return tasks.size();
    }

    public class TaskViewHolder extends RecyclerView.ViewHolder {
        TextView taskTitle, taskDetails, taskDueDate;
        Button deleteButton, editButton;

        public TaskViewHolder(View itemView) {
            super(itemView);
            taskTitle = itemView.findViewById(R.id.task_title);
            taskDetails = itemView.findViewById(R.id.detailsTextView);
            taskDueDate = itemView.findViewById(R.id.task_due_date);
            deleteButton = itemView.findViewById(R.id.deleteButton);
            editButton = itemView.findViewById(R.id.editButton);

            setupDeleteButton();
            setupEditButton();
        }

        private void setupDeleteButton() {
            deleteButton.setOnClickListener(v -> {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    Task taskToDelete = tasks.get(position);
                    new AlertDialog.Builder(context)
                            .setTitle("Delete Task")
                            .setMessage("Are you sure you want to delete this task?")
                            .setPositiveButton("Delete", (dialog, which) -> {
                                tasks.remove(position); // Remove from the list first
                                database.deleteTask(taskToDelete); // Then remove from the database
                                notifyItemRemoved(position);
                                notifyItemRangeChanged(position, tasks.size());
                            })
                            .setNegativeButton("Cancel", null)
                            .show();
                }
            });
        }

        private void setupEditButton() {
            editButton.setOnClickListener(v -> {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    Task taskToEdit = tasks.get(position);
                    showEditDialog(taskToEdit, position);
                }
            });
        }

        private void showEditDialog(Task task, int position) {
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            View customView = LayoutInflater.from(context).inflate(R.layout.dialog_edit_task, null);
            builder.setView(customView);

            EditText editTitle = customView.findViewById(R.id.editTaskTitle);
            EditText editDescription = customView.findViewById(R.id.editTaskDescription);
            EditText editDueDate = customView.findViewById(R.id.editTaskDueDate);

            editTitle.setText(task.getTitle());
            editDescription.setText(task.getDescription());
            editDueDate.setText(task.getDueDate());

            builder.setPositiveButton("Save", (dialog, which) -> {
                updateTask(task, position, editTitle.getText().toString(), editDescription.getText().toString(), editDueDate.getText().toString());
            });
            builder.setNegativeButton("Cancel", null);

            AlertDialog dialog = builder.create();
            dialog.show();
        }

        private void updateTask(Task task, int position, String title, String description, String dueDate) {
            task.setTitle(title);
            task.setDescription(description);
            task.setDueDate(dueDate);
            database.updateTask(task);
            notifyItemChanged(position);
        }
    }
}
