package com.example.midterm_james_jones;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import java.util.ArrayList;

public class HistoryActivity extends AppCompatActivity {


    private ListView listViewGeneratedHistory;


    private ArrayList<String> generatedHistoryList;
    private ArrayAdapter<String> historyAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_history);


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.history_layout), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        listViewGeneratedHistory = findViewById(R.id.listViewHistory);


        Intent retrieveIntent = getIntent();
        generatedHistoryList = retrieveIntent.getStringArrayListExtra("historyList");

        if (generatedHistoryList == null) {
            generatedHistoryList = new ArrayList<>();
        }


        historyAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, generatedHistoryList);
        listViewGeneratedHistory.setAdapter(historyAdapter);


        listViewGeneratedHistory.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                showDeleteConfirmationDialog(position);
            }
        });
    }

    private void showDeleteConfirmationDialog(int indexPosition) {
        new AlertDialog.Builder(HistoryActivity.this)
                .setTitle("Remove Entry")
                .setMessage("Do you want to delete this number from your history?")
                .setPositiveButton("Yes", (dialog, which) -> {
                    generatedHistoryList.remove(indexPosition);
                    historyAdapter.notifyDataSetChanged();
                    Toast.makeText(HistoryActivity.this, "Entry removed", Toast.LENGTH_SHORT).show();
                })
                .setNegativeButton("No", null)
                .show();
    }
}
