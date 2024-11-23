package com.example.projecte;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

import android.content.Intent;

import androidx.test.core.app.ActivityScenario;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.espresso.intent.Intents;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class DuplicateGroupTest {
    public ActivityScenario<GroupListPage> groupListActivity;

    DatabaseReference r = FirebaseDatabase.getInstance().getReference();
    String name = "GroupTest";
    String dupGroupName = "Duplicate Testing Group";

    int index;

    @Before
    public void before() {
        Intents.init();
    }

    @After
    public void after() {
        Intents.release();
    }

    @Test
    public void duplicateGroupTest() throws InterruptedException {
        Intent intent = new Intent(ApplicationProvider.getApplicationContext(), GroupListPage.class);
        intent.putExtra("name", name);
        groupListActivity = ActivityScenario.launch(intent);
        Thread.sleep(2000);
        onView(withId(R.id.addSign)).perform(click());
        intended(hasComponent(NewGroupStartPage.class.getName()));

        Thread.sleep(500);
        onView(withId(R.id.button)).perform(click());
        intended(hasComponent(NewGroupPage.class.getName()));

        Thread.sleep(500);
        onView(withId(R.id.group_name)).perform(typeText(dupGroupName));
        onView(withId(R.id.button)).perform(click());
        intended(hasComponent(NewGroupPage.class.getName()));

    }
}
