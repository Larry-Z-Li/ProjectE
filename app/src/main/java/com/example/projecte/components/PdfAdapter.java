package com.example.projecte.components;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projecte.PdfItem;
import com.example.projecte.R;

import java.util.List;

public class PdfAdapter extends RecyclerView.Adapter<PdfAdapter.PdfViewHolder> {

    public interface OnItemClickListener {
        void onItemClick(PdfItem pdfItem);
    }

    private List<PdfItem> pdfList;
    private OnItemClickListener listener;

    public PdfAdapter(List<PdfItem> pdfList, OnItemClickListener listener) {
        this.pdfList = pdfList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public PdfViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_pdf, parent, false);
        return new PdfViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PdfViewHolder holder, int position) {
        PdfItem pdfItem = pdfList.get(position);
        holder.textViewName.setText(pdfItem.getTitle()); // Use title instead of name

        holder.itemView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onItemClick(pdfItem);
            }
        });
    }

    @Override
    public int getItemCount() {
        return pdfList.size();
    }

    public static class PdfViewHolder extends RecyclerView.ViewHolder {
        TextView textViewName;

        public PdfViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewName = itemView.findViewById(R.id.text_view_name);
        }
    }
}
