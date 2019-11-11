package com.example.polinesukm.Adapter;

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
import com.example.polinesukm.DetailPage;
import com.example.polinesukm.Model.ukm_polines;
import com.example.polinesukm.R;

import java.util.ArrayList;

public class listAdapterUKM extends RecyclerView.Adapter<listAdapterUKM.ListViewHolder> {
    private ArrayList<ukm_polines> list_ukm;

    public listAdapterUKM(ArrayList<ukm_polines> list) {
        this.list_ukm = list;
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
    public listAdapterUKM.ListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_row_ukm, parent, false);
        return new ListViewHolder(view);    }

    @Override
    public void onBindViewHolder(@NonNull final listAdapterUKM.ListViewHolder holder, int position) {
        final ukm_polines ukm = list_ukm.get(position);
        Glide.with(holder.itemView.getContext())
                .load(ukm.getLogo_ukm())
                .apply(new RequestOptions().override(250,250))
                .into(holder.imgPhoto);
        holder.tvName.setText(ukm.getNama_ukm());
        holder.tvDescription.setText(ukm.getDeskripsi_ukm());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.itemView.getContext().startActivity(new Intent(holder.itemView.getContext(), DetailPage.class)
                        .putExtra("img", ukm.getLogo_ukm())
                        .putExtra("title", ukm.getNama_ukm())
                        .putExtra("descriptions", ukm.getDeskripsi_ukm()));
            }
        });

    }



    @Override
    public int getItemCount() {
        return list_ukm.size();
    }
}
