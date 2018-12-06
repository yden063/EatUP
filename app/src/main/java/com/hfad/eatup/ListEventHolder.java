package com.hfad.eatup;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.bumptech.glide.RequestManager;
import com.hfad.eatup.Model.Event;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ListEventHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.item_adress)
    TextView adressText;
    @BindView(R.id.item_city)
    TextView cityText;
    @BindView(R.id.item_title)
    TextView textTitle;


    public ListEventHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }
}
