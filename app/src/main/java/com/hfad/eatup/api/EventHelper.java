package com.hfad.eatup.api;

import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.hfad.eatup.Model.Event;

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

    public static Task<Void> createEvent(String uuid, String title, String address, String city, Date date, int maxppl, String description, String creator) {
        Event eventToCreate = new Event(uuid, title, address, city, date, maxppl, description, creator);


        return EventHelper.getEventsCollection()
                .document(uuid)
                .set(eventToCreate);
    }

    public static Query getAllParticipatingEvent(Query query, String uid){
        return query.whereArrayContains("listppl",uid);
    }

    public static Query getIntNextEvent(Query query, int limit){
        return query.limit(limit);
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

    public static Task<DocumentSnapshot> getEvent (String uid){
        return EventHelper.getEventsCollection().document(uid).get();
    }
}
