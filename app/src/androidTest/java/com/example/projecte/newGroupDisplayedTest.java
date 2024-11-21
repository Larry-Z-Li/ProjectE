package com.example.projecte;
import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.hasErrorText;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import static org.hamcrest.CoreMatchers.anything;
import static org.hamcrest.CoreMatchers.is;

import androidx.test.ext.junit.rules.ActivityScenarioRule;
import org.junit.Rule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Random;

@RunWith(AndroidJUnit4.class)
public class newGroupDisplayedTest {

    @Rule
    //opening java activity file so that it runs that page
    public ActivityScenarioRule<LoginActivity> activityRule = new ActivityScenarioRule<>(LoginActivity.class);

    @Test
    public void testCorrectLogin() throws InterruptedException {
        onView(withId(R.id.usernameText)).perform(typeText("userTest"));
        onView(withId(R.id.passwordText)).perform(typeText("passwordTest"));
        onView(withId(R.id.loginButton)).perform(click());
        Thread.sleep(2000);
        onView(withId(R.id.HomePageButton)).perform(click());
        onView(withId(R.id.myGroups)).perform(click());
        onView(withId(R.id.addSign)).perform(click());
        onView(withId(R.id.button)).perform(click());
        Random random = new Random();
        int randomInt = random.nextInt(10000);
        String groupName = "groupTest" + randomInt;
        onView(withId(R.id.group_name)).perform(typeText(groupName));
        onData(anything())
                .atPosition(1)
                .perform(click());
        onData(anything())
                .atPosition(2)
                .perform(click());
        onView(withId(R.id.button)).perform(click());
        System.out.println("Username in Page Successful");
    }

}
