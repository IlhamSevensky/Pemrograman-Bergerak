package com.ilham.tubes.mybiodata.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ilham.tubes.mybiodata.R;
import com.ilham.tubes.mybiodata.model.BiodataModel;

import java.util.ArrayList;

public class ListBiodataAdapter extends RecyclerView.Adapter<ListBiodataAdapter.ListViewHolder> {
    private ArrayList<BiodataModel> listBiodata;
    private OnItemClickCallback onItemClickCallback;

    public void setOnItemClickCallback(OnItemClickCallback onItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback;
    }

    public interface OnItemClickCallback {
        void onItemClicked(BiodataModel data);
    }

    public ListBiodataAdapter(ArrayList<BiodataModel> list) {
        this.listBiodata = list;
    }

    class ListViewHolder extends RecyclerView.ViewHolder {
        ImageView imgPhoto;
        RelativeLayout relativeRow;
        TextView tvName, tvID;

        public ListViewHolder(@NonNull View itemView) {
            super(itemView);
            imgPhoto = itemView.findViewById(R.id.img_photo);
            tvName = itemView.findViewById(R.id.tv_name);
            tvID = itemView.findViewById(R.id.tv_id);
            relativeRow = itemView.findViewById(R.id.relative_row);
        }
    }

    @NonNull
    @Override
    public ListBiodataAdapter.ListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_row_biodata, parent, false);
        return new ListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ListBiodataAdapter.ListViewHolder holder, int position) {
        final BiodataModel biodataModel = listBiodata.get(position);

        holder.tvID.setText(String.valueOf(biodataModel.getId()));
        holder.tvName.setText(biodataModel.getName());

        if (biodataModel.getGender().equals(holder.itemView.getResources().getString(R.string.gender_male))) {
            holder.imgPhoto.setImageResource(R.drawable.icon_male);
            holder.relativeRow.setBackgroundResource(R.drawable.bg_row_male);
        } else if (biodataModel.getGender().equals(holder.itemView.getResources().getString(R.string.gender_female))) {
            holder.imgPhoto.setImageResource(R.drawable.icon_female);
            holder.relativeRow.setBackgroundResource(R.drawable.bg_row_female);
        } else {
            holder.imgPhoto.setImageResource(R.drawable.ic_unavailable);
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onItemClickCallback.onItemClicked(listBiodata.get(holder.getAdapterPosition()));
            }
        });
    }

    @Override
    public int getItemCount() {
        return listBiodata.size();
    }

}
