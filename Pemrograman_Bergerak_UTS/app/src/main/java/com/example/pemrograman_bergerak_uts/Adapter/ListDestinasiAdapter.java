package com.example.pemrograman_bergerak_uts.Adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.pemrograman_bergerak_uts.DetailActivity;
import com.example.pemrograman_bergerak_uts.Model.Model_Destinasi;
import com.example.pemrograman_bergerak_uts.R;

import java.util.ArrayList;

public class ListDestinasiAdapter extends RecyclerView.Adapter<ListDestinasiAdapter.ListViewHolder> {

    private ArrayList<Model_Destinasi> list_destinasi;

    public ListDestinasiAdapter(ArrayList<Model_Destinasi> list) {
        this.list_destinasi = list;
    }


    public class ListViewHolder extends RecyclerView.ViewHolder {
        ImageView imgPhoto;
        TextView tvName, tvDescription;

        public ListViewHolder(@NonNull View itemView) {
            super(itemView);
            imgPhoto = itemView.findViewById(R.id.img_item);
            tvName = itemView.findViewById(R.id.tv_item_name);
            tvDescription = itemView.findViewById(R.id.tv_item_description);
        }
    }

    @NonNull
    @Override
    public ListDestinasiAdapter.ListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_row_destinasi, parent, false);
        return new ListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ListDestinasiAdapter.ListViewHolder holder, int position) {
        final Model_Destinasi destinasi = list_destinasi.get(position);
        Glide.with(holder.itemView.getContext())
                .load(destinasi.getImage_destinasi())
                .apply(new RequestOptions().override(250,250))
                .into(holder.imgPhoto);
        holder.tvName.setText(destinasi.getNama_destinasi());
        holder.tvDescription.setText(destinasi.getDeskripssi_destinasi());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(holder.itemView.getContext(), DetailActivity.class)
                        .putExtra("image", destinasi.getImage_destinasi())
                        .putExtra("name", destinasi.getNama_destinasi())
                        .putExtra("detail", destinasi.getDeskripssi_destinasi());
                holder.itemView.getContext().startActivity(intent);

            }
        });
    }

    @Override
    public int getItemCount() {
        return list_destinasi.size();
    }
}
