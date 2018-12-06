package com.hfad.eatup.api;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.hfad.eatup.ListEventHolder;
import com.hfad.eatup.Model.Event;
import com.hfad.eatup.Model.User;
import com.hfad.eatup.R;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class EventHelper {
    private static final String COLLECTION_NAME = "events";

    // --- COLLECTION REFERENCE ---

    public static CollectionReference getEventsCollection(){
        return FirebaseFirestore.getInstance().collection(COLLECTION_NAME);
    }

    // --- CREATE ---
    public static Task<Void> createEvent(String title, String address, String city, Date date, int maxppl, String description, String creator) {
        Event eventToCreate = new Event(title, address, city, date, maxppl, description, creator);



    return EventHelper.getEventsCollection()
            .document()
            .set(eventToCreate);
    }

    public static Query getAllTheirEvent(String uid){
        return EventHelper.getEventsCollection()
                .whereGreaterThan("uidCreator",uid)
                .whereLessThan("uidCreator",uid)
                .orderBy("date");
    }

    public static Query getAllYourEvent(String uid){
        return EventHelper.getEventsCollection()
                .whereEqualTo("uidCreator",uid)
                .orderBy("date");
    }

    public static Query getYourNextEvent(String uid){
        return EventHelper.getEventsCollection()
                .whereEqualTo("uidCreator",uid)
                .orderBy("date")
                .limit(1);
    }


    public static Query getEventByCity(Query query, String city){
        return query.whereEqualTo("city",city);
    }

    public static Query getEventByDate(Query query, String date){
        return query.whereEqualTo("date",date);
    }

    public static Query querryBuilder(){
        return EventHelper.getEventsCollection()
                .orderBy("date");
    }



}
