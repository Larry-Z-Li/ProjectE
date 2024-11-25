package com.example.projecte;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.equalTo;

import android.content.Intent;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.test.core.app.ActivityScenario;
import androidx.test.espresso.ViewAssertion;
import androidx.test.espresso.matcher.BoundedMatcher;
import androidx.test.espresso.intent.Intents;
import androidx.test.espresso.intent.matcher.IntentMatchers;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.core.app.ApplicationProvider;

import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Objects;
import java.util.HashMap;
import java.util.Map;

@RunWith(AndroidJUnit4.class)
public class HandleEmptyPdfListTest {

    private ActivityScenario<PDFView> activityScenario;
    private DatabaseReference r = FirebaseDatabase.getInstance().getReference();
    private String name = "TestUser";
    private String joinGroupName = "TestGroup";

    private String userName = "TestUser";
    private String groupName = "TestGroup";
    private String resourceType = "Books";

    @Before
    public void before() {
        Intents.init();
        clearPdfsFromGroup();
    }

    @After
    public void after() {
        Intents.release();

        r.child("courses").child("CSCI301").child("groups").child(joinGroupName)
                .child("members").setValue("");

        r.child("users").child(name).child("groups")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for(DataSnapshot s : snapshot.getChildren()) {
                            if(Objects.equals(s.getValue(String.class), joinGroupName)) {
                                s.getRef().removeValue();
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                    }
                });
    }

    private void clearPdfsFromGroup() {
        r.child("pdfs").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot pdfSnapshot : snapshot.getChildren()) {
                    String pdfGroupName = pdfSnapshot.child("groupName").getValue(String.class);
                    if(Objects.equals(pdfGroupName, groupName)) {
                        pdfSnapshot.getRef().removeValue();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }

    public static ViewAssertion isRecyclerViewEmpty() {
        return (view, noViewFoundException) -> {
            if (noViewFoundException != null) {
                throw noViewFoundException;
            }

            if (!(view instanceof RecyclerView)) {
                throw new AssertionError("The view is not a RecyclerView");
            }

            RecyclerView recyclerView = (RecyclerView) view;
            RecyclerView.Adapter adapter = recyclerView.getAdapter();

            if (adapter == null) {
                throw new AssertionError("RecyclerView has no adapter");
            }

            if (adapter.getItemCount() != 0) {
                throw new AssertionError("RecyclerView is not empty");
            }
        };
    }

    @Test
    public void testHandleEmptyPdfListDisplaysMessageAndEmptyRecyclerView() throws InterruptedException {
        Intent intent = new Intent(ApplicationProvider.getApplicationContext(), PDFView.class);
        intent.putExtra("resourceType", resourceType);
        intent.putExtra("groupName", groupName);
        intent.putExtra("userName", userName);

        activityScenario = ActivityScenario.launch(intent);

        Thread.sleep(3000);

        onView(withId(R.id.recycler_view_pdfs))
                .check(matches(isDisplayed()))
                .check(isRecyclerViewEmpty());

        System.out.println("Test Handle Empty PDF List Passed");
    }
}
