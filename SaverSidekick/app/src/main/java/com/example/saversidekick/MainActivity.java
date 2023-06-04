package com.example.saversidekick;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class MainActivity extends AppCompatActivity {

    FirebaseAuth auth;      // firebase authentication
    Button logoutButton,SehunButton;        // button to log out of the app
    TextView userEmail;         // text which shows the email of the current user logged in
    FirebaseUser currentUser;       // variable to store current user details from firebase
    // this function checks if the application is logged in and displays buttons and current user email
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // initialise variables for the main page
        auth = FirebaseAuth.getInstance();
        logoutButton = findViewById(R.id.logout);
        SehunButton = findViewById(R.id.Sehun);
        userEmail = findViewById(R.id.user_details);
        currentUser = auth.getCurrentUser();

        // next button opens earnings page once clicked
        Button nextButton = findViewById(R.id.buttonNext);

        nextButton.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, EarningsActivity.class);
            startActivity(intent);
        });

        SehunButton.setOnClickListener(view -> {
        Intent intent = new Intent(MainActivity.this, OverTimeHoursWorked.class);
           startActivity(intent);
        });
        if (currentUser == null)    // if there is no current user go to the log in page
        {
            Intent intent = new Intent(getApplicationContext(), Login.class);
            startActivity(intent);
            finish();
        }
        else
        {
            userEmail.setText(currentUser.getEmail());      // set the text to the current user's email
        }

        // functionality for the log out button, logs out in fire base and takes user to the log in screen
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                FirebaseAuth.getInstance().signOut();       // sign out in firebase
                Intent intent = new Intent(getApplicationContext(), Login.class);       // take user to log in screen
                startActivity(intent);
                finish();
            }
        });
    }
}
