package com.example.projecte;

import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.core.app.ActivityScenario;
import androidx.test.espresso.intent.Intents;
import androidx.test.espresso.intent.matcher.IntentMatchers;

import android.content.Intent;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.action.ViewActions.click;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.equalTo;
import static androidx.test.espresso.intent.Intents.intended;

@RunWith(AndroidJUnit4.class)
public class ResourcePageNavigationTest {

    private final String[] resourceTypes = {
            "Books", "Articles", "Video Notes", "Podcast Notes",
            "Webinars", "eBooks", "Templates", "Guides",
            "Courses", "Workshops", "Infographics", "Reports"
    };

    @Before
    public void init() {
        Intents.init();
    }

    @After
    public void release() {
        Intents.release();
    }

    @Test
    public void testClickResourceTypeNavigatesToPDFView() {
        String selectedResourceType = "Books";
        String groupName = "TestGroup";

        Intent intent = new Intent(ApplicationProvider.getApplicationContext(), ResourcePage.class);
        intent.putExtra("groupName", groupName);

        ActivityScenario.launch(intent);

        onData(allOf(is(instanceOf(String.class)), is(selectedResourceType)))
                .inAdapterView(withId(R.id.resource_list_page))
                .perform(click());

        intended(allOf(
                IntentMatchers.hasComponent(PDFView.class.getName()),
                IntentMatchers.hasExtra("resourceType", selectedResourceType),
                IntentMatchers.hasExtra("groupName", groupName)
        ));
    }
}
