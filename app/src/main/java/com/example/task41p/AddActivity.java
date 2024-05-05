package com.example.task41p;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.drawable.DrawableCompat;

public class AddActivity extends AppCompatActivity {
    EditText addTaskTitle, addTaskDescription, addTaskDueDate;
    Button btnAddTask;
    Datadbase datadbase;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_add_task);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        addTaskTitle = findViewById(R.id.addTaskTitle);
        addTaskDescription = findViewById(R.id.addTaskDescription);
        addTaskDueDate = findViewById(R.id.addTaskDueDate);
        btnAddTask = findViewById(R.id.SaveTask);

        datadbase = new Datadbase(this);

        btnAddTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title =  addTaskTitle.getText().toString();
                String description =addTaskDescription.getText().toString();
                String dueDate = addTaskDueDate.getText().toString();

                if (!TextUtils.isEmpty(title) && !TextUtils.isEmpty(description) && !TextUtils.isEmpty(dueDate)) {
                    Task task = new Task(title + dueDate ,title, description, dueDate);
                    datadbase.addTask(task);
                    Toast.makeText(AddActivity.this, "Already added the task!", Toast.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(AddActivity.this, "Please input all items!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        Toolbar toolbar = findViewById(R.id.toolbar);
        getMenuInflater().inflate(R.menu.bottom_nav_menu, menu);
        Drawable drawable = toolbar.getOverflowIcon();
        if (drawable != null) {
            drawable = DrawableCompat.wrap(drawable);
            DrawableCompat.setTint(drawable.mutate(), getResources().getColor(android.R.color.white));
            toolbar.setOverflowIcon(drawable);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        int id = item.getItemId();
        if (id == R.id.action_add) {
            startActivity(new Intent(AddActivity.this, AddActivity.class));
            return true;
        } else if (id == R.id.action_home) {
            startActivity(new Intent(AddActivity.this, MainActivity.class));
            return true;
        }
        return false;
    }
}
