package com.example.projecte;

import android.os.Bundle;
import android.widget.ListView;
import android.widget.SearchView;
import androidx.appcompat.app.AppCompatActivity;
import com.example.projecte.R;
import com.example.projecte.components.ResourcePageAdapter;

public class ResourcePage extends AppCompatActivity {

    private ListView resourceListView;
    private SearchView resourceSearchView;
    private ResourcePageAdapter adapter;

    // Sample data
    private String[] resourceTypes = {
            "Books", "Articles", "Videos", "Podcasts",
            "Webinars", "eBooks", "Templates", "Guides",
            "Courses", "Workshops", "Infographics", "Reports"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.resource_page); // Ensure correct layout file

        // Initialize views
        resourceListView = findViewById(R.id.resource_list_page);
        resourceSearchView = findViewById(R.id.search_bar);

        // Initialize adapter
        adapter = new ResourcePageAdapter(this, resourceTypes);

        // Set adapter to ListView
        resourceListView.setAdapter(adapter);

        // Set up SearchView listeners
        setupSearchView();
    }

    private void setupSearchView() {
        // Enable submitting queries
        resourceSearchView.setSubmitButtonEnabled(true);

        // Listener for query text changes
        resourceSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // Perform final search when query is submitted
                adapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                // Perform search as text changes
                adapter.getFilter().filter(newText);
                return false;
            }
        });

        // Optional: Listener for closing the search view
        resourceSearchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                // Reset the filter when search is closed
                adapter.getFilter().filter("");
                return false;
            }
        });
    }
}
