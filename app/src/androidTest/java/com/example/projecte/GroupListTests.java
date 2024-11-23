package com.example.projecte;

import static androidx.core.content.ContextCompat.startActivity;
import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import static org.hamcrest.CoreMatchers.anything;

import android.content.Intent;
import android.os.Bundle;

import androidx.constraintlayout.widget.Group;
import androidx.test.core.app.ActivityScenario;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.espresso.intent.Intents;
import androidx.test.ext.junit.rules.ActivityScenarioRule;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;

public class GroupListTests {

    public ActivityScenario<GroupListPage> groupListActivity;

    DatabaseReference r = FirebaseDatabase.getInstance().getReference();
    String name = "GroupTest";
    @Before
    public void before() {
        Intents.init();
        r.child("users").child(name).child("groups").child(name2).setValue("");

    }
    @After
    public void after(){
        Intents.release();
    }

    @Test
    public void joinGroupTest() throws InterruptedException {
        Intent intent = new Intent(ApplicationProvider.getApplicationContext(), GroupListPage.class);
        intent.putExtra("name", name);
        groupListActivity = ActivityScenario.launch(intent);
        Thread.sleep(2000);
        onData(anything()).inAdapterView(withId(R.id.group_list_page)).atPosition(1).
                perform(click());
        intended(hasComponent(GroupListPage.class.getName())); // did not switch page
        onData(anything()).inAdapterView(withId(R.id.group_list_page)).atPosition(1).onChildView(withId(R.id.group_list_join_button))
                .perform(click());
        Thread.sleep(500);
        onData(anything()).inAdapterView(withId(R.id.group_list_page)).atPosition(1).
                perform(click());
        intended(hasComponent(MemberListPage.class.getName())); // switched page
    }
    @Test
    public void duplicateGroupTest() throws InterruptedException {
        Intent intent = new Intent(ApplicationProvider.getApplicationContext(), GroupListPage.class);
        intent.putExtra("name", "GroupTest");
        groupListActivity = ActivityScenario.launch(intent);
        Thread.sleep(2000);
        onView(withId(R.id.addSign)).perform(click());
        intended(hasComponent(NewGroupStartPage.class.getName()));

        Thread.sleep(500);
        onView(withId(R.id.button)).perform(click());
        intended(hasComponent(NewGroupPage.class.getName()));

        Thread.sleep(500);
        onView(withId(R.id.group_name)).perform(typeText("Duplicate Testing Group"));
        onView(withId(R.id.button)).perform(click());
        intended(hasComponent(NewGroupPage.class.getName()));

    }
}
