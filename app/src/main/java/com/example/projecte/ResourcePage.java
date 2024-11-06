package com.example.projecte;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SearchView;

import com.example.projecte.components.ResourcePageAdapter;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.resource_page);
        group = getIntent().getStringExtra("groupName");

        resourceListView = findViewById(R.id.resource_list_page);
        resourceSearchView = findViewById(R.id.search_bar);

        adapter = new ResourcePageAdapter(this, resourceTypes);

        resourceListView.setAdapter(adapter);

        setupSearchView();

        resourceListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selectedResourceType = resourceTypes[position];
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
                adapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
                return false;
            }
        });

        resourceSearchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                adapter.getFilter().filter("");
                return false;
            }
        });
    }

    public void back(View view)
    {
        finish();
    }
}
