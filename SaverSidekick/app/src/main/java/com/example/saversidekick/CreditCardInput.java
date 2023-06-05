package com.example.saversidekick;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;

public class CreditCardInput extends AppCompatActivity {
    EditText Cardnumber,Expiredata,cvvnumber;
    //Radio group
    RadioGroup radioGroup;
    RadioButton radioButton;
    Button add;

    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle toggle;
    private NavigationView navigationView;
    private int selectedMenuItemId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_credit_card_input);
        //get setting
       // radioGroup = findViewById(R.id.RadioGroup);
        Cardnumber=findViewById(R.id.cardnumber);
        Expiredata=findViewById(R.id.Expire);
        cvvnumber=findViewById(R.id.cvv);
        add = findViewById(R.id.add);
        //change to var
//Radio group
        radioGroup = findViewById(R.id.banks);

        //credit card
       // CreditCard card = new CreditCard(getNum,date,cvv,"anz");
        //submit
        add.setOnClickListener(view -> {
            String getNum = Cardnumber.getText().toString();
            String date = Expiredata.getText().toString();
            String getCvv = cvvnumber.getText().toString();
            Intent intent = new Intent(CreditCardInput.this, DisplayCreditCard.class);
            if (TextUtils.isEmpty(getNum)) {
                Toast.makeText(CreditCardInput.this,"Please enter a correct Card number",Toast.LENGTH_SHORT).show();
                return;
            }
            if (getNum.length()<10) {
                Toast.makeText(CreditCardInput.this,"Please enter a correct Card number",Toast.LENGTH_SHORT).show();
                return;
            }
            if (TextUtils.isEmpty(date)) {
                Toast.makeText(CreditCardInput.this,"Please enter a Expire data",Toast.LENGTH_SHORT).show();
                return;
            }
            if (TextUtils.isEmpty(getCvv)) {
                Toast.makeText(CreditCardInput.this,"Please enter a correct cvv number",Toast.LENGTH_SHORT).show();
                return;
            }
            int radioID = radioGroup.getCheckedRadioButtonId();
            radioButton = findViewById(radioID);
            if (radioButton == null|| radioGroup==null) {
                Toast.makeText(CreditCardInput.this,"Please Select ur Bank",Toast.LENGTH_SHORT).show();
                return;
            }
            intent.putExtra("getNum", getNum);
            intent.putExtra("date", date);
            intent.putExtra("getCvv", getCvv);
            intent.putExtra("Bank", radioButton.getText());
            startActivity(intent);
        });

        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);

        toggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        navigationView.setNavigationItemSelectedListener(menuItem -> {
            Intent intent;
            switch (menuItem.getItemId()) {
                case R.id.nav_goal:
                    // Handle goals navigation
                    selectedMenuItemId = R.id.nav_goal;  // Update selectedMenuItemId
                    intent = new Intent(CreditCardInput.this, GoalsActivity.class);
                    break;
                case R.id.nav_graph:
                    // Handle budget navigation
                    selectedMenuItemId = R.id.nav_graph;  // Update selectedMenuItemId
                    intent = new Intent(CreditCardInput.this, GraphActivity.class);
                    SharedPreferences sharedPreferences = getSharedPreferences("MySharedPref", MODE_PRIVATE);
                    String monthSumString = sharedPreferences.getString("monthSums", "default_value_if_not_found");
                    intent.putExtra("monthString", monthSumString);
                    startActivity(intent);
                    break;
                case R.id.nav_home:
                    selectedMenuItemId = R.id.nav_home;
                    intent = new Intent(CreditCardInput.this, HomePageActivity.class);
                    break;
                case R.id.nav_creditCard:
                    selectedMenuItemId = R.id.nav_creditCard;
                    intent = new Intent(CreditCardInput.this, BudgetActivity.class);
                    break;
                // Handle additional navigation items here
                default:
                    return true;
            }
            intent.putExtra("selectedMenuItemId", selectedMenuItemId);
            startActivity(intent);
            drawerLayout.closeDrawer(GravityCompat.START);
            return true;
        });

        // Get the selectedMenuItemId passed from the previous Activity
        Intent intent = getIntent();
        selectedMenuItemId = intent.getIntExtra("selectedMenuItemId", R.id.nav_home);

        updateSelectedMenuItem();
    }

    private void updateSelectedMenuItem() {
        navigationView.setCheckedItem(selectedMenuItemId);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (toggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void checkButton(View view){
      int radioID = radioGroup.getCheckedRadioButtonId();
      radioButton = findViewById(radioID);
      Toast.makeText(this,"Selected radio"+radioButton.getText(),Toast.LENGTH_SHORT);
    }
}