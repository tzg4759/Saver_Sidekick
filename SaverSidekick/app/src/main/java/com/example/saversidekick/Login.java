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

public class Login extends AppCompatActivity {

    FirebaseAuth auth;     // firebase authentication
    TextInputEditText editEmail, editPassword;  //login field variables email/password
    Button loginButton;     // button to log into the app
    ProgressBar loadingBar;    // loading bar once button clicked
    TextView registerPage;      // link to open the register page

    // this function checks if the app is logged in upon launch, if yes go straight to the main activity page
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
        setContentView(R.layout.activity_login);

        //initialise log in screen variables
        auth = FirebaseAuth.getInstance();
        editEmail = findViewById(R.id.email);
        editPassword = findViewById(R.id.password);
        loginButton = findViewById(R.id.btn_login);
        loadingBar = findViewById(R.id.progressBar);
        registerPage = findViewById(R.id.registerLink);

        // set up link to the register page
        registerPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Register.class);
                startActivity(intent);
                finish();
            }
        });

        // set the login in button functionality to access firebase authentication and log into the application
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                loadingBar.setVisibility(View.VISIBLE);     // loading bar appears once login button clicked
                String email, password;                     // variables for email/password input
                email = String.valueOf(editEmail.getText());    // set variable for email
                password = String.valueOf(editPassword.getText());      // set variable for password

                if (TextUtils.isEmpty(email))   // error message displayed to user if email field is empty
                {
                    Toast.makeText(Login.this, "No email entered: PLease enter valid user email", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(password))    // error message displayed to user of password field is empty
                {
                    Toast.makeText(Login.this, "No password entered: Please enter valid password", Toast.LENGTH_SHORT).show();
                    return;
                }

                // this function will authenticate user into the firebase and open the main activity if successful
                auth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                loadingBar.setVisibility(View.GONE);
                                if (task.isSuccessful()) {
                                    Toast.makeText(getApplicationContext(), "Log in Successful.",
                                            Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                    startActivity(intent);
                                    finish();
                                } else {
                                    Toast.makeText(Login.this, "Could not log in: Authentication failed.",
                                            Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });
    }
}