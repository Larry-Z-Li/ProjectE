package com.example.projecte;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.projecte.components.PdfAdapter;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class PDFView extends AppCompatActivity {

    private FirebaseStorage storage;
    private FirebaseFirestore firestore;
    private Button buttonUpload;
    private RecyclerView recyclerView;
    private PdfAdapter pdfAdapter;
    private List<PdfItem> pdfList;
    private String resourceType;
    private String groupName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.resource_pdf_viewer);

        storage = FirebaseStorage.getInstance();
        firestore = FirebaseFirestore.getInstance();

        buttonUpload = findViewById(R.id.button_upload);
        recyclerView = findViewById(R.id.recycler_view_pdfs);

        Intent intent = getIntent();
        resourceType = intent.getStringExtra("resourceType");
        groupName = intent.getStringExtra("groupName");
        if (resourceType == null) {
            Toast.makeText(this, "Resource type not specified", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }
        else if (groupName == null) {
            Toast.makeText(this, "Resource group not specified", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        pdfList = new ArrayList<>();
        pdfAdapter = new PdfAdapter(pdfList, new PdfAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(PdfItem pdfItem) {
                Intent pdfIntent = new Intent(PDFView.this, PdfViewerActivity.class);
                pdfIntent.putExtra("pdf_url", pdfItem.getUrl());
                startActivity(pdfIntent);
            }
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(pdfAdapter);

        buttonUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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

    private static final int PICK_PDF_REQUEST = 1;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_PDF_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri pdfUri = data.getData();
            promptForTitleAndUpload(pdfUri);
        } else {
            Toast.makeText(this, "No PDF selected", Toast.LENGTH_SHORT).show();
        }
    }

    private void promptForTitleAndUpload(Uri pdfUri) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Enter PDF Title");

        final EditText input = new EditText(this);
        input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_CAP_WORDS);
        builder.setView(input);

        builder.setPositiveButton("Upload", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String title = input.getText().toString().trim();
                if (!title.isEmpty()) {
                    uploadPDF(pdfUri, title);
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

    private void uploadPDF(Uri pdfUri, String title) {
        String fileName = "pdfs/" + UUID.randomUUID().toString() + ".pdf";
        StorageReference storageRef = storage.getReference().child(fileName);

        ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Uploading PDF...");
        progressDialog.show();

        storageRef.putFile(pdfUri)
                .addOnSuccessListener(taskSnapshot -> {
                    storageRef.getDownloadUrl().addOnSuccessListener(uri -> {
                        PdfItem pdfItem = new PdfItem(title, uri.toString(), System.currentTimeMillis(), resourceType, groupName);

                        firestore.collection("pdfs")
                                .add(pdfItem)
                                .addOnSuccessListener(documentReference -> {
                                    progressDialog.dismiss();
                                    Toast.makeText(PDFView.this, "PDF uploaded successfully", Toast.LENGTH_SHORT).show();
                                    loadPdfs(); // Refresh the list
                                })
                                .addOnFailureListener(e -> {
                                    progressDialog.dismiss();
                                    Toast.makeText(PDFView.this, "Failed to upload metadata: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                                });
                    }).addOnFailureListener(e -> {
                        progressDialog.dismiss();
                        Toast.makeText(PDFView.this, "Failed to get download URL: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    });
                })
                .addOnFailureListener(e -> {
                    progressDialog.dismiss();
                    Toast.makeText(PDFView.this, "Failed to upload PDF: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                });
    }

    private void loadPdfs() {
        firestore.collection("pdfs")
                .whereEqualTo("resourceType", resourceType)
                .whereEqualTo("groupName", groupName)
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
}
