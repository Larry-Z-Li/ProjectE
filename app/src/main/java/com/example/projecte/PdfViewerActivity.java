package com.example.projecte;

import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Bundle;
import android.widget.Toast;

import com.github.barteksc.pdfviewer.PDFView;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;

public class PdfViewerActivity extends AppCompatActivity {

    private PDFView pdfView;
    private FirebaseStorage storage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdf_viewer);

        pdfView = findViewById(R.id.pdfView);
        storage = FirebaseStorage.getInstance();

        String pdfUrl = getIntent().getStringExtra("pdf_url");
        if (pdfUrl != null && !pdfUrl.isEmpty()) {
            loadPdf(pdfUrl);
        } else {
            Toast.makeText(this, "Invalid PDF URL", Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    private void loadPdf(String pdfUrl) {
        StorageReference storageRef = storage.getReferenceFromUrl(pdfUrl);
        try {
            File localFile = File.createTempFile("temp", "pdf", getCacheDir());

            storageRef.getFile(localFile)
                    .addOnSuccessListener(taskSnapshot -> {
                        pdfView.fromFile(localFile)
                                .enableSwipe(true)
                                .swipeHorizontal(false)
                                .enableDoubletap(true)
                                .onError(t -> {
                                    Toast.makeText(PdfViewerActivity.this, "Error loading PDF: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                                })
                                .load();
                    })
                    .addOnFailureListener(e -> {
                        Toast.makeText(PdfViewerActivity.this, "Failed to load PDF: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    });
        } catch (IOException e) {
            Toast.makeText(this, "Error creating temporary file: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
}

