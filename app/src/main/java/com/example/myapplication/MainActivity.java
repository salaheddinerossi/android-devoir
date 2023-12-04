package com.example.myapplication;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private EditText editTextLibelle;
    private EditText editTextPU;
    private Button buttonSubmit;
    private RecyclerView recyclerViewArticles;
    private ArticleAdapter articleAdapter;
    private ArticleDbHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextLibelle = findViewById(R.id.editTextLibelle);
        editTextPU = findViewById(R.id.editTextPU);
        buttonSubmit = findViewById(R.id.buttonSubmit);
        recyclerViewArticles = findViewById(R.id.recyclerViewArticles);

        // Initialize your database helper
        dbHelper = new ArticleDbHelper(this);

        // Set up the RecyclerView
        recyclerViewArticles.setLayoutManager(new LinearLayoutManager(this));
        articleAdapter = new ArticleAdapter(new ArrayList<Article>());
        recyclerViewArticles.setAdapter(articleAdapter);

        // Load existing articles
        loadArticles();

        // Set up the button click listener
        buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Get the values from the input fields
                String libelle = editTextLibelle.getText().toString();
                String puString = editTextPU.getText().toString();

                // Input validation
                if(libelle.isEmpty() || puString.isEmpty()){
                    Toast.makeText(MainActivity.this, "Please fill all the fields", Toast.LENGTH_SHORT).show();
                    return;
                }

                int pu = Integer.parseInt(puString);

                // Insert the new article into the database
                dbHelper.insertArticle(libelle, pu);

                // Clear input fields
                editTextLibelle.setText("");
                editTextPU.setText("");

                // Refresh the list of articles
                loadArticles();
            }
        });
    }

    private void loadArticles() {
        // Retrieve the list of articles from the database
        ArrayList<Article> articles = (ArrayList<Article>) dbHelper.getAllArticles();
        // Update the adapter with the new list
        articleAdapter.updateArticles(articles);
    }

    @Override
    protected void onDestroy() {
        dbHelper.close();
        super.onDestroy();
    }
}
