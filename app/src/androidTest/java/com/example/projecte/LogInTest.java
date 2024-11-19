package com.example.projecte;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.hasErrorText;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import androidx.test.ext.junit.rules.ActivityScenarioRule;
import org.junit.Rule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class LogInTest {

    @Rule
    //opening java activity file so that it runs that page
    public ActivityScenarioRule<LoginActivity> activityRule = new ActivityScenarioRule<>(LoginActivity.class);

    @Test
    public void testCorrectLogin() throws InterruptedException {
        onView(withId(R.id.usernameText)).perform(typeText("userTest"));
        onView(withId(R.id.passwordText)).perform(typeText("passwordTest"));
        onView(withId(R.id.loginButton)).perform(click());
        Thread.sleep(2000);
        onView(withId(R.id.listCourses)).check(matches(isDisplayed()));
        System.out.println("Login Successful");
    }

    @Test
    public void incorrectCorrectLogin() throws InterruptedException {
        onView(withId(R.id.usernameText)).perform(typeText("userTestWrong"));
        onView(withId(R.id.passwordText)).perform(typeText("passwordTestWrong"));
        onView(withId(R.id.loginButton)).perform(click());
        Thread.sleep(2000);
        onView(withId(R.id.passwordText)).check(matches(hasErrorText("Password incorrect")));
        System.out.println("Login not Successful");
    }
}
