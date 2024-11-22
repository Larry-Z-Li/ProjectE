package com.example.projecte.whitebox;

import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import android.content.Intent;
import android.view.View;
import android.widget.EditText;

import androidx.test.core.app.ActivityScenario;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.espresso.intent.Intents;

import com.example.projecte.ChatPage;
import com.example.projecte.GroupListPage;
import com.example.projecte.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.Objects;
import androidx.test.runner.AndroidJUnit4;

@RunWith(AndroidJUnit4.class)
public class ChatTests {
    public ActivityScenario<ChatPage> chatActivity;
    DatabaseReference r = FirebaseDatabase.getInstance().getReference();
    String username = "GroupTest1";
    String name2 = "GroupTest";
    String course = "BISC210";
    String group = "New Group test 2";
    ArrayList<String> messages;

    @Before
    public void before() {
        Intents.init();
        r.child("users").child(username).child("messages").child(name2).setValue("");
        r.child("users").child(name2).child("messages").child(username).setValue("");

    }

    @After
    public void after() {
        Intents.release();
        }

    @Test
    public void DMTest() throws InterruptedException {
//        r.child("users").child(username).child("messages").child(name2).removeValue();
//        r.child("users").child(name2).child("messages").child(username).removeValue();


        Intent intent = new Intent(ApplicationProvider.getApplicationContext(), ChatPage.class);
        intent.putExtra("name", "GroupTest1");
        intent.putExtra("name2", "GroupTest");
        intent.putExtra("type", "single");
        chatActivity = ActivityScenario.launch(intent);
        Thread.sleep(2000);
        chatActivity.onActivity(activity -> {
            EditText et = (EditText) activity.findViewById(R.id.edit_gchat_message);
            for(int i = 0; i < 20; i++)
            {
                et.setText("test");
                activity.send(new View(ApplicationProvider.getApplicationContext()));
            }
            this.messages = activity.messages;
        });

        Assert.assertEquals(20, messages.size());
    }

//    public void send(String type, int i) {
//        System.out.println("" + i);
//        r.child("users").child(username).child("messages").child(name2).child(i + "")
//                .child("name").setValue(username);
//        r.child("users").child(username).child("messages").child(name2).child(i + "")
//                .child("message").setValue("hi");
//        r.child("users").child(name2).child("messages").child(username).child(i + "")
//                .child("name").setValue(username);
//        r.child("users").child(name2).child("messages").child(username).child(i + "")
//                .child("message").setValue("hi");
//
//    }
}
