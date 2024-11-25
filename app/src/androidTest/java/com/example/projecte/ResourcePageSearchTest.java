package com.example.projecte;

import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isAssignableFrom;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;

import android.view.View;
import android.widget.SearchView;
import android.content.Intent;

import androidx.test.core.app.ApplicationProvider;
import androidx.test.espresso.UiController;
import androidx.test.espresso.ViewAction;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.core.app.ActivityScenario;

import org.hamcrest.Matcher;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class ResourcePageSearchTest {

    public static ViewAction typeSearchViewText(final String text){
        return new ViewAction(){
            @Override
            public Matcher<View> getConstraints() {
                return allOf(isDisplayed(), isAssignableFrom(SearchView.class));
            }

            @Override
            public String getDescription() {
                return "Change view text";
            }

            @Override
            public void perform(UiController uiController, View view) {
                ((SearchView) view).setQuery(text, false);
            }
        };
    }

    @Test
    public void testSearchBarFiltersListCorrectly() {
        Intent intent = new Intent(ApplicationProvider.getApplicationContext(), ResourcePage.class);
        intent.putExtra("groupName", "TestGroup");
        ActivityScenario.launch(intent);

        String searchQuery = "Book";
        onView(withId(R.id.search_bar))
                .perform(typeSearchViewText(searchQuery));

        onData(allOf(is(instanceOf(String.class)), is("Books")))
                .inAdapterView(withId(R.id.resource_list_page))
                .check(matches(isDisplayed()));
    }
}
