package com.hfad.eatup.api;

import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.hfad.eatup.Model.Event;
import com.hfad.eatup.Model.User;

import java.util.Date;

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
}
