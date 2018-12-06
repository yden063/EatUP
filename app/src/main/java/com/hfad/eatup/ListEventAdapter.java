package com.hfad.eatup;

import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.hfad.eatup.Model.Event;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;

public class ListEventAdapter extends FirestoreRecyclerAdapter<Event, ListEventHolder> {

    public interface Listener {
        void onDataChanged();
    }

    private Listener callback;

    public ListEventAdapter(@NonNull FirestoreRecyclerOptions<Event> options, Listener callback) {
        super(options);

        this.callback =callback;
    }

    @Override
    protected void onBindViewHolder(@NonNull ListEventHolder holder, int position, @NonNull Event model) {

        DateFormat dateFormat = new SimpleDateFormat("dd, MMM, HH:mm",Locale.CANADA);
        String address = "at "+model.getAddress();
        String city = "in "+model.getCity();
        String date = "Starting "+dateFormat.format(model.getDate());
        holder.textTitle.setText(model.getTitle());

        holder.adressText.setText(address);
        holder.cityText.setText(city);
        holder.textDate.setText(date);
    }

    @NonNull
    @Override
    public ListEventHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.list_event_item, viewGroup, false);

        return new ListEventHolder(view);
    }

    @Override
    public void onDataChanged() {
        super.onDataChanged();
        this.callback.onDataChanged();
    }
}
