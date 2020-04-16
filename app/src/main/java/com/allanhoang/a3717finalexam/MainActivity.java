package com.allanhoang.a3717finalexam;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {
    public final String TAG = "MainActivity";
    private RecyclerView movieRecyclerView;
    private RecyclerView.Adapter movieAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        movieRecyclerView = findViewById(R.id.movie_recycler_view);
        layoutManager = new LinearLayoutManager(this);
        movieRecyclerView.setLayoutManager(layoutManager);
        db = FirebaseFirestore.getInstance();
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateMoviesFromFirebase();
    }

    private void updateMoviesFromFirebase() {
        db.collection("movies")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            QuerySnapshot querySnapshot = task.getResult();
                            if (querySnapshot != null && !querySnapshot.isEmpty()) {
                                setMovieRecyclerWithData(querySnapshot);
                            }
                        } else {
                            Log.w(TAG, "Error getting documents.", task.getException());
                        }
                    }
                });
    }

    private void setMovieRecyclerWithData(QuerySnapshot querySnapshot) {
        ArrayList<HashMap<String, String>> moviesList = new ArrayList<>();
        for (QueryDocumentSnapshot doc : querySnapshot) {
            HashMap<String, String> movie = new HashMap<>();
            movie.put("title", doc.getString("title"));
            movie.put("director", doc.getString("director"));
            movie.put("genre", doc.getString("genre"));
            movie.put("description", doc.getString("description"));
            movie.put("url", doc.getString("url"));
            moviesList.add(movie);
        }
        movieAdapter = new MovieItemAdapter(moviesList);
        movieRecyclerView.setAdapter(movieAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_create_movie:
                Intent intent = new Intent(this, AddMovieActivity.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
