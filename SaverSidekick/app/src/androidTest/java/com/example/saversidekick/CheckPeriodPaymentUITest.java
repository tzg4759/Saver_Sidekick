package com.example.saversidekick;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.espresso.Espresso;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.matcher.ViewMatchers.withId;


@RunWith(AndroidJUnit4.class)
@LargeTest
public class CheckPeriodPaymentUITest {
    @Rule
    public ActivityTestRule<CheckPeriodPayment> activityRule = new ActivityTestRule<>(CheckPeriodPayment.class);

    @Test
    public void testCheckButtonWithoutInput() {
        // Click the checkButton without entering start and end dates
        Espresso.onView(withId(R.id.checkPaymentButton)).perform(click());

    }

    @Test
    public void testInvalidDateFormatError() {
        // Enter invalid date format in the start and end date fields
        Espresso.onView(withId(R.id.check_startDate)).perform(typeText("2023/12/23"));
        Espresso.onView(withId(R.id.check_endDate)).perform(typeText("2023/12/24"));

        // Click the checkButton
        Espresso.onView(withId(R.id.checkPaymentButton)).perform(click());

    }

    @Test
    public void testCheckButtonWithValidInput() {
        // Enter valid start and end dates
        Espresso.onView(withId(R.id.check_startDate)).perform(typeText("01/06/2023"));
        Espresso.onView(withId(R.id.check_endDate)).perform(typeText("30/06/2023"));

        // Click the checkButton
        Espresso.onView(withId(R.id.checkPaymentButton)).perform(click());

    }
}

