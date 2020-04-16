package com.allanhoang.a3717finalexam;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class AddMovieActivity extends AppCompatActivity {
    private final String TAG = "AddMovieActivity";
    private EditText editTextMovieTitle;
    private EditText editTextMovieDir;
    private EditText editTextMovieGenre;
    private EditText editTextMovieDes;
    private EditText editTextMovieURL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_movie);

        // setup action bar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        // get references to input
        editTextMovieTitle = findViewById(R.id.edit_text_movie_title);
        editTextMovieDir = findViewById(R.id.edit_text_movie_director);
        editTextMovieGenre = findViewById(R.id.edit_text_movie_genre);
        editTextMovieDes = findViewById(R.id.edit_text_movie_description);
        editTextMovieURL = findViewById(R.id.edit_text_movie_url);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_add_movie, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_add_movie_to_db:
                add_movie_to_firebase();
                finish();
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void add_movie_to_firebase() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        Map<String, Object> movie = new HashMap<>();


        movie.put("title", editTextMovieTitle.getText().toString());
        movie.put("director", editTextMovieDir.getText().toString());
        movie.put("genre", editTextMovieGenre.getText().toString());
        movie.put("description", editTextMovieDes.getText().toString());
        movie.put("url", editTextMovieURL.getText().toString());


        // Add a new document with a generated ID
        db.collection("movies")
                .add(movie)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d(TAG, "DocumentSnapshot added with ID: " + documentReference.getId());
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error adding document", e);
                    }
                });
    }
}
