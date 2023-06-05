package com.example.saversidekick;


import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static org.junit.Assert.assertTrue;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.widget.TextView;

import androidx.test.core.app.ActivityScenario;
import androidx.test.espresso.Espresso;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

@RunWith(AndroidJUnit4.class)
public class AddTransactionTest {

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    @Test
    public void testAddActivity() {
        ActivityScenario.launch(AddTransactionActivity.class);

        boolean test = false;

        String pattern = "dd/MM/yyyy";
        String date =new SimpleDateFormat(pattern).format(new Date());
        String name = "test";
        String amount = "100";

        Espresso.onView(ViewMatchers.withId(R.id.transactionNameInput))
                .perform(typeText(name));

        Espresso.onView(ViewMatchers.withId(R.id.transactionAmountInput))
                .perform(typeText(amount));

        Espresso.onView(withId(R.id.transactionInputButton)).perform(click());

        ArrayList<Transaction> list = HomePageActivity.transactionList;

        for (Transaction t : list)
        {
            if (t.getMemo().equals(name) && t.getDate().equals(date) && t.getAmount() == -100.0)
            {
                test = true;
            }
        }

        assertTrue(test);
    }

    @Test
    public void testAddActivityNoName() {
        ActivityScenario.launch(AddTransactionActivity.class);

        boolean test = false;

        String pattern = "dd/MM/yyyy";
        String date =new SimpleDateFormat(pattern).format(new Date());
        String name = "";
        String amount = "100";

        Espresso.onView(ViewMatchers.withId(R.id.transactionNameInput))
                .perform(typeText(name));

        Espresso.onView(ViewMatchers.withId(R.id.transactionAmountInput))
                .perform(typeText(amount));

        Espresso.onView(withId(R.id.transactionInputButton)).perform(click());

        ArrayList<Transaction> list = HomePageActivity.transactionList;

        if (list != null)
        {

            for (Transaction t : list)
            {
                if (t.getMemo().equals(name) && t.getDate().equals(date) && t.getAmount() == -100.0)
                {
                    test = true;
                }
            }
        }
        assert (test == false);
    }

    @Test
    public void testAddActivityIncorrectDate() {
        ActivityScenario.launch(AddTransactionActivity.class);

        boolean test = false;

        String date = "123456789";
        String name = "test3";
        String amount = "100";

        Espresso.onView(ViewMatchers.withId(R.id.transactionNameInput))
                .perform(typeText(name));

        Espresso.onView(ViewMatchers.withId(R.id.transactionAmountInput))
                .perform(typeText(amount));

        Espresso.onView(withId(R.id.transactionInputButton)).perform(click());

        ArrayList<Transaction> list = HomePageActivity.transactionList;

        if (list != null)
        {

            for (Transaction t : list)
            {
                if (t.getMemo().equals(name) && t.getDate().equals(date) && t.getAmount() == -100.0)
                {
                    test = true;
                }
            }
        }
        assert (test == false);
    }
}

