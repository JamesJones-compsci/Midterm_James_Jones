package com.example.midterm_james_jones;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {


    private EditText numberInputField;
    private Button generateButton, historyButton;
    private ListView multiplicationListView;


    private ArrayList<String> multiplicationResultsList = new ArrayList<>();
    private ArrayAdapter<String> listAdapter;


    public static ArrayList<Integer> generatedNumbersHistory = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        numberInputField = findViewById(R.id.input_number_field);
        generateButton = findViewById(R.id.generate_button);
        multiplicationListView = findViewById(R.id.table_list_view);
        historyButton = findViewById(R.id.history_button);


        listAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, multiplicationResultsList);
        multiplicationListView.setAdapter(listAdapter);


        generateButton.setOnClickListener(v -> {
            String inputText = numberInputField.getText().toString().trim();

            if (inputText.isEmpty()) {
                Toast.makeText(MainActivity.this, "Please enter a number first!", Toast.LENGTH_SHORT).show();
                return;
            }

            try {
                int baseNumber = Integer.parseInt(inputText);
                generateMultiplicationTable(baseNumber);


                if (!generatedNumbersHistory.contains(baseNumber)) {
                    generatedNumbersHistory.add(baseNumber);
                }

            } catch (NumberFormatException e) {
                Toast.makeText(MainActivity.this, "Invalid number format!", Toast.LENGTH_SHORT).show();
            }
        });


        multiplicationListView.setOnItemClickListener((AdapterView<?> parent, View view, int position, long id) -> {
            String selectedRow = multiplicationResultsList.get(position);


            new AlertDialog.Builder(MainActivity.this)
                    .setTitle("Delete Row")
                    .setMessage("Are you sure you want to delete:\n" + selectedRow + "?")
                    .setPositiveButton("Yes", (dialog, which) -> {
                        multiplicationResultsList.remove(position);
                        listAdapter.notifyDataSetChanged();
                        Toast.makeText(MainActivity.this, "Deleted: " + selectedRow, Toast.LENGTH_SHORT).show();
                    })
                    .setNegativeButton("No", null)
                    .show();
        });


        historyButton.setOnClickListener(v -> {
            Intent moveToHistory = new Intent(MainActivity.this, HistoryActivity.class);
            startActivity(moveToHistory);
        });
    }

    private void generateMultiplicationTable(int baseNumber) {
        multiplicationResultsList.clear();

        for (int i = 1; i <= 10; i++) {
            String rowResult = baseNumber + " × " + i + " = " + (baseNumber * i);
            multiplicationResultsList.add(rowResult);
        }

        listAdapter.notifyDataSetChanged();
    }
}