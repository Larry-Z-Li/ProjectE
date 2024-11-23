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

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.Group;
import androidx.test.core.app.ActivityScenario;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.espresso.intent.Intents;
import androidx.test.ext.junit.rules.ActivityScenarioRule;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;

import java.util.Objects;

public class JoinGroupTest {

    public ActivityScenario<GroupListPage> groupListActivity;

    DatabaseReference r = FirebaseDatabase.getInstance().getReference();
    String name = "GroupTest";
    String joinGroupName = "Larry's Testing Group";

    int index;

    @Before
    public void before() {
        Intents.init();
        r.child("courses").child("CSCI301").child("groups").child(joinGroupName).child("members").setValue("");
        r.child("users").child(name).child("groups").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot s: snapshot.getChildren())
                {
                    if(Objects.equals(s.getValue(String.class), joinGroupName))
                    {
                        s.getRef().removeValue();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    @After
    public void after() {
        Intents.release();
        r.child("courses").child("CSCI301").child("groups").child(joinGroupName).child("members").setValue("");
        r.child("users").child(name).child("groups").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot s: snapshot.getChildren())
                {
                    if(Objects.equals(s.getValue(String.class), joinGroupName))
                    {
                        s.getRef().removeValue();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Test
    public void joinGroupTest() throws InterruptedException {
        Intent intent = new Intent(ApplicationProvider.getApplicationContext(), GroupListPage.class);
        intent.putExtra("name", name);
        groupListActivity = ActivityScenario.launch(intent);
        Thread.sleep(2000);
        groupListActivity.onActivity(activity -> {
            index = activity.groups.indexOf(joinGroupName);
        });

        onData(anything()).inAdapterView(withId(R.id.group_list_page)).atPosition(index).
                perform(click());
        intended(hasComponent(GroupListPage.class.getName())); // did not switch page
        onData(anything()).inAdapterView(withId(R.id.group_list_page)).atPosition(index).onChildView(withId(R.id.group_list_join_button))
                .perform(click());
        Thread.sleep(500);
        onData(anything()).inAdapterView(withId(R.id.group_list_page)).atPosition(index).
                perform(click());
        intended(hasComponent(MemberListPage.class.getName())); // switched page

    }


}
