package com.example.lenovo.glassbookingapp;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.lenovo.glassbookingapp.Model.Person;
import com.example.lenovo.glassbookingapp.retrofit.RetrofitClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class mainActivity extends AppCompatActivity {


    private RecyclerView recyclerView;
    private PersonAdapter personAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        recyclerView = findViewById(R.id.rv_persons);
        setSupportActionBar(toolbar); 

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });


        //Shared ID
        SharedPreferences result= getSharedPreferences("saveDATA",MODE_PRIVATE);
        int value=result.getInt("Value",0);

        //



        Call<List<Person>> call =  RetrofitClient.getUrls().getPersons();
        call.enqueue(new Callback<List<Person>>() {
            @Override
            public void onResponse(@NonNull Call<List<Person>> call,
                                   @NonNull Response<List<Person>> response) {
                if (response.isSuccessful()){
                    List<Person> persons = response.body();

                    personAdapter = new PersonAdapter(persons, getApplicationContext());
                    LinearLayoutManager manager = new LinearLayoutManager(getApplicationContext());
                    recyclerView.setHasFixedSize(true);
                    recyclerView.setLayoutManager(manager);
                    recyclerView.setAdapter(personAdapter);


                }

            }

            @Override
            public void onFailure(@NonNull Call<List<Person>> call,
                                  @NonNull Throwable t) {
                Toast.makeText(getApplicationContext(),"Connection Problem",Toast.LENGTH_SHORT)
                        .show();
            }
        });
    }

}
