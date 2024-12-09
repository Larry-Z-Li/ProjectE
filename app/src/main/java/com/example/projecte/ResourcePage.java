package com.example.projecte;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SearchView;

import com.example.projecte.components.ResourcePageAdapter;

import java.util.ArrayList;
import java.util.Arrays;

public class ResourcePage extends AppCompatActivity {

    private ListView resourceListView;
    private SearchView resourceSearchView;
    private ResourcePageAdapter adapter;
    private String group;

    private String[] resourceTypes = {
            "Books", "Articles", "Video Notes", "Podcast Notes",
            "Webinars", "eBooks", "Templates", "Guides",
            "Courses", "Workshops", "Infographics", "Reports"
    };

    private String[] filteredResourceTypes = resourceTypes;  // Initialize to the original array

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.resource_page);
        EdgeToEdge.enable(this);
        group = getIntent().getStringExtra("groupName");

        resourceListView = findViewById(R.id.resource_list_page);
        resourceSearchView = findViewById(R.id.search_bar);

        adapter = new ResourcePageAdapter(this, filteredResourceTypes);

        resourceListView.setAdapter(adapter);

        setupSearchView();

        resourceListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selectedResourceType = filteredResourceTypes[position];
                Intent intent = new Intent(ResourcePage.this, PDFView.class);
                intent.putExtra("resourceType", selectedResourceType);
                intent.putExtra("groupName", group);
                startActivity(intent);
            }
        });
    }

    private void setupSearchView() {
        resourceSearchView.setSubmitButtonEnabled(true);

        resourceSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                filterList(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filterList(newText);
                return false;
            }
        });

        resourceSearchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                filterList("");
                return false;
            }
        });
    }

    private void filterList(String query) {
        if (query.isEmpty()) {
            filteredResourceTypes = resourceTypes;  // Reset to original array
        } else {
            ArrayList<String> filteredList = new ArrayList<>();
            for (String resourceType : resourceTypes) {
                if (resourceType.toLowerCase().contains(query.toLowerCase())) {
                    filteredList.add(resourceType);
                }
            }
            filteredResourceTypes = filteredList.toArray(new String[0]);  // Convert to String[]
        }
        adapter = new ResourcePageAdapter(this, filteredResourceTypes);
        resourceListView.setAdapter(adapter);  // Update the ListView with the new data
    }

    public void back(View view) {
        finish();
    }
}
