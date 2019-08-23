package com.example.todolistjava;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Scanner;

public class MainActivity extends AppCompatActivity {

    private ArrayList<String> taskList = new ArrayList<>();
    private static final String TASK_LIST_FILE = "taskstodo.txt";
    private ArrayAdapter<String> myAdapter;
    private ListView taskListView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        taskListView = findViewById(R.id.tasksList);

        setupList();
        populateList();
        onDelete();
    }

    private void setupList() {
        try {
            Scanner reader = new Scanner(openFileInput(TASK_LIST_FILE));
            while(reader.hasNextLine()) {
                String taskRead = reader.nextLine();
                taskList.add(taskRead);
            }
        }
        catch (Exception e) {
            //doNothing
        }

    }

    private void populateList() {
        myAdapter = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1, taskList );
        taskListView.setAdapter(myAdapter);

    }

    private void updateList() {
        try {
            PrintStream output = new PrintStream(openFileOutput(TASK_LIST_FILE, MODE_PRIVATE));
            for (String task : taskList) {
                output.println(task);
            }
            output.close();
            myAdapter.notifyDataSetChanged();
        }
        catch (Exception e) {
            //doNothing
        }
    }

    private void onDelete() {
        taskListView.setOnItemLongClickListener( (list, view, position, id) -> {
            taskList.remove(position);
            updateList();
            Toast.makeText(this, "Successfully removed.", Toast.LENGTH_SHORT).show();
            return true;
        });
    }

    public void addToList(View view) {
        EditText getTask = findViewById(R.id.enterTask);
        String taskToAdd = getTask.getText().toString();
        if(!taskToAdd.isEmpty()) {
            taskList.add(taskToAdd);
            updateList();
            Toast.makeText(this, "Successfully added.", Toast.LENGTH_SHORT).show();
            getTask.setText("");
        }
    }
}
