package com.example.projecte;

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

import androidx.test.core.app.ActivityScenario;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.espresso.intent.Intents;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class ChatTests {
    public ActivityScenario<GroupListPage> groupListActivity;

    @Before
    public void before() {
        Intents.init();
    }
    @After
    public void after() {
        Intents.release();
    }
    @Test
    public void DMTest() throws InterruptedException {
        Intent intent = new Intent(ApplicationProvider.getApplicationContext(), GroupListPage.class);
        intent.putExtra("name", "GroupTest1");
        groupListActivity = ActivityScenario.launch(intent);
        Thread.sleep(2000);
        onData(anything()).inAdapterView(withId(R.id.group_list_page)).atPosition(0).
                perform(click());
        intended(hasComponent(MemberListPage.class.getName()));

        Thread.sleep(2000);
        onData(anything()).inAdapterView(withId(R.id.list)).atPosition(0).
                perform(click());

        intended(hasComponent(ChatPage.class.getName()));
        onView(withId(R.id.edit_gchat_message)).perform(typeText("testing"));
        onView(withId(R.id.button_gchat_send)).perform(click());
    }
    @Test
    public void GroupChatTest() throws InterruptedException {
        Intent intent = new Intent(ApplicationProvider.getApplicationContext(), GroupListPage.class);
        intent.putExtra("name", "GroupTest1");
        groupListActivity = ActivityScenario.launch(intent);
        Thread.sleep(2000);
        onData(anything()).inAdapterView(withId(R.id.group_list_page)).atPosition(0).
                perform(click());
        intended(hasComponent(MemberListPage.class.getName()));

        Thread.sleep(2000);
        onView(withText("Chat Box")).perform(click());

        intended(hasComponent(ChatPage.class.getName()));
        onView(withId(R.id.edit_gchat_message)).perform(typeText("testing"));
        onView(withId(R.id.button_gchat_send)).perform(click());
    }
}
