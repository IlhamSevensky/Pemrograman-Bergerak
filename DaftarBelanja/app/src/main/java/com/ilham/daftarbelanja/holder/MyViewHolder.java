package com.ilham.daftarbelanja.holder;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ilham.daftarbelanja.R;

import java.text.NumberFormat;
import java.util.Locale;

public class MyViewHolder extends RecyclerView.ViewHolder {
    public View myview;

    public MyViewHolder(@NonNull View itemView) {
        super(itemView);
        myview=itemView;
    }

    public void setType(String type){
        TextView mType = myview.findViewById(R.id.type);
        mType.setText(type);
    }

    public void setNote(String note){
        TextView mNote = myview.findViewById(R.id.note);
        mNote.setText(note);
    }

    public void setDate(String date){
        TextView mDate = myview.findViewById(R.id.date);
        mDate.setText(date);
    }

    public void setAmount(int amount){
        TextView mAmount = myview.findViewById(R.id.amount);
//        String stam = String.valueOf(amount);
        Locale localeID = new Locale("in", "ID");
        NumberFormat numberFormat = NumberFormat.getCurrencyInstance(localeID);
        mAmount.setText(numberFormat.format(amount));
    }
}
