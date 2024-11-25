package com.example.projecte;

import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.core.app.ActivityScenario;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.espresso.assertion.ViewAssertions;

import android.content.Intent;

import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;

@RunWith(AndroidJUnit4.class)
public class ResourcePageTest {

    private final String[] resourceTypes = {
            "Books", "Articles", "Video Notes", "Podcast Notes",
            "Webinars", "eBooks", "Templates", "Guides",
            "Courses", "Workshops", "Infographics", "Reports"
    };

    @Test
    public void testAllResourceTypesDisplayed() {
        Intent intent = new Intent(ApplicationProvider.getApplicationContext(), ResourcePage.class);
        intent.putExtra("groupName", "TestGroup");

        ActivityScenario.launch(intent);

        for (String resourceType : resourceTypes) {
            onData(allOf(is(instanceOf(String.class)), is(resourceType)))
                    .inAdapterView(withId(R.id.resource_list_page))
                    .check(ViewAssertions.matches(isDisplayed()));
        }
    }
}
