package com.example.projecte;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.text.InputType;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import com.example.projecte.components.PdfAdapter;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class PDFView extends AppCompatActivity {

    private static final int PICK_PDF_REQUEST = 1;
    private static final int PERMISSION_REQUEST_READ_EXTERNAL_STORAGE = 100;

    private FirebaseStorage storage;
    private FirebaseFirestore firestore;
    private Button buttonUpload;
    private RecyclerView recyclerView;
    private PdfAdapter pdfAdapter;
    private List<PdfItem> pdfList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.resource_pdf_viewer);

        storage = FirebaseStorage.getInstance();
        firestore = FirebaseFirestore.getInstance();

        buttonUpload = findViewById(R.id.button_upload);
        recyclerView = findViewById(R.id.recycler_view_pdfs);

        pdfList = new ArrayList<>();
        pdfAdapter = new PdfAdapter(pdfList, new PdfAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(PdfItem pdfItem) {
                // Handle PDF item click
                Intent intent = new Intent(PDFView.this, PdfViewerActivity.class);
                intent.putExtra("pdf_url", pdfItem.getUrl());
                startActivity(intent);
            }
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(pdfAdapter);

        buttonUpload.setOnClickListener(view -> {
            if (ContextCompat.checkSelfPermission(PDFView.this,
                    Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(PDFView.this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        PERMISSION_REQUEST_READ_EXTERNAL_STORAGE);
            } else {
                openFileChooser();
            }
        });

        loadPdfs();
    }

    private void openFileChooser() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("application/pdf");
        startActivityForResult(Intent.createChooser(intent, "Select PDF"), PICK_PDF_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_PDF_REQUEST && resultCode == Activity.RESULT_OK
                && data != null && data.getData() != null) {
            Uri pdfUri = data.getData();
            uploadPDF(pdfUri);
        } else {
            Toast.makeText(this, "No PDF selected", Toast.LENGTH_SHORT).show();
        }
    }

    private void uploadPDF(Uri pdfUri) {
        if (pdfUri == null) {
            Toast.makeText(this, "No file selected", Toast.LENGTH_SHORT).show();
            return;
        }

        showTitleInputDialog(pdfUri);
    }

    private void showTitleInputDialog(Uri pdfUri) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Enter PDF Title");

        final EditText input = new EditText(this);
        input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_CAP_WORDS);
        builder.setView(input);

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String title = input.getText().toString().trim();
                if (!title.isEmpty()) {
                    proceedToUpload(pdfUri, title);
                } else {
                    Toast.makeText(PDFView.this, "Title cannot be empty", Toast.LENGTH_SHORT).show();
                }
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();
    }

    private void proceedToUpload(Uri pdfUri, String title) {
        String fileName = "pdfs/" + UUID.randomUUID().toString() + ".pdf";
        StorageReference storageRef = storage.getReference().child(fileName);

        storageRef.putFile(pdfUri)
                .addOnSuccessListener(taskSnapshot -> {
                    storageRef.getDownloadUrl().addOnSuccessListener(uri -> {
                        PdfItem pdfItem = new PdfItem(title, uri.toString(), System.currentTimeMillis());

                        firestore.collection("pdfs")
                                .add(pdfItem)
                                .addOnSuccessListener(documentReference -> {
                                    Toast.makeText(PDFView.this, "PDF uploaded successfully", Toast.LENGTH_SHORT).show();
                                    loadPdfs(); // Refresh the list
                                })
                                .addOnFailureListener(e -> {
                                    Toast.makeText(PDFView.this, "Failed to upload metadata: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                                });
                    }).addOnFailureListener(e -> {
                        Toast.makeText(PDFView.this, "Failed to get download URL: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    });
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(PDFView.this, "Failed to upload PDF: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                });
    }


    private void loadPdfs() {
        firestore.collection("pdfs")
                .orderBy("timestamp", Query.Direction.DESCENDING)
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    pdfList.clear();
                    for (QueryDocumentSnapshot document : queryDocumentSnapshots) {
                        PdfItem pdfItem = document.toObject(PdfItem.class);
                        pdfList.add(pdfItem);
                    }
                    pdfAdapter.notifyDataSetChanged();
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(PDFView.this, "Failed to load PDFs: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        openFileChooser();
//        if (requestCode == PERMISSION_REQUEST_READ_EXTERNAL_STORAGE) {
//            if (grantResults.length > 0
//                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//            } else {
//                Toast.makeText(this, "Permission required to select PDFs", Toast.LENGTH_SHORT).show();
//            }
//        }
    }
}
