package com.example.saversidekick;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Register extends AppCompatActivity {

    TextInputEditText editEmail, editPassword;      //login field variables email/password
    Button registerButton;      // button to register details
    FirebaseAuth auth;      // firebase authentication
    ProgressBar loadingBar;     // loading bar once button clicked
    TextView loginLink;     // link to open the login page

    // this function runs when the page opens checks if the user is logged in and if so runs the main activity
    @Override
    public void onStart() {
        super.onStart();
        FirebaseUser currentUser = auth.getCurrentUser();
        if(currentUser != null){
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
            finish();
        }
    }

    // this function runs if no user is signed in (user == null)
    // called when activity is first created
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        //initialise register screen variables
        auth = FirebaseAuth.getInstance();
        editEmail = findViewById(R.id.email);
        editPassword = findViewById(R.id.password);
        registerButton = findViewById(R.id.btn_register);
        loadingBar = findViewById(R.id.progressBar);
        loginLink = findViewById(R.id.loginLink);

        // set up link to the login page
        loginLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Login.class);
                startActivity(intent);
                finish();
            }
        });

        // set the register button functionality to access firebase authentication and register user details
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadingBar.setVisibility(View.VISIBLE);     // loading bar appears once login button clicked
                String email, password;                     // variables for email/password input
                email = String.valueOf(editEmail.getText());        // set variable for email
                password = String.valueOf(editPassword.getText());      // set variable for password

                if (TextUtils.isEmpty(email))       // error message displayed to user if email field is empty
                {
                    Toast.makeText(Register.this, "No email entered: PLease enter valid user email", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(password))     // error message displayed to user of password field is empty
                {
                    Toast.makeText(Register.this, "No password entered: Please enter valid password", Toast.LENGTH_SHORT).show();
                    return;
                }

                // this function creates a new user in firebase with the user input for email/password
                auth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                loadingBar.setVisibility(View.GONE);
                                if (task.isSuccessful()) {
                                    // check if registration was successful in firebase, display message to the user
                                    Toast.makeText(Register.this, "Registration Complete: Account successfully created.",
                                            Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(getApplicationContext(), Login.class);
                                    startActivity(intent);
                                    finish();

                                } else {
                                    // If registration in firebase fails, display message to the user
                                    Toast.makeText(Register.this, "Could not register: Authentication failed.",
                                            Toast.LENGTH_SHORT).show();

                                }
                            }
                        });
            }
        });

    }
}