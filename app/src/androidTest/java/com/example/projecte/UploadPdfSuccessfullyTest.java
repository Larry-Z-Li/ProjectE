package com.example.projecte;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.test.core.app.ActivityScenario;
import androidx.test.espresso.intent.Intents;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.core.app.ApplicationProvider;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Objects;

@RunWith(AndroidJUnit4.class)
public class UploadPdfSuccessfullyTest {

    public ActivityScenario<ResourcePage> activityRule;
    DatabaseReference r = FirebaseDatabase.getInstance().getReference();
    String name = "TestGroup";
    String joinGroupName = "TestGroup";

    private FirebaseFirestore firestore;
    private String userName = "TestUser";
    private String groupName = "GroupA";
    private String resourceType = "Books";

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
    public void testSuccessfulPdfUpload() throws InterruptedException {
        Intent intent = new Intent(ApplicationProvider.getApplicationContext(), PDFView.class);
        intent.putExtra("resourceType", resourceType);
        intent.putExtra("groupName", groupName);
        intent.putExtra("userName", userName);
        activityRule = ActivityScenario.launch(intent);

        Thread.sleep(2000);

        onView(withId(R.id.button_upload)).perform(click());

        Thread.sleep(1000);

        System.out.println("Test Successful PDF Upload Passed");
    }
}
