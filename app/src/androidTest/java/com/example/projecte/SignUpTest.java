package com.example.projecte;
import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.hasErrorText;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withClassName;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withSpinnerText;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import static org.hamcrest.CoreMatchers.anything;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.core.IsInstanceOf.instanceOf;

import static java.util.EnumSet.allOf;

import androidx.test.ext.junit.rules.ActivityScenarioRule;
import org.junit.Rule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Random;

@RunWith(AndroidJUnit4.class)
public class SignUpTest {

    @Rule
    public ActivityScenarioRule<SignupActivity> signupActivityRule =
            new ActivityScenarioRule<>(SignupActivity.class);

    @Test
    public void testSingup() throws InterruptedException {
        Random random = new Random();
        int randomInt = random.nextInt(10000);
        String username = "signUpTestUser" + randomInt;
        onView(withId(R.id.usernameText)).perform(typeText(username));
        onView(withId(R.id.passwordText)).perform(typeText("signUpTestPass"));
        onView(withId(R.id.passwordTextConfirm)).perform(typeText("signUpTestPass"));
        onView(withId(R.id.dropdownCourses)).perform(click());
        onData(anything())
                .atPosition(1)
                .perform(click());
        onView(withId(R.id.loginButton)).perform(click());
        Thread.sleep(2000);
        onView(withId(R.id.listCourses)).check(matches(isDisplayed()));
        System.out.println("Signup Successful");
    }
}