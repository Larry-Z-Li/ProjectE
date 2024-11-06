package com.example.projecte;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
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

        resourceListView = findViewById(R.id.resource_list_page);
        resourceSearchView = findViewById(R.id.search_bar);

        adapter = new ResourcePageAdapter(this, resourceTypes);

        resourceListView.setAdapter(adapter);

        setupSearchView();
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

    public void openResource(View view)
    {
        Intent intent = new Intent(ResourcePage.this, PDFView.class);
        startActivity(intent);
    }

    public void addResource(View view){

    }
}
