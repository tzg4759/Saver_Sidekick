package com.example.saversidekick;


import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
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

@RunWith(AndroidJUnit4.class)
public class EarningsActivityTest {

    private static final String TEST_AMOUNT = "500";

    private SharedPreferences sharedPreferences;

    @Before
    public void setUp() {
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(
                InstrumentationRegistry.getInstrumentation().getTargetContext());
    }

    @After
    public void tearDown() {
        sharedPreferences.edit().clear().apply();
    }

    @Test
    public void testWeeklyEarningsTransferToHomePage() {
        // Launch the EarningsActivity
        ActivityScenario.launch(EarningsActivity.class);

        // Enter the weekly earnings amount
        Espresso.onView(ViewMatchers.withId(R.id.editTextWeeklyEarnings))
                .perform(typeText(TEST_AMOUNT));

        // Click the submit button
        Espresso.onView(ViewMatchers.withId(R.id.buttonSubmitEarnings))
                .perform(click());

        // Verify that the value is transferred to the home page
        ActivityScenario.launch(HomePageActivity.class).onActivity(activity -> {
            TextView weeklyEarningsTextView = activity.findViewById(R.id.textViewWeeklyEarnings);
            String displayedAmount = weeklyEarningsTextView.getText().toString();
            String expectedAmount = "Your Weekly Earning: $" + TEST_AMOUNT;
            assertTrue(displayedAmount.contains(expectedAmount));
        });
    }
}

